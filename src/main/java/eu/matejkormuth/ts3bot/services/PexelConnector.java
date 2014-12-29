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
package eu.matejkormuth.ts3bot.services;

import java.util.ArrayList;
import java.util.List;

import com.google.common.eventbus.Subscribe;

import eu.matejkormuth.ts3bot.Service;
import eu.matejkormuth.ts3bot.api.IChannel;
import eu.matejkormuth.ts3bot.events.chat.ChannelChatMessageEvent;
import eu.matejkormuth.ts3bot.events.chat.PrivateChatMessageEvent;
import eu.matejkormuth.ts3bot.events.client.ClientMovedEvent;
import eu.matejkormuth.ts3bot.teamspeak.Channel.ChannelBuilder;

/**
 * Testing plugin.
 */
public class PexelConnector extends Service {
    private IChannel       maminec;
    private List<IChannel> channels;
    private int            i = 0;
    
    @Override
    public void enable() {
        this.channels = new ArrayList<IChannel>();
        this.maminec = new ChannelBuilder(this.getBot().getVirtualServer()).name(
                "Maminec")
                .parent(0)
                .permanent()
                .build();
        for (int i = 1; i < 5; i++) {
            this.channels.add(new ChannelBuilder(this.maminec.getServer()).parent(
                    this.maminec)
                    .name("#" + i)
                    .password((Math.random() * 31) + "protected")
                    .permanent()
                    .build());
        }
        this.getBot().getVirtualServer().join(this.maminec);
    }
    
    @Override
    public void disable() {
        this.maminec.delete();
    }
    
    @Subscribe
    public void onJoin(final ClientMovedEvent event) {
        // Do not move self.
        if (event.getClient().getId() == this.getBot().getVirtualServer().getSelfId()) { return; }
        
        // Only when joined Maminec.
        if (event.getChannel().getId() == this.maminec.getId()) {
            if (this.i == this.channels.size()) {
                this.i = 0;
            }
            IChannel ch = this.channels.get(this.i);
            if (ch != null) {
                event.getClient().sendMessage(
                        "Ahoj, " + event.getClient().getNickname()
                                + "! Presuvam ta do kanalu #" + (this.i + 1)
                                + " lebo tam patris! Vela zabavy!");
                event.getClient().moveTo(ch);
            }
            this.i++;
        }
    }
    
    @Subscribe
    public void onChat(final ChannelChatMessageEvent event) {
        
    }
    
    @Subscribe
    public void onChat(final PrivateChatMessageEvent event) {
        
    }
}
