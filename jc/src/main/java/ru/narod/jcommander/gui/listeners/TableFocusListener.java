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

package ru.narod.jcommander.gui.listeners;

import java.awt.Color;
import java.awt.event.FocusListener;
import java.awt.event.FocusEvent;

import ru.narod.jcommander.gui.MainFrame;
import ru.narod.jcommander.gui.table.FileTable;

/**
 * Класс описывает действия, выполняемые при изменении фокуса таблицы
 * @author  Sniper
 * @version 2005/05/02 8:36:04 PM
 */
public class TableFocusListener implements FocusListener{
    private FileTable left;
    private FileTable right;
    MainFrame parent;
    Color activeColor = Color.GRAY;
    Color passiveColor = Color.LIGHT_GRAY;
    /*
     * Создает объект класса TableFocusListener
     */
    public TableFocusListener(MainFrame parent){
        //this.left=left;
        //this.right=right;
        this.parent = parent;
    }
    /**
     * Метод выполняется, когда таблица получает фокус
     */
    public void focusGained(FocusEvent e) {
        this.left = parent.getTable(true);
        this.right = parent.getTable(false);
        if (left.hasFocus()){
            left.setActive(true);
            right.setActive(false);
            left.setSelectionBackground(activeColor);
            right.setSelectionBackground(passiveColor);
        }
        else{
            left.setActive(false);
            right.setActive(true);
            left.setSelectionBackground(passiveColor);
            right.setSelectionBackground(activeColor);
        }
/*
        FileTable table = (FileTable)(e.getSource());
        table.setActive(true);
        table.setSelectionBackground(Color.gray);
        if (e.getOppositeComponent() instanceof FileTable) {
            FileTable otherTable = (FileTable) e.getOppositeComponent();
            otherTable.setActive(false);
            otherTable.setSelectionBackground(Color.lightGray);
        }
*/
    }
    /**
     * Метод выполняется, когда таблица теряет фокус
     */
    public void focusLost(FocusEvent e) {
  /*      FileTable table = (FileTable)(e.getSource());
        table.setActive(false);
        table.setSelectionBackground(Color.lightGray);
   */
    }
}
