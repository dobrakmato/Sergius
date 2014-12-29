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

import eu.matejkormuth.ts3bot.api.IClient;
import eu.matejkormuth.ts3bot.api.IVirtualServer;

/**
 *
 */
public abstract class ClientEvent extends Event {
    private final IClient client;
    
    public ClientEvent(@Nonnull final IVirtualServer server,
            @Nonnull final IClient client) {
        super(server);
        this.client = client;
    }
    
    public IClient getClient() {
        return this.client;
    }
    
    @Override
    public String toString() {
        return "ClientJoinedServerEvent [getClient()=" + this.getClient()
                + ", getVirtualServer()=" + this.getVirtualServer() + "]";
    }
}
