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
 * FileHelper.java
 *
 * Created on 24 nov 2006, 20:41
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package ru.narod.jcommander.fileSystem;

import ru.narod.jcommander.fileSystem.archive.*;
import ru.narod.jcommander.fileSystem.net.NetFile;

/**
 * Абстрактный класс, унаследованный от java.io.File, реализует общие методы,
 * необходимые всем типам файлов
 * @author Strateg
 */
public abstract class FileHelper {

    /**
     * Получить файл (локальный, файл в архиве, сети) по папке <I>parent</I>  и имени
     * файла <I>child</I>
     * @param parent задает путь к файлу
     * @param child задает имя файла
     * @return искомый файл
     */
    //XXX если файл в архиве?
    public static BaseFile getRealFile(BaseFile parent, String child) {
        if (parent instanceof NetFile) {
            return new NetFile(((NetFile) parent).getPathWithSlash() + child);
        }
        if (parent instanceof MyZipFile) {
            //XXX здесь была строка
            //af = new MyZipFile((MyZipFile) parent, child);
            return new MyZipFile((MyZipFile) parent, child);
        }
        if (parent instanceof LocalFile) {
            return new LocalFile((LocalFile) parent, child);
        }
        return null;
    }

    /**
     * Получить файл (локальный, файл в архиве, сети) по пути <I>path</I>
     * @param path путь к файлу
     * @return искомый файл
     */
    public static BaseFile getRealFile(String path) {
        if (path.toLowerCase().startsWith("smb://")) {
            NetFile nf = new NetFile(path);
            //XXX do something with smb
            if (!path.equals("smb://")) {
                nf.setParent(new NetFile("smb://"));
            }
            return nf;
        } else {
            if (path.equals("")) {
                return RootFileSystem.getReadableRoot();
            }
            return new LocalFile(path);
        }
    }

    public static ArchiveFile getRealFile(ArchiveFile parent, LocalFile archive) {
        String filename = archive.getFilename().toLowerCase();
        if (filename.endsWith("zip") || filename.endsWith("jar")) {
            return new MyZipFile(parent, archive);
        }
        if (filename.endsWith("tar")) {
            return new MyTarFile(parent, archive);
        }
        if (filename.endsWith("tar.gz") || filename.endsWith("tgz")) {
            return new MyTgzFile(parent, archive);
        }

        return null;
    }

    public static boolean isAbsolutePath(String path) {
        if (path.isEmpty()) {
            return false;
        }
        if (path.startsWith("smb://")) {
            return true;
        }
        LocalFile f = (LocalFile) FileHelper.getRealFile(path);
        return f.isAbsolute();
    }

    public enum FileType {

        DIRECTORY, UNKNOWN_FILE, ARCHIVE, IMAGE,
    };

    static public FileType getFileType(BaseFile file) {
        if (file.isDirectory()) {
            return FileType.DIRECTORY;
        }
        String filename = file.getFilename().toLowerCase();
        return  getFileType(filename);
    }

    static public FileType getFileType(String filename) {
        if (filename.endsWith("zip") || filename.endsWith("tar") || filename.endsWith("tgz") || filename.endsWith("tar.gz") || filename.endsWith("jar")) {
            return FileType.ARCHIVE;
        }
        if (filename.endsWith("png") || filename.endsWith("jpg") || filename.endsWith("jpeg") || filename.endsWith("gif") || filename.endsWith("bmp")) {
            return FileType.IMAGE;
        }
        return FileType.UNKNOWN_FILE;
    }
    static public boolean isLocal(BaseFile file) {
        return file.isLocal();
    }
}
