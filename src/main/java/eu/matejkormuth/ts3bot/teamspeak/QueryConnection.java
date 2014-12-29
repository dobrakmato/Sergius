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

import java.util.logging.Level;
import java.util.regex.Pattern;

import javax.annotation.Nonnull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.theholywaffle.teamspeak3.TS3Api;
import com.github.theholywaffle.teamspeak3.TS3Config;
import com.github.theholywaffle.teamspeak3.TS3Query;
import com.github.theholywaffle.teamspeak3.TS3Query.FloodRate;
import com.github.theholywaffle.teamspeak3.api.event.TS3EventType;

/**
 * Represents connection to Teamspeak server using ServerQuery.
 */
public class QueryConnection {
    private final TS3Config config;
    private TS3Query        query;
    private TS3Api          api;
    private int             port = 10011;
    
    private String          nickname;
    private VirtualServer   virtualServer;
    private EventProxy      eventProxy;
    
    private final Logger    log  = LoggerFactory.getLogger(this.getClass());
    private final boolean   autoSubscribe;
    
    /**
     * <p>
     * Creates new {@link QueryConnection} with specified creditnals and specified ip and nickname. To connect to query
     * server use {@link #connect()} method.
     * </p>
     * <p>
     * You can pass IP address of target server in format <code>AAA.BBB.CCC.DDD</code> or with port
     * <code>AAA.BBB.CCC.DDD:PORT</code>.
     * </p>
     * <p>
     * You should not use anti-flooding mechanism and rather add IP address from which will Bot connect to Teamspeak
     * server to server query whilelist.
     * </p>
     * <p>
     * It is reccomended to register events only in channels, where you really need to recieve events, auto-registering
     * all channels can be really slow on server with many channels.
     * </p>
     * 
     * @param creditnals
     *            {@link QueryCreditnals} creditnals used to authenticate
     * @param ip
     *            ip of query server in format <code>a.b.c.d</code> or <code>a.b.c.d:xyz</code>
     * @param nickname
     *            nickname to use when on server
     * @param preventFlood
     *            whether to use mechanism that should prevent query from disconecting because of flooding server
     *            (recommended <code>false</code>; server must has ip on query whilelist)
     * @param autoSubscribe
     *            whether the query should <b>register channel and channel chat events in all channels on server</b>
     *            (<b>on servers with many channels this can be slow</b>, and can slow down whole Bot; use only when
     *            really needed - spying channels or other stuff)
     */
    public QueryConnection(@Nonnull final QueryCreditnals creditnals,
            @Nonnull final String ip, @Nonnull final String nickname,
            final boolean preventFlood, final boolean autoSubscribe) {
        
        this.config = new TS3Config();
        if (ip.contains(":")) {
            String[] parts = ip.split(Pattern.quote(":"));
            this.config.setHost(parts[0]);
            this.port = Integer.parseInt(parts[1]);
            this.config.setQueryPort(this.port);
        }
        else {
            this.config.setHost(ip);
        }
        if (preventFlood) {
            this.config.setFloodRate(FloodRate.DEFAULT);
        }
        else {
            this.config.setFloodRate(FloodRate.UNLIMITED);
        }
        this.autoSubscribe = autoSubscribe;
        this.config.setLoginCredentials(creditnals.getUsername(),
                creditnals.getPassword());
        this.config.setDebugLevel(Level.OFF);
        this.nickname = nickname;
    }
    
    /**
     * Connects to query server, selects virtual server and creates instance of {@link VirtualServer} that can be
     * accessed using getter {@link #getVirtualServer()}.
     */
    public void connect() {
        this.query = new TS3Query(this.config);
        this.log.info("Connecting to ServerQuery...");
        // Connect query to server.
        this.query.connect();
        // Make reference to API.
        this.api = this.query.getApi();
        this.log.info("Selecting virtual server...");
        // Select default virtual server.
        this.query.getApi().selectVirtualServerById(1);
        // Apply nickname.
        this.query.getApi().setNickname(this.nickname);
        // Create VirtualServer object.
        this.virtualServer = new VirtualServer(this, 1, -1, this.autoSubscribe); // TODO: Port unknown at this time.
        this.log.info("Registering events...");
        // Register all events and set up EventProxy.
        this.eventProxy = new EventProxy(this.virtualServer);
        this.api.registerEvent(TS3EventType.SERVER);
        this.api.registerEvent(TS3EventType.TEXT_SERVER);
        this.api.registerEvent(TS3EventType.TEXT_PRIVATE);
        this.api.addTS3Listeners(this.eventProxy);
        this.log.info("Connected!");
    }
    
    protected TS3Query getQuery() {
        return this.query;
    }
    
    protected TS3Api getApi() {
        return this.api;
    }
    
    /**
     * Returns currently used nickname on server.
     * 
     * @return currently used nickname
     */
    public String getNickname() {
        return this.nickname;
    }
    
    /**
     * Returns {@link VirtualServer} instance that is query currently connected to.
     * 
     * @return {@link VirtualServer} instance that is query connected to
     */
    public VirtualServer getVirtualServer() {
        return this.virtualServer;
    }
    
    /**
     * Returns port of query server.
     * 
     * @return the port
     */
    public int getPort() {
        return this.port;
    }
    
    /**
     * Updates client name on server.
     * 
     * @param nickname
     *            new nickname to use
     */
    public void setNickName(@Nonnull final String nickname) {
        this.nickname = nickname;
        this.api.setNickname(nickname);
    }
    
    /**
     * Disconnects from ServerQuery server.
     */
    public void disconnect() {
        this.log.info("Disconnecting from server query...");
        this.query.exit();
    }
}
