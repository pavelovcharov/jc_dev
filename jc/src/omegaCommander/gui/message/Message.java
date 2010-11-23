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
 * Message.java
 *
 * Created on 19 јпрель 2007 г., 9:23
 *
 */
package omegaCommander.gui.message;

/**
 *
 * @author Programmer
 * @version
 */
public class Message {

    private int messageID;
    private String messageText;
    private String description;
    private boolean isAction;
    private KeyShortcat defaultKey;
    private KeyShortcat userKey;

    /** Creates a new instance of Message */
    public Message(int messageID, MessageList.MessageDescriptor descriptor) {
        this(messageID, descriptor.name, descriptor.description);
    }
    public Message(int messageID, String messageText) {
        this(messageID, messageText, messageText);
    }

    public Message(int messageID, String messageText, String description) {
        this(messageID, messageText, description, true);
    }

    public Message(int messageID, String messageText, String description, boolean isAction) {
        this.messageID = messageID;
        this.messageText = messageText;
        this.description = description;
        this.isAction = isAction;
    }

    public boolean isAction() {
        return isAction;
    }

    public void setAction(boolean isAction) {
        this.isAction = isAction;
    }

    public int getMessageID() {
        return messageID;
    }

    public String getMessageText() {
        return messageText;
    }

    @Override
    public String toString() {
        return description;
    }

    @Override
    public int hashCode() {
        return messageID;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Message) {
            if (messageID == ((Message) obj).messageID) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param defaultKey the defaultKey to set
     */
    public void setDefaultKey(KeyShortcat defaultKey) {
        this.defaultKey = defaultKey;
    }

    /**
     * @return the userKey
     */
    public KeyShortcat getUserKey() {
        return userKey;
    }

    /**
     * @param userKey the userKey to set
     */
    public void setUserKey(KeyShortcat userKey) {
        this.userKey = userKey;
    }

    /**
     * @return the defaultKey
     */
    public KeyShortcat getDefaultKey() {
        return defaultKey;
    }

}
