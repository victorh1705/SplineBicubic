/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Estruturas;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import static java.lang.Math.pow;
import java.text.NumberFormat;
import org.la4j.Vector;
import org.la4j.matrix.sparse.CRSMatrix;
import org.la4j.vector.dense.BasicVector;

/**
 *
 * @author voitt
 */
public class MatrizInArray {

    private double[] array;
    private CRSMatrix matrix_esparca;
    private Vector resultados;
    private int linha,
            coluna,
            linha_aux,
            coluna_aux,
            num_equacao,
            indice_linha,
            indice_coluna,
            regiao_linha,
            regiao_coluna;

    private enum equation {
        function,
        function_x,
        function_y,
        function_xy,
        function_xx,
        function_yy;
    }

    public MatrizInArray(int linha, int coluna) {
        this.linha = linha;
        this.coluna = coluna;
        this.num_equacao = 16 * (linha - 1) * (coluna - 1);
        this.array = new double[linha * coluna];
        this.indice_linha = 0;
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
        if (indice_linha == 0 || indice_linha == linha - 1) {
            return true;
        }
        return indice_coluna == 0 || indice_coluna == coluna - 1;
    }

    private boolean isVertice(int indice_linha, int indice_coluna) {
        if (indice_linha == 0 || indice_linha == linha - 1) {
            if (indice_coluna == 0 || indice_coluna == coluna - 1) {
                return true;
            }
        }
        return false;
    }

    public MatrizInArray criaMatrizInterpolacao() {

        System.out.println("\nIndices: " + num_equacao);

        MatrizInArray matriz_interpolacao = new MatrizInArray(num_equacao, 16);
        matrix_esparca = new CRSMatrix(num_equacao, num_equacao);
        resultados = new BasicVector(num_equacao);

//        for (regiao_linha = 0; regiao_linha <= linha; regiao_linha++) {
//            for (regiao_coluna = 0; regiao_coluna <= coluna; regiao_coluna++) {
//
////                for (int i = 0; i <= 1; i++) {
////                    for (int j = 0; j <= 1; j++) {
////                        intervalo(matriz_interpolacao, i, j);
//                        intervalo(matriz_interpolacao, 0, 0);
////                    }
////                }
//            }
//        }
        for (linha_aux = 0; linha_aux < linha; linha_aux++) {
            for (coluna_aux = 0; coluna_aux < coluna; coluna_aux++) {
                intervalo(linha_aux, coluna_aux);
            }
        }

        System.out.print("\nNum_equacao: " + num_equacao);
        System.out.print("\nIndices_linha: " + indice_linha);

        createFile();
        return matriz_interpolacao;
    }

    /**
     * Trata os pontos da Regiao (regiao_linha, regiao_coluna) onde i
     * = regiao_linha e j = regiao_coluna
     *
     * (i,j), (i+1,j), (i,j+1), (i+1,j+1)
     *
     * @param matriz_interpolacao
     * @param ponto_x
     * @param ponto_y
     */
    private void intervalo(int ponto_x, int ponto_y) {

        regiao_linha = (ponto_x > 0) ? ponto_x - 1 : 0;
        regiao_coluna = (ponto_y > 0) ? ponto_y - 1 : 0;

//        resultados.set(indice_linha, );
        if (isVertice(ponto_x, ponto_y)) {
            vertice(ponto_x, ponto_y);
        } else if (isAresta(ponto_x, ponto_y)) {
//            aresta(ponto_x, ponto_y);
            indice_linha += 8;
        } else {
            indice_linha += 16;
        }

        System.out.printf("\nEq.: (x,y)=(%d,%d)", ponto_x, ponto_y);
    }

    public void createFile() {

        try (PrintStream out = new PrintStream("saida.csv")) {
            NumberFormat nf = NumberFormat.getInstance();
            out.print(matrix_esparca.toCSV(nf));
            System.out.print("\nSaida txt");
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
        try (PrintStream out = new PrintStream("vetor.csv")) {
            NumberFormat nf = NumberFormat.getInstance();
            out.print(resultados.toCSV(nf));
            System.out.print("\nResultado: vetor.txt");
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Insere todas as condições do vertice
     *
     * @param matriz_interpolacao
     * @param ponto_x
     * @param ponto_y
     */
    private void vertice(int ponto_x, int ponto_y) {
        function(ponto_x, ponto_y, equation.function);
        indice_linha++;
        function(ponto_x, ponto_y, equation.function_x);
        indice_linha++;
        function(ponto_x, ponto_y, equation.function_y);
        indice_linha++;
        function(ponto_x, ponto_y, equation.function_xy);
        indice_linha++;
    }

    private void aresta(int ponto_x, int ponto_y) {
        function(ponto_x, ponto_y, equation.function);
        indice_linha++;
        function(ponto_x, ponto_y, equation.function_x);
        indice_linha++;
        function(ponto_x, ponto_y, equation.function_y);
        indice_linha++;
        function(ponto_x, ponto_y, equation.function_xx);
        indice_linha++;
        function(ponto_x, ponto_y, equation.function_yy);
        indice_linha++;
        function(ponto_x, ponto_y, equation.function_xy);
        indice_linha++;
        //PENDING
        function(ponto_x, ponto_y, equation.function);
        if (ponto_x == 0 || ponto_x == linha) {
            function(ponto_x, ponto_y, equation.function, regiao_linha + 1, regiao_coluna, true);
            indice_linha++;
            function(ponto_x, ponto_y, equation.function_x);
            function(ponto_x, ponto_y, equation.function_x, regiao_linha + 1, regiao_coluna, true);
        } else {
            function(ponto_x, ponto_y, equation.function, regiao_linha, regiao_coluna + 1, true);
            indice_linha++;
            function(ponto_x, ponto_y, equation.function_y);
            function(ponto_x, ponto_y, equation.function_y, regiao_linha, regiao_coluna + 1, true);
        }
        indice_linha++;
    }

    private void interno(int ponto_x, int ponto_y) {
        function(ponto_x, ponto_y, equation.function);

        function(ponto_x, ponto_y, equation.function_x);
        function(ponto_x, ponto_y, equation.function_y);
        function(ponto_x, ponto_y, equation.function_xx);
        function(ponto_x, ponto_y, equation.function_yy);
    }

    /**
     * Insere os valores de A*x*y na matriz
     *
     * @param matriz_interpolacao
     * @param intervalo_x
     * @param intervalo_y
     * @param tipo
     */
    private void function(int intervalo_x, int intervalo_y, equation tipo) {
        function(intervalo_x, intervalo_y, tipo, regiao_linha, regiao_coluna, false);
    }

    private void function(int intervalo_x, int intervalo_y, equation tipo, int regiao_x, int regiao_y, boolean negativo) {
        double valor_matriz = 0,
                valor_vetor = get(intervalo_x, intervalo_y);
        int num_regioes_coluna = coluna - 1;

        for (int i = 0; i <= 3; i++) {
            for (int j = 0; j <= 3; j++) {
                indice_coluna = 16 * (num_regioes_coluna * regiao_x + regiao_y) + 4 * i + j;

                switch (tipo) {
                    case function:
                        valor_matriz = pow(intervalo_x, i) * pow(intervalo_y, j);
                        break;
                    case function_x:
                        valor_matriz = intervalo_x * pow(intervalo_x - 1, i) * pow(intervalo_y, j);
                        break;
                    case function_y:
                        valor_matriz = intervalo_y * pow(intervalo_x, i) * pow(intervalo_y - 1, j);
                        break;
                    case function_xy:
                        valor_matriz = intervalo_x * intervalo_y * pow(intervalo_x - 1, i) * pow(intervalo_y - 1, j);
                        break;
                    case function_xx:
                        valor_matriz = intervalo_x * (intervalo_x - 1) * pow(intervalo_x - 2, i) * pow(intervalo_y, j);
                        break;
                    case function_yy:
                        valor_matriz = intervalo_y * (intervalo_y - 1) * pow(intervalo_x, i) * pow(intervalo_y - 2, j);
                        break;

                }

                System.out.printf("\nindice_coluna = %d  valor= %.0f regiao_linha = %d regiao_coluna = %d ", indice_coluna, valor_matriz, regiao_x, regiao_y);
                if (negativo) {
                    valor_matriz *= -1;
                    valor_vetor *= -1;
                }
                matrix_esparca.set(indice_linha, indice_coluna, valor_matriz);
            }
        }
        resultados.set(indice_linha, valor_vetor);
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
