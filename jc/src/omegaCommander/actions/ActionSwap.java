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
import omegaCommander.gui.table.FileTable;

/**
 *
 * @author master
 */
public class ActionSwap extends AbstractAction {

    public ActionSwap(MainFrame parent) {
        super(parent);
    }

    @Override
    public void execute() {
        FileTable leftTable = parent.getTable(true);
        FileTable rightTable = parent.getTable(false);

        BaseFile left = leftTable.getCurrentDir();
        BaseFile right = rightTable.getCurrentDir();

        int leftPos = leftTable.getCurrentPosition();
        int rightPos = rightTable.getCurrentPosition();


        leftTable.setFileList(right);
        rightTable.setFileList(left);

        leftTable.setCurrentPosition(rightPos);
        rightTable.setCurrentPosition(leftPos);

        parent.updateMainWindow();

    }
}
