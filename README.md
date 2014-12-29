Sergius [![Build Status](https://travis-ci.org/dobrakmato/Sergius.svg)](https://travis-ci.org/dobrakmato/Sergius)
-----------
Sergius is *object-oriented* Java TeamSpeak 3 ServerQuery library prepeared for creating bots. **It is in heavy development.**

It uses TheHolyWaffle's TeamSpeak 3 library as wrapper of connection to server query and provides *object-oriented interface for work with ServerQuery*. For dependency managment it uses Maven and for loggin **slf4j**. You can custom log format in log4j.properties.

# Writing a Bot
If you want to create Your own bot, just extend `Service` class and append `bot.service=PATH_TO_YOUR_CLASS_THAT_EXTENDS_SERVICE` to configuration file `bot.properties`.

For service example look at `eu.matejkormuth.ts3bot.services.PexelConnector`.

Example bot.properties:

``` properties 
# TS3 Bot configuration.
config.useExternal=false
query.login=query123
query.password=password
server.ip=123.456.789.123
server.preventflood=false
server.autoRegisterChannelEvents=true
bot.name=John
bot.service=eu.matejkormuth.ts3bot.services.PexelConnector
```

# Contributing

If you want to contribute feel free. You can also open issue if You want to request feature or report bug!

-------------

TheHolyWaffle's TeamSpeak 3 library: <https://github.com/TheHolyWaffle/TeamSpeak-3-Java-API>