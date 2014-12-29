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

import java.util.Date;

import javax.annotation.Nullable;

/**
 * Represents Ban on Teamspeak server.
 * 
 * @see IVirtualServer#ban(IClient, long, String)
 */
// TODO: Refactor names
public interface IBan {
    /**
     * Returns {@link IVirtualServer} on which is this ban present.
     * 
     * @return instance of {@link IVirtualServer}
     */
    public abstract IVirtualServer getServer();
    
    /**
     * Returns ID of this ban.
     * 
     * @return id of this ban
     */
    public abstract int getId();
    
    /**
     * Returns banned IP address.
     * 
     * @return banned client's IP address
     */
    public abstract String getBannedIp();
    
    /**
     * Returns banned unique id.
     * 
     * @return banned client's UId
     */
    public abstract String getBannedUId();
    
    /**
     * Returns name of banned client.
     * 
     * @return banned client's name
     */
    public abstract String getBannedName();
    
    /**
     * Returns last nickname which banned client used.
     * 
     * @return client's last nickname
     */
    public abstract String getLastNickname();
    
    /**
     * Returns Date of when was this ban created.
     * 
     * @return date of creation
     */
    public abstract Date getCreatedAt();
    
    /**
     * Returns duration of this ban in seconds.
     * 
     * @return duration in seconds
     */
    public abstract long getDuration();
    
    /**
     * Returns name of client that created this ban.
     * 
     * @return name of invoker
     */
    public abstract String getInvokerName();
    
    public abstract String getInvokerUId();
    
    public abstract int getInvokerDBId();
    
    @Nullable
    public abstract String getReason();
    
    public abstract int getEnforcementsCount();
    
    public abstract void remove();
    
    public abstract String toString();
    
}
