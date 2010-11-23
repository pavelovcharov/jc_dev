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
package omegaCommander.threads.newThreads;

import java.util.Vector;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import omegaCommander.gui.MainFrame.GeneratedListModel;
import omegaCommander.util.LanguageBundle;

/**
 *
 * @author Programmer
 */
public class SearchStatusThread extends Thread {

    private NewSearchThread nst;
    private JLabel label;
    private JList list;
    private JButton button;

    /** Creates a new instance of SearchStatusThread */
    public SearchStatusThread(NewSearchThread nst, JLabel label, JList list, JButton button) {
        this.nst = nst;
        this.label = label;
        this.list = list;
        this.button = button;
    }

    @Override
    public void run() {
        while (State.RUNNABLE != nst.getState()) {
        }
        LanguageBundle lb = LanguageBundle.getInstance();
        label.setText(lb.getString("StrSearch") + "...");
        Vector al = new Vector();
        GeneratedListModel model = (GeneratedListModel)list.getModel();
        while (nst.isAlive()) {
            al = nst.getResultList();
//            list.setListData(al.toArray());
            model.addData(al);
            try {
                sleep(500);
            } catch (InterruptedException exc) {
            }
        }
        al = nst.getResultList();
//        list.setListData(al.toArray());
        model.addData(al);
//        ((GeneratedListModel)list.getModel()).sortByPath();
        label.setText(lb.getString("StrSearchComplete") + ". " + lb.getString("StrFound") + " " + model.getSize());
        button.setText(lb.getString("StrSearch"));
    }
}
