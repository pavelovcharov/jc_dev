/*
 * This file is part of jc, http://www.jcommander.narod.ru
 * Copyright (C) 2005-2011 Pavel Ovcharov
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

package omegaCommander.gui.search;

import java.awt.Component;
import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JList;
import omegaCommander.fileSystem.BaseFile;
import omegaCommander.fileSystem.FileHelper;
import omegaCommander.gui.ImageArchive;

/**
 *
 * @author master
 */
class SearchResultListCellRenderer extends DefaultListCellRenderer {

        @Override
        public Component getListCellRendererComponent(
                JList list,
                Object value,
                int index,
                boolean isSelected,
                boolean cellHasFocus) {
            Component retValue = super.getListCellRendererComponent(
                    list, value, index, isSelected, cellHasFocus);
            if (value instanceof BaseFile) {
                BaseFile file = (BaseFile) value;
                ImageIcon icon = null;
                switch (FileHelper.getFileType(file)) {
                    case DIRECTORY:
                        icon = ImageArchive.getImageFolder();
                        break;
                    case ARCHIVE:
                        icon = ImageArchive.getImageArchive();
                        break;
                    default:
                        icon = ImageArchive.getImageFile();
                        break;
                }
                setIcon(icon);
            }
            return retValue;
        }
    }
