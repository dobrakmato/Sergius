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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.Nonnull;

import eu.matejkormuth.ts3bot.api.IChannel;
import eu.matejkormuth.ts3bot.api.IClient;
import eu.matejkormuth.ts3bot.teamspeak.annotations.CachedValue;

/**
 * Represents channel group on Teamsepak server.
 */
public class ChannelGroup extends UserGroup implements
        eu.matejkormuth.ts3bot.api.IChannelGroup {
    public ChannelGroup(final VirtualServer server, final int id, final String name) {
        super(server, id, name);
    }
    
    public void setAsClientGroup(@Nonnull final IClient client,
            @Nonnull final IChannel channel) {
        this.server.getConnection()
                .getApi()
                .setClientChannelGroup(this.getId(), channel.getId(),
                        client.getDatabaseId());
    }
    
    public void setName(@Nonnull final String name) {
        this.name = name;
        this.server.getConnection().getApi().renameChannelGroup(this.getId(), name);
    }
    
    public void remove() {
        this.server.getConnection().getApi().deleteChannelGroup(this.getId());
        this.server.removeChannelGroup(this);
    }
    
    @CachedValue(callingUpdateHelps = false)
    public Collection<IClient> getClients() {
        return this.getClients(false);
    }
    
    public Collection<IClient> getClients(final boolean update) {
        List<IClient> clients = new ArrayList<IClient>();
        for (IClient c : this.server.getClients(update)) {
            if (c.getChannelGroup() == this) {
                clients.add(c);
            }
        }
        return clients;
    }
    
    protected void updateBy(
            final com.github.theholywaffle.teamspeak3.api.wrapper.ChannelGroup g) {
        this.setIconId(g.getIconId());
        this.setMemberAddPower(g.getMemberAddPower());
        this.setMemberRemovePower(g.getMemberRemovePower());
        this.setModifyPower(g.getModifyPower());
        this.setNameMode(g.getNameMode());
        this.setSortId(g.getSortId());
    }
    
    @Override
    public String toString() {
        return "ChannelGroup [getId()=" + this.getId() + ", getName()=" + this.getName()
                + ", getSortId()=" + this.getSortId() + ", getIconId()="
                + this.getIconId() + ", getNameMode()=" + this.getNameMode()
                + ", getModifyPower()=" + this.getModifyPower()
                + ", getMemberAddPower()=" + this.getMemberAddPower()
                + ", getMemberRemovePower()=" + this.getMemberRemovePower() + "]";
    }
}
