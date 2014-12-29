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

// TODO: Refactor names 
public interface IVirtualServer {
    
    public abstract int getId();
    
    public abstract int getPort();
    
    public abstract int getClientsOnline();
    
    public abstract int getQueryClientsOnline();
    
    public abstract int getMaxClients();
    
    public abstract long getUptime();
    
    public abstract String getName();
    
    public abstract void deleteAllBans();
    
    public abstract void join(@Nonnull IChannel channel);
    
    public abstract IBan ban(@Nonnull IClient client, @Nonnull String reason);
    
    public abstract void addBan(@Nullable String ip, @Nullable String uid,
            @Nullable String name, long timeInSeconds, @Nullable String reason);
    
    public abstract IBan ban(@Nonnull IClient client, long timeInSeconds);
    
    public abstract IBan ban(@Nonnull IClient client, long timeInSeconds,
            @Nonnull String reason);
    
    public abstract void kickFromServer(@Nonnull IClient client);
    
    public abstract void kickFromServer(@Nonnull IClient client, @Nonnull String reason);
    
    public abstract void kickFromChannel(@Nonnull IClient client);
    
    public abstract void kickFromChannel(@Nonnull IClient client, @Nonnull String reason);
    
    public abstract void broadcast(@Nonnull String message);
    
    public abstract void sendMessage(@Nonnull String message);
    
    public abstract IChannel createChannel(@Nonnull String name);
    
    public abstract IServerGroup getServerGroupById(int id);
    
    public abstract IChannelGroup getChannelGroupById(int id);
    
    public abstract int getSelfDBId();
    
    public abstract int getSelfId();
    
    public abstract String getSelfUId();
    
    public abstract String getSelfNickname();
    
    public abstract IClient getClientByName(@Nonnull String nickname);
    
    public abstract IClient getClientByDatabaseId(int databaseId);
    
    public abstract IClient getClientById(int id);
    
    public abstract IClient getClientByUid(@Nonnull String uid);
    
    public abstract Collection<IClient> getClients();
    
    public abstract Collection<IClient> getClients(boolean update);
    
    public abstract IChannel getChannelById(int cid);
    
    public abstract IChannel getChannelByName(@Nonnull String name);
    
    public abstract Collection<IChannel> getChannels();
    
    public abstract Collection<IChannel> getChannels(boolean forceUpdate);
    
    public abstract Collection<IChannelGroup> getChannelGroups();
    
    public abstract Collection<IServerGroup> getServerGroups();
    
    public abstract IBan getBanById(int bid);
    
    public abstract Collection<IBan> getBans();
    
    public abstract Collection<IBan> getBans(boolean forceUpdate);
    
    public abstract void update();
    
    public abstract IChannel getCurrentChannel();
    
}
