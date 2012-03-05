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
package ru.narod.jcommander.fileSystem;

import java.io.FileFilter;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Iterator;
import ru.narod.jcommander.fileSystem.archive.ArchiveFile;
import ru.narod.jcommander.gui.dialog.WarningDialog;
import ru.narod.jcommander.util.LanguageBundle;

/**
 * Класс предоставляет методы для работы с фаловой системой. Файловая система
 * задается текущим каталогом и списком файлов и подкаталогов в нем
 *
 * @author Programmer
 * @version 2005/04/24 1:32:27 PM
 */
public class FileSystemList {

    private BaseFile currentDir;
    private boolean hasParent;
    private ArrayList dirList;
    private ArrayList fileList;
    private BaseFile[] fsList;
    FileFilter fileFilter = null;
    /**
     * Задает путь к временной папке приложения
     */
    public static final String PATH_TO_TEMP =
            System.getProperty("java.io.tmpdir") + LocalFile.separator + "$jcomtemp";

    /**
     * Создает файловую систему с текущим каталогом <b>abstractFile</b>
     *
     * @param file текущий каталог файловой системы
     */
    public FileSystemList() {
    }

    /**
     * Задает текущий каталог
     *
     * @param file задает текущий каталог файловой системы
     */
    //XXX посмотреть как это работает. разобраться с рекурсиями и return'ами
    public void setFileList(BaseFile file) {
        //XXX запускать загрузку файлов в отдельном потоке

        if (file == null) {
            setFileList(RootFileSystem.getReadableRoot());
            return;
        }
        if (!file.isDirectory() && !(file instanceof ArchiveFile)) {
            setFileList(file.getAbsoluteParent());
            return;
        }
        this.currentDir = file;
        BaseFile[] tempList;
        tempList = currentDir.getFiles(fileFilter);
        if (null == tempList) {
            tempList = new BaseFile[0];
        }

        if (currentDir.getAbsoluteParent() != null) {
            hasParent = true;
        } else {
            hasParent = false;
        }
        setList(tempList);
    }

    /**
     * Возвращает число файлов
     *
     * @return число файлов в текущем каталоге
     */
    public int getFilesCount() {
        return fileList.size();
    }

    /**
     * Возвращает число подкаталогов
     *
     * @return число подкаталогов в текущем каталоге
     */
    public int getDirCount() {
        return dirList.size();
    }

    /**
     * Позволяет получить текущий каталог
     *
     * @return текущий каталог файловой системы
     */
    public BaseFile getCurrentDir() {
        return currentDir;
    }

    /**
     * Для текущего каталога создает список файлов, содержащихся в нем и
     * подкаталогов
     */
    public void setList(BaseFile[] list) {
        dirList = new ArrayList();
        fileList = new ArrayList();
        for (int i = 0; i < list.length; i++) {
            if (list[i].isDirectory()) {
                dirList.add(list[i]);
            } else {
                fileList.add(list[i]);
            }
        }
    }

    /**
     * Возвращает число файлов
     *
     * @return число файлов и подкаталогов в текущем каталоге
     */
    public int getTotalCount() {
        //return fsList.length;
        return fileList.size() + dirList.size();
    }

    /**
     * Позволяет определить является ли текущий каталог корневым
     *
     * @return <b>false</b>, если текущий каталог корневой, иначе - <b>true</b>
     */
    public boolean hasParent() {
        return hasParent;
    }

    /**
     * Позволяет получить список папок и файлов, содержащихся в текущем каталоге
     *
     * @return массив типа File, содержащий упоряоченный список папок и файлов
     */
    public BaseFile[] getFullList() {
        return fsList;
        //return null;
    }

    /**
     * Определяет каталог, находящийся на уровень выше текущего
     *
     * @return каталог, находящийся на уровень выше текущего
     */
    public BaseFile getParent() {
        return currentDir.getAbsoluteParent();
    }

    /**
     * Получить файл, соответствующий временной папке приложения
     *
     * @return файл, соответствующий временной папке приложения
     */
    public static LocalFile getTempDir() {
        java.util.Date d = new java.util.Date();
        LocalFile temp = new LocalFile(PATH_TO_TEMP + LocalFile.separator + d.getTime());
        temp.mkdirs();
        return temp.exists() ? temp : null;
    }

    /**
     * Очистить временную папку приложения
     *
     * @return <B>true</B>, если папка успешно очищена, иначе - <B>false</B>
     */
    public static boolean clearTempDir() {
        LocalFile temp = new LocalFile(PATH_TO_TEMP);
        boolean flag = false;
        ArrayList list = SubDirectoriesList.getList(temp);
        for (int i = list.size() - 1; i >= 1; i--) {
            flag = ((LocalFile) list.get(i)).delete();
        }
        return flag;
    }

    public ArrayList getFileList() {
        return fileList;
    }

    public ArrayList getDirList() {
        return dirList;
    }

    public static long getSize(AbstractList list) {
        long size = 0;
        if (null == list) {
            return 0;
        }
        for (Iterator it = list.iterator(); it.hasNext();) {
            size += ((BaseFile) it.next()).length();
        }
        return size;
    }

    public static long getSize(BaseFile file) {
        long size = 0;
        if (file.isDirectory()) {
            BaseFile[] fileList = file.getFiles();
            for (int i = 0; i < fileList.length; i++) {
                size += getSize(fileList[i]);
            }
        } else {
            size += file.length();
        }
        return size;
    }

    public static String getFormatedSize(long fileSize) {
        String size;
        String str = "";
        size = str + fileSize;
        int j = size.length();
        for (int i = j - 1; i >= 0; i--) {
            if ((j - i) % 3 == 0) {
                str = " " + size.substring(i, i + 1) + str;
            } else {
                str = size.substring(i, i + 1) + str;
            }
        }
        return str.trim();
    }

    public BaseFile[] getFiles() {
        BaseFile[] files = new BaseFile[fileList.size()];
        for (int i = 0; i < files.length; i++) {
            files[i] = (BaseFile) fileList.get(i);
        }
        return files;
    }

    public BaseFile[] getFolders() {
        BaseFile[] folders = new BaseFile[dirList.size()];
        for (int i = 0; i < folders.length; i++) {
            folders[i] = (BaseFile) dirList.get(i);
        }
        return folders;

    }

    public void addHiddenFilesFilter() {
        fileFilter = new HiddenFileFilter();
    }

    public void removeHiddenFilesFilter() {
        fileFilter = null;
    }
}
