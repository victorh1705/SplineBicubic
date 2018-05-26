package library.derivative.firstDerivative;

import library.derivative.Derivative;
import structure.Matriz;
import type.equation;


public class FirstDerivative extends Derivative {

    public FirstDerivative(Matriz matriz) {
        super(matriz);
    }

    public double derivate(int x, int y, equation type) {

        initVariables(x, y, type);

        if (type == equation.FUNCTION_X) {
            return calculateAxisX(x, y, type);
        }
        else if (type == equation.FUNCTION_Y) {
            return calculatingAxisY(x, y, type);
        }

        fail(type);
        return 0;
    }

    protected double diference(int x, int y, equation type, diff diff) {

        switch (type) {
            case FUNCTION_X:
                if (x == 0 || x == matriz.columns() - 1) {
                    return matriz.getValue(diff_final, y) -
                           matriz.getValue(diff_initial, y);
                } else {
                    if (Derivative.diff.DOWN.equals(diff)) {
                        return matriz.getValue(diff_middle, y) -
                               matriz.getValue(diff_initial, y);
                    } else if (Derivative.diff.UPPER.equals(diff)) {
                        return matriz.getValue(diff_final, y) -
                               matriz.getValue(diff_middle, y);
                    }
                }
                break;

            case FUNCTION_Y:

                if (y == 0 || y == matriz.rows() - 1) {
                    return matriz.getValue(x, diff_final) -
                           matriz.getValue(x, diff_initial);
                } else {
                    if (Derivative.diff.DOWN.equals(diff)) {
                        return matriz.getValue(x, diff_middle) -
                               matriz.getValue(x, diff_initial);
                    } else if (Derivative.diff.UPPER.equals(diff)) {
                        return matriz.getValue(x, diff_final) -
                               matriz.getValue(x, diff_middle);
                    }
                }
                break;
        }
        fail(type);
        return 0;
    }
}