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
 * MyTgzFile.java
 * Created on 04.05.2009 14:14:18
 */
package ru.narod.jcommander.fileSystem.archive;

import ru.narod.jcommander.fileSystem.*;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import ru.narod.jcommander.tarlib.TGZArchive;
import ru.narod.jcommander.tarlib.TarEntry;

/**
 *
 * @author Programmer
 */
public class MyTgzFile extends MyTarFile {

//    protected HashMap archiveMap;
//    protected LocalFile archive;
//    protected BaseFile parent;
//    protected TarEntry entry = null;

	//XXX � ���� ����� � ����?
	/**
	 * ������� ������ ������ MyTarFile � ������� ����� <I>parent</I>
	 *
	 * @param parent ������ ������ LocalFile, �������� ��������� ������
	 */
	public MyTgzFile(LocalFile parent) {
		super(parent);
//        if (false == exists()) throw new IllegalArgumentException("ArchiveFile " + parent + " doesn't exists!");
//        this.archive = parent;
//        this.parent = parent.getAbsoluteParent();
//        archiveMap = new HashMap();
//        getArchiveContent();
	}

	/**
	 * ������� ������ ������ MyTarFile � ������� ����� <I>parent</I>
	 * @param parent ������ ������ BaseFile
	 */
	public MyTgzFile(BaseFile parent) {
		super(parent);
//        this(new LocalFile(parent));
	}

	/**
	 * ������� ������ ������ MyTarFile � ������� ����� <I>parent</I> � ����� �����
	 * <I>child</I>
	 * @param parent ������ ���������� ���� � �����
	 * @param child ��� ����������� �����
	 */
	public MyTgzFile(MyTgzFile parent, String child) {
		super(parent, child);
//        this.parent = parent;
//        archive = parent.archive;
//        archiveMap = parent.archiveMap;
//        entry = new TarEntry(parent.entry+ARCHIVE_SEPARATOR+child);
//
	}

	protected MyTgzFile(LocalFile parent, TarEntry tarEntry) {
		super(parent, tarEntry);
//        super(((MyTgzFile)parent).archive, entry.getName());
//        this.entry = entry;
//        this.parent = parent;
//        if (parent instanceof MyTgzFile) {
//            archiveMap = ((MyTgzFile) parent).archiveMap;
//            archive = ((MyTgzFile) parent).archive;
//        }
	}

	/**
	 * ������� ������ ������ MyTgzFile. ������������ ��� ���������� ���������
	 * ����������� ������, ������������ ������ ������� ������.
	 * @param parent ������ ������ MyTgzFile, �������������� ����� �����, ���������� �������� �����
	 * �����������
	 * @param archive ������ ���� � ������ �� ����� (��������, �� ��������� �����)
	 */
//	public MyTgzFile(MyTgzFile parent, LocalFile archive) {
//		super(parent, archive);
////        this(archive);
////        this.parent = parent;
//	}

	/**
	 * ������� ������ ������ MyTgzFile. ������������ ��� ���������� ���������
	 * ����������� ������, ������������ ������ ������� ������.
	 * @param parent ������ ������ ArchiveFile, �������������� ����� �����, ���������� �������� �����
	 * �����������
	 * @param archive ������ ���� � ������ �� ����� (��������, �� ��������� �����)
	 */
	public MyTgzFile(ArchiveFile parent, LocalFile archive) {
		this(archive);
		this.parent = parent;
	}

	/**
	 * �������� ����� ��� ������ �� �����
	 * @return ����� ��� ������ �� �����
	 * @throws java.lang.Exception ����������, ���� ��� �������� ������ ��������� ������
	 */
	@Override
	public java.io.InputStream getInputStream() throws java.io.IOException {
		try {
			if (null == entry) {
				return parent.getInputStream();
			}
			final TGZArchive tarArchive = new TGZArchive(archive.getAbsolutePath());
			return tarArchive.getInputStream(entry.getName());
		} catch (Exception ex) {
			Logger.getLogger(MyTgzFile.class.getName()).log(Level.SEVERE, null, ex);
			return null;
		}
	}

	@Override
	protected boolean getArchiveContent() {
		boolean success = true;
		archiveMap.clear();

		try {
			String name;
			final TGZArchive tarArchive = new TGZArchive(archive.getAbsolutePath());
			final Collection entries = tarArchive.getEntries();
			for (Object object : entries) {
				if (object instanceof TarEntry) {
					TarEntry te = (TarEntry) object;
					name = te.getName();
					if (te.isDirectory()) {
						name = name.substring(0,
								name.lastIndexOf(ARCHIVE_SEPARATOR));
					}
					archiveMap.put(name, te);
				}

			}
		} catch (Exception ex) {
			success = false;
		}

		return success;
	}

//	private void setArchiveMap(HashMap archiveMap) {
//		this.archiveMap = archiveMap;
//	}

//	/**
//	 * �������� ������ ���� ������, ����������� � ������ ����� � ������
//	 * @return ������ ���� ������
//	 */
//	@Override
//	public String[] list() {
//		ArrayList names = new ArrayList();
//		Set keys = archiveMap.keySet();
//		if (keys != null) {
//			if (null == entry) {//������ ������
//				// search through all available archive entries
//				Iterator iter = keys.iterator();
//				while (iter.hasNext()) {
//					String key = (String) iter.next();
//					if (key.indexOf(ARCHIVE_SEPARATOR) == 0) {
//						// remove leading "/"
//						key = key.substring(ARCHIVE_SEPARATOR.length());
//					}
//
//					if (key.indexOf(ARCHIVE_SEPARATOR) < 0) {
//						// no more "/" found => correct level
//						names.add(key);
//					}
//				}
//			} else {
//				final String name = entry.getName();
//
//				// search through all available archive entries
//				final Iterator iter = keys.iterator();
//				while (iter.hasNext()) {
//					final String key = (String) iter.next();
//					if (key.startsWith(name) && !key.equals(name)) {
//						// candidate found
//						String rest = key.substring(name.length());
//						if (rest.indexOf(ARCHIVE_SEPARATOR) == 0) {
//							// remove leading "/"
//							rest = rest.substring(ARCHIVE_SEPARATOR.length());
//						}
//
//						if (rest.indexOf(ARCHIVE_SEPARATOR) < 0) {
//							// no more "/" found => correct level
//							names.add(rest);
//						}
//					}
//				}
//			}
//		}
//		String[] list = new String[names.size()];
//		for (int i = 0; i < list.length; i++) {
//			list[i] = names.get(i).toString();
//		}
//		return list;
//	}

//	/**
//	 * �������� ������ ������, ����������� � ������ ����� � ������
//	 * @return ������ ������
//	 */
//	@Override
//	public BaseFile[] getFiles(FileFilter filter) {
//		return getFiles();
//	}
	@Override
	public BaseFile[] getFiles() {
		final String[] names = list();
		MyTgzFile[] files = null;
		if (names != null) {
			files = new MyTgzFile[names.length];
			String name;
			for (int i = 0; i < names.length; ++i) {

				if (null != entry) {
					name = entry.getName() + names[i];
				} else {
					name = names[i];
				}
				files[i] = new MyTgzFile(this, (TarEntry) archiveMap.get(name));
			}
		}
		return files;
	}

//	/**
//	 * ����������, �������� �� ������ ������ ������ ��� ���������
//	 * @return <B>true</B>, ���� ������ ������ �������� ���������, ����� - <B>false</B>
//	 */
//	@Override
//	public boolean isDirectory() {
//		return (null == entry) ? archive.isDirectory() : entry.isDirectory();
//	}

//	/**
//	 * �������� ������������ ����� ��� ������� �����
//	 * @return ������ ������ BaseFile, �������������� ������������ ����� ������� �����
//	 */
//	@Override
//	public BaseFile getAbsoluteParent() {
//		return parent;
//	//return getAbstractParent();
//	}

//	/**
//	 * ������������ ������ � ����� ��������� �����. ��������, �����
//	 * 12455 ����� ����� ��� 12 455
//	 * @return ������ ���������� ������ ����� � ����������������� ����
//	 */
//	@Override
//	public String getFormatFileSize() {
//		if (null == entry) {
//			return getFormatFileSize(archive.length());
//		}
//		if (true == entry.isDirectory()) {
//			return DEFAULT_DIR_SIZE;
//		}
//		return getFormatFileSize(entry.getSize());
//	}

//	/**
//	 * ���������� ���� ���������� ����������� ����� ��� ��������
//	 * @return ���� ���������� ����������� � ������� dd.MM.yyyy hh:mm
//	 */
//	@Override
//	public String getLastModifiedDate() {
//		Date date = new Date();
//		SimpleDateFormat sdate = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
//		if (null == entry) {
//			date.setTime(((LocalFile) parent).lastModified());
//		} else {
//			date.setTime(entry.getTime());
//		}
//		return sdate.format(date);
//
//	}

//	/**
//	 * ���������� �������� ����� ��� ��������. ro - ������ ��� ������,
//	 * h - �������
//	 * @return ������, ���������� �������� ����� ��� ��������
//	 */
//	@Override
//	public String getAtributeString() {
//		if (null == entry) {
//			return ((LocalFile) parent).getAtributeString();
//		}
//		return "ro";
//	}

//	/**
//	 * �������� ����� ��� ������ � ����
//	 * @throws java.lang.Exception ����������, ���� ��� �������� ������ ��������� ������
//	 * @return ����� ��� ������ � ����
//	 */
//	@Override
//	public java.io.OutputStream getOutputStream() throws java.io.IOException {
//		if (null == entry) {
//			return parent.getOutputStream();
//		}
//		return null;
//	}

//	/**
//	 * �������� ��������� ������������� �������
//	 * @return ��������� �������������
//	 */
//	@Override
//	public String toString() {
//		if (false == parent.hasParent()) {
//			return parent + getFilename();
//		}
//		return parent + separator + getFilename();
//	}
//
//	@Override
//	public long getLastModifiedTime() {
//		if (null == entry) {
//			return parent.getLastModifiedTime();
//		} else {
//			return entry.getTime();
//		}
//	}
//
//	@Override
//	public long length() {
//		if (null == entry) {
//			return archive.length();
//		} else {
//			return entry.getSize();
//		}
//	}
//
//	@Override
//	public String getPathWithSlash() {
//		String path = getAbsolutePath();
//		//if (false == isDirectory() ) return super.getAbsolutePath();
//		return (super.getAbsolutePath().endsWith(separator)) ? path : path + separator;
//	}
}
