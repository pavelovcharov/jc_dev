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
 * TableKeyListener.java
 *
 * Created on 19 Апрель 2007 г., 9:09
 *
 */
package omegaCommander.gui.listeners;

import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.util.LinkedHashMap;

import omegaCommander.actions.Action;
import omegaCommander.gui.message.KeyShortcat;
import omegaCommander.gui.message.Message;
import omegaCommander.gui.message.MessageID;
import omegaCommander.gui.MainFrame;

/**
 *
 * @author Programmer
 * @version
 */
public class TableKeyListener implements KeyListener, MessageID {

    private MainFrame parent;

    /**
     * Создает новый объект класса TableKeyListener
     *
     * @param parent главное окно приложения
     */
    public TableKeyListener(MainFrame parent) {
        this.parent = parent;
    }

    /**
     * Метод выполняется при нажатии клавишы
     */
    @Override
    public void keyTyped(KeyEvent e) {
    }

    /**
     * Метод выполняется, если клавиша была зажата
     */
    @Override
    public void keyPressed(KeyEvent e) {
        LinkedHashMap lhm = parent.getDefaultKeyList();
        LinkedHashMap userHK = parent.getUserKeyList();

        KeyShortcat ks = new KeyShortcat(e);

        Message msg = (Message) userHK.get(ks);
        if (null == msg) {
            msg = (Message) lhm.get(ks);
        }
        if (null == msg) {
            return;
        }
        int msgId = msg.getMessageID();
        Action action = null;
        switch (msgId) {
            case MSG_ENTER: {//enter
                action = parent.ACTION_ENTER;
                break;
            }
            case MSG_TAB: {//tab
                action = parent.ACTION_TAB;
                break;
            }
            case MSG_VIEW: {//F3
                action = parent.ACTION_VIEW;
                break;
            }
            case MSG_EDIT: {//F4
                action = parent.ACTION_EDIT;
                break;
            }
            case MSG_NEW_EDIT: {
                action = parent.ACTION_NEW_FILE;
                break;
            }
            case MSG_NEW_DIR: {//F7
                action = parent.ACTION_NEW_DIR;
                break;
            }
            case MSG_DIR_UP: {//BACKSPACE
                action = parent.ACTION_PARENT_DIR;
                break;
            }
            case MSG_LEFT_COMBOBOX: {
                action = parent.ACTION_SHOW_LEFT_COMBO;
                break;
            }
            case MSG_RIGHT_COMBOBOX: {
                action = parent.ACTION_SHOW_RIGHT_COMBO;
                break;
            }
            case MSG_RENAME: {
                action = parent.ACTION_RENAME;
                break;
            }
            case MSG_SELECT_N_DOWN: {//insert
                action = parent.ACTION_SELECT_N_DOWN;
                break;
            }
            case MSG_SELECT_N_UP: {
                action = parent.ACTION_SELECT_N_UP;
                break;
            }
            case MSG_COPY: {
                action = parent.ACTION_COPY;
                break;
            }
            case MSG_MOVE: {
                action = parent.ACTION_MOVE;
                break;
            }
            case MSG_PACK: {
                action = parent.ACTION_PACK;
                break;
            }
            case MSG_DELETE: {
                action = parent.ACTION_DELETE;
                break;
            }
            case MSG_REFRESH: {
                action = parent.ACTION_REFRESH;
                break;
            }
            case MSG_SYNC: {
                action = parent.ACTION_SYNC;
                break;
            }
            case MSG_SELECT_ALL: {
                action = parent.ACTION_SELECT_ALL;
                break;
            }
            case MSG_ACTIVE_LEFT: {
                action = parent.ACTION_ACTIVATE_LEFT_TABLE;
                break;
            }
            case MSG_ACTIVE_RIGHT: {
                action = parent.ACTION_ACTIVATE_RIGHT_TABLE;
                break;
            }
            case MSG_UP: {
                action = parent.ACTION_UP;
                break;
            }
            case MSG_DOWN: {
                action = parent.ACTION_DOWN;
                break;
            }
            case MSG_PG_UP: {
                action = parent.ACTION_JUMP_UP;
                break;
            }
            case MSG_PG_DOWN: {
                action = parent.ACTION_JUMP_DOWN;
                break;
            }
            case MSG_FIRST: {
                action = parent.ACTION_FIRST;
                break;
            }
            case MSG_LAST: {
                action = parent.ACTION_LAST;
                break;
            }
            case MSG_QUICK_SEARCH: {
                action = parent.ACTION_QUICK_SEARCH;
                break;
            }
            case MSG_SHOW_PANELS: {
                action = parent.ACTION_SHOW_PANELS;
                break;
            }
            case MSG_FIND: {
                action = parent.ACTION_FIND;
                break;
            }
            case MSG_EXIT: {
                action = parent.ACTION_EXIT;
                break;
            }
            case MSG_SPACE: {//
                action = parent.ACTION_CALC_SIZE;
                break;
            }
            case MSG_ADD_TAB: {//
                action = parent.ACTION_ADD_TAB;
                break;
            }
            case MSG_REMOVE_TAB: {//
                action = parent.ACTION_REMOVE_TAB;
                break;
            }
            case MSG_NEXT_TAB: {//
                action = parent.ACTION_NEXT_TAB;
                break;
            }
            case MSG_PREV_TAB: {//
                action = parent.ACTION_PREV_TAB;
                break;
            }
            case MSG_COPY_PATH: {
                action = parent.ACTION_COPY_PATH;
                break;
            }
            case MSG_COPY_NAME: {
                action = parent.ACTION_COPY_NAME;
                break;
            }
            case MSG_GOTO_CMDLINE: {
                action = parent.ACTION_GOTO_CMDLINE;
                break;
            }
            case MSG_DECODE_HEX: {
                action = parent.ACTION_DECODE_HEX;
                break;
            }
            case MSG_SWAP: {
                action = parent.ACTION_SWAP;
                break;
            }
            case MSG_EXPLORER: {
                action = parent.ACTION_EXPLORER;
                break;
            }
            case MSG_COPY_SAME_FOLDER: {
                action = parent.ACTION_COPY_SAME_FOLDER;
                break;
            }
            default: {
                break;
            }
        }
        if (null != action) {
            action.execute();
        }

    }

    /**
     * Метод выполняется, когда клавиша была отпущена
     */
    @Override
    public void keyReleased(KeyEvent e) {
    }
}
