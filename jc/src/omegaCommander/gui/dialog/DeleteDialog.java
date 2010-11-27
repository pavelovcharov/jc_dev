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
 * DeleteDialog.java
 * Created on 31.03.2009 16:25:29
 */

package omegaCommander.gui.dialog;

import javax.swing.JOptionPane;
import omegaCommander.fileSystem.BaseFile;
import omegaCommander.gui.MainFrame;
import omegaCommander.prefs.PrefKeys;
import omegaCommander.util.LanguageBundle;

/**
 *
 * @author Programmer
 */
public class DeleteDialog implements PrefKeys {

	private static int filesToShowCount = 3;

	//	public DeleteDialog(MainFrame parent, BaseFile[] filesToDelete) {
//		String messageText = "<html><P>Выбранные файлы/каталоги будут удалены<br><br>";
//		if (filesToShowCount < filesToDelete.length) {
//
//		}
//		for (int i = 0; i < filesToShowCount; i++) {
//			messageText += filesToDelete[i].getFilename() + "<br>";
//		}
//		messageText += "....<br>";
//		messageText += "</P></html>";
//		Object[] message = new Object[]{messageText};
//
//		Object[] options = new Object[] {"OK", "Cancel"};
//
//		BaseDialog bd = new BaseDialog(parent, "jc", message, options, 0);
//		bd.setSize(350, 200);
//		bd.setIcon(new JLabel(ImageArchive.getImageFile()));
//		bd.setVisible(true);
//
//	}

	public static boolean showDeleteDialog(MainFrame parent, BaseFile[] filesToDelete) {
		
		if (null == filesToDelete) return false;

		LanguageBundle lb = LanguageBundle.getInstance();

		String messageText = "<html><P>" + lb.getString("StrDeleteFiles") + "<br><br>";
//		if (filesToShowCount < filesToDelete.length) {
//
//		}
		for (int i = 0; i < filesToDelete.length; i++) {
			messageText += filesToDelete[i].getFilename() + "<br>";
			if (i == filesToShowCount && i != filesToDelete.length-1) {
				messageText += "....<br>";
				messageText += lb.getString("StrTotalFilesDirs")+ " " + filesToDelete.length + "<br>";
				break;
			}
		}
//		messageText += "....<br>";
		
		messageText += "</P></html>";
		Object[] message = new Object[]{messageText};

		Object[] options = new Object[] {lb.getString("StrOk"), lb.getString("StrCancel")};
		//XXX use Warning Dialog
		if (0 == JOptionPane.showOptionDialog(parent, message, lb.getString("StrJC"), JOptionPane.PLAIN_MESSAGE, JOptionPane.WARNING_MESSAGE, null, options, options[0])) {
			return true;
		}
		else
			return false;
	}
}
