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

import java.util.Date;

import javax.annotation.Nullable;

/**
 * Class that represents Ban on Teamspeak server.
 */
public class Ban implements eu.matejkormuth.ts3bot.api.IBan {
    private final int           id;
    private String              bannedIp;
    private String              bannedUId;
    private String              bannedName;
    private String              lastNickname;
    private Date                createdAt;
    private long                duration;
    private String              invokerName;
    private String              invokerUId;
    private int                 invokerDBId;
    private String              reason;
    private int                 enforcementsCount;
    
    private final VirtualServer server;
    
    protected Ban(final VirtualServer server, final int id) {
        this.server = server;
        this.id = id;
    }
    
    public VirtualServer getServer() {
        return this.server;
    }
    
    protected void setBannedIp(final String bannedIp) {
        this.bannedIp = bannedIp;
    }
    
    protected void setBannedUId(final String bannedUId) {
        this.bannedUId = bannedUId;
    }
    
    protected void setBannedName(final String bannedName) {
        this.bannedName = bannedName;
    }
    
    protected void setLastNickname(final String lastNickname) {
        this.lastNickname = lastNickname;
    }
    
    protected void setCreatedAt(final Date created) {
        this.createdAt = created;
    }
    
    protected void setDuration(final long duration) {
        this.duration = duration;
    }
    
    protected void setInvokerName(final String invokerName) {
        this.invokerName = invokerName;
    }
    
    protected void setInvokerUId(final String invokerUId) {
        this.invokerUId = invokerUId;
    }
    
    protected void setInvokerDBId(final int invokerDBId) {
        this.invokerDBId = invokerDBId;
    }
    
    protected void setReason(@Nullable final String reason) {
        this.reason = reason;
    }
    
    protected void setEnforcements(final int enforcements) {
        this.enforcementsCount = enforcements;
    }
    
    public int getId() {
        return this.id;
    }
    
    public String getBannedIp() {
        return this.bannedIp;
    }
    
    public String getBannedUId() {
        return this.bannedUId;
    }
    
    public String getBannedName() {
        return this.bannedName;
    }
    
    public String getLastNickname() {
        return this.lastNickname;
    }
    
    public Date getCreatedAt() {
        return this.createdAt;
    }
    
    public long getDuration() {
        return this.duration;
    }
    
    public String getInvokerName() {
        return this.invokerName;
    }
    
    public String getInvokerUId() {
        return this.invokerUId;
    }
    
    public int getInvokerDBId() {
        return this.invokerDBId;
    }
    
    @Nullable
    public String getReason() {
        return this.reason;
    }
    
    public int getEnforcementsCount() {
        return this.enforcementsCount;
    }
    
    public void remove() {
        this.server.getConnection().getApi().deleteBan(this.getId());
    }
    
    protected void updateBy(final com.github.theholywaffle.teamspeak3.api.wrapper.Ban b) {
        this.bannedIp = b.getBannedIp();
        this.bannedName = b.getBannedName();
        this.bannedUId = b.getBannedUId();
        this.createdAt = b.getCreatedDate();
        this.duration = b.getDuration();
        this.enforcementsCount = b.getEnforcements();
        this.invokerDBId = b.getInvokerClientDBId();
        this.invokerName = b.getInvokerName();
        this.invokerUId = b.getInvokerUId();
        this.lastNickname = b.getLastNickname();
        this.reason = b.getReason();
    }
    
    @Override
    public String toString() {
        return "Ban [id=" + this.id + ", bannedIp=" + this.bannedIp + ", bannedUId="
                + this.bannedUId + ", bannedName=" + this.bannedName + ", lastNickname="
                + this.lastNickname + ", created=" + this.createdAt + ", duration="
                + this.duration + ", invokerName=" + this.invokerName + ", invokerUId="
                + this.invokerUId + ", invokerDBId=" + this.invokerDBId + ", reason="
                + this.reason + ", enforcements=" + this.enforcementsCount + ", server="
                + this.server + "]";
    }
    
}
