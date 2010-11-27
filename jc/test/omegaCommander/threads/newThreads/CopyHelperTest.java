/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package omegaCommander.threads.newThreads;

import junit.framework.TestCase;
import omegaCommander.fileSystem.AbsoluteFile;
import omegaCommander.fileSystem.LocalFile;

/**
 *
 * @author master
 */
public class CopyHelperTest extends TestCase {

    public CopyHelperTest(String testName) {
        super(testName);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }
    class TestFile extends LocalFile {
        @Override
        public boolean isDirectory() {
            return false;
        }
    }
    class TestDirectory extends LocalFile {
        @Override
        public boolean isDirectory() {
            return true;
        }
    }
    /**
     * Test of GetCopyTarget method, of class CopyHelper.
     */
    public void testGetCopyTarget1() {
        System.out.println("GetCopyTarget");
        AbsoluteFile[] files = new AbsoluteFile[]{new LocalFile(), new LocalFile()};
        AbsoluteFile source = null;
        String path = "c:\\tmp";
        String expResult = "c:\\tmp";
        String result = CopyHelper.GetCopyTarget(files, source, path);
        assertEquals(expResult, result);
    }
    public void testGetCopyTarget2() {
        System.out.println("GetCopyTarget");
        AbsoluteFile[] files = new AbsoluteFile[]{new TestDirectory()};
        AbsoluteFile source = null;
        String path = "c:\\tmp";
        String expResult = "c:\\tmp";
        String result = CopyHelper.GetCopyTarget(files, source, path);
        assertEquals(expResult, result);
    }
    public void testGetCopyTarget3() {
        System.out.println("GetCopyTarget");
        AbsoluteFile[] files = new AbsoluteFile[]{new TestFile()};
        AbsoluteFile source = null;
        String path = "c:\\tmp";
        String expResult = "c:\\tmp";
        String result = CopyHelper.GetCopyTarget(files, source, path);
        assertEquals(expResult, result);
    }
    public void testGetCopyTarget4() {
        System.out.println("GetCopyTarget");
        AbsoluteFile[] files = new AbsoluteFile[]{new TestFile()};
        AbsoluteFile source = new LocalFile("c:\\tmp");
        String path = "\\test";
        String expResult = "c:\\tmp\\test";
        String result = CopyHelper.GetCopyTarget(files, source, path);
        assertEquals(expResult, result);
    }
}
