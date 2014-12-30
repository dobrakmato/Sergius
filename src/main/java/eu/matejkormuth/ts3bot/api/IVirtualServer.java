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
package eu.matejkormuth.ts3bot.api;

import java.util.Collection;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import eu.matejkormuth.ts3bot.teamspeak.annotations.CachedValue;

// TODO: Refactor names 
public interface IVirtualServer {
    /**
     * Returns ID of this virtual server.
     * 
     * @return id of this server
     */
    public abstract int getId();
    
    /**
     * Returns port on which this virtual server listens.
     * 
     * @return this server port
     */
    public abstract int getPort();
    
    /**
     * Returns amount of clients online.
     * 
     * @return amount on online clients
     */
    @CachedValue
    public abstract int getClientsOnline();
    
    /**
     * Returns amount of query clients online.
     * 
     * @return amount on online query clients
     */
    @CachedValue
    public abstract int getQueryClientsOnline();
    
    /**
     * Returns maximum amount of clients that can be online at the same time.
     * 
     * @return maximum amount on online clients
     */
    @CachedValue
    public abstract int getMaxClients();
    
    /**
     * Returns uptime of this virtual server.
     * 
     * @return server's uptime
     */
    @CachedValue
    public abstract long getUptime();
    
    /**
     * Returns name of this virtual server.
     * 
     * @return name of this server
     */
    @CachedValue
    public abstract String getName();
    
    /**
     * Removes all bans from server.
     */
    public abstract void deleteAllBans();
    
    /**
     * Joins specified {@link IChannel}.
     * 
     * @param channel
     *            channel to join
     */
    public abstract void join(@Nonnull IChannel channel);
    
    /**
     * Bans specified client for specified reason permanently.
     * 
     * @param client
     *            client to ban
     * @param reason
     *            reason of ban
     * @return {@link IBan} instance
     */
    public abstract IBan ban(@Nonnull IClient client, @Nonnull String reason);
    
    /**
     * Adds ban with at least one parameter.
     * 
     * @param ip
     *            ip to ban
     * @param uid
     *            uid to ban
     * @param name
     *            name to ban
     * @param timeInSeconds
     *            length of ban
     * @param reason
     *            reason of ban
     */
    public abstract void addBan(@Nullable String ip, @Nullable String uid,
            @Nullable String name, long timeInSeconds, @Nullable String reason);
    
    /**
     * Bans specified client temporarly.
     * 
     * @param client
     *            client to ban
     * @param timeInSeconds
     *            length of ban
     * @return {@link IBan} instance
     */
    public abstract IBan ban(@Nonnull IClient client, long timeInSeconds);
    
    /**
     * Bans specified client for specified reason temporarly.
     * 
     * @param client
     *            client to ban
     * @param timeInSeconds
     *            length of ban
     * @param reason
     *            reason of ban
     * @return {@link IBan} instance
     */
    public abstract IBan ban(@Nonnull IClient client, long timeInSeconds,
            @Nonnull String reason);
    
    /**
     * Kick specified client from server without specified reason.
     * 
     * @param client
     *            client to kick
     */
    public abstract void kickFromServer(@Nonnull IClient client);
    
    /**
     * Kick specified client from server with specified reason.
     * 
     * @param client
     *            client to kick
     * @param reason
     *            reason of kick
     */
    public abstract void kickFromServer(@Nonnull IClient client, @Nonnull String reason);
    
    /**
     * Kick specified client from his current channel without specified reason.
     * 
     * @param client
     *            client to kick
     */
    public abstract void kickFromChannel(@Nonnull IClient client);
    
    /**
     * Kick specified client from his current channel with specified reason.
     * 
     * @param client
     *            client to kick
     * @param reason
     *            reason of kick
     */
    public abstract void kickFromChannel(@Nonnull IClient client, @Nonnull String reason);
    
    /**
     * Broadcast specified message.
     * 
     * @param message
     *            message to boardcast
     */
    public abstract void broadcast(@Nonnull String message);
    
    /**
     * Sends message to server (global) chat.
     * 
     * @param message
     *            message to send
     */
    public abstract void sendMessage(@Nonnull String message);
    
    /**
     * Creates new channel with specified name and returns it's instance.
     * 
     * @param name
     *            name of new channel
     * @return {@link IChannel} instnace
     */
    public abstract IChannel createChannel(@Nonnull String name);
    
    /**
     * Returns server group instance by server group id.
     * 
     * @param id
     *            id of server group
     * @return server group instance
     */
    public abstract IServerGroup getServerGroupById(int id);
    
    /**
     * Returns channel group instance by server group id.
     * 
     * @param id
     *            id of channel group
     * @return channel group instance
     */
    public abstract IChannelGroup getChannelGroupById(int id);
    
    /**
     * Returns database id of this query connection.
     * 
     * @return self db id
     */
    public abstract int getSelfDBId();
    
    /**
     * Returns id of this query connection.
     * 
     * @return self id
     */
    public abstract int getSelfId();
    
    /**
     * Returns UId of this query connection.
     * 
     * @return self UId
     */
    public abstract String getSelfUId();
    
    /**
     * Returns nickname of client of this query connection.
     * 
     * @return self nickname
     */
    public abstract String getSelfNickname();
    
    /**
     * Returns client instance by client's nickname.
     * 
     * @param nickname
     *            nickname of client
     * @return {@link IClient} instnace
     */
    public abstract IClient getClientByName(@Nonnull String nickname);
    
    /**
     * Returns client instance by client's database id.
     * 
     * @param databaseId
     *            client's database id
     * @return {@link IClient} instance
     */
    public abstract IClient getClientByDatabaseId(int databaseId);
    
    /**
     * Returns client instance by client's id.
     * 
     * @param id
     *            client's id
     * @return {@link IClient} instance
     */
    public abstract IClient getClientById(int id);
    
    /**
     * Returns client instance by client's unique id.
     * 
     * @param uid
     *            client's unique id
     * @return {@link IClient} instance
     */
    public abstract IClient getClientByUid(@Nonnull String uid);
    
    /**
     * Returns view of current cached clients.
     * 
     * @return collection of clients on server
     */
    @CachedValue
    public abstract Collection<IClient> getClients();
    
    /**
     * Returns view of current cached clients, if <code>forceUpdate</code> is true, client list will be redownloaded
     * from server before returning collection.
     * 
     * @return collection of clients on server
     */
    public abstract Collection<IClient> getClients(boolean update);
    
    /**
     * Returns channel object by it's channel id.
     * 
     * @param cid
     *            channel id
     * @return {@link IChannel} instance
     */
    public abstract IChannel getChannelById(int cid);
    
    /**
     * Returns channel object by it's name.
     * 
     * @param name
     *            cname of channel
     * @return {@link IChannel} instance
     */
    public abstract IChannel getChannelByName(@Nonnull String name);
    
    /**
     * Returns view of current cached channels.
     * 
     * @return collection of channels on server
     */
    @CachedValue
    public abstract Collection<IChannel> getChannels();
    
    /**
     * Returns view of current cached channels, if <code>forceUpdate</code> is true, channel list will be redownloaded
     * from server before returning collection.
     * 
     * @return collection of channels on server
     */
    public abstract Collection<IChannel> getChannels(boolean forceUpdate);
    
    /**
     * Returns collection of channel groups available on server.
     * 
     * @return collection of all channel groups available on server
     */
    public abstract Collection<IChannelGroup> getChannelGroups();
    
    /**
     * Returns collection of server groups available on server.
     * 
     * @return collection of all server groups available on server
     */
    public abstract Collection<IServerGroup> getServerGroups();
    
    /**
     * Returns ban object by ban id.
     * 
     * @param bid
     *            id of ban
     * @return {@link IBan} instance
     */
    public abstract IBan getBanById(int bid);
    
    /**
     * Returns collection of all bans on server.
     * 
     * @return collection of {@link IBan}
     */
    @CachedValue
    public abstract Collection<IBan> getBans();
    
    /**
     * Returns collection of all bans on server, if <code>forceUpdate</code> is true, ban list will be redownloaded from
     * server before returning collection.
     * 
     * @return collection of {@link IBan}
     */
    public abstract Collection<IBan> getBans(boolean forceUpdate);
    
    /**
     * Updates virtual server properties from TeamSpeak server.
     */
    public abstract void update();
    
    /**
     * Returns this conenction current channel.
     * 
     * @return current channel
     */
    public abstract IChannel getCurrentChannel();
    
}
