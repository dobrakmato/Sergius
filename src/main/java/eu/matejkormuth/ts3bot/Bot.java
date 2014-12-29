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
package eu.matejkormuth.ts3bot;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.eventbus.EventBus;

import eu.matejkormuth.ts3bot.teamspeak.QueryConnection;
import eu.matejkormuth.ts3bot.teamspeak.QueryCreditnals;
import eu.matejkormuth.ts3bot.teamspeak.VirtualServer;

/**
 * 
 */
public class Bot {
    private static Bot instance;
    
    public static Bot getInstance() {
        return Bot.instance;
    }
    
    private final Logger        logger   = LoggerFactory.getLogger(this.getClass());
    private final List<Service> services = new ArrayList<Service>();
    private final EventBus      eventBus = new EventBus();
    PropertiesConfiguration     configuration;
    private QueryConnection     queryConnection;
    private ConsoleReader       consoleReader;
    
    public Bot() {
        Bot.instance = this;
    }
    
    /**
     * Boots up Bot.
     */
    public void boot() {
        if (this.configuration != null) { throw new RuntimeException(
                "Bot is already booted up!"); }
        
        this.logger.info("Booting up...");
        
        this.logger.info("Reading configuration...");
        try {
            this.configuration = new PropertiesConfiguration("bot.properties");
        } catch (ConfigurationException e) {
            this.logger.error(
                    "Error while loading configuration from bot.properties: {}",
                    e.getMessage());
            try {
                this.configuration = new PropertiesConfiguration(this.getClass()
                        .getClassLoader()
                        .getResource("bot.properties"));
            } catch (ConfigurationException e2) {
                this.logger.error("Error while loading configuration from jar: {}",
                        e2.getMessage());
                this.configuration = new PropertiesConfiguration();
                this.configuration.setHeader("TS3 Bot configuration.");
                this.configuration.setFileName("bot.properties");
            }
        }
        
        // Connect to teamspeak server.
        String name = this.configuration.getString("query.login");
        String pass = this.configuration.getString("query.password");
        String ip = this.configuration.getString("server.ip");
        if (name == null || pass == null || ip == null) {
            // Can't continue without these values.
            throw new RuntimeException(
                    "Can't get query login, query password or server ip address from config! Can't continue!");
        }
        
        boolean flood = this.configuration.getBoolean("server.preventflood", false);
        boolean autoRegister = this.configuration.getBoolean(
                "server.autoRegisterChannelEvents", true);
        String nickname = this.configuration.getString("bot.name", "TS3Bot");
        this.logger.info("Connecting to teamspeak server {}...", ip);
        QueryCreditnals creds = new QueryCreditnals(name, pass);
        this.queryConnection = new QueryConnection(creds, ip, nickname, flood,
                autoRegister); // nickname will never be null...
        // Connect to server.
        this.queryConnection.connect();
        
        // Some default services.
        List<Object> services = this.configuration.getList("bot.services");
        // In case we have only one service.
        if (services.size() == 0) {
            if (this.configuration.getString("bot.service") != null) {
                services.add(this.configuration.getString("bot.service"));
            }
        }
        
        // Create all services.
        this.logger.info("Loading services...");
        for (Object o : services) {
            try {
                Class<?> clazz = Class.forName(o.toString());
                if (Service.class.isAssignableFrom(clazz)) {
                    Service service = (Service) clazz.getConstructor().newInstance();
                    this.services.add(service);
                }
                else {
                    this.logger.error("Specified service " + o.toString()
                            + " does not extend Service class! Can't load it.");
                }
            } catch (Exception e) {
                this.logger.error("Can't load service {}! Problem: {}", o.toString(),
                        e.getMessage());
                this.logger.error(e.toString());
            }
        }
        
        this.logger.info("Starting services...");
        // Start services.
        for (Service service : this.services) {
            if (service.isAsynchronous()) {
                this.logger.info("Enabling service asynchronously "
                        + service.getClass().getSimpleName() + "...");
                final Service s = service;
                // Start thread for service.
                new Thread(new Runnable() {
                    public void run() {
                        s.setBot(Bot.this);
                        Bot.this.eventBus.register(s);
                        s.enable();
                    }
                }, "service/" + service.getClass().getSimpleName()).run();
            }
            else {
                try {
                    this.logger.info("Enabling service synchronously "
                            + service.getClass().getSimpleName() + "...");
                    service.setBot(this);
                    this.eventBus.register(service);
                    service.enable();
                } catch (Exception e) {
                    this.logger.error("Can't enable service! Problem: {}",
                            e.getMessage());
                    this.logger.error(e.toString());
                }
            }
        }
        
        this.logger.info("Finished loading!");
        
        // Start reading commands from console.
        this.consoleReader = new ConsoleReader();
        this.consoleReader.setBot(this);
        this.consoleReader.enable();
    }
    
    public void shutdown() {
        // Disable reading of commands.
        this.consoleReader.disable();
        
        this.logger.info("Shutting down...");
        try {
            if (this.configuration.getBoolean("config.useExternal", true)) {
                this.configuration.setFile(new File("/bot.properties"));
                this.configuration.save();
                this.logger.info("Configuration saved!");
            }
        } catch (ConfigurationException e) {
            this.logger.error("Couldn't save configuration: {}", e.getMessage());
        }
        this.logger.info("Shutting down services...");
        // Shutdown services.
        for (Service service : this.services) {
            try {
                this.eventBus.unregister(service);
                service.disable();
            } catch (Exception e) {
                this.logger.error("Can't disable service! Problem: {}", e.getMessage());
                this.logger.error(e.toString());
            }
        }
        this.logger.info("Disconnecting...");
        // Close connection.
        this.queryConnection.disconnect();
        // Bye!
        this.logger.info("Thanks for using!");
        this.logger.info("Bye!");
    }
    
    public QueryConnection getQueryConnection() {
        return this.queryConnection;
    }
    
    public Configuration getConfiguration() {
        return this.configuration;
    }
    
    public List<Service> getServices() {
        return this.services;
    }
    
    public Logger getLogger() {
        return this.logger;
    }
    
    public EventBus getEventBus() {
        return this.eventBus;
    }
    
    /**
     * Returns {@link VirtualServer} that is query currently connected to. Same as calling {@link #getQueryConnection()}
     * and then {@link QueryConnection#getVirtualServer()}.
     * 
     * @return {@link VirtualServer} instance that is query connected to
     */
    public VirtualServer getVirtualServer() {
        return this.queryConnection.getVirtualServer();
    }
}
