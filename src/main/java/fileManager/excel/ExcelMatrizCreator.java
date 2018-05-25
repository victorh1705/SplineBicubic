package fileManager.excel;

import fileManager.ExcelCreator;
import structure.Matriz;

import java.io.FileNotFoundException;

public class ExcelMatrizCreator extends ExcelCreator {
    public ExcelMatrizCreator(String name) throws FileNotFoundException {
        super(name);
    }

    public void createMatrizSheet(Matriz matriz, int first_row, int first_col) {
        for (int i = first_row; i < matriz.rows() + first_row; i++) {
            for (int j = first_col; j < matriz.columns() + first_col; j++) {
                setValue(i, j, matriz.getValue(i, j));
            }
        }
    }

    public void createMatrizSheet(Matriz matriz) {
        this.createMatrizSheet(matriz, 0, 0);
    }

    public void createSheet(String sheetName, Matriz matriz, int first_row,
            int first_col) {
        createSheet(sheetName);
        createMatrizSheet(matriz, first_row, first_col);
    }

    public void createSheet(String sheetName, Matriz matriz) {
        createSheet(sheetName);
        createMatrizSheet(matriz);
    }
}
