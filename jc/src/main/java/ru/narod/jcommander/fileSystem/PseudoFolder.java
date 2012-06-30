/*
 * This file is part of jc, http://www.jcommander.narod.ru
 * Copyright (C) 2005-2011 Pavel Ovcharov
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
package ru.narod.jcommander.fileSystem;

import java.io.*;

/**
 *
 * @author master
 */
public class PseudoFolder implements BaseFile {

    BaseFile[] files;
    BaseFile parent;

    public PseudoFolder(BaseFile parent, BaseFile[] files) {
        this.files = files;
        this.parent = parent;
    }

    public String getName() {
        return parent.getName();
    }

    public String getExtention() {
        return null;
    }

    public BaseFile[] getFiles() {
        return files;
    }

    public BaseFile[] getFiles(FileFilter filter) {
        return files;
    }

    public BaseFile getAbsoluteParent() {
        return parent;
    }

    public boolean isDirectory() {
        return true;
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
        return parent != null;
    }

    public boolean exists() {
        return parent != null;
    }

    public String getAbsolutePath() {
        return parent.getAbsolutePath();
    }

    public BaseFile getRoot() {
        return parent.getRoot();
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
        return "";
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
        return null;
    }

    public long getLastModifiedTime() {
        return parent != null ? parent.getLastModifiedTime() : 0;
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
