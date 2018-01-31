/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.splinecubic;

import Structure.Matriz;
import FileManager.FileManager;
import org.la4j.LinearAlgebra;
import org.la4j.Matrix;
import org.la4j.Vector;
import org.la4j.linear.LinearSystemSolver;
import org.la4j.matrix.dense.Basic2DMatrix;
import org.la4j.vector.dense.BasicVector;

import java.io.IOException;

/**
 *
 * @author voitt
 */
public class NewMain {

    public static void main(String[] args) throws IOException {

        criacaoMatriz();
//        ma.createFile();
//        CriaVetor cv = new CriaVetor();
//        teste();
    }

    private static void teste() {
        Matrix a = new Basic2DMatrix(new double[][]{
            {1.0, 0.0, 0.0},
            {0.0, 1.0, 0.0},
            {0.0, 0.0, 1.0}
        });

        Vector resultados = new BasicVector(new double[]{2.0, 3.0, 4.0});
        LinearSystemSolver solver = a.withSolver(LinearAlgebra.SolverFactory.JACOBI);
        Vector polinomio = solver.solve(resultados);

        System.out.println("Polinomio: ");
        System.out.println(polinomio.toCSV());
    }

    private static void criacaoMatriz() throws IOException {
        FileManager fm = new FileManager("teste");

        Matriz data_matriz = fm.leituraArquivo();

//        int indices = data_matriz.columns() * data_matriz.rows();

        System.out.print("\n "+data_matriz.toCSV());
//        MatrizInArray ma = data_matriz.criaMatrizInterpolacao();
    }
}
