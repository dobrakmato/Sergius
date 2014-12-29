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
public interface IServerGroup extends IUserGroup {
    
    public abstract void addClient(@Nonnull IClient client);
    
    public abstract void removeClient(@Nonnull IClient client);
    
    public abstract void setName(@Nonnull String name);
    
    public abstract void delete();
    
    public abstract Collection<IClient> getClients();
    
    public abstract Collection<IClient> getClients(boolean update);
    
}
