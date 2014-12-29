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
package eu.matejkormuth.ts3bot.teamspeak.properties;

/**
 * Contains all possible channel properties.
 */
public enum ChannelProperty {
    /**
     * Codec used by the channel.
     */
    CHANNEL_CODEC,
    /**
     * Indicates whether speech data transmitted in this channel is encrypted or not.
     */
    CHANNEL_CODEC_IS_UNENCRYPTED,
    /**
     * <i>Undocumented</i>
     */
    CHANNEL_CODEC_LATENCY_FACTOR,
    /**
     * Codec quality used by the channel.
     */
    CHANNEL_CODEC_QUALITY,
    /**
     * Description of the channel.
     */
    CHANNEL_DESCRIPTION,
    /**
     * Path of the channels file repository.
     */
    CHANNEL_FILEPATH(false),
    /**
     * Indicates whether the channel is the virtual servers default channel or not.
     */
    CHANNEL_FLAG_DEFAULT,
    /**
     * Indicates whether the channel has a max clients limit or not.
     */
    CHANNEL_FLAG_MAXCLIENTS_UNLIMITED,
    /**
     * Indicates whether the channel inherits the max family clients from his parent channel or not.
     */
    CHANNEL_FLAG_MAXFAMILYCLIENTS_INHERITED,
    /**
     * Indicates whether the channel has a max family clients limit or not.
     */
    CHANNEL_FLAG_MAXFAMILYCLIENTS_UNLIMITED,
    /**
     * Indicates whether the channel has a password set or not.
     */
    CHANNEL_FLAG_PASSWORD(false),
    /**
     * Indicates whether the channel is permanent or not.
     */
    CHANNEL_FLAG_PERMANENT,
    /**
     * Indicates whether the channel is semi-permanent or not.
     */
    CHANNEL_FLAG_SEMI_PERMANENT,
    /**
     * Indicates whether the channel is temporary or not.
     */
    CHANNEL_FLAG_TEMPORARY,
    /**
     * Indicates whether the channel is silenced or not.
     */
    CHANNEL_FORCED_SILENCE(false),
    /**
     * CRC32 checksum of the channel icon.
     */
    CHANNEL_ICON_ID,
    /**
     * Individual max number of clients for the channel.
     */
    CHANNEL_MAXCLIENTS,
    /**
     * Individual max number of clients for the channel family.
     */
    CHANNEL_MAXFAMILYCLIENTS,
    /**
     * Name of the channel.
     */
    CHANNEL_NAME,
    /**
     * Phonetic name of the channel.
     */
    CHANNEL_NAME_PHONETIC,
    /**
     * Needed talk power for this channel.
     */
    CHANNEL_NEEDED_TALK_POWER,
    /**
     * <i>Undocumented</i>
     */
    CHANNEL_NEEDED_SUBSCRIBE_POWER,
    /**
     * ID of the channel below which the channel is positioned.
     */
    CHANNEL_ORDER,
    /**
     * Password of the channel.
     */
    CHANNEL_PASSWORD,
    /**
     * Topic of the channel.
     */
    CHANNEL_TOPIC,
    /**
     * The channels ID.
     */
    CID(false),
    /**
     * The channels parent ID.
     */
    CPID;
    
    private boolean changeable;
    
    private ChannelProperty() {
        this(true);
    }
    
    private ChannelProperty(final boolean changable) {
        this.changeable = changable;
    }
    
    public boolean isChangeable() {
        return this.changeable;
    }
    
    public String getName() {
        return this.name().toLowerCase();
    }
}
