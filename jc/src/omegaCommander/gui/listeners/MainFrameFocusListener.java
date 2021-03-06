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
 * MainFrameFocusListener.java
 *
 * Created on 20 ��� 2005 �., 17:27
 */

package omegaCommander.gui.listeners;

import java.awt.event.FocusListener;
import java.awt.event.FocusEvent;

import omegaCommander.gui.MainFrame;
//import omegaCommander.gui.table.FileTable;

/**
 * ����� ��������� ��������, ����������� ��� ��������� ������
 * ��������� ���� ����������
 * @author Sniper
 * @version 2005/05/20 17:27
 */
public class MainFrameFocusListener implements FocusListener{
    //private FileTable left,right;
    /**
     * ������� ������ ������ MainFrameFocusListener
     */
    public MainFrameFocusListener(/*FileTable left, FileTable right*/) {
        //this.left=left;
        //this.right=right;
    }
    /**
     * ����� �����������, ����� ���� �������� �����
     */
    public void focusGained(FocusEvent fe){
        
        if (fe.getSource() instanceof MainFrame) {
            MainFrame parent = (MainFrame)(fe.getSource());
            parent.getActiveTable().requestFocus();
            //left = parent.getTable(true);
            //right = parent.getTable(false);
        }
        //else 
        //    return;
        
        
        /*
        if(left.isActive()){
            left.requestFocus();
            return;
        }
        if (right.isActive()){
            right.requestFocus();
        }
         */
    }
    /**
     * ����� �����������, ����� ���� ������ �����
     */
    public void focusLost(FocusEvent fe){
    }
}
