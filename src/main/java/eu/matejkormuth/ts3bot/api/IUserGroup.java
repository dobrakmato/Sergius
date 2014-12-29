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

// TODO: Refactor names
/**
 * Represents user group on TeamSpeak server. This class is extended by {@link IServerGroup} and {@link IChannelGroup}.
 */
public interface IUserGroup {
    /**
     * Returns internal id of this user group.
     * 
     * @return id of this group
     */
    public abstract int getId();
    
    /**
     * Returns name of this user group.
     * 
     * @return name of this group
     */
    public abstract String getName();
    
    /**
     * Returns sort id od this user group.
     * 
     * @return sort id
     */
    public abstract int getSortId();
    
    /**
     * Returns id of this user group's icon.
     * 
     * @return icon id
     */
    public abstract long getIconId();
    
    /**
     * Returns name mode of this user group.
     * 
     * @return name mode
     */
    public abstract int getNameMode();
    
    /**
     * Returns power required to modify this user group.
     * 
     * @return modify power
     */
    public abstract int getModifyPower();
    
    /**
     * Returns power required to add user to this user group.
     * 
     * @return add power
     */
    public abstract int getMemberAddPower();
    
    /**
     * Returns power required to remove user from this user group.
     * 
     * @return remove power
     */
    public abstract int getMemberRemovePower();
    
    /**
     * Returns {@link IVirtualServer} on which is this user group present.
     * 
     * @return instance of {@link IVirtualServer}
     */
    public abstract IVirtualServer getServer();
    
}
