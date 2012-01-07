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
 * BaseThread.java
 * Created on 23.01.2009 14:59:33
 */
package ru.narod.jcommander.threads.newThreads;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.JDialog;

/**
 *
 * @author Programmer
 */
public class BaseThread extends Thread {

    protected String currentAction;
    protected double totalFiles = 1;
    protected double filesReady = 0;
    protected boolean interrupt = false;
    protected long currentProgress = 0;
    protected long currentFileSize = 1;
    protected JDialog parent = null;
//	protected JComponent parent = null;

    public BaseThread() {
    }

    public BaseThread(String name) {
        super(name);
    }
    
    public void setInterrupt(boolean interrupt) {
        this.interrupt = interrupt;
    }

    public String getCurrentAction() {
        return currentAction;
    }

    public int getCurrentProgress() {
        return (int) (100. * currentProgress / currentFileSize);
    }

    public int getTotalProgerss() {
        return (int) (100. * filesReady / totalFiles);
    }

    public void setFrameParent(JDialog dialog) {
        this.parent = dialog;
    }
}