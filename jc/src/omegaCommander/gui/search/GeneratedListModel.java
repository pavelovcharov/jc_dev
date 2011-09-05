/*
 * This file is part of jc, http://www.jcommander.narod.ru
 * Copyright (C) 2005-2011 Pavel Ovcharov
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

package omegaCommander.gui.search;

import java.util.Collection;
import java.util.Collections;
import java.util.Vector;
import javax.swing.AbstractListModel;
import omegaCommander.fileSystem.BaseFile;

/**
 *
 * @author master
 */
public class GeneratedListModel extends AbstractListModel {

    private final Vector data = new Vector();

    public GeneratedListModel() {
    }

    private void update() {
        this.fireContentsChanged(this, 0, getSize());
    }

    private void update(int start, int end) {
        this.fireIntervalAdded(this, start, end);
    }

    public void addData(Collection v) {
        data.addAll(v);
        update(getSize() - v.size(), getSize());
    }

    @Override
    public int getSize() {
        return data.size();
    }

    @Override
    public Object getElementAt(int index) {
        return data.elementAt(index);
    }

    public void sortByPath() {
        Collections.sort(data);
        update();
    }

    public void clear() {
        data.clear();
        update();
    }
    public BaseFile[] getList() {
        return (BaseFile[]) data.toArray(new BaseFile[0]);
    }
}