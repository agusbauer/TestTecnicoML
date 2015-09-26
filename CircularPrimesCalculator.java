/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

//import static circularprimes.Main.circularNumbers;
//import static circularprimes.Main.isPrime;
//import static circularprimes.Main.resultList;
import java.util.List;

/**
 *
 * @author agustin
 */
public class CircularPrimesCalculator implements Runnable {

    private final int startValue, limitValue; //valores para fijar el rango de busqueda

    public CircularPrimesCalculator(int start,int limit){
        this.startValue = start;
        this.limitValue = limit;
    }
    
    @Override
    public void run() {
        for(int i = startValue; i <= limitValue; i++){ //cicla dentro de los limites dados
            List<Integer> circNumbers = Main.circularNumbers(i); //obtiene la lista de todos los shifteos del numero
            boolean isPrimeNumber = true;
            int j = 0;
            while(isPrimeNumber && j < circNumbers.size()){  
                isPrimeNumber = Main.isPrime(circNumbers.get(j)); //verifica que todos sean primos
                j++;
            }
            if(isPrimeNumber){
                synchronized( this ) {Main.resultList.add(i); }  //si el numero es un primo circular lo agrega a la lista resultado
            }
        }
    }
    
}
