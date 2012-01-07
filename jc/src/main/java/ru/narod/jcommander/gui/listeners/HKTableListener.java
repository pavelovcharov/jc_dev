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
 * HKTableListener.java
 *
 * Created on 16 ��� 2007 �., 15:18
 *
 */
package ru.narod.jcommander.gui.listeners;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JTable;

import ru.narod.jcommander.gui.MainFrame;
import ru.narod.jcommander.gui.dialog.WarningDialog;
import ru.narod.jcommander.gui.message.KeyShortcat;
import ru.narod.jcommander.gui.message.Message;
import ru.narod.jcommander.util.LanguageBundle;

/**
 *
 * @author Programmer
 * @version
 */
public class HKTableListener implements KeyListener, ru.narod.jcommander.prefs.PrefKeys {

    private JTable table;
    private MainFrame parent;
    private KeyShortcat oldKs;
    private KeyShortcat newKs;

    /** Creates a new instance of HKTableListener */
    public HKTableListener(MainFrame parent) {
        this.parent = parent;
        table = parent.getHotKeysTable();
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if ((KeyEvent.VK_CONTROL == keyCode) || (KeyEvent.VK_ALT == keyCode) ||
                (KeyEvent.VK_SHIFT == keyCode)) {
            return;
        }
        if (KeyEvent.VK_ESCAPE == e.getKeyCode()) {
            table.setValueAt(null, table.getSelectedRow(), 2);
            return;
        }
        if (false == ((Message) table.getValueAt(table.getSelectedRow(), 0)).isAction()) {
            return;
        }
        oldKs = (KeyShortcat) table.getValueAt(table.getSelectedRow(), 2);
        newKs = new KeyShortcat(e);
        int defRow = findShortcat(newKs, 1);
        int userRow = findShortcat(newKs, 2);
        int row = userRow == -1 ? defRow : userRow;
        if (-1 != row) {
            LanguageBundle lb = LanguageBundle.getInstance();
            String[] options = {lb.getString("StrOk"), lb.getString("StrCancel")};
            String msg = lb.format("StrHotKeyExists", newKs, table.getValueAt(row, 0));
            int result = WarningDialog.showMessage(parent.getPreferencesDialog(), msg,
                    lb.getString("StrJC"), options, WarningDialog.MESSAGE_WARNING, 0);
            if (0 != result) {
                table.setValueAt(oldKs, table.getSelectedRow(), 2);
                return;
            }
            if (-1 != userRow) {
                table.setValueAt(null, userRow, 2);
            }
        }
        table.setValueAt(newKs, table.getSelectedRow(), 2);
    }

    private int findShortcat(KeyShortcat newKs, int col) {
        KeyShortcat ks;
        for (int i = 0; i < table.getRowCount(); i++) {
            ks = (KeyShortcat) table.getValueAt(i, col);
            if (newKs.equals(ks)) {
                return i;
            }
        }
        return -1;
    }
}
