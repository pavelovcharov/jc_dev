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
 * Element.java
 *
 * Created on 15 ������� 2006 �., 11:28
 */

package ru.narod.jcommander.gui.table.tableElements;

import java.awt.Color;

/**
 * ����� �������� ���� � ������, ����� ��� ���� ��������� �������
 * @author Pavel Ovcharov
 */
public abstract class Element implements Comparable{
    
    protected Color fontColor;
    protected Color bgColor;
    protected boolean isSelected = false;
    
    public Element() {
    }
    /**
     * ������� ����� ��������� ������ Element � �������� ������ ���� � ������
     * @param fontColor ���� ������
     * @param bgColor ���� ����
     */
    public Element(Color fontColor, Color bgColor) {
        this.fontColor = fontColor;
        this.bgColor = bgColor;
    }
    /**
     * �������� ���� ������ ��� �������� �������
     * @return ���� ������
     */
    public Color getFontColor() {
        return fontColor;
    }
    /**
     * �������� ���� ���� ��� �������� �������
     * @return ���� ����
     */
    public Color getBackgroundColor() {
        return bgColor;
    }
    /**
     * ���������, �������� �� ������ ������� ����������
     * @return <B>true</B>, ���� ������� �������� ����������, ����� - 
     * <B>false</B>
     */
    public boolean isSelected() {
        return isSelected;
    }
    /**
     * �������� ������� ��� ����� ���������
     * @param isSelected <B>true</B> ��� ��������� ��������, <B>false</B>
     */
    public void setSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }
}
