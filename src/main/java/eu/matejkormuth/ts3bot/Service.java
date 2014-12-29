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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class that represents Service. Implements {@link Logger} and reference to current {@link Bot}. Contains abstract
 * methods <code>start()</code> and <code>shutdown()</code>.
 */
public abstract class Service {
    private Bot          bot;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    
    public Bot getBot() {
        return this.bot;
    }
    
    void setBot(final Bot bot) {
        this.bot = bot;
    }
    
    public Logger getLogger() {
        return this.logger;
    }
    
    public abstract void enable();
    
    public abstract void disable();
    
    public boolean isAsynchronous() {
        return true;
    }
}
