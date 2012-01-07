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
 * ColorRenderer.java
 *
 * Created on 6 ������� 2006 �., 9:01
 *
 */
package ru.narod.jcommander.gui.table;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import ru.narod.jcommander.gui.table.tableElements.Element;

/**
 * ����� ������������ ��� ����������� ����������� ������� ���������� ������� 
 * � ����������� �� �������� ��������
 * @author Pavel Ovcharov
 */
public class ColorRenderer extends DefaultTableCellRenderer {

    /**
     * ������� ����� ��������� ������ ColorRenderer
     */
    public ColorRenderer() {
        super();
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        if (value instanceof Element) {
            if (((Element) value).isSelected()) {
                setForeground(Color.RED);
            } else {
                if (!isSelected) {
                    setForeground(Color.BLACK);
                }
            }
        }
        return c;
    }
}
