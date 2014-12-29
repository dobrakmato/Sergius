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
public interface IUserGroup {
    
    public abstract int getId();
    
    public abstract String getName();
    
    public abstract int getSortId();
    
    public abstract long getIconId();
    
    public abstract int getNameMode();
    
    public abstract int getModifyPower();
    
    public abstract int getMemberAddPower();
    
    public abstract int getMemberRemovePower();
    
    public abstract IVirtualServer getServer();
    
}
