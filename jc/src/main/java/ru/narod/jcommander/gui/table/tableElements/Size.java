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
 * Size.java
 *
 * Created on 25 jun 2007, 9:59
 *
 */

package ru.narod.jcommander.gui.table.tableElements;

import ru.narod.jcommander.fileSystem.BaseFile;
import ru.narod.jcommander.fileSystem.FileSystemList;

/**
 *
 * @author Programmer
 * @version
 */
public class Size extends Element{
    
    private String sizeStr;
    long size = 0;
    static protected String DEFAULT_DIR_SIZE = "<DIR>";
    
    /** Creates a new instance of Size */
    public Size(BaseFile aFile) {
        if (aFile.isDirectory())
            sizeStr = DEFAULT_DIR_SIZE;
        else {
            size = aFile.length();
            sizeStr = FileSystemList.getFormatedSize(size);
        }
    }
    
    public Size(long size) {
        this.size = size;
        sizeStr = FileSystemList.getFormatedSize(size);
    }
    
	@Override
    public String toString() {
        return sizeStr;
    }

    public int compareTo(Object o) {
        if (false == (o instanceof Size)) return 1;
        Size s = (Size)o;
        if (size> s.size) return 1;
        if (size == s.size) return 0;
        return -1;
    }
    
    public long getSize() {
        return size;
    }
    
}
