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
 * NewPackThread.java
 * Created on 09.04.2009 9:22:23
 */
package omegaCommander.threads.newThreads;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.zip.Deflater;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import omegaCommander.fileSystem.BaseFile;
import omegaCommander.fileSystem.SubDirectoriesList;
import omegaCommander.gui.dialog.WarningDialog;
import omegaCommander.util.LanguageBundle;
import omegaCommander.util.Support;

/**
 *
 * @author Programmer
 */
public class NewPackThread extends BaseThread {

	private BaseFile sourceDir;
	private BaseFile targetFile;
	private BaseFile[] filesToPack;
	private int packLevel;
	private ArrayList list = new ArrayList();

	public NewPackThread(BaseFile sourceDir, BaseFile targetFile, BaseFile[] filesToPack, int packLevel) {
		this.sourceDir = sourceDir;
		this.targetFile = targetFile;
		this.filesToPack = filesToPack;

		switch (packLevel) {
			case 0: {
				this.packLevel = Deflater.NO_COMPRESSION;
				break;
			}
			case 1: {
				this.packLevel = Deflater.BEST_SPEED;
				break;
			}
			case 2: {
				this.packLevel = Deflater.DEFAULT_COMPRESSION;
				break;
			}
			case 3: {
				this.packLevel = Deflater.BEST_COMPRESSION;
				break;
			}
			default:
				this.packLevel = Deflater.DEFAULT_COMPRESSION;
				break;
		}
	}

	@Override
	public void run() {
		for (int i = 0; i < filesToPack.length; i++) {
			list.addAll(SubDirectoriesList.getList(filesToPack[i]));
		}
		totalFiles = list.size();
		action();
	}

	private void action() {

		BaseFile targetDir = targetFile.getAbsoluteParent();
		targetDir.mkdirs();

		LanguageBundle lb = LanguageBundle.getInstance();
		if (targetFile.exists()) {
			int res = WarningDialog.showMessage(parent, lb.getString("StrReplace") + " " + targetFile.getAbsolutePath(), lb.getString("StrJC"),
					new String[]{lb.getString("StrOk"), lb.getString("StrCancel")}, WarningDialog.MESSAGE_WARNING, 0);
			if (0 != res) {
				return;
			}
		}

		ZipOutputStream zos = null;
		try {
			zos = new ZipOutputStream(targetFile.getOutputStream());
			zos.setLevel(packLevel);
		} catch (Exception ex) {
			WarningDialog.showMessage(parent, lb.getString("StrFileNotAccessible") + " \n " + targetDir.getAbsolutePath(),
					lb.getString("StrJC"), new String[]{lb.getString("StrOk")}, WarningDialog.MESSAGE_ERROR, 0);
			return;
		}

		ZipEntry ze;
		for (int i = 0; i < list.size(); i++) {
			BaseFile temp = (BaseFile) list.get(i);
			try {
				String relativePath = Support.getStringRelativeTo(temp.getPathWithSlash(), sourceDir.getPathWithSlash());
				currentAction = lb.getString("StrArchiving") + " " + temp.getFilename();
				ze = new ZipEntry(relativePath.replace(File.separatorChar, '/'));
				zos.putNextEntry(ze);
				if (false == temp.isDirectory()) {
					InputStream fis = temp.getInputStream();

					currentFileSize = temp.length();
					currentProgress = 0;
					byte[] buf = new byte[8000];
					int nLength;
					while (true) {
						nLength = fis.read(buf);
						if (nLength < 0) {
							break;
						}
						currentProgress += nLength;
						zos.write(buf, 0, nLength);
						if (interrupt) {
							throw new InterruptedException();
						}
					}
					fis.close();
					currentProgress = currentFileSize;
				}
				zos.closeEntry();
			} catch (InterruptedException e) {
				break;
			} catch (Exception e) {
				targetFile.delete();
				WarningDialog.showMessage(parent, lb.getString("StrFileNotAccessible") + " \n " + temp.getAbsolutePath(),
						lb.getString("StrJC"), new String[]{lb.getString("StrOk")}, WarningDialog.MESSAGE_ERROR, 0);
				break;
			}
			filesReady++;
		}
		try {
			zos.close();
		} catch (Exception ex) {
//			targetFile.delete();
			WarningDialog.showMessage(parent, lb.getString("StrFileNotAccessible") + " \n " + targetFile.getAbsolutePath(),
					lb.getString("StrJC"), new String[]{lb.getString("StrOk")}, WarningDialog.MESSAGE_ERROR, 0);
		}
	}
}
