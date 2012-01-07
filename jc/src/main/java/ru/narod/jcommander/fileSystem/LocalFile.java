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
 * LocalFile.java
 *
 * Created on 27 ���� 2006 �., 10:16
 *
 */
package ru.narod.jcommander.fileSystem;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Date;
import java.text.SimpleDateFormat;
/**
 * ����� ������ ���� ��� ������� �������� �������
 * @author Pavel Ovcharov
 */
public class LocalFile extends File implements BaseFile {

    static protected String DEFAULT_DATE_FORMAT = "dd.MM.yyyy HH:mm";
    static protected String DEFAULT_DIR_SIZE = "<DIR>";

    /**
     * ������� ��������� ������ LocalFile. �� ��������� ��� ������� �������
     * �������� �������
     */
    public LocalFile() {
        super(RootFileSystem.getReadableRoot().getAbsolutePath());
    }

    /**
     * ������� ��������� ������ LocalFile � ������ (�����) <I>fileName</I>
     * 
     * @param fileName ���� � �����
     */
    public LocalFile(String fileName) {
        super(fileName);
    }

    /**
     * ������� ��������� ������ LocalFile �� ����� anotherFile
     * 
     * @param anotherFile ������ ����� java.io.File
     * @see java.io.File
     */
    public LocalFile(File anotherFile) {
        super(anotherFile.getAbsolutePath());
    }

    /**
     * ������� ��������� ������ LocalFile � ������ <I>fileName</I> � �����,
     * �������� ������ <I>parentFile</I>.
     * 
     * @param parentFile ������ ������ java.io.File ������ ���� � ��������
     * @param fileName ������ ��� ������������ �����
     */
    public LocalFile(File parentFile, String fileName) {
        super(parentFile, fileName);
    }

    /**
     * ������� ��������� ������ LocalFile � ������ <I>fileName</I> � �����,
     * �������� ������ <I>parentFile</I>.
     * 
     * @param parent ������ ������ LocalFile ������ ���� � ��������
     * @param child ������ ��� ������������ �����
     */
    public LocalFile(LocalFile parent, String child) {
        super(parent, child);
    }

    /**
     * ������� ��������� ������ LocalFile ��� ����� ����� aFile
     * 
     * @param aFile ������ ������ LocalFile
     */
    public LocalFile(LocalFile aFile) {
        super(aFile.getAbsolutePath());
    }

    /**
     * ������� ��������� ������ LocalFile ��� ����� ����� aFile
     * 
     * @param aFile ������ ������ BaseFile
     */
    public LocalFile(BaseFile aFile) {
        super(aFile.getAbsolutePath());
    }

    /**
     * ���������� ���������� �����
     * @return ������ ������, ���� ������� ������ �������� �������, �����
     * ������������ ������, ���������� ���������� �����
     */
    public String getExtention() {
        if (super.isDirectory()) {
            return "";
        }
        String name = super.getName();
        int len = name.length();
        for (int i = len - 1; i >= 0; i--) {
            if (name.charAt(i) == '.') {
                return name.substring(i + 1);
            }
        }
        return "";
    }

    /**
     * ��������� �������� ��� ����� ��� ��������
     * @return ��� �������� ����� (��� ����������) ��� ��������
     */
    public String getAbstractFileName() {
        String name = super.getName();
        if (name.equals("")) {
            return getAbsolutePath();
        }
        int len = name.length();
        if (super.isDirectory()) {
            return name;
        }
        len = name.length();
        for (int i = len - 1; i >= 0; i--) {
            if (name.charAt(i) == '.') {
                return name.substring(0, i);
            }
        }
        return name;
    }

    /**
     * �������� ������������ ������� ������� �����
     * @return ������������ �������; ���� ������������� �������� ���,
     * ������������ <B>null</B>
     */
    public LocalFile getAbstractParent() {
        if (super.getParentFile() != null) {
            return new LocalFile(super.getParentFile());
        } else {
            return null;
        }
    }

    /**
     * ����������, ���� �� ������������ ������� � ������� �����
     * @return <B>true</B>���� ������������ ������� ����, ����� - <B>false</B>
     */
    public boolean hasParent() {
        return (null != super.getParentFile()) ? true : false;

    }

    /**
     * ���������� ��� ����� ��� ��������
     * @return ��� ������� �������� ��� ����� (� �����������)
     */
    public String getFilename() {
        return super.getName();
    }

    /**
     * ���������� ������������� ���� (������������� ������������ path)
     * @param path
     * @return
     */
    public String getPathRelativeTo(String path) throws IllegalArgumentException {
        String absolutePath = getAbsolutePath();
        if (!absolutePath.startsWith(path)) {
            throw new IllegalArgumentException(absolutePath + "don't contains " + path);
        }
        return getAbsolutePath().substring(path.length());
    }

    /**
     * ���������� ���� ���������� ����������� ����� ��� ��������
     * @return ���� ���������� ����������� � ������� dd.MM.yyyy hh:mm
     */
    public String getLastModifiedDate() {
        return getLastModifiedDate(lastModified());
    }

    protected String getLastModifiedDate(long time) {
        Date date = new Date();
        SimpleDateFormat sdate = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
        date.setTime(time);
        return sdate.format(date);
    }

    /**
     * ��������� ������ �����
     * @return ������������, ���������� ���� ���������� ������
     */
    public long getSize() {
        return length();
    }

    /**
     * ������������ ������ � ����� ��������� �����. ��������, �����
     * 12455 ����� ����� ��� 12 455
     * @return ������ ���������� ������ ����� � ����������������� ����
     */
    public String getFormatFileSize() {
        String str = new String("");
        String size;
        if (super.isDirectory()) {
            return DEFAULT_DIR_SIZE;
        }
        return getFormatFileSize(this.length());
    }

    /**
     * 
     * @param fileSize 
     * @return 
     */
    protected String getFormatFileSize(long fileSize) {
        return FileSystemList.getFormatedSize(fileSize);
    }

    /**
     * ���������� �������� ����� ��� ��������. ro - ������ ��� ������,
     * h - �������
     * @return ������, ���������� �������� ����� ��� ��������
     */
    public String getAtributeString() {
        String atr = "";
        if (!canWrite()) {
            atr = "ro  ";
        }
        if (isHidden()) {
            atr += "h";
        }
        return atr;
    }
    /*
    public LocalFile[] getFileList() {
    java.io.File[] fileList =this.listFiles();
    LocalFile[] abstractRoots = new LocalFile[fileList.length];
    for(int i=0; i<fileList.length; i++) {
    abstractRoots[i] = new LocalFile(fileList[i]);
    }
    return abstractRoots;

    }
     */

    /**
     * ���������� ��������� �� ���� ������
     * 
     * @param aFile ������ ������ LocalFile
     * @return <B>true</B>, ���� ���� ������� ����� � ����� aFile ���������,
     * ����� - <B>false</B>
     */
    public boolean equals(LocalFile aFile) {
        return this.getAbsolutePath().equals(aFile.getAbsolutePath());
    }

//    /**
//     *
//     * @param pathToFile
//     * @throws java.lang.Exception
//     */
//    public void copyTo(String pathToFile) throws Exception{
//        LocalFile target = new LocalFile(pathToFile);
//        copyTo(target);
//    }
//    public void copyTo(LocalFile target) throws Exception{
//        FileInputStream  fis= new FileInputStream(this);
//        FileOutputStream fos = new FileOutputStream(target);
//        byte[] buf = new byte[1024];
//        int j = 0;
//        while((j=fis.read(buf))!=-1) {
//            fos.write(buf, 0, j);
//        }
//        fis.close();
//        fos.close();
//        target.setLastModified(lastModified());
//    }
    /**
     * �������� ����, ������������� ����� ����� ('C:\', 'D:\' � �.�.
     * ��� Windows, '/' ��� Linux)
     * @return ������ ������ BaseFile - ������ �����
     */
    public BaseFile getRoot() {
        LocalFile root = this;
        while (root.hasParent()) {
            root = root.getAbstractParent();
        }
        return root;
    }

    /**
     * �������� ������������ ������� ������� �����
     * @return ������������ ������� - ������ ������ BaseFile; ����
     * ������������� �������� ���, ������������ <B>null</B>
     */
    public BaseFile getAbsoluteParent() {
        return getAbstractParent();
    }

    /**
     * �������� ����� ��� ������ �� �����
     * @return ����� ��� ������ �� �����
     * @throws java.lang.Exception ����������, ���� ��� �������� ������ ��������� ������
     */
    public java.io.InputStream getInputStream() throws java.io.IOException {
        return new FileInputStream(this);
    }

    /**
     * �������� ����� ��� ������ � ����
     * @throws java.lang.Exception ����������, ���� ��� �������� ������ ��������� ������
     * @return ����� ��� ������ � ����
     */
    public java.io.OutputStream getOutputStream() throws java.io.IOException {
        return new FileOutputStream(this);
    }

    /**
     * �������� ���������� ���� � ����� � ������������ � �����
     * ("C:\123\", "C:\123\1.zip\", "/home/user/")
     * @return ���������� ���� � �����
     */
    public String getPathWithSlash() {
        String path = super.getAbsolutePath();
        if (false == isDirectory()) {
            return super.getAbsolutePath();
        }
        return (super.getAbsolutePath().endsWith(separator)) ? path : path + separator;
    }

    /**
     * ��������� ��������������� ������ ���� � ����, �������� ������ <I>targetFile</I>
     * @param targetFile ������ ���� � ������ �����
     * @return <B>true</B>,���� �������������� ������ �������; ����� - <B>false</B>
     */
    public boolean renameTo(BaseFile targetFile) {
        return super.renameTo((File) targetFile);
    }

    public long getLastModifiedTime() {
        return lastModified();
    }

    /**
     * �������� ������ ������ � ������ ��������
     * @return ������ ������ � ������ ��������
     */
    public BaseFile[] getFiles(FileFilter filter) {
        File[] list = super.listFiles(filter);
        if (null == list) {
            return null;//throw new NullPointerException("File "+this+" has no children");
        }
        LocalFile[] af = new LocalFile[list.length];
        for (int i = 0; i < list.length; i++) {
            af[i] = new LocalFile(list[i]);
        }
        return af;
    }

    /**
     * �������� ������ ������ � ������ ��������
     * @return ������ ������ � ������ ��������
     */
    public BaseFile[] getFiles() {
        File[] list = super.listFiles();
        if (null == list) {
            return null;//throw new NullPointerException("File "+this+" has no children");
        }
        LocalFile[] af = new LocalFile[list.length];
        for (int i = 0; i < list.length; i++) {
            af[i] = new LocalFile(list[i]);
        }
        return af;
    }

    public boolean isLocal() {
        return true;
    }

    public File toFile() {
        if (isLocal())
            return (File)this;
        else return null;
    }
}
