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
import omegaCommander.fileSystem.SuperFile;
import omegaCommander.gui.MainFrame;
import omegaCommander.gui.dialog.InputDialog;
import omegaCommander.gui.dialog.WarningDialog;
import omegaCommander.util.LanguageBundle;

/**
 *
 * @author Programmer
 */
public class ActionNewDir extends AbstractAction {

	static private String NEW_DIR_NAME = "New dir";

	public ActionNewDir(MainFrame parent) {
		super(parent);
	}

	public void execute() {
		AbsoluteFile activeFile = parent.getActiveTable().getFileAtCursor();

		String newDir;
		if (activeFile.getFilename().equals("")) {
			newDir = NEW_DIR_NAME;
		} else {
			newDir = activeFile.getFilename();
		}

		LanguageBundle lb = LanguageBundle.getInstance();

		//newDir = InputDialog.showAnotherDialog(parent, "Create new file", newDir);
		newDir = InputDialog.showInputDialog(parent, lb.getString("StrCreateNewDir"), newDir);
		if (null == newDir) //cancel pressed
		{
			return;
		}
		AbsoluteFile result = SuperFile.getRealFile(parent.getActiveTable().getCurrentDir(), newDir);

		boolean res = result.mkdirs();
		parent.updateMainWindow();
		if (!res) {
			WarningDialog.showMessage(parent, lb.getString("StrCreateError") + " " + result,
					lb.getString("StrError"), new Object[] {lb.getString("StrOk")}, WarningDialog.MESSAGE_ERROR, 0);
		}
		else
			parent.getActiveTable().moveToFile(result);
//		if (activeTable.getCurrentDir().equals(passiveTable.getCurrentDir())) {
//			parent.updateMainWindow();
//		} else {
//			parent.updateActivePanel();
//		}
	}
}
