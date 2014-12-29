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

import javax.annotation.Nonnull;

/**
 * Represents creditnals used to logging in to ServerQuery.
 */
public class QueryCreditnals {
    private final String username;
    private final String password;
    
    /**
     * <p>
     * Creates new instance of {@link QueryCreditnals} with specified user name and password.
     * </p>
     * 
     * @param username
     *            username to use
     * @param password
     *            password to use
     */
    public QueryCreditnals(@Nonnull final String username, @Nonnull final String password) {
        this.username = username;
        this.password = password;
    }
    
    /**
     * Returns username from this creditnals.
     * 
     * @return username
     */
    public String getUsername() {
        return this.username;
    }
    
    /**
     * Returns password from this creditnals.
     * 
     * @return password
     */
    public String getPassword() {
        return this.password;
    }
    
    @Override
    public String toString() {
        return "QueryCreditnals [username=" + this.username + ", password="
                + this.password + "]";
    }
}
