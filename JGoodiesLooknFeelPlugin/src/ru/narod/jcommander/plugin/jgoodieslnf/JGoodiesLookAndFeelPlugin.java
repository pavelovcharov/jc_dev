/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.narod.jcommander.plugin.jgoodieslnf;

import com.jgoodies.looks.plastic.Plastic3DLookAndFeel;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.LookAndFeel;
import ru.narod.jcommander.plugin.api.LookAndFeelPlugin;

/**
 *
 * @author master
 */
public class JGoodiesLookAndFeelPlugin implements LookAndFeelPlugin {
    private static Logger logger = Logger.getLogger(JGoodiesLookAndFeelPlugin.class.getName());
    @Override
    public LookAndFeel getLookAndFeel() {
        return lnf;
    }

    @Override
    public String getName() {
        return "JGoodiesLookAndFeelPlugin";
    }
    private Plastic3DLookAndFeel lnf;
    @Override
    public void init() {
        lnf = new Plastic3DLookAndFeel();
        logger.log(Level.INFO, "{0} initialized!", getName());
    }
    
}
