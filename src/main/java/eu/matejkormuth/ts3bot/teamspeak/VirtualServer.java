/*
 * Copyright (C) 2014 Matej Kormuth <http://matejkormuth.eu>
 * 
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with this program. If not, see
 * <http://www.gnu.org/licenses/>.
 */
package eu.matejkormuth.ts3bot.teamspeak;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.theholywaffle.teamspeak3.api.ChannelProperty;
import com.github.theholywaffle.teamspeak3.api.event.TS3EventType;
import com.github.theholywaffle.teamspeak3.api.wrapper.ChannelInfo;
import com.github.theholywaffle.teamspeak3.api.wrapper.ServerQueryInfo;
import com.github.theholywaffle.teamspeak3.api.wrapper.VirtualServerInfo;

import eu.matejkormuth.ts3bot.api.IBan;
import eu.matejkormuth.ts3bot.api.IChannel;
import eu.matejkormuth.ts3bot.api.IChannelGroup;
import eu.matejkormuth.ts3bot.api.IClient;
import eu.matejkormuth.ts3bot.api.IServerGroup;
import eu.matejkormuth.ts3bot.teamspeak.annotations.CachedValue;

/**
 * Class representating virtual server on teamspeak server.
 */
public class VirtualServer implements Updatable,
        eu.matejkormuth.ts3bot.api.IVirtualServer {
    private final Logger                     log = LoggerFactory.getLogger(this.getClass());
    
    private final int                        id;
    private final int                        port;
    private int                              clientsOnline;
    private int                              queryClientsOnline;
    private int                              maxClients;
    private long                             uptime;
    private String                           name;
    
    // Whether to automatically register each channel events.
    private final boolean                    autoRegisterEvents;
    
    // Current channel.
    protected IChannel                       currentChannel;
    
    // Some self values.
    protected int                            selfId;
    protected int                            selfDBId;
    protected String                         selfUId;
    
    // Cached channels. Does not reflect real channels on server.
    private final Map<Integer, Channel>      channels;
    // Cached clients. Does not relfect real clients on server.
    private final Map<Integer, Client>       clients;
    
    // Cached bans. Does not relfect real bans on server.
    private final Map<Integer, Ban>          bans;
    
    // Cached server groups. Does not reflect real server groups on server. 
    private final Map<Integer, ServerGroup>  serverGroups;
    // Cached channel groups. Does not relfect real channel groups on server.
    private final Map<Integer, ChannelGroup> channelGroups;
    
    // Reference to connection to allow api calls from virtual server.
    private final QueryConnection            connection;
    
    protected VirtualServer(final QueryConnection connection, final int id,
            final int port, final boolean autoRegister) {
        this.id = id;
        this.port = port;
        
        this.autoRegisterEvents = autoRegister;
        this.connection = connection;
        
        this.channels = Collections.synchronizedMap(new HashMap<Integer, Channel>());
        this.clients = Collections.synchronizedMap(new HashMap<Integer, Client>());
        this.bans = Collections.synchronizedMap(new HashMap<Integer, Ban>());
        this.serverGroups = Collections.synchronizedMap(new HashMap<Integer, ServerGroup>());
        this.channelGroups = Collections.synchronizedMap(new HashMap<Integer, ChannelGroup>());
        
        // Query basic info about myself.
        ServerQueryInfo info = this.connection.getApi().whoAmI();
        this.selfDBId = info.getDatabaseId();
        this.selfId = info.getId();
        this.selfUId = info.getUniqueIdentifier();
        
        // Load all channel and register channel events.
        this.log.info("Loading all channels...");
        this.getChannels(true);
        this.log.debug("Currently has " + this.channels.size() + " channels.");
    }
    
    protected void setClientsOnline(final int clientsOnline) {
        this.clientsOnline = clientsOnline;
    }
    
    protected void setQueryClientsOnline(final int queryClientsOnline) {
        this.queryClientsOnline = queryClientsOnline;
    }
    
    protected void setMaxClients(final int maxClients) {
        this.maxClients = maxClients;
    }
    
    protected void setUptime(final int uptime) {
        this.uptime = uptime;
    }
    
    protected void setName(final @Nonnull String name) {
        this.name = name;
    }
    
    public int getId() {
        return this.id;
    }
    
    public int getPort() {
        return this.port;
    }
    
    @CachedValue
    public int getClientsOnline() {
        return this.clientsOnline;
    }
    
    @CachedValue
    public int getQueryClientsOnline() {
        return this.queryClientsOnline;
    }
    
    @CachedValue
    public int getMaxClients() {
        return this.maxClients;
    }
    
    @CachedValue
    public long getUptime() {
        return this.uptime;
    }
    
    @CachedValue
    public String getName() {
        return this.name;
    }
    
    public void deleteAllBans() {
        this.connection.getApi().deleteAllBans();
    }
    
    public void join(@Nonnull final IChannel channel) {
        this.currentChannel = channel;
        this.connection.getApi().moveClient(this.connection.getApi().whoAmI().getId(),
                channel.getId());
        this.log.info("Joining channel #{}", channel.getId());
        // Register events for this channel.
        if (this.isAutoRegisterEvents()) {
            int cid = channel.getId();
            this.log.debug("Autoregistering channel events for channel #" + cid);
            this.connection.getApi().registerEvent(TS3EventType.CHANNEL, cid);
            this.connection.getApi().registerEvent(TS3EventType.TEXT_CHANNEL, cid);
        }
    }
    
    public IBan ban(@Nonnull final IClient client, @Nonnull final String reason) {
        return client.ban(reason);
    }
    
    public void addBan(@Nullable final String ip, @Nullable final String uid,
            @Nullable final String name, final long timeInSeconds,
            @Nullable final String reason) {
        this.connection.getApi().addBan(ip, name, uid, timeInSeconds, reason);
    }
    
    public IBan ban(@Nonnull final IClient client, final long timeInSeconds) {
        return client.ban(timeInSeconds);
    }
    
    public IBan ban(@Nonnull final IClient client, final long timeInSeconds,
            @Nonnull final String reason) {
        return client.ban(reason, timeInSeconds);
    }
    
    public void kickFromServer(@Nonnull final IClient client) {
        this.getConnection().getApi().kickClientFromServer(client.getId());
    }
    
    public void kickFromServer(@Nonnull final IClient client,
            @Nonnull final String reason) {
        this.getConnection().getApi().kickClientFromServer(reason, client.getId());
    }
    
    public void kickFromChannel(@Nonnull final IClient client) {
        this.getConnection().getApi().kickClientFromChannel(client.getId());
    }
    
    public void kickFromChannel(@Nonnull final IClient client,
            @Nonnull final String reason) {
        this.getConnection().getApi().kickClientFromChannel(reason, client.getId());
    }
    
    public void broadcast(@Nonnull final String message) {
        this.connection.getApi().broadcast(message);
    }
    
    public void sendMessage(@Nonnull final String message) {
        this.connection.getApi().sendServerMessage(this.id, message);
    }
    
    public Channel createChannel(@Nonnull final String name) {
        int channelId = this.connection.getApi().createChannel(name,
                new HashMap<ChannelProperty, String>());
        this.channels.put(channelId, new Channel(this, channelId, 0));
        this.log.info("Creating channel with name {}", name);
        return this.channels.get(channelId);
    }
    
    @Nullable
    public ServerGroup getServerGroupById(final int id) {
        if (this.serverGroups.containsKey(id)) { return this.serverGroups.get(id); }
        // Must request server to be sure.
        
        return null;
    }
    
    @Nullable
    public ChannelGroup getChannelGroupById(final int id) {
        if (this.channelGroups.containsKey(id)) { return this.channelGroups.get(id); }
        // Must request server to be sure.
        
        return null;
    }
    
    public int getSelfDBId() {
        return this.selfDBId;
    }
    
    public int getSelfId() {
        return this.selfId;
    }
    
    public String getSelfUId() {
        return this.selfUId;
    }
    
    public String getSelfNickname() {
        return this.connection.getNickname();
    }
    
    @CachedValue
    @Nullable
    public Client getClientByName(@Nonnull final String nickname) {
        for (Client c : this.clients.values()) {
            if (c.getNickname().equals(nickname)) { return c; }
        }
        // Must request server to be sure.
        List<com.github.theholywaffle.teamspeak3.api.wrapper.Client> clients = this.connection.getApi()
                .getClientByName(nickname);
        if (clients.get(0) != null) {
            com.github.theholywaffle.teamspeak3.api.wrapper.Client c = clients.get(0);
            if (!this.clients.containsKey(c.getId())) {
                this.clients.put(
                        c.getId(),
                        new Client(this, c.getNickname(), c.getId(),
                                c.getUniqueIdentifier(), c.getDatabaseId(),
                                c.getCountry(), c.getPlatform(), c.getVersion(),
                                ClientType.byId(c.getType())));
            }
            this.clients.get(c.getId()).updateBy(c);
            return this.clients.get(c.getId());
        }
        return null;
    }
    
    @CachedValue
    @Nullable
    public Client getClientByDatabaseId(final int databaseId) {
        for (Client c : this.clients.values()) {
            if (c.getDatabaseId() == databaseId) { return c; }
        }
        return null;
    }
    
    @CachedValue
    @Nullable
    public Client getClientById(final int id) {
        for (Client c : this.clients.values()) {
            if (c.getId() == id) { return c; }
        }
        // Must request server to be sure.
        
        return null;
    }
    
    @Nullable
    public Client getClientByUid(@Nonnull final String uid) {
        for (Client c : this.clients.values()) {
            if (c.getUid().equals(uid)) { return c; }
        }
        // Must request server to be sure.
        
        return null;
    }
    
    @CachedValue
    public Collection<IClient> getClients() {
        return new ArrayList<IClient>(this.clients.values());
    }
    
    public Collection<IClient> getClients(final boolean update) {
        if (!update) {
            return this.getClients();
        }
        else {
            // Must do full update.
            for (com.github.theholywaffle.teamspeak3.api.wrapper.Client c : this.connection.getApi()
                    .getClients()) {
                if (!this.clients.containsKey(c.getId())) {
                    this.clients.put(
                            c.getId(),
                            new Client(this, c.getNickname(), c.getId(),
                                    c.getUniqueIdentifier(), c.getDatabaseId(),
                                    c.getCountry(), c.getPlatform(), c.getVersion(),
                                    ClientType.byId(c.getType())));
                }
                this.clients.get(c.getId()).updateBy(c);
            }
            return new ArrayList<IClient>(this.clients.values());
        }
    }
    
    @Nullable
    public Channel getChannelById(final int cid) {
        for (Channel c : this.channels.values()) {
            if (c.getId() == cid) { return c; }
        }
        // Must check server to be sure.
        ChannelInfo ch = this.connection.getApi().getChannelInfo(cid);
        if (ch != null) {
            if (!this.channels.containsKey(cid)) {
                this.channels.put(cid, new Channel(this, cid, ch.getParentChannelId()));
            }
            Channel channel = this.channels.get(cid);
            channel.updateBy(ch);
            return channel;
        }
        return null;
    }
    
    @Nullable
    public Channel getChannelByName(@Nonnull final String name) {
        for (Channel c : this.channels.values()) {
            if (c.getName().equals(name)) { return c; }
        }
        // Must check server to be sure.
        com.github.theholywaffle.teamspeak3.api.wrapper.Channel ch = this.connection.getApi()
                .getChannelByName(name);
        if (ch != null) {
            if (!this.channels.containsKey(ch.getId())) {
                this.channels.put(ch.getId(),
                        new Channel(this, ch.getId(), ch.getParentChannelId()));
            }
            Channel channel = this.channels.get(ch.getId());
            channel.updateBy(ch);
            return channel;
        }
        return null;
    }
    
    @CachedValue
    public Collection<IChannel> getChannels() {
        return new ArrayList<IChannel>(this.channels.values());
    }
    
    public Collection<IChannel> getChannels(final boolean forceUpdate) {
        if (!forceUpdate) {
            return this.getChannels();
        }
        else {
            // Must do full update.
            for (com.github.theholywaffle.teamspeak3.api.wrapper.Channel ch : this.connection.getApi()
                    .getChannels()) {
                if (!this.channels.containsKey(ch.getId())) {
                    // Create new group.
                    this.channels.put(ch.getId(),
                            new Channel(this, ch.getId(), ch.getParentChannelId()));
                }
                // Update values.
                this.channels.get(ch.getId()).updateBy(ch);
            }
            return new ArrayList<IChannel>(this.channels.values());
        }
    }
    
    public Collection<IChannelGroup> getChannelGroups() {
        // Must do full update.
        for (com.github.theholywaffle.teamspeak3.api.wrapper.ChannelGroup g : this.connection.getApi()
                .getChannelGroups()) {
            if (!this.channelGroups.containsKey(g.getGroupId())) {
                // Create new group.
                this.channelGroups.put(g.getGroupId(),
                        new ChannelGroup(this, g.getGroupId(), g.getName()));
            }
            // Update values.
            this.channelGroups.get(g.getGroupId()).updateBy(g);
        }
        
        return new ArrayList<IChannelGroup>(this.channelGroups.values());
    }
    
    public Collection<IServerGroup> getServerGroups() {
        // Must do full update.
        for (com.github.theholywaffle.teamspeak3.api.wrapper.ServerGroup g : this.connection.getApi()
                .getServerGroups()) {
            if (!this.serverGroups.containsKey(g.getId())) {
                // Create new group.
                this.serverGroups.put(g.getId(),
                        new ServerGroup(this, g.getId(), g.getName()));
            }
            // Update values.
            this.serverGroups.get(g.getId()).updateBy(g);
        }
        
        return new ArrayList<IServerGroup>(this.serverGroups.values());
    }
    
    @Nullable
    public Ban getBanById(final int bid) {
        for (Ban b : this.bans.values()) {
            if (b.getId() == bid) { return b; }
        }
        // TODO: Invent check.
        
        return null;
    }
    
    public Collection<IBan> getBans() {
        return new ArrayList<IBan>(this.bans.values());
    }
    
    public Collection<IBan> getBans(final boolean forceUpdate) {
        if (!forceUpdate) {
            return this.getBans();
        }
        else {
            // Must do full update.
            for (com.github.theholywaffle.teamspeak3.api.wrapper.Ban b : this.connection.getApi()
                    .getBans()) {
                if (!this.bans.containsKey(b.getId())) {
                    // Create new group.
                    this.bans.put(b.getId(), new Ban(this, b.getId()));
                }
                // Update values.
                this.bans.get(b.getId()).updateBy(b);
            }
            
            return new ArrayList<IBan>(this.bans.values());
        }
    }
    
    public void update() {
        this.updateBy(this.connection.getApi().getServerInfo());
    }
    
    public IChannel getCurrentChannel() {
        return this.currentChannel;
    }
    
    private void updateBy(final VirtualServerInfo si) {
        this.clientsOnline = si.getClientsOnline();
        this.maxClients = si.getMaxClients();
        this.name = si.getName();
        this.queryClientsOnline = si.getQueryClientsOnline();
        this.uptime = si.getUptime();
    }
    
    protected QueryConnection getConnection() {
        return this.connection;
    }
    
    protected void removeChannel(final Channel channel) {
        for (Iterator<Channel> iterator = this.channels.values().iterator(); iterator.hasNext();) {
            Channel c = iterator.next();
            if (c == channel) {
                iterator.remove();
            }
        }
    }
    
    protected void removeClient(final Client client) {
        for (Iterator<Client> iterator = this.clients.values().iterator(); iterator.hasNext();) {
            Client c = iterator.next();
            if (c == client) {
                iterator.remove();
            }
        }
    }
    
    protected void removeServerGroup(final ServerGroup group) {
        for (Iterator<ServerGroup> iterator = this.serverGroups.values().iterator(); iterator.hasNext();) {
            ServerGroup g = iterator.next();
            if (g == group) {
                iterator.remove();
            }
        }
    }
    
    protected void removeChannelGroup(final ChannelGroup group) {
        for (Iterator<ChannelGroup> iterator = this.channelGroups.values().iterator(); iterator.hasNext();) {
            ChannelGroup g = iterator.next();
            if (g == group) {
                iterator.remove();
            }
        }
    }
    
    @Nonnull
    protected Channel addChannel(@Nonnull final Channel channel) {
        this.channels.put(channel.getId(), channel);
        return channel;
    }
    
    protected boolean isAutoRegisterEvents() {
        return this.autoRegisterEvents;
    }
    
    @Nonnull
    protected Client addClient(@Nonnull final Client client) {
        this.clients.put(client.getId(), client);
        return client;
    }
}
