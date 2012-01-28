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
package ru.narod.jcommander.gui.table;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import ru.narod.jcommander.fileSystem.BaseFile;
import ru.narod.jcommander.fileSystem.FileSystemList;
import ru.narod.jcommander.fileSystem.utils.FileMonitor;
import ru.narod.jcommander.gui.table.tableElements.*;
import ru.narod.jcommander.gui.table.tableHeader.ColumnNumbers;
import ru.narod.jcommander.gui.table.tableHeader.TableHeader;

/**
 * Класс наследован от JTable и описывают таблицу для отображения списка файлов
 * и каталогов. Для отображения файловой системы с таблицей связан объект класса
 * FileSystemList.
 *
 * @author Pavel Ovcharov
 * @version 2005/04/25 11:03:08 PM
 * @see FileSystemList
 */
public class FileTable extends JTable implements ColumnNumbers {

    public static final int QUICK_SEARCH_FROM_BEGINING = 0;
    public static final int QUICK_SEARCH_WHOLE_WORD = 1;
    private static final Color BK_COLOR = Color.getColor("238, 238, 238");
    private boolean active;
    private FileSystemList fsl;
    private int currentPosition;
    private ArrayList selectedFilesList;
    private BaseFile currentDir;
    private FileMonitor currentFileMonitor;
    private FileTableSorter model;
    private final String[] TITLE = TableHeader.TITLE;
    public int[] headerSizes = new int[TITLE.length];
    private ArrayList sortingColumns = new ArrayList();
    private boolean showToolTip;
    private int quickSearchMode;

    /**
     * Создает новую таблицу. Текущим каталогом устанавливается каталог <i>f</i>
     *
     * @param file устанавливает текущий каталог файловой системы
     */
    public FileTable(BaseFile file) {
        super();
        fsl = new FileSystemList();
        setCurrentDir(file);
        setShowGrid(false);

        setFont(new Font("SansSerif", Font.BOLD, 12));
        active = false;
        currentPosition = 0;
        selectedFilesList = new ArrayList();
        setSelectionMode(0);
        setDefaultKeys();

        setDefaultRenderer(Element.class, new ColorRenderer());
        setSelectionBackground(active ? Color.GRAY : Color.LIGHT_GRAY);
        setSelectionForeground(Color.WHITE);

        setAutoResizeMode(AUTO_RESIZE_NEXT_COLUMN);

        setDragEnabled(true);

        getTableHeader().addMouseListener(new TableHeaderListener(this));
        getTableHeader().setDefaultRenderer(new SortableHeaderRenderer(getTableHeader().getDefaultRenderer()));

        getTableHeader().setPreferredSize(new Dimension(0, 15));
    }

    @Override
    public void updateUI() {
        super.updateUI();
        setBackground(BK_COLOR);
        setDefaultKeys();
    }

    public void addMouseInputAdapter(MouseInputAdapter mouseInputAdapter) {
        addMouseMotionListener(mouseInputAdapter);
        addMouseListener(mouseInputAdapter);
    }

    public void refreshTableIfNeeded() {
        if (currentFileMonitor.refresh(currentDir)) {
            refreshTable();
        }
    }

    /**
     * Обновляет содержание таблицы
     */
    public void refreshTable() {
        fsl.setFileList(currentDir);
        model = new FileTableSorter(fsl, sortingColumns);
        setModel(model);

        getTableHeader().setReorderingAllowed(false);
        TableColumn column;

        for (int i = 0; i < TITLE.length; i++) {
            column = getColumn(i);
            column.setPreferredWidth(headerSizes[i]);
        }
        column = getColumn(ICON);
        column.setResizable(false);
        column.setMaxWidth(20);
        column.setPreferredWidth(20);

        clearSelectedList();

        setCurrentPosition(currentPosition);
    }

    public void setDefaultKeys() {
        InputMap im = getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        im.getParent().clear();
    }

    /**
     * Возвращает файловую систему для текущего каталога
     *
     * @return объект класса FileSystemList - файловую систему для текущего
     * каталога
     */
    public FileSystemList getFileList() {
        return fsl;
    }

    /**
     * Устанавливает состояние данной таблицы
     *
     * @param active <b>true</b> делает таблицу активной (т.е. с которой ведется
     * работа), <b>false</b> - делает таблицу неактивной
     *
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * Возвращает состояние таблицы
     *
     * @return <b>true</b> если таблица активна, иначе - <b>false</b>
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Определяет текущую строку таблицы
     *
     * @return номер выделенной строки
     */
    public int getCurrentPosition() {
        return currentPosition;
    }

    /**
     * Устанавливает текущую строку таблицы
     *
     * @param currentPosition новый номер текущей строки
     */
    public void setCurrentPosition(int currentPosition) {
        if (currentPosition > getRowCount() - 1) {
            this.currentPosition = getRowCount() - 1;
        } else if (currentPosition < 0) {
            this.currentPosition = 0;
        } else {
            this.currentPosition = currentPosition;
        }
        setRowSelectionInterval(this.currentPosition, this.currentPosition);
    }

    /**
     * Определить позицию объекта в таблице (папки, файла, родительской папки)
     *
     * @param anotherObject объект в таблице
     * @return позиция файла в таблице: -1, если файла в таблице нет
     */
    public int getElementPosition(Object anotherObject) {
        if ((anotherObject instanceof UpperDirectory) == true) {
            return 0;
        }
        Object obj;
        for (int i = 0; i < getRowCount(); i++) {
            obj = getValueAt(i, FileTable.NAME);
            if (false == (obj instanceof UpperDirectory)) {
                if (true == ((NameInterface) obj).equals((NameInterface) anotherObject)) {
                    return i;
                }
            }
        }
        return -1;
    }

    /**
     * Определяет позицию файла в таблице
     *
     * @param aFile файла в таблице
     * @return позиция файла в таблице: -1, если файла в таблице нет
     * @see BaseFile
     */
    public int getFilePosition(BaseFile aFile) {
        NameInterface ni;
        if (aFile.isDirectory()) {
            ni = new Directory(aFile);
        } else {
            ni = new Filename(aFile);
        }
        int pos = getElementPosition(ni);
        if (-1 == pos) {
            pos = currentPosition;
        }
        return pos;
    }

    public NameInterface getElementAt(int pos) {
        if (pos < 0 || pos >= getRowCount()) {
            return null;
        } else {
            return (NameInterface) getValueAt(pos, NAME);
        }
    }

    public BaseFile getFileAt(int pos) {
        return getElementAt(pos).getFile();
    }

    /**
     * Выделить в таблице строки с <I>index1</I> по <I>index2</I>
     *
     * @param index0 индекс начала выделения
     * @param index1 индекс конца выделения
     * @throws java.lang.IllegalArgumentException исключение вызывается, если
     * <I>index1>index2</I>
     */
    @Override
    public void setRowSelectionInterval(int index0, int index1) throws IllegalArgumentException {
        if (0 == getRowCount()) {
            return;
        }

        if (index0 > index1) {
            throw new IllegalArgumentException("index0 > index1");
        }
        if (index0 > (getRowCount() - 1)) {
            index0 = getRowCount() - 1;
        }
        if (index1 > (getRowCount() - 1)) {
            index1 = getRowCount() - 1;
        }
        if (index0 < 0) {
            index0 = 0;
        }
        if (index1 < 0) {
            index1 = 0;
        }

        changeSelection(currentPosition, 0, true, true);
        super.setRowSelectionInterval(index0, index1);

    }

    public void jump(boolean up) {
        int rows = (int) (getVisibleRect().getHeight() / getRowHeight());
        int pos;
        if (up) {
            pos = getCurrentPosition() + 1 - 2 * rows;
        } else {
            pos = getCurrentPosition() - 1 + 2 * rows;
        }
        setCurrentPosition(pos);
    }

    /**
     * Определить позицию файла, название которого начинается со строки
     * <I>str</I>
     *
     * @param str начало названия файла
     * @return позиция найденного файла; -1, если файл не найден
     */
    public int getElementPositionByString(String str) {
        Object obj;
        for (int i = 0; i < getRowCount(); i++) {
            obj = getValueAt(i, FileTable.NAME);
            if (false == (obj instanceof UpperDirectory)) {
                boolean fResult = quickSearchMode == QUICK_SEARCH_FROM_BEGINING
                        ? ((NameInterface) obj).getFileName().startsWith(str)
                        : ((NameInterface) obj).getFileName().contains(str);
                if (fResult) {
                    return i;
                }
            }
        }
        return -1;
    }

    /**
     * Добавить файл в список выделенных
     *
     * @param newFile новый файл
     * @see BaseFile
     */
    public void addFileToSelectedList(BaseFile newFile) {
        if (selectedFilesList.contains(newFile)) {
            selectedFilesList.remove(newFile);
        } else {
            selectedFilesList.add(newFile);
        }
    }

    /**
     * Определяет, есть ли в таблице выделенные файлы
     *
     * @return <B>true</B>если список выделенных файлов не пуст, <B>false</B> -
     * иначе
     */
    public boolean hasSelectedFiles() {
        return selectedFilesList.isEmpty();
    }

    /**
     * Получить список выделенных файлов
     *
     * @return список выделенных файлов
     */
    public ArrayList getSelectedList() {
        return selectedFilesList;
    }

    /**
     * Очистить список выделенных файлов
     */
    public void clearSelectedList() {
        selectedFilesList.clear();
    }

    /**
     * Получить текущий каталог, отображаемый в таблице
     *
     * @return текущий каталог таблицы
     * @see BaseFile
     */
    public BaseFile getCurrentDir() {
        return currentDir;
    }

    public void setCurrentDir(BaseFile currentDir) {
        this.currentDir = currentDir;
        fsl.setFileList(currentDir);
        currentFileMonitor = new FileMonitor(currentDir);
    }

    public int[] getHeaderSizes() {
        return headerSizes;
    }

    public void setHeaderSizes(int[] sizes) {
        headerSizes = (int[]) sizes.clone();
    }

    public void selectFileAt(int row) {
        Vector v = ((FileTableSorter) getModel()).getValuesAt(row);
        NameInterface name = (NameInterface) v.get(NAME);
        if (name instanceof UpperDirectory) {
        } else {
            addFileToSelectedList(name.getFile());
            for (int i = 0; i < TITLE.length; i++) {
                if (ICON == i) {
                    continue;
                }
                Element e = (Element) v.get(i);
                e.setSelected(!e.isSelected());
            }
        }
    }

    public void selectAllFiles() {
        for (int i = 0; i < getRowCount(); i++) {
            selectFileAt(i);
        }
    }

    public void selectCurrentFile() {
        selectFileAt(currentPosition);
    }

    public BaseFile getFileAtCursor() {
        Object obj = getValueAt(currentPosition, NAME);
        if (obj instanceof NameInterface) {
            return ((NameInterface) obj).getFile();
        } else {
            return null;
        }
    }

    public NameInterface getElementAtCursor() {
        Object obj = getValueAt(currentPosition, NAME);
        if (obj instanceof NameInterface) {
            return (NameInterface) obj;
        } else {
            return null;
        }
    }

    public void showFileSize() {
        BaseFile af = getFileAtCursor();
        if (null != af) {
            long size = FileSystemList.getSize(af);
            Size newSize = new Size(size);
            newSize.setSelected(((Size) getValueAt(currentPosition, SIZE)).isSelected());
            setValueAt(newSize, currentPosition, SIZE);
        }
    }

    protected Icon getHeaderRendererIcon(int column, int size) {
        Directive d = model.getDirective(column);
        if (d.equals(FileTableSorter.NULL_DIRECTIVE)) {
            return null;
        }
        return new Arrow(d.getDirection() == FileTableSorter.DESCENDING, size, model.sortingColumns.indexOf(d));
    }

    public void setSortingColumns(Directive directive) {
        sortingColumns.clear();
        sortingColumns.add(directive);
    }

    public ArrayList getSortingColumns() {
        return sortingColumns;
    }

    public long getSelectedFilesSize() {
        Size size;
        long result = 0;
        for (int i = 0; i < getRowCount(); i++) {
            size = (Size) getValueAt(i, SIZE);
            if (size.isSelected()) {
                result += size.getSize();
            }
        }
        return result;
    }

    public void setHiddenFilesVisibility(boolean show) {
        if (show) {
            fsl.removeHiddenFilesFilter();
        } else {
            fsl.addHiddenFilesFilter();
        }
    }

    public void moveCursorUp() {
        int index = currentPosition - 1;
        if (0 > index) {
            index++;
            repaint();
        }
        setCurrentPosition(index);
        changeSelection(index, NAME, false, false);
    }

    public void moveCursorDown() {
        int index = currentPosition + 1;
        if (index == getRowCount()) {
            index--;
            repaint();
        }
        setCurrentPosition(index);
        changeSelection(index, NAME, false, false);
    }

    public void moveToFile(BaseFile file) {
        setCurrentPosition(getFilePosition(file));
    }

    public BaseFile[] getActiveFiles() {
        BaseFile[] files = null;
        if (hasSelectedFiles()) {
            files = new BaseFile[selectedFilesList.size()];
            for (int i = 0; i < files.length; i++) {
                files[i] = (BaseFile) selectedFilesList.get(i);
            }
        } else {
            if (!(getElementAtCursor() instanceof UpperDirectory)) {
                files = new BaseFile[]{getFileAtCursor()};
            }
        }
        return files;
    }

    public void showToolTipAtRow(int row) {
        if (showToolTip) {
            if (0 > row || getRowCount() <= row) {
                return;
            }
            NameInterface ni = getElementAt(row);
            if (null != ni) {
                int width = getGraphics().getFontMetrics().stringWidth(ni.getName()) + 10;
                if (width > getColumn(NAME).getWidth()) {
                    setToolTipText(ni.getFileName());
                } else {
                    setToolTipText(null);
                }
            }
        }
    }

    public TableColumn getColumn(int column) {
        return getColumnModel().getColumn(column);
    }

    public void showToolTip(boolean show) {
        showToolTip = show;
        setToolTipText("");
    }

    public void setQuickSearchMode(int quickSearchMode) {
        this.quickSearchMode = quickSearchMode;
    }

    class TableHeaderListener implements MouseListener {

        private FileTable table;

        public TableHeaderListener(FileTable table) {
            this.table = table;
        }

        @Override
        public void mouseClicked(MouseEvent e) {

            FileTableSorter model = (FileTableSorter) table.getModel();

            JTableHeader h = (JTableHeader) e.getSource();
            TableColumnModel columnModel = h.getColumnModel();
            int viewColumn = columnModel.getColumnIndexAtX(e.getX());
            int column = columnModel.getColumn(viewColumn).getModelIndex();
            if ((column != -1) && (column != ICON)) {
                int status;
                Directive d = model.getDirective(column);
                if (d.equals(FileTableSorter.NULL_DIRECTIVE)) {
                    status = 0;
                } else {
                    model.sortingColumns.remove(d);
                    status = d.getDirection() - 1;
                    if (status < -1) {
                        status = 0;
                    }
                }
                if (false == e.isControlDown()) {
                    model.sortingColumns.clear();
                }
                model.sortingColumns.add(new Directive(column, status));
            }
            model.sortData();
            table.setModel(model);
            table.repaint();
            h.repaint();

        }

        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            TableColumn column;
            for (int i = 0; i < TITLE.length; i++) {
                column = getColumn(i);
                headerSizes[i] = column.getPreferredWidth();
            }
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }
    }

    private static class Arrow implements Icon {

        private boolean descending;
        private int size;
        private int priority;

        public Arrow(boolean descending, int size, int priority) {
            this.descending = descending;
            this.size = size;
            this.priority = priority;
        }

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
    }

    private class SortableHeaderRenderer implements TableCellRenderer {

        private TableCellRenderer tableCellRenderer;

        public SortableHeaderRenderer(TableCellRenderer tableCellRenderer) {
            this.tableCellRenderer = tableCellRenderer;
        }

        @Override
        public Component getTableCellRendererComponent(JTable table,
                Object value,
                boolean isSelected,
                boolean hasFocus,
                int row,
                int column) {
            Component c = tableCellRenderer.getTableCellRendererComponent(table,
                    value, isSelected, hasFocus, row, column);
            if (c instanceof JLabel) {
                JLabel l = (JLabel) c;
                l.setHorizontalTextPosition(JLabel.LEFT);
                int modelColumn = table.convertColumnIndexToModel(column);
                l.setIcon(getHeaderRendererIcon(modelColumn, l.getFont().getSize()));
            }
            return c;
        }
    }
}
