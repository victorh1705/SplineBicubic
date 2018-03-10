/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.splinecubic;

import FileManager.FileManager;
import Structure.Matriz;
import Structure.PolinomioSolver;
import org.la4j.LinearAlgebra;
import org.la4j.Matrix;
import org.la4j.Vector;
import org.la4j.linear.LinearSystemSolver;
import org.la4j.matrix.dense.Basic2DMatrix;
import org.la4j.vector.dense.BasicVector;

import java.io.IOException;

/**
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
        LinearSystemSolver solver = a.withSolver(
                LinearAlgebra.SolverFactory.JACOBI);
        Vector polinomio = solver.solve(resultados);

        System.out.println("Polinomio: ");
        System.out.println(polinomio.toCSV());
    }

    private static void criacaoMatriz() throws IOException {
        FileManager fm = new FileManager("teste");

        Matriz data_matriz = fm.leituraArquivo();

        PolinomioSolver ps = new PolinomioSolver(data_matriz);
        Vector polinomio = ps.solver();

//        int indices = data_matriz.columns() * data_matriz.rows();

        fm.createFile("saida", ps.getSparce_matriz());
        fm.createFile("resultado", ps.getValue_b());
        fm.createFile("polinomio", polinomio);

        System.out.print("\n " + data_matriz.toCSV() + "\n");

//        for (int i = 0; i < 20; i++) {
//            for (int j = 0; j < 20; j++) {
//                valorZ(data_matriz,polinomio,i*0.1, j*0.1);
//            }
//        }


        Matriz.valorZ(data_matriz,polinomio,1, 0);
        Matriz.valorZ(data_matriz,polinomio,2, 0);
        Matriz.valorZ(data_matriz,polinomio,0, 1);
        Matriz.valorZ(data_matriz,polinomio,0, 2);
        Matriz.valorZ(data_matriz, polinomio, 1, 1);
        Matriz.valorZ(data_matriz, polinomio, 2, 1);
        Matriz.valorZ(data_matriz, polinomio, 1, 2);
        Matriz.valorZ(data_matriz, polinomio, 2, 2);

        System.out.println();
//        Matriz.valorZ2(data_matriz,polinomio,0.0, 0.0);
        Matriz.valorZ2(data_matriz,polinomio,1, 0);
        Matriz.valorZ2(data_matriz,polinomio,2, 0);
        Matriz.valorZ2(data_matriz,polinomio,0, 1);
        Matriz.valorZ2(data_matriz,polinomio,0, 2);
        Matriz.valorZ2(data_matriz, polinomio, 1, 1);
        Matriz.valorZ2(data_matriz, polinomio, 2, 1);
        Matriz.valorZ2(data_matriz, polinomio, 1, 2);
        Matriz.valorZ2(data_matriz, polinomio, 2, 2);

//        MatrizInArray ma = data_matriz.criaMatrizInterpolacao();
    }

}
