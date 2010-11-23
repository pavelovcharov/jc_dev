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
 * TableHeader.java
 *
 * Created on 9 Январь 2007 г., 10:02
 *
 */
package omegaCommander.gui.table.tableHeader;

import omegaCommander.util.LanguageBundle;

/**
 *
 * @author Pavel Ovcharov
 */
public class TableHeader implements omegaCommander.prefs.PrefKeys, ColumnNumbers {

	public static String[] TITLE;// = {"", "Имя","Тип","Размер","Дата","Атрибуты"};

	static {
		try {
			LanguageBundle lb = LanguageBundle.getInstance();
			TITLE = new String[]{lb.getString("StrIcon"), lb.getString("StrName"), lb.getString("StrExtention"),
						lb.getString("StrSize"), lb.getString("StrDate"), lb.getString("StrAttribute")
					};
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String getColumnName(int column) {
		LanguageBundle lb = LanguageBundle.getInstance();
		String result;
		switch (column) {
			case ICON:
				result = lb.getString("StrIcon");
				break;
			case NAME:
				result = lb.getString("StrName");
				break;
			case EXT:
				result = lb.getString("StrExtention");
				break;
			case SIZE:
				result = lb.getString("StrSize");
				break;
			case DATE:
				result = lb.getString("StrDate");
				break;
			case ATR:
				result = lb.getString("StrAttribute");
				break;
			default:
				result = "";
		}
		return result;
	}
}
