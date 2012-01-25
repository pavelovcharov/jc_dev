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
 * ImageViewer.java
 *
 * Created on 25 nov 2006, 17:42
 */
package ru.narod.jcommander.editor;

import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.event.KeyEvent;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.prefs.Preferences;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

import ru.narod.jcommander.fileSystem.BaseFile;
import ru.narod.jcommander.fileSystem.FileHelper;
import ru.narod.jcommander.gui.ImageArchive;
import ru.narod.jcommander.util.LanguageBundle;

/**
 * Класс описывает окно просмотра и редактирования содержимого файлов
 * @author Pavel Ovcharov
 */
public class ImageViewer extends javax.swing.JFrame implements EditorPrefs, ru.narod.jcommander.prefs.PrefKeys {

	protected enum Scale {

		SCALE_REAL, SCALE_FIT
	};

	/**
	 * Создать новый объект класса Editor
	 * @param parent объект класса javax.swing.JFrame, окно родительское по отношение к данному
	 */
	public ImageViewer(JFrame parent) {
		initComponents();
		loadPrefs();
//		setupLanguage();
		this.parent = parent;

		jButtonBack.setIcon(ImageArchive.getArrowBack());
		jButtonForward.setIcon(ImageArchive.getArrowForward());
	}

	private void setupLanguage() {
		LanguageBundle lb = LanguageBundle.getInstance();
		jMenuFile.setText(lb.getString("iv_file"));
		jMenuItemExit.setText(lb.getString("iv_exit"));
		jMenuScale.setText(lb.getString("iv_scale"));

        jRadioButtonMenuItemFit.setText(lb.getString("iv_scaleFit"));
        jRadioButtonMenuItemReal.setText(lb.getString("iv_scaleReal"));

//		jButtonBack.setText(lb.getString("iv_prevfile"));
//		jButtonForward.setText(lb.getString("iv_nextfile"));
		jButtonBack.setToolTipText(lb.getString("iv_prevfile"));
		jButtonForward.setToolTipText(lb.getString("iv_nextfile"));
	}

	public void openFile(BaseFile file) {
		this.file = file;
		
		setupLanguage();

        if (scale == Scale.SCALE_FIT) {
            jRadioButtonMenuItemReal.setSelected(false);
            jRadioButtonMenuItemFit.setSelected(true);
        }
        if (scale == Scale.SCALE_REAL) {
            jRadioButtonMenuItemReal.setSelected(true);
            jRadioButtonMenuItemFit.setSelected(false);
        }

		setTitle("image viewer - " + file);
		setVisible(true);

		scale();
	}

	public void loadPrefs() {
		setState(prefs.getInt(P_STATE, p_STATE));
		setLocation(prefs.getInt(P_POS_X, p_POS_X), prefs.getInt(P_POS_Y, p_POS_Y));
		setSize(prefs.getInt(P_WIDTH, p_WIDTH), prefs.getInt(P_HEIGHT, p_HEIGHT));
		scale = Scale.values()[prefs.getInt(P_SCALE, p_SCALE)];
	}

	public void savePrefs() {
		prefs.putInt(P_POS_X, getLocation().x);
		prefs.putInt(P_POS_Y, getLocation().y);
		prefs.putInt(P_WIDTH, getWidth());
		prefs.putInt(P_HEIGHT, getHeight());
		prefs.putInt(P_STATE, getExtendedState());
		prefs.putInt(P_SCALE, scale.ordinal());
	}

	public void onExit() {
		savePrefs();
	}

	private void waitForImage(Image image) {
		MediaTracker tracker = new MediaTracker(this);
		tracker.addImage(image, 0);
		try {
			tracker.waitForID(0);
		} catch (InterruptedException e) {
		}
		tracker.removeImage(image);
	}

	private void scale() {
//		System.out.println("scale");
		try {
//			this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
			Image image;
			if (file.getFilename().toLowerCase().endsWith("bmp")) {
				image =	ImageIO.read(file.getInputStream());
			}
			else {
				int read;
				byte buffer[] = new byte[1024];
				ByteArrayOutputStream bout = new ByteArrayOutputStream();
				InputStream in = file.getInputStream();
				while ((read = in.read(buffer, 0, buffer.length)) != -1) {
					bout.write(buffer, 0, read);
				}

				byte imageBytes[] = bout.toByteArray();
				bout.close();
				in.close();

				image = getToolkit().createImage(imageBytes);

				waitForImage(image);
			}
			int width = image.getWidth(null);
			int height = image.getHeight(null);

//			this.setCursor(Cursor.getDefaultCursor());


			Image scaledImage = null;
			switch (scale) {
				case SCALE_FIT: {

					int cWidth = jScrollPane1.getWidth() - 10;
					int cHeight = jScrollPane1.getHeight() - 10;

					double coef1 = (double) width / cWidth;
					double coef2 = (double) height / cHeight;

					double coef = Math.max(coef1, coef2);

					int newWidth = (int) (width / coef);
					int newHeight = (int) (height / coef);
					scaledImage = image.getScaledInstance(newWidth, newHeight, Image.SCALE_FAST);
					break;
				}
				case SCALE_REAL: {
					scaledImage = image;
					break;
				}
				default:
					break;
			}

			if (null == scaledImage) {
				throw new NullPointerException();
			}
			jLabel1.setIcon(new ImageIcon(scaledImage));
		} catch (Exception e) {
			// XXX querry error
		}
	}

	private void nextFile() {
		BaseFile[] files = file.getAbsoluteParent().getFiles();
		int index = 0;
		for (int i = 0; i < files.length; i++) {
			BaseFile absoluteFile = files[i];
			if (absoluteFile.equals(file)) {
				index = i + 1;
			}
		}
		while (index < files.length) {
			if (FileHelper.getFileType(files[index]) == FileHelper.FileType.IMAGE) {
				file = files[index];
				openFile(files[index]);
				break;
			}
			index++;
		}
	}

	private void prevFile() {
		BaseFile[] files = file.getAbsoluteParent().getFiles();
		int index = 0;
		for (int i = 0; i < files.length; i++) {
			BaseFile absoluteFile = files[i];
			if (absoluteFile.equals(file)) {
				index = i - 1;
			}
		}
		while (index >= 0) {
			if (FileHelper.getFileType(files[index]) == FileHelper.FileType.IMAGE) {
				file = files[index];
				openFile(files[index]);
				break;
			}
			index--;
		}
	}

	/** This method is called from within the constructor to
	 * initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is
	 * always regenerated by the Form Editor.
	 */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jLabel1 = new javax.swing.JLabel();
        jToolBar1 = new javax.swing.JToolBar();
        jButtonBack = new javax.swing.JButton();
        jButtonForward = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenuFile = new javax.swing.JMenu();
        jMenuItemExit = new javax.swing.JMenuItem();
        jMenuScale = new javax.swing.JMenu();
        jRadioButtonMenuItemReal = new javax.swing.JRadioButtonMenuItem();
        jRadioButtonMenuItemFit = new javax.swing.JRadioButtonMenuItem();

        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                formComponentResized(evt);
            }
        });
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
            }
        });

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jScrollPane1.setViewportView(jLabel1);

        getContentPane().add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jToolBar1.setRollover(true);

        jButtonBack.setBorderPainted(false);
        jButtonBack.setFocusable(false);
        jButtonBack.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButtonBack.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButtonBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonBackActionPerformed(evt);
            }
        });
        jToolBar1.add(jButtonBack);

        jButtonForward.setBorderPainted(false);
        jButtonForward.setFocusable(false);
        jButtonForward.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButtonForward.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButtonForward.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonForwardActionPerformed(evt);
            }
        });
        jToolBar1.add(jButtonForward);

        getContentPane().add(jToolBar1, java.awt.BorderLayout.PAGE_START);

        jMenuFile.setText("File");

        jMenuItemExit.setText("Exit");
        jMenuItemExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemExitActionPerformed(evt);
            }
        });
        jMenuFile.add(jMenuItemExit);

        jMenuBar1.add(jMenuFile);

        jMenuScale.setText("Scale");

        jRadioButtonMenuItemReal.setSelected(true);
        jRadioButtonMenuItemReal.setText("real");
        jRadioButtonMenuItemReal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonMenuItemRealActionPerformed(evt);
            }
        });
        jMenuScale.add(jRadioButtonMenuItemReal);

        jRadioButtonMenuItemFit.setSelected(true);
        jRadioButtonMenuItemFit.setText("fit");
        jRadioButtonMenuItemFit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonMenuItemFitActionPerformed(evt);
            }
        });
        jMenuScale.add(jRadioButtonMenuItemFit);

        jMenuBar1.add(jMenuScale);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
		onExit();
    }//GEN-LAST:event_formWindowClosing

    private void jMenuItemExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemExitActionPerformed
		onExit();
		setVisible(false);
    }//GEN-LAST:event_jMenuItemExitActionPerformed

    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed
		switch (evt.getKeyCode()) {
			case KeyEvent.VK_ESCAPE:
				jMenuItemExit.doClick();
				break;
			case KeyEvent.VK_RIGHT:
				nextFile();
				break;
			case KeyEvent.VK_LEFT:
				prevFile();
				break;
		}
    }//GEN-LAST:event_formKeyPressed

private void formComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentResized
//	System.out.println("resize");
	scale();
}//GEN-LAST:event_formComponentResized

private void jButtonBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonBackActionPerformed
	prevFile();
}//GEN-LAST:event_jButtonBackActionPerformed

private void jButtonForwardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonForwardActionPerformed
	nextFile();
}//GEN-LAST:event_jButtonForwardActionPerformed

private void jRadioButtonMenuItemRealActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonMenuItemRealActionPerformed
    jRadioButtonMenuItemFit.setSelected(false);
    scale = Scale.SCALE_REAL;
	scale();
}//GEN-LAST:event_jRadioButtonMenuItemRealActionPerformed

private void jRadioButtonMenuItemFitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonMenuItemFitActionPerformed
	jRadioButtonMenuItemReal.setSelected(false);
    scale = Scale.SCALE_FIT;
	scale();
}//GEN-LAST:event_jRadioButtonMenuItemFitActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonBack;
    private javax.swing.JButton jButtonForward;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenu jMenuFile;
    private javax.swing.JMenuItem jMenuItemExit;
    private javax.swing.JMenu jMenuScale;
    private javax.swing.JRadioButtonMenuItem jRadioButtonMenuItemFit;
    private javax.swing.JRadioButtonMenuItem jRadioButtonMenuItemReal;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToolBar jToolBar1;
    // End of variables declaration//GEN-END:variables
	private JFrame parent;
	private BaseFile file = null;
	private Scale scale;
	private Preferences prefs = Preferences.userNodeForPackage(this.getClass());
}
