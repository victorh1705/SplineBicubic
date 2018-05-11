package structure;

import library.derivative.firstDerivative.FirstDerivative;
import org.la4j.Vector;
import org.la4j.matrix.dense.Basic2DMatrix;
import type.equation;

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

    /**
     * Equivalent to get(row,line) or super.get(line,row)
     *
     * @param x row
     * @param y line
     * @return super.get(y, x)
     */
    public double getValue(int x, int y) {
        return super.get(y, x);
    }

    /**
     * Equivalent to set(row,line) or super.set(line,row)
     *
     * @param x     row
     * @param y     line
     * @param value
     */
    public void setValue(int x, int y, double value) {
        super.set(y, x, value);
    }

    /**
     * Get the value related to the <b><i>x</i></b> axis
     *
     * @param index
     * @return
     */
    public double get_x(int index) {
        return value_x[index];
    }

    /**
     * Get the value related to the <b><i>y</i></b> axis
     *
     * @param index
     * @return
     */
    public double get_y(int index) {
        return value_y[index];
    }


    /**
     * Get the type(typePoint) of a point's matrix
     *
     * @param x
     * @param y
     * @return
     */
    public typePoint getType_point(int x, int y) {
        return type_point[x][y];
    }

    /**
     * Set the tyoe(typePoint) of every point's matrix
     *
     * @param x
     * @param y
     */
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

    /**
     * Calculate a derivate of a point
     *
     * @param x
     * @param y
     * @param type type of derivate
     * @return
     */
    public double derivate(int x, int y, equation type) {
        switch (type) {
            case function_x:
                return new FirstDerivative(this).derivate(x, y,
                        type);
            case function_y:
                return new FirstDerivative(this).derivate(x, y,
                        type);
            case function_xy:
                break;
            case function_xx:
                break;
            case function_yy:
                break;
        }
        fail("Illegal Equation type:" + type);
        return 0;
    }

    /**
     * Ensure if the matriz has the minimum valid values
     *
     * @param rows
     * @param columns
     */
    @Override
    protected void ensureDimensionsAreCorrect(int rows, int columns) {
        super.ensureDimensionsAreCorrect(rows, columns);
        if (rows < 3 && columns < 3)
            this.fail("The size of rows(" + rows + ") and columns (" +
                      columns + ") must be at the minimum of 3");

    }

    /**
     * It defines if the point is a <i>vertex</i> || <i> edge</i> ||
     * <i>intern</i>
     */
    public enum typePoint {
        vertex,
        edge,
        intern
    }
}
