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
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.narod.jcommander.actions;

import javax.swing.JDialog;
import ru.narod.jcommander.gui.MainFrame;

/**
 *
 * @author Programmer
 */
public class ActionFind extends AbstractAction {

	public ActionFind(MainFrame parent) {
		super(parent);
	}

	public void execute() {
            JDialog form = parent.getFindDialog();
            form.setLocationRelativeTo(parent);
            form.setVisible(true);
	}

}
