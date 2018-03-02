package Structure;

import org.la4j.Vector;
import org.la4j.matrix.sparse.CRSMatrix;

import static java.lang.Math.pow;

@SuppressWarnings("Duplicates")
public class PolinomioBuilder {

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
     * @param row    row's index
     * @param column column's index
     */
    public void interval(int row, int column) {

        row_region = (row > 0) ? row - 1 : 0;
        col_region = (column > 0) ? column - 1 : 0;

        // Otimizar os métodos interno, vértice e ponto
        switch (matriz.getType_point(row, column)) {
            case vertex:
                vertex(row, column);
//                line += 4;
                break;
            case edge:
                edge(row, column);
//                line += 8;
                break;
            case intern:
                intern(row, column);
//                line += 16;
                break;
        }

    }

    protected void vertex(int row, int column) {
        function(row, column, equation.function);
        line++;
        function(row, column, equation.function_xx);
        line++;
        function(row, column, equation.function_yy);
        line++;
        function(row, column, equation.function_xy);
        line++;
    }

    protected void edge(int row, int column) {
        function(row, column, equation.function);
        line++;
        function(row, column, equation.function_xy);
        line++;
//        function(row, column, equation.function);
        if (row == 0 || row == matriz.rows() - 1) { //X axis
            function(row, column, equation.function, row_region, col_region + 1,
                    false);
            line++;
            //paralela
            function(row, column, equation.function_x);
            function(row, column, equation.function_x, row_region,
                    col_region + 1, true);
            line++;
            function(row, column, equation.function_xx);
            function(row, column, equation.function_xx, row_region,
                    col_region + 1, true);
            line++;
            //perpendicular
            function(row, column, equation.function_y);
            line++;
            function(row, column, equation.function_yy, row_region,
                    col_region + 1, false);
            line++;
            function(row, column, equation.function_xy, row_region,
                    col_region + 1, false);
        } else { //Y axis
            function(row, column, equation.function, row_region + 1, col_region,
                    false);
            line++;
            //paralela
            function(row, column, equation.function_y);
            function(row, column, equation.function_y, row_region + 1,
                    col_region, true);
            line++;
            function(row, column, equation.function_yy);
            function(row, column, equation.function_yy, row_region + 1,
                    col_region, true);
            line++;
            //perpendicular
            function(row, column, equation.function_x);
            line++;
            function(row, column, equation.function_xx, row_region + 1,
                    col_region, false);
            line++;
            function(row, column, equation.function_xy, row_region + 1,
                    col_region, false);
        }
        line++;
    }

    protected void intern(int row, int column) {

        function(row, column, equation.function);
        line++;
        function(row, column, equation.function_xy);
        line++;

        for (int i = 0; i <= 1; i++) {
            for (int j = 0; j <= 1; j++) {
                if (i != 0 && j != 0) {
                    function(row, column, equation.function, row_region + i,
                            col_region + j, true);
                    line++;

                    function(row, column, equation.function_xy, row_region + i,
                            col_region + j, true);
                    line++;
                }
            }
        }

        function(row, column, equation.function_x);
        function(row, column, equation.function_x, row_region + 1, col_region,
                true);
        line++;
        function(row, column, equation.function_x, row_region, col_region + 1,
                false);
        function(row, column, equation.function_x, row_region + 1,
                col_region + 1, true);
        line++;

        function(row, column, equation.function_y);
        function(row, column, equation.function_y, row_region + 1, col_region,
                true);
        line++;
        function(row, column, equation.function_y, row_region, col_region + 1,
                false);
        function(row, column, equation.function_y, row_region + 1,
                col_region + 1, true);
        line++;

        function(row, column, equation.function_xx);
        function(row, column, equation.function_xx, row_region + 1, col_region,
                true);
        line++;
        function(row, column, equation.function_xx, row_region, col_region + 1,
                false);
        function(row, column, equation.function_xx, row_region + 1,
                col_region + 1, true);
        line++;

        function(row, column, equation.function_yy);
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
    protected void function(int intervalo_x, int intervalo_y, equation tipo) {
        function(intervalo_x, intervalo_y, tipo, row_region, col_region,
                false);
    }

    //TODO: Resolver Function para derivada segunda
    protected void function(int intervalo_x, int intervalo_y, equation tipo,
            int x_region, int y_region, boolean negative) {
        double matriz_value,

                //Get values from function, if is not a derivate
                value_vector =
                        (tipo == equation.function) ? matriz.get(intervalo_x,
                                intervalo_y) : 0;

        int num_regioes_coluna = matriz.columns() - 1;

        for (int i = 0; i <= 3; i++) {
            for (int j = 0; j <= 3; j++) {
                matriz_value = 0;
                int column = 16 * (num_regioes_coluna * x_region + y_region) +
                             4 * i + j;

//              intervalo_x,
                double delta_x =
                        (intervalo_x == x_region) ? 0 : intervalo_x - x_region,
//              intervalo_y;
                        delta_y =
                                (intervalo_y == y_region) ? 0 :
                                intervalo_y - y_region;

                //noinspection Duplicates
                switch (tipo) {
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

                System.out.printf(
                        "\ncolumn = %d  valor= %.0f row_region = %d col_region = %d ",
                        column, matriz_value, x_region, y_region);
                if (negative) matriz_value *= -1;
                sparce_matriz.set(line, column, matriz_value);
            }
        }
        if (negative) value_vector *= -1;

        //trocar array abaixo value_b -> results
        value_vector += value_b.get(line);// increment the value
        value_b.set(line, value_vector);// Update
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