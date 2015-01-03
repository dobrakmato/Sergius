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
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.annotation.Nonnull;

import com.google.common.eventbus.Subscribe;

import eu.matejkormuth.ts3bot.Service;
import eu.matejkormuth.ts3bot.api.IChannel;
import eu.matejkormuth.ts3bot.api.IClient;
import eu.matejkormuth.ts3bot.events.chat.PrivateChatMessageEvent;
import eu.matejkormuth.ts3bot.events.client.ClientMovedEvent;
import eu.matejkormuth.ts3bot.teamspeak.Channel;
import eu.matejkormuth.ts3bot.teamspeak.properties.ChannelProperty;

/**
 * Service that provieds right amount of "Free Rooms" on teamspeak server.
 */
public class FreeRoomService extends Service {
    // Settings
    private final String   mainName           = "Free Rooms";
    private final String   childName          = "Free Room #";
    private final boolean  watchMainChannel   = true;
    private final int      minimumAmount      = 3;
    private final int      maximumAmount      = 30;           // -1 unlimited
    private final boolean  enableChatRequests = true;
    private final int      checkInterval      = 7000;         // in ms; -1 to disable
                                                               
    // Chat keywords.
    private final String   lockRoomKeyword    = "lock";
    private final String   passwordKeyword    = "password";
    
    private IChannel       mainChannel;
    private List<IChannel> channels;
    private Timer          timer;
    
    @Override
    public void enable() {
        if (this.maximumAmount <= this.minimumAmount) {
            this.getLogger()
                    .error("Maximum amount of channels is lower then Minimum amount of channels!");
            return;
        }
        
        this.getLogger().info("Builing channels...");
        
        // Create main room.
        this.mainChannel = new Channel.ChannelBuilder(this.getBot().getVirtualServer()).name(
                this.mainName)
                .permanent()
                .description(
                        "Join this channel and You will be moved to next empty free room!")
                .parent(0)
                .build();
        
        this.mainChannel.join();
        
        // Listen for specified room.
        if (this.watchMainChannel) {
            this.getLogger().info("Watching main channel...");
            this.mainChannel.registerEvents();
        }
        
        // Create minimal amount of child rooms.
        this.channels = Collections.synchronizedList(new ArrayList<IChannel>());
        for (int i = 0; i < this.minimumAmount; i++) {
            this.channels.add(new Channel.ChannelBuilder(this.getBot()
                    .getVirtualServer()).name(this.childName + (i + 1))
                    .permanent()
                    .parent(this.mainChannel)
                    .build());
        }
        
        // Enable periodic checking.
        if (this.checkInterval > 0) {
            this.getLogger().info("Enabling periodic checking...");
            this.timer = new Timer("FreeRoomTimer", true);
            this.timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    FreeRoomService.this.checkRooms();
                }
            }, 0L, this.checkInterval);
        }
    }
    
    @Override
    public void disable() {
        this.timer.cancel();
        // Remove main channel - therefor kick clients and remove subchennels.
        this.mainChannel.remove();
    }
    
    private void checkRooms() {
        int emptyChannels = 0;
        
        // Update clients.
        this.getBot().getVirtualServer().getClients(true);
        
        for (IChannel c : this.channels) {
            if (c.getClients().isEmpty()) {
                // Remove password
                if (c.isPasswordProtected()) {
                    c.setProperty(ChannelProperty.CHANNEL_PASSWORD, "");
                }
                emptyChannels++;
            }
        }
        
        int emtpyChannelLeft = emptyChannels;
        if (this.channels.size() > this.minimumAmount) {
            for (IChannel c : this.channels) {
                if (c.getClients().isEmpty()) {
                    if (emtpyChannelLeft > 1) {
                        this.channels.remove(c);
                        c.remove();
                    }
                }
            }
        }
        
        // If there are no empty channels, create one.
        if (emptyChannels == 0) {
            // But only if we don't cross maximum limit.
            if (this.maximumAmount > 0) {
                if (this.channels.size() + 1 < this.maximumAmount) {
                    this.addChannel();
                }
            } else {
                this.addChannel();
            }
        }
    }
    
    @Nonnull
    private IChannel addChannel() {
        IChannel c = new Channel.ChannelBuilder(this.getBot().getVirtualServer()).name(
                this.childName + (this.channels.size() + 1))
                .permanent()
                .parent(this.mainChannel)
                .build();
        this.channels.add(c);
        return c;
    }
    
    @Subscribe
    public void onClientEnterMain(final ClientMovedEvent event) {
        if (event.getChannel() == this.mainChannel) {
            if (this.watchMainChannel) {
                // Move him to first empty channel.
                for (IChannel c : this.channels) {
                    if (c.getClients().isEmpty()) {
                        event.getClient().moveTo(c);
                        return;
                    }
                }
                
                // Not found empty, create one.
                event.getClient().moveTo(this.addChannel());
                
            }
        }
    }
    
    @Subscribe
    public void onPrivateChat(final PrivateChatMessageEvent event) {
        // Response only to client in our channels.
        if (this.enableChatRequests) {
            IClient sender = event.getSender();
            sender.update();
            if (this.channels.contains(event.getSender().getChannel())) {
                if (event.getMessage()
                        .toLowerCase(Locale.ENGLISH)
                        .contains(this.lockRoomKeyword)) {
                    sender.getChannel().update();
                    
                    // Lock only if room is not already locked.
                    if (!event.getSender().getChannel().isPasswordProtected()) {
                        // Lock this room and tell password back.
                        String password = PasswordGenerator.getPassword();
                        sender.getChannel().setProperty(
                                ChannelProperty.CHANNEL_PASSWORD, password);
                        
                        sender.sendMessage("Room "
                                + sender.getChannel().getName()
                                + " is now password protected. Password is: "
                                + password
                                + ". As soon as the channel gets empty, password protection is removed.");
                    } else {
                        sender.sendMessage("Room is already password protected!");
                    }
                } else if (event.getMessage()
                        .toLowerCase(Locale.ENGLISH)
                        .contains(this.passwordKeyword)) {
                    
                    if (sender.getChannel().isPasswordProtected()) {
                        String password = event.getSender().getChannel().getPassword();
                        sender.sendMessage("Password is: " + password);
                    } else {
                        sender.sendMessage("Room "
                                + event.getSender().getChannel().getName()
                                + " is not password protected!");
                    }
                } else {
                    sender.sendMessage("Can't answer this command! Supported commands: lock, passsword");
                }
            } else {
                sender.sendMessage("I don't work for You here!");
            }
        }
    }
    
    static class PasswordGenerator {
        private static String[] adj_wordlist;
        private static String[] noun_wordlist;
        private static Random   random = new Random();
        
        static {
            adj_wordlist = new String[] { "adorable", "adventurous", "aggressive",
                    "agreeable", "alert", "alive", "amused", "angry", "annoyed",
                    "annoying", "anxious", "arrogant", "ashamed", "attractive",
                    "average", "awful", "bad", "beautiful", "better", "bewildered",
                    "black", "bloody", "blue", "blue-eyed", "blushing", "bored",
                    "brainy", "brave", "breakable", "bright", "busy", "calm", "careful",
                    "cautious", "charming", "cheerful", "clean", "clear", "clever",
                    "cloudy", "clumsy", "colorful", "combative", "comfortable",
                    "concerned", "condemned", "confused", "cooperative", "courageous",
                    "crazy", "creepy", "crowded", "cruel", "curious", "cute",
                    "dangerous", "dark", "dead", "defeated", "defiant", "delightful",
                    "depressed", "determined", "different", "difficult", "disgusted",
                    "distinct", "disturbed", "dizzy", "doubtful", "drab", "dull",
                    "eager", "easy", "elated", "elegant", "embarrassed", "enchanting",
                    "encouraging", "energetic", "enthusiastic", "envious", "evil",
                    "excited", "expensive", "exuberant", "fair", "faithful", "famous",
                    "fancy", "fantastic", "fierce", "filthy", "fine", "foolish",
                    "fragile", "frail", "frantic", "friendly", "frightened", "funny",
                    "gentle", "gifted", "glamorous", "gleaming", "glorious", "good",
                    "gorgeous", "graceful", "grieving", "grotesque", "grumpy",
                    "handsome", "happy", "healthy", "helpful", "helpless", "hilarious",
                    "homeless", "homely", "horrible", "hungry", "hurt", "ill",
                    "important", "impossible", "inexpensive", "innocent", "inquisitive",
                    "itchy", "jealous", "jittery", "jolly", "joyous", "kind", "lazy",
                    "light", "lively", "lonely", "long", "lovely", "lucky",
                    "magnificent", "misty", "modern", "motionless", "muddy", "mushy",
                    "mysterious", "nasty", "naughty", "nervous", "nice", "nutty",
                    "obedient", "obnoxious", "odd", "old-fashioned", "open",
                    "outrageous", "outstanding", "panicky", "perfect", "plain",
                    "pleasant", "poised", "poor", "powerful", "precious", "prickly",
                    "proud", "puzzled", "quaint", "real", "relieved", "repulsive",
                    "rich", "scary", "selfish", "shiny", "shy", "silly", "sleepy",
                    "smiling", "smoggy", "sore", "sparkling", "splendid", "spotless",
                    "stormy", "strange", "stupid", "successful", "super", "talented",
                    "tame", "tender", "tense", "terrible", "testy", "thankful",
                    "thoughtful", "thoughtless", "tired", "tough", "troubled",
                    "ugliest", "ugly", "uninterested", "unsightly", "unusual", "upset",
                    "uptight", "vast", "victorious", "vivacious", "wandering", "weary",
                    "wicked", "wide-eyed", "wild", "witty", "worrisome", "worried",
                    "wrong", "zany", "zealous" };
            
            noun_wordlist = new String[] { "alligator", "ant", "bear", "bee", "bird",
                    "camel", "cat", "cheetah", "chicken", "chimpanzee", "cow",
                    "crocodile", "deer", "dog", "dolphin", "duck", "eagle", "elephant",
                    "fish", "fly", "fox", "frog", "giraffe", "goat", "goldfish",
                    "hamster", "hippopotamus", "horse", "kangaroo", "kitten", "lion",
                    "lobster", "monkey", "octopus", "owl", "panda", "pig", "puppy",
                    "rabbit", "rat", "scorpion", "seal", "shark", "sheep", "snail",
                    "snake", "spider", "squirrel", "tiger", "turtle", "wolf", "zebra" };
        }
        
        @Nonnull
        public static String getPassword() {
            return adj_wordlist[random.nextInt(adj_wordlist.length)]
                    + noun_wordlist[random.nextInt(noun_wordlist.length)];
        }
    }
}
