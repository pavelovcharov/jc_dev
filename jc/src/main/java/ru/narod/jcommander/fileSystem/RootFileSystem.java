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

import java.io.File;
import java.util.ArrayList;
import ru.narod.jcommander.fileSystem.net.NetFile;

/**
 * Класс предоставляет методы для определения списка разделов и сменных
 * устройств (приводов CD-ROM, дисководов)
 *
 * @author Sniper
 * @version 2005/04/24 1:12:37 PM
 */
abstract public class RootFileSystem {

    static public String FDD_1 = "a:" + java.io.File.separator;
    static public String FDD_2 = "b:" + java.io.File.separator;

    /**
     * Возвращает список разделов
     *
     * @return массив класса File, содержащий файлы разделов
     */
    public static BaseFile[] getRoots() {
        java.io.File[] roots = java.io.File.listRoots();
        ArrayList list = new ArrayList();

        for (int i = 0; i < roots.length; i++) {
            list.add(new LocalFile(roots[i]));
        }
        NetFile netFile = new NetFile("smb://");

        list.add(netFile);
        BaseFile[] obj = new BaseFile[list.size()];
        for (int i = 0; i < list.size(); i++) {
            obj[i] = (BaseFile) list.get(i);
        }
        return obj;
    }

    /**
     * Проверяет является ли файл файлом раздела
     *
     * @return <b>true, если <i>file</i> является файлом раздела, иначе -
     * <b>false</b>
     * @param file файл
     */
    public static boolean isRoot(LocalFile file) {
        File[] roots = File.listRoots();
        for (int i = 0; i < roots.length; i++) {
            if (new LocalFile(roots[i]).equals(file)) {
                return true;
            }
        }
        return false;

    }

    public static LocalFile getReadableRoot() {
        File[] rootArray = File.listRoots();
        for (int i = 0; i < rootArray.length; i++) {
            if (rootArray[i].getAbsolutePath().compareToIgnoreCase(RootFileSystem.FDD_1) == 0
                    || rootArray[i].getAbsolutePath().compareToIgnoreCase(RootFileSystem.FDD_2) == 0) {
                continue;
            }
            if (rootArray[i].canRead()) {
                return new LocalFile(rootArray[i]);
            }
        }
        return null;
    }
}
