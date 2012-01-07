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
 * WarningDialog.java
 * Created on 14.04.2009 10:05:36
 */

package ru.narod.jcommander.gui.dialog;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import ru.narod.jcommander.util.LanguageBundle;

/**
 *
 * @author Programmer
 */
public class WarningDialog {

	public static final int MESSAGE_ERROR = JOptionPane.ERROR_MESSAGE;
	public static final int MESSAGE_WARNING = JOptionPane.WARNING_MESSAGE;
        public static  final int MESSAGE_QUESTION = JOptionPane.QUESTION_MESSAGE;

	public static  int showMessage(JFrame parent, Object message, String title, int messageType) {
		return javax.swing.JOptionPane.showOptionDialog(parent, message, title, JOptionPane.DEFAULT_OPTION, messageType, null,
				new Object[]{LanguageBundle.getInstance().getString("StrOk")}, LanguageBundle.getInstance().getString("StrOk"));
	}

	/**
	 *
	 * @param message
	 * @param title
	 * @param options
	 * @param option
	 * @param messageType
	 * @param index
	 * @return
	 */
	public static int showMessage(Object message, String title, Object options[], int messageType, int index) {
		return javax.swing.JOptionPane.showOptionDialog(null, message, title, JOptionPane.DEFAULT_OPTION, messageType, null, options, options[index]);
	}

	public static int showMessage(JFrame parent, Object message, String title, Object options[], int messageType, int index) {
		return javax.swing.JOptionPane.showOptionDialog(parent, message, title, JOptionPane.DEFAULT_OPTION, messageType, null, options, options[index]);
	}
	
	public static  int showMessage(JDialog parent, Object message, String title, Object options[], int messageType, int index) {
		return javax.swing.JOptionPane.showOptionDialog(parent, message, title, JOptionPane.DEFAULT_OPTION, messageType, null, options, options[index]);
	}

}
