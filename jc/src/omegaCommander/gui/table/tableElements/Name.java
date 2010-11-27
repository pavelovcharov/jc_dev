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
 * Name.java
 *
 * Created on 25 Июнь 2007 г., 8:44
 *
 */

package omegaCommander.gui.table.tableElements;

import java.awt.Color;

import omegaCommander.fileSystem.BaseFile;

/**
 *
 * @author Programmer
 * @version
 */
abstract public class Name extends Element implements NameInterface{
    
    protected BaseFile aFile;
    /**
     * Имя файла (без расширения)
     */
    protected String name;  
    /**
     * Полное имя файла
     */
    protected String fileName;
    protected String extention;
            
    /** Creates a new instance of Name */
    public Name(BaseFile aFile, Color fontColor, Color bgColor) {
        super(fontColor, bgColor);
        this.aFile = aFile;
        name = aFile.getAbstractFileName();
        fileName = aFile.getFilename();
        extention = aFile.getExtention();
    }
    
    public Name(BaseFile aFile) {
        this(aFile, null, null);
    }
    
    /**
     * Получить файл, связанный с данным объектом
     * @return файл связанный с данным объектом
     */
    public BaseFile getFile() {
        return aFile;
    }

    public String getExtention() {
        return extention;
    }

    public String getFileName() {
        return fileName;
    }
    /**
     * Получить имя каталога
     * @return имя каталога, соответствующего данному объекту
     */
    public String getName() {
        return name;
    }

    public int compareTo(Object obj) {
        if (false == (obj instanceof Name)) return 1;
        Name nm = (Name)obj;
        return name.compareTo(nm.name);
    }
    

}
