package Structure;

import org.la4j.Vector;
import org.la4j.matrix.dense.Basic2DMatrix;

import static java.lang.Math.pow;

public class Matriz extends Basic2DMatrix {

    private double[] value_x;
    private double[] value_y;
    private typePoint[][] type_point;

    public Matriz(int x, int y) {
        super(x, y);

        value_x = new double[x];
        value_y = new double[y];

        buildTypePoint(x, y);

        for (int i = 0; i < x; i++) {
            value_x[i] = i;
        }
        for (int i = 0; i < y; i++) {
            value_y[i] = i;
        }
    }

    public Matriz(double[] x, double[] y) {
        super(x.length, y.length);
        value_x = x;
        value_y = y;

        type_point = new typePoint[x.length][y.length];
        buildTypePoint(x.length, y.length);
    }

    public static double valorZ(Matriz matriz, Vector polinomio, double x,
            double y) {
        double valor = 0;

        int int_X = (int) x;
        int int_Y = (int) y;

        int x_index =
                (!(int_X >= 1)) ? int_X :
                int_X - 1;
        int y_index =
                (!(int_Y >= 1)) ? int_Y :
                int_Y - 1;

        int first_term = 16 * ((matriz.rows() - 1) * x_index + y_index);

        for (int i = 0; i <= 3; i++) {
            for (int j = 0; j <= 3; j++) {
                valor += (pow(x, i)) * (pow(y, j)) *
                         polinomio.get(first_term + (4 * i + j));
            }
        }

        System.out.printf("\nValor(%.2f,%.2f) = %.4f  Regiao X=%d, Y=%d ", x,
                y, valor, x_index, y_index);
        return valor;
    }

    public static double valorZ2(Matriz matriz, Vector polinomio, double x,
            double y) {
        double valor = 0;

        int int_X = (int) x;
        int int_Y = (int) y;

        int x_index = (!(int_X == matriz.rows() - 1)) ? int_X :
                      int_X - 1;
        int y_index = (!(int_Y == matriz.columns() - 1)) ? int_Y :
                      int_Y - 1;

        int first_term = 16 * ((matriz.rows() - 1) * x_index + y_index);

        for (int i = 0; i <= 3; i++) {
            for (int j = 0; j <= 3; j++) {
                valor += (pow(x, i)) * (pow(y, j)) *
                         polinomio.get(first_term + (4 * i + j));
            }
        }

        System.out.printf("\nValor 2 (%.2f,%.2f) = %.4f  Regiao X=%d, Y=%d " +
                          "", x,
                y,
                valor, x_index, y_index);
        return valor;
    }

    public typePoint getType_point(int x, int y) {
        return type_point[x][y];
    }

    private void buildTypePoint(int x, int y) {
        type_point = new typePoint[x][y];
        for (int row = 0; row < rows(); row++) {
            for (int column = 0; column < columns(); column++) {
                //vertex' point
                if (row == 0 || row == rows() - 1) {
                    if (column == 0 || column == columns() - 1) {
                        type_point[row][column] = typePoint.vertex;
                    } else {
                        type_point[row][column] = typePoint.edge;
                    }
                } else if (column == 0 || column == columns() - 1) {
                    type_point[row][column] = typePoint.edge;
                } else {
                    type_point[row][column] = typePoint.intern;
                }
            }
        }
    }

    @Override
    protected void ensureDimensionsAreCorrect(int rows, int columns) {
        super.ensureDimensionsAreCorrect(rows, columns);
        if (rows < 3 && columns < 3)
            this.fail("The size of rows(" + rows + ") and columns (" +
                      columns + ") must be at the minimum of 3");

    }


    public enum typePoint {
        vertex,
        edge,
        intern
    }
}
