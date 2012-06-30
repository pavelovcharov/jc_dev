/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.narod.jcommander.plugin.api;

import javax.swing.LookAndFeel;

/**
 *
 * @author master
 */
public interface LookAndFeelPlugin extends AppPlugin {
    LookAndFeel getLookAndFeel();
}
