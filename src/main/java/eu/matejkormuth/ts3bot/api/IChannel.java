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
import java.util.Set;

import javax.annotation.Nonnull;

import eu.matejkormuth.ts3bot.teamspeak.Channel.ChannelBuilder;
import eu.matejkormuth.ts3bot.teamspeak.Codec;
import eu.matejkormuth.ts3bot.teamspeak.properties.ChannelProperty;

// TODO: Refactor names
/**
 * Represnts channel on TeamSpeak server.
 */
public interface IChannel {
    /**
     * Returns name of this channel.
     * 
     * @return the name
     */
    public abstract String getName();
    
    /**
     * Returns internal id of this channel.
     * 
     * @return id of this channel
     */
    public abstract int getId();
    
    /**
     * Returns internal id of this channels parent channel.
     * 
     * @return id of parent channel
     */
    public abstract int getParentChannelId();
    
    /**
     * Returns instance of parent channel.
     * 
     * @return parent channel instance
     */
    public abstract IChannel getParent();
    
    /**
     * Returns this channel order.
     * 
     * @return order of this channel
     */
    public abstract int getOrder();
    
    /**
     * Returns topic of this channel or null if this channel has no topic
     * 
     * @return topic or null
     */
    public abstract String getTopic();
    
    /**
     * Checks whether this channel is default or not.
     * 
     * @return true if channel is default, otherwise false
     */
    public abstract boolean isDefaultChannel();
    
    /**
     * Checks whether this channel is password protected or not.
     * 
     * @return true if channel is password protected, false otherwise
     */
    public abstract boolean isPasswordProtected();
    
    /**
     * Check whether this channel is permament or not.
     * 
     * @return true if channel is permanent, false otherwise
     */
    public abstract boolean isPermanent();
    
    /**
     * Check whether this channel is semi-permament or not.
     * 
     * @return true if channel is semi-permanent, false otherwise
     */
    public abstract boolean isSemiPermanent();
    
    /**
     * Returns voice {@link Codec} of this channel.
     * 
     * @return {@link Codec} used by this channel
     */
    public abstract Codec getCodec();
    
    /**
     * Returns quality of voice codec used by this channel
     * 
     * @return voice codec quality
     */
    public abstract int getCodecQuality();
    
    /**
     * Returns icon id of this channel.
     * 
     * @return this channels icon's id
     */
    public abstract long getIconId();
    
    /**
     * Returns amount of clients in this channel family
     * 
     * @return amount of clients in this channel family
     */
    public abstract int getTotalClientsFamily();
    
    /**
     * Returns maximum amount of clients in this channel.
     * 
     * @return this channel's maximum amount of clients
     */
    public abstract int getMaxClients();
    
    /**
     * Returns maxiumum amount of clients in this channel family tree.
     * 
     * @return amount amount of clients in this channl family tree
     */
    public abstract int getMaxFamilyClients();
    
    /**
     * Returns amount of clients in this channel
     * 
     * @return amount of clients in this channel
     */
    public abstract int getTotalClients();
    
    /**
     * Returns power needed to subscribe to this channel.
     * 
     * @return needed power to subscribe
     */
    public abstract int getNeededSubscribePower();
    
    /**
     * Returns password of this channel.
     * 
     * @return this channel's password
     */
    public abstract String getPassword();
    
    /**
     * Returns phonetic name of this channel.
     * 
     * @return phonetic name
     */
    public abstract String getPhoneticName();
    
    /**
     * Returns {@link IVirtualServer} on which is this ban present.
     * 
     * @return instance of {@link IVirtualServer}
     */
    public abstract IVirtualServer getServer();
    
    /**
     * Creates sub channel of this channel. For more advanced creation (permanent, password, etc...), please use
     * {@link ChannelBuilder}.
     * 
     * @param name
     *            name of the channel
     * @return created channel
     */
    public abstract IChannel createSubChannel(@Nonnull String name);
    
    /**
     * Returns view of server clients containing only clients that are in this channel. This method returns clients from
     * cache, thus it's fast and can be inaccurate.
     * 
     * @return collection of this channel's clients
     */
    public abstract Collection<IClient> getClients();
    
    /**
     * Returns view of server clients containing only clients that are in this channel. This method does update clients
     * from TeamSpeak server before creation of view when <code>forceUpdate</code> is true.
     * 
     * @return collection of this channel's clients
     */
    public abstract Collection<IClient> getClients(boolean forceUpdate);
    
    /**
     * Joins this channel.
     */
    public abstract void join();
    
    /**
     * Sets value of specified {@link ChannelProperty} if property is changable.
     * 
     * @param property
     *            property to set
     * @param value
     *            new value of property
     * @throws IllegalArgumentException
     *             if specified {@link ChannelProperty} is not changable
     */
    public abstract void setProperty(
            eu.matejkormuth.ts3bot.teamspeak.properties.ChannelProperty property,
            @Nonnull String value);
    
    /**
     * Sets specified properties to this channel. Same as calling {@link #setProperty(ChannelProperty, String)} many
     * times.
     * 
     * @param properties
     *            properties to set
     */
    public abstract void setProperties(
            @Nonnull Map<eu.matejkormuth.ts3bot.teamspeak.properties.ChannelProperty, String> properties);
    
    /**
     * Returns value of specified {@link ChannelProperty} of this channel.
     * 
     * @param property
     *            property to get
     * @return property value
     */
    public abstract String getProperty(
            eu.matejkormuth.ts3bot.teamspeak.properties.ChannelProperty property);
    
    /**
     * Sets specified {@link IChannelGroup} for specified {@link IClient} in this channel.
     * 
     * @param client
     *            client to set group to
     * @param group
     *            group to set
     */
    public abstract void setChannelGroup(@Nonnull IClient client,
            @Nonnull IChannelGroup group);
    
    /**
     * Removes this channel from TeamSpeak server. If there are subchannels, they will be removed. Clients will be
     * kicked.
     */
    public abstract void remove();
    
    /**
     * Sends message to this channel.
     * 
     * @param message
     *            message to send
     */
    public abstract void sendMessage(@Nonnull String message);
    
    /**
     * Returns collection of channels with this channel as parent channel.
     * 
     * @param forceUpdate
     *            whether to update channels from server before executing
     * @return set of sub channels
     */
    public abstract Set<IChannel> getSubChannels(boolean forceUpdate);
    
    /**
     * Updates properties of this {@link IChannel} from TeamSpeak server.
     */
    public abstract void update();
    
    /**
     * Registers channel events on this channel.
     */
    public abstract void registerEvents();
    
}
