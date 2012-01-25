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
 * KeyShortcat.java
 *
 * Created on 19 apr 2007, 10:34
 *
 */

package ru.narod.jcommander.gui.message;

import java.awt.event.KeyEvent;

/**
 *
 * @author Programmer
 * @version
 */
public class KeyShortcat {
    
    private int keyCode;
    private boolean altDown;
    private boolean ctrlDown;
    private boolean shiftDown;
    
    /** Creates a new instance of KeyShortcat */
    public KeyShortcat(KeyEvent keyEvent) {
        this.keyCode = keyEvent.getKeyCode();
        this.altDown = keyEvent.isAltDown();
        this.ctrlDown = keyEvent.isControlDown();
        this.shiftDown = keyEvent.isShiftDown();
    }
    
    public KeyShortcat(int keyCode, boolean alt, boolean ctrl, boolean shift) {
        this.keyCode = keyCode;
        this.altDown = alt;
        this.ctrlDown = ctrl;
        this.shiftDown = shift;
    }
    
    public KeyShortcat(int keyCode) {
        this.keyCode = keyCode;
        this.altDown = false;
        this.ctrlDown = false;
        this.shiftDown = false;
    }

	@Override
    public boolean equals(Object obj) {
        if ( null == obj || !(obj instanceof KeyShortcat)) return false;
        KeyShortcat ks = (KeyShortcat)obj;
        return ((keyCode==ks.keyCode)&&(altDown==ks.altDown)&&
                (ctrlDown==ks.ctrlDown)&&(shiftDown==ks.shiftDown)) ? true : false;
    }
    
	@Override
    public int hashCode() {
        int hash = keyCode;
        if (altDown) hash+=1000;
        if (ctrlDown) hash+= 10000;
        if (shiftDown) hash+=100000;
        return hash;
    }
    
	@Override
    public String toString() {
        String result;
        int modifier = 0;
        if (ctrlDown) {
            modifier = modifier|2;
        }
        if (altDown) {
            modifier = modifier|8;            
        }
        
        if (shiftDown) {
            modifier = modifier|1;        
        }
        
        result = KeyEvent.getKeyModifiersText(modifier);
        if (false == result.equals("")) 
            result += " + ";
        
        if ((KeyEvent.VK_CONTROL != keyCode) && (KeyEvent.VK_ALT != keyCode) &&
                (KeyEvent.VK_SHIFT != keyCode)) {
            result += KeyEvent.getKeyText(keyCode);    
        }
        
        return result;
    }

    public int getKeyCode() {
        return keyCode;
    }

    public boolean isAltDown() {
        return altDown;
    }

    public boolean isCtrlDown() {
        return ctrlDown;
    }

    public boolean isShiftDown() {
        return shiftDown;
    }

}
