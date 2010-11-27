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

package omegaCommander.util;


import java.util.regex.Matcher;
import java.util.regex.Pattern;
import omegaCommander.fileSystem.BaseFile;
import omegaCommander.fileSystem.FileHelper;

/**
 *
 * @author Vladimir
 */
public class NameCorrector {

    private static Pattern hexCode = Pattern.compile("%([\\dABCDEF]{4})");

    private static String decodeHex(String hexCodes) {
        Matcher m = hexCode.matcher(hexCodes);
        StringBuffer sb = new StringBuffer(hexCodes.length());
        boolean replacementMade = false;
        while (m.find()) {
            replacementMade = true;
            m.appendReplacement(sb, String.valueOf((char) Integer.parseInt(m.group(1), 16)));
        }
        if (replacementMade) {
            m.appendTail(sb);
            return sb.toString();
        } else {
            return null;
        }
    }

//    public static int renameAll(String path) {
//        int counter = 0;
//        File root = new File(path);
//        if (root.exists()) {
//            counter += renameFiles(root);
//        } else {
//            System.out.println("Bad file name: " + path);
//        }
//        return counter;
//    }

    public static int renameFiles(BaseFile file) {
        int c = 0;
        String name = file.getFilename();
		BaseFile newFile = file;
        name = decodeHex(name);
        if (name != null) {
            newFile = FileHelper.getRealFile(file.getAbsoluteParent(), name);
            if (file.renameTo(newFile)) {
                c++;
                System.out.println(file.getFilename() + " to " + newFile.getFilename());
            } else {
                return c;
            }
        }
        if (newFile.isDirectory()) {
            for (BaseFile f : newFile.getFiles()) {
                c += renameFiles(f);
            }
        }
        return c;
    }
}