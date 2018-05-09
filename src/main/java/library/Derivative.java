package library;

import structure.Matriz;
import type.equation;

import static java.lang.Math.pow;

public class Derivative {

    private static int diff_initial = 0;
    private static int diff_middle = 0;
    private static int diff_final = 0;

    private static int y_initial = 0;
    private static int y_middle = 0;
    private static int y_final = 0;

    private static int x_initial = 0;
    private static int x_middle = 0;
    private static int x_final = 0;

    private static Matriz matriz = null;

    public static double firstDerivative(boolean negative, int x, int y,
            equation type, Matriz matriz) {
        double retorno = 0;

        initVariables(x, y, type, matriz);


        if (type == equation.function_x) {
            retorno = calculateAxisX(x, y, type, matriz);
        } else if (type == equation.function_y) {
            retorno = calculatingAxisY(x, y, type, matriz);
        }

        return retorno;
    }


    public static double secondDerivative(boolean negative, int x, int y,
            equation type, Matriz matriz) {
        double retorno = 0;

        initVariables(x, y, type, matriz);

        if (type == equation.function_xx) {
            retorno = calculateAxisX(x, y, type, matriz);
        } else if (type == equation.function_yy) {
            retorno = calculatingAxisY(x, y, type, matriz);
        } else if (type == equation.function_xy) {
            if (null != matriz.getType_point(x, y)) {
                int x_big = (x < matriz.columns() - 1) ? x + 1 : x,
                        x_small = (x > 0) ? x - 1 : 0,
                        y_big = (y < matriz.rows() - 1) ? y + 1 : y,
                        y_small = (y > 0) ? y - 1 : 0;

                retorno =
                        matriz.getValue(x_big, y_big) + matriz.getValue(x_small,
                                y_small) - matriz.getValue(x_big, y_small) -
                        matriz.getValue
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
            // retorno += value_b.get(line);
        }
        return retorno;
    }

    private static void initVariables(int x, int y, equation type,
            Matriz matriz) {
        y_initial = (y == 0) ? 0 : y - 1;
        y_middle = y;
        y_final = (y == matriz.rows() - 1) ? y : y + 1;

        x_initial = (x == 0) ? 0 : x - 1;
        x_middle = x;
        x_final = (x == matriz.columns() - 1) ? x : x + 1;


        if (type == equation.function_x || type == equation.function_xx) {
            diff_initial = x_initial;
            diff_middle = x_middle;
            diff_final = x_final;
        } else if (type == equation.function_y || type == equation.function_yy
                ) {
            diff_initial = y_initial;
            diff_middle = y_middle;
            diff_final = y_final;
        }
    }

    private static double calculateAxisX(int x, int y, equation type,
            Matriz matriz) {
        double diff_axis;
        double diff_index;
        double retorno;
        if (x == 0 || x == matriz.columns() - 1) {
            diff_axis = diference(x, y, type, matriz, diff.UPPER);
            diff_index = matriz.get_x(diff_final) -
                         matriz.get_x(diff_initial);

            retorno = diff_axis / diff_index;
        } else {
            diff_axis = diference(x, y, type, matriz, diff.DOWN);
            diff_index = matriz.get_x(diff_middle) -
                         matriz.get_x(diff_initial);

            double diff_axis_1 = diference(x, y, type, matriz, diff.UPPER),

                    diff_index_1 = matriz.get_x(diff_final) - matriz
                            .get_x(diff_middle);

            retorno = (diff_axis * diff_index_1) +
                      (diff_axis_1 * diff_index);

            retorno /= 2 * (diff_index * diff_index_1);
        }
        return retorno;
    }


    private static double calculatingAxisY(int x, int y, equation type,
            Matriz matriz) {
        double diff_axis;
        double diff_index;
        double retorno;
        if (y == 0 || y == matriz.rows() - 1) {
            diff_axis = diference(x, y, type, matriz, diff.UPPER);
            diff_index = matriz.get_y(diff_final) -
                         matriz.get_y(diff_initial);

            retorno = diff_axis / diff_index;
        } else {
            diff_axis = diference(x, y, type, matriz, diff.DOWN);
            diff_index = matriz.get_y(diff_middle) -
                         matriz.get_y(diff_initial);

            double diff_axis_1 = diference(x, y, type, matriz, diff.UPPER),

                    diff_index_1 = matriz.get_y(diff_final) -
                                   matriz.get_y(diff_middle);

            retorno = (diff_axis * diff_index_1) +
                      (diff_axis_1 * diff_index);

            retorno /= 2 * (diff_index * diff_index_1);
        }
        return retorno;
    }


    private static double diference(int x, int y, equation type, Matriz
            matriz, diff diff) {
        double retorno = 0;

        switch (type) {
            case function_x:
                if (x == 0 || x == matriz.columns() - 1) {
                    retorno = matriz.getValue(diff_final, y) - matriz.getValue
                            (diff_initial, y);
                } else {
                    if (Derivative.diff.DOWN.equals(diff)) {
                        retorno = matriz.getValue(diff_middle, y) -
                                  matriz.getValue
                                          (diff_initial, y);
                    } else if (Derivative.diff.UPPER.equals(diff)) {
                        retorno =
                                matriz.getValue(diff_final, y) - matriz.getValue
                                        (diff_middle, y);
                    }
                }

                break;
            case function_y:

                if (y == 0 || y == matriz.rows() - 1) {
                    retorno = matriz.getValue(x, diff_final) -
                              matriz.getValue(x, diff_initial);
                } else {
                    if (Derivative.diff.DOWN.equals(diff)) {
                        retorno = matriz.getValue(x, diff_middle) -
                                  matriz.getValue(x, diff_initial);
                    } else if (Derivative.diff.UPPER.equals(diff)) {
                        retorno = matriz.getValue(x, diff_final) -
                                  matriz.getValue(x, diff_middle);
                    }
                }
                break;

            case function_xx:
                equation fd_type = equation.function_x;
                if (x == 0 || x == matriz.rows() - 1) {
                    retorno =
                            firstDerivative(false, diff_final, y, fd_type,
                                    matriz) -
                            firstDerivative(false, diff_initial, y, fd_type,
                                    matriz);
                } else {
                    if (Derivative.diff.DOWN.equals(diff)) {
                        double re1 =
                                firstDerivative(false, diff_middle, y, fd_type,
                                        matriz);
                        double re2 = firstDerivative(false,
                                diff_initial, y, fd_type, matriz);
                        retorno = re1 - re2;
                    } else if (Derivative.diff.UPPER.equals(diff)) {
                        retorno =
                                firstDerivative(false, diff_final, y, fd_type,
                                        matriz) -
                                firstDerivative(false, diff_middle, y, fd_type,
                                        matriz);
                    }
                }

                break;
        }
        return retorno;
    }


    private enum diff {
        UPPER,
        DOWN
    }
}
