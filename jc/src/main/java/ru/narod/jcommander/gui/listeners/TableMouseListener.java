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
 * TableMouseListener.java
 *
 * Created on 23 may 2005, 17:47
 */
package ru.narod.jcommander.gui.listeners;

import java.awt.event.MouseEvent;
import javax.swing.event.MouseInputAdapter;

import ru.narod.jcommander.gui.table.FileTable;
import ru.narod.jcommander.gui.MainFrame;

/**
 * Класс для изменения состояния таблиц FileTable при двойном щелчке
 * мыши. Если двойной щелчок выполнен на каталоге в таблицу будет выведено
 * содержимое этого каталога. Если двойной щелчок выполнен на файле, данный
 * файл будет запущен. Помимо, состояния таблицы изменяется состояние 
 * строки статуса и пути.
 * 
 * @author Programmer
 * @version 2005/05/23 17:47
 */
public class TableMouseListener extends MouseInputAdapter {

	private MainFrame parent;
	private boolean rightMousePressed = false;
	private int prevRow = -1;

	/**
	 * Создает новый объект класса TableMouseListener
	 * @param table таблица, состояние которой изменяется
	 * @param path строка пути
	 * @param status строка статуса
	 */
	/*
	public TableMouseListener(MainFrame parent, FileTable table) {
	//this.table = table;
	this.parent = parent;
	}
	 */
	public TableMouseListener(MainFrame parent) {
		this.parent = parent;
	}

	/**
	 * Метод выполняется при щелчке мыши в таблице
	 */
	@Override
	public void mouseClicked(MouseEvent evt) {

		FileTable table = (FileTable) evt.getSource();
		switch (evt.getButton()) {
			case MouseEvent.BUTTON1: {
				if (evt.getClickCount() == 1) {
					table.setCurrentPosition(table.getSelectedRow());
					table.requestFocus();
                                        table.setActive(true);
					return;
				}
				if (evt.getClickCount() == 2) {
					parent.ACTION_ENTER.execute();
				}
				break;
			}
			case MouseEvent.BUTTON3: {
				int currentRow = evt.getY() / table.getRowHeight();
				if (currentRow >= table.getRowCount()) {
					return;
				}
//				if (prevRow == currentRow) {
//					return;
//				}
				table.selectFileAt(currentRow);
				table.setCurrentPosition(currentRow);
				table.repaint();
				table.requestFocus();
				table.setActive(true);
				parent.updateActiveStatusLabel();
			}
		}
	}

	@Override
	public void mousePressed(java.awt.event.MouseEvent evt) {
		if (MouseEvent.BUTTON3 == evt.getButton()) {
			rightMousePressed = true;
			FileTable table = (FileTable) evt.getSource();
			table.requestFocus();
			table.setActive(true);

		}

	}

	@Override
	public void mouseReleased(java.awt.event.MouseEvent evt) {
		if (MouseEvent.BUTTON3 == evt.getButton()) {
			rightMousePressed = false;
			prevRow = 0;

		}

	}

	@Override
	public void mouseDragged(java.awt.event.MouseEvent evt) {
		if (rightMousePressed) {
			if (evt.getSource() instanceof FileTable) {
				FileTable table = (FileTable) evt.getSource();
				int currentRow = evt.getY() / table.getRowHeight();
				if (currentRow >= table.getRowCount()) {
					return;
				}

				if (prevRow == currentRow) {
					return;
				}

				table.selectFileAt(currentRow);
				table.setCurrentPosition(currentRow);
				table.repaint();
				prevRow = currentRow;

				parent.updateActiveStatusLabel();
			}
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		//rightMousePressed = false;
		prevRow = 0;
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		FileTable table = (FileTable) e.getSource();
		int currentRow = e.getY() / table.getRowHeight();

		table.showToolTipAtRow(currentRow);

	}


}
