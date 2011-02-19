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
 * FileTableSorter.java
 *
 * Created on 26 Июнь 2007 г., 11:25
 *
 */
package omegaCommander.gui.table;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Vector;
import javax.swing.ImageIcon;
import javax.swing.table.AbstractTableModel;

import omegaCommander.fileSystem.BaseFile;
import omegaCommander.fileSystem.FileSystemList;
import omegaCommander.gui.ImageArchive;
import omegaCommander.gui.table.tableElements.Attribute;
import omegaCommander.gui.table.tableElements.Date;
import omegaCommander.gui.table.tableElements.Directory;
import omegaCommander.gui.table.tableElements.Element;
import omegaCommander.gui.table.tableElements.Extention;
import omegaCommander.gui.table.tableElements.Filename;
import omegaCommander.gui.table.tableElements.Size;
import omegaCommander.gui.table.tableElements.UpperDirectory;
import omegaCommander.gui.table.tableHeader.ColumnNumbers;
import omegaCommander.gui.table.tableHeader.TableHeader;
import omegaCommander.prefs.JCPreferenses;

/**
 *
 * @author Pavel Ovcharov
 * @version
 */
public class FileTableSorter extends AbstractTableModel implements ColumnNumbers {

    private static ImageIcon imageFolder = ImageArchive.getImageFolder();
    private static ImageIcon imageFile = ImageArchive.getImageFile();
    private static ImageIcon imageUp = ImageArchive.getImageUp();
    private static ImageIcon imageArchive = ImageArchive.getImageArchive();
    private FileSystemList fsl;
    public static final int DESCENDING = -1;
    public static final int NOSORT = 0;
    public static final int ASCENDING = 1;
    public static Directive NULL_DIRECTIVE = new Directive(-1, NOSORT);
    //private Vector rowsVector;
    //private Row[] rows;
    private Vector rows;
    private Row[] fileRows;
    private Row[] dirRows;
    private Row parentRow = null;
    public ArrayList sortingColumns;
    private boolean useSystemIcons;

    /**
     * Создать новый экземпляр класса FileTableModel на основе <I>fsl</I>
     * @param fsl список файлов - объект класса FileSystemList
     */
    public FileTableSorter(FileSystemList fsl, ArrayList sortingColumns) {
        this.fsl = fsl;
        this.sortingColumns = sortingColumns;
        //this.header = header;
        rows = new Vector();

        if (fsl.hasParent()) {
            parentRow = new Row();
            parentRow.createParentRow(fsl.getParent());
            //rows.add(r);
        }

        BaseFile[] files = fsl.getFiles();
        BaseFile[] folders = fsl.getFolders();

        if (null != folders) {
            Arrays.sort(folders);
            dirRows = new Row[folders.length];
            for (int i = 0; i < folders.length; i++) {
                Row r = new Row(folders[i]/*, false*/);
                dirRows[i] = r;
            }
        }

        if (null != files) {
            fileRows = new Row[files.length];
            for (int i = 0; i < files.length; i++) {
                Row r = new Row(files[i]/*, false*/);
                fileRows[i] = r;
            }
            sortData();
        }
    }

    public void sortData() {
        rows.clear();
        if (null != parentRow) {
            rows.add(parentRow);
        }
        if (null != dirRows) {
            rows.addAll(Arrays.asList(dirRows));
        }
        if (null != fileRows) {
            Arrays.sort(fileRows);
            rows.addAll(Arrays.asList(fileRows));
        }
    }

    /**
     * Получить количество строк
     * @return количество строк в модели (количество файлов в списке)
     */
    public int getRowCount() {
        return fsl.hasParent() ? fsl.getTotalCount() + 1 : fsl.getTotalCount();
    }

    /**
     * Получить число столбцов в таблице
     * @return число колонок в таблице
     */
    public int getColumnCount() {
        return TableHeader.TITLE.length;
    }

    /**
     * Получить имя столбца
     * @param column номер столбца
     * @return имя столбца
     */
    @Override
    public String getColumnName(int column) {
        return TableHeader.getColumnName(column);
    }

    /**
     * Проверить доступна ли ячейка таблицы для редактирования
     * @param row номер строки
     * @param column номер столбца
     * @return <b>true</b>, если заданная ячейка доступна для редактирования,
     * <b>false</b> - иначе 
     */
    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }

    /**
     * Получить класс объекта в заданном столбце
     * @param c номер столбца
     * @return класс объекта - объект класса <B>Class</B>
     */
    @Override
    public Class getColumnClass(int c) {
//        return (ICON == c) ? getValueAt(0, c).getClass() :
//            omegaCommander.gui.table.tableElements.Element.class;
        return (ICON == c) ? ImageIcon.class
                : omegaCommander.gui.table.tableElements.Element.class;
    }

    /**
     * Получить значение в заданной ячейке таблицы
     * @param aRow номер строки
     * @param aColumn номер колонки
     * @return объект в заданной ячейке
     */
    public Object getValueAt(int aRow, int aColumn) {
        //Vector row = (Vector)rows.elementAt(aRow);
        //Row r = (Row)rows.elementAt(aRow);
        if (0 == getRowCount()) {
            return null;
        }
        Row r = (Row) rows.get(aRow);
        return r.data.elementAt(aColumn);
    }

    /**
     * Установить значение в заданной ячейке
     * @param value новое значение
     * @param row номер строки
     * @param column номер колонки
     */
    @Override
    public void setValueAt(Object value, int row, int column) {
        //Row r = (Row)rows.elementAt(row);
        Row r = (Row) rows.get(row);
        //Vector dataRow = (Vector)rows.elementAt(row);
        //dataRow.setElementAt(value, column);
        r.data.setElementAt(value, column);

    }

    public Vector getValuesAt(int row) {
        return ((Row) (rows.get(row))).data;
        //return (Vector)rows.elementAt(row);
        //return rows[row];
    }

    public Directive getDirective(int column) {
        for (int i = 0; i < sortingColumns.size(); i++) {
            Directive directive = (Directive) sortingColumns.get(i);
            if (directive.getColumn() == column) {
                return directive;
            }
        }
        return NULL_DIRECTIVE;
    }

    class Row implements Comparable {
        //private Name nameObject;

        private Vector data = new Vector(TableHeader.TITLE.length);

        public Row() {
        }

        public void createParentRow(BaseFile file) {
            //Row newRow = new Row();
            data.add(ICON, imageUp);
            data.add(NAME, new UpperDirectory(file));
            data.add(EXT, new Extention());
            data.add(SIZE, new Size(file));
            data.add(DATE, new Date(file));
            data.add(ATR, new Attribute());
        }

        public Row(BaseFile file/*, boolean parent*/) {
            createRow(file);
        }
        JCPreferenses jcPrefs = JCPreferenses.getJCPreferenses();

        public void createRow(BaseFile file) {
            if (file.isDirectory()) {
                data.add(ICON, imageFolder);
                data.add(NAME, new Directory(file));

            } else {
                data.add(ICON, ImageArchive.getImageFile(file, jcPrefs.useSystemIcons));
                data.add(NAME, new Filename(file));
            }
            data.add(EXT, new Extention(file.getExtention()));
            data.add(SIZE, new Size(file));
            data.add(DATE, new Date(file));
            data.add(ATR, new Attribute(file.getAtributeString()));
        }

        @Override
        public String toString() {
            String str = "";
            for (Iterator it = data.iterator(); it.hasNext();) {
                str += it.next() + " ";
            }
            str += "\n";
            return str;
        }

        public int compareTo(Object o) {
            /*
            int row1 = modelIndex;
            int row2 = ((Row) o).modelIndex;
             */

            for (Iterator it = sortingColumns.iterator(); it.hasNext();) {
                Directive directive = (Directive) it.next();
                int column = directive.getColumn();
                Element e1 = (Element) data.get(column);
                Element e2 = (Element) ((Row) o).data.get(column);

                int comparison = 0;
                // Define null less than everything, except null.
                if (e1 == null && e2 == null) {
                    comparison = 0;
                } else if (e1 == null) {
                    comparison = -1;
                } else if (e2 == null) {
                    comparison = 1;
                } else {
                    comparison = e1.compareTo(e2);
                }
                if (comparison > 0) {
                    comparison = 1;
                }
                if (comparison < 0) {
                    comparison = -1;
                }

                if (comparison != 0) {
                    return directive.getDirection() == DESCENDING ? -comparison : comparison;
                }

            }

            return 0;
        }

        public Element getValueAt(int column) {
            return (Element) data.get(column);
        }
    }
}
