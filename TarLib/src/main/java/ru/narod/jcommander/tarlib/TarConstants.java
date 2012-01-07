/*
 * TarConstants.java
 * Created on 29.04.2009 13:36:09
 */

package ru.narod.jcommander.tarlib;

/**
 *
 * @author Programmer
 */
public interface TarConstants {
	
	static public final int LENGTH_NAME = 100;
	static public final int LENGTH_MODE = 8;
	static public final int LENGTH_USER_ID = 8;
	static public final int LENGTH_GROUP_ID = 8;
	static public final int LENGTH_SIZE = 12;
	static public final int LENGTH_MODIFY_TIME = 12;
	static public final int LENGTH_CHKSUM = 8;
	static public final int LENGTH_FILE_TYPE = 1;
	static public final int LENGTH_LINK_NAME = 100;
	static public final int LENGTH_USTAR = 6;
	static public final int LENGTH_USTAR_VERSION = 2;
	static public final int LENGTH_USER_NAME = 32;
	static public final int LENGTH_GROUP_NAME = 32;
	static public final int LENGTH_DEVICE_MAJOR = 8;
	static public final int LENGTH_DEVICE_MINOR = 8;
	static public final int LENGTH_PREFIX = 155;

	static public final String ARCHIVE_SEPARATOR = "/";
}
