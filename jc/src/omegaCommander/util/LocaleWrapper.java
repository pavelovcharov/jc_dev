/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package omegaCommander.util;

import java.util.Collection;
import java.util.Hashtable;
import java.util.Locale;

/**
 *
 * @author master
 */
public class LocaleWrapper {

    private Locale locale;
    private String displayName;

    public LocaleWrapper(Locale locale, String displayName) {
        this.locale = locale;
        this.displayName = displayName;
    }

    public LocaleWrapper(String localeString, String displayName) {
        this(new Locale(localeString), displayName);
    }

    @Override
    public String toString() {
        return String.format("%s", displayName);
    }

    public Locale getLocale() {
        return locale;
    }

    static void addLocale(String localeString, String name) {
        Locale l = new Locale(localeString);
        LocaleWrapper wrapper = new LocaleWrapper(l, name);
        locales.put(l, wrapper);
    }
    static LocaleWrapper defaultWrapper = new LocaleWrapper(Locale.getDefault(), "");
    static Hashtable<Locale, LocaleWrapper> locales = new Hashtable<Locale, LocaleWrapper>();

    static {
        addLocale("en_us", LanguageBundle.LocaleDescription_EN);
        addLocale("it_it", LanguageBundle.LocaleDescription_IT);
        addLocale("ru_ru", LanguageBundle.LocaleDescription_RU);
    }

    public static Collection<LocaleWrapper> getLocales() {
        return locales.values();
    }

    public static LocaleWrapper getLocaleWrapper(String localeString) {
        Locale l = new Locale(localeString);
        return locales.containsKey(l) ? locales.get(l) : defaultWrapper;
    }
}
