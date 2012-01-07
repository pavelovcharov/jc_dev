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

import ru.narod.jcommander.fileSystem.archive.ArchiveFile;
import java.io.FileFilter;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Iterator;
import ru.narod.jcommander.gui.dialog.WarningDialog;
import ru.narod.jcommander.util.LanguageBundle;

/**
 * ����� ������������� ������ ��� ������ � ������� ��������.
 * �������� ������� �������� ������� ��������� � ������� ������
 * � ������������ � ���
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
     * ������ ���� � ��������� ����� ����������
     */
    public static final String PATH_TO_TEMP =
            System.getProperty("java.io.tmpdir") + LocalFile.separator + "$jcomtemp";

    /**
     * ������� ������� �������. ������� �������� ������� �������
     * ����������
     */
//    public FileSystemList(){
//        currentDir=new LocalFile(".");
//        setFileList(currentDir);
//    }
    /**
     * ������� �������� ������� � ������� ��������� <b>abstractFile</b>
     * @param file ������� ������� �������� �������
     */
    public FileSystemList() {
        
    }

    /**
     * ������ ������� �������
     * @param file ������ ������� ������� �������� �������
     */
    //XXX ���������� ��� ��� ��������. ����������� � ���������� � return'���
    public void setFileList(BaseFile file) {
        //XXX ��������� �������� ������ � ��������� ������

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
     * ���������� ����� ������
     * @return ����� ������ � ������� ��������
     */
    public int getFilesCount() {
        return fileList.size();
    }

    /**
     * ���������� ����� ������������
     * @return ����� ������������ � ������� ��������
     */
    public int getDirCount() {
        return dirList.size();
    }

    /**
     * ��������� �������� ������� �������
     * @return ������� ������� �������� �������
     */
    public BaseFile getCurrentDir() {
        return currentDir;
    }

    /**
     * ��� �������� �������� ������� ������ ������, ������������ �
     * ��� � ������������
     */
    public void setList(BaseFile[] list) {
        //BaseFile [] list = currentDir.getFiles();
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
     * ���������� ����� ������
     * @return ����� ������ � ������������ � ������� ��������
     */
    public int getTotalCount() {
        //return fsList.length;
        return fileList.size() + dirList.size();
    }

    /**
     * ��������� ���������� �������� �� ������� ������� ��������
     * @return <b>false</b>, ���� ������� ������� ��������, ����� -
     * <b>true</b>
     */
    public boolean hasParent() {
        return hasParent;
    }

    /**
     * ��������� �������� ������ ����� � ������, ������������ �
     * ������� ��������
     * @return ������ ���� File, ���������� ������������ ������ �����
     * � ������
     */
    public BaseFile[] getFullList() {
        return fsList;
        //return null;
    }

    /**
     * ���������� �������, ����������� �� ������� ���� ��������
     * @return �������, ����������� �� ������� ���� ��������
     */
    public BaseFile getParent() {
        return currentDir.getAbsoluteParent();
    }

    /**
     * �������� ����, ��������������� ��������� ����� ����������
     * @return ����, ��������������� ��������� ����� ����������
     */
    public static LocalFile getTempDir() {
        java.util.Date d = new java.util.Date();
        //java.text.SimpleDateFormat f = new java.text.SimpleDateFormat("dd-MM-yyyy");
        LocalFile temp = new LocalFile(PATH_TO_TEMP + LocalFile.separator + d.getTime());
        temp.mkdirs();
        return temp.exists() ? temp : null;
    }

    /**
     * �������� ��������� ����� ����������
     * @return <B>true</B>, ���� ����� ������� �������, ����� - <B>false</B>
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
        //if (0 == fileList.size()) return null;
        BaseFile[] files = new BaseFile[fileList.size()];
        for (int i = 0; i < files.length; i++) {
            files[i] = (BaseFile) fileList.get(i);
        }
        return files;
    }

    public BaseFile[] getFolders() {
        //if (0 == dirList.size()) return null;
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
