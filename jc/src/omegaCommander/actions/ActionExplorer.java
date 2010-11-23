/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package omegaCommander.actions;

import java.io.IOException;
import omegaCommander.gui.MainFrame;

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
