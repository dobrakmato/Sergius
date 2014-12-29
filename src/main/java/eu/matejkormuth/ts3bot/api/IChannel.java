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
import java.util.Map;

import javax.annotation.Nonnull;

import eu.matejkormuth.ts3bot.teamspeak.Codec;

// TODO: Refactor names
public interface IChannel {
    
    public abstract String getName();
    
    public abstract int getId();
    
    public abstract int getParentChannelId();
    
    public abstract IChannel getParent();
    
    public abstract int getOrder();
    
    public abstract String getTopic();
    
    public abstract boolean isDefaultChannel();
    
    public abstract boolean isPasswordProtected();
    
    public abstract boolean isPermanent();
    
    public abstract boolean isSemiPermanent();
    
    public abstract Codec getCodec();
    
    public abstract int getCodecQuality();
    
    public abstract long getIconId();
    
    public abstract int getTotalClientsFamily();
    
    public abstract int getMaxClients();
    
    public abstract int getMaxFamilyClients();
    
    public abstract int getTotalClients();
    
    public abstract int getNeededSubscribePower();
    
    public abstract String getPassword();
    
    public abstract String getPhoneticName();
    
    public abstract IVirtualServer getServer();
    
    public abstract IChannel createSubChannel(@Nonnull String name);
    
    public abstract Collection<IClient> getClients();
    
    public abstract Collection<IClient> getClients(boolean forceUpdate);
    
    public abstract void join();
    
    public abstract void setProperty(
            eu.matejkormuth.ts3bot.teamspeak.properties.ChannelProperty property,
            @Nonnull String value);
    
    public abstract void setProperties(
            @Nonnull Map<eu.matejkormuth.ts3bot.teamspeak.properties.ChannelProperty, String> properties);
    
    public abstract String getProperty(
            eu.matejkormuth.ts3bot.teamspeak.properties.ChannelProperty property);
    
    public abstract void setChannelGroup(@Nonnull IClient client,
            @Nonnull IChannelGroup group);
    
    public abstract void delete();
    
    public abstract void sendMessage(@Nonnull String message);
    
    public abstract void update();
    
    public abstract void registerEvents();
    
}
