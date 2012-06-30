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
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.narod.jcommander.util;

import java.awt.Desktop;
import java.io.IOException;
import ru.narod.jcommander.fileSystem.BaseFile;

/**
 * The PlatformHelper class provides access to the the native desktop
 * functions.
 * Supported operations include: launching a registered application to
 * open.
 *
 * @author master
 */
public class PlatformHelper {

    static Desktop desktop;

    static {
        if (Desktop.isDesktopSupported()) {
            desktop = Desktop.getDesktop();
        }
    }

    /**
     * Launches the associated application to open the file. If the specified
     * file is a directory, the file manager of the current platform is launched
     * to open it.
     *
     * @param file - the file to be opened with associated application
     * @throws IOException - if file can not be opened
     */
    public static void openFile(BaseFile file) throws IOException {
        if (desktop != null) {
            desktop.open(file.toFile());
        }
    }
}
