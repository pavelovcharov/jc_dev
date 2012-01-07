/*
 * TarInputStream.java
 * Created on 30.04.2009 9:38:55
 */

package ru.narod.jcommander.tarlib;

import java.io.IOException;
import java.io.InputStream;

/**
 *
 * @author Programmer
 */
public class TarInputStream extends InputStream {

	private InputStream is;
	private long bytesToSkip;
	private long bytesToRead;
	private long bytesLeft;
	
	public TarInputStream(InputStream is, long bytesToSkip, long bytesToRead) throws IOException {
		this.is = is;
		this.bytesToRead = bytesToRead;
		this.bytesToSkip = bytesToSkip;
		bytesLeft = bytesToRead;
		is.skip(bytesToSkip);
	}
	public int read(byte[] bytes) throws IOException {
		if (bytesLeft <= 0) return -1;
		int res;
		if (bytesLeft < bytes.length)
			res = is.read(bytes, 0, (int)bytesLeft);
		else
			res = is.read(bytes);
		if (res != -1)
			bytesLeft -= res;
		return res;
	}

	public int read() throws IOException {
		if (bytesLeft <= 0) return -1;
		int res = is.read();
		bytesLeft--;
		return res;
	}

}
