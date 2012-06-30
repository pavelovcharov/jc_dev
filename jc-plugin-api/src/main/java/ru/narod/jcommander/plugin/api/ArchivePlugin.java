/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.narod.jcommander.plugin.api;

/**
 *
 * @author master
 */
public interface ArchivePlugin extends AppPlugin {
    void openArchive(String path);
    void readHeader();
}
