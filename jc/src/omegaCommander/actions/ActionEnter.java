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
 * ActionEnter.java
 * Created on 11.01.2009 14:26:36
 */
package omegaCommander.actions;

import java.awt.Cursor;
import omegaCommander.fileSystem.BaseFile;
import omegaCommander.fileSystem.archive.ArchiveFile;
import omegaCommander.fileSystem.archive.MyZipFile;
import omegaCommander.fileSystem.FileSystemList;
import omegaCommander.fileSystem.LocalFile;
import omegaCommander.fileSystem.FileHelper;
import omegaCommander.fileSystem.archive.MyTarFile;
import omegaCommander.fileSystem.archive.MyTgzFile;
import omegaCommander.gui.MainFrame;
import omegaCommander.gui.dialog.ProgressDialog;
import omegaCommander.gui.dialog.WarningDialog;
import omegaCommander.gui.table.FileTable;
import omegaCommander.gui.table.tableElements.Directory;
import omegaCommander.gui.table.tableElements.NameInterface;
import omegaCommander.gui.table.tableElements.UpperDirectory;
import omegaCommander.threads.newThreads.NewMoveThread;
import omegaCommander.threads.newThreads.ProgressThread;
import omegaCommander.util.LanguageBundle;
import omegaCommander.util.PlatformHelper;

/**
 *
 * @author Programmer
 */
public class ActionEnter extends AbstractAction {

    public ActionEnter(MainFrame parent) {
        super(parent);
    }

    public void execute() {
        Cursor c = parent.getCursor();
        parent.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

        FileTable activeTable = parent.getActiveTable();
        NameInterface name = activeTable.getElementAtCursor();
        BaseFile currentFile;
        if (null == name) {
            return;
        }
        currentFile = name.getFile();
        if (null == currentFile) {
            return;
        }
        int index = 0;
        if (true == (name instanceof UpperDirectory)) {
            BaseFile file = activeTable.getCurrentDir();
            activeTable.setCurrentDir(currentFile);
            activeTable.refreshTable();
            index = activeTable.getFilePosition(file);
        } else {
            if (true == (name instanceof Directory)) {
                activeTable.setCurrentDir(currentFile);
            } else {
                if (FileHelper.FileType.ARCHIVE == FileHelper.getFileType(currentFile)) {
                    if (currentFile instanceof ArchiveFile) {
                        currentFile = copyToTemp(currentFile);
                        currentFile = FileHelper.getRealFile((ArchiveFile) activeTable.getCurrentDir(),
                                new LocalFile((LocalFile) currentFile.getAbsoluteParent(), ((LocalFile) currentFile).getFilename()));
                        activeTable.setCurrentDir(currentFile);
                    } else {
                        String filename = currentFile.getFilename().toLowerCase();
                        if (filename.endsWith("zip") || filename.endsWith("jar")) {
                            activeTable.setCurrentDir(new MyZipFile(currentFile));
                        }
                        if (filename.endsWith("tar")) {
                            activeTable.setCurrentDir(new MyTarFile(currentFile));
                        }
                        if (filename.endsWith("tgz") || filename.endsWith("tar.gz")) {
                            activeTable.setCurrentDir(new MyTgzFile(currentFile));
                        }
                    }
                } else {
                    //executing currentFile
                    index = activeTable.getCurrentPosition();
                    if (currentFile instanceof ArchiveFile) {
                        currentFile = copyToTemp(currentFile);
                        currentFile = new LocalFile((LocalFile) currentFile.getAbsoluteParent(), ((LocalFile) currentFile).getFilename());
                    }
                    executeFile(currentFile);
                }
            }
        }
        if(activeTable.getCurrentDir() == currentFile)
            activeTable.setCurrentPosition(index);
        activeTable.clearSelectedList();
        parent.updateActivePanel();

        parent.setCursor(c);
    }

    private void enterArchive(BaseFile archive) {
    }

    private BaseFile copyToTemp(BaseFile file) {
        LocalFile tempDir = FileSystemList.getTempDir();
        if (null == tempDir) {
            //XXX query error or use another place
            return null;
        }
        BaseFile newTarget = null;
        BaseFile[] filesToCopy = new BaseFile[]{file};
        if (null != filesToCopy) {
            omegaCommander.gui.dialog.CopyDialog cd = new omegaCommander.gui.dialog.CopyDialog(parent, file.getAbsoluteParent(), tempDir, filesToCopy, true);
            String targetPath = cd.getNewTargetString();
            if (null != targetPath && !targetPath.isEmpty()) {
                NewMoveThread nmt = new NewMoveThread(file.getAbsoluteParent(), targetPath, filesToCopy, true);
                ProgressDialog pd = new ProgressDialog(parent, nmt, true);
                ProgressThread pt = new ProgressThread(nmt, pd);
                nmt.setFrameParent(pd.getDialog());
                nmt.start();
                pt.start();
                pd.show();

                newTarget = FileHelper.getRealFile(targetPath);
                if (newTarget.isDirectory()) {
                    newTarget = FileHelper.getRealFile(newTarget, file.getFilename());
                }
            }
        }
        return newTarget;
    }

    private void executeFile(BaseFile file) {
        String command = PlatformHelper.DEFAULT_HANDLER_TEXT + " " + file;
        try {
            if (true == file.exists()) {
                java.io.File workingDir = null;
                if (file.getAbsoluteParent() instanceof java.io.File) {
                    workingDir = (java.io.File) file.getAbsoluteParent();
                }
                Runtime.getRuntime().exec(command, null, workingDir);
            } else {
                throw new IllegalArgumentException();
            }
        } catch (Exception e) {
            LanguageBundle lb = LanguageBundle.getInstance();
            WarningDialog.showMessage(parent, lb.getString("StrErrorExecute") + "\n" + command,
                    lb.getString("StrJC"), WarningDialog.MESSAGE_ERROR);
        }
    }
}
