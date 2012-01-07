/*
 * Main.java
 *
 * Created on 2 ������ 2007 �., 8:34
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package ru.narod.jcommander.tarlib;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.StringTokenizer;

/***
The format of the header block for a file, in the older UNIX-compatible TAR format.

Field Width 	Field Name 	Meaning
100 	name 	name of file
8 	mode 	file mode
8 	uid 	owner user ID
8 	gid 	owner group ID
12 	size 	length of file in bytes
12 	mtime 	modify time of file
8 	chksum 	checksum for header
1 	link 	indicator for links
100 	linkname 	name of linked file
Table 1: tar Header Block (TAR Format)

The link field is 1 for a linked file, 2 for a symbolic link, and 0 otherwise. A directory is indicated by a trailing slash (/) in its name.

For the new USTAR format, headers take on the format shown in Table 2. Note that tar can determine that the USTAR format is being used by the presence of the null-terminated string "ustar" in the magic field. All fields before the magic field correspond to those of the older format described earlier, except that the typeflag replaces the link field.

Field Width 	Field Name 	Meaning
100 	name 	name of file
8 	mode 	file mode
8 	uid 	owner user ID
8 	gid 	owner group ID
12 	size 	length of file in bytes
12 	mtime 	modify time of file
8 	chksum 	checksum for header
1 	typeflag 	type of file
100 	linkname 	name of linked file
6 	magic 	USTAR indicator
2 	version 	USTAR version
32 	uname 	owner user name
32 	gname 	owner group name
8 	devmajor 	device major number
8 	devminor 	device minor number
155 	prefix 	prefix for file name

 ***/
public class TarArchive implements TarConstants {

	static private final int DEFAULT_BLOCK_SIZE = 512;
//	static private final int HEADER_SIZE = 257;
//	static private final int USTAR_HEADER_SIZE = 500;
	static private final int RADIX_8 = 8;
//	private String name;
//	private String mode;
//	private String user;
//	private String group;
//	private String sizeStr;
//	private long size;
//	private String modifyTime;
//	private String checksum;
//	private String fileType;
//	private String linkName;
	private String ustar;
	private String ustarVersion;
//	private String userName;
//	private String groupName;
//	private String devMajor;
//	private String devMinor;
//	private String prefix;
//	private int headerSize;
//	private FileInputStream is;
	//private ArrayList entries = new ArrayList();
	protected HashMap entryMap = new HashMap();
	private int blockSize;
	protected String filename;

	public TarArchive(String filename) {
		this.filename = filename;
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(filename);
			blockSize = DEFAULT_BLOCK_SIZE;
			parse(fis);

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				fis.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

	public TarArchive(InputStream is) {
//		this.is = is;
		blockSize = DEFAULT_BLOCK_SIZE;
		try {
			parse(is);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private String parseField(byte[] byteArray, int offset, int length) {
		for (int i = 0; i < length; i++) {
			if (0 == byteArray[i + offset]) {
				length = i;
				break;
			}
		}
		return new String(byteArray, offset, length);
	}

	private void parse(InputStream fis) throws Exception {

//		System.out.println(is.getChannel().size());

		byte[] b = new byte[DEFAULT_BLOCK_SIZE];

//		int read;
		long filePos = 0;
		while (-1 != fis.read(b)/*DEFAULT_BLOCK_SIZE == is.read(b)*/ && b[0] != 0) {
//				System.out.println(new String(b));
			int offset = 0;

			TarEntry te = new TarEntry(parseField(b, offset, LENGTH_NAME));

			offset += LENGTH_NAME;
			te.setMode(parseField(b, offset, LENGTH_MODE));

			offset += LENGTH_MODE;
			te.setUserId(parseField(b, offset, LENGTH_USER_ID));

			offset += LENGTH_USER_ID;
			te.setGroupId(parseField(b, offset, LENGTH_GROUP_ID));

			offset += LENGTH_GROUP_ID;
			String sz = parseField(b, offset, LENGTH_SIZE);
			long size = Long.parseLong(sz.trim(), RADIX_8);
			te.setSize(size);

			offset += LENGTH_SIZE;
			String modifyTime = parseField(b, offset, LENGTH_MODIFY_TIME);
			long l = Long.parseLong(modifyTime.trim(), RADIX_8) * 1000;
			te.setTime(l);

			offset += LENGTH_MODIFY_TIME;
			te.setCheckSum(parseField(b, offset, LENGTH_CHKSUM));

			offset += LENGTH_CHKSUM;
			te.setFileType(parseField(b, offset, LENGTH_FILE_TYPE));

			offset += LENGTH_FILE_TYPE;
			te.setLinkName(parseField(b, offset, LENGTH_LINK_NAME));

			offset += LENGTH_LINK_NAME;
			ustar = parseField(b, offset, LENGTH_USTAR);

			if (ustar.trim().equals("ustar")) { //new ustar format

//					headerSize = USTAR_HEADER_SIZE;

				offset += LENGTH_USTAR;
				ustarVersion = parseField(b, offset, LENGTH_USTAR_VERSION);

				offset += LENGTH_USTAR_VERSION;

				te.setUserName(parseField(b, offset, LENGTH_USER_NAME));

				offset += LENGTH_USER_NAME;
				te.setGroupName(parseField(b, offset, LENGTH_GROUP_NAME));

				offset += LENGTH_GROUP_NAME;
				te.setDeviceMajor(parseField(b, offset, LENGTH_DEVICE_MAJOR));

				offset += LENGTH_DEVICE_MAJOR;
				te.setDeviceMinor(parseField(b, offset, LENGTH_DEVICE_MINOR));

				offset += LENGTH_DEVICE_MINOR;
				te.setPrefix(parseField(b, offset, LENGTH_PREFIX));
			} else {
//					headerSize = HEADER_SIZE;
			}
//			te.setOffset(is.getChannel().position());
			filePos += DEFAULT_BLOCK_SIZE;
			te.setOffset(filePos);
//			entryMap.put(te.getName(), te);
			addTarEntry(te);


			//���������� ���� � ���� ����� ����
			long fileSize = size;
			long iPer = (int) (fileSize / blockSize);
			if (0 != fileSize) {
				if (fileSize % blockSize == 0) {
					fis.skip(fileSize);
					filePos += fileSize;
				} else {
					fis.skip((iPer + 1) * blockSize);
					filePos += (iPer + 1) * blockSize;
				}
			}
//			te.printEntryHeader();

		}
//		Collection c = getEntries();
//		for (java.util.Iterator it = c.iterator(); it.hasNext();) {
//			TarEntry object = (TarEntry) it.next();
//			object.printEntryHeader();
//		}

	}

	public Collection getEntries() {
		return entryMap.values();
	}

	public InputStream getInputStream(String name) {
		TarEntry te = (TarEntry) entryMap.get(name);
		if (null == te) {
			return null;
		}

		try {
			return new TarInputStream(new FileInputStream(filename), te.getOffset(), te.getSize());
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return null;
	}

	private void addTarEntry(TarEntry te) {
		String name = te.getName();
		entryMap.put(name, te);

		StringTokenizer st = new StringTokenizer(name, ARCHIVE_SEPARATOR);
//		System.out.println(name);
		if (st.countTokens() <= 1) return;
		String[] tokens = new String[st.countTokens()-1];
		for (int i = 0; i < tokens.length; i++) {
			tokens[i] = st.nextToken();
		}

		TarEntry e = new TarEntry(tokens[0]+ARCHIVE_SEPARATOR);
		entryMap.put(e.getName(), e);
		for (int i = 1; i < tokens.length; i++) {
			for (int j=1; j<=i; j++) {
				String nm = e.getName() + tokens[j] + ARCHIVE_SEPARATOR;
				if (!entryMap.containsKey(nm)) {
					TarEntry e1 = new TarEntry(nm);
					entryMap.put(e1.getName(), e1);

				}

//				System.out.println(nm);
			}
		}
//		System.out.println("");
		
	}
}










