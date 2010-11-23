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
package omegaCommander.gui.dialog;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import omegaCommander.gui.MainFrame;
import omegaCommander.gui.message.Message;
import omegaCommander.gui.message.MessageList;

class ButtonKeyListener implements KeyListener {

    private JDialog owner;

    public ButtonKeyListener(JDialog owner) {
        super();
        this.owner = owner;
    }

    public void keyTyped(KeyEvent e) {
    }

    public void keyPressed(KeyEvent e) {
        JButton button = (JButton) e.getSource();
        Message msg = MainFrame.findMessage(e);
        if (null != msg) {
            if (MessageList.MSG_ENTER == msg.getMessageID()) {
                button.doClick();
            }
        }
        switch (e.getKeyCode()) {
            case KeyEvent.VK_RIGHT: {
                button.transferFocus();
                break;
            }
            case KeyEvent.VK_DOWN: {
                button.transferFocus();
                break;
            }
            case KeyEvent.VK_LEFT: {
                button.transferFocusBackward();
                break;
            }
            case KeyEvent.VK_UP: {
                button.transferFocusBackward();
                break;
            }
            case KeyEvent.VK_ESCAPE: {
                owner.setVisible(false);
                break;
            }
        }
    }

    public void keyReleased(KeyEvent e) {
    }
}
