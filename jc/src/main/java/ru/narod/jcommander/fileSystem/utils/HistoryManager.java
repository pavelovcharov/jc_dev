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
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.narod.jcommander.fileSystem.utils;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;
import ru.narod.jcommander.fileSystem.BaseFile;

/**
 *
 * @author master
 */
public class HistoryManager {

    LinkedList<BaseFile> historyList = new LinkedList<BaseFile>();
    ListIterator<BaseFile> listIterator = historyList.listIterator();
    BaseFile current;

    public HistoryManager() {
    }

    public void put(BaseFile file) {
        //historyList.add(file);
        current = file;
        if (listIterator.hasNext()) {
            listIterator.next();
        }
        listIterator.add(file);
    }

    public Iterator<BaseFile> getIterator() {
        return historyList.iterator();
    }

    public BaseFile getCurrent() {
        return current;
    }
    boolean direction = false;

    public BaseFile next() {

        return listIterator.hasNext() ? listIterator.next() : null;
    }

    public BaseFile prev() {
        BaseFile prev = listIterator.hasPrevious() ? listIterator.previous() : null;
        if (!direction) {
            direction = !direction;
            BaseFile pr = prev();
            return pr != null ? pr : prev;
        }
        return prev;
    }
}
