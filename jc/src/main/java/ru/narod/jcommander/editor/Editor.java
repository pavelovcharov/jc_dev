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
 * Editor.java
 *
 * Created on 25 nov 2006, 17:42
 */

package ru.narod.jcommander.editor;

import java.awt.event.ActionEvent; 

import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.prefs.Preferences;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import javax.swing.text.Document;

import ru.narod.jcommander.fileSystem.BaseFile;
import ru.narod.jcommander.gui.dialog.WarningDialog;
import ru.narod.jcommander.util.LanguageBundle;

/**
 * Класс описывает окно просмотра и редактирования содержимого файлов
 * @author Pavel Ovcharov
 */
public class Editor extends javax.swing.JFrame implements EditorPrefs, ru.narod.jcommander.prefs.PrefKeys {

	private LanguageBundle lb = LanguageBundle.getInstance();
    
    /**
     * Создать новый объект класса Editor
     * @param parent объект класса javax.swing.JFrame, окно родительское по отношение к данному
     */
    public Editor(JFrame parent) {
        initComponents();
        loadPrefs();
        this.parent = parent;
        String charsets[] = {
            "windows-1251",
            "UTF-16",
            "KOI8-R",
            "IBM866"
        };
        JMenuItem menuItem[] = new JMenuItem[charsets.length];
        for (int i = 0; i < menuItem.length; i++) {
            menuItem[i] = new JMenuItem(charsets[i]);
            jMenuCharset.add(menuItem[i]);
            menuItem[i].setAccelerator(KeyStroke.getKeyStroke("control "+(i+1)));
            menuItem[i].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    charset = Charset.forName(((JMenuItem)e.getSource()).getText());
                    jTextArea1.setText("");
                    new FileLoader(file, jTextArea1.getDocument()).start();
                    jLabel1.setText(charset.name());
                }
            });
            
        }
        jLabel1.setText(charset.name());
    }
    
    /**
     * Открыть файл в окне просмотра
     * @param file открываемый файл
     * @param isEditable <B>true</B>, если файл открывается для редактирования, иначе - <B>false</B>
     */
    public void openFile(BaseFile file, boolean isEditable) {
        jPanel2.setVisible(false);
        jProgressBar1.setMinimum(0);
        jProgressBar1.setMaximum((int) file.length());
        jProgressBar1.setValue(0);
        jTextArea1.setEditable(isEditable);
        jMenuItemNew.setEnabled(false);
        jMenuItemOpen.setEnabled(false);
        jMenuItemSave.setEnabled(false);

		changeLanguage();

        setTitle("jed - "+file);
        setVisible(true);
        this.file = file;
        new FileLoader(file, jTextArea1.getDocument()).start();
        
    }
    
    public void loadPrefs() {
        setState(prefs.getInt(P_STATE, p_STATE));
        setLocation(prefs.getInt(P_POS_X, p_POS_X), prefs.getInt(P_POS_Y, p_POS_Y));
        setSize(prefs.getInt(P_WIDTH, p_WIDTH), prefs.getInt(P_HEIGHT, p_HEIGHT));
        charset = Charset.forName(prefs.get(P_CHARSET, Charset.defaultCharset().name()));
    }
    
    public void savePrefs() {
        prefs.putInt(P_POS_X, getLocation().x);
        prefs.putInt(P_POS_Y, getLocation().y);    
        prefs.putInt(P_WIDTH, getWidth());
        prefs.putInt(P_HEIGHT, getHeight());
        prefs.putInt(P_STATE, getExtendedState());
        prefs.put(P_CHARSET, charset.name());
        //prefs.putInt("SCALE", 0);
    
        try {
            prefs.exportNode(new FileOutputStream(new java.io.File("viewer.xml")));
        }
		catch (Exception e) {
			e.printStackTrace();
		}
//		catch (FileNotFoundException ex) {
//            ex.printStackTrace();
//        } catch (BackingStoreException ex) {
//            ex.printStackTrace();
//        } catch (IOException ex) {
//            ex.printStackTrace();
//        }
    }
    
    public void onExit() {
        savePrefs();
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jPanel2 = new javax.swing.JPanel();
        jProgressBar1 = new javax.swing.JProgressBar();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenuFile = new javax.swing.JMenu();
        jMenuItemNew = new javax.swing.JMenuItem();
        jMenuItemOpen = new javax.swing.JMenuItem();
        jMenuItemSave = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JSeparator();
        jMenuItemExit = new javax.swing.JMenuItem();
        jMenuCharset = new javax.swing.JMenu();

        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
            }
        });

        jPanel1.setLayout(new java.awt.BorderLayout());

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jTextArea1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextArea1KeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(jTextArea1);

        jPanel1.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jPanel2.setLayout(new javax.swing.BoxLayout(jPanel2, javax.swing.BoxLayout.LINE_AXIS));

        jProgressBar1.setStringPainted(true);
        jPanel2.add(jProgressBar1);

        jPanel1.add(jPanel2, java.awt.BorderLayout.SOUTH);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        jPanel3.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 5, 0));

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 10));
        jLabel1.setText("jLabel1"); // NOI18N
        jPanel3.add(jLabel1);

        getContentPane().add(jPanel3, java.awt.BorderLayout.SOUTH);

        jMenuFile.setText("File");

        jMenuItemNew.setText("New");
        jMenuFile.add(jMenuItemNew);

        jMenuItemOpen.setText("Open");
        jMenuItemOpen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemOpenActionPerformed(evt);
            }
        });
        jMenuFile.add(jMenuItemOpen);

        jMenuItemSave.setText("Save");
        jMenuItemSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemSaveActionPerformed(evt);
            }
        });
        jMenuFile.add(jMenuItemSave);
        jMenuFile.add(jSeparator1);

        jMenuItemExit.setText("Exit");
        jMenuItemExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemExitActionPerformed(evt);
            }
        });
        jMenuFile.add(jMenuItemExit);

        jMenuBar1.add(jMenuFile);

        jMenuCharset.setText("Charsets");
        jMenuBar1.add(jMenuCharset);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        onExit();
    }//GEN-LAST:event_formWindowClosing

    private void jMenuItemSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemSaveActionPerformed
        
        //Writer out;
        try {
            OutputStream out = file.getOutputStream();//new FileWriter(file);
            java.io.OutputStreamWriter osr = new java.io.OutputStreamWriter(out, charset);
            osr.write(jTextArea1.getText());
            //out.write(jTextArea1.getText().getBytes());
            osr.close();
            return;
        }
		catch (Exception e) {
			e.printStackTrace();
		}
//        catch (IOException ex) {
//            //System.out.println(ex);
//            ex.printStackTrace();
//        }
//        catch (SecurityException ex) {
//            //System.out.println(ex);
//            ex.printStackTrace();
//        }
//        catch (Exception ex) {
//            //System.out.println(ex);
//            ex.printStackTrace();
//        }
        finally {
            
        }
        String [] options = {lb.getString("StrOk")};
        WarningDialog.showMessage(lb.getString("StrErrorSave"), lb.getString("StrJC"), options,
                WarningDialog.MESSAGE_ERROR, 0);
    }//GEN-LAST:event_jMenuItemSaveActionPerformed

    private void jTextArea1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextArea1KeyPressed
        if (KeyEvent.VK_ESCAPE == evt.getKeyCode()) {
            jMenuItemExit.doClick();
        }
        if (KeyEvent.VK_S == evt.getKeyCode()) {
            if (evt.isControlDown()) {
                jMenuItemSave.doClick();
            }
        }
    }//GEN-LAST:event_jTextArea1KeyPressed

    private void jMenuItemExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemExitActionPerformed
        onExit();
        setVisible(false);
    }//GEN-LAST:event_jMenuItemExitActionPerformed

    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed
        if (KeyEvent.VK_ESCAPE == evt.getKeyCode())
            onExit();
    }//GEN-LAST:event_formKeyPressed

    private void jMenuItemOpenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemOpenActionPerformed
        // TODO: open file
    }//GEN-LAST:event_jMenuItemOpenActionPerformed
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenu jMenuCharset;
    private javax.swing.JMenu jMenuFile;
    private javax.swing.JMenuItem jMenuItemExit;
    private javax.swing.JMenuItem jMenuItemNew;
    private javax.swing.JMenuItem jMenuItemOpen;
    private javax.swing.JMenuItem jMenuItemSave;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextArea jTextArea1;
    // End of variables declaration//GEN-END:variables
    
    private JFrame parent;
    private BaseFile file;
    private Preferences prefs = Preferences.userNodeForPackage(this.getClass());
    private Charset charset;// = Charset.forName("windows-1251");
    
    class FileLoader extends Thread {

        FileLoader(BaseFile f, Document doc) {
            setPriority(4);
            this.f = f;
            this.doc = doc;
        }
        
		@Override
        public void run() {
            jPanel2.setVisible(true);

            try {
                // try to start reading
                InputStream in = f.getInputStream();//new FileReader(f);            
                InputStreamReader isr = new InputStreamReader(in, charset);
                char[] buff = new char[4096];
                int nch;
                //while ((nch = in.read(buff, 0, buff.length)) != -1) {
                while ((nch = isr.read(buff, 0, buff.length)) != -1) {
                    doc.insertString(doc.getLength(), new String(buff, 0, nch), null);
                    jProgressBar1.setValue(jProgressBar1.getValue() + nch);
                }
                isr.close();
                //in.close();
                return;
            }
			catch (Exception e) {
				e.printStackTrace();
			}
//            catch (IOException e) {
//                e.printStackTrace();
//            }
//            catch (BadLocationException e) {
//                e.printStackTrace();
//            }
//            catch (Exception e) {
//                e.printStackTrace();
//            }
            finally {
                if (jTextArea1.isEditable())
                    jMenuItemSave.setEnabled(true);
                jPanel2.setVisible(false);
            }
            String [] options = {lb.getString("StrOk")};
            WarningDialog.showMessage(lb.getString("StrErrorOpen"), lb.getString("StrJC"), options,
                    WarningDialog.MESSAGE_ERROR, 0);
            
        }
        
        Document doc;
        BaseFile f;
    }

	private void changeLanguage() {
		jMenuFile.setText(lb.getString("File"));
		jMenuItemNew.setText(lb.getString("StrNew"));
		jMenuItemOpen.setText(lb.getString("StrOpen"));
		jMenuItemSave.setText(lb.getString("StrSave"));
		jMenuCharset.setText(lb.getString("StrCharsets"));
		jMenuItemExit.setText(lb.getString("Exit"));
	}
}
