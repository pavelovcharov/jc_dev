/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.narod.jcommander.fileSystem.net;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import ru.narod.jcommander.fileSystem.BaseFile;

/**
 *
 * @author master
 */
public class MyFtp implements BaseFile {

    public MyFtp() {
        
    }

    public String getAbstractFileName() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String getExtention() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public BaseFile[] getFiles() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public BaseFile[] getFiles(FileFilter filter) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public BaseFile getAbsoluteParent() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean isDirectory() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String getFormatFileSize() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String getLastModifiedDate() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String getAtributeString() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean hasParent() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean exists() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String getAbsolutePath() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public BaseFile getRoot() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public InputStream getInputStream() throws IOException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean delete() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public OutputStream getOutputStream() throws IOException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean setLastModified(long time) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String getPathWithSlash() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean mkdirs() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean renameTo(BaseFile targetFile) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean canRead() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean createNewFile() throws IOException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public long length() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String getFilename() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public long getLastModifiedTime() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean isHidden() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean isLocal() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public File toFile() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
