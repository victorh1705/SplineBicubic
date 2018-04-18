package Structure;

import org.la4j.Vector;
import org.la4j.matrix.sparse.CRSMatrix;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.pow;

@SuppressWarnings("Duplicates")
public class PolinomioBuilder {

    public static final equation FUNCTION = equation.function;
    public static final List<equation> FIRST_DERIVATIVE = new ArrayList<equation>() {{
        add(equation.function_x);
        add(equation.function_y);
    }};
    public static final List<equation> SECOND_DERIVATIVE = new ArrayList<equation>
            () {{
        add(equation.function_xx);
        add(equation.function_xy);
        add(equation.function_yy);
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
     * @param column column's index
     * @param row    row's index
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
        function(column, row, equation.function);
        line++;
        function(column, row, equation.function_xx);
        line++;
        function(column, row, equation.function_yy);
        line++;
        function(column, row, equation.function_xy);
        line++;
    }

    protected void edge(int column, int row) {
        function(column, row, equation.function);
        line++;
        function(column, row, equation.function_xy);
        line++;
        if (row == 0 || row == matriz.rows() - 1) { //X axis
            function(row, column, equation.function, row_region, col_region + 1,
                    false);
            line++;
            //paralela
            function(column, row, equation.function_y);
            function(row, column, equation.function_y, row_region,
                    col_region + 1, true);
            line++;
            function(column, row, equation.function_yy);
            function(row, column, equation.function_yy, row_region,
                    col_region + 1, true);
            line++;
            //perpendicular
            function(column, row, equation.function_x);
            line++;
            function(row, column, equation.function_xx, row_region,
                    col_region + 1, false);
            line++;
            function(row, column, equation.function_xy, row_region,
                    col_region + 1, false);
        } else { //Y axis
            function(row, column, equation.function, row_region + 1, col_region,
                    false);
            line++;
            //paralela
            function(column, row, equation.function_x);
            function(row, column, equation.function_x, row_region + 1,
                    col_region, true);
            line++;
            function(column, row, equation.function_xx);
            function(row, column, equation.function_xx, row_region + 1,
                    col_region, true);
            line++;
            //perpendicular
            function(column, row, equation.function_y);
            line++;
            function(row, column, equation.function_yy, row_region + 1,
                    col_region, false);
            line++;
            function(row, column, equation.function_xy, row_region + 1,
                    col_region, false);

        }
        line++;
    }

    protected void intern(int column, int row) {

        function(column, row, equation.function);
        line++;
        function(column, row, equation.function_xy);
        line++;

        for (int i = 0; i <= 1; i++) {
            for (int j = 0; j <= 1; j++) {
                if (i != 0 || j != 0) {
                    function(row, column, equation.function, row_region + i,
                            col_region + j, false);
                    line++;

                    function(row, column, equation.function_xy, row_region + i,
                            col_region + j, false);
                    line++;
                }
            }
        }

        function(column, row, equation.function_x);
        function(row, column, equation.function_x, row_region + 1, col_region,
                true);
        line++;
        function(row, column, equation.function_x, row_region, col_region + 1,
                false);
        function(row, column, equation.function_x, row_region + 1,
                col_region + 1, true);
        line++;

        function(column, row, equation.function_y);
        function(row, column, equation.function_y, row_region + 1, col_region,
                true);
        line++;
        function(row, column, equation.function_y, row_region, col_region + 1,
                false);
        function(row, column, equation.function_y, row_region + 1,
                col_region + 1, true);
        line++;

        function(column, row, equation.function_xx);
        function(row, column, equation.function_xx, row_region + 1, col_region,
                true);
        line++;
        function(row, column, equation.function_xx, row_region, col_region + 1,
                false);
        function(row, column, equation.function_xx, row_region + 1,
                col_region + 1, true);
        line++;

        function(column, row, equation.function_yy);
        function(row, column, equation.function_yy, row_region + 1, col_region,
                true);
        line++;
        function(row, column, equation.function_yy, row_region, col_region + 1,
                false);
        function(row, column, equation.function_yy, row_region + 1,
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
                int column = 16 * (num_cols_regions * x_region + y_region) +
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
                    case function:
                        matriz_value = pow(delta_x, i) * pow(delta_y, j);
                        break;
                    case function_x:
                        if (i > 0) {
                            matriz_value =
                                    i * pow(delta_x, i - 1) * pow(delta_y, j);
                        }
                        break;
                    case function_y:
                        if (j > 0) {
                            matriz_value =
                                    j * pow(delta_x, i) * pow(delta_y, j - 1);
                        }
                        break;
                    case function_xy:
                        if (i > 0 && j > 0) {
                            matriz_value = i * j * pow(delta_x, i - 1) *
                                           pow(delta_y, j - 1);
                        }
                        break;
                    case function_xx:
                        if (i > 1) {
                            matriz_value = i * (i - 1) * pow(delta_x, i - 2) *
                                           pow(delta_y, j);
                        }
                        break;
                    case function_yy:
                        if (j > 1) {
                            matriz_value = j * (j - 1) * pow(delta_x, i) *
                                           pow(delta_y, j - 2);
                        }
                        break;

                }

//                System.out.printf(
//                        "\ncolumn = %d  valor= %.0f row_region = %d col_region = %d ",
//                        column, matriz_value, x_region, y_region);
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
            retorno = matriz.get(x, y);
        } else if (SECOND_DERIVATIVE.contains(type)) {
            return secondDerivative(negative, x, y, type);
        }
        return retorno;
    }

    private double secondDerivative(boolean negative, int x, int y,
            equation type) {
        double retorno = 0;

        if (type == equation.function_xy) {
            if (null != matriz.getType_point(x, y)) {
                int x_big = (x < matriz.columns() - 1) ? x + 1 : x,
                        x_small = (x > 0) ? x - 1 : 0,
                        y_big = (y < matriz.columns() - 1) ? y + 1 : y,
                        y_small = (y > 0) ? y - 1 : 0;

                retorno = matriz.get(x_big, y_big) + matriz.get(x_small,
                        y_small) - matriz.get(x_big, y_small) - matriz.get
                        (x_small, y_big);
                retorno = retorno / (pow(1, 2));

                if (matriz.getType_point(x, y) ==
                    Matriz.typePoint.edge) {
                    retorno = retorno / 2;
                }

            }

            if (negative) {
                retorno *= -1;
            }

            //add to the existing value
            retorno += value_b.get(line);
        }
        return retorno;
    }


    protected enum equation {
        function,
        function_x,
        function_y,
        function_xy,
        function_xx,
        function_yy
    }

}
