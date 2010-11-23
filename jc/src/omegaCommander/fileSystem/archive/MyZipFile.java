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
 * Created on 23 Ноябрь 2006 г., 21:54
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package omegaCommander.fileSystem.archive;

import omegaCommander.fileSystem.*;
import java.io.FileFilter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

//XXX посмотреть как это работает

/**
 * Класс задает файл в zip или jar-архиве. Позволяет просматривать содержимое
 * архивов, как обычные папки на диске
 * @author Pavel Ovcharov
 */
public class MyZipFile extends LocalFile implements ArchiveFile {
    
    private HashMap archiveMap;
    private LocalFile archive;
    private AbsoluteFile parent;
    private ZipEntry entry = null;

	//XXX а если архив в сети?
    /**
     * Создать объект класса MyZipFile с помощью файла <I>parent</I>
     * 
     * @param parent объект класса LocalFile, задающий положение архива
     */
    public MyZipFile(LocalFile parent) {
        super(parent);
        if (false == exists()) throw new IllegalArgumentException("ArchiveFile " + parent + " doesn't exists!");
        this.archive = parent;
        this.parent = parent.getAbsoluteParent();
        archiveMap = new HashMap();
        getArchiveContent();
    }
    
    /**
     * Создать объект класса MyZipFile с помощью файла <I>parent</I>
     * @param parent объект класса AbsoluteFile
     */
    public MyZipFile(AbsoluteFile parent) {
        this(new LocalFile(parent));
    }
    
    /**
     * Создать объект класса MyZipFile с помощью файла <I>parent</I> и имени файла
     * <I>child</I>
     * @param parent задает абсолютный путь к файлу
     * @param child имя создавемого файла
     */
    public MyZipFile(MyZipFile parent, String child) {
        super(parent, child);
        this.parent = parent;
        archive = parent.archive;
        archiveMap = parent.archiveMap;
        entry = new ZipEntry(parent.entry+ARCHIVE_SEPARATOR+child);
        
    }
    
    private MyZipFile(LocalFile parent, ZipEntry zipEntry) {
        super(((MyZipFile)parent).archive, zipEntry.getName());
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
     * @param parent объект класса MyZipFile, представляющий собой архив, содержимое которого нужно
     * просмотреть
     * @param archive задает путь к архиву на диске (например, во временной папке)
     */
//    public MyZipFile(MyZipFile parent, LocalFile archive) {
//        this(archive);
//        this.parent = parent;
//    }

	/**
     * Создать объект класса MyZipFile. Используется для реализации просмотра
     * содержимого архива, находящегося внутри другого архива.
     * @param parent объект класса ArchiveFile, представляющий собой архив, содержимое которого нужно
     * просмотреть
     * @param archive задает путь к архиву на диске (например, во временной папке)
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
            final ZipFile zipFile = new ZipFile(archive);
            final Enumeration entries = zipFile.entries();
            while (entries.hasMoreElements()) {
                final Object object = entries.nextElement();
                if (object instanceof ZipEntry) {
//                    ZipEntry ze = (ZipEntry) object;
//                    name = ze.getName();
					name = ((ZipEntry) object).getName();
                    if (name.endsWith(ARCHIVE_SEPARATOR)) {
                        name = name.substring(0,
                            name.lastIndexOf(ARCHIVE_SEPARATOR));
                    }

                    archiveMap.put(name, object);
                }
            }
        }
        catch (Exception ex) {
            success = false;
        }

        return success;
    }

//	private void setArchiveMap(HashMap archiveMap) {
//        this.archiveMap = archiveMap;
//    }

    /**
     * Получить список имен файлов, находящихся в данной папке в архиве
     * @return массив имен файлов
     */
	@Override
    public String[] list() {
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
            }
            else {
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
        String [] list = new String[names.size()];
        for (int i=0; i<list.length; i++) {
            list[i] = names.get(i).toString();
        }
        return list;
    }
    
    /**
     * Получить список файлов, находящихся в данной папке в архиве
     * @return массив файлов
     */
	@Override
    public AbsoluteFile[] getFiles(FileFilter filter) {
		return getFiles();
//        final String[] names = list();
//        MyZipFile[] files = null;
//        if (names != null) {
//            files = new MyZipFile[names.length];
//            String name;
//            for (int i = 0; i < names.length; ++i) {
//
//                if (null != zipEntry){
//                    name = zipEntry.getName() + names[i];
//                }
//                else {
//                    name = names[i];
//                }
//                files[i] = new MyZipFile(this, (ZipEntry) archiveMap.get(name));
//            }
//        }
//        return files;
    }
	@Override
    public AbsoluteFile[] getFiles() {
        final String[] names = list();
        MyZipFile[] files = null;
        if (names != null) {
            files = new MyZipFile[names.length];
            String name;
            for (int i = 0; i < names.length; ++i) {

                if (null != entry){
                    name = entry.getName() + names[i];
                }
                else {
                    name = names[i];
                }
                files[i] = new MyZipFile(this, (ZipEntry) archiveMap.get(name));
            }
        }
        return files;
    }

    /**
     * Определить, является ли данный объект файлом или каталогом
     * @return <B>true</B>, если данный объект является каталогом, иначе - <B>false</B>
     */
	@Override
    public boolean isDirectory() {
        return (null == entry) ? archive.isDirectory() : entry.isDirectory();
    }
    
    /**
     * Получить родительскую папку для данного файла
     * @return объект класса AbsoluteFile, представляющий родительскую папку данного файла
     */
	@Override
    public AbsoluteFile getAbsoluteParent() {
        return parent;
        //return getAbstractParent();
    }
    
    /**
     * Представляет размер в более наглядной форме. Например, число
     * 12455 будет иметь вид 12 455
     * @return строка содержащая размер файла в отформатированном виде
     */
	@Override
    public String getFormatFileSize() {
        if (null == entry) return getFormatFileSize(archive.length());
        if (true == entry.isDirectory()) return DEFAULT_DIR_SIZE;
        return getFormatFileSize(entry.getSize());
    }
    
    /**
     * Возвращает дату последеней модификации файла или каталога
     * @return дату последеней модификации в формате dd.MM.yyyy hh:mm
     */
	@Override
    public String getLastModifiedDate() {
        Date date=new Date();
        SimpleDateFormat sdate=new SimpleDateFormat(DEFAULT_DATE_FORMAT);
        if (null == entry)
            date.setTime(((LocalFile)parent).lastModified());
        else 
            date.setTime(entry.getTime());
        return sdate.format(date);

    }
    
    /**
     * Возвращает атрибуты файла или каталога. ro - только для чтения,
     * h - скрытый
     * @return строку, содержащую атрибуты файла или каталога
     */
	@Override
    public String getAtributeString() {
        if (null == entry) return ((LocalFile)parent).getAtributeString();
        return "ro";
    }
    
    /**
     * Получить поток для чтения из файла
     * @return поток для чтения из файла
     * @throws java.lang.Exception вызывается, если при создании потока произошла ошибка
     */
	@Override
    public java.io.InputStream getInputStream() throws java.io.IOException {
        if (null == entry)
            return parent.getInputStream();
        return new ZipFile(archive).getInputStream(entry);
    }
    
    /**
     * Получить поток для записи в файл
     * @throws java.lang.Exception вызывается, если при создании потока произошла ошибка
     * @return поток для записи в файл
     */
	@Override
    public java.io.OutputStream getOutputStream() throws java.io.IOException {
        if (null == entry)
            return parent.getOutputStream();
        return null;
    }
    
    /**
     * Получить дату последней модификации файла
     * @return дата последней модификации файла - число миллисекунд прошедших с 00:00:00 1 
     * января 1970; если файл не существует возвращается 0L
     */
//    public long lastModified() {
//        if (null == zipEntry) return parent.lastModified();
//        return zipEntry.getTime();
//    }
    
    /**
     * Получить строковое представление объекта
     * @return строковое представление
     */
	@Override
    public String toString() {
        if (false == parent.hasParent()) return parent + getFilename();
        return parent + separator + getFilename();
    }

	@Override
    public long getLastModifiedTime() {
        if (null == entry)
            return parent.getLastModifiedTime();
        else 
            return entry.getTime();
    }
    
	@Override
    public long length() {
        if (null == entry)
            return archive.length();
        else
            return entry.getSize();
    }
    
	@Override
     public String getPathWithSlash() {
         String path = getAbsolutePath();
         //if (false == isDirectory() ) return super.getAbsolutePath();
         return (super.getAbsolutePath().endsWith(separator)) ? path : path + separator;
     }

}
