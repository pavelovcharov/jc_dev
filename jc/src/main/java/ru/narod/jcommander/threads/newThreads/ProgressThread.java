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
 * ProgressThread.java
 * Created on 12.03.2009 9:58:12
 */
package ru.narod.jcommander.threads.newThreads;

import ru.narod.jcommander.gui.dialog.ProgressDialog;

/**
 *
 * @author Programmer
 */
public class ProgressThread extends Thread {

    private BaseThread baseThread;
    private ProgressDialog pd;

    public ProgressThread(BaseThread baseThread, ProgressDialog pd) {
        this.baseThread = baseThread;
        this.pd = pd;
    }

    @Override
    public void run() {
        while (baseThread.isAlive()) {
            pd.setCurrentProgress(baseThread.getCurrentProgress());
            pd.setTotalProgress(baseThread.getTotalProgerss());
            pd.setCurrentAction(baseThread.getCurrentAction());
            try {
                sleep(700);
            } catch (InterruptedException ex) {
            }
        }
        pd.hide();
    }
}
