/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.narod.jcommander.actions;

import com.sun.jna.platform.FileUtils;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import ru.narod.jcommander.fileSystem.BaseFile;
import ru.narod.jcommander.gui.MainFrame;

/**
 *
 * @author master
 */
public class ActionMoveToTrash extends ActionDelete {

    static FileUtils instance = FileUtils.getInstance();

    public ActionMoveToTrash(MainFrame parent) {
        super(parent);
    }

    @Override
    protected void deleteCore(BaseFile[] filesToDelete) {
        if (instance.hasTrash()) {
            moveToTrash(filesToDelete);
        } else {
            super.deleteCore(filesToDelete);
        }
    }

    @Override
    protected boolean canMoveToTrash() {
        return instance != null ? instance.hasTrash() : super.canMoveToTrash();
    }

    private void moveToTrash(BaseFile[] files) {
        ArrayList<File> list = new ArrayList<File>();
        for (BaseFile baseFile : files) {
            File local = baseFile.toFile();
            if (local != null) {
                list.add(local);
            }
        }
        File[] f = new File[list.size()];
        try {
            instance.moveToTrash(list.toArray(f));
        } catch (IOException ex) {
            Logger.getLogger(ActionMoveToTrash.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
