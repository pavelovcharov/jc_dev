/*
 * TGZArhive.java
 * Created on 04.05.2009 11:12:45
 */
package ru.narod.jcommander.tarlib;

import java.io.InputStream;

/**
 *
 * @author Programmer
 */
public class TGZArchive extends TarArchive {

    public TGZArchive(String filename) throws Exception {
        super(new GZInputStream(filename));
        this.filename = filename;
    }

    @Override
    public InputStream getInputStream(String name) {
        TarEntry te = (TarEntry) entryMap.get(name);
        if (null == te) {
            return null;
        }
        try {
            return new TarInputStream(new GZInputStream(filename), te.getOffset(), te.getSize());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
}