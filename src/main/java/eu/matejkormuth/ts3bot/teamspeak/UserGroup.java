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

/**
 * Abstract class to allow easier implementation of {@link ServerGroup} and {@link ChannelGroup} on Teamspeak server.
 */
public abstract class UserGroup implements eu.matejkormuth.ts3bot.api.IUserGroup {
    private final int             id;
    protected String              name;
    private long                  iconId;
    private int                   nameMode;
    private int                   modifyPower;
    private int                   memberAddPower;
    private int                   memberRemovePower;
    private int                   sortId;
    
    protected final VirtualServer server;
    
    public UserGroup(final VirtualServer server, final int id, final String name) {
        this.id = id;
        this.name = name;
        this.server = server;
    }
    
    protected void setIconId(final long iconId) {
        this.iconId = iconId;
    }
    
    protected void setNameMode(final int nameMode) {
        this.nameMode = nameMode;
    }
    
    protected void setModifyPower(final int modifyPower) {
        this.modifyPower = modifyPower;
    }
    
    protected void setMemberAddPower(final int memberAddPower) {
        this.memberAddPower = memberAddPower;
    }
    
    protected void setMemberRemovePower(final int memberRemovePower) {
        this.memberRemovePower = memberRemovePower;
    }
    
    protected void setSortId(final int sortId) {
        this.sortId = sortId;
    }
    
    public int getId() {
        return this.id;
    }
    
    public String getName() {
        return this.name;
    }
    
    public int getSortId() {
        return this.sortId;
    }
    
    public long getIconId() {
        return this.iconId;
    }
    
    public int getNameMode() {
        return this.nameMode;
    }
    
    public int getModifyPower() {
        return this.modifyPower;
    }
    
    public int getMemberAddPower() {
        return this.memberAddPower;
    }
    
    public int getMemberRemovePower() {
        return this.memberRemovePower;
    }
    
    public VirtualServer getServer() {
        return this.server;
    }
}
