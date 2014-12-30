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

import eu.matejkormuth.ts3bot.teamspeak.ClientType;
import eu.matejkormuth.ts3bot.teamspeak.annotations.CachedValue;

// TODO: Refactor names
/**
 * Represents client on TeamSpeak server.
 */
public interface IClient {
    /**
     * Returns internal id of this client on TeamSpeak server.
     * 
     * @return id of this client
     */
    public abstract int getId();
    
    /**
     * Returns unique identifier of this client.
     * 
     * @return UId of this client
     */
    public abstract String getUid();
    
    /**
     * Returns nickname of this client
     * 
     * @return this client's nickname
     */
    @CachedValue
    public abstract String getNickname();
    
    /**
     * Checks whether this client is away or not.
     * 
     * @return true if this client is away
     */
    @CachedValue
    public abstract boolean isAway();
    
    /**
     * Checks if this client can talk or not.
     * 
     * @return true if client can talk
     */
    @CachedValue
    public abstract boolean canTalk();
    
    /**
     * Check if this client has away message or not.
     * 
     * @return true if client has away message, false otherwise
     */
    @CachedValue
    public abstract boolean hasAwayMessage();
    
    /**
     * Returns away message if client has one, null if client has not message.
     * 
     * @return message or null
     */
    @CachedValue
    public abstract String getAwayMessage();
    
    /**
     * Returns channel id of client's current channel.
     * 
     * @return id of client's current channel
     */
    @CachedValue
    public abstract int getChannelId();
    
    /**
     * Returns client's current channel.
     * 
     * @return instance of {@link IChannel}
     */
    @CachedValue
    public abstract IChannel getChannel();
    
    /**
     * Returns client's country.
     * 
     * @return client's country
     */
    public abstract String getCounty();
    
    /**
     * Returns id of client's current channel group.
     * 
     * @return id of cleint's channel group
     */
    @CachedValue
    public abstract int getChannelGroupId();
    
    /**
     * Returns client's current channel group.
     * 
     * @return client {@link IChannelGroup}
     */
    @CachedValue
    public abstract IChannelGroup getChannelGroup();
    
    /**
     * Returns client's idle time.
     * 
     * @return client's idle time
     */
    @CachedValue
    public abstract long getIdleTime();
    
    /**
     * Returns this client's inherited channel group id.
     * 
     * @return id of client's inherited channel group
     */
    @CachedValue
    public abstract int getInheritedChannelGroupId();
    
    /**
     * Returns this client's inherited channel group.
     * 
     * @return client's inherited {@link IChannelGroup}
     */
    @CachedValue
    public abstract IChannelGroup getInrehitedChannelGroup();
    
    /**
     * Returns client's platform.
     * 
     * @return platform of client
     */
    public abstract String getPlatform();
    
    /**
     * Returns array of ids of {@link IServerGroup}s that is this client currently in.
     * 
     * @return array of server group ids
     */
    @CachedValue
    public abstract int[] getServerGroupsIds();
    
    /**
     * Returns collection of {@link IServerGroup}s of this client.
     * 
     * @return collection of {@link IServerGroup}
     */
    @CachedValue
    public abstract Collection<IServerGroup> getServerGroups();
    
    /**
     * Checks whether this client is in specified {@link IServerGroup}.
     * 
     * @param group
     *            group to check for
     * @return true if client is in specified group, false otherwise
     */
    @CachedValue
    public abstract boolean isInServerGroup(IServerGroup group);
    
    /**
     * Returns client's talk power.
     * 
     * @return client's talk power
     */
    @CachedValue
    public abstract int getTalkPower();
    
    /**
     * Returns type of this client.
     * 
     * @return type of this client
     */
    public abstract ClientType getType();
    
    /**
     * Returns version of TeamSpeak of this client.
     * 
     * @return this client's TeamSpeak version
     */
    public abstract String getClientVersion();
    
    /**
     * Check whether this client is a channel commander.
     * 
     * @return true if this client is channel commander, false otherwise
     */
    @CachedValue
    public abstract boolean isChannelCommander();
    
    /**
     * Checks if client has input hardware.
     * 
     * @return true if client has input hardware, false otherwise
     */
    @CachedValue
    public abstract boolean hasInputHardware();
    
    /**
     * Checks if client has input hardware muted.
     * 
     * @return true if client has input hardware muted, false otherwise
     */
    @CachedValue
    public abstract boolean isInputMuted();
    
    /**
     * Checks if client has output hardware.
     * 
     * @return true if client has output hardware, false otherwise
     */
    @CachedValue
    public abstract boolean hasOutputHardware();
    
    /**
     * Checks if client has output hardware muted.
     * 
     * @return true if client has output hardware muted, false otherwise
     */
    @CachedValue
    public abstract boolean isOutputMuted();
    
    /**
     * Check whether this client is a priority speaker.
     * 
     * @return true if this client is priority speaker, false otherwise
     */
    @CachedValue
    public abstract boolean isPrioritySpeaker();
    
    /**
     * Check if client is currently recording.
     * 
     * @return true if client is recording, false otherwise
     */
    @CachedValue
    public abstract boolean isRecording();
    
    /**
     * Check if client is currently talking.
     * 
     * @return true if client is talking, false otherwise
     */
    @CachedValue
    public abstract boolean isTalking();
    
    /**
     * Returns description of client.
     * 
     * @return description of client
     */
    @CachedValue
    public abstract String getDescription();
    
    /**
     * Returns clients ip.
     * 
     * @return ip of client
     */
    @CachedValue
    public abstract String getIp();
    
    /**
     * Checks if client is requesting to talk.
     * 
     * @return true if client wants to talk, false otherwise
     */
    @CachedValue
    public abstract boolean isRequestingToTalk();
    
    /**
     * Returns database id of this client.
     * 
     * @return database id of this client
     */
    public abstract int getDatabaseId();
    
    /**
     * Returns {@link IVirtualServer} on which is this user group present.
     * 
     * @return instance of {@link IVirtualServer}
     */
    public abstract IVirtualServer getServer();
    
    /**
     * Deletes all complaint on this client.
     */
    public abstract void deleteAllComplaints();
    
    /**
     * Returns all complaints about thsi client.
     * 
     * @return collection of complaints about this client
     */
    public abstract Collection<IComplaint> getComplaints();
    
    /**
     * Removes complaint on this client from specified client.
     * 
     * @param from
     *            author of complaint to remove
     */
    public abstract void removeComplaintFrom(@Nonnull IClient from);
    
    /**
     * Creates complaint about this client with specified text.
     * 
     * @param text
     *            text of complaint
     */
    public abstract void addComplaint(@Nonnull String text);
    
    /**
     * Bans this client from server permanently.
     * 
     * @param reason
     *            reason of ban
     * @return {@link IBan} instance of this ban
     */
    public abstract IBan ban(@Nonnull String reason);
    
    /**
     * Bans this client from server temporarly.
     * 
     * @param reason
     *            reason of ban
     * @param timeInSeconds
     *            length of ban in seconds
     * @return {@link IBan} instance of this ban
     */
    public abstract IBan ban(@Nonnull String reason, long timeInSeconds);
    
    /**
     * Bans this client from server temporarly.
     * 
     * @param timeInSeconds
     *            length of ban in seconds
     * @return {@link IBan} instance of this ban
     */
    public abstract IBan ban(long timeInSeconds);
    
    /**
     * Kick this client from server without specified reason.
     */
    public abstract void kickFromServer();
    
    /**
     * Kick this client from server with specified reason.
     * 
     * @param reason
     *            reason of kick
     */
    public abstract void kickFromServer(@Nonnull String reason);
    
    /**
     * Kick this client from his current channel without specified reason.
     */
    public abstract void kickFromChannel();
    
    /**
     * Kick this client from his current channel with specified reason.
     * 
     * @param reason
     *            reason of kick
     */
    public abstract void kickFromChannel(@Nonnull String reason);
    
    /**
     * Pokes this client with specified poke message.
     * 
     * @param message
     *            poke message
     */
    public abstract void poke(String message);
    
    /**
     * Moves this client to specified channel.
     * 
     * @param channel
     *            channel to move client to
     */
    public abstract void moveTo(@Nonnull IChannel channel);
    
    /**
     * Moves this client to specified channel using specified channelPassword.
     * 
     * @param channel
     *            channel to move client to
     * @param channelPassword
     *            channel password
     */
    public abstract void moveTo(@Nonnull IChannel channel,
            @Nonnull String channelPassword);
    
    /**
     * Sends private text message to specified client.
     * 
     * @param message
     *            text of message
     */
    public abstract void sendMessage(@Nonnull String message);
    
    /**
     * Sends {@link IOfflineMessage} to this client.
     * 
     * @param message
     *            message to send
     */
    public abstract void sendOfflineMessage(@Nonnull IOfflineMessage message);
    
    /**
     * Sends {@link IOfflineMessage} to this client.
     * 
     * @param subject
     *            subject of message
     * @param content
     *            content of message
     */
    public abstract void sendOfflineMessage(@Nonnull String subject,
            @Nonnull String content);
    
    /**
     * Updates properties of this client from TeamSpeak server.
     */
    public abstract void update();
    
}
