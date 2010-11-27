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
 * Main.java
 *
 * Created on 26 Апрель 2005 г., 9:26
 */
package omegaCommander;

import omegaCommander.gui.MainFrame;

/**
 * Главный класс. Функция main запускает основное окно приложения
 * @author Pavel Ovcharov
 * @version 2005/04/26 9:26
 */
public class Launcher {

    /**
     * Функция заускает главное окно приложения
     * @param args Параметры командной строки. В настоящее время не используются.
     * @see MainFrame
     */
    public static void main(String[] args) {
        String javaVersion = System.getProperty("java.version");
        MainFrame.run("jc", "v.0.655" + " @ Java " + javaVersion);
    }
}
