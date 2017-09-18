/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.splinecubic;

import FileManager.FileManager;
import Estruturas.MatrizInArray;

/**
 *
 * @author voitt
 */
public class NewMain {

    public static void main(String[] args) {

        FileManager fm = new FileManager("teste.txt");

        fm.leituraArquivo();

        MatrizInArray matriz_dados = fm.leituraDados();

        int indices = matriz_dados.getColuna() * matriz_dados.getLinha();
        
        MatrizInArray ma = matriz_dados.criaMatrizInterpolacao(matriz_dados);

        ma.createFile();
//        CriaVetor cv = new CriaVetor();
    }
}
