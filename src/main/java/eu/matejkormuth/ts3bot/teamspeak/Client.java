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
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.common.base.Strings;

import eu.matejkormuth.ts3bot.api.IChannel;
import eu.matejkormuth.ts3bot.api.IClient;
import eu.matejkormuth.ts3bot.api.IComplaint;
import eu.matejkormuth.ts3bot.api.IOfflineMessage;
import eu.matejkormuth.ts3bot.api.IServerGroup;
import eu.matejkormuth.ts3bot.teamspeak.annotations.CachedValue;

/**
 * Represents client on Teamspeak server.
 */
public class Client implements Updatable, eu.matejkormuth.ts3bot.api.IClient {
    private final int           id;
    private final String        uid;
    private final String        nickname;
    private boolean             away;
    private boolean             canTalk;
    private String              awayMessage;
    private int                 channelId;
    private final String        county;
    private int                 channelGroupId;
    private final int           databaseId;
    private long                idleTime;
    private int                 inheritedChannelGroupId;
    private final String        platform;
    private int[]               serverGroupsIds;
    private int                 talkPower;
    private final ClientType    type;
    private final String        clientVersion;
    private boolean             channelCommander;
    private boolean             inputHardware;
    private boolean             inputMuted;
    private boolean             outputHardware;
    private boolean             outputMuted;
    private boolean             prioritySpeaker;
    private boolean             recording;
    private boolean             talking;
    private boolean             requestingToTalk;
    private String              description;
    private String              ip;
    
    private final VirtualServer server;
    
    protected Client(final VirtualServer server, final String nickname, final int id,
            final String uid, final int databaseId, final String country,
            final String platform, final String clientVersion, final ClientType type) {
        this.id = id;
        this.nickname = nickname;
        this.uid = uid;
        this.databaseId = databaseId;
        this.county = country;
        this.platform = platform;
        this.clientVersion = clientVersion;
        this.type = type;
        this.server = server;
    }
    
    protected void setAway(final boolean away) {
        this.away = away;
    }
    
    protected void setCanTalk(final boolean canTalk) {
        this.canTalk = canTalk;
    }
    
    protected void setAwayMessage(final @Nullable String awayMessage) {
        this.awayMessage = awayMessage;
    }
    
    protected void setChannelId(final int channelId) {
        this.channelId = channelId;
    }
    
    protected void setChannelGroupId(final int channelGroupId) {
        this.channelGroupId = channelGroupId;
    }
    
    protected void setIdleTime(final long idleTime) {
        this.idleTime = idleTime;
    }
    
    protected void setInheritedChannelGroupId(final int inheritedChannelGroupId) {
        this.inheritedChannelGroupId = inheritedChannelGroupId;
    }
    
    protected void setServerGroups(final int[] serverGroups) {
        this.serverGroupsIds = serverGroups;
    }
    
    protected void setTalkPower(final int talkPower) {
        this.talkPower = talkPower;
    }
    
    protected void setChannelCommander(final boolean channelCommander) {
        this.channelCommander = channelCommander;
    }
    
    protected void setInputHardware(final boolean inputHardware) {
        this.inputHardware = inputHardware;
    }
    
    protected void setInputMuted(final boolean inputMuted) {
        this.inputMuted = inputMuted;
    }
    
    protected void setOutputHardware(final boolean outputHardware) {
        this.outputHardware = outputHardware;
    }
    
    protected void setOutputMuted(final boolean outputMuted) {
        this.outputMuted = outputMuted;
    }
    
    protected void setPrioritySpeaker(final boolean prioritySpeaker) {
        this.prioritySpeaker = prioritySpeaker;
    }
    
    protected void setRecording(final boolean recording) {
        this.recording = recording;
    }
    
    protected void setTalking(final boolean talking) {
        this.talking = talking;
    }
    
    public int getId() {
        return this.id;
    }
    
    public String getUid() {
        return this.uid;
    }
    
    @CachedValue
    public String getNickname() {
        return this.nickname;
    }
    
    @CachedValue
    public boolean isAway() {
        return this.away;
    }
    
    @CachedValue
    public boolean canTalk() {
        return this.canTalk;
    }
    
    @CachedValue
    public boolean hasAwayMessage() {
        return Strings.isNullOrEmpty(this.awayMessage);
    }
    
    @CachedValue
    public String getAwayMessage() {
        return this.awayMessage;
    }
    
    @CachedValue
    public int getChannelId() {
        return this.channelId;
    }
    
    @CachedValue
    public Channel getChannel() {
        return this.server.getChannelById(this.channelId);
    }
    
    public String getCounty() {
        return this.county;
    }
    
    @CachedValue
    public int getChannelGroupId() {
        return this.channelGroupId;
    }
    
    @CachedValue
    public ChannelGroup getChannelGroup() {
        return this.server.getChannelGroupById(this.channelGroupId);
    }
    
    @CachedValue
    public long getIdleTime() {
        return this.idleTime;
    }
    
    @CachedValue
    public int getInheritedChannelGroupId() {
        return this.inheritedChannelGroupId;
    }
    
    @CachedValue
    public ChannelGroup getInrehitedChannelGroup() {
        return this.server.getChannelGroupById(this.inheritedChannelGroupId);
    }
    
    public String getPlatform() {
        return this.platform;
    }
    
    @CachedValue
    public int[] getServerGroupsIds() {
        return this.serverGroupsIds;
    }
    
    @CachedValue
    public Collection<IServerGroup> getServerGroups() {
        List<IServerGroup> groups = new ArrayList<IServerGroup>(
                this.serverGroupsIds.length);
        for (int id : this.serverGroupsIds) {
            groups.add(this.server.getServerGroupById(id));
        }
        return groups;
    }
    
    @CachedValue
    public boolean isInServerGroup(final IServerGroup group) {
        return Arrays.asList(this.serverGroupsIds).contains(group.getId());
    }
    
    @CachedValue
    public int getTalkPower() {
        return this.talkPower;
    }
    
    public ClientType getType() {
        return this.type;
    }
    
    public String getClientVersion() {
        return this.clientVersion;
    }
    
    @CachedValue
    public boolean isChannelCommander() {
        return this.channelCommander;
    }
    
    @CachedValue
    public boolean hasInputHardware() {
        return this.inputHardware;
    }
    
    @CachedValue
    public boolean isInputMuted() {
        return this.inputMuted;
    }
    
    @CachedValue
    public boolean hasOutputHardware() {
        return this.outputHardware;
    }
    
    @CachedValue
    public boolean isOutputMuted() {
        return this.outputMuted;
    }
    
    @CachedValue
    public boolean isPrioritySpeaker() {
        return this.prioritySpeaker;
    }
    
    @CachedValue
    public boolean isRecording() {
        return this.recording;
    }
    
    @CachedValue
    public boolean isTalking() {
        return this.talking;
    }
    
    @CachedValue
    public String getDescription() {
        if (this.description == null) {
            this.update();
        }
        return this.description;
    }
    
    @CachedValue
    public String getIp() {
        if (this.ip == null) {
            this.update();
        }
        return this.ip;
    }
    
    @CachedValue
    public boolean isRequestingToTalk() {
        return this.requestingToTalk;
    }
    
    public int getDatabaseId() {
        return this.databaseId;
    }
    
    public VirtualServer getServer() {
        return this.server;
    }
    
    public void deleteAllComplaints() {
        this.server.getConnection().getApi().deleteAllComplaints(this.getDatabaseId());
    }
    
    public Collection<IComplaint> getComplaints() {
        List<com.github.theholywaffle.teamspeak3.api.wrapper.Complaint> cs = this.server.getConnection()
                .getApi()
                .getComplaints(this.getDatabaseId());
        List<IComplaint> complaints = new ArrayList<IComplaint>(cs.size());
        for (com.github.theholywaffle.teamspeak3.api.wrapper.Complaint c : cs) {
            Client target = Client.this.server.getClientByDatabaseId(c.getTargetClientDatabaseId());
            Client source = Client.this.server.getClientByDatabaseId(c.getSourceClientDatabaseId());
            complaints.add(new Complaint(Client.this.server, source, target,
                    c.getMessage(), c.getTimestamp()));
        }
        return complaints;
    }
    
    public void removeComplaintFrom(@Nonnull final IClient from) {
        this.server.getConnection()
                .getApi()
                .deleteComplaint(this.getDatabaseId(), from.getDatabaseId());
    }
    
    public void addComplaint(@Nonnull final String text) {
        this.server.getConnection().getApi().addComplain(this.getDatabaseId(), text);
    }
    
    public Ban ban(@Nonnull final String reason) {
        int banId = this.server.getConnection().getApi().banClient(this.getId(), reason);
        Ban ban = new Ban(this.server, banId);
        ban.setBannedIp(this.getIp());
        ban.setBannedName(this.getNickname());
        ban.setBannedUId(this.getUid());
        ban.setCreatedAt(new Date());
        ban.setDuration(-1);
        ban.setEnforcements(0);
        ban.setInvokerDBId(this.server.getSelfDBId());
        ban.setInvokerName(this.server.getSelfNickname());
        ban.setInvokerUId(this.server.getSelfUId());
        ban.setLastNickname(this.getNickname());
        ban.setReason(reason);
        return ban;
    }
    
    public Ban ban(@Nonnull final String reason, final long timeInSeconds) {
        int banId = this.server.getConnection()
                .getApi()
                .banClient(this.id, timeInSeconds, reason);
        Ban ban = new Ban(this.server, banId);
        ban.setBannedIp(this.getIp());
        ban.setBannedName(this.getNickname());
        ban.setBannedUId(this.getUid());
        ban.setCreatedAt(new Date());
        ban.setDuration(timeInSeconds);
        ban.setEnforcements(0);
        ban.setInvokerDBId(this.server.getSelfDBId());
        ban.setInvokerName(this.server.getSelfNickname());
        ban.setInvokerUId(this.server.getSelfUId());
        ban.setLastNickname(this.getNickname());
        ban.setReason(reason);
        return ban;
    }
    
    public Ban ban(final long timeInSeconds) {
        int banId = this.server.getConnection()
                .getApi()
                .banClient(this.id, timeInSeconds);
        Ban ban = new Ban(this.server, banId);
        ban.setBannedIp(this.getIp());
        ban.setBannedName(this.getNickname());
        ban.setBannedUId(this.getUid());
        ban.setCreatedAt(new Date());
        ban.setDuration(timeInSeconds);
        ban.setEnforcements(0);
        ban.setInvokerDBId(this.server.getSelfDBId());
        ban.setInvokerName(this.server.getSelfNickname());
        ban.setInvokerUId(this.server.getSelfUId());
        ban.setLastNickname(this.getNickname());
        ban.setReason(null);
        return ban;
    }
    
    public void kickFromServer() {
        this.server.getConnection().getApi().kickClientFromServer(this.getId());
    }
    
    public void kickFromServer(@Nonnull final String reason) {
        this.server.getConnection().getApi().kickClientFromServer(reason, this.getId());
    }
    
    public void kickFromChannel() {
        this.server.getConnection().getApi().kickClientFromChannel(this.getId());
    }
    
    public void kickFromChannel(@Nonnull final String reason) {
        this.server.getConnection().getApi().kickClientFromChannel(reason, this.getId());
    }
    
    public void poke(String message) {
        if (Strings.isNullOrEmpty(message)) {
            message = "";
        }
        this.server.getConnection().getApi().pokeClient(this.getId(), message);
    }
    
    public void moveTo(@Nonnull final IChannel channel) {
        this.server.getConnection().getApi().moveClient(this.getId(), channel.getId());
    }
    
    public void moveTo(@Nonnull final IChannel channel,
            @Nonnull final String channelPassword) {
        this.server.getConnection()
                .getApi()
                .moveClient(this.getId(), channel.getId(), channelPassword);
    }
    
    public void sendMessage(@Nonnull final String message) {
        this.server.getConnection().getApi().sendPrivateMessage(this.id, message);
    }
    
    public void sendOfflineMessage(@Nonnull final IOfflineMessage message) {
        this.server.getConnection()
                .getApi()
                .sendOfflineMessage(this.uid, message.getSubject(), message.getContent());
    }
    
    public void sendOfflineMessage(@Nonnull final String subject,
            @Nonnull final String message) {
        this.server.getConnection()
                .getApi()
                .sendOfflineMessage(this.uid, subject, message);
    }
    
    protected void updateBy(
            final com.github.theholywaffle.teamspeak3.api.wrapper.Client c) {
        this.away = c.isAway();
        this.awayMessage = c.getAwayMessage();
        this.canTalk = c.canTalk();
        this.channelCommander = c.isChannelCommander();
        this.channelGroupId = c.getChannelGroupId();
        this.channelId = c.getChannelId();
        this.idleTime = c.getIdleTime();
        this.inheritedChannelGroupId = c.getInheritedChannelGroupId();
        this.inputHardware = c.isInputHardware();
        this.inputMuted = c.isInputMuted();
        this.outputHardware = c.isOutputHardware();
        this.outputMuted = c.isOutputMuted();
        this.prioritySpeaker = c.isPrioritySpeaker();
        this.recording = c.isRecording();
        this.serverGroupsIds = c.getServerGroups();
        this.talking = c.isTalking();
        this.talkPower = c.getTalkPower();
    }
    
    protected void updateBy(
            final com.github.theholywaffle.teamspeak3.api.wrapper.ClientInfo c) {
        this.away = c.isAway();
        this.awayMessage = c.getAwayMessage();
        this.canTalk = c.canTalk();
        this.channelCommander = c.isChannelCommander();
        this.channelGroupId = c.getChannelGroupId();
        this.channelId = c.getChannelId();
        this.idleTime = c.getIdleTime();
        this.inputHardware = c.isInputHardware();
        this.inputMuted = c.isInputMuted();
        this.outputHardware = c.isOutputHardware();
        this.outputMuted = c.isOutputMuted();
        this.prioritySpeaker = c.isPrioritySpeaker();
        this.recording = c.isRecording();
        this.serverGroupsIds = c.getServerGroups();
        this.talkPower = c.getTalkPower();
        this.requestingToTalk = c.isRequestingToTalk();
        this.description = c.getDescription();
        this.ip = c.getIp();
    }
    
    public void update() {
        this.server.getConnection().getApi().getClientInfo(this.getId());
    }
    
    @Override
    public String toString() {
        return "Client [id=" + this.id + ", uid=" + this.uid + ", nickname="
                + this.nickname + ", away=" + this.away + ", canTalk=" + this.canTalk
                + ", awayMessage=" + this.awayMessage + ", channelId=" + this.channelId
                + ", county=" + this.county + ", channelGroupId=" + this.channelGroupId
                + ", databaseId=" + this.databaseId + ", idleTime=" + this.idleTime
                + ", inheritedChannelGroupId=" + this.inheritedChannelGroupId
                + ", platform=" + this.platform + ", serverGroupsIds="
                + Arrays.toString(this.serverGroupsIds) + ", talkPower="
                + this.talkPower + ", type=" + this.type + ", clientVersion="
                + this.clientVersion + ", channelCommander=" + this.channelCommander
                + ", inputHardware=" + this.inputHardware + ", inputMuted="
                + this.inputMuted + ", outputHardware=" + this.outputHardware
                + ", outputMuted=" + this.outputMuted + ", prioritySpeaker="
                + this.prioritySpeaker + ", recording=" + this.recording + ", talking="
                + this.talking + ", server=" + this.server + "]";
    }
}
