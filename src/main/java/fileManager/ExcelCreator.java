package fileManager;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.WorkbookUtil;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class ExcelCreator implements AutoCloseable {

    protected static String path = "export/";
    protected static String type = ".xls";

    protected Workbook wb;
    protected Sheet sheet;
    protected Row row;
    protected Cell cell;
    protected OutputStream fileOut;
    protected String name;


    public ExcelCreator(String name) throws FileNotFoundException {
        this.name = WorkbookUtil.createSafeSheetName(name);
        this.wb = new HSSFWorkbook();

        this.fileOut = new FileOutputStream(path + name + type);
    }

    public void createSheet(String name) {
        sheet = wb.createSheet(name);
    }

    public Sheet getSheet(String name) {
        return wb.getSheet(name);
    }

    /**
     * Set the <b>sheet</b> class' variable with the given name
     *
     * @param name
     */
    public void setSheet(String name) {
        sheet = wb.getSheet(name);
    }

    /**
     * Set a value at the given location
     * <p>
     * Handle with non-exist location
     *
     * @param row_local row
     * @param column    column
     * @param value     double value
     */
    public void setValue(int row_local, int column, double value) {
        // Verify if row exist
        if (sheet.getLastRowNum() > row_local) {
            for (int i = sheet.getLastRowNum(); i <= row_local; i++) {
                row = sheet.createRow(i);
            }
        }

        // Verify if column exist
        if (row.getLastCellNum() > column) {
            for (int i = row.getLastCellNum(); i <= column; i++) {
                cell = row.createCell(i);
            }
        }

        cell.setCellValue(value);
    }

    /**
     * Set a value at the given location
     * <p>
     * Handle with non-exist location
     *
     * @param row_local row
     * @param column    column
     * @param value     String
     */
    public void setValue(int row_local, int column, String value) {
        // Verify if row exist
        if (sheet.getLastRowNum() > row_local) {
            for (int i = sheet.getLastRowNum(); i < row_local; i++) {
                row = sheet.createRow(i);
            }
        }
        row = sheet.createRow(row_local);

        // Verify if column exist
        if (row.getLastCellNum() > column) {
            for (int i = row.getLastCellNum(); i < column; i++) {
                cell = row.createCell(i);
            }
        }
        cell = row.createCell(column);

        cell.setCellValue(value);
    }

    public Cell getCell(int row, int column) {
        return sheet.getRow(row).getCell(column);
    }

    /**
     * Get the <i>String</i> value of a cell
     *
     * @param row_local
     * @param column
     *
     * @return String value
     */
    public String getValueString(int row_local, int column) {
        ensureCellLocation(row_local, column);

        return sheet.getRow(row_local).getCell(column).getStringCellValue();
    }

    /**
     * Get the <i>Numeric</i> value of a cell
     *
     * @param row_local
     * @param column
     *
     * @return Numeric value
     */
    public double getValueNumeric(int row_local, int column) {
        ensureCellLocation(row_local, column);

        return sheet.getRow(row_local).getCell(column).getNumericCellValue();
    }

    private void ensureCellLocation(int row_local, int column) {
        if (sheet.getLastRowNum() > row_local) {
            fail();
        }

        if (sheet.getRow(row_local).getLastCellNum() > column) {
            fail();
        }
    }

    public void writeFile() {
        try {
            wb.write(fileOut);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * throw Exception
     */
    protected void fail() {
        throw new IllegalArgumentException();
    }

    @Override
    public void close() throws Exception {
        this.fileOut.close();
    }
}
