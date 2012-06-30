/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.narod.jcommander.fileSystem.utils;

import java.util.Iterator;
import java.util.ListIterator;
import org.junit.*;
import static org.junit.Assert.*;
import ru.narod.jcommander.fileSystem.BaseFile;
import ru.narod.jcommander.fileSystem.LocalFile;

/**
 *
 * @author master
 */
public class HistoryManagerTest {

    public HistoryManagerTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    class TestFile extends LocalFile {

        public TestFile(String namStringe) {
            this.namStringe = namStringe;
        }
        private String namStringe;

        public TestFile() {
        }

        public String getNamStringe() {
            return namStringe;
        }

        public void setNamStringe(String namStringe) {
            this.namStringe = namStringe;
        }
    }

    /**
     * Test of put method, of class HistoryManager.
     */
    @Test
    public void testPut() {
//        System.out.println("put");
//        BaseFile file1 = new TestFile("file1");
//        BaseFile file2 = new TestFile("file2");
//        BaseFile file3 = new TestFile("file3");
//        HistoryManager instance = new HistoryManager();
//        instance.put(file1);
//        instance.put(file2);
//        instance.put(file3);
//
//        assertEquals(3, instance.historyList.size());
//
//        ListIterator<BaseFile> itr = instance.listIterator;
//
//        System.out.println("forward direction");
//        while (itr.hasNext()) {
//            TestFile t = (TestFile)itr.next();
//            System.out.println(t.namStringe);
//        }
//        System.out.println("reverse direction");
//        while (itr.hasPrevious()) {
//            TestFile t = (TestFile)itr.previous();
//            System.out.println(t.namStringe);
//        }
//        System.out.println("forward direction");
//        while (itr.hasNext()) {
//            TestFile t = (TestFile)itr.next();
//            System.out.println(t.namStringe);
//        }
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }
}
