/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.splinecubic;

import FileManager.FileManager;
import Estruturas.MatrizInArray;
import org.la4j.LinearAlgebra;
import org.la4j.Matrix;
import org.la4j.Vector;
import org.la4j.linear.LinearSystemSolver;
import org.la4j.matrix.dense.Basic2DMatrix;
import org.la4j.vector.dense.BasicVector;

/**
 *
 * @author voitt
 */
public class NewMain {

    public static void main(String[] args) {

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

    private static void criacaoMatriz() {
        FileManager fm = new FileManager("teste.txt");

        fm.leituraArquivo();

        MatrizInArray matriz_dados = fm.leituraDados();

        int indices = matriz_dados.getColuna() * matriz_dados.getLinha();

        MatrizInArray ma = matriz_dados.criaMatrizInterpolacao();
    }
}
