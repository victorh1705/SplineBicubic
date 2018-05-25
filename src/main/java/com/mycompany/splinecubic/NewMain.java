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
import plotting.Surface;
import structure.Matriz;
import structure.PolinomioSolver;

import java.io.FileNotFoundException;
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
        try {
            ExcelCreator ec = new ExcelCreator("novo");

            ec.createSheet("planilha1");
            ec.setValue(0, 0, "Linha 1");
            ec.setValue(1, 0, "Linha 2");
            ec.setValue(2, 0, "Linha 3");
            //TODO: (valorReferencia - valorSaida)/valorSaida
            ec.writeFile();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
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
        fm.createData("data", data_matriz, polinomio, 0.1);


        plot(data_matriz, polinomio);

//        printDados(data_matriz, polinomio);
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
