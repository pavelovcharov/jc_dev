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
 * WildcardFilenameFilter.java
 * Created on 02.07.2009 14:21:18
 */

package ru.narod.jcommander.fileSystem;

import java.io.File;
import java.io.FileFilter;
import java.util.StringTokenizer;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOCase;

/**
 *
 * @author Programmer
 */
public class WildcardFilenameFilter implements FileFilter {

    String [] wildcards;
	IOCase caseSensitivity;
	
	/**
	 *
	 * @param wildcard
	 * @param caseSensitivity
	 */
	public WildcardFilenameFilter(String wildcard, IOCase caseSensitivity) {
		StringTokenizer stringTokenizer = new StringTokenizer(wildcard, ";");
		wildcards = new String[stringTokenizer.countTokens()];
//		String s;
//		for (int i=0; i<wildcards.length; i++) {
//			wildcards[i] = stringTokenizer.nextToken();
//
//		}
		for (int i=0; stringTokenizer.hasMoreTokens(); i++) {
			String token = stringTokenizer.nextToken();
			wildcards[i] = token;
		}

		this.caseSensitivity = caseSensitivity;
	}

	
	/**
	 *
	 * @param name
	 * @return
	 */
	public boolean accept(String name) {
		 for (int i = 0; i < wildcards.length; i++) {
            if (FilenameUtils.wildcardMatch(name, wildcards[i], caseSensitivity)) {
                return true;
            }
        }
        return false;
	}

	public boolean accept(File pathname) {
		return accept(pathname.getName());
	}
}
