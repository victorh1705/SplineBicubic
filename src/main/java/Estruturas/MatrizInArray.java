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
import org.la4j.LinearAlgebra;
import org.la4j.Vector;
import org.la4j.linear.LinearSystemSolver;
import org.la4j.matrix.sparse.CRSMatrix;
import org.la4j.vector.dense.BasicVector;

/**
 *
 * @author voitt
 */
public class MatrizInArray {

    private double[] array;
    private CRSMatrix matrix_esparca;
    private Vector resultados,
            polinomio,
            index_col;
    private int linha,
            coluna,
            linha_aux,
            coluna_aux,
            num_equacao,
            indice_linha,
            indice_coluna,
            regiao_linha,
            regiao_coluna;
    private Ponto tipo_ponto;

    private enum equation {
        function,
        function_x,
        function_y,
        function_xy,
        function_xx,
        function_yy
    }

    private enum Ponto {
        Vertice,
        Aresta,
        Interno
    }

    public MatrizInArray(int linha, int coluna) {
        this.linha = linha;
        this.coluna = coluna;
        this.num_equacao = 16 * (linha - 1) * (coluna - 1);
        this.array = new double[linha * coluna];
        this.indice_linha = 0;
        index_col = new BasicVector(num_equacao);
        for (int i = 0; i < coluna; i++) {
            index_col.set(i, i);
        }
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

        for (linha_aux = 0; linha_aux < linha; linha_aux++) {
            for (coluna_aux = 0; coluna_aux < coluna; coluna_aux++) {
                intervalo(linha_aux, coluna_aux);
            }
        }

        System.out.printf("\nNum_equacao: %d", num_equacao);
        System.out.printf("\nIndices_linha: %d\n", indice_linha);

        pivotamento();
        LinearSystemSolver solver = matrix_esparca.withSolver(LinearAlgebra.SolverFactory.GAUSSIAN);
        polinomio = solver.solve(resultados);
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
            tipo_ponto = Ponto.Vertice;
            vertice(ponto_x, ponto_y);
//            indice_linha += 4;
        } else if (isAresta(ponto_x, ponto_y)) {
            tipo_ponto = Ponto.Aresta;
            aresta(ponto_x, ponto_y);
//            indice_linha += 8;
        } else {
            tipo_ponto = Ponto.Interno;
            interno(ponto_x, ponto_y);
//            indice_linha += 16;
        }

        System.out.printf("\nEq.: (x,y)=(%d,%d)", ponto_x, ponto_y);
    }

    public void createFile() {

        try (PrintStream out = new PrintStream("saida2.csv")) {
            NumberFormat nf = NumberFormat.getInstance();
            out.print(matrix_esparca.toCSV(nf));
            System.out.print("\nSaida txt");
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
        try (PrintStream out = new PrintStream("resultado2.csv")) {
            NumberFormat nf = NumberFormat.getInstance();
            out.print(resultados.toCSV(nf));

            int cont_zeros = 0;
            for (int i = 0; i < resultados.length(); i++) {
                if (resultados.get(i) == 0) {
                    cont_zeros++;
                }
            }
            out.printf("\n quantidade de Zeros : %d \n", cont_zeros);

            System.out.print("\nResultado: resultado.txt");
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
        try (PrintStream out = new PrintStream("polinomio.csv")) {
            NumberFormat nf = NumberFormat.getInstance();
            out.print(polinomio.toCSV(nf));

            System.out.print("\nResultado: polinomio.txt");
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
        function(ponto_x, ponto_y, equation.function_xx);
        indice_linha++;
        function(ponto_x, ponto_y, equation.function_yy);
        indice_linha++;
        function(ponto_x, ponto_y, equation.function_xy);
        indice_linha++;
    }

    private void aresta(int ponto_x, int ponto_y) {
        function(ponto_x, ponto_y, equation.function);
        indice_linha++;
        function(ponto_x, ponto_y, equation.function_xy);
        indice_linha++;
        //PENDING
        function(ponto_x, ponto_y, equation.function);
        if (ponto_x == 0 || ponto_x == linha - 1) {
            function(ponto_x, ponto_y, equation.function, regiao_linha, regiao_coluna + 1, false);
            indice_linha++;
            //paralela
            function(ponto_x, ponto_y, equation.function_y);
            function(ponto_x, ponto_y, equation.function_y, regiao_linha, regiao_coluna + 1, true);
            indice_linha++;
            function(ponto_x, ponto_y, equation.function_yy);
            function(ponto_x, ponto_y, equation.function_yy, regiao_linha, regiao_coluna + 1, true);
            indice_linha++;
            //perpendicular
            function(ponto_x, ponto_y, equation.function_x);
            indice_linha++;
            function(ponto_x, ponto_y, equation.function_xx, regiao_linha, regiao_coluna + 1, false);
            indice_linha++;
            function(ponto_x, ponto_y, equation.function_xy, regiao_linha, regiao_coluna + 1, false);
        } else {
            function(ponto_x, ponto_y, equation.function, regiao_linha + 1, regiao_coluna, false);
            indice_linha++;
            //paralela
            function(ponto_x, ponto_y, equation.function_x);
            function(ponto_x, ponto_y, equation.function_x, regiao_linha + 1, regiao_coluna, true);
            indice_linha++;
            function(ponto_x, ponto_y, equation.function_xx);
            function(ponto_x, ponto_y, equation.function_xx, regiao_linha + 1, regiao_coluna, true);
            indice_linha++;
            //perpendicular
            function(ponto_x, ponto_y, equation.function_y);
            indice_linha++;
            function(ponto_x, ponto_y, equation.function_yy, regiao_linha + 1, regiao_coluna, false);
            indice_linha++;
            function(ponto_x, ponto_y, equation.function_xy, regiao_linha + 1, regiao_coluna, false);
        }
        indice_linha++;
    }

    private void interno(int ponto_x, int ponto_y) {
        function(ponto_x, ponto_y, equation.function);
        indice_linha++;
        function(ponto_x, ponto_y, equation.function_xy);
        indice_linha++;
        for (int i = 0; i <= 1; i++) {
            for (int j = 0; j <= 1; j++) {
                if (i != 0 || j != 0) {
                    function(ponto_x, ponto_y, equation.function, regiao_linha + i, regiao_coluna + j, false);
                    indice_linha++;

                    function(ponto_x, ponto_y, equation.function_xy, regiao_linha + i, regiao_coluna + j, false);
                    indice_linha++;
                }
            }
        }

        function(ponto_x, ponto_y, equation.function_x);
        function(ponto_x, ponto_y, equation.function_x, regiao_linha + 1, regiao_coluna, true);
        indice_linha++;
        function(ponto_x, ponto_y, equation.function_x, regiao_linha, regiao_coluna + 1, false);
        function(ponto_x, ponto_y, equation.function_x, regiao_linha + 1, regiao_coluna + 1, true);
        indice_linha++;

        function(ponto_x, ponto_y, equation.function_y);
        function(ponto_x, ponto_y, equation.function_y, regiao_linha + 1, regiao_coluna, true);
        indice_linha++;
        function(ponto_x, ponto_y, equation.function_y, regiao_linha, regiao_coluna + 1, false);
        function(ponto_x, ponto_y, equation.function_y, regiao_linha + 1, regiao_coluna + 1, true);
        indice_linha++;

        function(ponto_x, ponto_y, equation.function_xx);
        function(ponto_x, ponto_y, equation.function_xx, regiao_linha + 1, regiao_coluna, true);
        indice_linha++;
        function(ponto_x, ponto_y, equation.function_xx, regiao_linha, regiao_coluna + 1, false);
        function(ponto_x, ponto_y, equation.function_xx, regiao_linha + 1, regiao_coluna + 1, true);
        indice_linha++;

        function(ponto_x, ponto_y, equation.function_yy);
        function(ponto_x, ponto_y, equation.function_yy, regiao_linha + 1, regiao_coluna, true);
        indice_linha++;
        function(ponto_x, ponto_y, equation.function_yy, regiao_linha, regiao_coluna + 1, false);
        function(ponto_x, ponto_y, equation.function_yy, regiao_linha + 1, regiao_coluna + 1, true);
        indice_linha++;
    }

    /**
     * Insere os valores de A*x*y na matriz
     *
     * @param matriz_interpolacao
     * @param intervalo_x
     * @param intervalo_y
     * @param equacao
     */
    private void function(int intervalo_x, int intervalo_y, equation equacao) {
        function(intervalo_x, intervalo_y, equacao, regiao_linha, regiao_coluna, false);
    }

    private void function(int intervalo_x, int intervalo_y, equation equacao, int regiao_x, int regiao_y, boolean negativo) {
        double valor_matriz;
        int num_regioes_coluna = coluna - 1;

        for (int i = 0; i <= 3; i++) {
            for (int j = 0; j <= 3; j++) {
                valor_matriz = 0;
                indice_coluna = 16 * (num_regioes_coluna * regiao_x + regiao_y) + 4 * i + j;

                double delta_x
                        = intervalo_x,
                        //                        (intervalo_x == regiao_x) ? 0 : intervalo_x - regiao_x,
                        delta_y
                        = intervalo_y;
//                        (intervalo_y == regiao_y) ? 0 : intervalo_y - regiao_y;

                switch (equacao) {
                    case function:
                        valor_matriz = pow(delta_x, i) * pow(delta_y, j);
                        break;
                    case function_x:
                        if (i > 0) {
                            valor_matriz = i * pow(delta_x, i - 1) * pow(delta_y, j);
                        }
                        break;
                    case function_y:
                        if (j > 0) {
                            valor_matriz = j * pow(delta_x, i) * pow(delta_y, j - 1);
                        }
                        break;
                    case function_xy:
                        if (i > 0 && j > 0) {
                            valor_matriz = i * j * pow(delta_x, i - 1) * pow(delta_y, j - 1);
                        }
                        break;
                    case function_xx:
                        if (i > 1) {
                            valor_matriz = i * (i - 1) * pow(delta_x, i - 2) * pow(delta_y, j);
                        }
                        break;
                    case function_yy:
                        if (j > 1) {
                            valor_matriz = j * (j - 1) * pow(delta_x, i) * pow(delta_y, j - 2);
                        }
                        break;

                }

                System.out.printf("\nindice_coluna = %d  valor= %.0f regiao_linha = %d regiao_coluna = %d ", indice_coluna, valor_matriz, regiao_x, regiao_y);
                if (negativo) {
                    valor_matriz *= -1;
                }
                matrix_esparca.set(indice_linha, indice_coluna, valor_matriz);
            }
        }

        double valor_vetor = CalcularValorVetor(negativo, equacao, intervalo_x, intervalo_y);
        resultados.set(indice_linha, valor_vetor);
    }

    private double CalcularValorVetor(boolean negativo, equation equacao, int intervalo_x, int intervalo_y) {
        double retorno = 0;
        if (equacao == equation.function) {
            retorno = get(intervalo_x, intervalo_y);
        } else if (equacao == equation.function_xy) {
            if (null != tipo_ponto) {
                int x_maior = (intervalo_x < coluna - 1) ? intervalo_x + 1 : intervalo_x,
                        x_menor = (intervalo_x > 0) ? intervalo_x - 1 : 0,
                        y_maior = (intervalo_y < linha - 1) ? intervalo_y + 1 : intervalo_y,
                        y_menor = (intervalo_y > 0) ? intervalo_y - 1 : 0;

                retorno += get(x_maior, y_maior);
                retorno += get(x_menor, y_menor);
                retorno -= get(x_maior, y_menor);
                retorno -= get(x_menor, y_maior);
                retorno = retorno / (pow(1, 2));
                if (tipo_ponto==Ponto.Aresta) {
                    retorno = retorno/2;
                }

            }

            if (negativo) {
                retorno *= -1;
            }

            //soma ao valor ja existente 
            retorno += resultados.get(indice_linha);
            return retorno;
        }
        return retorno;
    }

    private void pivotamento() {
//        pivotamentoSimples(false);
        pivoteamentoParcial();
//        pivoteamentoCompleto();
    }

    private void pivotamentoSimples(boolean invertido) {
        if (invertido) {
            for (int i = num_equacao - 1; i >= 0; i--) {
                if (matrix_esparca.get(i, i) == 0) {
                    for (int j = i; j >= 0; j--) {
                        if (matrix_esparca.get(j, i) != 0) {
                            matrix_esparca.swapRows(i, j);
                            resultados.swapElements(i, j);
                            break;
                        }
                    }
                }
            }
        } else {
            for (int i = 0; i < num_equacao; i++) {
                if (matrix_esparca.get(i, i) == 0) {
                    for (int j = i; j < num_equacao; j++) {
                        if (matrix_esparca.get(j, i) != 0) {
                            matrix_esparca.swapRows(i, j);
                            resultados.swapElements(i, j);
                            break;
                        }
                    }
                }
            }
        }
    }

    private void pivoteamentoParcial() {
        for (int i = 0; i < matrix_esparca.rows(); i++) {
            int linha_index = i;
            for (int j = i; j < matrix_esparca.rows(); j++) {
                if (matrix_esparca.get(j, i) != 0 && matrix_esparca.get(j, i) > matrix_esparca.get(i, i)) {
                    linha_index = j;
                }
            }
            matrix_esparca.swapRows(i, linha_index);
            resultados.swapElements(i, linha_index);
        }
    }

    private void pivoteamentoCompleto() {
        for (int i = 0; i < matrix_esparca.rows(); i++) {
            int linha_index = i,
                    coluna_index = i;
            for (int j = i; j < matrix_esparca.rows(); j++) {
                for (int k = i; k < matrix_esparca.columns(); k++) {
                    if (matrix_esparca.get(j, k) != 0) {
                        if (matrix_esparca.get(j, k) > matrix_esparca.get(linha_index, coluna_index)) {
                            linha_index = j;
                            coluna_index = k;
                        } else if (matrix_esparca.get(linha_index, coluna_index) == 0) {
                            linha_index = j;
                            coluna_index = k;
                        }
                    }
                }
            }
            matrix_esparca.swapRows(i, linha_index);
            resultados.swapElements(i, linha_index);

            matrix_esparca.swapColumns(i, coluna_index);
            index_col.swapElements(i, coluna_index);
        }
    }

    public double valorZ(double x, double y) {
        double valor = 0;

        int indice_x = (int) (x);
        int indice_y = (int) (y);

        int inicio = 16 * ((linha - 1) * indice_x + indice_y);

        for (int i = 0; i <= 3; i++) {
            for (int j = 0; j <= 3; j++) {
                valor += (pow(x, i)) * (pow(y, j)) * polinomio.get(inicio + (4 * i + j));
            }
        }

        System.out.printf("\nValor(%.2f,%.2f) = %.2f", x, y, valor);
        return valor;
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
