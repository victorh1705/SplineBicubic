package Structure;

import org.la4j.matrix.dense.Basic2DMatrix;

public class Matriz extends Basic2DMatrix {

    public enum typePoint {
        vertex,
        edge,
        intern
    }

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
        buildTypePoint(x.length,y.length);
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
}
