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
 * ResourceArchive.java
 *
 * Created on 30 ���� 2006 �., 9:55
 *
 */
package ru.narod.jcommander.gui;

import java.io.File;
import java.util.HashMap;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.filechooser.FileSystemView;
import ru.narod.jcommander.fileSystem.BaseFile;
import ru.narod.jcommander.fileSystem.FileHelper;

/**
 *
 * @author Pavel Ovcharov
 */
abstract public class ImageArchive {

    private static ImageIcon imageFolder = null;
    private static ImageIcon imageFile = null;
    private static ImageIcon imageUp = null;
    private static ImageIcon logo = null;
    private static ImageIcon splash = null;
    private static ImageIcon archive = null;
    private static ImageIcon arrowBack = null;
    private static ImageIcon arrowForward = null;
    private static Icon systemFolderIcon = null;
    private static FileSystemView fileSystemView;

    static {
        try {
            imageFolder = new ImageIcon(ImageArchive.class.getResource("/ru/narod/jcommander/resources/folder16x16.gif"));
            imageFile = new ImageIcon(ImageArchive.class.getResource("/ru/narod/jcommander/resources/file16x16.gif"));
            imageUp = new ImageIcon(ImageArchive.class.getResource("/ru/narod/jcommander/resources/up.gif"));
            logo = new ImageIcon(ImageArchive.class.getResource("/ru/narod/jcommander/resources/omega.png"));
            splash = new ImageIcon(ImageArchive.class.getResource("/ru/narod/jcommander/resources/omega.png"));
            archive = new ImageIcon(ImageArchive.class.getResource("/ru/narod/jcommander/resources/archive.png"));
            arrowBack = new ImageIcon(ImageArchive.class.getResource("/ru/narod/jcommander/resources/arrowBack.png"));
            arrowForward = new ImageIcon(ImageArchive.class.getResource("/ru/narod/jcommander/resources/arrowForward.png"));

            fileSystemView = FileSystemView.getFileSystemView();
            systemFolderIcon = getSystemFolderIcon();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static Icon getSystemFolderIcon() {
        File temp = new File(System.getProperty("java.io.tmpdir"));
        return fileSystemView.getSystemIcon(temp);
    }

    public static ImageIcon getImageFolder() {
        return imageFolder;
    }

    public static ImageIcon getImageFile() {
        return imageFile;
    }

    public static ImageIcon getImageUp() {
        return imageUp;
    }

    public static ImageIcon getImageLogo() {
        return logo;
    }

    public static ImageIcon getImageSplash() {
        return splash;
    }

    public static ImageIcon getImageArchive() {
        return archive;
    }

    public static ImageIcon getArrowBack() {
        return arrowBack;
    }

    public static ImageIcon getArrowForward() {
        return arrowForward;
    }
    private static HashMap<String, Icon> systemIcons = new HashMap<String, Icon>();

    public static Icon getImageFile(BaseFile file, boolean system) {
        if (system) {
            String type = file.getExtention().toLowerCase();
            if (systemIcons.containsKey(type)) {
                return systemIcons.get(type);
            } else {
                Icon icon = getSystemIcon(file.getFilename());
                if (icon != null) {
                    systemIcons.put(type, icon);
                    return icon;
                }
            }
        }
        return FileHelper.getFileType(file) == FileHelper.FileType.ARCHIVE
                ? getImageArchive() : getImageFile();
    }

    public static Icon getImageFolder(boolean system) {
        return system ? systemFolderIcon : imageFolder;
    }

    private static Icon getSystemIcon(String filename) {
        Icon icon = null;
        try {
            File f = File.createTempFile("tmp", filename);
            icon = fileSystemView.getSystemIcon(f);
            f.delete();
        } catch (Exception e) {
        }
        return icon;
    }
    static HashMap<java.io.File, Icon> folders = new HashMap<java.io.File, Icon>();

    public static Icon getImageFolder(BaseFile file, boolean system) {
        File fi = file.toFile();
        if (system && fi != null) {
            if (folders.containsKey(fi)) {
                return folders.get(fi);
            } else {
                Icon icon = fileSystemView.getSystemIcon(fi);
                folders.put(fi, icon);
                return icon;
            }
        }
        return getImageFolder(system);
    }
}
