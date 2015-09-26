/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author agustin
 */
public class Main {
    

    public static List<Integer> resultList = new LinkedList<>();
    private static final int MaxNumberToSearch = 1000000;
    private static final int CantThreads = 10;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {    
        int start = 2;
        int limit = MaxNumberToSearch / CantThreads;
        Thread[] threadsPull = new Thread[CantThreads];
        for(int i = 0; i < CantThreads; i++){ 
            threadsPull[i] = new Thread (new CircularPrimesCalculator(start,limit)); //reparto la busqueda en varios threads
            threadsPull[i].start();                                                  //cada uno busca en distintos rangos                   
            start = limit + 1;
            limit += MaxNumberToSearch / CantThreads; 
        }   
        
        for (Thread t :  threadsPull){ 
            try {
                t.join();  // espera a que todos los threads terminen antes de continuar
            } catch (InterruptedException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        System.out.println("Cantidad de primos circulares encontrados: " + resultList.size());
        System.out.println("Lista de primos circulares: \n" + resultList);
    }
    
    /**
     * Obtiene los numeros circulares de un numero dado.
     *
     * @param number
     * @return Lista con todos los numeros circulares
     */
    public static List<Integer> circularNumbers(int number){
        List<Integer> result = new LinkedList<>();
        int digits = (int)(Math.log10(number)+1); // obtiene la cantidad de digitos de un numero
        result.add(number);
        int shiftMult = 1;
        for(int i = 0 ; i < digits - 1; i++){  // obtiene el numero para multiplicar en el shifteo dependiendo de la cantidad de digitos
            shiftMult *= 10;
        }
        for(int i = 0 ; i < digits - 1; i++){ //cicla segun la cantidad de digitos que tenga el numero
            number = (number/10 + (number%10) * shiftMult); //hace el shifteo a la derecha
            result.add(number);
        }
        return result;
    }
    
     /**
     * Evalua si un numero dado es primo.
     *
     * @param number
     * @return true si el numero es primo, falso si no.
     */
    public static boolean isPrime(int number){
        if(number <= 1) return false;
        if(number == 2) return true;
        if (number%2==0) return false; 
        for(int i=3;i*i<=number;i+=2) { //busca entre los impares y hasta la raiz cuadrada del numero
            if(number%i==0) //si lo puede dividir por otro numero es falso
                return false;
        }
        return true;
    }
}
