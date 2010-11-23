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

import omegaCommander.fileSystem.AbsoluteFile;
import omegaCommander.gui.MainFrame;
//import omegaCommander.gui.actions.DeleteDialog;
import omegaCommander.gui.dialog.DeleteDialog;
import omegaCommander.gui.table.FileTable;
import omegaCommander.gui.table.tableElements.NameInterface;
import omegaCommander.gui.table.tableElements.UpperDirectory;
//import omegaCommander.threads.DeletingThread;
import omegaCommander.threads.newThreads.NewDeleteThread;
import omegaCommander.threads.newThreads.ProgressThread;
import omegaCommander.gui.dialog.ProgressDialog;
//import omegaCommander.threads.ThreadStatus;

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
//		FileTable passiveTable = parent.getPassiveTable();
		NameInterface name = activeTable.getElementAtCursor();
		AbsoluteFile currentFile;
		if (null != name) {
			currentFile = name.getFile();
		} else {
			currentFile = null;
		}
		if (null == currentFile) {
			return;
		}
//		if (activeTable.getSelectedList().size() < 1) {
		if (!activeTable.hasSelectedFiles()) {
			if (true == (name instanceof UpperDirectory)) {
				return;
			}
			activeTable.selectFileAt(activeTable.getCurrentPosition());
			activeTable.repaint();
			parent.updateActiveStatusLabel();
		}

//		DeleteDialog dd = new DeleteDialog(parent, activeTable.getActiveFiles());
		AbsoluteFile[] filesToDelete = activeTable.getActiveFiles();
		if (DeleteDialog.showDeleteDialog(parent, filesToDelete)) {
			NewDeleteThread ndt = new NewDeleteThread(filesToDelete);
			ProgressDialog pd = new ProgressDialog(parent, ndt);
			ProgressThread pt = new ProgressThread(ndt, pd);
			ndt.setFrameParent(pd.getDialog());
			ndt.start();
			pt.start();
			pd.show();
		}


//		DeleteDialog dd = new DeleteDialog(parent, activeTable.getSelectedList());
//		if (dd.getResultStatus() == ThreadStatus.THREAD_IS_NOT_RUNNING) {
//			return;
//		}



		parent.updateMainWindow();
		parent.requestFocus();
	}
}
