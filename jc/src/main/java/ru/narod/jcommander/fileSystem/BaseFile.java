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
 * BaseFile.java
 *
 * Created on 24 ������ 2006 �., 19:54
 *
 */

package ru.narod.jcommander.fileSystem;
import java.io.FileFilter;


/**
 * ��������� ���������� ������, ������� ������ ���� ����������� �� ���� �����
 * ������
 * @author Pavel Ovcharov
 */
public interface BaseFile {
    /**
     * �������� ��� ����� (��� ����������)
     * @return ��� �����
     */
    public String getAbstractFileName();
    /**
     * �������� ���������� �����
     * @return ���������� �����
     */
    public String getExtention();
    
    /**
     * �������� ������ ������, ���� ������ ������ �������� ���������.
     * @return ������ ������ � ������ ��������
     */
    public BaseFile [] getFiles();
    
    /**
     * �������� ��������������� ������ ������, ���� ������ ������ �������� ���������.
     * @return ������ ������ � ������ ��������
     */
    public BaseFile [] getFiles(FileFilter filter);
    /**
     * �������� �������, ������������ �� ��������� � ������� �����
     * @return ������������ �������
     */
    public BaseFile getAbsoluteParent();
    /**
     * ����������, �������� �� ������ ������ ������ ��� ���������
     * @return <B>true</B>, ���� ������ ������ �������� ���������, ����� - <B>false</B>
     */
    public boolean isDirectory();
    /**
     * ������������ ������ � ����� ��������� �����. ��������, �����
     * 12455 ����� ����� ��� 12 455
     * @return ������ ���������� ������ ����� � ����������������� ����
     */
    public String getFormatFileSize();
    /**
     * ���������� ���� ���������� ����������� ����� ��� ��������
     * @return ���� ���������� ����������� � ������� dd.MM.yyyy hh:mm
     */
    public String getLastModifiedDate();
     /**
     * ���������� �������� ����� ��� ��������. ro - ������ ��� ������,
     * h - �������
     * @return ������, ���������� �������� ����� ��� ��������
     */
    public String getAtributeString();
    /**
     * ����������, ���� �� ������������ ������� � ������� �����
     * @return <B>true</B>���� ������������ ������� ����, ����� - <B>false</B>
     */
    public boolean hasParent();
    /**
     * ����� ��������� ����������, ���������� �� ���� ��������� �� �����
     * @return <B>true</B>, ��� ���� ����������, <B>false</B> - �����
     */
    public boolean exists();
    /**
     * �������� ������������ ������� ������� �����
     * @return ������������ ������� - ������ ������ BaseFile; ����
     * ������������� �������� ���, ������������ <B>null</B>
     */
    public String getAbsolutePath();
    /**
     * �������� ����, ��������������� ����� ����� ('C:\', 'D:\' � �.�.
     * ��� Windows, '/' ��� Linux)
     * @return ������ ������ BaseFile - ������ �����
     */
    public BaseFile getRoot();
    /**
     * �������� ����� ��� ������ �� �����
     * @return ����� ��� ������ �� �����
     * @throws java.lang.Exception ����������, ���� ��� �������� ������ ��������� ������
     */
    public java.io.InputStream getInputStream() throws java.io.IOException;
    /**
     * �������� ���� ��������� ����������� �����
     * @return ���� ��������� ����������� ����� - ����� ����������� ��������� � 00:00:00 1 
     * ������ 1970; ���� ���� �� ���������� ������������ 0L
     */
    //public long lastModified();
    
    /**
     * ������ ������ ���� � �����
     * @return <B>true</B>, ���� ���� ��� ������� ������, ����� - <B>false</B>
     */
    public boolean delete();
    /**
     * �������� ����� ��� ������ � ����
     * @throws java.lang.Exception ����������, ���� ��� �������� ������ ��������� ������
     * @return ����� ��� ������ � ����
     */
    public java.io.OutputStream getOutputStream() throws java.io.IOException;
    /**
     * ���������� ���� ����������� ��������� �����
     * @param time ���� ��������� ����������� - ����� �����������, ��������� � ������ �����
     * @return <B>true</B>, ���� ���� ������� ��������, ����� - <B>false</B>
     */
    public boolean setLastModified(long time);
    /**
     * �������� ���������� ���� � �����, ��� �������� - � ������������ � �����
     * ("C:\123\", "/home/user/")
     * @return ���������� ���� � �����
     */
    public String getPathWithSlash();
    /**
     * ������� ����� �� ����, ������ ����� ������ ������
     * @return <B>true</B>, ���� ����� ���� �������, ����� - <B>false</B>
     */
    public boolean mkdirs();
    /**
     * ������������� ������ ���� � ���� � ������, �������� <I>targetFile</I>
     * @param targetFile ����-��������
     * @return <B>true</B>, ���� ���� ��� �����������, ����� - <B>false</B>
     */
    public boolean renameTo(BaseFile targetFile);
    /**
     * ����������, ����� �� ��������� ������ ����
     * @return <B>true</B>, ���� ���� �������� ��� ������, ����� - <B>false</B>
     */
    public boolean canRead();
    /**
     * ������� ����� ����
     * @return <B>true</B>, ���� ���� ��� ������, ����� - <B>false</B>
     * @throws java.io.IOException ���������� ����������, ���� ��� �������� ����� ��������� ������
     */
    public boolean createNewFile() throws java.io.IOException;
    /**
     * ���������� ������ �����
     * @return ������ ����� � ������
     */
    public long length();
    
    public String getFilename();
    
    public long getLastModifiedTime();
    
    public boolean isHidden();
    public  boolean  isLocal();
    public  java.io.File toFile();
}
