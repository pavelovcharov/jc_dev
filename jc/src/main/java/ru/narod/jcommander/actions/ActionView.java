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

import java.util.ArrayList;
import ru.narod.jcommander.fileSystem.BaseFile;
import ru.narod.jcommander.fileSystem.archive.MyZipFile;
import ru.narod.jcommander.fileSystem.FileSystemList;
import ru.narod.jcommander.fileSystem.LocalFile;
import ru.narod.jcommander.fileSystem.FileHelper;
import ru.narod.jcommander.gui.MainFrame;
import ru.narod.jcommander.gui.dialog.ProgressDialog;
import ru.narod.jcommander.gui.table.FileTable;
import ru.narod.jcommander.threads.newThreads.NewMoveThread;
import ru.narod.jcommander.threads.newThreads.ProgressThread;

/**
 *
 * @author Programmer
 */
public class ActionView extends AbstractAction {

    public ActionView(MainFrame parent) {
        super(parent);
    }

    @Override
    public void execute() {
        FileTable activeTable = parent.getActiveTable();
        BaseFile currentFile = activeTable.getFileAtCursor();
        if (currentFile.isDirectory()) {
            return;
        }
        if (currentFile instanceof MyZipFile) {
            ArrayList list = new ArrayList();
            list.add(currentFile);
            LocalFile tempDir = FileSystemList.getTempDir();
            if (null == tempDir) {
                //XXX query error ��� ������������ �����-�� ������ ����, ����. C:
                return;
            }
//			if (CopyDialog.STATUS_OK != CopyDialog.showDialog(parent, activeTable.getCurrentDir(), tempDir, list, true))
//				return;
            BaseFile[] filesToCopy = new BaseFile[]{currentFile};
            if (null != filesToCopy) {
                ru.narod.jcommander.gui.dialog.CopyDialog cd = new ru.narod.jcommander.gui.dialog.CopyDialog(parent, activeTable.getCurrentDir(), tempDir, filesToCopy, true);
                //XXX maybe use any function
                String targetPath = cd.getNewTargetString();
                if (null != targetPath && !targetPath.isEmpty()) {
                    NewMoveThread nmt = new NewMoveThread(activeTable.getCurrentDir(), targetPath, filesToCopy, true);
                    ProgressDialog pd = new ProgressDialog(parent, nmt, true);
                    ProgressThread pt = new ProgressThread(nmt, pd);
                    nmt.setFrameParent(pd.getDialog());
                    nmt.start();
                    pt.start();
                    pd.show();

                    BaseFile target = FileHelper.getRealFile(targetPath);
                    if (target.isDirectory()) {
                        currentFile = FileHelper.getRealFile(target, currentFile.getFilename());
                    }
                    currentFile = target;

                } else {
                    return;
                }
            }
        }
        //XXX image viewer
        //String extention = currentFile.getExtention();
        //if (extention.equalsIgnoreCase("bmp") || extention.equalsIgnoreCase("jpg") || extention.equalsIgnoreCase("gif")) {
        if (FileHelper.getFileType(currentFile) == FileHelper.FileType.IMAGE) {
            if (null == iv) {
                iv = new ru.narod.jcommander.editor.ImageViewer(parent);
            }
            iv.openFile(currentFile);
            return;
        }

        ru.narod.jcommander.editor.Editor editor = new ru.narod.jcommander.editor.Editor(parent);
        editor.openFile(currentFile, false);
    }
    private ru.narod.jcommander.editor.ImageViewer iv = null;
}
