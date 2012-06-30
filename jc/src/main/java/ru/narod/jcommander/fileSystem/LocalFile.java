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
 * Created on 27 mar 2006, 10:16
 *
 */
package ru.narod.jcommander.fileSystem;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Класс задает файл или каталог файловой системы
 *
 * @author Pavel Ovcharov
 */
public class LocalFile extends File implements BaseFile {

    static protected String DEFAULT_DATE_FORMAT = "dd.MM.yyyy HH:mm";
    static protected String DEFAULT_DIR_SIZE = "<DIR>";

    /**
     * Создать экземпляр класса LocalFile. По умолчанию это текущий каталог
     * файловой системы
     */
    public LocalFile() {
        super(RootFileSystem.getReadableRoot().getAbsolutePath());
    }

    /**
     * Создать экземпляр класса LocalFile с именем (путем) <I>fileName</I>
     *
     * @param fileName путь к файлу
     */
    public LocalFile(String fileName) {
        super(fileName);
    }

    /**
     * Создать экземпляр класса LocalFile по файлу anotherFile
     *
     * @param anotherFile объект класс java.io.File
     * @see java.io.File
     */
    public LocalFile(File anotherFile) {
        super(anotherFile.getAbsolutePath());
    }

    /**
     * Создать экземпляр класса LocalFile с именем <I>fileName</I> в папке,
     * заданной файлом <I>parentFile</I>.
     *
     * @param parentFile объект класса java.io.File задает путь к каталогу
     * @param fileName задает имя создаваемого файла
     */
    public LocalFile(File parentFile, String fileName) {
        super(parentFile, fileName);
    }

    /**
     * Создать экземпляр класса LocalFile с именем <I>fileName</I> в папке,
     * заданной файлом <I>parentFile</I>.
     *
     * @param parent объект класса LocalFile задает путь к каталогу
     * @param child задает имя создаваемого файла
     */
    public LocalFile(LocalFile parent, String child) {
        super(parent, child);
    }

    /**
     * Создать экземпляр класса LocalFile как копию файла aFile
     *
     * @param aFile объект класса LocalFile
     */
    public LocalFile(LocalFile aFile) {
        super(aFile.getAbsolutePath());
    }

    /**
     * Создать экземпляр класса LocalFile как копию файла aFile
     *
     * @param aFile объект класса BaseFile
     */
    public LocalFile(BaseFile aFile) {
        super(aFile.getAbsolutePath());
    }

    /**
     * Возвращает расширение файла
     *
     * @return пустую строку, если текущим файлом является каталог, иначе
     * возвращается строка, содержащая расширение файла
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
     * Позволяет получить имя файла или каталога
     *
     * @return имя текущего файла (без расширения) или каталога
     */
    public String getAbstractFileName() {
        String name = super.getName();
        if (name.equals("")) {
            return getAbsolutePath();
        }
        
        if (super.isDirectory()) {
            return name;
        }
        int len = name.length();
        for (int i = len - 1; i >= 0; i--) {
            if (name.charAt(i) == '.') {
                return name.substring(0, i);
            }
        }
        return name;
    }

    /**
     * Получить родительский каталог данного файла
     *
     * @return родительский каталог; если родительского каталога нет,
     * возвращается <B>null</B>
     */
    public LocalFile getAbstractParent() {
        if (super.getParentFile() != null) {
            return new LocalFile(super.getParentFile());
        } else {
            return null;
        }
    }

    /**
     * Определить, есть ли родительский каталог у данного файла
     *
     * @return <B>true</B>если родительский каталог есть, иначе - <B>false</B>
     */
    public boolean hasParent() {
        return (null != super.getParentFile());

    }

    /**
     * Возвращает имя файла или каталога
     *
     * @return имя текщего каталога или файла (с расширением)
     */
    public String getFilename() {
        return super.getName();
    }

    /**
     * Возвращает относительный путь (относительный относительно path)
     *
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
     * Возвращает дату последеней модификации файла или каталога
     *
     * @return дату последеней модификации в формате dd.MM.yyyy hh:mm
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
     * Возващает размер файла
     *
     * @return возвращается, количество байт занимаемых файлом
     */
    public long getSize() {
        return length();
    }

    /**
     * Представляет размер в более наглядной форме. Например, число 12455 будет
     * иметь вид 12 455
     *
     * @return строка содержащая размер файла в отформатированном виде
     */
    public String getFormatFileSize() {
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
     * Возвращает атрибуты файла или каталога. ro - только для чтения, h -
     * скрытый
     *
     * @return строку, содержащую атрибуты файла или каталога
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

    /**
     * Определяет совпадают ли пути файлов
     *
     * @param aFile объект класса LocalFile
     * @return <B>true</B>, если пути данного файла и файла aFile совпадают,
     * иначе - <B>false</B>
     */
    public boolean equals(LocalFile aFile) {
        return this.getAbsolutePath().equals(aFile.getAbsolutePath());
    }

    /**
     * Получить файл, соответсвущий корню диска ('C:\', 'D:\' и т.д. для
     * Windows, '/' для Linux)
     *
     * @return объект класса BaseFile - корень диска
     */
    public BaseFile getRoot() {
        LocalFile root = this;
        while (root.hasParent()) {
            root = root.getAbstractParent();
        }
        return root;
    }

    /**
     * Получить родительский каталог данного файла
     *
     * @return родительский каталог - объект класса BaseFile; если родительского
     * каталога нет, возвращается <B>null</B>
     */
    public BaseFile getAbsoluteParent() {
        return getAbstractParent();
    }

    /**
     * Получить поток для чтения из файла
     *
     * @return поток для чтения из файла
     * @throws java.lang.Exception вызывается, если при создании потока
     * произошла ошибка
     */
    public java.io.InputStream getInputStream() throws java.io.IOException {
        return new FileInputStream(this);
    }

    /**
     * Получить поток для записи в файл
     *
     * @throws java.lang.Exception вызывается, если при создании потока
     * произошла ошибка
     * @return поток для записи в файл
     */
    public java.io.OutputStream getOutputStream() throws java.io.IOException {
        return new FileOutputStream(this);
    }

    /**
     * Получить абсолютный путь к файлу с разделителем в конце ("C:\123\",
     * "C:\123\1.zip\", "/home/user/")
     *
     * @return абсолютный путь к файлу
     */
    public String getPathWithSlash() {
        String path = super.getAbsolutePath();
        if (false == isDirectory()) {
            return super.getAbsolutePath();
        }
        return (super.getAbsolutePath().endsWith(separator)) ? path : path + separator;
    }

    /**
     * Физически переименовывает данный файл в файл, заданный файлом
     * <I>targetFile</I>
     *
     * @param targetFile задает путь к новому файлу
     * @return <B>true</B>,если переименование прошло успешно; иначе -
     * <B>false</B>
     */
    public boolean renameTo(BaseFile targetFile) {
        return super.renameTo((File) targetFile);
    }

    public long getLastModifiedTime() {
        return lastModified();
    }

    /**
     * Получить список файлов в данном каталоге
     *
     * @return массив файлов в данном каталоге
     */
    public BaseFile[] getFiles(FileFilter filter) {
        File[] list = super.listFiles(filter);
        if (null == list) {
            return null;
        }
        LocalFile[] af = new LocalFile[list.length];
        for (int i = 0; i < list.length; i++) {
            af[i] = new LocalFile(list[i]);
        }
        return af;
    }

    /**
     * Получить список файлов в данном каталоге
     *
     * @return массив файлов в данном каталоге
     */
    public BaseFile[] getFiles() {
        File[] list = super.listFiles();
        if (null == list) {
            return null;
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
        if (isLocal()) {
            return (File) this;
        } else {
            return null;
        }
    }
}
