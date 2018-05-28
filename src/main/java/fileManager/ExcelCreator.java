package fileManager;

import fileManager.excel.Excel;
import org.apache.poi.ss.usermodel.Row;
import org.la4j.Vector;
import structure.Matriz;

import java.io.FileNotFoundException;

public class ExcelCreator extends Excel {
    public ExcelCreator(String name) throws FileNotFoundException {
        super(name);
    }

    public ExcelCreator() {
        super();
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

    public Matriz readMatriz(String spreadsheet) {
        sheet = wb.getSheet(spreadsheet);

        //add 1, because its not the size, it is the index of last row
        int num_rows = sheet.getLastRowNum() + 1;
        int num_cols = 0;
        for (Row row : sheet) {
            if (row.getLastCellNum() > num_cols) {
                num_cols = row.getLastCellNum();
            }
        }

        Matriz matriz = new Matriz(num_cols, num_rows);

        for (int y = 0; y < num_rows; y++) {

            if (sheet.getRow(y) != null) {
                row = sheet.getRow(y);

                for (int x = 0; x < num_cols; x++) {

                    if (row.getCell(x) != null) {
                        cell = row.getCell(x);
                        double value = cell.getNumericCellValue();
                        matriz.setValue(y, x, value);
                    }
                }
            }
        }
        return matriz;
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
