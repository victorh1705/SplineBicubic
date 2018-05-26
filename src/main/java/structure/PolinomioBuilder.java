package structure;

import org.la4j.Vector;
import org.la4j.matrix.sparse.CRSMatrix;
import type.equation;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.pow;

public class PolinomioBuilder {

    public static final equation FUNCTION = equation.FUNCTION;
    public static final List<equation> FIRST_DERIVATIVE = new ArrayList<equation>() {{
        add(equation.FUNCTION_X);
        add(equation.FUNCTION_Y);
    }};
    public static final List<equation> SECOND_DERIVATIVE = new ArrayList<equation>
            () {{
        add(equation.FUNCTION_XX);
        add(equation.FUNCTION_XY);
        add(equation.FUNCTION_YY);
    }};
    protected Vector value_b; // vetor de value_b
    protected CRSMatrix sparce_matriz;
    protected int line;
    protected int row_region;
    protected int col_region;
    protected Matriz matriz;

    public PolinomioBuilder(Matriz matriz) {
        this.matriz = matriz;
    }

    public Vector getValue_b() {
        return value_b;
    }

    public CRSMatrix getSparce_matriz() {
        return sparce_matriz;
    }

    /**
     * Define the caracteristics of the point for a futher use of it
     *
     * @param column VERTICAL's index
     * @param row    HORIZONTAL's index
     */
    public void interval(int column, int row) {

        row_region = (row > 0) ? row - 1 : 0;
        col_region = (column > 0) ? column - 1 : 0;

        // Otimizar os métodos interno, vértice e ponto
        switch (matriz.getType_point(row, column)) {
            case vertex:
                vertex(column, row);
//                line += 4;
                break;
            case edge:
                edge(column, row);
//                line += 8;
                break;
            case intern:
                intern(column, row);
//                line += 16;
                break;
        }

    }

    protected void vertex(int column, int row) {
        function(column, row, equation.FUNCTION);
        line++;
        function(column, row, equation.FUNCTION_XX);
        line++;
        function(column, row, equation.FUNCTION_YY);
        line++;
        function(column, row, equation.FUNCTION_XY);
        line++;
    }

    protected void edge(int column, int row) {
        function(column, row, equation.FUNCTION);
        line++;
        function(column, row, equation.FUNCTION_XY);
        line++;
        if (row == 0 || row == matriz.rows() - 1) { //X axis
            function(row, column, equation.FUNCTION, row_region, col_region + 1,
                    false);
            line++;
            //paralela
            function(column, row, equation.FUNCTION_Y);
            function(row, column, equation.FUNCTION_Y, row_region,
                    col_region + 1, true);
            line++;
            function(column, row, equation.FUNCTION_YY);
            function(row, column, equation.FUNCTION_YY, row_region,
                    col_region + 1, true);
            line++;
            //perpendicular
            function(column, row, equation.FUNCTION_X);
            line++;
            function(row, column, equation.FUNCTION_XX, row_region,
                    col_region + 1, false);
            line++;
            function(row, column, equation.FUNCTION_XY, row_region,
                    col_region + 1, false);
        } else { //Y axis
            function(row, column, equation.FUNCTION, row_region + 1, col_region,
                    false);
            line++;
            //paralela
            function(column, row, equation.FUNCTION_X);
            function(row, column, equation.FUNCTION_X, row_region + 1,
                    col_region, true);
            line++;
            function(column, row, equation.FUNCTION_XX);
            function(row, column, equation.FUNCTION_XX, row_region + 1,
                    col_region, true);
            line++;
            //perpendicular
            function(column, row, equation.FUNCTION_Y);
            line++;
            function(row, column, equation.FUNCTION_YY, row_region + 1,
                    col_region, false);
            line++;
            function(row, column, equation.FUNCTION_XY, row_region + 1,
                    col_region, false);

        }
        line++;
    }

    protected void intern(int column, int row) {

        function(column, row, equation.FUNCTION);
        line++;
        function(column, row, equation.FUNCTION_XY);
        line++;

        for (int i = 0; i <= 1; i++) {
            for (int j = 0; j <= 1; j++) {
                if (i != 0 || j != 0) {
                    function(row, column, equation.FUNCTION, row_region + i,
                            col_region + j, false);
                    line++;

                    function(row, column, equation.FUNCTION_XY, row_region + i,
                            col_region + j, false);
                    line++;
                }
            }
        }

        function(column, row, equation.FUNCTION_X);
        function(row, column, equation.FUNCTION_X, row_region + 1, col_region,
                true);
        line++;
        function(row, column, equation.FUNCTION_X, row_region, col_region + 1,
                false);
        function(row, column, equation.FUNCTION_X, row_region + 1,
                col_region + 1, true);
        line++;

        function(column, row, equation.FUNCTION_Y);
        function(row, column, equation.FUNCTION_Y, row_region + 1, col_region,
                true);
        line++;
        function(row, column, equation.FUNCTION_Y, row_region, col_region + 1,
                false);
        function(row, column, equation.FUNCTION_Y, row_region + 1,
                col_region + 1, true);
        line++;

        function(column, row, equation.FUNCTION_XX);
        function(row, column, equation.FUNCTION_XX, row_region + 1, col_region,
                true);
        line++;
        function(row, column, equation.FUNCTION_XX, row_region, col_region + 1,
                false);
        function(row, column, equation.FUNCTION_XX, row_region + 1,
                col_region + 1, true);
        line++;

        function(column, row, equation.FUNCTION_YY);
        function(row, column, equation.FUNCTION_YY, row_region + 1, col_region,
                true);
        line++;
        function(row, column, equation.FUNCTION_YY, row_region, col_region + 1,
                false);
        function(row, column, equation.FUNCTION_YY, row_region + 1,
                col_region + 1, true);
        line++;

    }

    /**
     * Insere os valores de A*x*y na matriz
     */
    protected void function(int intervalo_x, int intervalo_y,
            equation tipo) {
        //Todo: Invertido x e y
        function(intervalo_y, intervalo_x, tipo, row_region, col_region,
                false);
    }

    protected void function(int intervalo_x, int intervalo_y,
            equation type, int x_region, int y_region, boolean negative) {

        int num_cols_regions = matriz.rows() - 1;

        for (int i = 0; i <= 3; i++) {
            for (int j = 0; j <= 3; j++) {
                double matriz_value = 0;
                int column = 16 *
                             (num_cols_regions * x_region + y_region) +
                             4 * i + j;

//              intervalo_x,
                double delta_x = intervalo_x;
//                        (intervalo_x == x_region) ? 0 : intervalo_x - x_region,
//              intervalo_y;
                double delta_y = intervalo_y;
//                                (intervalo_y == y_region) ? 0 :
//                                intervalo_y - y_region;

                //noinspection Duplicates
                switch (type) {
                    case FUNCTION:
                        matriz_value = pow(delta_x, i) * pow(delta_y, j);
                        break;
                    case FUNCTION_X:
                        if (i > 0) {
                            matriz_value =
                                    i * pow(delta_x, i - 1) * pow(delta_y, j);
                        }
                        break;
                    case FUNCTION_Y:
                        if (j > 0) {
                            matriz_value =
                                    j * pow(delta_x, i) * pow(delta_y, j - 1);
                        }
                        break;
                    case FUNCTION_XY:
                        if (i > 0 && j > 0) {
                            matriz_value = i * j * pow(delta_x, i - 1) *
                                           pow(delta_y, j - 1);
                        }
                        break;
                    case FUNCTION_XX:
                        if (i > 1) {
                            matriz_value = i * (i - 1) * pow(delta_x, i - 2) *
                                           pow(delta_y, j);
                        }
                        break;
                    case FUNCTION_YY:
                        if (j > 1) {
                            matriz_value = j * (j - 1) * pow(delta_x, i) *
                                           pow(delta_y, j - 2);
                        }
                        break;

                }

//                System.out.printf(
//                        "\ncolumn = %d  valor= %.0f row_region = %d col_region = %d ",
//                        VERTICAL, matriz_value, x_region, y_region);
                if (negative) matriz_value *= -1;
                sparce_matriz.set(line, column, matriz_value);
            }
        }

        //trocar array abaixo value_b -> results
        double value = CalculateValueB(negative, type, intervalo_x,
                intervalo_y);
        value_b.set(line, value);
    }


    private double CalculateValueB(boolean negative, equation type,
            int x, int y) {
        double retorno = 0;


        if (FUNCTION.equals(type)) {
            return matriz.get(x, y);
        } else if (FIRST_DERIVATIVE.contains(type)) {
            //return Derivative.firstDerivative(negative, x, y, type,matriz);
        } else if (SECOND_DERIVATIVE.contains(type)) {
            // return Derivative.secondDerivative(negative, x, y, type,matriz);
        }
        return retorno;
    }

}
