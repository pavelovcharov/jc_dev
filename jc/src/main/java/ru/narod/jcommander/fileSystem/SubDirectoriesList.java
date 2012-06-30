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

import java.util.ArrayList;

/**
 * Класс предоставляет метод для создания и получения списка файлов и
 * подкаталогов из заданного каталога
 *
 * @author Sniper
 * @version 2005/05/08 12:15:18 PM
 */
public abstract class SubDirectoriesList {

    private static ArrayList list;

    private static void getFilesFromSubDirectories(BaseFile[] fileList) {
        int i;
        if (null == fileList) {
            return;
        }
        for (i = 0; i < fileList.length; i++) {
            if (fileList[i].isDirectory()) {
                list.add(fileList[i]);
                BaseFile[] files = fileList[i].getFiles();
                if (null == files) {
                    return;
                }
                getFilesFromSubDirectories(files);
            } else {
                list.add(fileList[i]);
            }
        }

    }

    /**
     * Возвращает созданный список файлов и подкаталогов
     *
     * @param aFile каталог, в котором выполняется поиск файлов и подкаталогов
     * @return Возвращает объект класса ArrayList, содержащий файлы и
     * подкаталои. Если исходный список не содержит файлов/папок, возвращается
     * пустой список.
     */
    public static ArrayList getList(BaseFile aFile) {
        if (null == aFile) {
            return null;
        }
        list = new ArrayList();
        if (aFile.isDirectory()) {
            list.add(aFile);
            getFilesFromSubDirectories(aFile.getFiles());
        } else {
            list.add(aFile);
        }
        return list;
    }
}
