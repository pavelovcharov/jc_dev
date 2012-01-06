/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package omegaCommander.fileSystem.utils;

import omegaCommander.fileSystem.BaseFile;

/**
 *
 * @author master
 */
public class FileMonitor {

    private long lastModified;

    public FileMonitor(BaseFile file) {
        boolean exists = file.exists();
        lastModified = (exists ? file.getLastModifiedTime() : 0);
    }

    public boolean refresh(BaseFile file) {
        long origLastModified = lastModified;
        boolean exists = file.exists();
        lastModified = (exists ? file.getLastModifiedTime() : 0);
        return lastModified != origLastModified;
    }
}
