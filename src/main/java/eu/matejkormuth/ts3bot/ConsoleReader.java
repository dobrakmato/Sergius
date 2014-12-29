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

import java.util.Scanner;

/**
 *
 */
public class ConsoleReader extends Service {
    private boolean isRunning = true;
    private Scanner scanner;
    
    @Override
    public void enable() {
        this.scanner = new Scanner(System.in);
        this.read();
    }
    
    @Override
    public void disable() {
        this.isRunning = false;
        this.scanner.close();
    }
    
    private void read() {
        while (this.isRunning) {
            System.out.append('>').flush();
            String line = this.scanner.nextLine();
            this.parseCommand(line);
        }
    }
    
    private void parseCommand(final String line) {
        if (line.length() > 0) {
            String[] strs = line.split("\\s");
            String[] args = new String[strs.length - 1];
            System.arraycopy(strs, 0, args, 0, args.length);
            this.executeCommand(strs[0], strs);
        }
    }
    
    private void executeCommand(final String command, final String... args) {
        if (command.equalsIgnoreCase("stop")) {
            this.command_stop();
        }
        else {
            this.getLogger().warn("Command '{}' does not exists!", command);
        }
    }
    
    private void command_stop() {
        this.isRunning = false;
        this.getBot().shutdown();
    }
}
