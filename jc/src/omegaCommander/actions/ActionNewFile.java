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
import omegaCommander.fileSystem.FileHelper;
import omegaCommander.gui.MainFrame;
import omegaCommander.gui.dialog.InputDialog;
import omegaCommander.gui.dialog.WarningDialog;
import omegaCommander.util.LanguageBundle;

/**
 *
 * @author Programmer
 */
public class ActionNewFile extends AbstractAction {

    public ActionNewFile(MainFrame parent) {
        super(parent);
    }

    @Override
    public void execute() {

        BaseFile activeFile = parent.getActiveTable().getFileAtCursor();

        String newDir;
        if (activeFile.getFilename().equals("")) {
            newDir = "New file";
        } else {
            newDir = activeFile.getFilename();
        }

        LanguageBundle lb = LanguageBundle.getInstance();

        newDir = InputDialog.showInputDialog(parent, lb.getString("StrCreateNewFile"), newDir);
        if (null == newDir) //cancel pressed
        {
            return;
        }
        BaseFile result = FileHelper.getRealFile(parent.getActiveTable().getCurrentDir(), newDir);

        boolean res;
        try {
            res = result.createNewFile();

        } catch (Exception e) {
            res = false;
        }

        if (!res) {
            if (result.exists() && !result.isDirectory()) {
            } else {
                WarningDialog.showMessage(parent, lb.getString("StrCreateError") + " " + result,
                        lb.getString("StrError"), new Object[]{lb.getString("StrOk")}, WarningDialog.MESSAGE_ERROR, 0);
                //XXX return from this
            }
        }
        if (null != result) {
            parent.runFileInEditor(result);
        }
        parent.updateMainWindow();
        parent.getActiveTable().moveToFile(result);
    }
}
