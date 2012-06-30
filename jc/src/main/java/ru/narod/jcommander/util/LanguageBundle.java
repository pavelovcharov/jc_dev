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
 * LanguageBundle.java
 * Created on 09.04.2009 11:15:10
 */
package ru.narod.jcommander.util;

import java.util.Formatter;
import java.util.Locale;
import java.util.ResourceBundle;
import ru.narod.jcommander.prefs.PrefKeys;

/**
 *
 * @author Programmer
 */
public class LanguageBundle implements PrefKeys {

    public static final String LocaleDescription_RU = "Русский";
    public static final String LocaleDescription_EN = "English";
    public static final String LocaleDescription_IT = "Italiano by joe20";
    private ResourceBundle bundle;
    private static LanguageBundle instance = new LanguageBundle();

    public static LanguageBundle getInstance() {
        return instance;
    }

    private LanguageBundle() {
        generateBundle();
    }

    public void generateBundle() {
        java.util.prefs.Preferences pref = java.util.prefs.Preferences.userNodeForPackage(ru.narod.jcommander.gui.MainFrame.class);
        Locale locale = Locale.getDefault();
        locale = new Locale(pref.get(PK_LOCALE, locale.getLanguage() + "_" + locale.getCountry()));
        bundle = java.util.ResourceBundle.getBundle(PATH_TO_LANGUAGE, locale);
    }

    public String getString(String key) {
        return bundle.getString(key);
    }

    public String format(String key, Object... args) {
        return new Formatter().format(getString(key), args).toString();
    }
}
