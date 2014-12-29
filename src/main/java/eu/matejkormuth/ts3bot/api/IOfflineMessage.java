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
/**
 * Represents offline message on TeamSpeak server.
 */
public interface IOfflineMessage {
    /**
     * Returns id of this message on TeamSpeak server.
     * 
     * @return message id
     */
    public abstract int getMessageId();
    
    /**
     * Returns subject of this message.
     * 
     * @return subject
     */
    public abstract String getSubject();
    
    /**
     * Returns content of this message.
     * 
     * @return content
     */
    public abstract String getContent();
    
    /**
     * Returns unique identifier of sender.
     * 
     * @return UId of sender
     */
    public abstract String getSenderUid();
    
    /**
     * Returns unique identifier of receiver.
     * 
     * @return UId of receiver
     */
    public abstract String getRecipientUid();
}
