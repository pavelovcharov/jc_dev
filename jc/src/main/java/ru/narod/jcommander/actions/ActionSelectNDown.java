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

import ru.narod.jcommander.fileSystem.BaseFile;
import ru.narod.jcommander.gui.MainFrame;
import ru.narod.jcommander.gui.table.FileTable;
import ru.narod.jcommander.gui.table.tableElements.NameInterface;
import ru.narod.jcommander.gui.table.tableElements.UpperDirectory;

/**
 *
 * @author Programmer
 */
public class ActionSelectNDown extends AbstractAction {

    public ActionSelectNDown(MainFrame parent) {
        super(parent);
    }

    public void execute() {
        FileTable activeTable = parent.getActiveTable();
        NameInterface name = activeTable.getElementAtCursor();
        if (!(name instanceof UpperDirectory)) {
            BaseFile currentFile = name.getFile();
            if (null == currentFile) {
                return;
            }
            activeTable.selectFileAt(activeTable.getCurrentPosition());
        }

        activeTable.moveCursorDown();
        parent.updateActiveStatusLabel();
    }
}
