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
 * ProgressDialog.java
 * Created on 28.01.2009 11:29:07
 */
package omegaCommander.gui.dialog;

import java.awt.Dimension;
import java.util.EventObject;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import omegaCommander.gui.MainFrame;
import omegaCommander.prefs.PrefKeys;
import omegaCommander.threads.newThreads.BaseThread;
import omegaCommander.util.LanguageBundle;

/**
 *
 * @author Programmer
 */
public class ProgressDialog implements PrefKeys, BaseDialog.BaseDialogActionEventListener {

    private JProgressBar jProgressBarCurrent = new JProgressBar();
    private JProgressBar jProgressBarTotal = new JProgressBar();
    private JLabel jLabelCurrentAction = new JLabel();
    private BaseDialog bd;
    private BaseThread bt;
    private MainFrame parent;

    public ProgressDialog(MainFrame parent, BaseThread bt) {
        this(parent, bt, false);

    }

    public ProgressDialog(MainFrame parent, BaseThread bt, boolean modal) {

        this.parent = parent;
        this.bt = bt;

        jProgressBarCurrent.setPreferredSize(new Dimension(jProgressBarCurrent.getWidth(), 25));

        jProgressBarCurrent.setStringPainted(true);
        jProgressBarTotal.setStringPainted(true);

        Object message[] = new Object[]{
            jLabelCurrentAction,
            jProgressBarCurrent,
            jProgressBarTotal,};
        LanguageBundle lb = LanguageBundle.getInstance();
        Object options[] = new Object[]{lb.getString("StrCancel")};

        bd = new BaseDialog(parent, lb.getString("StrJC"), message, options, 0);
        bd.setModal(modal);
        bd.addActionEventListener(this);
    }

    public void show() {
        if (Thread.State.TERMINATED == bt.getState()) {
            return;
        }
        bd.setVisible(true);
    }

    public void hide() {
        bd.setVisible(false);
    }

    public void setTotalProgress(int totalProgress) {
        jProgressBarTotal.setValue(totalProgress);
    }

    public void setCurrentProgress(int currentProgress) {
        jProgressBarCurrent.setValue(currentProgress);
    }

    public void setCurrentAction(String currentAction) {
        jLabelCurrentAction.setText(currentAction);
    }

    public JDialog getDialog() {
        return bd;
    }

    public void setCurrentProgressVisibility(boolean visible) {
        jProgressBarCurrent.setVisible(visible);
        bd.repack();
    }

    public void handleThreadEventClassEvent(EventObject e) {
        hide();
    }

    public void handleBaseDialogActionEvent(EventObject e) {
        int res = bd.getResult();
        if (0 == res || -1 == res) {//cancel clicked or X
            bt.setInterrupt(true);
        }
        while (bt.isAlive()) {
        }
        parent.updateMainWindow();
//        parent.requestFocus();
    }
}
