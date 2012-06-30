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
package ru.narod.jcommander.actions;

import java.awt.Cursor;
import ru.narod.jcommander.fileSystem.BaseFile;
import ru.narod.jcommander.gui.MainFrame;
import ru.narod.jcommander.gui.table.FileTable;
import ru.narod.jcommander.gui.table.tableElements.Name;
import ru.narod.jcommander.gui.table.tableElements.UpperDirectory;

/**
 *
 * @author Programmer
 */
public class ActionCalcSize extends AbstractAction {

    public ActionCalcSize(MainFrame parent) {
        super(parent);
    }

    public void execute() {
        Cursor c = parent.getCursor();
        parent.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

        FileTable activeTable = parent.getActiveTable();
        Name name = (Name) activeTable.getElementAtCursor();
        BaseFile currentFile;
        if (null != name) {
            currentFile = name.getFile();
        } else {
            currentFile = null;
        }
        if (null == currentFile) {
            return;
        }
        if (!(name instanceof UpperDirectory)) {
            if (!name.isSelected()) {
                activeTable.showFileSize();
            }
            activeTable.selectFileAt(activeTable.getCurrentPosition());
            activeTable.repaint();
            parent.updateActiveStatusLabel();
        }
        parent.setCursor(c);
    }
}
