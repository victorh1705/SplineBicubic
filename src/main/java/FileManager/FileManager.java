/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FileManager;

import Estruturas.MatrizInArray;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.la4j.matrix.MatrixFactory;
import org.la4j.matrix.sparse.CRSMatrix;

/**
 *
 * @author voitt
 */
public class FileManager {

    private String path;
    private int numLinha;
    private int numColuna;

    public FileManager(String name) {
        this.path = name;
        numColuna = 0;
        numLinha = 0;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void leituraArquivo() {
        leituraInicial();
        leituraDados();
    }

    private void leituraInicial() {
        try {
            InputStream is = new FileInputStream(path);
            InputStreamReader isr = new InputStreamReader(is);
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
        }
    }

    public MatrizInArray leituraDados() {
        try {
            InputStream is = new FileInputStream(path);
            InputStreamReader isr = new InputStreamReader(is);
            try (BufferedReader reader = new BufferedReader(isr)) {

                MatrizInArray matriz = new MatrizInArray(numLinha, numColuna);
                String linha = reader.readLine();
                String[] coluna = null;

                int indiceLinha = 0;

                while (linha != null) {
                    linha = reader.readLine();
                    if (linha != null) {
                        coluna = linha.split(" ");
                    }

                    int indiceColuna = 0;

                    for (String col : coluna) {
                        int indice = numColuna * indiceLinha + indiceColuna;

                        matriz.add(indiceLinha, indiceColuna, Double.valueOf(col));
                        indiceColuna++;
                    }
                    System.out.println("");

                    indiceLinha++;
                }

                return matriz;
            }
        } catch (IOException e) {
            System.out.println("Erro ao tentar ler o arquivo " + e);
        }
        return null;
    }
}
