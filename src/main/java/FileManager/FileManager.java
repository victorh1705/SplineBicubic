/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FileManager;

import Estruturas.Matriz;
import Estruturas.MatrizInArray;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.la4j.matrix.MatrixFactory;
import org.la4j.matrix.sparse.CRSMatrix;

/**
 * @author voitt
 */
public class FileManager {

    private String path;
    private int numLinha;
    private int numColuna;

    InputStream is;
    InputStreamReader isr;

    public FileManager(String name) {
        this.path = name + ".txt";
        numColuna = 0;
        numLinha = 0;
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

    public Matriz leituraDados() throws IOException {
        try {
            is = new FileInputStream(path);
            isr = new InputStreamReader(is);
            try (BufferedReader reader = new BufferedReader(isr)) {

                Matriz matriz = new Matriz(numLinha, numColuna);
                String linha = reader.readLine();
                String[] coluna = null;

                int indiceLinha = 0;

                while (linha != null) {
                    if (linha != null) {
                        coluna = linha.split(" ");
                    }

                    int indiceColuna = 0;

                    for (String col : coluna) {
                        int indice = numColuna * indiceLinha + indiceColuna;

                        System.out.printf("\nIndice coluna: %d valor %s", indice, col);

                        matriz.set(indiceLinha, indiceColuna, Double.valueOf(col));
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
