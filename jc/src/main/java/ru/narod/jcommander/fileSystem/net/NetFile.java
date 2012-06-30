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
 * Created on 7 dec 2006, 9:38
 *
 */
package ru.narod.jcommander.fileSystem.net;

import java.io.FileFilter;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import jcifs.smb.SmbException;
import jcifs.smb.SmbFile;
import ru.narod.jcommander.JCLogger;
import ru.narod.jcommander.fileSystem.BaseFile;
import ru.narod.jcommander.fileSystem.LocalFile;

/**
 * Класс задает файл в сети. Для доступа к сети используется библиотека
 * jcifs-1.2.10
 *
 * @author Strateg
 */
public class NetFile extends LocalFile implements ru.narod.jcommander.prefs.PrefKeys {

    private SmbFile smbFile;
    private NetFile parent;

    /**
     * Создает новый экземпляр класса NetFile для заданного пути
     *
     * @param path путь к файлу в сети
     */
    public NetFile(String path) {
        super(path);
        if (path.equals("")) {
            path = "smb://";
        }
        try {
            smbFile = new SmbFile(path);
        } catch (MalformedURLException ex) {
            JCLogger.logSevere(null, ex);
        }
    }

    /**
     * Создает новый экземпляр класса NetFile по объекту <I>smbFile</I>
     *
     * @param smbFile файл в сети
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
     * Возвращает расширение файла
     *
     * @return пустую строку, если текущим файлом является каталог, иначе
     * возвращается строка, содержащая расширение файла
     */
    @Override
    public String getExtention() {
        return isDirectory() ? "" : super.getExtention();
    }

    /**
     * Получить список файлов, находящихся в данной папке в архиве
     *
     * @return массив файлов
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
            JCLogger.logSevere(null, ex);
        }
        return null;
    }

    @Override
    public BaseFile[] getFiles(FileFilter filter) {
        return getFiles();
    }

    /**
     * Получить родительскую папку для данного файла
     *
     * @return объект класса BaseFile, представляющий родительскую папку данного
     * файла
     */
    @Override
    public BaseFile getAbsoluteParent() {
        return parent;
    }

    /**
     * Определить, является ли данный объект файлом или каталогом
     *
     * @return <B>true</B>, если данный объект является каталогом, иначе -
     * <B>false</B>
     */
    @Override
    public boolean isDirectory() {
        try {
            return smbFile.isDirectory();
        } catch (SmbException ex) {
            JCLogger.logSevere(null, ex);
        }
        return false;
    }

    /**
     * Представляет размер в более наглядной форме. Например, число 12455 будет
     * иметь вид 12 455
     *
     * @return строка содержащая размер файла в отформатированном виде
     */
    @Override
    public String getFormatFileSize() {
        if (isDirectory()) {
            return DEFAULT_DIR_SIZE;
        }
        try {
            return getFormatFileSize(smbFile.length());
        } catch (SmbException ex) {
            JCLogger.logSevere(null, ex);
        }
        return "" + 0;
    }

    /**
     * Возвращает дату последеней модификации файла или каталога
     *
     * @return дату последеней модификации в формате dd.MM.yyyy hh:mm
     */
    @Override
    public String getLastModifiedDate() {
        long time = smbFile.getLastModified();
        return getLastModifiedDate(time);
    }

    /**
     * Возвращает атрибуты файла или каталога. ro - только для чтения, h -
     * скрытый
     *
     * @return строку, содержащую атрибуты файла или каталога
     */
    @Override
    public String getAtributeString() {
        String atr = "";
        try {
            if (!smbFile.canWrite()) {
                atr = "ro  ";
            }
            if (smbFile.isHidden()) {
                atr += "h";
            }
        } catch (SmbException ex) {
            JCLogger.logSevere(null, ex);
        }

        return atr;
    }

    /**
     * Удалит данный файл с диска
     *
     * @return <B>true</B>, если файл был успешно удален, иначе - <B>false</B>
     */
    @Override
    public boolean delete() {
        try {
            smbFile.delete();
            return true;
        } catch (SmbException ex) {
            JCLogger.logSevere(null, ex);
        }
        return false;
    }

    /**
     *
     * @return
     */
    @Override
    public String getAbsolutePath() {
        return smbFile.getPath();
    }

    /**
     * Получить поток для чтения из файла
     *
     * @return поток для чтения из файла
     * @throws java.lang.Exception вызывается, если при создании потока
     * произошла ошибка
     */
    @Override
    public InputStream getInputStream() throws java.io.IOException {
        return smbFile.getInputStream();
    }

    /**
     * Получить поток для записи в файл
     *
     * @throws java.lang.Exception вызывается, если при создании потока
     * произошла ошибка
     * @return поток для записи в файл
     */
    @Override
    public OutputStream getOutputStream() throws java.io.IOException {
        return smbFile.getOutputStream();
    }

    /**
     * Метод позволяет определить, существует ли файл физически на диске
     *
     * @return <B>true</B>, ели файл существует, <B>false</B> - иначе
     */
    @Override
    public boolean exists() {
        try {
            return smbFile.exists();
        } catch (SmbException ex) {
            JCLogger.logSevere(null, ex);
        }
        return false;
    }

    /**
     * Получить строковое представление объекта
     *
     * @return строковое представление
     */
    @Override
    public String toString() {
        return (null == parent) ? smbFile.getName() : (parent + smbFile.getName());
    }

    /**
     * Создать папки на пути, котоый задан данным файлом
     *
     * @return <B>true</B>, если папки были созданы, иначе - <B>false</B>
     */
    @Override
    public boolean mkdirs() {
        try {
            smbFile.mkdirs();
            return true;
        } catch (SmbException ex) {
            JCLogger.logSevere(null, ex);
            return false;
        }

    }

    /**
     * Установить дату последеного изменения файла
     *
     * @param time дата последней модификации - число миллисекунд, прошедших с
     * начала эпохи
     * @return <B>true</B>, если дата успешно изменена, иначе - <B>false</B>
     */
    @Override
    public boolean setLastModified(long time) {
        try {
            smbFile.setLastModified(time);
            return true;
        } catch (SmbException ex) {
            JCLogger.logSevere(null, ex);
        }
        return false;
    }

    /**
     * Получить абсолютный путь к файлу, для каталога - с разделителем в конце
     * ("smb://123/sad/")
     *
     * @return абсолютный путь к файлу
     */
    @Override
    public String getPathWithSlash() {
        return smbFile.getPath();
    }

    /**
     * Переименовать данный файл в файл с именем, заданным <I>targetFile</I>
     *
     * @param targetFile файл-преемник
     * @return <B>true</B>, если файл был переименовн, иначе - <B>false</B>
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
        } catch (Exception ex) {
            JCLogger.logSevere(null, ex);
        }
        return false;
    }

    /**
     * Получить файл, соответствующий начальной папке в сети (smb://)
     *
     * @return объект класса BaseFile - начальная папка в сети
     */
    @Override
    public BaseFile getRoot() {
        return new NetFile("smb://");
        /*
         * NetFile root = this; root.smbFile. while(root.hasParent()) { root =
         * (NetFile) root.getAbsoluteParent(); if (null == root) break; } return
         * root;
         */
    }

    /**
     * Определить размер файла
     *
     * @return размер файла в байтах
     */
    @Override
    public long length() {
        try {
            return smbFile.length();
        } catch (SmbException ex) {
            JCLogger.logSevere(null, ex);
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
            JCLogger.logSevere(null, ex);
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
