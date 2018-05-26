/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fileManager;

import org.la4j.Matrix;
import org.la4j.Vector;
import structure.Matriz;

import java.io.*;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * @author voitt
 */
public class FileManager {

    private String type;
    private int numLinha;
    private int numColuna;

    private InputStream is;
    private InputStreamReader isr;

    public FileManager(String name) {
        type = name + ".txt";
        numColuna = 0;
        numLinha = 0;
    }

    public static void createFile(String nameFile, Vector vector) {
        try (PrintStream out = new PrintStream(nameFile + ".csv")) {
            out.print(vector.toCSV(NumberFormat.getInstance(Locale.US)));
            System.out.printf("\nArquivo %s criado", nameFile);
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    public static void createFile(String nameFile, Matrix matrix) {
        try (PrintStream out = new PrintStream(nameFile + ".csv")) {
            out.print(matrix.toCSV(NumberFormat.getInstance()));
            System.out.printf("\nArquivo %s criado", nameFile);
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    public static void createData(String nameFile, Matriz matriz, Vector vector,
            double range) {

        int x_range = (int) ((matriz.rows() - 1) / range) + 2,
                y_range = (int) ((matriz.columns() - 1) / range) + 2;

        Matriz result = new Matriz(x_range, y_range);

        for (int i = 1; i < result.rows(); i++) {
            result.set(i, 0, range * (i - 1));
//            System.out.printf("range %f  - %f\n", range * (i - 1),
//                    result.get(i, 0));
        }
        for (int i = 1; i < result.columns(); i++) {
            result.set(0, i, range * (i - 1));
//            System.out.printf("range %f  - %f\n", range * (i - 1),
//                    result.get(0, i));
        }

        for (int i = 1; i < result.rows(); i++) {
            for (int j = 1; j < result.columns(); j++) {
                double data = Matriz.valorZ2(matriz, vector, range * (i - 1),
                        range * (j - 1));
                result.set(i, j, data);
            }
        }

        try (PrintStream out = new PrintStream(nameFile + ".csv")) {
            out.print(result.mkString(NumberFormat.getInstance(), "\n", ";"));
            System.out.printf("\nArquivo %s criado", nameFile);
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Matriz leituraArquivo() throws IOException {
        leituraInicial();
        return leituraDados();
    }

    private void leituraInicial() throws IOException {
        String print = "\n";
        try {
            is = new FileInputStream(type);
            isr = new InputStreamReader(is);
            try (BufferedReader reader = new BufferedReader(isr)) {
                String linha = reader.readLine();

                while (linha != null) {
                    print += linha + "\n";
                    linha = reader.readLine();
                    numLinha++;
                    if (linha != null) {
                        numColuna = linha.split("\\s").length;
                    }
                }
            }

            print += "numColuna = " + numColuna;
            print += "\nnumLinha = " + numLinha;
        } catch (IOException e) {
            print += "\nErro ao tentar ler o arquivo " + e;
        } finally {
            close();
            System.out.println(print);
        }
    }

    private Matriz leituraDados() throws IOException {
        try {
            is = new FileInputStream(type);
            isr = new InputStreamReader(is);
            try (BufferedReader reader = new BufferedReader(isr)) {

                Matriz matriz = new Matriz(numLinha, numColuna);
                String linha = reader.readLine();
                String[] coluna;

                int indiceLinha = 0;

                while (linha != null) {
                    coluna = linha.split("\\s");

                    int indiceColuna = 0;

                    for (String col : coluna) {
                        int indice = numColuna * indiceLinha + indiceColuna;

                        matriz.set(indiceLinha, indiceColuna,
                                Double.valueOf(col));
                        indiceColuna++;
                    }

                    indiceLinha++;
                    linha = reader.readLine();
                }

                return matriz;
            }
        } catch (IOException e) {
            System.out.println("\nErro ao tentar ler o arquivo " + e);
        } finally {
            close();
        }
        return null;
    }

    private void close() throws IOException {
        is.close();
        isr.close();
    }
}
