/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.narod.jcommander.actions;

import java.io.IOException;
import ru.narod.jcommander.gui.MainFrame;

/**
 *
 * @author master
 */
public class ActionExplorer extends AbstractAction {

    public ActionExplorer(MainFrame parent) {
        super(parent);
    }

    public void execute() {
        try {
            Runtime.getRuntime().exec("explorer " + parent.getActiveTable().getCurrentDir().getAbsolutePath());
        } catch (IOException ex) {
        }
    }
}
