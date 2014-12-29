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
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.theholywaffle.teamspeak3.api.ChannelProperty;
import com.github.theholywaffle.teamspeak3.api.event.TS3EventType;
import com.github.theholywaffle.teamspeak3.api.wrapper.ChannelInfo;

import eu.matejkormuth.ts3bot.api.IChannel;
import eu.matejkormuth.ts3bot.api.IChannelGroup;
import eu.matejkormuth.ts3bot.api.IClient;
import eu.matejkormuth.ts3bot.api.IVirtualServer;
import eu.matejkormuth.ts3bot.teamspeak.annotations.CachedValue;

/**
 * Class representating channel on teamspeak server.
 */
public class Channel implements Updatable, eu.matejkormuth.ts3bot.api.IChannel {
    private final Logger        log = LoggerFactory.getLogger(this.getClass());
    
    private final int           cid;
    private int                 pcid;
    private String              name;
    private int                 order;
    private String              topic;
    private boolean             defaultChannel;
    private boolean             passwordProtected;
    private String              password;
    private boolean             permanent;
    private boolean             semiPermanent;
    private Codec               codec;
    private int                 codecQuality;
    private long                iconId;
    private int                 totalClientsFamily;
    private int                 maxClients;
    private int                 maxFamilyClients;
    private int                 totalClients;
    private int                 neededSubscribePower;
    private String              phoneticName;
    
    private final VirtualServer server;
    
    protected Channel(final VirtualServer server, final int cid, final int pcid) {
        this.cid = cid;
        this.pcid = pcid;
        this.server = server;
    }
    
    protected void setName(final @Nonnull String name) {
        this.name = name;
    }
    
    protected void setOrder(final int order) {
        this.order = order;
    }
    
    protected void setTopic(final @Nullable String topic) {
        this.topic = topic;
    }
    
    protected void setDefaultChannel(final boolean defaultChannel) {
        this.defaultChannel = defaultChannel;
    }
    
    protected void setPasswordProtected(final boolean passwordProtected) {
        this.passwordProtected = passwordProtected;
    }
    
    protected void setPermanent(final boolean permanent) {
        this.permanent = permanent;
    }
    
    protected void setSemiPermanent(final boolean semiPermanent) {
        this.semiPermanent = semiPermanent;
    }
    
    protected void setCodec(final Codec codec) {
        this.codec = codec;
    }
    
    protected void setCodecQuality(final int codecQuality) {
        this.codecQuality = codecQuality;
    }
    
    protected void setIconId(final long iconId) {
        this.iconId = iconId;
    }
    
    protected void setTotalClientsFamily(final int totalClientsFamily) {
        this.totalClientsFamily = totalClientsFamily;
    }
    
    protected void setMaxClients(final int maxClients) {
        this.maxClients = maxClients;
    }
    
    protected void setMaxFamilyClients(final int maxFamilyClients) {
        this.maxFamilyClients = maxFamilyClients;
    }
    
    protected void setTotalClients(final int totalClients) {
        this.totalClients = totalClients;
    }
    
    protected void setNeededSubscribePower(final int neededSubscribePower) {
        this.neededSubscribePower = neededSubscribePower;
    }
    
    protected void setPcid(final int pcid) {
        this.pcid = pcid;
    }
    
    public String getName() {
        return this.name;
    }
    
    public int getId() {
        return this.cid;
    }
    
    @CachedValue
    public int getParentChannelId() {
        return this.pcid;
    }
    
    @CachedValue
    public Channel getParent() {
        return this.server.getChannelById(this.pcid);
    }
    
    @CachedValue
    public int getOrder() {
        return this.order;
    }
    
    @CachedValue
    public String getTopic() {
        return this.topic;
    }
    
    @CachedValue
    public boolean isDefaultChannel() {
        return this.defaultChannel;
    }
    
    @CachedValue
    public boolean isPasswordProtected() {
        return this.passwordProtected;
    }
    
    @CachedValue
    public boolean isPermanent() {
        return this.permanent;
    }
    
    @CachedValue
    public boolean isSemiPermanent() {
        return this.semiPermanent;
    }
    
    @CachedValue
    public Codec getCodec() {
        return this.codec;
    }
    
    @CachedValue
    public int getCodecQuality() {
        return this.codecQuality;
    }
    
    @CachedValue
    public long getIconId() {
        return this.iconId;
    }
    
    @CachedValue
    public int getTotalClientsFamily() {
        return this.totalClientsFamily;
    }
    
    @CachedValue
    public int getMaxClients() {
        return this.maxClients;
    }
    
    @CachedValue
    public int getMaxFamilyClients() {
        return this.maxFamilyClients;
    }
    
    @CachedValue
    public int getTotalClients() {
        return this.totalClients;
    }
    
    @CachedValue
    public int getNeededSubscribePower() {
        return this.neededSubscribePower;
    }
    
    @CachedValue
    public String getPassword() {
        return this.password;
    }
    
    @CachedValue
    public String getPhoneticName() {
        return this.phoneticName;
    }
    
    public VirtualServer getServer() {
        return this.server;
    }
    
    public void registerEvents() {
        this.log.debug("Registering channel events for channel #" + this.cid);
        this.server.getConnection()
                .getApi()
                .registerEvent(TS3EventType.CHANNEL, this.cid);
        this.server.getConnection()
                .getApi()
                .registerEvent(TS3EventType.TEXT_CHANNEL, this.cid);
    }
    
    public Channel createSubChannel(@Nonnull final String name) {
        HashMap<com.github.theholywaffle.teamspeak3.api.ChannelProperty, String> props = new HashMap<com.github.theholywaffle.teamspeak3.api.ChannelProperty, String>();
        props.put(com.github.theholywaffle.teamspeak3.api.ChannelProperty.CPID,
                Integer.toString(this.getId()));
        int channelId = this.server.getConnection().getApi().createChannel(name, props);
        return this.server.addChannel(new Channel(this.server, channelId, this.getId()));
    }
    
    public void join() {
        this.server.currentChannel = this;
        this.server.getConnection()
                .getApi()
                .moveClient(this.server.getConnection().getApi().whoAmI().getId(),
                        this.getId());
    }
    
    public void setProperty(
            final eu.matejkormuth.ts3bot.teamspeak.properties.ChannelProperty property,
            @Nonnull final String value) {
        if (property.isChangeable()) {
            this.editChannel(Collections.singletonMap(property, value));
        }
        else {
            throw new IllegalArgumentException(
                    "Can't change unchangeable ChannelProperty!");
        }
    }
    
    public void setProperties(
            @Nonnull final Map<eu.matejkormuth.ts3bot.teamspeak.properties.ChannelProperty, String> properties) {
        for (eu.matejkormuth.ts3bot.teamspeak.properties.ChannelProperty p : properties.keySet()) {
            if (!p.isChangeable()) { throw new IllegalArgumentException(
                    "Can't change unchangeable ChannelProperty!"); }
        }
        this.editChannel(properties);
        
    }
    
    public String getProperty(
            final eu.matejkormuth.ts3bot.teamspeak.properties.ChannelProperty property) {
        ChannelInfo ci = this.server.getConnection()
                .getApi()
                .getChannelInfo(this.getId());
        this.updateBy(ci);
        return ci.get(property.getName());
    }
    
    private void editChannel(
            final Map<eu.matejkormuth.ts3bot.teamspeak.properties.ChannelProperty, String> properties) {
        HashMap<com.github.theholywaffle.teamspeak3.api.ChannelProperty, String> props = new HashMap<com.github.theholywaffle.teamspeak3.api.ChannelProperty, String>(
                properties.size());
        for (Entry<eu.matejkormuth.ts3bot.teamspeak.properties.ChannelProperty, String> entry : properties.entrySet()) {
            props.put(
                    com.github.theholywaffle.teamspeak3.api.ChannelProperty.valueOf(entry.getKey()
                            .name()), entry.getValue());
        }
    }
    
    @CachedValue
    public Collection<IClient> getClients() {
        List<IClient> clients = new ArrayList<IClient>();
        for (IClient c : this.server.getClients()) {
            if (c.getChannel() == this) {
                clients.add(c);
            }
        }
        return clients;
    }
    
    public Collection<IClient> getClients(final boolean forceUpdate) {
        List<IClient> clients = new ArrayList<IClient>();
        for (IClient c : this.server.getClients(forceUpdate)) {
            if (c.getChannel() == this) {
                clients.add(c);
            }
        }
        return clients;
    }
    
    public void setChannelGroup(@Nonnull final IClient client,
            @Nonnull final IChannelGroup group) {
        this.server.getConnection()
                .getApi()
                .setClientChannelGroup(group.getId(), this.cid, client.getDatabaseId());
    }
    
    public void delete() {
        this.server.getConnection().getApi().deleteChannel(this.getId());
        this.server.removeChannel(this);
    }
    
    public void sendMessage(@Nonnull final String message) {
        this.server.getConnection().getApi().sendChannelMessage(this.getId(), message);
    }
    
    protected void updateBy(
            final com.github.theholywaffle.teamspeak3.api.wrapper.Channel ch) {
        this.setCodec(Codec.byId(ch.getCodec()));
        this.setCodecQuality(ch.getCodecQuality());
        this.setDefaultChannel(ch.isDefault());
        this.setIconId(ch.getIconId());
        this.setMaxClients(ch.getMaxClients());
        this.setMaxFamilyClients(ch.getMaxFamilyClients());
        if (ch.getName() != null) {
            this.setName(ch.getName());
        }
        this.setNeededSubscribePower(ch.getNeededSubscribePower());
        this.setOrder(ch.getOrder());
        this.setPasswordProtected(ch.hasPassword());
        this.setPermanent(ch.isPermanent());
        this.setSemiPermanent(ch.isSemiPermanent());
        this.setTopic(ch.getTopic());
        this.setTotalClients(ch.getTotalClients());
        this.setTotalClientsFamily(ch.getTotalClientsFamily());
        this.setPcid(ch.getParentChannelId());
    }
    
    protected void updateBy(final ChannelInfo ch) {
        this.setCodec(Codec.byId(ch.getCodec()));
        this.setCodecQuality(ch.getCodecQuality());
        this.setDefaultChannel(ch.isDefault());
        this.setIconId(ch.getIconId());
        this.setMaxClients(ch.getMaxClients());
        this.setMaxFamilyClients(ch.getMaxFamilyClients());
        if (ch.getName() != null) {
            this.setName(ch.getName());
        }
        this.setOrder(ch.getOrder());
        this.setPasswordProtected(ch.hasPassword());
        this.setPermanent(ch.isPermanent());
        this.setSemiPermanent(ch.isSemiPermanent());
        this.setTopic(ch.getTopic());
        this.setPcid(ch.getParentChannelId());
        this.password = ch.getPassword();
        this.phoneticName = ch.getPhoneticName();
    }
    
    public void update() {
        this.updateBy(this.server.getConnection().getApi().getChannelInfo(this.getId()));
    }
    
    @Override
    public String toString() {
        return "Channel [cid=" + this.cid + ", pcid=" + this.pcid + ", name="
                + this.name + ", order=" + this.order + ", topic=" + this.topic
                + ", defaultChannel=" + this.defaultChannel + ", passwordProtected="
                + this.passwordProtected + ", permanent=" + this.permanent
                + ", semiPermanent=" + this.semiPermanent + ", codec=" + this.codec
                + ", codecQuality=" + this.codecQuality + ", iconId=" + this.iconId
                + ", totalClientsFamily=" + this.totalClientsFamily + ", maxClients="
                + this.maxClients + ", maxFamilyClients=" + this.maxFamilyClients
                + ", totalClients=" + this.totalClients + ", neededSubscribePower="
                + this.neededSubscribePower + ", server=" + this.server + "]";
    }
    
    /**
     * Fluent builder for creating {@link Channel}s on Teamspeak server.
     */
    public static class ChannelBuilder {
        private final Logger                                                                   log;
        private String                                                                         name;
        private final HashMap<com.github.theholywaffle.teamspeak3.api.ChannelProperty, String> props;
        private int                                                                            parentChannelId = 0;
        
        private final IVirtualServer                                                           server;
        
        /**
         * Creates a new {@link ChannelBuilder} that builds channel on specified {@link VirtualServer}.
         * 
         * @param server
         *            server to build channel on
         */
        public ChannelBuilder(final IVirtualServer server) {
            this.server = server;
            this.props = new HashMap<com.github.theholywaffle.teamspeak3.api.ChannelProperty, String>();
            this.log = LoggerFactory.getLogger(this.getClass());
        }
        
        /**
         * Specifies name of this channel.
         * 
         * @param name
         *            name of this channel
         * @return self
         */
        public ChannelBuilder name(final String name) {
            this.name = name;
            return this;
        }
        
        /**
         * Specifies parent channel by {@link Channel} object.
         * 
         * @param channel
         *            parent channel of this channel
         * @return self
         */
        public ChannelBuilder parent(final IChannel channel) {
            this.parentChannelId = channel.getId();
            return this;
        }
        
        /**
         * Specifies parent channel by parent channel id.
         * 
         * @param pcid
         *            parent channel id
         * @return self
         */
        public ChannelBuilder parent(final int pcid) {
            this.parentChannelId = pcid;
            return this;
        }
        
        /**
         * Applies specified value of specified {@link ChannelProperty} to this channel.
         * 
         * @param property
         *            property type to set
         * @param value
         *            value of property
         * @return self
         * @throws IllegalArgumentException
         *             if specfied {@link ChannelProperty} is not changable.
         */
        public ChannelBuilder property(
                final eu.matejkormuth.ts3bot.teamspeak.properties.ChannelProperty property,
                final String value) {
            if (property.isChangeable()) {
                this.props.put(
                        com.github.theholywaffle.teamspeak3.api.ChannelProperty.valueOf(property.name()),
                        value);
                return this;
            }
            else {
                throw new IllegalArgumentException(
                        "Can't set unchangeable ChannelProperty!");
            }
        }
        
        /**
         * Specifies that this channel is permanent.
         * 
         * @return self
         */
        public ChannelBuilder permanent() {
            this.props.put(ChannelProperty.CHANNEL_FLAG_PERMANENT, "1");
            return this;
        }
        
        /**
         * Specifies that this channel is semipermanent.
         * 
         * @return self
         */
        public ChannelBuilder semipermanent() {
            this.props.put(ChannelProperty.CHANNEL_FLAG_SEMI_PERMANENT, "1");
            return this;
        }
        
        /**
         * Creates new channel on Teamspeak server and returns object.
         * 
         * @return newly created {@link Channel}
         * @throws IllegalStateException
         *             if channel name was not specified.
         */
        @Nonnull
        public IChannel build() {
            if (this.name == null) { throw new IllegalStateException(
                    "Channel name can't be null! Channel must have a name."); }
            this.props.put(ChannelProperty.CPID, Integer.toString(this.parentChannelId));
            this.log.info("Creating channel with name {} and parent CID {}", this.name,
                    this.parentChannelId);
            int channelId = ((VirtualServer) this.server).getConnection()
                    .getApi()
                    .createChannel(this.name, this.props);
            Channel c = new Channel((VirtualServer) this.server, channelId,
                    this.parentChannelId);
            ((VirtualServer) this.server).addChannel(c);
            return c;
        }
        
        /**
         * Specifies password for this channel.
         * 
         * @param string
         *            password
         * @return self
         */
        public ChannelBuilder password(final String string) {
            this.props.put(ChannelProperty.CHANNEL_PASSWORD, string);
            return this;
        }
    }
}
