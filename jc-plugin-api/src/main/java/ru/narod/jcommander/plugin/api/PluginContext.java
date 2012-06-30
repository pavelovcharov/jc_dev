/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.narod.jcommander.plugin.api;

import java.net.URL;
import java.util.List;
import javax.swing.JFrame;

/**
 *
 * @author master
 */

public interface PluginContext {
    URL getLeftURL();
    URL getRightURL();
    URL getCurrentItem();
    List<URL> getSelectedItems();
    JFrame getOwner();
}
