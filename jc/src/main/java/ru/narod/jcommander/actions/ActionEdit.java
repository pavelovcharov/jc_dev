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

/**
 *
 * @author Programmer
 */
public class ActionEdit extends AbstractAction {

	public ActionEdit(MainFrame parent) {
		super(parent);
	}

	public void execute() {
		BaseFile currentFile = parent.getActiveTable().getFileAtCursor();
		if (currentFile.isDirectory()) {
			return;
		}
//		if (parent.getExternEditor()) {
//			String externEditorString = parent.getExternEditorString();
//			if (!externEditorString.trim().equals("")) {
//				try {
//					Runtime.getRuntime().exec(externEditorString + " " + currentFile);
//				} catch (IOException exc) {
//					System.out.println("Can't execute " + externEditorString + " " + currentFile);
//				}
//			}
//		}
//		else {
//			ru.narod.jcommander.editor.Editor editor = new ru.narod.jcommander.editor.Editor(parent);
//			editor.openFile(currentFile, true);
//		}
		parent.runFileInEditor(currentFile);
	}

}
