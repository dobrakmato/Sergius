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

import java.util.HashMap;
import java.util.Map;

/**
 * Represents all available codecs on Teamspeak server.
 */
public enum Codec {
    CODEC_SPEEX_NARROWBAND(0),
    CODEC_SPEEX_WIDEBAND(1),
    CODEC_SPEEX_ULTRAWIDEBAND(2),
    CODEC_CELT_MONO(3),
    OPUS_VOICE(4),
    OPUS_MUSIC(5),
    UNKNOWN(-1);
    
    private int id;
    
    private Codec(final int id) {
        this.id = id;
    }
    
    public int getId() {
        return this.id;
    }
    
    private static final Map<Integer, Codec> mapping = new HashMap<Integer, Codec>();
    
    public static Codec byId(final int codec) {
        if (mapping.containsKey(codec)) {
            return mapping.get(codec);
        }
        else {
            return UNKNOWN;
        }
    }
    
    static {
        for (Codec c : Codec.values()) {
            mapping.put(c.id, c);
        }
    }
}
