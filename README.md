Sergius [![Build Status](https://travis-ci.org/dobrakmato/Sergius.svg)](https://travis-ci.org/dobrakmato/Sergius)
-----------
Sergius is *object-oriented* Java TeamSpeak 3 ServerQuery library prepeared for creating bots.

Javadocs (tries to be latest always): <http://beta.mtkn.eu/docs/Sergius/>

It uses TheHolyWaffle's TeamSpeak 3 library as wrapper of connection to server query and provides *object-oriented interface for work with ServerQuery*. For dependency managment it uses Maven and for loggin **slf4j**. You can custom log format in log4j.properties.

# Writing a Bot
If you want to create Your own bot, just extend `Service` class and append `bot.service=PATH_TO_YOUR_CLASS_THAT_EXTENDS_SERVICE` to configuration file `bot.properties`.

For service example look at classes in package `eu.matejkormuth.ts3bot.services`.

Example bot.properties:

``` properties 
# TS3 Bot configuration.
config.useExternal=false
query.login=query123
query.password=password
server.ip=123.456.789.123
server.preventflood=false
server.autoRegisterChannelEvents=true
bot.name=Sergius
bot.service=eu.matejkormuth.ts3bot.services.FreeRoomService
```

# Pre-made Bots
**Sergius comes with some premade bots**. For example `FreeRoomService` is a service which provides set of 'public rooms'. 

## FreeRoomService
Users can join specified public room, *request temporary password protection* which is removed when room gets empty.

Rooms are created and removed when needed. *Amount of them never cross maximum amount of public rooms* (if specified).

# Contributing

If you want to contribute feel free. You can also open issue if You want to request feature or report bug!

-------------

TheHolyWaffle's TeamSpeak 3 library: <https://github.com/TheHolyWaffle/TeamSpeak-3-Java-API>