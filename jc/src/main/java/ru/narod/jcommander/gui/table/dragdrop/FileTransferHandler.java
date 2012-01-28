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
 * FileTransferHandler.java
 *
 * Created on 26 jun 2007 г., 8:31
 *
 */
package ru.narod.jcommander.gui.table.dragdrop;

import java.awt.Component;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.TransferHandler;
import ru.narod.jcommander.fileSystem.BaseFile;
import ru.narod.jcommander.fileSystem.LocalFile;
import ru.narod.jcommander.gui.MainFrame;
import ru.narod.jcommander.gui.dialog.ProgressDialog;
import ru.narod.jcommander.gui.dialog.WarningDialog;
import ru.narod.jcommander.gui.table.FileTable;
import ru.narod.jcommander.gui.table.FileTablePanel;
import ru.narod.jcommander.threads.newThreads.NewMoveThread;
import ru.narod.jcommander.threads.newThreads.ProgressThread;
import ru.narod.jcommander.util.LanguageBundle;

/**
 *
 * @author Programmer
 * @version
 */
public class FileTransferHandler extends TransferHandler {

    MainFrame parent;
    private DataFlavor fileFlavor, stringFlavor;

    public FileTransferHandler(MainFrame pareFrame) {
        fileFlavor = DataFlavor.javaFileListFlavor;
        stringFlavor = DataFlavor.stringFlavor;
        parent = pareFrame;
    }

    @Override
    public boolean canImport(TransferSupport support) {
        if (!support.isDrop()) {
            return false;
        }
        if (!support.isDataFlavorSupported(fileFlavor)) {
            return false;
        }
        return true;
    }

    @Override
    public boolean importData(TransferSupport support) {

        Component target = support.getComponent();

        if (!canImport(support)) {
            return false;
        }

        int action = support.getDropAction();

        BaseFile currentDir = null;

        if (target instanceof FileTable) {
            JTable.DropLocation dl = (JTable.DropLocation) support.getDropLocation();
            FileTable table = (FileTable) target;

            BaseFile targetFile = table.getFileAt(dl.getRow());

            currentDir = targetFile.isDirectory() ? targetFile : table.getCurrentDir();

            table.setCurrentPosition(dl.getRow());
        }
        if (target instanceof FileTablePanel) {
            currentDir = ((FileTablePanel) target).getFileTable().getCurrentDir();
        }
        if (currentDir == null) {
            return false;
        }

        try {
            if (hasFileFlavor(support.getDataFlavors())) {
                java.util.List files =
                        (java.util.List) support.getTransferable().getTransferData(fileFlavor);
                BaseFile[] f = new BaseFile[files.size()];

                for (int i = 0; i < files.size(); i++) {
                    File file = (File) files.get(i);
                    f[i] = new LocalFile(file);
                }

                if (null != files) {
                    LanguageBundle lb = LanguageBundle.getInstance();

                    String messageText = action == COPY ? LanguageBundle.getInstance().getString("StrCopyFilesTo")
                            : LanguageBundle.getInstance().getString("StrMoveFilesTo");
                    messageText = String.format(messageText, f.length, currentDir.getAbsolutePath());

                    String[] options = {lb.getString("StrOk"), lb.getString("StrCancel")};
                    int res = WarningDialog.showMessage(parent, messageText, lb.getString("StrJC"), options,
                            WarningDialog.MESSAGE_QUESTION, 0);
                    if (res == 0) {
                        String path = currentDir.getPathWithSlash();
                        if (f.length == 1 && f[0].isDirectory()) {
                            path += f[0].getFilename();
                        }
                        NewMoveThread nmt = new NewMoveThread(new LocalFile(), path, f, action == COPY);
                        ProgressDialog pd = new ProgressDialog(parent, nmt, true);
                        ProgressThread pt = new ProgressThread(nmt, pd);
                        nmt.setFrameParent(pd.getDialog());
                        nmt.start();
                        pt.start();
                        pd.show();
                    }
                }
                return true;
            }
        } catch (UnsupportedFlavorException ufe) {
        } catch (IOException ieo) {
        }
        return false;
    }

    @Override
    protected Transferable createTransferable(JComponent c) {
        System.out.println("create transferable");
        Transferable t = (Transferable) DataFlavor.javaFileListFlavor;
        java.util.List files;
        try {
            files = (java.util.List) t.getTransferData(fileFlavor);
            files.add(new File("c:/1.txt"));
        } catch (Exception ex) {
            Logger.getLogger(FileTransferHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public int getSourceActions(JComponent c) {
        super.getSourceActions(c);
        return COPY | MOVE | LINK;
    }

    @Override
    protected void exportDone(JComponent c, Transferable data, int action) {
    }

    private boolean hasFileFlavor(DataFlavor[] flavors) {
        for (int i = 0; i < flavors.length; i++) {
            if (fileFlavor.equals(flavors[i])) {
                return true;
            }
        }
        return false;
    }

    private boolean hasStringFlavor(DataFlavor[] flavors) {
        for (int i = 0; i < flavors.length; i++) {
            if (stringFlavor.equals(flavors[i])) {
                return true;
            }
        }
        return false;
    }
}
