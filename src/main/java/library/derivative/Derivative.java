package library.derivative;

import structure.Matriz;
import type.equation;

public abstract class Derivative {

    protected int diff_initial = 0;
    protected int diff_middle = 0;
    protected int diff_final = 0;

    protected Matriz matriz = null;

    public Derivative(Matriz matriz) {
        this.matriz = matriz;
    }

    /**
     * Return the <i>UPPER</i> or <i>DOWNER</i> difference between the axis of
     * the <b>type</b>
     *
     * @param x
     * @param y
     * @param type type of FUNCTION that will be returned, can be normal value
     *             or can be a derivative
     * @param diff (UPPER || DOWN). types of difference. those difference has
     *             the value of the axis as reference.
     *             <p>
     *             e.g.:: x = 1, y = 0, type = FUNCTION_X, diff = UPPER diff =
     *             get(<b>x + 1</b>, y) - get(<b>x</b>, y)
     * @return
     */
    protected abstract double diference(int x, int y, equation type, diff diff);

    public abstract double derivate(int x, int y, equation type);

    /**
     * Initialize class variables to be used in others method. It depends of
     * <b>type</b> <i>e.g.:</i> type = FUNCTION_X || FUNCTION_XX diff = x
     *
     * @param x
     * @param y
     * @param type type of FUNCTION that will be returned, can be normal value
     *             or can be a derivative
     */
    protected void initVariables(int x, int y, equation type) {

        if (type == equation.FUNCTION_X ||
            type == equation.FUNCTION_XX) {
            diff_initial = (x == 0) ? 0 : x - 1;
            diff_middle = x;
            diff_final = (x == matriz.columns() - 1) ? x : x + 1;
        }
        else if (type == equation.FUNCTION_Y ||
                 type == equation.FUNCTION_YY) {
            diff_initial = (y == 0) ? 0 : y - 1;
            diff_middle = y;
            diff_final = (y == matriz.rows() - 1) ? y : y + 1;
        }
    }

    /**
     * Make calculations relates to the <b>x</b> axis
     *
     * @param x
     * @param y
     * @param type type of FUNCTION that will be returned, can be normal value
     *             or can be a derivative
     * @return
     */
    protected double calculateAxisX(int x, int y, equation type) {
        double diff_axis;
        double diff_index;
        double retorno;
        if (x == 0 || x == matriz.columns() - 1) {
            diff_axis = diference(x, y, type, diff.UPPER);
            diff_index = matriz.get_x(diff_final) -
                         matriz.get_x(diff_initial);

            retorno = diff_axis / diff_index;
        } else {
            diff_axis = diference(x, y, type, diff.DOWN);
            initVariables(x, y, type);
            diff_index = matriz.get_x(diff_middle) -
                         matriz.get_x(diff_initial);

            double diff_axis_1 = diference(x, y, type, diff.UPPER);
            initVariables(x, y, type);
            double diff_index_1 = matriz.get_x(diff_final) -
                                  matriz.get_x(diff_middle);

            retorno = (diff_axis * diff_index_1) +
                      (diff_axis_1 * diff_index);

            retorno /= 2 * (diff_index * diff_index_1);
        }
        return retorno;
    }

    /**
     * Make calculations relates to the <b>y</b> axis
     *
     * @param x
     * @param y
     * @param type type of FUNCTION that will be returned, can be normal value
     *             or can be a derivative
     * @return
     */
    protected double calculatingAxisY(int x, int y, equation type) {
        double diff_axis;
        double diff_index;
        double retorno;
        if (y == 0 || y == matriz.rows() - 1) {
            diff_axis = diference(x, y, type, diff.UPPER);
            diff_index = matriz.get_y(diff_final) -
                         matriz.get_y(diff_initial);

            retorno = diff_axis / diff_index;
        } else {
            diff_axis = diference(x, y, type, diff.DOWN);
            diff_index = matriz.get_y(diff_middle) -
                         matriz.get_y(diff_initial);

            double diff_axis_1 = diference(x, y, type, diff.UPPER),

                    diff_index_1 = matriz.get_y(diff_final) -
                                   matriz.get_y(diff_middle);

            retorno = (diff_axis * diff_index_1) +
                      (diff_axis_1 * diff_index);

            retorno /= 2 * (diff_index * diff_index_1);
        }
        return retorno;
    }

    protected void fail(String message) {
        throw new IllegalArgumentException(message);
    }

    protected void fail(equation type) {
        throw new IllegalArgumentException("Illegal equation type:" + type);
    }

    /**
     * Enumeration to help the diff's method
     */
    protected enum diff {
        UPPER,
        DOWN
    }
}
