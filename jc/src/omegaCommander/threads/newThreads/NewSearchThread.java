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
 * NewSearchThread.java
 * Created on 20.04.2009 14:52:05
 */
package omegaCommander.threads.newThreads;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.Vector;

import omegaCommander.fileSystem.BaseFile;
import omegaCommander.fileSystem.WildcardFilenameFilter;
import omegaCommander.fileSystem.archive.ArchiveFile;
import org.apache.commons.io.IOCase;

/**
 *
 * @author Programmer
 */
public class NewSearchThread extends BaseThread {

    private final Vector resultList;
    private BaseFile file;
    private String namePart;
    private String searchText;
    private boolean matchCase = false;
    private WildcardFilenameFilter w;

    public NewSearchThread(BaseFile file, String namePart, String searchText, boolean matchCase) {
        resultList = new Vector();
        this.file = file;
        this.namePart = namePart.trim().toLowerCase();
        if (this.namePart.isEmpty()) {
            this.namePart = "*";
        }
        this.matchCase = matchCase;
        if (matchCase) {
            this.searchText = searchText;
        } else {
            this.searchText = searchText.toLowerCase();
        }
        w = new WildcardFilenameFilter(this.namePart, IOCase.INSENSITIVE);
    }

    private void action(BaseFile file) {
        if (interrupt) {
            return;
        }
        if (file.isDirectory() || file instanceof ArchiveFile) {
            BaseFile[] files = file.getFiles();

            if (files != null) {
                for (int i = 0; i < files.length; i++) {
                    action(files[i]);
                }
            }
        }
        int rs = 0;
        if (!w.accept(file.getFilename())) {
            rs = -1;
        }
        if (-1 != rs) {
            if (null != searchText && !searchText.equals("")) {
                LineNumberReader lineNumberReader = null;
                InputStreamReader inputStreamReader = null;
                rs = -1;
                try {
                    inputStreamReader = new InputStreamReader(file.getInputStream());
                    lineNumberReader = new LineNumberReader(inputStreamReader);
                    String line;
                    while (null != (line = lineNumberReader.readLine())) {
                        line = matchCase ? line : line.toLowerCase();
                        rs = line.indexOf(searchText);
                        if (-1 != rs) {
                            break;
                        }
                    }
                } catch (IOException ex) {
                    rs = -1;
                } finally {
                    try {
                        if (inputStreamReader != null) {
                            inputStreamReader.close();
                        }
                        if (lineNumberReader != null) {
                            lineNumberReader.close();
                        }
                    } catch (IOException ex) {
                    }
                }
            }
        }
        if (-1 != rs) {
            synchronized (resultList) {
                resultList.add(file);
            }
        }
    }

    @Override
    public void run() {
        action(file);

    }

    public Vector getResultList() {
        Vector res;
        synchronized (resultList) {
            res = new Vector(resultList);
            resultList.clear();
        }
        return res;
    }
}
