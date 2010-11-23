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
 * Created on 30 Март 2006 г., 9:55
 *
 */
package omegaCommander.gui;

import javax.swing.ImageIcon;

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

	static {
		try {
			imageFolder = new ImageIcon(ImageArchive.class.getResource("/omegaCommander/resources/folder16x16.gif"));
			imageFile = new ImageIcon(ImageArchive.class.getResource("/omegaCommander/resources/file16x16.gif"));
			imageUp = new ImageIcon(ImageArchive.class.getResource("/omegaCommander/resources/up.gif"));
			logo = new ImageIcon(ImageArchive.class.getResource("/omegaCommander/resources/omega.png"));
			splash = new ImageIcon(ImageArchive.class.getResource("/omegaCommander/resources/omega.png"));
			archive = new ImageIcon(ImageArchive.class.getResource("/omegaCommander/resources/archive.png"));
			arrowBack = new ImageIcon(ImageArchive.class.getResource("/omegaCommander/resources/arrowBack.png"));
			arrowForward = new ImageIcon(ImageArchive.class.getResource("/omegaCommander/resources/arrowForward.png"));

		} catch (Exception e) {
			e.printStackTrace();
		}
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
}
