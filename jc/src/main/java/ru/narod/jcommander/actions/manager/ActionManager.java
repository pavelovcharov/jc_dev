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
package ru.narod.jcommander.actions.manager;

import ru.narod.jcommander.fileSystem.BaseFile;
import ru.narod.jcommander.gui.MainFrame;
import ru.narod.jcommander.gui.dialog.ProgressDialog;
import ru.narod.jcommander.gui.table.FileTable;
import ru.narod.jcommander.gui.table.tableElements.NameInterface;
import ru.narod.jcommander.gui.table.tableElements.UpperDirectory;
import ru.narod.jcommander.threads.newThreads.NewMoveThread;
import ru.narod.jcommander.threads.newThreads.ProgressThread;

/**
 *
 * @author master
 */
public class ActionManager {

    private MainFrame owner;

    public ActionManager(MainFrame owner) {
        this.owner = owner;
    }

    public void selectCurrentFile(FileTable table) {
        if (!table.hasSelectedFiles()) {
            NameInterface name = table.getElementAtCursor();
            if (true == (name instanceof UpperDirectory)) {
                return;
            }
            table.selectCurrentFile();
            table.repaint();
            owner.updateActiveStatusLabel();
        }
    }

    void transferAction(BaseFile source, BaseFile target, BaseFile[] filesToCopy, boolean toCopy) {
        if (null != filesToCopy) {
            ru.narod.jcommander.gui.dialog.CopyDialog cd = new ru.narod.jcommander.gui.dialog.CopyDialog(owner, source, target, filesToCopy, toCopy);
            String targetPath = cd.getNewTargetString();
            if (targetPath != null && !targetPath.isEmpty()) {
                NewMoveThread nmt = new NewMoveThread(source, targetPath, filesToCopy, toCopy);
                ProgressDialog pd = new ProgressDialog(owner, nmt);
                ProgressThread pt = new ProgressThread(nmt, pd);
                nmt.setFrameParent(pd.getDialog());
                nmt.start();
                pt.start();
                pd.show();
            }
        }
    }

    public void copyAction(BaseFile source, BaseFile target, BaseFile[] filesToCopy) {
        transferAction(source, target, filesToCopy, true);
    }

    public void moveAction(BaseFile source, BaseFile target, BaseFile[] filesToMove) {
        transferAction(source, target, filesToMove, false);
    }

    public void clearSelection(FileTable table) {
        table.clearSelectedList();
    }
}
