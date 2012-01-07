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
 * NetFile.java
 *
 * Created on 7 ������� 2006 �., 9:38
 *
 */
package ru.narod.jcommander.fileSystem.net;

import java.io.FileFilter;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import jcifs.smb.SmbException;
import jcifs.smb.SmbFile;
import ru.narod.jcommander.fileSystem.BaseFile;
import ru.narod.jcommander.fileSystem.LocalFile;

/**
 * ����� ������ ���� � ����. ��� ������� � ���� ������������ ���������� 
 * jcifs-1.2.10
 * @author Strateg
 */
public class NetFile extends LocalFile implements ru.narod.jcommander.prefs.PrefKeys {

    private SmbFile smbFile;
    private NetFile parent;

    /**
     * ������� ����� ��������� ������ NetFile ��� ��������� ����
     * @param path ���� � ����� � ����
     */
    public NetFile(String path) {
        super(path);
        if (path.equals("")) {
            path = "smb://";
        }
        try {
            smbFile = new SmbFile(path);
            //System.out.println(smbFile.getParent());
            //parent = new NetFile(new SmbFile(smbFile.getParent()));
        } catch (MalformedURLException ex) {
            //ex.printStackTrace();
            //System.out.println(ex);
            ex.printStackTrace();
        }
    }

    /**
     * ������� ����� ��������� ������ NetFile �� ������� <I>smbFile</I>
     * @param smbFile ���� � ����
     */
    public NetFile(SmbFile smbFile) {
        super(smbFile.getPath());
        this.smbFile = smbFile;

    }

    private NetFile(SmbFile smbFile, NetFile parent) {
        this(smbFile);
        this.parent = parent;
    }

    /**
     * ���������� ���������� �����
     * @return ������ ������, ���� ������� ������ �������� �������, �����
     * ������������ ������, ���������� ���������� �����
     */
    @Override
    public String getExtention() {
        return isDirectory() ? "" : super.getExtention();
    }

    /**
     * �������� ������ ������, ����������� � ������ ����� � ������
     * @return ������ ������
     */
    @Override
    public BaseFile[] getFiles() {
        try {
            SmbFile[] f = smbFile.listFiles();
            NetFile[] list = new NetFile[f.length];
            for (int i = 0; i < f.length; i++) {
                list[i] = new NetFile(f[i], this);
            }
            return list;
        } catch (SmbException ex) {
//			LanguageBundle lb = LanguageBundle.getInstance();
//            String [] opt = {lb.getString("StrOk")};
//			WarningDialog.showMessage(lb.getString("StrNoRes"), lb.getString("StrJC"), opt, WarningDialog.MESSAGE_ERROR, 0);
        }
        return null;
    }

    @Override
    public BaseFile[] getFiles(FileFilter filter) {
        return getFiles();
    }

    /**
     * �������� ������������ ����� ��� ������� �����
     * @return ������ ������ BaseFile, �������������� ������������ ����� ������� �����
     */
    @Override
    public BaseFile getAbsoluteParent() {
        //return new NetFile(smbFile.getParent());
        return parent;
    }

    /**
     * ����������, �������� �� ������ ������ ������ ��� ���������
     * @return <B>true</B>, ���� ������ ������ �������� ���������, ����� - <B>false</B>
     */
    @Override
    public boolean isDirectory() {
        try {
            return smbFile.isDirectory();
        } catch (SmbException ex) {
            ex.printStackTrace();
            //System.out.println(ex);
        }
        return false;
    }

    /**
     * ������������ ������ � ����� ��������� �����. ��������, �����
     * 12455 ����� ����� ��� 12 455
     * @return ������ ���������� ������ ����� � ����������������� ����
     */
    @Override
    public String getFormatFileSize() {
        if (isDirectory()) {
            return DEFAULT_DIR_SIZE;
        }
        try {
            return getFormatFileSize(smbFile.length());
        } catch (SmbException ex) {
            ex.printStackTrace();
            //System.out.println(ex);
        }
        return "" + 0;
    }

    /**
     * ���������� ���� ���������� ����������� ����� ��� ��������
     * @return ���� ���������� ����������� � ������� dd.MM.yyyy hh:mm
     */
    @Override
    public String getLastModifiedDate() {
        long time = smbFile.getLastModified();
        return getLastModifiedDate(time);
    }

    /**
     * ���������� �������� ����� ��� ��������. ro - ������ ��� ������,
     * h - �������
     * @return ������, ���������� �������� ����� ��� ��������
     */
    @Override
    public String getAtributeString() {
        String atr = "";
        try {
            if (false == smbFile.canWrite()) {
                atr = "ro  ";
            }
            if (smbFile.isHidden()) {
                atr += "h";
            }
        } catch (SmbException ex) {
            ex.printStackTrace();
            //System.out.println(ex);
        }

        return atr;

    }

    /**
     * ������ ������ ���� � �����
     * @return <B>true</B>, ���� ���� ��� ������� ������, ����� - <B>false</B>
     */
    @Override
    public boolean delete() {
        try {
            smbFile.delete();
            return true;
        } catch (SmbException ex) {
            ex.printStackTrace();
            //System.out.println(ex);
        }
        return false;
    }

    /**
     * 
     * @return 
     */
    @Override
    public String getAbsolutePath() {
        //return ""+parent+smbFile.getName();
        return smbFile.getPath();
    }

    /**
     * �������� ����� ��� ������ �� �����
     * @return ����� ��� ������ �� �����
     * @throws java.lang.Exception ����������, ���� ��� �������� ������ ��������� ������
     */
    @Override
    public InputStream getInputStream() throws java.io.IOException {
        return smbFile.getInputStream();
    }

    /**
     * �������� ����� ��� ������ � ����
     * @throws java.lang.Exception ����������, ���� ��� �������� ������ ��������� ������
     * @return ����� ��� ������ � ����
     */
    @Override
    public OutputStream getOutputStream() throws java.io.IOException {
        return smbFile.getOutputStream();
    }

    /**
     * �������� ���� ��������� ����������� �����
     * @return ���� ��������� ����������� ����� - ����� ����������� ��������� � 00:00:00 1 
     * ������ 1970; ���� ���� �� ���������� ������������ 0L
     */
//    public long lastModified() {
//        try {
//            return smbFile.lastModified();
//        } catch (SmbException ex) {
//            //ex.printStackTrace();
//            System.out.println(ex);
//        }
//        return 0;
//    }
    /**
     * ����� ��������� ����������, ���������� �� ���� ��������� �� �����
     * @return <B>true</B>, ��� ���� ����������, <B>false</B> - �����
     */
    @Override
    public boolean exists() {
        try {
            return smbFile.exists();
        } catch (SmbException ex) {
            ex.printStackTrace();
            //System.out.println(ex);
        }
        return false;
    }

    /**
     * �������� ��������� ������������� �������
     * @return ��������� �������������
     */
    @Override
    public String toString() {
        return (null == parent) ? smbFile.getName() : (parent + smbFile.getName());
    }

    /**
     * ������� ����� �� ����, ������ ����� ������ ������
     * @return <B>true</B>, ���� ����� ���� �������, ����� - <B>false</B>
     */
    @Override
    public boolean mkdirs() {
        try {
            smbFile.mkdirs();
            return true;
        } catch (SmbException ex) {
            ex.printStackTrace();
            //System.out.println(ex);
            return false;
        }

    }

    /**
     * ���������� ���� ����������� ��������� �����
     * @param time ���� ��������� ����������� - ����� �����������, ��������� � ������ �����
     * @return <B>true</B>, ���� ���� ������� ��������, ����� - <B>false</B>
     */
    @Override
    public boolean setLastModified(long time) {
        try {
            smbFile.setLastModified(time);
            return true;
        } catch (SmbException ex) {
            ex.printStackTrace();
            //System.out.println(ex);
        }
        return false;
    }

    /**
     * �������� ���������� ���� � �����, ��� �������� - � ������������ � �����
     * ("smb://123/sad/")
     * @return ���������� ���� � �����
     */
    @Override
    public String getPathWithSlash() {
        return smbFile.getPath();
    }

    /**
     * ������������� ������ ���� � ���� � ������, �������� <I>targetFile</I>
     * @param targetFile ����-��������
     * @return <B>true</B>, ���� ���� ��� �����������, ����� - <B>false</B>
     */
    @Override
    public boolean renameTo(BaseFile dest) {
        try {
            if (dest instanceof NetFile) {
                smbFile.renameTo(((NetFile) dest).smbFile);
            } else {
                smbFile.renameTo(new SmbFile(dest.getPathWithSlash()));
            }
            return true;
        } catch (SmbException ex) {
            ex.printStackTrace();
            //System.out.println(ex);
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
            //System.out.println(ex);
        } catch (Exception ex) {
            ex.printStackTrace();
            //System.out.println(ex);
        }
        return false;
    }

    /**
     * �������� ����, ��������������� ��������� ����� � ���� (smb://)
     * @return ������ ������ BaseFile - ��������� ����� � ����
     */
    @Override
    public BaseFile getRoot() {
        return new NetFile("smb://");
        /*
        NetFile root = this;
        root.smbFile.
        while(root.hasParent()) {
        root = (NetFile) root.getAbsoluteParent();
        if (null == root) break;
        }
        return root;
         */
    }

    /**
     * ���������� ������ �����
     * @return ������ ����� � ������
     */
    @Override
    public long length() {
        try {
            return smbFile.length();
        } catch (SmbException ex) {
            ex.printStackTrace();
            //System.out.println(ex);
        }
        return 0;
    }

    public void setParent(NetFile parent) {
        this.parent = parent;
    }

    @Override
    public long getLastModifiedTime() {
        try {
            return smbFile.lastModified();
        } catch (SmbException ex) {
            ex.printStackTrace();
            //System.out.println(ex);
        }
        return 0;
    }

    @Override
    public boolean isHidden() {
        try {
            return smbFile.isHidden();
        } catch (SmbException ex) {
            return false;
        }
    }

    @Override
    public boolean isLocal() {
        return false;
    }
}
