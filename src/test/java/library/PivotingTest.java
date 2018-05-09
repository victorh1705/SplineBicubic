/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library;

import org.junit.*;
import org.la4j.Vector;
import org.la4j.matrix.SparseMatrix;

import static org.junit.Assert.fail;

/**
 * @author victor
 */
public class PivotingTest {

    public PivotingTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of Basic method, of class Pivoting.
     */
    @Test
    public void testBasic() {
        System.out.println("Basic");
        SparseMatrix matrix = null;
        Vector vector = null;
        boolean invertido = false;
        Pivoting.Basic(matrix, vector, invertido);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of Parcial method, of class Pivoting.
     */
    @Test
    public void testParcial() {
        System.out.println("Parcial");
        SparseMatrix matrix = null;
        Vector vector = null;
        Pivoting.Parcial(matrix, vector);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of Complete method, of class Pivoting.
     */
    @Test
    public void testComplete() {
        System.out.println("Complete");
        SparseMatrix matrix = null;
        Vector vector = null;
        Pivoting.Complete(matrix, vector);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}
