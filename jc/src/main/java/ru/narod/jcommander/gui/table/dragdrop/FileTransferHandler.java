/*
 * FileTransferHandler.java
 *
 * Created on 26 ���� 2007 �., 8:31
 *
 */
package ru.narod.jcommander.gui.table.dragdrop;

import java.awt.Component;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.File;
import java.io.IOException;
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
        } catch (UnsupportedFlavorException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        /*
        source = (JTextArea)c;
        int start = source.getSelectionStart();
        int end = source.getSelectionEnd();
        Document doc = source.getDocument();
        if (start == end) {
        return null;
        }
        try {
        p0 = doc.createPosition(start);
        p1 = doc.createPosition(end);
        } catch (BadLocationException e) {
        System.out.println(
        "Can't create position - unable to remove text from source.");
        }
        shouldRemove = true;
        String data = source.getSelectedText();

        return new StringSelection(data);
         */
        //return null;
        /*
        Transferable t = DataFlavor.javaFileListFlavor;
        java.util.List files =
        (java.util.List)t.getTransferData(fileFlavor);
        files.add(new File("c:/1.txt"));
         */
        /*
        Transferable t = new Transferable() {
        public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException,IOException {
        DataFlavor.javaFileListFlavor.
        }
        public DataFlavor[] getTransferDataFlavors() {
        }
        public boolean isDataFlavorSupported(DataFlavor flavor) {
        }
        }SeleDataFlavor.javaFileListFlavor
        return t;
         */

        return null;
    }

    @Override
    public int getSourceActions(JComponent c) {
        super.getSourceActions(c);
        return COPY | MOVE | LINK;
    }

    //Remove the old text if the action is a MOVE.
    //However, we do not allow dropping on top of the selected text,
    //so in that case do nothing.
    @Override
    protected void exportDone(JComponent c, Transferable data, int action) {
//        if (shouldRemove && (action == MOVE)) {
//            if ((p0 != null) && (p1 != null)
//                    && (p0.getOffset() != p1.getOffset())) {
//                try {
//                    JTextComponent tc = (JTextComponent) c;
//                    tc.getDocument().remove(
//                            p0.getOffset(), p1.getOffset() - p0.getOffset());
//                } catch (BadLocationException e) {
//                    System.out.println("Can't remove text from source.");
//                }
//            }
//        }
//        source = null;
    }

//    @Override
//    public boolean canImport(JComponent c, DataFlavor[] flavors) {
//        if (hasFileFlavor(flavors)) {
//            return true;
//        }
//        if (hasStringFlavor(flavors)) {
//            return true;
//        }
//        return false;
//    }
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
