/*
 * TGZArhive.java
 * Created on 04.05.2009 11:12:45
 */

package ru.narod.jcommander.tarlib;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

/**
 *
 * @author Programmer
 */
public class TGZArchive extends TarArchive/*implements TarConstants */{

//	static private final int DEFAULT_BLOCK_SIZE = 512;
//	static private final int RADIX_8 = 8;

//	private String ustar;
//	private String ustarVersion;
//
//	private InputStream is;
//
//	private HashMap entryMap = new HashMap();
//	private int blockSize;
//
//	private String filename;

	public TGZArchive(String filename) throws Exception {
		super(new GZInputStream(filename));
		this.filename = filename;

//		this.filename = filename;
////		FileInputStream fis = null;
//		try {
//			is = new FileInputStream(filename);
//			blockSize = DEFAULT_BLOCK_SIZE;
//			parse();
//
//		} catch (Exception ex) {
//			ex.printStackTrace();
//		} finally {
//			try {
//				is.close();
//			} catch (IOException ex) {
//				ex.printStackTrace();
//			}
//		}
	}

//	public TGZArchive(InputStream is) {
//		super(is);
////		this.is = is;
////		blockSize = DEFAULT_BLOCK_SIZE;
////		try {
////			parse();
////		} catch (Exception ex) {
////			ex.printStackTrace();
////		}
//	}

//	private String parseField(byte[] byteArray, int offset, int length) {
//		for (int i = 0; i < length; i++) {
//			if (0 == byteArray[i + offset]) {
//				length = i;
//				break;
//			}
//		}
//		return new String(byteArray, offset, length);
//	}
//
//	private void parse() throws Exception {
//
////		System.out.println(is.getChannel().size());
//
//		byte[] b = new byte[DEFAULT_BLOCK_SIZE];
//
//		int read = 0;
//		long filePos = 0;
//		while (/*DEFAULT_BLOCK_SIZE ==*/-1 != (read = is.read(b))/* && b[0] != 0*/) {
////			System.out.println("");
////				System.out.println(new String(b));
////			if (DEFAULT_BLOCK_SIZE != read) {
////					read += is.read(b, read, DEFAULT_BLOCK_SIZE-read);
////			}
//			if (b[0] == 0) continue;
////			System.out.println(read);
//			int offset = 0;
//
//			TarEntry te = new TarEntry(parseField(b, offset, LENGTH_NAME));
//
//			offset += LENGTH_NAME;
//			te.setMode(parseField(b, offset, LENGTH_MODE));
//
//			offset += LENGTH_MODE;
//			te.setUserId(parseField(b, offset, LENGTH_USER_ID));
//
//			offset += LENGTH_USER_ID;
//			te.setGroupId(parseField(b, offset, LENGTH_GROUP_ID));
//
//			offset += LENGTH_GROUP_ID;
//			String sz = parseField(b, offset, LENGTH_SIZE);
//			long size = Long.parseLong(sz.trim(), RADIX_8);
//			te.setSize(size);
//
//			offset += LENGTH_SIZE;
//			String modifyTime = parseField(b, offset, LENGTH_MODIFY_TIME);
//			long l = Long.parseLong(modifyTime.trim(), RADIX_8) * 1000;
//			te.setTime(l);
//
//			offset += LENGTH_MODIFY_TIME;
//			te.setCheckSum(parseField(b, offset, LENGTH_CHKSUM));
//
//			offset += LENGTH_CHKSUM;
//			te.setFileType(parseField(b, offset, LENGTH_FILE_TYPE));
//
//			offset += LENGTH_FILE_TYPE;
//			te.setLinkName(parseField(b, offset, LENGTH_LINK_NAME));
//
//			offset += LENGTH_LINK_NAME;
//			ustar = parseField(b, offset, LENGTH_USTAR);
//
//			if (ustar.trim().equals("ustar")) { //new ustar format
//
////					headerSize = USTAR_HEADER_SIZE;
//
//				offset += LENGTH_USTAR;
//				ustarVersion = parseField(b, offset, LENGTH_USTAR_VERSION);
//
//				offset += LENGTH_USTAR_VERSION;
//
//				te.setUserName(parseField(b, offset, LENGTH_USER_NAME));
//
//				offset += LENGTH_USER_NAME;
//				te.setGroupName(parseField(b, offset, LENGTH_GROUP_NAME));
//
//				offset += LENGTH_GROUP_NAME;
//				te.setDeviceMajor(parseField(b, offset, LENGTH_DEVICE_MAJOR));
//
//				offset += LENGTH_DEVICE_MAJOR;
//				te.setDeviceMinor(parseField(b, offset, LENGTH_DEVICE_MINOR));
//
//				offset += LENGTH_DEVICE_MINOR;
//				te.setPrefix(parseField(b, offset, LENGTH_PREFIX));
//			} else {
////					headerSize = HEADER_SIZE;
//			}
//			filePos += DEFAULT_BLOCK_SIZE;
//			te.setOffset(filePos);
//
//			entryMap.put(te.getName(), te);
////			entries.add(te);
//
//			//���������� ���� � ���� ����� ����
//			long fileSize = size;
//			long iPer = (int) (fileSize / blockSize);
//			if (0 != fileSize) {
//				if (fileSize % blockSize == 0) {
//					is.skip(fileSize);
//					filePos += fileSize;
//				} else {
//					is.skip((iPer + 1) * blockSize);
//					filePos += (iPer + 1) * blockSize;
//				}
//			}
////			te.printEntryHeader();
//
//		}
//		Collection c = getEntries();
//		for (Iterator it = c.iterator(); it.hasNext();) {
//			TarEntry object = (TarEntry) it.next();
//			object.printEntryHeader();
//		}
//		System.out.println(c.size());
//
//	}
//
//	public Collection getEntries() {
//		return entryMap.values();
//	}

	public InputStream getInputStream(String name) {
		TarEntry te = (TarEntry)entryMap.get(name);
		if (null == te) return null;
		try {
//			return new TarInputStream(new FileInputStream(filename), te.getOffset(), te.getSize());
			return new TarInputStream(new GZInputStream(filename), te.getOffset(), te.getSize());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
}
