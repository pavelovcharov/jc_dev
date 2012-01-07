/*
 * TarEntry.java
 * Created on 29.04.2009 12:08:51
 */
package ru.narod.jcommander.tarlib;

/**
 *
 * @author Programmer
 */
public class TarEntry implements TarConstants {

	private String name;
	private String mode;
	private String userId;
	private String groupId;
	private long size;
	private long modifyTime;
	private String fileType;
	private String linkName;
//	private String ustar;
//	private String ustarVersion;
	private String userName;
	private String groupName;
	private String devMajor;
	private String devMinor;
	private String prefix;
	private String checkSum;

	long offset; //�������� ������ ������������ ������

	private boolean isDirectory;

	public TarEntry(String name) {
		if (name == null) {
			throw new NullPointerException();
		}
		if (name.length() > LENGTH_NAME) {
			throw new IllegalArgumentException("entry name too long");
		}
		this.name = name;
		if (this.name.endsWith(ARCHIVE_SEPARATOR))
			isDirectory = true;
	}

	public void setMode(String mode) {
		if (mode == null) {
			throw new NullPointerException();
		}
		if (mode.length() > LENGTH_MODE) {
			throw new IllegalArgumentException("entry mode too long");
		}
		this.mode = mode;
	}

	public void setUserId(String userId) {
		if (userId == null) {
			throw new NullPointerException();
		}
		if (userId.length() > LENGTH_USER_ID) {
			throw new IllegalArgumentException("entry nameId too long");
		}
		this.userId = userId;
	}

	public void setGroupId(String groupId) {
		if (groupId == null) {
			throw new NullPointerException();
		}
		if (groupId.length() > LENGTH_GROUP_ID) {
			throw new IllegalArgumentException("entry groupId too long");
		}
		this.groupId = groupId;
	}

	public void setSize(long size) {
		//XXX ��������� ��� �������
		if (size < 0/* || size > 0xFFFFFFFFL*/) {
			throw new IllegalArgumentException("invalid entry size " + size);
		}
		this.size = size;
	}

	public void setTime(long mTime) {
		if (mTime < 0/* || mTime > 0xFFFFFFFFL*/) {
			throw new IllegalArgumentException("invalid entry modify time " + mTime);
		}
		this.modifyTime = mTime;
	}

	public void setFileType(String fileType) {
		if (fileType == null) {
			throw new NullPointerException();
		}
		if (fileType.length() > LENGTH_FILE_TYPE) {
			throw new IllegalArgumentException("entry fileType too long");
		}
		this.fileType = fileType;
	}

	public void setLinkName(String linkName) {
		if (linkName == null) {
			throw new NullPointerException();
		}
		if (linkName.length() > LENGTH_LINK_NAME) {
			throw new IllegalArgumentException("entry linkName too long");
		}
		this.linkName = linkName;
	}

	public void setUserName(String userName) {
		if (userName == null) {
			throw new NullPointerException();
		}
		if (userName.length() > LENGTH_USER_NAME) {
			throw new IllegalArgumentException("entry userName too long");
		}
		this.userName = userName;
	}

	public void setGroupName(String groupName) {
		if (groupName == null) {
			throw new NullPointerException();
		}
		if (groupName.length() > LENGTH_GROUP_NAME) {
			throw new IllegalArgumentException("entry groupName too long");
		}
		this.groupName = groupName;
	}

	public void setDeviceMajor(String deviceMajor) {
		if (deviceMajor == null) {
			throw new NullPointerException();
		}
		if (deviceMajor.length() > LENGTH_DEVICE_MAJOR) {
			throw new IllegalArgumentException("entry deviceMajor too long");
		}
		this.devMajor = deviceMajor;
	}

	public void setDeviceMinor(String deviceMinor) {
		if (deviceMinor == null) {
			throw new NullPointerException();
		}
		if (deviceMinor.length() > LENGTH_DEVICE_MINOR) {
			throw new IllegalArgumentException("entry deviceMinor too long");
		}
		this.devMinor = deviceMinor;
	}

	public void setPrefix(String prefix) {
		if (prefix == null) {
			throw new NullPointerException();
		}
		if (prefix.length() > LENGTH_PREFIX) {
			throw new IllegalArgumentException("entry prefix too long");
		}
		this.prefix = prefix;
	}

	public void setCheckSum(String checkSum) {
		if (checkSum == null) {
			throw new NullPointerException();
		}
		if (checkSum.length() > LENGTH_PREFIX) {
			throw new IllegalArgumentException("entry checksum too long");
		}
		//XXX check sum and throw exception if it wrong
		this.checkSum = checkSum;
	}

	void setOffset(long offset) {
		if (offset < 0/* || size > 0xFFFFFFFFL*/) {
			throw new IllegalArgumentException("invalid entry offset " + offset);
		}
		this.offset = offset;
	}

	public void printEntryHeader() {
			System.out.println("====" + name + "===");
			System.out.println("mode " + mode);
			System.out.println("userId " + userId);
			System.out.println("groupId " + groupId);
			System.out.println("size " + size);
			System.out.println("modifyTime " + modifyTime);
			System.out.println("fileType " + fileType);
			System.out.println("linkName " + linkName);
			System.out.println("userName " + userName);
			System.out.println("groupName " + groupName);
			System.out.println("devMajor " + devMajor);
			System.out.println("devMinor " + devMinor);
			System.out.println("prefix " + prefix);
//			System.out.println("checkSum " + checkSum);
			System.out.println("isDirectory " + isDirectory);
			System.out.println();
	}

	public String getName() {
		return name;
	}

	public boolean isDirectory() {
		return isDirectory;
	}

	public long getSize() {
		return size;
	}

	public long getTime() {
		return modifyTime;
	}

	public long getOffset() {
		return offset;
	}


}
