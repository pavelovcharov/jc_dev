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
 * ActionCopyPath.java
 * Created on 11.01.2009 14:08:10
 */

package ru.narod.jcommander.actions;

import javax.swing.JTextField;
import ru.narod.jcommander.fileSystem.BaseFile;
import ru.narod.jcommander.gui.MainFrame;
import ru.narod.jcommander.gui.table.tableElements.NameInterface;

/**
 *
 * @author Programmer
 */
public class ActionCopyPath extends AbstractAction {

	public ActionCopyPath(MainFrame parent) {
		super(parent);
	}

	public void execute() {
		NameInterface name = parent.getActiveTable().getElementAtCursor();
		BaseFile currentFile;
		if (null == name) {
			return;
		} else {
			currentFile = name.getFile();
		}
		if (null == currentFile) {
			return;
		}
		JTextField cmdLine = parent.getCmdLine();
		cmdLine.setText(cmdLine.getText() + " " + currentFile.getAbsolutePath() + " ");
	}
}
