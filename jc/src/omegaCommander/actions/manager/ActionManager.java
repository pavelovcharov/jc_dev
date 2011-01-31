/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package omegaCommander.actions.manager;

import omegaCommander.fileSystem.BaseFile;
import omegaCommander.gui.MainFrame;
import omegaCommander.gui.dialog.ProgressDialog;
import omegaCommander.gui.table.FileTable;
import omegaCommander.gui.table.tableElements.NameInterface;
import omegaCommander.gui.table.tableElements.UpperDirectory;
import omegaCommander.threads.newThreads.NewMoveThread;
import omegaCommander.threads.newThreads.ProgressThread;

/**
 *
 * @author master
 */
public class ActionManager {

    private MainFrame owner;

    public ActionManager(MainFrame owner) {
        this.owner = owner;
    }

    public void selectCurrentFile(FileTable table) {
        if (!table.hasSelectedFiles()) {
            NameInterface name = table.getElementAtCursor();
            if (true == (name instanceof UpperDirectory)) {
                return;
            }
            table.selectCurrentFile();
            table.repaint();
            owner.updateActiveStatusLabel();
        }
    }

    void transferAction(BaseFile source, BaseFile target, BaseFile[] filesToCopy, boolean toCopy) {
        if (null != filesToCopy) {
            omegaCommander.gui.dialog.CopyDialog cd = new omegaCommander.gui.dialog.CopyDialog(owner, source, target, filesToCopy, toCopy);
            String targetPath = cd.getNewTargetString();
            if (targetPath != null && !targetPath.isEmpty()) {
                NewMoveThread nmt = new NewMoveThread(source, targetPath, filesToCopy, toCopy);
                ProgressDialog pd = new ProgressDialog(owner, nmt);
                ProgressThread pt = new ProgressThread(nmt, pd);
                nmt.setFrameParent(pd.getDialog());
                nmt.start();
                pt.start();
                pd.show();
            }
        }
    }

    public void copyAction(BaseFile source, BaseFile target, BaseFile[] filesToCopy) {
        transferAction(source, target, filesToCopy, true);
    }

    public void moveAction(BaseFile source, BaseFile target, BaseFile[] filesToMove) {
        transferAction(source, target, filesToMove, false);
    }
    public  void clearSelection(FileTable table) {
        table.clearSelectedList();
    }
}
