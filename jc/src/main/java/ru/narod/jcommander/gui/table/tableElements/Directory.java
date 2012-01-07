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
 * Directory.java
 *
 * Created on 15 ������� 2006 �., 11:39
 */

package ru.narod.jcommander.gui.table.tableElements;

import java.awt.Color;
import ru.narod.jcommander.fileSystem.BaseFile;

/**
 * ����� ����������� ������� ������� FileTable - ������� �������� �������
 * @author Pavel Ovcharov
 */
public class Directory extends Name {
 
    //private BaseFile aFile;
    //private String dirName;

    private String DIR_CHARACTER_LEFT = "";
    private String DIR_CHARACTER_RIGHT = "";
    
    /**
     * ������� ����� ��������� ������ Directory ��� �������� aFile. ������� ����� 
     * ������������ � ������� � ����������� � ��������� ������� <I>fontColor</I> � 
     * <I>bgColor</I>
     * @param aFile ������� �������� �������
     * @param fontColor ���� ������
     * @param bgColor ���� ����
     */
    public Directory(BaseFile aFile, Color fontColor, Color bgColor) {
        super(aFile, fontColor, bgColor);
        //this.dirName = aFile.getAbstractFileName();
        //this.aFile = aFile;
    }
    
    /**
     * ������� ����� ��������� ������ Directory ��� �������� aFile. ������� ����� 
     * ������������ � ������� � ����������� � ��������� ������� <I>fontColor</I> � 
     * <I>bgColor</I>
     * @param aFile ������� �������� �������
     */
    public Directory(BaseFile aFile) {
        super(aFile);
        //this.dirName = aFile.getAbstractFileName();
        //this.aFile = aFile;
    }
    /**
     * �������� ��������� ������������� �������
     * @return ��������� �������������
     */
	@Override
    public String toString() {
        return DIR_CHARACTER_LEFT + name + DIR_CHARACTER_RIGHT;
    }

    /**
     * ���������� ��������� �� ��� ������� - ������ � <I>obj</I>
     * @param obj ������������ ������
     * @return <B>true</B>, ���� ������� ���������, ����� - <B>false</B>
     */
	@Override
    public boolean equals(Object obj) {
        return (obj instanceof Directory)?(getName().equals(((Directory)obj).getName())):false;
    }
   
}
