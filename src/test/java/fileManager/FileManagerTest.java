/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fileManager;

import org.junit.*;
import org.la4j.Vector;
import org.la4j.vector.dense.BasicVector;
import structure.Matriz;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;

import static org.junit.Assert.*;

/**
 * @author victor
 */
public class FileManagerTest {

    private static FileManager fm;
    private static String path = "src/test/dados/";

    public FileManagerTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        fm = new FileManager("teste");
    }

    @AfterClass
    public static void tearDownClass() {
        try {
            Files.delete(Paths.get(path + "novo.csv"));
            Files.delete(Paths.get(path + "nova_matriz.csv"));
        } catch (NoSuchFileException x) {
            System.err.format("%s: no such" + " file or directory%n");
        } catch (DirectoryNotEmptyException x) {
            System.err.format("%s not empty%n");
        } catch (IOException x) {
            // File permission problems are caught here.
            System.err.println(x);
        }
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of createFile method, of class fileManager.
     */
    @Test
    public void testCreateFile_String_Vector() {
        System.out.println("createFile_Vector");
        String nameFile = "novo";
        double[] vetor = {2.2, 3.4, 4.0, 5.0, 4.1};
        Vector vector = new BasicVector(vetor);

        fm.createFile(path + nameFile, vector);

        boolean find = false;
        for (File file : fileList()) {
            if (file.getName().equals("novo.csv")) {
                find = true;
            }

        }
        assertTrue(find);
    }

    /**
     * Test of createFile method, of class fileManager.
     */
    @Test
    public void testCreateFile_String_Matrix() throws IOException {
        System.out.println("createFile_Matrix");
        String nameFile = "nova_matriz";

        Matriz matriz = fm.leituraArquivo();
        fm.createFile(path + nameFile, matriz);

        boolean find = false;
        for (File file : fileList()) {
            if (file.getName().equals("nova_matriz.csv")) {
                find = true;
            }

        }
        assertTrue(find);
    }

    /**
     * Test of createData method, of class fileManager.
     */
    public void testCreateData() {
        System.out.println("createData");
        String nameFile = "";
        Matriz matriz = null;
        Vector vector = null;
        double range = 0.0;
        FileManager.createData(path + nameFile, matriz, vector, range);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getType method, of class fileManager.
     */
    public void testGetType() {
        System.out.println("getType");
        FileManager instance = null;
        String expResult = "";
        String result = instance.getType();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setType method, of class fileManager.
     */
    public void testSetType() {
        System.out.println("setType");
        String type = "";
        FileManager instance = null;
        instance.setType(type);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of leituraArquivo method, of class fileManager.
     */
    public void testLeituraArquivo() throws Exception {
        System.out.println("leituraArquivo");
        FileManager instance = null;
        Matriz expResult = null;
        Matriz result = instance.leituraArquivo();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    private File[] fileList() {
        return new File("src/test/dados/").listFiles();
    }

}
