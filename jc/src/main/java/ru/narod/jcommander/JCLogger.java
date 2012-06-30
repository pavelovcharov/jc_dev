/*
 * This file is part of jc, http://www.jcommander.narod.ru
 * Copyright (C) 2005-2010 Pavel Ovcharov
 *
 * jc is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package ru.narod.jcommander;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * The JCLogger class provides logging facilities to the jc application using
 * the
 * <code>java.util.logging</code> API.
 *
 * @author Pavel Ovcharov
 */
public class JCLogger {

    private final static Logger logger;

    static {
        logger = Logger.getLogger(JCLogger.class.getName());
        FileHandler fh;
        try {
            fh = new FileHandler("jc.log", Integer.MAX_VALUE, 1, true);
            logger.addHandler(fh);
            logger.setLevel(Level.ALL);
            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);

        } catch (IOException ex) {
            Logger.getLogger(Launcher.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(Launcher.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns the
     * <code>java.util.logging.Logger</code> instance used by this class to log
     * records.
     *
     * @return The Logger instance that this class uses to log
     */
    public static Logger getLogger() {
        return logger;
    }

    /**
     * Log a message, with associated Throwable information. If the logger is currently enabled for
     * the SEVERE message level then the given message is forwarded to all the
     * registered output Handler objects.
     *
     * @param message The string message (or a key in the message catalog)
     * @param thrown Throwable associated with log message
     */
    public static void logSevere(String message, Throwable thrown) {
        logger.log(Level.SEVERE, message, thrown);
    }
    /**
     * Log a message, with associated Throwable information. If the logger is currently enabled for
     * the WARNING message level then the given message is forwarded to all the
     * registered output Handler objects.
     *
     * @param message The string message (or a key in the message catalog)
     * @param thrown Throwable associated with log message
     */
    public static void logWarning(String message, Throwable thrown) {
        logger.log(Level.WARNING, message, thrown);
    }
}
