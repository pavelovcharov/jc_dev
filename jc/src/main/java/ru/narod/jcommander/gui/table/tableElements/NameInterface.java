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
 * NameInterface.java
 *
 * Created on 15 oct 2006, 11:42
 */
package ru.narod.jcommander.gui.table.tableElements;

/**
 *
 * @author Pavel Ovcharov
 */
public interface NameInterface {

    @Override
    public String toString();

    public String getName();

    public String getExtention();

    public String getFileName();

    @Override
    public boolean equals(Object obj);

    public ru.narod.jcommander.fileSystem.BaseFile getFile();
}
