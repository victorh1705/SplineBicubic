/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FileManager;

import Structure.Matriz;
import org.la4j.Matrix;
import org.la4j.Vector;

import java.io.*;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * @author voitt
 */
@SuppressWarnings("Duplicates")
public class FileManager {

    private String path;
    private int numLinha;
    private int numColuna;

    private InputStream is;
    private InputStreamReader isr;

    public FileManager(String name) {
        this.path = name + ".txt";
        numColuna = 0;
        numLinha = 0;
    }

    public static void createFile(String path, Vector vector) {
        try (PrintStream out = new PrintStream(path + ".csv")) {
            out.print(vector.toCSV(NumberFormat.getInstance
                    (Locale.US)));
            System.out.printf("\nArquivo %s criado", path);
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    public static void createFile(String path, Matrix matrix) {
        try (PrintStream out = new PrintStream(path + ".csv")) {
            out.print(matrix.toCSV(NumberFormat.getInstance()));
            System.out.printf("\nArquivo %s criado", path);
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Matriz leituraArquivo() throws IOException {
        leituraInicial();
        return leituraDados();
    }

    private void leituraInicial() throws IOException {
        try {
            is = new FileInputStream(path);
            isr = new InputStreamReader(is);
            try (BufferedReader reader = new BufferedReader(isr)) {
                String linha = reader.readLine();

                while (linha != null) {
                    System.out.println(linha);
                    linha = reader.readLine();
                    numLinha++;
                    if (linha != null) {
                        numColuna = linha.split(" ").length;
                    }
                }
            }

            System.out.println("numColuna = " + numColuna);
            System.out.println("numLinha = " + numLinha);
        } catch (IOException e) {
            System.out.println("Erro ao tentar ler o arquivo " + e);
        } finally {
            close();
        }
    }

    private Matriz leituraDados() throws IOException {
        try {
            is = new FileInputStream(path);
            isr = new InputStreamReader(is);
            try (BufferedReader reader = new BufferedReader(isr)) {

                Matriz matriz = new Matriz(numLinha, numColuna);
                String linha = reader.readLine();
                String[] coluna;

                int indiceLinha = 0;

                while (linha != null) {
                    coluna = linha.split(" ");

                    int indiceColuna = 0;

                    for (String col : coluna) {
                        int indice = numColuna * indiceLinha + indiceColuna;

                        System.out.printf("\nIndice coluna: %d valor %s",
                                indice, col);

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
            System.out.println("Erro ao tentar ler o arquivo " + e);
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
