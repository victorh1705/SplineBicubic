/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.splinecubic;

import fileManager.ExcelCreator;
import fileManager.FileManager;
import org.jzy3d.analysis.AnalysisLauncher;
import org.la4j.Vector;
import org.la4j.vector.dense.BasicVector;
import plotting.Surface;
import structure.Matriz;
import structure.PolinomioSolver;

import java.io.IOException;

/**
 * @author voitt
 */
public class NewMain {

    public static void main(String[] args) throws Exception {

//        criacaoMatriz();
//        ma.createFile();
//        CriaVetor cv = new CriaVetor();
        excelCreator();


    }

    private static void excelCreator() {
        Matriz matriz_esperado;


        try (ExcelCreator ec = new ExcelCreator("report")) {
            ec.createSheet("resultado_esperado");


            //Resultado Esperado
            ExcelCreator readMatriz = new ExcelCreator();
            readMatriz.readFile("data/Bicubica.xlsx");

            matriz_esperado = readMatriz.readMatriz(
                    "Calculos_2");

            ec.setMatriz(matriz_esperado);


            //Resultado Calculado
            Vector aux = new BasicVector(21);
            for (int i = 0; i <= 20; i++) {
                aux.set(i, i * 0.1);
            }
            Vector polinomio = criacaoMatriz();

            Matriz matriz_calculado = new Matriz(aux.length(), aux.length());
            for (int i = 0; i <= 20; i++) {
                for (int j = 0; j <= 20; j++) {
                    double valor = Matriz.valorZ2(polinomio,
                            aux.get(i), aux.get(j));
                    matriz_calculado.setValue(i, j, valor);
                }
            }


            ec.createSheet("resultado_real");

            ec.setVector(aux, 1, ExcelCreator.Direction.VERTICAL);
            ec.setVector(aux, 1, ExcelCreator.Direction.HORIZONTAL);
            ec.setMatriz(matriz_calculado, 1, 1);


            //TODO: (valorReferencia - valorSaida)/valorSaida
            ec.createSheet("diferenca");

            Matriz diference = new Matriz(matriz_calculado.columns(),
                    matriz_calculado.rows());

            for (int i = 0; i < matriz_calculado.columns(); i++) {
                for (int j = 0; j < matriz_calculado.rows(); j++) {
                    double value =
                            Math.abs(
                                    (matriz_esperado.getValue(i + 1, j + 1) -
                                     matriz_calculado.getValue(i, j))
                                    / matriz_calculado.getValue(i, j));

                    diference.setValue(i, j, value);
                }
            }

            ec.setMatriz(diference, 1, 1);


            ec.writeFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Vector criacaoMatriz() throws IOException {
        FileManager fm = new FileManager("teste");

        Matriz data_matriz = fm.leituraArquivo();

        PolinomioSolver ps = new PolinomioSolver(data_matriz);
        Vector polinomio = ps.solver();

//        int indices = data_matriz.columns() * data_matriz.rows();

//        fm.createFile("saida", ps.getSparce_matriz());
//        fm.createFile("resultado", ps.getValue_b());
//        fm.createFile("polinomio", polinomio);
//        fm.createData("data", data_matriz, polinomio, 0.1);


//        plot(data_matriz, polinomio);

//        printDados(data_matriz, polinomio);
        return polinomio;
    }

    private static void printDados(Matriz data_matriz, Vector polinomio) {
        System.out.print("\n " + data_matriz.toCSV() + "\n");

        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                Matriz.valorZ(data_matriz, polinomio, i * 0.1, j * 0.1);
            }
        }

        Matriz.valorZ(data_matriz, polinomio, 1, 0);
        Matriz.valorZ(data_matriz, polinomio, 2, 0);
        Matriz.valorZ(data_matriz, polinomio, 0, 1);
        Matriz.valorZ(data_matriz, polinomio, 0, 2);
        Matriz.valorZ(data_matriz, polinomio, 1, 1);
        Matriz.valorZ(data_matriz, polinomio, 2, 1);
        Matriz.valorZ(data_matriz, polinomio, 1, 2);
        Matriz.valorZ(data_matriz, polinomio, 2, 2);

        System.out.println();
        Matriz.valorZ2(data_matriz, polinomio, 0.0, 0.0);
        Matriz.valorZ2(data_matriz, polinomio, 1, 0.1);
        Matriz.valorZ2(data_matriz, polinomio, 2, 0.1);
        Matriz.valorZ2(data_matriz, polinomio, 0.1, 1);
        Matriz.valorZ2(data_matriz, polinomio, 0.1, 2);
        Matriz.valorZ2(data_matriz, polinomio, 1, 1.1);
        Matriz.valorZ2(data_matriz, polinomio, 2, 1.1);
        Matriz.valorZ2(data_matriz, polinomio, 1.1, 2);
        Matriz.valorZ2(data_matriz, polinomio, 2, 2);
    }

    private static void plot(Matriz data_matriz, Vector polinomio) {
        Surface surface = new Surface(data_matriz, polinomio);
        try {
            AnalysisLauncher.open(surface);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
