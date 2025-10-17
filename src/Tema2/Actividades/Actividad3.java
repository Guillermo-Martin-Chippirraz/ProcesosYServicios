package Tema2.Actividades;

import java.util.Random;

public class Actividad3 extends Thread{
    private int resultado;
    private static int suma = 0;

    public int getResultado() {
        return resultado;
    }

    public static int getSuma(){
        return suma;
    }

    public synchronized void sumar(){
        suma += resultado;
    }
    public void run(){
        Random rand = new Random();
        resultado = rand.nextInt(6) + 1;
        System.out.println(Thread.currentThread().getName() + " tiró: " + resultado);
    }
}
