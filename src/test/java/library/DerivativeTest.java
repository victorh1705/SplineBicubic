/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library;

import fileManager.FileManager;
import org.junit.*;
import structure.Matriz;
import type.equation;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

/**
 * @author victor
 */
public class DerivativeTest {

    private static FileManager fm;
    private static String path = "src/test/dados/";
    private static Matriz df_x;
    private static Matriz df_xx;
    private static Matriz df_y;
    private static Matriz df_yy;
    private static Matriz matriz;

    public DerivativeTest() {
    }

    @BeforeClass
    public static void setUpClass() throws IOException {
        df_x = new FileManager(path + "pd_x").leituraArquivo();
        df_xx = new FileManager(path + "sd_xx").leituraArquivo();
        df_y = new FileManager(path + "pd_y").leituraArquivo();
        df_yy = new FileManager(path + "sd_yy").leituraArquivo();
        matriz = new FileManager("teste").leituraArquivo();
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
     * Test of firstDerivative method, of class Derivative.
     */
//    @Test
    public void function_x() {
        System.out.println("X - firstDerivative");
        equation type = equation.function_x;
        double expResult = 0.0;

        Matriz pd = new Matriz(matriz.rows(), matriz.columns());
        for (int y = 0; y < matriz.columns(); y++) {
            for (int x = 0; x < matriz.rows(); x++) {
                double result = Derivative.firstDerivative(false, x, y, type,
                        matriz);
                pd.setValue(x, y, result);
            }
        }

        assertEqualsMatriz(df_x, pd, expResult);
    }

    /**
     * Test of 2ndDerivative method, of class Derivative.
     */
    @Test
    public void function_xx() {
        System.out.println("X - 2ndDerivative");
        equation type = equation.function_xx;
        double expResult = 0.0;

        Matriz pd = new Matriz(matriz.rows(), matriz.columns());
        for (int y = 0; y < matriz.columns(); y++) {
            for (int x = 0; x < matriz.rows(); x++) {
                double result = Derivative.secondDerivative(false, x, y, type,
                        matriz);
                pd.setValue(x, y, result);
            }
        }

        assertEqualsMatriz(df_xx, pd, expResult);
    }

    /**
     * Test of firstDerivative method, of class Derivative.
     */
//    @Test
    public void function_y() {
        System.out.println("Y - firstDerivative");
        equation type = equation.function_y;
        double expResult = 0.0;

        Matriz pd = new Matriz(matriz.rows(), matriz.columns());
        for (int y = 0; y < matriz.columns(); y++) {
            for (int x = 0; x < matriz.rows(); x++) {
                double result = Derivative.firstDerivative(false, x, y, type,
                        matriz);
                pd.setValue(x, y, result);
            }
        }

        assertEqualsMatriz(df_y, pd, expResult);
    }


    /**
     * Test of secondDerivative method, of class Derivative.
     */
    @Test
    public void function_yy() {
        System.out.println("Y - 2nd Derivative");
        equation type = equation.function_yy;
        double expResult = 0.0;

        Matriz pd = new Matriz(matriz.rows(), matriz.columns());
        for (int y = 0; y < matriz.columns(); y++) {
            for (int x = 0; x < matriz.rows(); x++) {
                double result = Derivative.secondDerivative(false, x, y, type,
                        matriz);
                pd.setValue(x, y, result);
            }
        }

        assertEqualsMatriz(df_yy, pd, expResult);
    }


    private void assertEqualsMatriz(Matriz matriz_1, Matriz matriz_2,
            double delta) {
        System.out.println(matriz.toCSV());

        System.out.println(matriz_1.toCSV());
        System.out.println(matriz_2.toCSV());

        for (int y = 0; y < matriz_2.columns(); y++) {
            for (int x = 0; x < matriz_2.rows(); x++) {
                double value_1 = matriz_1.get(x, y);
                double value_2 = matriz_2.get(x, y);
                assertEquals(value_1, value_2, delta);
            }
        }
    }

}