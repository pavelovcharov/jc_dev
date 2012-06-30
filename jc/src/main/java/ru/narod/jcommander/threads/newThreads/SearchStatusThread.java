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
 * SearchStatusThread.java
 * Created on 20.04.2009 15:00:30
 */
package ru.narod.jcommander.threads.newThreads;

import java.util.ArrayList;
import java.util.List;
import ru.narod.jcommander.gui.search.SearchStatusListener;

/**
 *
 * @author Programmer
 */
public class SearchStatusThread extends Thread {

    private NewSearchThread nst;
    private final static String name = "SearchStatusThread";

    /** Creates a new instance of SearchStatusThread */
    public SearchStatusThread(NewSearchThread nst) {
        super(name);
        this.nst = nst;
    }

    @Override
    public void run() {
        while (State.RUNNABLE != nst.getState()) {
        }
        if (listeners != null) {
            for (int i = 0; i < listeners.size(); i++) {
                listeners.get(i).onSearchStart();
            }
        }
        while (nst.isAlive()) {
            try {
                sleep(500);
            } catch (InterruptedException exc) {
            }
            if (listeners != null) {
                for (int i = 0; i < listeners.size(); i++) {
                    listeners.get(i).onSearchProgressChange(nst.getResultList());
                }
            }
        }
        if (listeners != null) {
            for (int i = 0; i < listeners.size(); i++) {
                listeners.get(i).onSearchEnd(nst.getResultList());
            }
        }
    }
    private List<SearchStatusListener> listeners = null;

    public void addListener(SearchStatusListener listener) {
        if (listeners == null) {
            listeners = new ArrayList<SearchStatusListener>(1);
        }
        listeners.add(listener);
    }
}
