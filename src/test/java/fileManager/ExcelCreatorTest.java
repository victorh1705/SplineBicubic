package fileManager;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.junit.*;
import org.la4j.Vector;
import org.la4j.vector.dense.BasicVector;
import structure.Matriz;

import java.io.IOException;

public class ExcelCreatorTest {

    private static ExcelCreator matriz;
    private static ExcelCreator v_vertical;
    private static ExcelCreator v_horizontal;
    private static ExcelCreator leitura;

    @BeforeClass
    public static void setUpClass() throws IOException, InvalidFormatException {
        matriz = new ExcelCreator("test/matriz");
        v_vertical = new ExcelCreator("test/vetor_vertical");
        v_horizontal = new ExcelCreator("test/vetor_horizontal");

        leitura = new ExcelCreator();
        leitura.readFile("data/Bicubica.xlsx");
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
        matriz.close();
        v_vertical.close();
        v_horizontal.close();
        leitura.close();
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void setMatriz() {
//        Create matriz
        Matriz local_matriz = new Matriz(3, 3);

        local_matriz.setValue(0, 0, 1);
        local_matriz.setValue(1, 0, 2);
        local_matriz.setValue(2, 0, 3);
        local_matriz.setValue(0, 1, 4);
        local_matriz.setValue(1, 1, 5);
        local_matriz.setValue(2, 1, 6);
        local_matriz.setValue(0, 2, 7);
        local_matriz.setValue(1, 2, 8);
        local_matriz.setValue(2, 2, 9);

        matriz.createSheet("matriz_sheet");
        matriz.setMatriz(local_matriz, 0, 0);
        matriz.writeFile();
    }

    @Test
    public void setVectorVertical() {

//        Create Vector
        Vector vertical = new BasicVector(2);

        vertical.set(0, 1);
        vertical.set(1, 2);

        v_vertical.createSheet("vetor_sheet");
        v_vertical.setVector(vertical, 1, ExcelCreator.Direction.VERTICAL);
        v_vertical.writeFile();
    }

    @Test
    public void setVetorHorizontal() {
        Vector horizontal = new BasicVector(2);

        horizontal.set(0, 1);
        horizontal.set(1, 2);

        v_horizontal.createSheet("vetor_sheet");
        v_horizontal.setVector(horizontal, 1,
                ExcelCreator.Direction.HORIZONTAL);
        v_horizontal.writeFile();
    }

    @Test
    public void readFunction() {
    }
}