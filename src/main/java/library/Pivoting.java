package library;

import org.la4j.Vector;
import org.la4j.matrix.SparseMatrix;


public class Pivoting {


    /**
     * Use Simple Pivoting, only changing if number at the mainly diagonal is
     * zero, into the matrix (A) and also change the line of the Vector(x) (for
     * further use)
     * <p>
     * Ax = B
     *
     * @param matrix SparseMatrix that will be pivoted
     * @param vector Vector that multiply the Matrix
     */
    public static void Basic(SparseMatrix matrix, Vector vector, boolean
            invertido) {

        int number_equations = vector.length();

        if (invertido) {
            for (int i = number_equations - 1; i >= 0; i--) {
                if (matrix.get(i, i) == 0) {
                    for (int j = i; j >= 0; j--) {
                        if (matrix.get(j, i) != 0) {
                            matrix.swapRows(i, j);
                            vector.swapElements(i, j);
                            break;
                        }
                    }
                }
            }
        } else {
            for (int i = 0; i < number_equations; i++) {
                if (matrix.get(i, i) == 0) {
                    for (int j = i; j < number_equations; j++) {
                        if (matrix.get(j, i) != 0) {
                            matrix.swapRows(i, j);
                            vector.swapElements(i, j);
                            break;
                        }
                    }
                }
            }
        }
    }

    /**
     * Use Parcial Pivoting into the matrix(A) and also change the line of the
     * Vector(x) (for further use)
     * <p>
     * Ax = B
     *
     * @param matrix SparseMatrix that will be pivoted
     * @param vector Vector that multiply the Matrix
     */
    public static void Parcial(SparseMatrix matrix, Vector vector) {

        for (int i = 0; i < matrix.columns(); i++) {
            int line = i;
            for (int j = i; j < matrix.rows(); j++) {
                if (matrix.get(j, i) != 0 &&
                    matrix.get(j, i) > matrix.get(line, i)) {
                    line = j;
                }
            }
            matrix.swapRows(i, line);
            vector.swapElements(i, line);
        }
    }

    /**
     * Use Complete Pivoting into the matrix(A) and also change the line of the
     * Vector(x) (for further use)
     * <p>
     * Ax = B
     *
     * @param matrix SparseMatrix that will be pivoted
     * @param vector Vector that multiply the Matrix
     */
    public static void Complete(SparseMatrix matrix, Vector
            vector) {


        for (int i = 0; i < matrix.rows(); i++) {
            int line = i,
                    column = i;
            for (int j = i; j < matrix.rows(); j++) {
                for (int k = i; k < matrix.columns(); k++) {
                    if (matrix.get(j, k) != 0) {
                        if (matrix.get(j, k) >
                            matrix.get(line, column)) {
                            line = j;
                            column = k;
                        } else if (matrix.get(line, column) == 0) {
                            line = j;
                            column = k;
                        }
                    }
                }
            }
            matrix.swapRows(i, line);
            vector.swapElements(i, line);

            matrix.swapColumns(i, column);
            //TODO: Analisar troca de coluna para vector
        }
    }
}
