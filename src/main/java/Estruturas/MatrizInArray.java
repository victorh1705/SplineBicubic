/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Estruturas;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import static java.lang.Math.pow;

/**
 *
 * @author voitt
 */
public class MatrizInArray {

    private double[] array;
    private int linha;
    private int coluna;

    public MatrizInArray(int linha, int coluna) {
        this.linha = linha;
        this.coluna = coluna;
        this.array = new double[coluna * linha];
    }

    public void add(int indice_linha, int indice_coluna, double valor) {
        int indice = getIndice(indice_linha, indice_coluna);
        array[indice] = valor;
    }

    public double get(int indice_linha, int indice_coluna) {
        int indice = getIndice(indice_linha, indice_coluna);
        return array[indice];
    }

    private int getIndice(int indice_linha, int indice_coluna) {
        return coluna * indice_linha + indice_coluna;
    }

    private boolean isAresta(int indice_linha, int indice_coluna) {
        if (indice_linha > 0 && indice_linha < linha - 1) {
            return true;
        }
        if (indice_coluna > 0 && indice_coluna < coluna - 1) {
            return true;
        }
        return false;
    }

    private boolean isVertice(int indice_linha, int indice_coluna) {
        if (indice_linha > 0 && indice_linha < linha - 1) {
            if (indice_coluna > 0 && indice_coluna < coluna - 1) {
                return true;
            }
        }
        return false;
    }

    public MatrizInArray criaMatrizInterpolacao(MatrizInArray dados) {

        int indices = linha * coluna;

        MatrizInArray matriz_interpolacao = new MatrizInArray(indices, 16);

        for (int i = 0; i < linha; i++) {
            for (int j = 0; j < coluna; j++) {

                for (int k = 0; k < 4; k++) {
                    for (int l = 0; l < 4; l++) {
                        int indice = 16 * i * coluna + 16 * j + 4 * k + l;
                        int indice_linha = indice / 16;
                        int indice_coluna = indice % 16;

                        double valor = (double) ((double) pow(i+1, k) * pow(j+1, l));

                        matriz_interpolacao.add(indice_linha, indice_coluna, valor);
                    }

                }

            }

        }

        return matriz_interpolacao;
    }

    public void createFile() {

        try (PrintStream out = new PrintStream("saida.txt")) {
            for (int i = 0; i < linha; i++) {
                for (int j = 0; j < coluna; j++) {
                    out.print(get(i, j) + "\t");
                }
                out.println();
            }
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    public double[] getArray() {
        return array;
    }

    public void setArray(double[] array) {
        this.array = array;
    }

    public int getLinha() {
        return linha;
    }

    public void setLinha(int linha) {
        this.linha = linha;
    }

    public int getColuna() {
        return coluna;
    }

    public void setColuna(int coluna) {
        this.coluna = coluna;
    }
}
