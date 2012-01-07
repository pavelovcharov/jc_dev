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
 * BaseComboBoxListener.java
 *
 * Created on 21 ��� 2007 �., 13:06
 *
 */

package ru.narod.jcommander.gui.listeners.combobox;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedHashMap;
import javax.swing.JComboBox;

import ru.narod.jcommander.gui.message.KeyShortcat;
import ru.narod.jcommander.gui.message.Message;
import ru.narod.jcommander.gui.message.MessageID;

/**
 *
 * @author Programmer
 * @version
 */
public class BaseComboBoxListener implements KeyListener, MessageID{
    
    private JComboBox comboBox;
    private LinkedHashMap defaultMap;
    private LinkedHashMap userMap;

    /**
     * Creates a new instance of BaseComboBoxListener
     */
    public BaseComboBoxListener(JComboBox comboBox, LinkedHashMap defaultMap, LinkedHashMap userMap) {
        this.comboBox = comboBox;
        this.defaultMap = defaultMap;
        this.userMap = userMap;
    }

    public void keyTyped(KeyEvent e) {
    }

    public void keyPressed(KeyEvent e) {
        if (KeyEvent.VK_ESCAPE == e.getKeyCode()) {
            comboBox.firePopupMenuCanceled();
            return;
        }
        KeyShortcat ks = new KeyShortcat(e);
        Message msg = (Message)userMap.get(ks);
        if (null == msg){
            msg = (Message)defaultMap.get(ks);
        }
        if (null == msg) {
            return;
        }
        int index = 0;
        switch(msg.getMessageID()) {

            case MSG_UP: {
                index = comboBox.getSelectedIndex()-1;
                if (0 > index) index = comboBox.getItemCount()-1;
                break;
            }
            case MSG_DOWN: {
                index = comboBox.getSelectedIndex()+1;
                if (comboBox.getItemCount() == index) index = 0;
                break;
            } 
            case MSG_FIRST: {
                index = 0;
                break;
            }
            case MSG_LAST: {
                index = comboBox.getItemCount()-1;
                break;
            }            
            case MSG_ENTER: {
                comboBox.setPopupVisible(false);
                return;
            }
            default:
                break;
        }
        comboBox.setSelectedIndex(index);
    }

    public void keyReleased(KeyEvent e) {
    }
    
}
