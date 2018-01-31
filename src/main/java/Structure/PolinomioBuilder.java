package Structure;

import org.la4j.LinearAlgebra;
import org.la4j.Vector;
import org.la4j.linear.LinearSystemSolver;
import org.la4j.matrix.sparse.CRSMatrix;

import static java.lang.Math.pow;

@SuppressWarnings("Duplicates")
public class PolinomioBuilder {

    private Vector value_b; // vetor de value_b
    private CRSMatrix sparce_matriz;
    private int row_aux,
            col_aux,
            line,
            column,
            row_region,
            col_region;
    private Matriz matriz;

    public PolinomioBuilder(Matriz matriz) {
        this.matriz = matriz;
    }

    public Vector buildPoliniomio(Vector results) {

        sparce_matriz = new CRSMatrix(matriz.rows(), matriz.columns());
        Vector result;

        for (int row = 0; row < matriz.rows(); row++) {
            //TODO: Analisar intervalos em MatrizInArray
            for (int column = 0; column < matriz.columns(); column++)
                interval(row, column);
        }

        //TODO: create
//        Pivoting.Complete(sparce_matriz,value_b);

        LinearSystemSolver solver = sparce_matriz.withSolver(
                LinearAlgebra.SolverFactory.JACOBI);
        result = solver.solve(value_b);

        return result;
    }

    /**
     * Define the caracteristics of the point for a futher use of it
     *
     * @param row    row's index
     * @param column column's index
     */
    private void interval(int row, int column) {

        row_region = (row > 0) ? row - 1 : 0;
        col_region = (column > 0) ? column - 1 : 0;

        //TODO: Otimizar os métodos interno, vértice e ponto
        switch (matriz.getType_point(row, column)) {
            case vertex:
                vertex(row, column);
                break;
            case edge:
                edge(row, column);
                break;
            case intern:
                intern(row, column);
                break;
        }

    }

    private void vertex(int row, int column) {
        function(row, column, equation.function);
        line++;
        function(row, column, equation.function_x);
        line++;
        function(row, column, equation.function_y);
        line++;
        function(row, column, equation.function_xy);
        line++;
    }

    private void edge(int row, int column) {
        function(row, column, equation.function);
        line++;
        function(row, column, equation.function_x);
        line++;
        function(row, column, equation.function_y);
        line++;
        function(row, column, equation.function_xx);
        line++;
        function(row, column, equation.function_yy);
        line++;
        function(row, column, equation.function_xy);
        line++;
        //PENDING
        function(row, column, equation.function);
        if (row == 0 || row == matriz.rows() - 1) {
            function(row, column, equation.function, row_region, col_region + 1, true);
            line++;
            function(row, column, equation.function_y);
            function(row, column, equation.function_y, row_region, col_region + 1, true);
        } else {
            function(row, column, equation.function, row_region + 1, col_region, true);
            line++;
            function(row, column, equation.function_x);
            function(row, column, equation.function_x, row_region + 1, col_region, true);
        }
        line++;
    }

    private void intern(int row, int column) {
        function(row, column, equation.function);
        line++;
        for (int i = 0; i <= 1; i++) {
            for (int j = 0; j <= 1; j++) {
                if (i != 0 || j != 0) {
                    function(row, column, equation.function);
                    function(row, column, equation.function, row_region + i, col_region + j, true);
                    line++;

                    function(row, column, equation.function_x);
                    function(row, column, equation.function_x, row_region + i, col_region + j, true);
                    line++;

                    function(row, column, equation.function_y);
                    function(row, column, equation.function_y, row_region + i, col_region + j, true);
                    line++;

                    function(row, column, equation.function_xx);
                    function(row, column, equation.function_xx, row_region + i, col_region + j, true);
                    line++;

                    function(row, column, equation.function_yy);
                    function(row, column, equation.function_yy, row_region + i, col_region + j, true);
                    line++;
                }
            }
        }
    }

    /**
     * Insere os valores de A*x*y na matriz
     */
    private void function(int intervalo_x, int intervalo_y, equation tipo) {
        function(intervalo_x, intervalo_y, tipo, row_region, col_region,
                false);
    }

    //TODO: Resolver Function
    private void function(int intervalo_x, int intervalo_y, equation tipo,
            int x_region, int y_region, boolean negative) {
        double valor_matriz,

                //Get values from function, if is not a derivate
                valor_vetor =
                        (tipo == equation.function) ? matriz.get(intervalo_x,
                                intervalo_y) : 0;

        int num_regioes_coluna = matriz.columns() - 1;

        for (int i = 0; i <= 3; i++) {
            for (int j = 0; j <= 3; j++) {
                valor_matriz = 0;
                column = 16 * (num_regioes_coluna * x_region + y_region) +
                         4 * i + j;

                double delta_x =
//                        intervalo_x,
                        (intervalo_x == x_region) ? 0 : intervalo_x - x_region,
                        delta_y =
//                        intervalo_y;
                                (intervalo_y == y_region) ? 0 :
                                intervalo_y - y_region;

                //noinspection Duplicates
                switch (tipo) {
                    case function:
                        valor_matriz = pow(delta_x, i) * pow(delta_y, j);
                        break;
                    case function_x:
                        if (i > 0) {
                            valor_matriz =
                                    i * pow(delta_x, i - 1) * pow(delta_y, j);
                        }
                        break;
                    case function_y:
                        if (j > 0) {
                            valor_matriz =
                                    j * pow(delta_x, i) * pow(delta_y, j - 1);
                        }
                        break;
                    case function_xy:
                        if (i > 0 && j > 0) {
                            valor_matriz = i * j * pow(delta_x, i - 1) *
                                           pow(delta_y, j - 1);
                        }
                        break;
                    case function_xx:
                        if (i > 1) {
                            valor_matriz = i * (i - 1) * pow(delta_x, i - 2) *
                                           pow(delta_y, j);
                        }
                        break;
                    case function_yy:
                        if (j > 1) {
                            valor_matriz = j * (j - 1) * pow(delta_x, i) *
                                           pow(delta_y, j - 2);
                        }
                        break;

                }

                System.out.printf(
                        "\ncolumn = %d  valor= %.0f row_region = %d col_region = %d ",
                        column, valor_matriz, x_region, y_region);
                if (negative) {
                    valor_matriz *= -1;
                }
                sparce_matriz.set(line, column, valor_matriz);
            }
        }
        if (negative) {
            valor_vetor *= -1;
        }
        //TODO: trocar array abaixo value_b -> results
        valor_vetor += value_b.get(line);
        value_b.set(line, valor_vetor);
    }


    private enum equation {
        function,
        function_x,
        function_y,
        function_xy,
        function_xx,
        function_yy
    }

}
