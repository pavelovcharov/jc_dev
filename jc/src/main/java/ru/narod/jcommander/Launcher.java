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
 * Created on 26 apr 2005, 9:26
 */
package ru.narod.jcommander;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import ru.narod.jcommander.gui.MainFrame;

import java.util.ServiceLoader;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import ru.narod.jcommander.plugin.api.AppContext;
import ru.narod.jcommander.plugin.api.AppPlugin;
import ru.narod.jcommander.plugin.api.LookAndFeelPlugin;
import ru.narod.jcommander.plugins.PluginService;
import ru.narod.jcommander.plugins.PluginServiceFactory;
import sun.net.www.protocol.file.FileURLConnection;

/**
 * jc start class
 *
 * @author Pavel Ovcharov
 * @version 2005/04/26 9:26
 */
public class Launcher {

    /**
     * Launch application main window
     *
     * @param args Command line parameters. Not used for now.
     * @see MainFrame
     */
    public static void main(String[] args) {
	  
	Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {

            public void uncaughtException(Thread t, final Throwable e) {

                
                
                

                JCLogger.logWarning(null, e);

                java.awt.EventQueue.invokeLater(new Runnable() {

                    @Override
                    public void run() {
                        final Throwable t = e;
                        String message = t.toString();
                        String header = "Unhandled exception occured";
                        final JWindow frame = new JWindow();
                        frame.getRootPane().setBorder(new LineBorder(Color.DARK_GRAY, 2, true));

                        frame.setSize(300, 125);
                        frame.setLayout(new GridBagLayout());
                        GridBagConstraints constraints = new GridBagConstraints();
                        constraints.gridx = 0;
                        constraints.gridy = 0;
                        constraints.weightx = 1.0f;
                        constraints.weighty = 1.0f;
                        constraints.insets = new Insets(5, 5, 5, 5);
                        constraints.fill = GridBagConstraints.BOTH;
                        JLabel headingLabel = new JLabel(header);
//headingLabel.setIcon(headingIcon); // --- use image icon you want to be as heading image.
                        headingLabel.setOpaque(false);
                        frame.add(headingLabel, constraints);
                        constraints.gridx++;
                        constraints.weightx = 0f;
                        constraints.weighty = 0f;
                        constraints.fill = GridBagConstraints.NONE;
                        constraints.anchor = GridBagConstraints.NORTH;
//                        JButton cloesButton = new JButton("X");
                        Border emptyBorder = BorderFactory.createEmptyBorder();
                        JButton cloesButton = new JButton(new AbstractAction() {

                            @Override
                            public void actionPerformed(final ActionEvent e) {
                                frame.dispose();
                            }
                        });
                        //cloesButton.setBackground(Color.)
                        cloesButton.setBorder(emptyBorder);
                        cloesButton.setBorderPainted(false);
                        cloesButton.setContentAreaFilled(false);
                        cloesButton.setIcon(new Icon() {

                            private boolean descending;
                            private int size = 16;
                            private int priority;

//                            public Arrow(boolean descending, int size, int priority) {
//                                this.descending = descending;
//                                this.size = size;
//                                this.priority = priority;
//                            }

                            @Override
                            public void paintIcon(Component c, Graphics g, int x, int y) {
                                Color color = c == null ? Color.GRAY : c.getBackground();
                                // In a compound sort, make each succesive triangle 20%
                                // smaller than the previous one.
                                int dx = (int) (size / 2 * Math.pow(0.8, priority));
                                int dy = descending ? dx : -dx;
                                // Align icon (roughly) with font baseline.
                                y = y + 5 * size / 6 + (descending ? -dy : 0);
                                int shift = descending ? 1 : -1;
                                g.translate(x, y);

                                // Right diagonal.
                                g.setColor(color.darker());
                                g.drawLine(dx / 2, dy, 0, 0);
                                g.drawLine(dx / 2, dy + shift, 0, shift);

                                // Left diagonal.
                                g.setColor(color.brighter());
                                g.drawLine(dx / 2, dy, dx, 0);
                                g.drawLine(dx / 2, dy + shift, dx, shift);

                                // Horizontal line.
                                if (descending) {
                                    g.setColor(color.darker().darker());
                                } else {
                                    g.setColor(color.brighter().brighter());
                                }
                                g.drawLine(dx, 0, 0, 0);

                                g.setColor(color);
                                g.translate(-x, -y);
                            }

                            @Override
                            public int getIconWidth() {
                                return size;
                            }

                            @Override
                            public int getIconHeight() {
                                return size;
                            }
                        });
//                        cloesButton.setMaximumSize(new Dimension(50, 50));
                        cloesButton.setMinimumSize(new Dimension(32, 32));
                        cloesButton.setMargin(new Insets(1, 4, 1, 4));
                        cloesButton.setFocusable(false);
                        frame.add(cloesButton, constraints);
                        constraints.gridx = 0;
                        constraints.gridy++;
                        constraints.weightx = 1.0f;
                        constraints.weighty = 1.0f;
                        constraints.insets = new Insets(5, 5, 5, 5);
                        constraints.fill = GridBagConstraints.BOTH;
                        JLabel messageLabel = new JLabel("<HtMl>" + message);
                        frame.add(messageLabel, constraints);


                        Dimension scrSize = Toolkit.getDefaultToolkit().getScreenSize();// size of the screen
                        Insets toolHeight = Toolkit.getDefaultToolkit().getScreenInsets(frame.getGraphicsConfiguration());// height of the task bar
                        frame.setLocation(scrSize.width - frame.getWidth(), scrSize.height - toolHeight.bottom - frame.getHeight());

                        frame.setVisible(true);
                    }
                });


            }
        });
	
        try {
            loadPlugins();
            URI uri = new java.io.File(".").toURI();
            URL toURL = new java.io.File(".").toURI().toURL();
            JCAppContext context = new JCAppContext(null, toURL, toURL, null, null);

            String javaVersion = System.getProperty("java.version");
            MainFrame.run("jc", "v.0.659" + " @ Java " + javaVersion);
        } catch (MalformedURLException ex) {
            Logger.getLogger(Launcher.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void loadPlugins() {
        PluginService pluginService = PluginServiceFactory.createPluginService();
        pluginService.initPlugins();

        Iterator<AppPlugin> iterator = pluginService.getPlugins();
        if (!iterator.hasNext()) {
//            logger.info("No plugins were found!");
        }
        while (iterator.hasNext()) {
            AppPlugin plugin = iterator.next();
//            logger.info("Initializing the plugin " + plugin.getName());
//            plugin.init();
            if (plugin instanceof LookAndFeelPlugin) {
                Launcher.pfp = (LookAndFeelPlugin) plugin;
            }
        }
    }
    public static LookAndFeelPlugin pfp;
}

class JCAppContext extends AppContext {

    public JCAppContext(JFrame owner, URL leftDir, URL rightDir, List<URL> selectedItems, URL currentItem) {
        super(owner, leftDir, rightDir, selectedItems, currentItem);
    }
}
