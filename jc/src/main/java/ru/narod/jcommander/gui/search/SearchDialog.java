/*
 * This file is part of jc, http://www.jcommander.narod.ru
 * Copyright (C) 2005-2011 Pavel Ovcharov
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
 * SearchDialog.java
 *
 * Created on Sep 3, 2011, 4:49:12 PM
 */
package ru.narod.jcommander.gui.search;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Collection;
import java.util.List;
import javax.swing.*;
import ru.narod.jcommander.fileSystem.BaseFile;
import ru.narod.jcommander.fileSystem.FileHelper;
import ru.narod.jcommander.fileSystem.PseudoFolder;
import ru.narod.jcommander.gui.MainFrame;
import ru.narod.jcommander.threads.newThreads.NewSearchThread;
import ru.narod.jcommander.threads.newThreads.SearchStatusThread;
import ru.narod.jcommander.util.LanguageBundle;

/**
 *
 * @author master
 */
public class SearchDialog extends javax.swing.JDialog implements SearchStatusListener, ActionListener, KeyListener {

    MainFrame parent;
    LanguageBundle currentBundle;
    NewSearchThread searchThread;
    final SearchResultListCellRenderer searchResultListCellRenderer = new SearchResultListCellRenderer();
    final DefaultListCellRenderer defaultListCellRenderer = new DefaultListCellRenderer();

    /**
     * Creates new form SearchDialog
     */
    public SearchDialog(MainFrame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.parent = parent;
        startListen();
    }

    public void setupComponents(LanguageBundle languageBundle) {
        currentBundle = languageBundle;
        this.setTitle(languageBundle.getString("StrSearch"));
        jLabelFindWhat.setText(languageBundle.getString("StrFindWhat"));
        jLabelFindWhere.setText(languageBundle.getString("StrFindWhere"));
        jCheckBoxFindText.setText(languageBundle.getString("StrFindText"));
        jCheckBoxCase.setText(languageBundle.getString("StrCase"));
        jButtonFind.setText(languageBundle.getString("StrSearch"));
        jButtonCancelFind.setText(languageBundle.getString("StrCancel"));
        jLabelSearchStatus.setText(languageBundle.getString("StrReady"));
        jButtonBrowse.setText(languageBundle.getString("StrBrowse"));
        jButtonPanelize.setText(languageBundle.getString("StrFeedToPanel"));
    }

    private void startListen() {
        jButtonFind.addKeyListener(this);
        jButtonCancelFind.addKeyListener(this);
        jButtonBrowse.addKeyListener(this);
        jButtonPanelize.addKeyListener(this);
        jCheckBoxCase.addKeyListener(this);
        jCheckBoxFindText.addKeyListener(this);
        jTextFieldFindText.addKeyListener(this);
        jTextFieldFindWhat.addKeyListener(this);
        jTextFieldFindWhere.addKeyListener(this);

        jButtonFind.setActionCommand("find");
        jButtonFind.addActionListener(this);
        jButtonCancelFind.setActionCommand("cancel");
        jButtonCancelFind.addActionListener(this);
        jButtonPanelize.setActionCommand("panelize");
        jButtonPanelize.addActionListener(this);
        jButtonBrowse.setActionCommand("browse");
        jButtonBrowse.addActionListener(this);

        jList1.addKeyListener(this);

        jCheckBoxFindText.setActionCommand("searchText");
        jCheckBoxFindText.addActionListener(this);
    }

    private void onKeyPressed(KeyEvent evt) {
        Component component = evt.getComponent();
        int key = evt.getKeyCode();
        if (KeyEvent.VK_ESCAPE == key) {
            if (null != searchThread && searchThread.isAlive()) {
                stopSearchThread();
            } else {
                closeDialog();
            }
            return;
        }
        if (component instanceof JList) {
            JList sender = (JList) component;
            BaseFile af = (BaseFile) sender.getSelectedValue();
            if (af != null) {
                if (key == KeyEvent.VK_ENTER) {
                    closeDialog();
                    parent.moveToFile(af);
                }
                if (key == KeyEvent.VK_F3) {
                    parent.viewFile(af);
                }
                if (key == KeyEvent.VK_F4) {
                    parent.runFileInEditor(af);
                }
            }
            return;
        }
        if (KeyEvent.VK_ENTER == key) {
            JButton sender = component instanceof JButton
                    ? (JButton) evt.getComponent()
                    : jButtonFind;
            sender.doClick();
        }
        if (KeyEvent.VK_UP == key || KeyEvent.VK_LEFT == key) {
            if (component instanceof JButton || component instanceof JCheckBox) {
                component.transferFocusBackward();
            }
        }
        if (KeyEvent.VK_DOWN == key || KeyEvent.VK_RIGHT == key) {
            if (component instanceof JButton || component instanceof JCheckBox) {
                component.transferFocus();
            }
        }
    }

    private void closeDialog() {
        setVisible(false);
        stopSearchThread();
    }

    private void startSearchThread(String findWhat, String findWhere, String findText, boolean matchCase) {
        ((GeneratedListModel) jList1.getModel()).clear();
        BaseFile file = FileHelper.getRealFile(findWhere);
        searchThread = new NewSearchThread(file, findWhat, findText, matchCase);
        SearchStatusThread sst = new SearchStatusThread(searchThread);
        sst.addListener(this);
        sst.start();
        searchThread.start();
    }

    private void stopSearchThread() {
        if (searchThread != null) {
            searchThread.setInterrupt(true);
            while (searchThread.isAlive()) {
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabelFindWhat = new javax.swing.JLabel();
        jLabelFindWhere = new javax.swing.JLabel();
        jCheckBoxFindText = new javax.swing.JCheckBox();
        jTextFieldFindWhat = new javax.swing.JTextField();
        jTextFieldFindWhere = new javax.swing.JTextField();
        jTextFieldFindText = new javax.swing.JTextField();
        jCheckBoxCase = new javax.swing.JCheckBox();
        jButtonBrowse = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList();
        jToolBar1 = new javax.swing.JToolBar();
        jLabelSearchStatus = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jButtonCancelFind = new javax.swing.JButton();
        jButtonFind = new javax.swing.JButton();
        jButtonPanelize = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();

        setMinimumSize(new java.awt.Dimension(700, 450));
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                formComponentShown(evt);
            }
        });

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabelFindWhat.setText("jLabel1");

        jLabelFindWhere.setText("jLabel2");

        jCheckBoxFindText.setText("jCheckBox1");

        jTextFieldFindWhat.setText("*");

        jTextFieldFindText.setEnabled(false);

        jCheckBoxCase.setText("jCheckBox2");
        jCheckBoxCase.setEnabled(false);

        jButtonBrowse.setText("jButton1");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelFindWhat)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelFindWhere)
                            .addComponent(jCheckBoxFindText))
                        .addGap(12, 12, 12)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextFieldFindText, javax.swing.GroupLayout.DEFAULT_SIZE, 164, Short.MAX_VALUE)
                            .addComponent(jTextFieldFindWhat, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 164, Short.MAX_VALUE)
                            .addComponent(jCheckBoxCase)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addComponent(jTextFieldFindWhere, javax.swing.GroupLayout.DEFAULT_SIZE, 85, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButtonBrowse)))))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelFindWhat)
                    .addComponent(jTextFieldFindWhat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelFindWhere)
                    .addComponent(jTextFieldFindWhere, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonBrowse))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldFindText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jCheckBoxFindText))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 9, Short.MAX_VALUE)
                .addComponent(jCheckBoxCase))
        );

        jList1.setModel(new GeneratedListModel());
        jList1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jList1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jList1);

        jToolBar1.setFloatable(false);

        jLabelSearchStatus.setText("jLabel3");
        jToolBar1.add(jLabelSearchStatus);

        jButtonCancelFind.setText("jButton2");

        jButtonFind.setText("jButton1");

        jButtonPanelize.setText("jButton3");
        jButtonPanelize.setEnabled(false);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButtonFind, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButtonPanelize, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.DEFAULT_SIZE, 73, Short.MAX_VALUE)
                    .addComponent(jButtonCancelFind, javax.swing.GroupLayout.DEFAULT_SIZE, 73, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButtonFind)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonCancelFind)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(jButtonPanelize)
                .addGap(40, 40, 40))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jToolBar1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 281, Short.MAX_VALUE)
                            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE)
                        .addGap(15, 15, 15))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(11, 11, 11))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, 0)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGap(0, 0, 0)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 303, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, 0)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGap(0, 0, 0)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentShown
        jTextFieldFindWhat.requestFocus();
        jTextFieldFindWhat.selectAll();
        jTextFieldFindWhere.setText(parent.getActiveTable().getCurrentDir().getAbsolutePath());
}//GEN-LAST:event_formComponentShown

    private void jList1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jList1MouseClicked
        JList sender = (JList) evt.getComponent();
        if (2 == evt.getClickCount()) {
            BaseFile af = (BaseFile) jList1.getSelectedValue();
            if (af != null) {
                closeDialog();
                parent.moveToFile(af);
            }
        }
    }//GEN-LAST:event_jList1MouseClicked
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonBrowse;
    private javax.swing.JButton jButtonCancelFind;
    private javax.swing.JButton jButtonFind;
    private javax.swing.JButton jButtonPanelize;
    private javax.swing.JCheckBox jCheckBoxCase;
    private javax.swing.JCheckBox jCheckBoxFindText;
    private javax.swing.JLabel jLabelFindWhat;
    private javax.swing.JLabel jLabelFindWhere;
    private javax.swing.JLabel jLabelSearchStatus;
    private javax.swing.JList jList1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextField jTextFieldFindText;
    private javax.swing.JTextField jTextFieldFindWhat;
    private javax.swing.JTextField jTextFieldFindWhere;
    private javax.swing.JToolBar jToolBar1;
    // End of variables declaration//GEN-END:variables
    // <editor-fold defaultstate="collapsed" desc="SearchStatusListener">

    public void onSearchStart() {
        SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                jList1.setCellRenderer(defaultListCellRenderer);
                jLabelSearchStatus.setText(currentBundle.getString("StrSearch") + "...");
                jButtonFind.setActionCommand("stop");
            }
        });
    }

    public void onSearchProgressChange(List found) {
        final Collection list = found;
        SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                GeneratedListModel model = (GeneratedListModel) jList1.getModel();
                model.addData(list);
            }
        });

    }

    public void onSearchEnd(List found) {
        final Collection list = found;
        SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                GeneratedListModel model = (GeneratedListModel) jList1.getModel();
                model.addData(list);
                model.sortByPath();
                String status = currentBundle.getString("StrSearchComplete") + ". " + currentBundle.getString("StrFound") + " " + model.getSize();
                jLabelSearchStatus.setText(status);
                jButtonFind.setText(currentBundle.getString("StrSearch"));
                jButtonPanelize.setEnabled(model.getSize() > 0);
                jList1.setCellRenderer(searchResultListCellRenderer);
                jButtonFind.setActionCommand("find");
            }
        });

    }// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="ActionListener">

    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if (command.equals("find")) {
            jButtonPanelize.setEnabled(false);
            String what = jTextFieldFindWhat.getText().trim();
            String where = jTextFieldFindWhere.getText().trim();
            if (where.equals("")) {
                return;
            }
            String text = "";
            if (jCheckBoxFindText.isSelected()) {
                text = jTextFieldFindText.getText();
            }
            startSearchThread(what, where, text, jCheckBoxCase.isSelected());
            jButtonFind.setText(currentBundle.getString("StrStop"));
        }
        if (command.equals("stop")) {
            if (null != searchThread) {
                if (searchThread.isAlive()) {
                    stopSearchThread();
                    return;
                }
            }
        }
        if (command.equals("cancel")) {
            closeDialog();
        }
        if (command.equals("browse")) {
            JFileChooser fileChooser = new JFileChooser();
            //fileChooser.setMultiSelectionEnabled(true);
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            if (JFileChooser.APPROVE_OPTION == fileChooser.showOpenDialog(this)) {
                jTextFieldFindWhere.setText(fileChooser.getSelectedFile().getAbsolutePath());
            }
        }
        if (command.equals("panelize")) {
            GeneratedListModel model = (GeneratedListModel) jList1.getModel();
            BaseFile[] files = model.getList();
            PseudoFolder pseudoFolder = new PseudoFolder(parent.getActiveTable().getCurrentDir(), files);
            parent.getActiveTable().setCurrentDir(pseudoFolder);
            parent.updateActivePanel();
            closeDialog();
        }
        if (command.equals("searchText")) {
            boolean selected = jCheckBoxFindText.isSelected();
            jTextFieldFindText.setEnabled(selected);
            jCheckBoxCase.setEnabled(selected);
            if (selected) {
                jTextFieldFindText.requestFocus();
            }
        }
    }// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="KeyListener">

    public void keyTyped(KeyEvent e) {
    }

    public void keyPressed(KeyEvent e) {
        onKeyPressed(e);
    }

    public void keyReleased(KeyEvent e) {
    }// </editor-fold>
}
