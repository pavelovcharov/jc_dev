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

import java.awt.Component;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JButton;
import javax.swing.JDialog;

class ComponentKeyListener implements KeyListener {

    private JDialog owner;
    private JButton defaultButton;

    public ComponentKeyListener(JDialog owner, JButton defaultButton) {
        super();
        this.defaultButton = defaultButton;
        this.owner = owner;
    }

    public void keyTyped(KeyEvent e) {
    }

    public void keyPressed(KeyEvent e) {
        Component c = (Component) (e.getSource());
        switch (e.getKeyCode()) {
            case KeyEvent.VK_ENTER: {
                defaultButton.doClick();
                break;
            }
            case KeyEvent.VK_ESCAPE: {
                owner.setVisible(false);
                break;
            }
//			case KeyEvent.VK_DOWN: {
//				c.transferFocus();
//				break;
//			}
//			case KeyEvent.VK_UP: {
//				c.transferFocusBackward();
//				break;
//			}
//			case KeyEvent.VK_LEFT: {
//				c.transferFocusBackward();
//				break;
//			}
//			case KeyEvent.VK_RIGHT: {
//				c.transferFocus();
//				break;
//			}
        }
    }

    public void keyReleased(KeyEvent e) {
    }
}
