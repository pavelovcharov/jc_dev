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
package omegaCommander.actions;

import omegaCommander.fileSystem.BaseFile;
import omegaCommander.gui.MainFrame;
import omegaCommander.gui.dialog.ProgressDialog;
import omegaCommander.gui.table.FileTable;
import omegaCommander.gui.table.tableElements.NameInterface;
import omegaCommander.gui.table.tableElements.UpperDirectory;
import omegaCommander.threads.newThreads.NewMoveThread;
import omegaCommander.threads.newThreads.ProgressThread;

/**
 *
 * @author Programmer
 */
public class ActionCopy extends AbstractAction {

    public ActionCopy(MainFrame parent) {
        super(parent);
    }

    public void execute() {
        FileTable activeTable, passiveTable;
        activeTable = parent.getActiveTable();
        passiveTable = parent.getPassiveTable();
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
            if (true == (name instanceof UpperDirectory)) {
                return;
            }
            activeTable.selectFileAt(activeTable.getCurrentPosition());
            activeTable.repaint();
            parent.updateActiveStatusLabel();
        }

        BaseFile[] filesToCopy = activeTable.getActiveFiles();
        if (null != filesToCopy) {
            omegaCommander.gui.dialog.CopyDialog cd = new omegaCommander.gui.dialog.CopyDialog(parent, activeTable.getCurrentDir(), passiveTable.getCurrentDir(), filesToCopy, true);
            String targetPath = cd.getNewTargetString();
            if (targetPath != null && !targetPath.isEmpty()) {
                NewMoveThread nmt = new NewMoveThread(activeTable.getCurrentDir(), targetPath, filesToCopy, true);
                ProgressDialog pd = new ProgressDialog(parent, nmt);
                ProgressThread pt = new ProgressThread(nmt, pd);
                nmt.setFrameParent(pd.getDialog());
                nmt.start();
                pt.start();
                pd.show();
            }
        }
        return;
    }
}
