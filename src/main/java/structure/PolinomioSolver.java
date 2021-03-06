package structure;

import fileManager.FileManager;
import org.la4j.LinearAlgebra;
import org.la4j.Matrix;
import org.la4j.Vector;
import org.la4j.inversion.MatrixInverter;
import org.la4j.matrix.sparse.CRSMatrix;
import org.la4j.vector.dense.BasicVector;

public class PolinomioSolver extends PolinomioBuilder {

    public PolinomioSolver(Matriz matriz) {
        super(matriz);
    }

    /**
     * This FUNCTION builds a Sparse matrix based on the initial matrix. Then
     * make the pivoting, if necessary, and finally solve the solution with the
     * 'value_b'(vector with the results of the matrix)
     * <p>
     * Simple: -Build Matrix -Pivoting -Solve
     *
     * @return Vector - Result of Spline Bicubic
     */
    public Vector solver() {

        final int matriz_size =
                16 * (matriz.rows() - 1) * (matriz.columns() - 1);

        sparce_matriz = new CRSMatrix(matriz_size, matriz_size);
        value_b = new BasicVector(matriz_size);

        for (int row = 0; row < matriz.rows(); row++) {
            for (int column = 0; column < matriz.columns(); column++) {
                interval(column, row);
            }
        }

//        Pivoting.Parcial(sparce_matriz,value_b);
        FileManager.createFile("saida", sparce_matriz);


        return inverseAndMultiply();
//        return sparce_matriz.withSolver(LinearAlgebra.SolverFactory.GAUSSIAN)
//                .solve(value_b);


    }

    private Vector inverseAndMultiply() {
        LinearAlgebra.InverterFactory inverterFactory =  LinearAlgebra.GAUSS_JORDAN;
        MatrixInverter inverter = sparce_matriz.withInverter(inverterFactory);

        Matrix inverse = inverter.inverse();
        inverse.multiply(value_b);

        return inverse.multiply(value_b);
    }
}
