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
 * CopyDialog.java
 * Created on 23.01.2009 10:50:09
 */
package omegaCommander.gui.dialog;

import java.awt.Dimension;
import javax.swing.JTextField;
import omegaCommander.fileSystem.AbsoluteFile;
import omegaCommander.fileSystem.LocalFile;
import omegaCommander.fileSystem.SuperFile;
import omegaCommander.gui.MainFrame;
import omegaCommander.prefs.PrefKeys;
import omegaCommander.util.LanguageBundle;

/**
 *
 * @author Programmer
 */
public class CopyDialog implements PrefKeys {

    private boolean toCopy;
    private AbsoluteFile newTarget;

    public CopyDialog(MainFrame parent, AbsoluteFile sourceDir, AbsoluteFile targerDir, AbsoluteFile[] filesToCopy, boolean toCopy) {
        //XXX зачем нам sourceDir и filesToCopy просто возвращаем новую строку, а создаем для нее файл не в диалоге
        this.toCopy = toCopy;

        LanguageBundle lb = LanguageBundle.getInstance();

        String messageText;
        if (toCopy) {
            messageText = lb.getString("StrToCopy");
        } else {
            messageText = lb.getString("StrToMove");
        }
        JTextField jTextFieldPath = new JTextField();
        if (filesToCopy.length > 1) {
            messageText += " " + filesToCopy.length + " " + lb.getString("StrManyFilesTo") + " " + lb.getString("StrTo");
            jTextFieldPath.setText(targerDir.getPathWithSlash());
        } else {
            messageText += " " + filesToCopy[0].getFilename() + " " + lb.getString("StrTo");
            if (filesToCopy[0].isDirectory()) {
                jTextFieldPath.setText(targerDir.getPathWithSlash() + filesToCopy[0].getFilename() + LocalFile.separator);
            } else {
                jTextFieldPath.setText(targerDir.getPathWithSlash() + filesToCopy[0].getFilename());
            }
            jTextFieldPath.setPreferredSize(new Dimension(400, 18));
        }

        Object message[] = new Object[]{
            messageText,
            jTextFieldPath
        };
        Object options[] = new Object[]{lb.getString("StrOk"), lb.getString("StrCancel")};

        BaseDialog bd = new BaseDialog(parent, lb.getString("StrJC"), message, options, 0);

        jTextFieldPath.selectAll();
        jTextFieldPath.requestFocus();


        bd.setVisible(true);

//		System.out.println("result = " + bd.getResult());
        if (0 == bd.getResult()) {//OK
        } else {
            newTarget = null;
            return;
        }
        String newPath = jTextFieldPath.getText();
//		System.out.println(newPath);

//		AbsoluteFile newTarget = targerDir;
        newTarget = targerDir;
        if (newPath.startsWith("smb://")) {
        } else {
            if (SuperFile.isAbsolutePath(newPath)) {
                newTarget = SuperFile.getRealFile(newPath);
            } else {
                if (!newPath.isEmpty())
                    newTarget = SuperFile.getRealFile(sourceDir, newPath);
                else {
                    if (filesToCopy.length == 1)
                        newTarget = filesToCopy[0];
                }
            }
        }

//		System.out.println(newTarget.getAbsolutePath());

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
}
