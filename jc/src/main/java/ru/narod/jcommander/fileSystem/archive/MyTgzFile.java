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
 * MyTgzFile.java
 * Created on 04.05.2009 14:14:18
 */
package ru.narod.jcommander.fileSystem.archive;

import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import ru.narod.jcommander.fileSystem.BaseFile;
import ru.narod.jcommander.fileSystem.LocalFile;
import ru.narod.jcommander.tarlib.TGZArchive;
import ru.narod.jcommander.tarlib.TarEntry;

/**
 *
 * @author Programmer
 */
public class MyTgzFile extends MyTarFile {

    //XXX а если архив в сети?
    /**
     * Создать объект класса MyTarFile с помощью файла <I>parent</I>
     *
     * @param parent объект класса LocalFile, задающий положение архива
     */
    public MyTgzFile(LocalFile parent) {
        super(parent);
    }

    /**
     * Создать объект класса MyTarFile с помощью файла <I>parent</I>
     *
     * @param parent объект класса BaseFile
     */
    public MyTgzFile(BaseFile parent) {
        super(parent);
    }

    /**
     * Создать объект класса MyTarFile с помощью файла <I>parent</I> и имени
     * файла <I>child</I>
     *
     * @param parent задает абсолютный путь к файлу
     * @param child имя создавемого файла
     */
    public MyTgzFile(MyTgzFile parent, String child) {
        super(parent, child);
    }

    protected MyTgzFile(LocalFile parent, TarEntry tarEntry) {
        super(parent, tarEntry);
    }

    /**
     * Создать объект класса MyTgzFile. Используется для реализации просмотра
     * содержимого архива, находящегося внутри другого архива.
     *
     * @param parent объект класса ArchiveFile, представляющий собой архив,
     * содержимое которого нужно просмотреть
     * @param archive задает путь к архиву на диске (например, во временной
     * папке)
     */
    public MyTgzFile(ArchiveFile parent, LocalFile archive) {
        this(archive);
        this.parent = parent;
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
        try {
            if (null == entry) {
                return parent.getInputStream();
            }
            final TGZArchive tarArchive = new TGZArchive(archive.getAbsolutePath());
            return tarArchive.getInputStream(entry.getName());
        } catch (Exception ex) {
            Logger.getLogger(MyTgzFile.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    protected boolean getArchiveContent() {
        boolean success = true;
        archiveMap.clear();

        try {
            String name;
            final TGZArchive tarArchive = new TGZArchive(archive.getAbsolutePath());
            final Collection entries = tarArchive.getEntries();
            for (Object object : entries) {
                if (object instanceof TarEntry) {
                    TarEntry te = (TarEntry) object;
                    name = te.getName();
                    if (te.isDirectory()) {
                        name = name.substring(0,
                                name.lastIndexOf(ARCHIVE_SEPARATOR));
                    }
                    archiveMap.put(name, te);
                }

            }
        } catch (Exception ex) {
            success = false;
        }

        return success;
    }

    @Override
    public BaseFile[] getFiles() {
        final String[] names = list();
        MyTgzFile[] files = null;
        if (names != null) {
            files = new MyTgzFile[names.length];
            String name;
            for (int i = 0; i < names.length; ++i) {

                if (null != entry) {
                    name = entry.getName() + names[i];
                } else {
                    name = names[i];
                }
                files[i] = new MyTgzFile(this, (TarEntry) archiveMap.get(name));
            }
        }
        return files;
    }
}
