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
 * BaseDialog.java
 *
 * Created on 8 Èþíü 2007 ã., 13:59
 */
package omegaCommander.gui.dialog;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.Iterator;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 *
 * @author  Programmer
 */
public class BaseDialog extends javax.swing.JDialog {

    private JButton defaultButton;

    /** Creates new form BaseDialog */
    public BaseDialog(JFrame parent, String title, Object[] message,
            Object[] options, int defaultOption) {
        super(parent);
        createDialog(title, message, options, defaultOption);
    }

    public BaseDialog(JDialog parent, String title, Object[] message,
            Object[] options, int defaultOption) {
        super(parent);
        createDialog(title, message, options, defaultOption);
    }

    private void createDialog(String title, Object[] message,
            Object[] options, int defaultOption) {
        initComponents();
        setTitle(title);

        for (int i = 0; i < options.length; i++) {
            JButton b;
            if (options[i] instanceof JButton) {
                b = (JButton) options[i];
            } else {
                b = new JButton(options[i].toString());
                b.addActionListener(new ButtonActionListener(this, i));
                b.addKeyListener(new ButtonKeyListener(this));
            }
            if (i == defaultOption) {
                defaultButton = b;
            }
            b.setPreferredSize(new Dimension(100, 25));
            b.setToolTipText(b.getText());
            jPanel2.add(b);
        }

        jPanel1.setLayout(new java.awt.GridLayout(message.length, 1, 0, 5));
        for (int i = 0; i < message.length; i++) {
            if (message[i] instanceof Component) {
                JComponent c = (JComponent) message[i];
                c.addKeyListener(new ComponentKeyListener(this, defaultButton));
                if (!(c instanceof JTextField) && !(c instanceof JComboBox)) {
                    c.addKeyListener(new CursorKeyListener());
                }
                jPanel1.add(c);
            } else {
                jPanel1.add(new JLabel((String) message[i]));
            }

        }

        repack();

        if (null != defaultButton) {
            defaultButton.requestFocus();
        }
    }

    public void repack() {
        pack();
        int width = getWidth();
        int height = getHeight();
        if (getWidth() < 300) {
            width = 300;
        }
        if (getHeight() < 120) {
            height = 120;
        }
        setSize(width, height);
    }

    public void setIcon(JLabel iconLabel) {
        jPanel4.add(iconLabel);
    }

    @Override
    public void setSize(int width, int height) {
        super.setSize(width, height);
        setLocationRelativeTo(getParent());
    }

    static int showDialog() {
        return 0;
    }

    public int getResult() {
        return result;
    }

    public void setButtonSize(int newWidth) {
        Component[] c = jPanel2.getComponents();
        if (null == c) {
            return;
        }
        for (int i = 0; i < c.length; i++) {
            if (c[i] instanceof JButton) {
                JButton b = (JButton) c[i];
                b.setPreferredSize(new Dimension(newWidth, c[i].getHeight()));
            }
        }
        repack();
    }
    private List _listeners = new ArrayList();

    public synchronized void addActionEventListener(BaseDialogActionEventListener listener) {
        _listeners.add(listener);
    }

    public synchronized void removeActioEventListener(BaseDialogActionEventListener listener) {
        _listeners.remove(listener);
    }

    private synchronized void fireEvent() {
        BaseDialogActionEvent event = new BaseDialogActionEvent(this);
        Iterator i = _listeners.iterator();
        while (i.hasNext()) {
            ((BaseDialogActionEventListener) i.next()).handleBaseDialogActionEvent(event);
        }
    }

    public void fireActionEvent() {
        fireEvent();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setModal(true);
        setResizable(false);
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentHidden(java.awt.event.ComponentEvent evt) {
                formComponentHidden(evt);
            }
        });
        getContentPane().setLayout(new java.awt.BorderLayout(5, 0));

        jPanel1.setLayout(new java.awt.GridLayout(1, 0));
        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);
        getContentPane().add(jPanel2, java.awt.BorderLayout.SOUTH);
        getContentPane().add(jPanel3, java.awt.BorderLayout.EAST);
        getContentPane().add(jPanel4, java.awt.BorderLayout.WEST);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formComponentHidden(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentHidden
        fireActionEvent();
    }//GEN-LAST:event_formComponentHidden

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    // End of variables declaration//GEN-END:variables
    private int result = -1;

    private class ButtonActionListener implements ActionListener {

        private int buttonNumber;
        private BaseDialog owner;

        public ButtonActionListener(BaseDialog owner, int buttonNumber) {
            this.buttonNumber = buttonNumber;
            this.owner = owner;
        }

        public void actionPerformed(ActionEvent e) {
            result = buttonNumber;
            owner.setVisible(false);
            owner.fireActionEvent();

        }
    }

    public class BaseDialogActionEvent extends java.util.EventObject {

        public BaseDialogActionEvent(Object source) {
            super(source);
        }
    }

    public interface BaseDialogActionEventListener {

        public void handleBaseDialogActionEvent(EventObject e);
    }
}
