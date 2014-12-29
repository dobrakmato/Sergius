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

// TODO: Refactor names
/**
 * Represents server group on TeamSpeak server.
 */
public interface IServerGroup extends IUserGroup {
    
    /**
     * Adds specified {@link IClient} to this server group.
     * 
     * @param client
     *            client which should be added to this group
     */
    public abstract void addClient(@Nonnull IClient client);
    
    /**
     * Removes specified {@link IClient} from this server group.
     * 
     * @param client
     *            client which should be added to this group
     */
    public abstract void removeClient(@Nonnull IClient client);
    
    /**
     * Chanes name of this server group to specified name.
     * 
     * @param name
     *            new name
     */
    public abstract void setName(@Nonnull String name);
    
    /**
     * Removes this channel group from server.
     */
    public abstract void remove();
    
    /**
     * Returns view of {@link IClient}s in this {@link IServerGroup}. Does not force update clients (collection may be
     * outdated).
     * 
     * @return collection of clients in this channel group
     */
    public abstract Collection<IClient> getClients();
    
    /**
     * Returns view of {@link IClient}s in this {@link IServerGroup}. Force update clients if parameter
     * <code>forceUpdate</code> is true.
     * 
     * @param forceUpdate
     *            whether to update clients from teamspeak server
     * @return collection of clients in this channel group
     */
    public abstract Collection<IClient> getClients(boolean update);
    
}
