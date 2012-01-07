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
 * NewDeleteThread.java
 * Created on 31.03.2009 9:18:04
 */
package ru.narod.jcommander.threads.newThreads;

import java.util.ArrayList;
import ru.narod.jcommander.fileSystem.BaseFile;
import ru.narod.jcommander.fileSystem.SubDirectoriesList;
import ru.narod.jcommander.util.LanguageBundle;

/**
 *
 * @author Programmer
 */
public class NewDeleteThread extends FileThread {

    ArrayList list = new ArrayList();
    BaseFile[] filesToDelete;

    public NewDeleteThread(BaseFile[] filesToDelete) {
        this.filesToDelete = filesToDelete;
    }

    /**
     * �������� ����� ������. ����������� ��� ������ ������ <B>start()</B>
     */
    @Override
    public void run() {
        for (int i = 0; i < filesToDelete.length; i++) {
            list.addAll(SubDirectoriesList.getList(filesToDelete[i]));
        }
        totalFiles = list.size();
        action();
    }

    private void action() {
        for (int i = list.size() - 1; i >= 0; i--) {
            BaseFile file = (BaseFile) list.get(i);

            currentAction = LanguageBundle.getInstance().getString("StrDeletion") + " " + file.getFilename();
            currentProgress = 0;
            currentFileSize = file.length();

            if (false == file.delete()) {
                if (queryError(LanguageBundle.getInstance().getString("StrFileNotAccessible") + " \n " + file.getAbsolutePath())) {
                    if (resultError.equals(ErrorAction.Retry)) { // try again
                        i++;
                    }
                    continue;
                } else //cancel
                {
                    break;
                }
            }
            if (interrupt) {
                break;
            }
            currentProgress = currentFileSize;
            filesReady++;
        }
    }
}
