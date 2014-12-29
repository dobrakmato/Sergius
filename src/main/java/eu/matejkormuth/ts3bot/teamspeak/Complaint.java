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

import java.util.Date;

/**
 * Represents Complain on Teamspeak server.
 */
public class Complaint implements eu.matejkormuth.ts3bot.api.IComplaint {
    private Client              source;
    private Client              target;
    private String              message;
    private Date                createdAt;
    
    private final VirtualServer server;
    
    public Complaint(final VirtualServer server, final Client source,
            final Client target, final String message, final Date createdAt) {
        this.server = server;
        this.source = source;
        this.target = target;
        this.message = message;
        this.createdAt = createdAt;
    }
    
    protected void setSource(final Client source) {
        this.source = source;
    }
    
    protected void setTarget(final Client target) {
        this.target = target;
    }
    
    protected void setMessage(final String message) {
        this.message = message;
    }
    
    protected void setCreatedAt(final Date createdAt) {
        this.createdAt = createdAt;
    }
    
    public VirtualServer getServer() {
        return this.server;
    }
    
    public Client getSource() {
        return this.source;
    }
    
    public Client getTarget() {
        return this.target;
    }
    
    public String getMessage() {
        return this.message;
    }
    
    public Date getCreatedAt() {
        return this.createdAt;
    }
    
    public void remove() {
        this.server.getConnection()
                .getApi()
                .deleteComplaint(this.target.getId(), this.source.getId());
    }
    
    @Override
    public String toString() {
        return "Complaint [source=" + this.source + ", target=" + this.target
                + ", message=" + this.message + ", createdAt=" + this.createdAt
                + ", server=" + this.server + "]";
    }
}
