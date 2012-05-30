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
package ru.narod.jcommander.gui.dialog;

import javax.swing.JOptionPane;
import ru.narod.jcommander.fileSystem.BaseFile;
import ru.narod.jcommander.gui.MainFrame;
import ru.narod.jcommander.prefs.PrefKeys;
import ru.narod.jcommander.util.LanguageBundle;

/**
 *
 * @author Programmer
 */
public class DeleteDialog implements PrefKeys {

    private static int filesToShowCount = 3;

    public static boolean showDeleteDialog(MainFrame parent, BaseFile[] filesToDelete, boolean moveToTrash) {

        if (null == filesToDelete) {
            return false;
        }

        LanguageBundle lb = LanguageBundle.getInstance();
        String deleteString = moveToTrash ? lb.getString("StrMoveToTrash") : lb.getString("StrDeleteFiles");
        String messageText = "<html><P>" + deleteString + "<br><br>";
        for (int i = 0; i < filesToDelete.length; i++) {
            messageText += filesToDelete[i].getFilename() + "<br>";
            if (i == filesToShowCount && i != filesToDelete.length - 1) {
                messageText += "....<br>";
                messageText += lb.getString("StrTotalFilesDirs") + " " + filesToDelete.length + "<br>";
                break;
            }
        }

        messageText += "</P></html>";
        Object[] message = new Object[]{messageText};

        Object[] options = new Object[]{lb.getString("StrOk"), lb.getString("StrCancel")};
        //XXX use Warning Dialog
        return 0 == JOptionPane.showOptionDialog(parent, message, lb.getString("StrJC"), JOptionPane.PLAIN_MESSAGE, JOptionPane.WARNING_MESSAGE, null, options, options[0]);
    }
}
