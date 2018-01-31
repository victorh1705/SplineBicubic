/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Structure;

/**
 *
 * @author voitt
 */
public class CriaVetor {
    
    float[] vetor;

    public CriaVetor(int coluna, int linha) {
        this.vetor = new float[coluna*linha];
    }

    public void criaVetor(float[] matriz, int linha, int coluna) {

        for (int i = 0; i < linha; i++) {
            for (int j = 0; j < coluna; j++) {
                int indice
                        = indice = coluna * i + j;

                if (0 == j && j == i) {
                    //matriz[indice] = ;
                }

            }

        }

    }

    private void matrizNormal(int linha, int coluna, float valor) {
//        for (int i = 0; i < arr.length; i++) {
//            for (int j = 0; j < arr.length; j++) {
//                //Object object = arr[j];
//                
//            }
//            
//        }
    }

}
