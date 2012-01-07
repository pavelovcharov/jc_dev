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
 * UpperDirectory.java
 *
 * Created on 15 ������� 2006 �., 11:58
 */

package ru.narod.jcommander.gui.table.tableElements;

import java.awt.Color;
import ru.narod.jcommander.fileSystem.BaseFile;
/**
 *
 * @author Pavel Ovcharov
 */
public class UpperDirectory extends Name{

    private String UPPER_DIRECTORY_DEFAULT_NAME = "..";
    /** Creates a new instance of UpperDirectory */

    public UpperDirectory(BaseFile aFile, Color fontColor, Color bgColor) {
        super(aFile, fontColor, bgColor);
    }
    
    public UpperDirectory(BaseFile aFile) {
        super(aFile);
    }
    
	@Override
    public String toString() {
        return UPPER_DIRECTORY_DEFAULT_NAME;
    }
	@Override
    public void setSelected(boolean isSelected) {
        isSelected = false;
    }
/*
    public String getName() {
        return UPPER_DIRECTORY_DEFAULT_NAME;
    }

    public String getExtention() {
        return "";
    }

    public String getFileName() {
        return UPPER_DIRECTORY_DEFAULT_NAME;
    }

    public BaseFile getFile() {
        return aFile;
    }
 */
}
