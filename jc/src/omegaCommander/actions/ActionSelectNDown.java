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
import omegaCommander.gui.table.FileTable;
import omegaCommander.gui.table.tableElements.NameInterface;
import omegaCommander.gui.table.tableElements.UpperDirectory;

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
		if ((name instanceof UpperDirectory) == false) {
			AbsoluteFile currentFile = name.getFile();
			if (null == currentFile) {
				return;
			}
			activeTable.selectFileAt(activeTable.getCurrentPosition());
		}

//		activeTableIndex = activeTable.getCurrentPosition() + 1;
//		if (activeTableIndex == activeTable.getRowCount() - 1) {
//			activeTableIndex = activeTable.getRowCount() - 1;
//		}
//		activeTable.changeSelection(activeTableIndex, FileTable.NAME, false, false);
		activeTable.moveCursorDown();
		parent.updateActiveStatusLabel();
	}


}
