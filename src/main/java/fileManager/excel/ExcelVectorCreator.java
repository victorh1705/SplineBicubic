package fileManager.excel;

import fileManager.ExcelCreator;
import org.la4j.Vector;

import java.io.FileNotFoundException;

public class ExcelVectorCreator extends ExcelCreator {
    public ExcelVectorCreator(String name) throws FileNotFoundException {
        super(name);
    }

    public void createVectorSheet(Vector vector, Direction direction) {
        createVectorSheet(vector, 0, direction);
    }

    public void createVectorSheet(Vector vector, int index,
            Direction direction) {
        switch (direction) {
            case row:
                createVectorSheet(vector, index, 0, direction);
                break;
            case column:
                createVectorSheet(vector, 0, index, direction);
                break;
            default:
                fail();
                break;
        }


    }

    private void createVectorSheet(Vector vector, int first_row, int
            first_col, Direction direction) {
        switch (direction) {
            case row:
                for (int i = 0; i < vector.length(); i++) {
                    setValue(first_row + i, first_col, vector.get(i));
                }
                break;
            case column:
                for (int i = 0; i < vector.length(); i++) {
                    setValue(first_row, first_col + i, vector.get(i));
                }
                break;
            default:
                fail();
                break;
        }
    }

    protected enum Direction {
        row,
        column
    }
}
