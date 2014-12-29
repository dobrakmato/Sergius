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
package eu.matejkormuth.ts3bot.events.client;

import javax.annotation.Nonnull;

import eu.matejkormuth.ts3bot.api.IChannel;
import eu.matejkormuth.ts3bot.api.IClient;
import eu.matejkormuth.ts3bot.api.IVirtualServer;
import eu.matejkormuth.ts3bot.events.ClientEvent;

/**
 *
 */
public class ClientMovedEvent extends ClientEvent {
    private final IChannel channel;
    
    public ClientMovedEvent(@Nonnull final IVirtualServer server,
            @Nonnull final IClient client, @Nonnull final IChannel targetChannel) {
        super(server, client);
        this.channel = targetChannel;
    }
    
    public IChannel getChannel() {
        return this.channel;
    }
    
    @Override
    public String toString() {
        return "ClientMovedEvent [channel=" + this.channel + ", getClient()="
                + this.getClient() + ", getVirtualServer()=" + this.getVirtualServer()
                + "]";
    }
}
