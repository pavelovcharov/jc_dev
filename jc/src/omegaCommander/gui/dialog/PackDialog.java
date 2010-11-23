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
 * PackDialog.java
 * Created on 06.04.2009 10:50:22
 */

package omegaCommander.gui.dialog;

import java.awt.GridLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import omegaCommander.fileSystem.AbsoluteFile;
import omegaCommander.fileSystem.SuperFile;
import omegaCommander.gui.MainFrame;
import omegaCommander.prefs.PrefKeys;
import omegaCommander.util.LanguageBundle;

/**
 *
 * @author Programmer
 */
public class PackDialog implements PrefKeys {

	private AbsoluteFile newTarget;
	private int packLevel;
	public static String DEFAULT_EXTENTION = "zip";
	public static String DEFAULT_ARCHIVE_NAME = "archive";

	public PackDialog(MainFrame parent, AbsoluteFile sourceDir, AbsoluteFile targerDir, AbsoluteFile[] filesToPack) {
		//XXX зачем нам sourceDir и filesToCopy просто возвращаем новую строку, а создаем для нее файл не в диалоге

		LanguageBundle lb = LanguageBundle.getInstance();

		String messageText = lb.getString("StrArchive");
		JTextField jTextFieldPath = new JTextField();
		String archiveName;
		if (filesToPack.length > 1) {
			messageText += " " + filesToPack.length + " " + lb.getString("StrManyFilesTo");
			if (sourceDir.hasParent()) {
				archiveName = sourceDir.getFilename();
			} else {
				archiveName = DEFAULT_ARCHIVE_NAME;
			}
		} else {
			messageText += " " + filesToPack[0].getFilename();
			archiveName = filesToPack[0].getAbstractFileName();
		}
		jTextFieldPath.setText(targerDir.getPathWithSlash() + archiveName + "." + DEFAULT_EXTENTION);

		JPanel panel = new JPanel();
		JComboBox jComboBoxLevel = new JComboBox(new Object[]{lb.getString("StrNoCompression"),
		lb.getString("StrFast"), lb.getString("StrNormal"), lb.getString("StrMax")});

		jComboBoxLevel.setSelectedItem(lb.getString("StrMax"));

		panel.setLayout(new GridLayout(1, 2, 10, 0));

		JLabel l = new JLabel(lb.getString("StrLevel"));
//		l.setFont(new java.awt.Font(null/*"Dialog"*/, 0, 12));
//		jComboBoxLevel.setFont(new java.awt.Font(null/*"Dialog"*/, 0, 12));

		panel.add(l);
		panel.add(jComboBoxLevel);


		Object message[] = new Object[]{
			messageText,
			jTextFieldPath,
			panel,
		};
		Object options[] = new Object[]{lb.getString("StrOk"), lb.getString("StrCancel")};

		BaseDialog bd = new BaseDialog(parent, lb.getString("StrJC"), message, options, 0);

		String path = jTextFieldPath.getText();
        jTextFieldPath.select(path.length() - archiveName.length() -
                DEFAULT_EXTENTION.length() - 1, path.length()-DEFAULT_EXTENTION.length() - 1);
		jTextFieldPath.requestFocus();

		bd.setVisible(true);

		if (0 == bd.getResult()) {//OK

		} else {
			newTarget = null;
			return;
		}


		String newPath = jTextFieldPath.getText();

		newTarget = targerDir;
		if (newPath.startsWith("smb://")) {
		}
		else {
			if (SuperFile.isAbsolutePath(newPath)) {
				newTarget = SuperFile.getRealFile(newPath);
			}
			else {
				newTarget = SuperFile.getRealFile(sourceDir, newPath);
			}
		}

		packLevel = jComboBoxLevel.getSelectedIndex();

//		NewMoveThread nmt = new NewMoveThread(sourceDir, newTarget, filesToCopy, toCopy);
//		ProgressDialog pd = new ProgressDialog(parent, nmt);
//		ProgressThread pt = new ProgressThread(nmt, pd);
//		nmt.setFrameParent(pd.getDialog());
//		nmt.start();
//		pt.start();
//		pd.show();


//		omegaCommander.threads.MovingThread mt =
//				new omegaCommander.threads.MovingThread(sourceDir, sourceList, toCopy);
//		mt.setTargetDirString(targetDir.getAbsolutePath());
//
//		okButton.addActionListener(new CopyButtonListener(mt, okButton, parent, this));
//		okButton.addKeyListener(new ButtonKeyListener(okButton, mt));
//		jTextFieldPath.addKeyListener(new PathFieldKeyListener(this, okButton));
//		addWindowListener(new DialogListener(parent, mt));
	}

	public AbsoluteFile getNewTarget() {
		return newTarget;
	}

	public int getPackLevel() {
		return packLevel;
	}
}
