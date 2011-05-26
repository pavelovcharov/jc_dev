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

/*
 * Main.java
 *
 * Created on 26 јпрель 2005 г., 9:26
 */
package omegaCommander;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import omegaCommander.gui.MainFrame;

/**
 * jc start class
 * @author Pavel Ovcharov
 * @version 2005/04/26 9:26
 */
public class Launcher {

    /**
     * Launch application main window
     * @param args Command line parameters. Not used for now.
     * @see MainFrame
     */
    public static void main(String[] args) {

        Logger logger = Logger.getLogger(MainFrame.class.getName());
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

        String javaVersion = System.getProperty("java.version");
        MainFrame.run("jc", "v.0.659" + " @ Java " + javaVersion);
    }
}
