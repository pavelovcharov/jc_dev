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
package omegaCommander.fileSystem;

import omegaCommander.fileSystem.archive.ArchiveFile;
import java.io.FileFilter;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Iterator;
import omegaCommander.gui.dialog.WarningDialog;
import omegaCommander.util.LanguageBundle;

/**
 * Класс предоставляет методы для работы с фаловой системой.
 * Файловая система задается текущим каталогом и списком файлов
 * и подкаталогов в нем
 * @author Programmer
 * @version 2005/04/24 1:32:27 PM
 */
public class FileSystemList {

    private AbsoluteFile currentDir;
    private boolean hasParent;
    private ArrayList dirList;
    private ArrayList fileList;
    private AbsoluteFile[] fsList;
    FileFilter fileFilter = null;
    /**
     * Задает путь к временной папке приложения
     */
    public static final String PATH_TO_TEMP =
            System.getProperty("java.io.tmpdir") + LocalFile.separator + "$jcomtemp";

    /**
     * Создает фаловую систему. Текущим является рабочий каталог
     * приложения
     */
//    public FileSystemList(){
//        currentDir=new LocalFile(".");
//        setFileList(currentDir);
//    }
    /**
     * Создает файловую систему с текущим каталогом <b>abstractFile</b>
     * @param file текущий каталог файловой системы
     */
    public FileSystemList(AbsoluteFile file) {
        setFileList(file);
    }

    /**
     * Задает текущий каталог
     * @param file задает текущий каталог файловой системы
     */
    //XXX посмотреть как это работает. разобраться с рекурсиями и return'ами
    public void setFileList(AbsoluteFile file) {
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
        AbsoluteFile[] tempList;
        tempList = currentDir.getFiles(fileFilter);
        if (null == tempList) {

            //XXX fix this
            //XXX throw exception and catch it at MainFrame. Set parent to WarningDialog
            LanguageBundle lb = LanguageBundle.getInstance();
            String[] opt = {lb.getString("StrOk")};
            WarningDialog.showMessage(lb.getString("StrNoRes"), lb.getString("StrJC"), opt, WarningDialog.MESSAGE_ERROR, 0);

            if (null != currentDir.getAbsoluteParent()) {
                setFileList(currentDir.getAbsoluteParent());
            } else {
                setFileList(RootFileSystem.getReadableRoot());
            }
            return;
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
     * @return число файлов в текущем каталоге
     */
    public int getFilesCount() {
        return fileList.size();
    }

    /**
     * Возвращает число подкаталогов
     * @return число подкаталогов в текущем каталоге
     */
    public int getDirCount() {
        return dirList.size();
    }

    /**
     * Позволяет получить текущий каталог
     * @return текущий каталог файловой системы
     */
    public AbsoluteFile getCurrentDir() {
        return currentDir;
    }

    /**
     * Для текущего каталога создает список файлов, содержащихся в
     * нем и подкаталогов
     */
    public void setList(AbsoluteFile[] list) {
        //AbsoluteFile [] list = currentDir.getFiles();
        dirList = new ArrayList();
        fileList = new ArrayList();
        for (int i = 0; i < list.length; i++) {
            //if (false == list[i].canRead()) continue;
            if (list[i].isDirectory()) {
                dirList.add(list[i]);
            } else {
                fileList.add(list[i]);
            }
        }
    }

    /**
     * Возвращает число файлов
     * @return число файлов и подкаталогов в текущем каталоге
     */
    public int getTotalCount() {
        //return fsList.length;
        return fileList.size() + dirList.size();
    }

    /**
     * Позволяет определить является ли текущий каталог корневым
     * @return <b>false</b>, если текущий каталог корневой, иначе -
     * <b>true</b>
     */
    public boolean hasParent() {
        return hasParent;
    }

    /**
     * Позволяет получить список папок и файлов, содержащихся в
     * текущем каталоге
     * @return массив типа File, содержащий упоряоченный список папок
     * и файлов
     */
    public AbsoluteFile[] getFullList() {
        return fsList;
        //return null;
    }

    /**
     * Определяет каталог, находящийся на уровень выше текущего
     * @return каталог, находящийся на уровень выше текущего
     */
    public AbsoluteFile getParent() {
        return currentDir.getAbsoluteParent();
    }

    /**
     * Получить файл, соответствующий временной папке приложения
     * @return файл, соответствующий временной папке приложения
     */
    public static LocalFile getTempDir() {
        java.util.Date d = new java.util.Date();
        //java.text.SimpleDateFormat f = new java.text.SimpleDateFormat("dd-MM-yyyy");
        LocalFile temp = new LocalFile(PATH_TO_TEMP + LocalFile.separator + d.getTime());
        temp.mkdirs();
        return temp.exists() ? temp : null;
    }

    /**
     * Очистить временную папку приложения
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
            size += ((AbsoluteFile) it.next()).length();
        }
        return size;
    }

    public static long getSize(AbsoluteFile file) {
        long size = 0;
        if (file.isDirectory()) {
            AbsoluteFile[] fileList = file.getFiles();
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

    public AbsoluteFile[] getFiles() {
        //if (0 == fileList.size()) return null;
        AbsoluteFile[] files = new AbsoluteFile[fileList.size()];
        for (int i = 0; i < files.length; i++) {
            files[i] = (AbsoluteFile) fileList.get(i);
        }
        return files;
    }

    public AbsoluteFile[] getFolders() {
        //if (0 == dirList.size()) return null;
        AbsoluteFile[] folders = new AbsoluteFile[dirList.size()];
        for (int i = 0; i < folders.length; i++) {
            folders[i] = (AbsoluteFile) dirList.get(i);
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
