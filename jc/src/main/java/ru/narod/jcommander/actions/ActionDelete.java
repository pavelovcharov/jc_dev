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
import ru.narod.jcommander.gui.dialog.DeleteDialog;
import ru.narod.jcommander.gui.dialog.ProgressDialog;
import ru.narod.jcommander.gui.table.FileTable;
import ru.narod.jcommander.gui.table.tableElements.NameInterface;
import ru.narod.jcommander.gui.table.tableElements.UpperDirectory;
import ru.narod.jcommander.threads.newThreads.NewDeleteThread;
import ru.narod.jcommander.threads.newThreads.ProgressThread;

/**
 *
 * @author Programmer
 */
public class ActionDelete extends AbstractAction {

    public ActionDelete(MainFrame parent) {
        super(parent);
    }

    public void execute() {
        FileTable activeTable = parent.getActiveTable();
        NameInterface name = activeTable.getElementAtCursor();
        BaseFile currentFile;
        if (null != name) {
            currentFile = name.getFile();
        } else {
            currentFile = null;
        }
        if (null == currentFile) {
            return;
        }
        if (!activeTable.hasSelectedFiles()) {
            if ((name instanceof UpperDirectory)) {
                return;
            }
            activeTable.selectFileAt(activeTable.getCurrentPosition());
            activeTable.repaint();
            parent.updateActiveStatusLabel();
        }
        BaseFile[] filesToDelete = activeTable.getActiveFiles();
        if (DeleteDialog.showDeleteDialog(parent, filesToDelete, canMoveToTrash())) {
            deleteCore(filesToDelete);
            parent.updateMainWindow();
        }
        parent.requestFocus();
    }

    protected void deleteCore(BaseFile[] filesToDelete) {
        NewDeleteThread ndt = new NewDeleteThread(filesToDelete);
        ProgressDialog pd = new ProgressDialog(parent, ndt);
        ProgressThread pt = new ProgressThread(ndt, pd);
        ndt.setFrameParent(pd.getDialog());
        ndt.start();
        pt.start();
        pd.show();
    }

    protected boolean canMoveToTrash() {
        return false;
    }
}
