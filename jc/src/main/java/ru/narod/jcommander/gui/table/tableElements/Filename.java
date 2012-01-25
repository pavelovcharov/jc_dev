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
 * Filename.java
 *
 * Created on 15 oct 2006, 11:47
 */
package ru.narod.jcommander.gui.table.tableElements;

import java.awt.Color;
import ru.narod.jcommander.fileSystem.BaseFile;

/**
 *
 * @author Pavel Ovcharov
 */
public class Filename extends Name {

    /** Creates a new instance of Filename */
    public Filename(BaseFile aFile, Color fontColor, Color bgColor) {
        super(aFile, fontColor, bgColor);
    }

    public Filename(BaseFile aFile) {
        super(aFile);
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof Filename) ? (fileName.equals(((Filename) obj).fileName)) : false;
    }
}
