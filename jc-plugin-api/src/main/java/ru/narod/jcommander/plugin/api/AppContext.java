/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.narod.jcommander.plugin.api;

import java.net.URI;
import java.net.URL;
import java.util.List;
import javax.swing.JFrame;

/**
 *
 * @author master
 */
public abstract class AppContext implements PluginContext {

    protected URL leftUrl;
    protected URL rightUrl;
    protected List<URL> selectedItems;
    protected URL currentItem;
    protected JFrame owner;

    public AppContext(JFrame owner, URL leftDir, URL rightDir, List<URL> selectedItems, URL currentItem) {
        this.leftUrl = leftDir;
        this.rightUrl = rightDir;
        this.selectedItems = selectedItems;
        this.currentItem = currentItem;
    }

    public URL getLeftURL() {
        return leftUrl;
    }

    public URL getRightURL() {
        return rightUrl;
    }

    public URL getCurrentItem() {
        return currentItem;
    }

    public List<URL> getSelectedItems() {
        return  selectedItems;
    }

    public JFrame getOwner() {
        return  owner;
    }
    
}
