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

// TODO: Refactor names
/**
 * Represents complaint on TeamSpeak server.
 */
public interface IComplaint {
    /**
     * Returns {@link IVirtualServer} on which is this complaint present.
     * 
     * @return instance of {@link IVirtualServer}
     */
    public abstract IVirtualServer getServer();
    
    /**
     * Returns {@link IClient} which created this complaint.
     * 
     * @return instance of client thats complaining
     */
    public abstract IClient getSource();
    
    /**
     * Returns {@link IClient} which is this complaint targeted at.
     * 
     * @return target of this complaint
     */
    public abstract IClient getTarget();
    
    /**
     * Returns message of this complaint.
     * 
     * @return this complaint's message
     */
    public abstract String getMessage();
    
    /**
     * Returns date of creation of this complaint.
     * 
     * @return creation date
     */
    public abstract Date getCreatedAt();
    
    /**
     * Removes this complain.
     */
    public abstract void remove();
    
}
