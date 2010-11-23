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
 * InputDialog.java
 * Created on 30.03.2009 9:47:37
 */
package omegaCommander.gui.dialog;

import javax.swing.JTextField;
import omegaCommander.gui.MainFrame;
import omegaCommander.prefs.PrefKeys;
import omegaCommander.util.LanguageBundle;

/**
 *
 * @author Programmer
 */
public class InputDialog implements PrefKeys {

	//
	static public String showInputDialog(MainFrame parent, String msgText, String initialString) {

		JTextField jTextFieldInput = new JTextField(initialString);
		jTextFieldInput.selectAll();
		
		Object message[] = new Object[]{
			msgText,
			jTextFieldInput,};
		
		LanguageBundle lb = LanguageBundle.getInstance();

		Object options[] = new Object[]{lb.getString("StrOk"), lb.getString("StrCancel")};

		BaseDialog bd = new BaseDialog(parent, lb.getString("StrJC"), message, options, 0);
//		bd.setSize(300, 110);

//		bd.setIcon(new JLabel(ImageArchive.getLogo()));

		jTextFieldInput.requestFocus();
		bd.setVisible(true);

		if (0 == bd.getResult())
			return jTextFieldInput.getText();
		return null;

//		String newPath = jTextFieldPath.getText();


//		omegaCommander.threads.MovingThread mt =
//				new omegaCommander.threads.MovingThread(sourceDir, sourceList, toCopy);
//		mt.setTargetDirString(targetDir.getAbsolutePath());
//
//		okButton.addActionListener(new CopyButtonListener(mt, okButton, parent, this));
//		okButton.addKeyListener(new ButtonKeyListener(okButton, mt));
//		jTextFieldPath.addKeyListener(new PathFieldKeyListener(this, okButton));
//		addWindowListener(new DialogListener(parent, mt));
	}

}
