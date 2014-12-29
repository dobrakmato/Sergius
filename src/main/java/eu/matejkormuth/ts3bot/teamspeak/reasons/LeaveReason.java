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
package eu.matejkormuth.ts3bot.teamspeak.reasons;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nonnull;

/**
 * Represents reason why client left server.
 */
public enum LeaveReason {
    /**
     * Client lost connetion to server.
     */
    CONNECTION_LOST(3),
    /**
     * Client was kicked from server.
     */
    KICK(5),
    /**
     * Clients disconnected from server.
     */
    DISCONNECT(8),
    /**
     * Disconnect reason is unknown.
     */
    UNKNOWN(-1);
    
    private int reasonId;
    
    private LeaveReason(final int id) {
        this.reasonId = id;
    }
    
    /**
     * Returns integer representation of this reason.
     * 
     * @return integer representation of this reason
     */
    public int getReasonId() {
        return this.reasonId;
    }
    
    private static final Map<Integer, LeaveReason> mapping = new HashMap<Integer, LeaveReason>();
    
    /**
     * Returns {@link LeaveReason} by integer representation of its value.
     * 
     * @param id
     *            integer representation of specified reason
     * @return {@link LeaveReason} for specified id
     */
    @Nonnull
    public static LeaveReason byId(final int id) {
        if (mapping.containsKey(id)) {
            return mapping.get(id); // will never be null...
        }
        else {
            return UNKNOWN;
        }
    }
    
    static {
        for (LeaveReason lr : LeaveReason.values()) {
            mapping.put(lr.getReasonId(), lr);
        }
    }
}
