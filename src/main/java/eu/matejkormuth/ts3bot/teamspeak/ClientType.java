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
 *
 */
public enum ClientType {
    TS_CLIENT(0),
    SERVER_QUERY(1);
    
    private int id;
    
    private ClientType(final int id) {
        this.id = id;
    }
    
    public int getId() {
        return this.id;
    }
    
    public static ClientType byId(final int id) {
        return TS_CLIENT;
        //TODO: Add client types.
    }
}
