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
 * NewMoveThread.java
 * Created on 23.01.2009 11:24:23
 */
package omegaCommander.threads.newThreads;

import java.util.ArrayList;
import omegaCommander.fileSystem.BaseFile;
import omegaCommander.fileSystem.FileSystemList;
import omegaCommander.fileSystem.net.NetFile;
import omegaCommander.fileSystem.SubDirectoriesList;
import omegaCommander.fileSystem.FileHelper;
import omegaCommander.util.LanguageBundle;
import omegaCommander.util.Support;

/**
 * ����� ���������� ����� ��� �����������/����������� ������.
 *
 * @author Pavel Ovcharov
 */
public class NewMoveThread extends FileThread {

    private boolean toCopy;
    private BaseFile sourceDir;
    private BaseFile[] filesToCopy;
    private BaseFile targetDir;
    private String tartgetPath;

    /**
     * ������� ����� ��������� ������ NewMovingThread
     */
    public NewMoveThread(BaseFile sourceDir, String targetPath, BaseFile[] filesToCopy, boolean toCopy) {
        this.sourceDir = sourceDir;
        this.tartgetPath = targetPath;
        this.filesToCopy = filesToCopy;
        this.toCopy = toCopy;
    }

    /**
     * �������� ����� ������. ����������� ��� ������ ������ <B>start()</B>
     */
    @Override
    public void run() {
        if (toCopy) {
            currentAction = LanguageBundle.getInstance().getString("StrCopying");
        } else {
            currentAction = LanguageBundle.getInstance().getString("StrMoving");
        }
        for (int i = 0; i < filesToCopy.length; i++) {
            totalFiles += FileSystemList.getSize(filesToCopy[i]);
        }
        ArrayList list = new ArrayList();
        for (int i = 0; i < filesToCopy.length; i++) {
            list.addAll(SubDirectoriesList.getList(filesToCopy[i]));
            if (filesToCopy.length == 1 && filesToCopy[0].isDirectory()) {
                list.remove(filesToCopy[0]);
                sourceDir = filesToCopy[i];

            } else {
                sourceDir = filesToCopy[i].getAbsoluteParent();
            }

            String target = CopyHelper.GetCopyTarget(filesToCopy, sourceDir, tartgetPath);
            targetDir = FileHelper.getRealFile(target);
            doAction(list, sourceDir);
            list.clear();
        }
    }

    void doAction(ArrayList files, BaseFile relativeParent) {
        BaseFile newFile;
        if (filesToCopy.length == 1 && filesToCopy[0].isDirectory()) {
            targetDir.mkdirs();
        }
        for (int i = 0; i < files.size(); i++) {
            BaseFile temp = (BaseFile) files.get(i);
            if (filesToCopy.length == 1 && !filesToCopy[0].isDirectory()) {
                newFile = targetDir;
                if (newFile.exists() && newFile.isDirectory()) {
                    newFile = FileHelper.getRealFile(targetDir, temp.getFilename());
                }
            } else {
                String relativePath = Support.getStringRelativeTo(temp.getPathWithSlash(), sourceDir.getPathWithSlash());
                newFile = FileHelper.getRealFile(targetDir, relativePath);
            }
            if (newFile.hasParent()) {
                newFile.getAbsoluteParent().mkdirs();
            }

            if (temp.isDirectory()) {
                newFile.mkdirs();
                continue;
            }
            try {
                if (newFile.exists()) {
                    if (newFile.equals(temp)) { //�������� ���� ��� � ����
                        if (queryError(String.format(LanguageBundle.getInstance().getString("StrCopyToItself"), newFile.getFilename()))) {
                            if (resultError.equals(ErrorAction.Retry)) { // try again
                                i--;
                            }
                            continue;
                        } else {//cancel
                            return;
                        }

                    } else {
                        //XXX �� �������� ��������� ��� ��� ����� Retry
                        if (queryReplace(temp, newFile)) {
                            switch (replaceResult) {
                                case REPLACE:
                                    //action(temp, newFile);
                                    break;
                                case REPLACE_OLD:
                                    if (temp.getLastModifiedTime() < newFile.getLastModifiedTime()) {
                                        filesReady += temp.length();
                                        continue;
                                    }
                                    break;
                                case SKIP:
                                    filesReady += temp.length();
                                    continue;
                            }
                            newFile.delete();
                        } else { // cancel operation
                            return;
                        }
                    }
                }
                action(temp, newFile);
            } catch (InterruptedException ie) {
                return;
            } catch (Exception ex) {
                if (queryError(LanguageBundle.getInstance().getString("StrFileNotAccessible") + " \n " + newFile.getAbsolutePath())) {
                    if (resultError.equals(ErrorAction.Retry)) { // try again
                        i--;
                        continue;
                    }
                } else {//cancel
                    return;
                }
            }
            filesReady += temp.length();
        }
        if (!toCopy) {//������� ����������
            for (int i = files.size() - 1; i >= 0; i--) {
                BaseFile file = (BaseFile) files.get(i);
                if (file.isDirectory()) {
                    file.delete();
                }
            }
            sourceDir.delete();
        }

    }

    private void jCopy(BaseFile source, BaseFile target) throws Exception {
        currentProgress = 0;
        currentFileSize = source.length();

        currentAction = LanguageBundle.getInstance().getString("StrCopying") + " " + source.getFilename();

        java.io.InputStream fis = source.getInputStream();
        java.io.OutputStream fos = target.getOutputStream();
        byte[] buf = new byte[2048];
        int readCount = 0;
        while ((readCount = fis.read(buf)) != -1) {
            fos.write(buf, 0, readCount);
            currentProgress += readCount;

            if (interrupt == true) {
                fis.close();
                fos.close();
                //������� ���������������� ����
                target.delete();
                throw new InterruptedException();
            }
        }
        currentProgress = currentFileSize;
        fis.close();
        fos.close();
        target.setLastModified(source.getLastModifiedTime());
    }

    private void jMove(BaseFile source, BaseFile target) throws Exception {
        currentAction = LanguageBundle.getInstance().getString("StrMoving") + " " + source.getFilename();

        currentProgress = 0;
        currentFileSize = source.length();

        //XXX �������� ����������� ������������ � ������
        //XXX ���� ����� rename � NetFile
        //XXX ��� �������� �����������
        boolean result = true;
        if ((source instanceof NetFile) || (target instanceof NetFile)) {
            jCopy(source, target);
            source.delete();
        } else {
            result = source.renameTo(target);
        }
        if (!result) {
            throw new Exception();
        }
        if (interrupt == true) {
            throw new InterruptedException();
        }
        currentProgress = currentFileSize;
    }

    private void action(BaseFile temp, BaseFile newFile) throws Exception {
        if (toCopy) {
            jCopy(temp, newFile);
        } else {
            jMove(temp, newFile);
        }
    }
}
