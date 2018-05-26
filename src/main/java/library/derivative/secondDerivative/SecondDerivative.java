package library.derivative.secondDerivative;

import library.derivative.Derivative;
import library.derivative.firstDerivative.FirstDerivative;
import structure.Matriz;
import type.equation;


public class SecondDerivative extends Derivative {

    public SecondDerivative(Matriz matriz) {
        super(matriz);
    }

    public double derivate(int x, int y, equation type) {
        double retorno = 0;

        initVariables(x, y, type);

        if (type == equation.FUNCTION_XX) {
            return calculateAxisX(x, y, type);
        }
        else if (type == equation.FUNCTION_YY) {
            return calculatingAxisY(x, y, type);
        }
        else if (type == equation.FUNCTION_XY) {
            if (null != matriz.getType_point(x, y)) {
                int x_big = (x < matriz.columns() - 1) ? x + 1 : x,
                        x_med = x,
                        x_small = (x > 0) ? x - 1 : 0,

                        y_big = (y < matriz.rows() - 1) ? y + 1 : y,
                        y_med = y,
                        y_small = (y > 0) ? y - 1 : 0;

                retorno = matriz.getValue(x_big, y_big) +
                          matriz.getValue(x_small, y_small) -
                          matriz.getValue(x_big, y_small) -
                          matriz.getValue(x_small, y_big);
                retorno = retorno / (Math.pow(1, 2));

                if (matriz.getType_point(x, y) == Matriz.typePoint.edge) {
                    retorno = retorno / 2;
                }

            }
            return retorno;
//            return calculateAxisXY(x, y, type);

        }

        fail(type);
        return 0;
    }


    @SuppressWarnings("Duplicates")
    protected double diference(int x, int y, equation type, diff diff) {
        double retorno = 0;

        equation fd_type = null;


        switch (type) {
            case FUNCTION_XX:
                fd_type = equation.FUNCTION_X;
                if (x == 0 || x == matriz.rows() - 1) {
                    return new FirstDerivative(matriz).derivate(
                            diff_final, y, fd_type) -
                           new FirstDerivative(matriz).derivate(
                                   diff_initial, y, fd_type);
                } else {
                    if (Derivative.diff.DOWN.equals(diff)) {
                        return new FirstDerivative(matriz).derivate(diff_middle,
                                y, fd_type) -
                               new FirstDerivative(matriz).derivate(
                                       diff_initial, y, fd_type);
                    } else if (Derivative.diff.UPPER.equals(diff)) {
                        return new FirstDerivative(matriz).derivate(
                                diff_final, y, fd_type) -
                               new FirstDerivative(matriz).derivate(
                                       diff_middle, y, fd_type);
                    }
                }

                break;
            case FUNCTION_YY:
                fd_type = equation.FUNCTION_Y;
                if (y == 0 || y == matriz.rows() - 1) {
                    return new FirstDerivative(matriz).derivate(x,
                            diff_final, fd_type) -
                           new FirstDerivative(matriz).derivate(x,
                                   diff_initial, fd_type);
                } else {
                    if (Derivative.diff.UPPER.equals(diff)) {
                        if (Derivative.diff.UPPER.equals(diff)) {
                            return new FirstDerivative(matriz).derivate(
                                    x, diff_final, fd_type) -
                                   new FirstDerivative(matriz).derivate(
                                           x, diff_middle, fd_type);
                        }
                    } else if (Derivative.diff.DOWN.equals(diff)) {
                        return new FirstDerivative(matriz).derivate(x,
                                diff_middle, fd_type) -
                               new FirstDerivative(matriz).derivate(x,
                                       diff_initial, fd_type);
                    }
                }
                break;
            case FUNCTION_XY:
                fd_type = equation.FUNCTION_X;
                if (y == 0 || y == matriz.rows() - 1) {
                    return new FirstDerivative(matriz).derivate(x,
                            diff_final, fd_type) -
                           new FirstDerivative(matriz).derivate(x,
                                   diff_initial, fd_type);
                } else {
                    if (Derivative.diff.UPPER.equals(diff)) {
                        if (Derivative.diff.UPPER.equals(diff)) {
                            return new FirstDerivative(matriz).derivate(
                                    x, diff_final, fd_type) -
                                   new FirstDerivative(matriz).derivate(
                                           x, diff_middle, fd_type);
                        }
                    } else if (Derivative.diff.DOWN.equals(diff)) {
                        return new FirstDerivative(matriz).derivate(x,
                                diff_middle, fd_type) -
                               new FirstDerivative(matriz).derivate(x,
                                       diff_initial, fd_type);
                    }
                }
                break;
        }
        return retorno;
    }


    @SuppressWarnings("Duplicates")
    private double calculateAxisXY(int x, int y, equation type) {
        double retorno = 0;

        int x_initial = (x == 0) ? 0 : x - 1,
                x_middle = x,
                x_final = (x == matriz.columns() - 1) ? x : x + 1;

        int y_initial = (y == 0) ? 0 : y - 1,
                y_middle = y,
                y_final = (y == matriz.rows() - 1) ? y : y + 1;


        double diff_axis;
        double diff_index;
        if (y == 0 || y == matriz.rows() - 1) {
            diff_axis = diference(x, y, type, diff.UPPER);
            diff_index = matriz.get_y(y_final) -
                         matriz.get_y(y_final);

            retorno = diff_axis / diff_index;
        } else {
            diff_axis = diference(x, y, type, diff.DOWN);
            diff_index = matriz.get_y(y_middle) -
                         matriz.get_y(y_initial);

            double diff_axis_1 = diference(x, y, type, diff.UPPER),

                    diff_index_1 = matriz.get_y(y_final) -
                                   matriz.get_y(y_middle);

            retorno = (diff_axis * diff_index_1) +
                      (diff_axis_1 * diff_index);

            retorno /= 2 * (diff_index * diff_index_1);
        }
        return retorno;
    }
}
