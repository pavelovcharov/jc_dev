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

import omegaCommander.fileSystem.AbsoluteFile;
import omegaCommander.fileSystem.FileSystemList;
import omegaCommander.fileSystem.net.NetFile;
import omegaCommander.fileSystem.SubDirectoriesList;
import omegaCommander.fileSystem.SuperFile;
import omegaCommander.util.LanguageBundle;
import omegaCommander.util.Support;

/**
 * Класс определяет поток для копирования/перемещения файлов.
 *
 * @author Pavel Ovcharov
 */
public class NewMoveThread extends FileThread {

    private boolean toCopy;
    private AbsoluteFile sourceDir;
    private AbsoluteFile[] filesToCopy;
    private AbsoluteFile targetDir;
    private ArrayList list = new ArrayList();
    private boolean oneFileFlag;

    /**
     * Создает новый экземпляр класса NewMovingThread
     */
    public NewMoveThread(AbsoluteFile sourceDir, AbsoluteFile targetDir, AbsoluteFile[] filesToCopy, boolean toCopy) {
        this.sourceDir = sourceDir;
        this.targetDir = targetDir;
        this.filesToCopy = filesToCopy;
        this.toCopy = toCopy;
        oneFileFlag = 1 == filesToCopy.length && !filesToCopy[0].isDirectory();
        if (filesToCopy.length == 1 && filesToCopy[0].isDirectory())
            this.targetDir = targetDir.getAbsoluteParent();
    }

    /**
     * Основной метод потока. Запускается при вызове метода <B>start()</B>
     */
    @Override
    public void run() {
        if (toCopy) {
            currentAction = LanguageBundle.getInstance().getString("StrCopying");
        } else {
            currentAction = LanguageBundle.getInstance().getString("StrMoving");
        }

//        if(targetDir.hasParent())
//            targetDir.getAbsoluteParent().mkdirs();

        for (int i = 0; i < filesToCopy.length; i++) {
            totalFiles += FileSystemList.getSize(filesToCopy[i]);
        }
        for (int i = 0; i < filesToCopy.length; i++) {
            list.addAll(SubDirectoriesList.getList(filesToCopy[i]));
            sourceDir = filesToCopy[i].getAbsoluteParent();
            action();
            list.clear();
        }
//        action();
    }

    private int action() {
        AbsoluteFile newFile;
        for (int i = 0; i < list.size(); i++) {
            AbsoluteFile temp = (AbsoluteFile) list.get(i);
            if (oneFileFlag) {
//                newFile = targetDir;
                String relativePath = Support.getStringRelativeTo(temp.getPathWithSlash(), targetDir.getPathWithSlash());
                newFile = SuperFile.getRealFile(targetDir, relativePath);
            } else {
                String relativePath = Support.getStringRelativeTo(temp.getPathWithSlash(), sourceDir.getPathWithSlash());
                newFile = SuperFile.getRealFile(targetDir, relativePath);
            }
            if(newFile.hasParent())
                newFile.getAbsoluteParent().mkdirs();

            if (temp.isDirectory()) {
                newFile.mkdirs();
                continue;
            }
            try {
                if (newFile.exists()) {
                    if (newFile.equals(temp)) { //копируем файл сам в себя
                        if (queryError(String.format(LanguageBundle.getInstance().getString("StrCopyToItself"), newFile.getFilename()))) {
                            if (resultError.equals(ErrorAction.Retry)) { // try again
                                i--;
                                continue;
                            }
                        } else {//cancel
                            return 0;
                        }

                    } else {
                        //XXX не выдавать сообщение еще раз после Retry
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
                            return 0;
                        }
                    }
                }
                action(temp, newFile);
            } catch (InterruptedException ie) {
                return 0;
            } catch (Exception ex) {
                if (queryError(LanguageBundle.getInstance().getString("StrFileNotAccessible") + " \n " + newFile.getAbsolutePath())) {
                    if (resultError.equals(ErrorAction.Retry)) { // try again
                        i--;
                        continue;
                    }
                } else {//cancel
                    return 0;
                }
            }
            filesReady += temp.length();
        }
        if (!toCopy) {//удаляем директории
            for (int i = list.size() - 1; i >= 0; i--) {
                AbsoluteFile file = (AbsoluteFile) list.get(i);
                if (file.isDirectory()) {
                    file.delete();
                }
            }
        }
        return 0;
    }

    private void jCopy(AbsoluteFile source, AbsoluteFile target) throws Exception {
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
                //удаляем недокопированный файл
                target.delete();
                throw new InterruptedException();
            }
        }
        currentProgress = currentFileSize;
        fis.close();
        fos.close();
        target.setLastModified(source.getLastModifiedTime());
    }

    private void jMove(AbsoluteFile source, AbsoluteFile target) throws Exception {
        currentAction = LanguageBundle.getInstance().getString("StrMoving") + " " + source.getFilename();

        currentProgress = 0;
        currentFileSize = source.length();

        //XXX заменить перемещение копированием в архиве
        //XXX есть метод rename у NetFile
        //XXX как отменить перемещение
        if ((source instanceof NetFile) || (target instanceof NetFile)) {
            jCopy(source, target);
            source.delete();
        } else {
            source.renameTo(target);
        }
        if (interrupt == true) {
            throw new InterruptedException();
        }
        currentProgress = currentFileSize;
    }

    private void action(AbsoluteFile temp, AbsoluteFile newFile) throws Exception {
        if (toCopy) {
            jCopy(temp, newFile);
        } else {
            jMove(temp, newFile);
        }

    }
}
