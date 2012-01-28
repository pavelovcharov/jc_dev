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
 * MessageList.java
 * Created on 12.01.2009 13:47:31
 */
package ru.narod.jcommander.gui.message;

import java.util.Collection;
import java.util.LinkedHashMap;
import ru.narod.jcommander.util.LanguageBundle;

/**
 *
 * @author Programmer
 */
public class MessageList implements MessageID, ru.narod.jcommander.prefs.PrefKeys {

    enum MessageType {

        TitleNavigation, Enter, Tab, DirUp, LeftCombo, RightCombo, ActivateLeft,
        ActiateRight, Up, Down, PgUp, PgDown, First, Last, ShowPanels, GotoCmdLine, SwapPanels, Exit,
        TitleFiles, Rename, View, Edit, Copy, CopySameFolder, Move, NewDir, Delete, Refresh, Sync, Pack, EditNew, MoveToTrash,
        QuickSearch, Find,
        TitleSelection, SelectAll, SelectDown, SelectUp, SelectSpace,
        TitleTabs, AddTab, RemoveTab, NextTab, PrevTab,
        TiteMisc, CopyPath, CopyName, DecodeHex, Explorer,
    };
    LinkedHashMap<Object, MessageDescriptor> descriptors;
    LinkedHashMap<Object, Message> messages;
    static final LinkedHashMap<Object, Integer> numbers = new LinkedHashMap<Object, Integer>();

    static {
        numbers.put(MessageType.TitleNavigation, MSG_TITLE_NAVIGATION);
        numbers.put(MessageType.Enter, MSG_ENTER);
        numbers.put(MessageType.Tab, MSG_TAB);
        numbers.put(MessageType.DirUp, MSG_DIR_UP);
        numbers.put(MessageType.LeftCombo, MSG_LEFT_COMBOBOX);
        numbers.put(MessageType.RightCombo, MSG_RIGHT_COMBOBOX);
        numbers.put(MessageType.ActivateLeft, MSG_ACTIVE_LEFT);
        numbers.put(MessageType.ActiateRight, MSG_ACTIVE_RIGHT);
        numbers.put(MessageType.Up, MSG_UP);
        numbers.put(MessageType.Down, MSG_DOWN);
        numbers.put(MessageType.PgUp, MSG_PG_UP);
        numbers.put(MessageType.PgDown, MSG_PG_DOWN);
        numbers.put(MessageType.First, MSG_FIRST);
        numbers.put(MessageType.Last, MSG_LAST);
        numbers.put(MessageType.ShowPanels, MSG_SHOW_PANELS);
        numbers.put(MessageType.GotoCmdLine, MSG_GOTO_CMDLINE);
        numbers.put(MessageType.SwapPanels, MSG_SWAP);
        numbers.put(MessageType.Exit, MSG_EXIT);
        numbers.put(MessageType.TitleFiles, MSG_TITLE_FILES);
        numbers.put(MessageType.Rename, MSG_RENAME);
        numbers.put(MessageType.View, MSG_VIEW);
        numbers.put(MessageType.Edit, MSG_EDIT);
        numbers.put(MessageType.Copy, MSG_COPY);
        numbers.put(MessageType.CopySameFolder, MSG_COPY_SAME_FOLDER);
        numbers.put(MessageType.Move, MSG_MOVE);
        numbers.put(MessageType.NewDir, MSG_NEW_DIR);
        numbers.put(MessageType.Delete, MSG_DELETE);
        numbers.put(MessageType.Refresh, MSG_REFRESH);
        numbers.put(MessageType.Sync, MSG_SYNC);
        numbers.put(MessageType.Pack, MSG_PACK);
        numbers.put(MessageType.EditNew, MSG_NEW_EDIT);
        numbers.put(MessageType.QuickSearch, MSG_QUICK_SEARCH);
        numbers.put(MessageType.Find, MSG_FIND);
        numbers.put(MessageType.TitleSelection, MSG_TITLE_SELECTION);
        numbers.put(MessageType.SelectAll, MSG_SELECT_ALL);
        numbers.put(MessageType.SelectDown, MSG_SELECT_N_DOWN);
        numbers.put(MessageType.SelectUp, MSG_SELECT_N_UP);
        numbers.put(MessageType.SelectSpace, MSG_SPACE);
        numbers.put(MessageType.TitleTabs, MSG_TITLE_TABS);
        numbers.put(MessageType.AddTab, MSG_ADD_TAB);
        numbers.put(MessageType.RemoveTab, MSG_REMOVE_TAB);
        numbers.put(MessageType.NextTab, MSG_NEXT_TAB);
        numbers.put(MessageType.PrevTab, MSG_PREV_TAB);
        numbers.put(MessageType.TiteMisc, MSG_TITLE_MISC);
        numbers.put(MessageType.CopyPath, MSG_COPY_PATH);
        numbers.put(MessageType.CopyName, MSG_COPY_NAME);
        numbers.put(MessageType.DecodeHex, MSG_DECODE_HEX);
        numbers.put(MessageType.Explorer, MSG_EXPLORER);
        numbers.put(MessageType.MoveToTrash, MSG_MOVE_TO_TRASH);
    }

    void createMaps() {
        descriptors = new LinkedHashMap<Object, MessageDescriptor>();
        messages = new LinkedHashMap<Object, Message>();

        LanguageBundle lb = LanguageBundle.getInstance();
        LanguageBundle languageBundle = lb;
        descriptors.put(MessageType.TitleNavigation, new MessageDescriptor("MSG_TITLE_NAVIGATION", lb.getString("KeyTitleNavigation")));
        descriptors.put(MessageType.Enter, new MessageDescriptor("MSG_ENTER", lb.getString("KeyEnter")));
        descriptors.put(MessageType.Tab, new MessageDescriptor("MSG_TAB", lb.getString("KeySwitch")));
        descriptors.put(MessageType.DirUp, new MessageDescriptor("MSG_DIR_UP", lb.getString("KeyParent")));
        descriptors.put(MessageType.LeftCombo, new MessageDescriptor("MSG_LEFT_COMBOBOX", lb.getString("KeyLeftDrive")));
        descriptors.put(MessageType.RightCombo, new MessageDescriptor("MSG_RIGHT_COMBOBOX", lb.getString("KeyRightDrive")));
        descriptors.put(MessageType.ActivateLeft, new MessageDescriptor("MSG_ACTIVE_LEFT", lb.getString("KeyLeft")));
        descriptors.put(MessageType.ActiateRight, new MessageDescriptor("MSG_ACTIVE_RIGHT", lb.getString("KeyRight")));
        descriptors.put(MessageType.Up, new MessageDescriptor("MSG_UP", lb.getString("KeyUp")));
        descriptors.put(MessageType.Down, new MessageDescriptor("MSG_DOWN", lb.getString("KeyDown")));
        descriptors.put(MessageType.PgUp, new MessageDescriptor("MSG_PG_UP", lb.getString("KeyPgUp")));
        descriptors.put(MessageType.PgDown, new MessageDescriptor("MSG_PG_DOWN", lb.getString("KeyPgDown")));
        descriptors.put(MessageType.First, new MessageDescriptor("MSG_FIRST", lb.getString("KeyFirst")));
        descriptors.put(MessageType.Last, new MessageDescriptor("MSG_LAST", lb.getString("KeyLast")));
        descriptors.put(MessageType.ShowPanels, new MessageDescriptor("MSG_SHOW_PANELS", lb.getString("KeyConsole")));
        descriptors.put(MessageType.GotoCmdLine, new MessageDescriptor("MSG_GOTO_CMDLINE", lb.getString("KeyGotoCmdLine")));
        descriptors.put(MessageType.SwapPanels, new MessageDescriptor("MSG_SWAP", lb.getString("KeySwapPanels")));
        descriptors.put(MessageType.Exit, new MessageDescriptor("MSG_EXIT", lb.getString("KeyExit")));
        descriptors.put(MessageType.TitleFiles, new MessageDescriptor("MSG_TITLE_FILES", lb.getString("KeyTitleFiles")));
        descriptors.put(MessageType.Rename, new MessageDescriptor("MSG_RENAME", lb.getString("KeyRename")));
        descriptors.put(MessageType.View, new MessageDescriptor("MSG_VIEW", lb.getString("KeyView")));
        descriptors.put(MessageType.Edit, new MessageDescriptor("MSG_EDIT", lb.getString("KeyEdit")));
        descriptors.put(MessageType.Copy, new MessageDescriptor("MSG_COPY", lb.getString("KeyCopy")));
        descriptors.put(MessageType.CopySameFolder, new MessageDescriptor("MSG_COPY_SAME_FOLDER", lb.getString("KeyCopySameFolder")));
        descriptors.put(MessageType.Move, new MessageDescriptor("MSG_MOVE", lb.getString("KeyMove")));
        descriptors.put(MessageType.NewDir, new MessageDescriptor("MSG_NEW_DIR", lb.getString("KeyNewDir")));
        descriptors.put(MessageType.Delete, new MessageDescriptor("MSG_DELETE", lb.getString("KeyDel")));
        descriptors.put(MessageType.Refresh, new MessageDescriptor("MSG_REFRESH", lb.getString("KeyRefresh")));
        descriptors.put(MessageType.Sync, new MessageDescriptor("MSG_SYNC", lb.getString("KeySync")));
        descriptors.put(MessageType.Pack, new MessageDescriptor("MSG_PACK", lb.getString("KeyPack")));
        descriptors.put(MessageType.EditNew, new MessageDescriptor("MSG_NEW_EDIT", lb.getString("KeyNewFile")));
        descriptors.put(MessageType.QuickSearch, new MessageDescriptor("MSG_QUICK_SEARCH", lb.getString("KeyQuickSearch")));
        descriptors.put(MessageType.Find, new MessageDescriptor("MSG_FIND", lb.getString("KeySearch")));
        descriptors.put(MessageType.TitleSelection, new MessageDescriptor("MSG_TITLE_SELECTION", lb.getString("KeyTitleSelection")));
        descriptors.put(MessageType.SelectAll, new MessageDescriptor("MSG_SELECT_ALL", lb.getString("KeySelectAll")));
        descriptors.put(MessageType.SelectDown, new MessageDescriptor("MSG_SELECT_N_DOWN", lb.getString("KeySelectDown")));
        descriptors.put(MessageType.SelectUp, new MessageDescriptor("MSG_SELECT_N_UP", lb.getString("KeySelectUp")));
        descriptors.put(MessageType.SelectSpace, new MessageDescriptor("MSG_SPACE", lb.getString("KeySize")));
        descriptors.put(MessageType.TitleTabs, new MessageDescriptor("MSG_TITLE_TABS", lb.getString("KeyTitleTabs")));
        descriptors.put(MessageType.AddTab, new MessageDescriptor("MSG_ADD_TAB", lb.getString("KeyAddTab")));
        descriptors.put(MessageType.RemoveTab, new MessageDescriptor("MSG_REMOVE_TAB", lb.getString("KeyRemoveTab")));
        descriptors.put(MessageType.NextTab, new MessageDescriptor("MSG_NEXT_TAB", lb.getString("KeyNextTab")));
        descriptors.put(MessageType.PrevTab, new MessageDescriptor("MSG_PREV_TAB", lb.getString("KeyPrevTab")));
        descriptors.put(MessageType.TiteMisc, new MessageDescriptor("MSG_TITLE_MISC", lb.getString("KeyTitleMisc")));
        descriptors.put(MessageType.CopyPath, new MessageDescriptor("MSG_COPY_PATH", lb.getString("KeyCopyPath")));
        descriptors.put(MessageType.CopyName, new MessageDescriptor("MSG_COPY_NAME", lb.getString("KeyCopyFilename")));
        descriptors.put(MessageType.DecodeHex, new MessageDescriptor("MSG_DECODE_HEX", lb.getString("KeyDecodeHex")));
        descriptors.put(MessageType.Explorer, new MessageDescriptor("MSG_EXPORER", lb.getString("KeyExplorer")));
        descriptors.put(MessageType.MoveToTrash, new MessageDescriptor("MSG_MOVE_TO_TRASH", lb.getString("KeyMoveToTrash")));


        int msgCount = 0;
        for (MessageType type : MessageType.values()) {
            messages.put(type, new Message(numbers.get(type), descriptors.get(type)));
        }

    }

    class MessageDescriptor {

        public MessageDescriptor(String name, String description) {
            this.name = name;
            this.description = description;
        }
        public String name;
        public String description;
    }

    public Message MESSAGE_ENTER;
    public Message MESSAGE_TAB;
    public Message MESSAGE_RENAME;
    public Message MESSAGE_VIEW;
    public Message MESSAGE_EDIT;
    public Message MESSAGE_COPY;
    public Message MESSAGE_MOVE;
    public Message MESSAGE_NEW_DIR;
    public Message MESSAGE_DELETE;
    public Message MESSAGE_EXIT;
    public Message MESSAGE_DIR_UP;
    public Message MESSAGE_SHOW_LEFT_COMBOBOX;
    public Message MESSAGE_SHOW_RIGHT_COMBOBOX;
    public Message MESSAGE_REFRESH;
    public Message MESSAGE_SYNC;
    public Message MESSAGE_SELECT_ALL;
    public Message MESSAGE_SELECT_N_DOWN;
    public Message MESSAGE_SELECT_N_UP;
    public Message MESSAGE_ACTIVE_LEFT;
    public Message MESSAGE_ACTIVE_RIGHT;
    public Message MESSAGE_PACK;
    public Message MESSAGE_UP;
    public Message MESSAGE_DOWN;
    public Message MESSAGE_PG_UP;
    public Message MESSAGE_PG_DOWN;
    public Message MESSAGE_FIRST;
    public Message MESSAGE_LAST;
    public Message MESSAGE_NEW_EDIT;
    public Message MESSAGE_QUICK_SEARCH;
    public Message MESSAGE_SHOW_PANELS;
    public Message MESSAGE_FIND;
    public Message MESSAGE_SPACE;
    public Message MESSAGE_ADD_TAB;
    public Message MESSAGE_REMOVE_TAB;
    public Message MESSAGE_NEXT_TAB;
    public Message MESSAGE_PREV_TAB;
    public Message MESSAGE_TITLE_NAVIGATION;
    public Message MESSAGE_TITLE_FILES;
    public Message MESSAGE_TITLE_SELECTION;
    public Message MESSAGE_TITLE_TABS;
    public Message MESSAGE_TITLE_MISC;
    public Message MESSAGE_COPY_PATH;
    public Message MESSAGE_COPY_NAME;
    public Message MESSAGE_GOTO_CMDLINE;
    public Message MESSAGE_DECODE_HEX;
    public Message MESSAGE_SWAP;
    public Message MESSAGE_EXPLORER;
    public Message MESSAGE_COPY_SAME_FOLDER;
    public Message MESSAGE_MOVE_TO_TRASH;

    private MessageList() {
        createMaps();
        createMessages();
    }

    void createMessages() {
        MESSAGE_TITLE_NAVIGATION = messages.get(MessageType.TitleNavigation);
        MESSAGE_TITLE_NAVIGATION.setAction(false);
        MESSAGE_ENTER = messages.get(MessageType.Enter);
        MESSAGE_TAB = messages.get(MessageType.Tab);
        MESSAGE_DIR_UP = messages.get(MessageType.DirUp);
        MESSAGE_SHOW_LEFT_COMBOBOX = messages.get(MessageType.LeftCombo);
        MESSAGE_SHOW_RIGHT_COMBOBOX = messages.get(MessageType.RightCombo);
        MESSAGE_ACTIVE_LEFT = messages.get(MessageType.ActivateLeft);
        MESSAGE_ACTIVE_RIGHT = messages.get(MessageType.ActivateLeft);
        MESSAGE_UP = messages.get(MessageType.Up);
        MESSAGE_DOWN = messages.get(MessageType.Down);
        MESSAGE_PG_UP = messages.get(MessageType.PgUp);
        MESSAGE_PG_DOWN = messages.get(MessageType.PgDown);
        MESSAGE_FIRST = messages.get(MessageType.First);
        MESSAGE_LAST = messages.get(MessageType.Last);
        MESSAGE_SHOW_PANELS = messages.get(MessageType.ShowPanels);
        MESSAGE_GOTO_CMDLINE = messages.get(MessageType.GotoCmdLine);
        MESSAGE_SWAP = messages.get(MessageType.SwapPanels);
        MESSAGE_EXIT = messages.get(MessageType.Exit);

        MESSAGE_TITLE_FILES = messages.get(MessageType.TitleFiles);
        MESSAGE_TITLE_FILES.setAction(false);
        MESSAGE_RENAME = messages.get(MessageType.Rename);
        MESSAGE_VIEW = messages.get(MessageType.View);
        MESSAGE_EDIT = messages.get(MessageType.Edit);
        MESSAGE_COPY = messages.get(MessageType.Copy);
        MESSAGE_MOVE = messages.get(MessageType.Move);
        MESSAGE_NEW_DIR = messages.get(MessageType.NewDir);
        MESSAGE_DELETE = messages.get(MessageType.Delete);
        MESSAGE_REFRESH = messages.get(MessageType.Refresh);
        MESSAGE_SYNC = messages.get(MessageType.Sync);
        MESSAGE_PACK = messages.get(MessageType.Pack);
        MESSAGE_NEW_EDIT = messages.get(MessageType.EditNew);
        MESSAGE_QUICK_SEARCH = messages.get(MessageType.QuickSearch);
        MESSAGE_FIND = messages.get(MessageType.Find);
        MESSAGE_MOVE_TO_TRASH = messages.get(MessageType.MoveToTrash);

        MESSAGE_TITLE_SELECTION = messages.get(MessageType.TitleSelection);
        MESSAGE_TITLE_SELECTION.setAction(false);
        MESSAGE_SELECT_ALL = messages.get(MessageType.SelectAll);
        MESSAGE_SELECT_N_DOWN = messages.get(MessageType.SelectDown);
        MESSAGE_SELECT_N_UP = messages.get(MessageType.SelectUp);
        MESSAGE_SPACE = messages.get(MessageType.SelectSpace);

        MESSAGE_TITLE_TABS = messages.get(MessageType.TitleTabs);
        MESSAGE_TITLE_TABS.setAction(false);
        MESSAGE_ADD_TAB = messages.get(MessageType.AddTab);
        MESSAGE_REMOVE_TAB = messages.get(MessageType.RemoveTab);
        MESSAGE_NEXT_TAB = messages.get(MessageType.NextTab);
        MESSAGE_PREV_TAB = messages.get(MessageType.PrevTab);

        MESSAGE_TITLE_MISC = messages.get(MessageType.TiteMisc);
        MESSAGE_TITLE_MISC.setAction(false);
        MESSAGE_COPY_PATH = messages.get(MessageType.CopyPath);
        MESSAGE_COPY_NAME = messages.get(MessageType.CopyName);
        MESSAGE_DECODE_HEX = messages.get(MessageType.DecodeHex);
        MESSAGE_EXPLORER = messages.get(MessageType.Explorer);
        MESSAGE_COPY_SAME_FOLDER = messages.get(MessageType.CopySameFolder);
    }

    public Collection<Message> getMessages() {
        return messages.values();
    }
    public static MessageList defaultMessageList;

    public Message getMessage(String s) {
        for (MessageType type : MessageType.values()) {
            Message msg = messages.get(type);
            if (msg.getMessageText().equals(s)) {
                return msg;
            }
        }
        return null;
    }

    public static MessageList ensureMessageList() {
        defaultMessageList = new MessageList();
        return defaultMessageList;
    }
}
