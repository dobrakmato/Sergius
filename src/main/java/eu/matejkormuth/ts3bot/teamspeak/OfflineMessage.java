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

import javax.annotation.Nonnull;

/**
 * Class that represents offline message on Teamspeak server.
 */
public class OfflineMessage implements eu.matejkormuth.ts3bot.api.IOfflineMessage {
    private int          messageId;
    private final String subject;
    private final String content;
    private String       senderUid;
    private String       recipientUid;
    
    public OfflineMessage(@Nonnull final String subject, @Nonnull final String content) {
        this.subject = subject;
        this.content = content;
    }
    
    protected OfflineMessage(final int messageId, @Nonnull final String subject,
            @Nonnull final String content, @Nonnull final String senderUid,
            @Nonnull final String recipientUid) {
        this.messageId = messageId;
        this.subject = subject;
        this.content = content;
        this.senderUid = senderUid;
        this.recipientUid = recipientUid;
    }
    
    public int getMessageId() {
        return this.messageId;
    }
    
    public String getSubject() {
        return this.subject;
    }
    
    public String getContent() {
        return this.content;
    }
    
    public String getSenderUid() {
        return this.senderUid;
    }
    
    public String getRecipientUid() {
        return this.recipientUid;
    }
    
    @Override
    public String toString() {
        return "OfflineMessage [messageId=" + this.messageId + ", subject="
                + this.subject + ", content=" + this.content + ", senderUid="
                + this.senderUid + ", recipientUid=" + this.recipientUid + "]";
    }
    
}
