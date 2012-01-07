/*
 * GZInputStream.java
 * Created on 04.05.2009 11:50:33
 */

package ru.narod.jcommander.tarlib;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;

/**
 *
 * @author Programmer
 */
public class GZInputStream extends GZIPInputStream {

	public GZInputStream(String filename) throws Exception {

		super(new FileInputStream(filename));
	}

	public int read(byte[] b) throws IOException {
		int read = 0;
		int bytesToRead = b.length;

//		int offset = 0;
		int length = bytesToRead;
		int ready = 0;

		while ((read = super.read(b, ready, length)) != -1 && (ready += read) != bytesToRead) {
//			System.out.println(read);
			length = bytesToRead - ready;
//			offset = ready;
		}

		return read == -1 ? read : ready;
	}
	
}
