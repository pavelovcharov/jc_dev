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
 * CursorKeyListener.java
 * Created on 03.07.2009 11:04:27
 */
package omegaCommander.gui.dialog;

import java.awt.Component;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 *
 * @author Programmer
 */
public class CursorKeyListener implements KeyListener {

    public CursorKeyListener() {
        super();
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        Component c = (Component) (e.getSource());
        switch (e.getKeyCode()) {

            case KeyEvent.VK_DOWN: {
                c.transferFocus();
                break;
            }
            case KeyEvent.VK_UP: {
                c.transferFocusBackward();
                break;
            }
            case KeyEvent.VK_LEFT: {
                c.transferFocusBackward();
                break;
            }
            case KeyEvent.VK_RIGHT: {
                c.transferFocus();
                break;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
