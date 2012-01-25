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
 * Extention.java
 *
 * Created on 25 jun 2007, 9:54
 *
 */

package ru.narod.jcommander.gui.table.tableElements;

/**
 *
 * @author Programmer
 * @version
 */
public class Extention extends Element{
    
    private String extention;
    /** Creates a new instance of Extention */
    public Extention() {
    }
    
    public Extention(String extention) {
        this.extention = extention;
    }
    
	@Override
    public String toString() {
        return extention;
    }
    
    public String getExtention() {
        return extention;
    }

    public int compareTo(Object o) {
        if (false == (o instanceof Extention)) return 1;
        Extention e = (Extention)o;
//        return extention.compareTo(e.extention);
        return extention.toUpperCase().compareTo(e.extention.toUpperCase());
    }
    
}
