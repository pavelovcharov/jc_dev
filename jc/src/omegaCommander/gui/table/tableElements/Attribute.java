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
 * Attribute.java
 *
 * Created on 25 ���� 2007 �., 10:57
 *
 */

package omegaCommander.gui.table.tableElements;

/**
 *
 * @author Programmer
 * @version
 */
public class Attribute extends Element{
    
    private String attributeStr;
    
    /** Creates a new instance of Attribute */
    public Attribute() {
        attributeStr = "";
    }
    
    public Attribute(String attribute) {
        attributeStr = attribute;
    }
    
	@Override
    public String toString() {
        return attributeStr;
    }

    public int compareTo(Object o) {
        if (false == (o instanceof Attribute)) return 1;
        Attribute a = (Attribute)o;
        return attributeStr.compareTo(a.attributeStr);
    }
    
    
    
}
