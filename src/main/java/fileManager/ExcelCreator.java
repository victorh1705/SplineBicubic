package fileManager;

import fileManager.excel.Excel;
import org.la4j.Vector;
import structure.Matriz;

import java.io.FileNotFoundException;

public class ExcelCreator extends Excel {
    public ExcelCreator(String name) throws FileNotFoundException {
        super(name);
    }

    public void setMatriz(Matriz matriz, int first_row, int first_col) {
        for (int i = 0; i < matriz.rows(); i++) {
            for (int j = 0; j < matriz.columns(); j++) {
                setValue(first_row + i, first_col + j,
                        matriz.getValue(i, j));
            }
        }
    }

    public void setMatriz(Matriz matriz) {
        this.setMatriz(matriz, 0, 0);
    }

    public void setVector(Vector vector, int index,
            ExcelCreator.Direction direction) {
        switch (direction) {
            case HORIZONTAL:
                setVector(vector, index, 0, direction);
                break;
            case VERTICAL:
                setVector(vector, 0, index, direction);
                break;
            default:
                fail();
                break;
        }


    }

    private void setVector(Vector vector, int first_row, int
            first_col, Direction direction) {
        switch (direction) {
            case HORIZONTAL:
                for (int i = 0; i < vector.length(); i++) {
                    setValue(first_row + i, first_col, vector.get(i));
                }
                break;
            case VERTICAL:
                for (int i = 0; i < vector.length(); i++) {
                    setValue(first_row, first_col + i, vector.get(i));
                }
                break;
            default:
                fail();
                break;
        }
    }

    public enum Direction {
        HORIZONTAL,
        VERTICAL
    }
}
