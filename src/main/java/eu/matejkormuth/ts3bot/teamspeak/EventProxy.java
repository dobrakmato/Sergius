package eu.matejkormuth.ts3bot.teamspeak;

import javax.annotation.Nonnull;

import com.github.theholywaffle.teamspeak3.api.ClientProperty;
import com.github.theholywaffle.teamspeak3.api.event.ChannelCreateEvent;
import com.github.theholywaffle.teamspeak3.api.event.ChannelDeletedEvent;
import com.github.theholywaffle.teamspeak3.api.event.ChannelDescriptionEditedEvent;
import com.github.theholywaffle.teamspeak3.api.event.ChannelEditedEvent;
import com.github.theholywaffle.teamspeak3.api.event.ChannelMovedEvent;
import com.github.theholywaffle.teamspeak3.api.event.ChannelPasswordChangedEvent;
import com.github.theholywaffle.teamspeak3.api.event.ClientJoinEvent;
import com.github.theholywaffle.teamspeak3.api.event.ClientLeaveEvent;
import com.github.theholywaffle.teamspeak3.api.event.ClientMovedEvent;
import com.github.theholywaffle.teamspeak3.api.event.ServerEditedEvent;
import com.github.theholywaffle.teamspeak3.api.event.TS3Listener;
import com.github.theholywaffle.teamspeak3.api.event.TextMessageEvent;
import com.google.common.base.Preconditions;
import com.google.common.eventbus.EventBus;

import eu.matejkormuth.ts3bot.Bot;
import eu.matejkormuth.ts3bot.events.chat.ChannelChatMessageEvent;
import eu.matejkormuth.ts3bot.events.chat.PrivateChatMessageEvent;
import eu.matejkormuth.ts3bot.events.chat.ServerChatMessageEvent;
import eu.matejkormuth.ts3bot.events.client.ClientKickedEvent;
import eu.matejkormuth.ts3bot.teamspeak.reasons.LeaveReason;

/**
 * This class proxies internal ServerQuery wrapper events to our EventBus. Marked as final, because there is no reason
 * to extend it.
 */
public final class EventProxy implements TS3Listener {
    private final EventBus      bus = Bot.getInstance().getEventBus();
    @Nonnull
    private final VirtualServer server;
    
    public EventProxy(@Nonnull final VirtualServer virtualServer) {
        this.server = virtualServer;
    }
    
    // TODO: Figure out events and event names
    
    public void onChannelCreate(final ChannelCreateEvent event) {
        Channel channel = this.server.getChannelById(event.getChannelId());
        Client invoker = this.server.getClientById(event.getInvokerId());
        Preconditions.checkNotNull(channel);
        Preconditions.checkNotNull(invoker);
        this.bus.post(new eu.matejkormuth.ts3bot.events.channel.ChannelCreatedEvent(
                this.server, channel, invoker));
    }
    
    public void onChannelDeleted(final ChannelDeletedEvent event) {
        Channel channel = this.server.getChannelById(event.getChannelId());
        Preconditions.checkNotNull(channel);
        this.bus.post(new eu.matejkormuth.ts3bot.events.channel.ChannelDeletedEvent(
                this.server, channel));
    }
    
    public void onChannelDescriptionChanged(final ChannelDescriptionEditedEvent event) {
        Channel channel = this.server.getChannelById(event.getChannelId());
        Preconditions.checkNotNull(channel);
        this.bus.post(new eu.matejkormuth.ts3bot.events.channel.ChannelDescriptionEditedEvent(
                this.server, channel));
    }
    
    public void onChannelEdit(final ChannelEditedEvent event) {
        Channel channel = this.server.getChannelById(event.getChannelId());
        Preconditions.checkNotNull(channel);
        this.bus.post(new eu.matejkormuth.ts3bot.events.channel.ChannelEditedEvent(
                this.server, channel));
    }
    
    public void onChannelMoved(final ChannelMovedEvent event) {
        Channel channel = this.server.getChannelById(event.getChannelId());
        if (channel != null) {
            // Update channel
            channel.setPcid(event.getChannelParentId());
            channel.setOrder(event.getChannelOrder());
            this.bus.post(new eu.matejkormuth.ts3bot.events.channel.ChannelMovedEvent(
                    this.server, channel));
        }
    }
    
    public void onChannelPasswordChanged(final ChannelPasswordChangedEvent event) {
        Channel channel = this.server.getChannelById(event.getChannelId());
        Preconditions.checkNotNull(channel);
        this.bus.post(new eu.matejkormuth.ts3bot.events.channel.ChannelPasswordChangedEvent(
                this.server, channel));
    }
    
    public void onClientJoin(final ClientJoinEvent event) {
        // Returns null when client not on server.
        Client c = this.server.getClientById(event.getClientId());
        
        if (c == null) {
            // Client is not cached, just joined server.
            c = this.server.addClient(new Client(this.server, event.getClientNickname(),
                    event.getClientId(),
                    event.get(ClientProperty.CLIENT_UNIQUE_IDENTIFIER),
                    event.getClientDatabaseId(), event.getClientCountry(),
                    event.get(ClientProperty.CLIENT_PLATFORM),
                    event.get(ClientProperty.CLIENT_VERSION),
                    ClientType.byId(event.getClientType())));
            // Post event.
            this.bus.post(new eu.matejkormuth.ts3bot.events.client.ClientJoinedServerEvent(
                    this.server, c));
        }
        else {
            // Client is cached, he joined channel.
            Channel fromc = this.server.getChannelById(event.getClientFromId());
            Channel toc = this.server.getChannelById(event.getClientTargetId());
            if (fromc != null && toc != null) {
                this.bus.post(new eu.matejkormuth.ts3bot.events.client.ClientJoinedChannelEvent(
                        this.server, c, fromc, toc));
            }
        }
        
        // Update client's properties.
        c.setAway(event.isClientAway());
        c.setAwayMessage(event.getClientAwayMessage());
        c.setChannelCommander(event.isClientChannelCommander());
        c.setChannelGroupId(event.getClientChannelGroupId());
        c.setChannelId(event.getClientTargetId());
        c.setInheritedChannelGroupId(event.getClientInheritedChannelGroupId());
        c.setInputMuted(event.isClientInputMuted());
        c.setOutputMuted(event.isClientOutputMuted());
        c.setPrioritySpeaker(event.isClientPrioritySpeaker());
        c.setRecording(event.isClientRecording());
        c.setTalking(event.isClientTalking());
        c.setTalkPower(event.getClientTalkPower());
    }
    
    public void onClientLeave(final ClientLeaveEvent event) {
        Client c = this.server.getClientById(event.getClientId());
        Preconditions.checkNotNull(c);
        LeaveReason lr = LeaveReason.byId(event.getReasonId());
        // Fire leave event.
        if (lr == LeaveReason.KICK) {
            this.bus.post(new ClientKickedEvent(this.server, c, lr));
        }
        else {
            this.bus.post(new eu.matejkormuth.ts3bot.events.client.ClientLeaveChannelEvent(
                    this.server, c, LeaveReason.byId(event.getReasonId())));
        }
        
        // Remove client.
        this.server.removeClient(c);
    }
    
    public void onClientMoved(final ClientMovedEvent event) {
        // Returns null when client not on server.
        Client c = this.server.getClientById(event.getClientId());
        
        if (c == null) {
            // This is not good! Reload clients on TS.
            this.server.getClients(true);
        }
        c = this.server.getClientById(event.getClientId());
        
        if (c != null) {
            // Client is cached, he joined channel.
            Channel toc = this.server.getChannelById(event.getClientTargetId());
            if (toc != null) {
                this.bus.post(new eu.matejkormuth.ts3bot.events.client.ClientMovedEvent(
                        this.server, c, toc));
            }
        }
    }
    
    public void onServerEdit(final ServerEditedEvent event) {
        Client invoker = this.server.getClientById(event.getInvokerId());
        Preconditions.checkNotNull(invoker);
        this.bus.post(new eu.matejkormuth.ts3bot.events.server.ServerEditedEvent(
                this.server, invoker));
    }
    
    public void onTextMessage(final TextMessageEvent event) {
        Client sender = this.server.getClientById(event.getInvokerId());
        Preconditions.checkNotNull(sender);
        switch (event.getTargetMode()) {
            case CHANNEL:
                this.bus.post(new ChannelChatMessageEvent(this.server,
                        this.server.getCurrentChannel(), sender, event.getMessage()));
                break;
            case CLIENT:
                this.bus.post(new PrivateChatMessageEvent(this.server, sender,
                        event.getMessage()));
                break;
            case SERVER:
                this.bus.post(new ServerChatMessageEvent(this.server, sender,
                        event.getMessage()));
                break;
        }
    }
}
