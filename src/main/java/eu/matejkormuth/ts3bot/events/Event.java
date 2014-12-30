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
package eu.matejkormuth.ts3bot.events;

import javax.annotation.Nonnull;

import eu.matejkormuth.ts3bot.api.IVirtualServer;

/**
 * Specifies Event that happend on some VirtualServer.
 */
public abstract class Event {
    private final IVirtualServer virtualServer;
    
    public Event(@Nonnull final IVirtualServer server) {
        this.virtualServer = server;
    }
    
    /**
     * Returns {@link IVirtualServer} on which this event happened.
     * 
     * @return virtual server instance
     */
    public IVirtualServer getVirtualServer() {
        return this.virtualServer;
    }
}
