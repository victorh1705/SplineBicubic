package Structure;

import FileManager.FileManager;
import org.la4j.LinearAlgebra;
import org.la4j.Vector;
import org.la4j.matrix.sparse.CRSMatrix;
import org.la4j.vector.dense.BasicVector;

public class PolinomioSolver {


    /**
     * This function builds a Sparse matrix based on the initial matrix. Then
     * make the pivoting, if necessary, and finally solve the solution with the
     * 'value_b'(vector with the results of the matrix)
     * <p>
     * Simple: -Build Matrix -Pivoting -Solve
     *
     * @return Vector - Result of Spline Bicubic
     */
    public Vector polinomioSolver(Matriz matriz) {

        final int matriz_size =
                16 * (matriz.rows() - 1) * (matriz.columns() - 1);

        CRSMatrix sparce_matriz = new CRSMatrix(matriz_size, matriz_size);
        BasicVector value_b = new BasicVector(matriz_size);
        PolinomioBuilder pb = new PolinomioBuilder(matriz);

        for (int row = 0; row < matriz.rows(); row++) {
            //TODO: Analisar intervalos em MatrizInArray
            for (int column = 0; column < matriz.columns(); column++) {
                pb.interval(row, column);
            }
        }

        // create
//        Pivoting.Parcial(sparce_matriz,value_b);
        FileManager.createFile("saida", sparce_matriz);

        return sparce_matriz.withSolver(
                LinearAlgebra.SolverFactory.JACOBI)
                .solve(value_b);

    }
}
