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
 * AbsoluteFile.java
 *
 * Created on 24 Ноябрь 2006 г., 19:54
 *
 */

package omegaCommander.fileSystem;
import java.io.FileFilter;


/**
 * Интерфейс определяет методы, которые должны быть реализованы во всех типах
 * файлов
 * @author Pavel Ovcharov
 */
public interface AbsoluteFile {
    /**
     * Получить имя файла (без расширения)
     * @return имя файла
     */
    public String getAbstractFileName();
    /**
     * Получить расширение файла
     * @return расширение файла
     */
    public String getExtention();
    
    /**
     * Получить список файлов, если данный объект является каталогом.
     * @return список файлов в данном каталоге
     */
    public AbsoluteFile [] getFiles();
    
    /**
     * Получить отфильтрованный список файлов, если данный объект является каталогом.
     * @return список файлов в данном каталоге
     */
    public AbsoluteFile [] getFiles(FileFilter filter);
    /**
     * получить каталог, родительский по отношению к данному файлу
     * @return родительский каталог
     */
    public AbsoluteFile getAbsoluteParent();
    /**
     * Определить, является ли данный объект файлом или каталогом
     * @return <B>true</B>, если данный объект является каталогом, иначе - <B>false</B>
     */
    public boolean isDirectory();
    /**
     * Представляет размер в более наглядной форме. Например, число
     * 12455 будет иметь вид 12 455
     * @return строка содержащая размер файла в отформатированном виде
     */
    public String getFormatFileSize();
    /**
     * Возвращает дату последеней модификации файла или каталога
     * @return дату последеней модификации в формате dd.MM.yyyy hh:mm
     */
    public String getLastModifiedDate();
     /**
     * Возвращает атрибуты файла или каталога. ro - только для чтения,
     * h - скрытый
     * @return строку, содержащую атрибуты файла или каталога
     */
    public String getAtributeString();
    /**
     * Определить, есть ли родительский каталог у данного файла
     * @return <B>true</B>если родительский каталог есть, иначе - <B>false</B>
     */
    public boolean hasParent();
    /**
     * Метод позволяет определить, существует ли файл физически на диске
     * @return <B>true</B>, ели файл существует, <B>false</B> - иначе
     */
    public boolean exists();
    /**
     * Получить родительский каталог данного файла
     * @return родительский каталог - объект класса AbsoluteFile; если
     * родительского каталога нет, возвращается <B>null</B>
     */
    public String getAbsolutePath();
    /**
     * Получить файл, соответствующий корню диска ('C:\', 'D:\' и т.д.
     * для Windows, '/' для Linux)
     * @return объект класса AbsoluteFile - корень диска
     */
    public AbsoluteFile getRoot();
    /**
     * Получить поток для чтения из файла
     * @return поток для чтения из файла
     * @throws java.lang.Exception вызывается, если при создании потока произошла ошибка
     */
    public java.io.InputStream getInputStream() throws java.io.IOException;
    /**
     * Получить дату последней модификации файла
     * @return дата последней модификации файла - число миллисекунд прошедших с 00:00:00 1 
     * января 1970; если файл не существует возвращается 0L
     */
    //public long lastModified();
    
    /**
     * Удалит данный файл с диска
     * @return <B>true</B>, если файл был успешно удален, иначе - <B>false</B>
     */
    public boolean delete();
    /**
     * Получить поток для записи в файл
     * @throws java.lang.Exception вызывается, если при создании потока произошла ошибка
     * @return поток для записи в файл
     */
    public java.io.OutputStream getOutputStream() throws java.io.IOException;
    /**
     * Установить дату последеного изменения файла
     * @param time дата последней модификации - число миллисекунд, прошедших с начала эпохи
     * @return <B>true</B>, если дата успешно изменена, иначе - <B>false</B>
     */
    public boolean setLastModified(long time);
    /**
     * Получить абсолютный путь к файлу, для каталога - с разделителем в конце
     * ("C:\123\", "/home/user/")
     * @return абсолютный путь к файлу
     */
    public String getPathWithSlash();
    /**
     * Создать папки на пути, котоый задан данным файлом
     * @return <B>true</B>, если папки были созданы, иначе - <B>false</B>
     */
    public boolean mkdirs();
    /**
     * Переименовать данный файл в файл с именем, заданным <I>targetFile</I>
     * @param targetFile файл-преемник
     * @return <B>true</B>, если файл был переименовн, иначе - <B>false</B>
     */
    public boolean renameTo(AbsoluteFile targetFile);
    /**
     * Определить, можно ли прочитать данный файл
     * @return <B>true</B>, если файл доступен для чтения, иначе - <B>false</B>
     */
    public boolean canRead();
    /**
     * Создать новый файл
     * @return <B>true</B>, если файл был создан, иначе - <B>false</B>
     * @throws java.io.IOException исключение вызывается, если при созданни файла произошли ошибки
     */
    public boolean createNewFile() throws java.io.IOException;
    /**
     * Определить размер файла
     * @return размер файла в байтах
     */
    public long length();
    
    public String getFilename();
    
    public long getLastModifiedTime();
    
    public boolean isHidden();
}
