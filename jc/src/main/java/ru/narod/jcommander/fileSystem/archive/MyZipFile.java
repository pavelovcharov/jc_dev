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
 * MyZipFile.java
 *
 * Created on 23 nov 2006, 21:54
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package ru.narod.jcommander.fileSystem.archive;

import java.io.FileFilter;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import ru.narod.jcommander.fileSystem.BaseFile;
import ru.narod.jcommander.fileSystem.LocalFile;

/**
 * Класс задает файл в zip или jar-архиве. Позволяет просматривать содержимое
 * архивов, как обычные папки на диске
 *
 * @author Pavel Ovcharov
 */
public class MyZipFile extends LocalFile implements ArchiveFile {

    private HashMap archiveMap;
    private LocalFile archive;
    private BaseFile parent;
    private ZipEntry entry = null;

    //XXX а если архив в сети?
    /**
     * Создать объект класса MyZipFile с помощью файла <I>parent</I>
     *
     * @param parent объект класса LocalFile, задающий положение архива
     */
    public MyZipFile(LocalFile parent) {
        super(parent);
        if (!exists()) {
            throw new IllegalArgumentException("ArchiveFile " + parent + " doesn't exists!");
        }
        this.archive = parent;
        this.parent = parent.getAbsoluteParent();
        archiveMap = new HashMap();
        getArchiveContent();
    }

    /**
     * Создать объект класса MyZipFile с помощью файла <I>parent</I>
     *
     * @param parent объект класса BaseFile
     */
    public MyZipFile(BaseFile parent) {
        this(new LocalFile(parent));
    }

    /**
     * Создать объект класса MyZipFile с помощью файла <I>parent</I> и имени
     * файла <I>child</I>
     *
     * @param parent задает абсолютный путь к файлу
     * @param child имя создавемого файла
     */
    public MyZipFile(MyZipFile parent, String child) {
        super(parent, child);
        this.parent = parent;
        archive = parent.archive;
        archiveMap = parent.archiveMap;
        entry = new ZipEntry(parent.entry + ARCHIVE_SEPARATOR + child);

    }

    private MyZipFile(LocalFile parent, ZipEntry zipEntry) {
        super(((MyZipFile) parent).archive, zipEntry.getName());
        this.entry = zipEntry;
        this.parent = parent;
        if (parent instanceof MyZipFile) {
            archiveMap = ((MyZipFile) parent).archiveMap;
            archive = ((MyZipFile) parent).archive;
        }
    }

    /**
     * Создать объект класса MyZipFile. Используется для реализации просмотра
     * содержимого архива, находящегося внутри другого архива.
     *
     * @param parent объект класса ArchiveFile, представляющий собой архив,
     * содержимое которого нужно просмотреть
     * @param archive задает путь к архиву на диске (например, во временной
     * папке)
     */
    public MyZipFile(ArchiveFile parent, LocalFile archive) {
        this(archive);
        this.parent = parent;
    }

    private boolean getArchiveContent() {
        boolean success = true;
        archiveMap.clear();

        try {
            String name;
            final ZipFile zipFile = new ZipFile(archive.getAbsolutePath());
            final Enumeration entries = zipFile.entries();
            while (entries.hasMoreElements()) {
                final Object object = entries.nextElement();
                if (object instanceof ZipEntry) {
                    name = ((ZipEntry) object).getName();
                    if (name.endsWith(ARCHIVE_SEPARATOR)) {
                        name = name.substring(0,
                                name.lastIndexOf(ARCHIVE_SEPARATOR));
                    }

                    archiveMap.put(name, object);
                }
            }
        } catch (Exception ex) {
            success = false;
        }

        return success;
    }

    /**
     * Получить список имен файлов, находящихся в данной папке в архиве
     *
     * @return массив имен файлов
     */
    protected String[] list() {
        ArrayList names = new ArrayList();
        Set keys = archiveMap.keySet();
        if (keys != null) {
            if (null == entry) {//корень архива
                // search through all available archive entries
                Iterator iter = keys.iterator();
                while (iter.hasNext()) {
                    String key = (String) iter.next();
                    if (key.indexOf(ARCHIVE_SEPARATOR) == 0) {
                        // remove leading "/"
                        key = key.substring(ARCHIVE_SEPARATOR.length());
                    }

                    if (key.indexOf(ARCHIVE_SEPARATOR) < 0) {
                        // no more "/" found => correct level
                        names.add(key);
                    }
                }
            } else {
                final String name = entry.getName();

                // search through all available archive entries
                final Iterator iter = keys.iterator();
                while (iter.hasNext()) {
                    final String key = (String) iter.next();
                    if (key.startsWith(name) && !key.equals(name)) {
                        // candidate found
                        String rest = key.substring(name.length());
                        if (rest.indexOf(ARCHIVE_SEPARATOR) == 0) {
                            // remove leading "/"
                            rest = rest.substring(ARCHIVE_SEPARATOR.length());
                        }

                        if (rest.indexOf(ARCHIVE_SEPARATOR) < 0) {
                            // no more "/" found => correct level
                            names.add(rest);
                        }
                    }
                }
            }
        }
        String[] list = new String[names.size()];
        for (int i = 0; i < list.length; i++) {
            list[i] = names.get(i).toString();
        }
        return list;
    }

    /**
     * Получить список файлов, находящихся в данной папке в архиве
     *
     * @return массив файлов
     */
    @Override
    public BaseFile[] getFiles(FileFilter filter) {
        return getFiles();
    }

    @Override
    public BaseFile[] getFiles() {
        final String[] names = list();
        MyZipFile[] files = null;
        if (names != null) {
            files = new MyZipFile[names.length];
            String name;
            for (int i = 0; i < names.length; ++i) {

                if (null != entry) {
                    name = entry.getName() + names[i];
                } else {
                    name = names[i];
                }
                files[i] = new MyZipFile(this, (ZipEntry) archiveMap.get(name));
            }
        }
        return files;
    }

    /**
     * Определить, является ли данный объект файлом или каталогом
     *
     * @return <B>true</B>, если данный объект является каталогом, иначе -
     * <B>false</B>
     */
    @Override
    public boolean isDirectory() {
        return (null == entry) ? archive.isDirectory() : entry.isDirectory();
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
     * Представляет размер в более наглядной форме. Например, число 12455 будет
     * иметь вид 12 455
     *
     * @return строка содержащая размер файла в отформатированном виде
     */
    @Override
    public String getFormatFileSize() {
        if (null == entry) {
            return getFormatFileSize(archive.length());
        }
        if (entry.isDirectory()) {
            return DEFAULT_DIR_SIZE;
        }
        return getFormatFileSize(entry.getSize());
    }

    /**
     * Возвращает дату последеней модификации файла или каталога
     *
     * @return дату последеней модификации в формате dd.MM.yyyy hh:mm
     */
    @Override
    public String getLastModifiedDate() {
        Date date = new Date();
        SimpleDateFormat sdate = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
        if (null == entry) {
            date.setTime(parent.getLastModifiedTime());
        } else {
            date.setTime(entry.getTime());
        }
        return sdate.format(date);

    }

    /**
     * Возвращает атрибуты файла или каталога. ro - только для чтения, h -
     * скрытый
     *
     * @return строку, содержащую атрибуты файла или каталога
     */
    @Override
    public String getAtributeString() {
        if (null == entry) {
            return ((LocalFile) parent).getAtributeString();
        }
        return "ro";
    }

    /**
     * Получить поток для чтения из файла
     *
     * @return поток для чтения из файла
     * @throws java.lang.Exception вызывается, если при создании потока
     * произошла ошибка
     */
    @Override
    public java.io.InputStream getInputStream() throws java.io.IOException {
        if (null == entry) {
            return parent.getInputStream();
        }
        return new ZipFile(archive.getAbsolutePath()).getInputStream(entry);
    }

    /**
     * Получить поток для записи в файл
     *
     * @return поток для записи в файл
     * @throws java.lang.Exception вызывается, если при создании потока
     * произошла ошибка
     */
    @Override
    public java.io.OutputStream getOutputStream() throws java.io.IOException {
        if (null == entry) {
            return parent.getOutputStream();
        }
        return null;
    }

    /**
     * Получить строковое представление объекта
     *
     * @return строковое представление
     */
    @Override
    public String toString() {
        if (!parent.hasParent()) {
            return parent + getFilename();
        }
        return parent + separator + getFilename();
    }

    @Override
    public long getLastModifiedTime() {
        if (null == entry) {
            return parent.getLastModifiedTime();
        } else {
            return entry.getTime();
        }
    }

    @Override
    public long length() {
        if (null == entry) {
            return archive.length();
        } else {
            return entry.getSize();
        }
    }

    @Override
    public String getPathWithSlash() {
        String path = getAbsolutePath();
        return (super.getAbsolutePath().endsWith(separator)) ? path : path + separator;
    }

    @Override
    public boolean exists() {
        return super.exists() || archive != null;
    }
}
