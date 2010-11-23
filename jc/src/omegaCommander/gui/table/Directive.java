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
 * Directive.java
 *
 * Created on 28 Èþíü 2007 ã., 10:15
 *
 */

package omegaCommander.gui.table;

/**
 *
 * @author Programmer
 * @version
 */
public class Directive {

    private int column;
    private int direction;

    public Directive(int column, int direction) {
        this.column = column;
        this.direction = direction;
    }
    public int getDirection() {
        return direction;
    }

    public int getColumn() {
        return column;
    }

}
