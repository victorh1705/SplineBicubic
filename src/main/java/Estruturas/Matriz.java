package Estruturas;

import org.la4j.matrix.dense.Basic2DMatrix;

public class Matriz extends Basic2DMatrix {

    private double[] value_x;
    private double[] value_y;

    public Matriz(int x, int y) {
        super(x, y);

        value_x = new double[x];
        value_y = new double[y];

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
    }


}
