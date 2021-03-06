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
 * SplashFrame.java
 *
 * Created on 16 ��� 2006 �., 16:26
 */

package omegaCommander.gui.splash;

import omegaCommander.gui.ImageArchive;

/**
 *
 * @author  Pavel Ovcharov
 */
public class SplashFrame extends javax.swing.JFrame {
    
    private static javax.swing.Timer timer;
    private final int delay = 3000;
    //private ResourceArchive resourceArchive;

    /** Creates new form SplashFrame */
    public SplashFrame() {

        initComponents();
        
        javax.swing.ImageIcon splash;
        
        //resourceArchive = new ResourceArchive("omegaCommander.resources.jdc");
        //resourceArchive = ResourceArchive.getDefaultResourceArchive();
        //if(resourceArchive.getResource()!=null) {
            try {
                //splash = new javax.swing.ImageIcon(javax.imageio.ImageIO.read(resourceArchive.getResourceUrl("splashImage")));
                
                splash = ImageArchive.getImageSplash();
                setSize(splash.getIconWidth(), splash.getIconHeight());
            }
            catch(Exception e) {
                e.printStackTrace();
                //System.out.println(e);
                return;
            }

        //}
        //else return;
            
        setLocation(300, 300);
        setVisible(true);
        timer  = new javax.swing.Timer(delay, null);
        timer.setRepeats(false);
        timer.start();
        while(timer.isRunning()) {
            
        }
        setVisible(false);
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();

        getContentPane().setLayout(new java.awt.CardLayout());

        setResizable(false);
        setUndecorated(true);
        jPanel1.setLayout(new java.awt.CardLayout());

        jPanel1.setBorder(new javax.swing.border.EtchedBorder());
        jPanel1.add(jLabel1, "card2");

        getContentPane().add(jPanel1, "card2");

        pack();
    }

    // </editor-fold>//GEN-END:initComponents

    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
    
}
