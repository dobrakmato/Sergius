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

// TODO: Refactor names
public interface IClient {
    
    public abstract int getId();
    
    public abstract String getUid();
    
    public abstract String getNickname();
    
    public abstract boolean isAway();
    
    public abstract boolean canTalk();
    
    public abstract boolean hasAwayMessage();
    
    public abstract String getAwayMessage();
    
    public abstract int getChannelId();
    
    public abstract IChannel getChannel();
    
    public abstract String getCounty();
    
    public abstract int getChannelGroupId();
    
    public abstract IChannelGroup getChannelGroup();
    
    public abstract long getIdleTime();
    
    public abstract int getInheritedChannelGroupId();
    
    public abstract IChannelGroup getInrehitedChannelGroup();
    
    public abstract String getPlatform();
    
    public abstract int[] getServerGroupsIds();
    
    public abstract Collection<IServerGroup> getServerGroups();
    
    public abstract boolean isInServerGroup(IServerGroup group);
    
    public abstract int getTalkPower();
    
    public abstract ClientType getType();
    
    public abstract String getClientVersion();
    
    public abstract boolean isChannelCommander();
    
    public abstract boolean hasInputHardware();
    
    public abstract boolean isInputMuted();
    
    public abstract boolean hasOutputHardware();
    
    public abstract boolean isOutputMuted();
    
    public abstract boolean isPrioritySpeaker();
    
    public abstract boolean isRecording();
    
    public abstract boolean isTalking();
    
    public abstract String getDescription();
    
    public abstract String getIp();
    
    public abstract boolean isRequestingToTalk();
    
    public abstract int getDatabaseId();
    
    public abstract IVirtualServer getServer();
    
    public abstract void deleteAllComplaints();
    
    public abstract Collection<IComplaint> getComplaints();
    
    public abstract void deleteComplaintFrom(@Nonnull IClient from);
    
    public abstract void addComplain(@Nonnull String text);
    
    public abstract IBan ban(@Nonnull String reason);
    
    public abstract IBan ban(@Nonnull String reason, long timeInSeconds);
    
    public abstract IBan ban(long timeInSeconds);
    
    public abstract void kickFromServer();
    
    public abstract void kickFromServer(@Nonnull String reason);
    
    public abstract void kickFromChannel();
    
    public abstract void kickFromChannel(@Nonnull String reason);
    
    public abstract void poke(String message);
    
    public abstract void moveTo(@Nonnull IChannel channel);
    
    public abstract void moveTo(@Nonnull IChannel channel,
            @Nonnull String channelPassword);
    
    public abstract void sendMessage(@Nonnull String message);
    
    public abstract void sendOfflineMessage(@Nonnull IOfflineMessage message);
    
    public abstract void sendOfflineMessage(@Nonnull String subject,
            @Nonnull String message);
    
    public abstract void update();
    
}
