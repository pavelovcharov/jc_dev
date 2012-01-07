/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.narod.jcommander.util;

/**
 *
 * @author master
 */
public class PlatformHelper {
    public static final String DEFAULT_HANDLER_TEXT;// = "rundll32 url.dll,FileProtocolHandler";
    static {
        if (System.getProperty("os.name").startsWith("Windows")) {
            DEFAULT_HANDLER_TEXT = "rundll32 url.dll,FileProtocolHandler";
        } else {
            DEFAULT_HANDLER_TEXT = "";
        }
    }
}
