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
 * Directory.java
 *
 * Created on 15 oct 2006, 11:39
 */
package ru.narod.jcommander.gui.table.tableElements;

import java.awt.Color;
import ru.narod.jcommander.fileSystem.BaseFile;

/**
 * Класс определеяет элемент таблицы FileTable - каталог файловой системы
 *
 * @author Pavel Ovcharov
 */
public class Directory extends Name {

    private String DIR_CHARACTER_LEFT = "";
    private String DIR_CHARACTER_RIGHT = "";

    /**
     * Создать новый экземпляр класса Directory для каталога aFile. Каталог
     * будет отображаться в таблице в соответсвии с заданными цветами
     * <I>fontColor</I> и <I>bgColor</I>
     *
     * @param aFile каталог файловой системы
     * @param fontColor цвет шрифта
     * @param bgColor цвет фона
     */
    public Directory(BaseFile aFile, Color fontColor, Color bgColor) {
        super(aFile, fontColor, bgColor);
    }

    /**
     * Создать новый экземпляр класса Directory для каталога aFile. Каталог
     * будет отображаться в таблице в соответсвии с заданными цветами
     * <I>fontColor</I> и <I>bgColor</I>
     *
     * @param aFile каталог файловой системы
     */
    public Directory(BaseFile aFile) {
        super(aFile);
    }

    /**
     * Получить строковое представление объекта
     *
     * @return строковое представление
     */
    @Override
    public String toString() {
        return DIR_CHARACTER_LEFT + name + DIR_CHARACTER_RIGHT;
    }

    /**
     * Определить одинаковы ли два объекта - данный и <I>obj</I>
     *
     * @param obj сравниваемый объект
     * @return <B>true</B>, если объекты одинаковы, иначе - <B>false</B>
     */
    @Override
    public boolean equals(Object obj) {
        return (obj instanceof Directory) ? (getName().equals(((Directory) obj).getName())) : false;
    }
}
