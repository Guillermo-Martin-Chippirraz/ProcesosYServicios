package Tema2.Practica;
import java.util.Random;

public class Main {
    static final int DIAS = 18250;
    static final int MIN_TEMP = -20;
    static final int MAX_TEMP = 50;
    public static void main(String[] args) throws InterruptedException{
        int[] temp = new int[DIAS];
        int numHilos = Integer.parseInt(args[0]);
        Random rand = new Random();

        for (int i = 0; i < DIAS; i++){
            temp[i] = rand.nextInt(MAX_TEMP - MIN_TEMP + 1) + MIN_TEMP;
        }

        Actividad1[] hilos = new Actividad1[numHilos];
        int segSize = DIAS/numHilos;

        for (int i = 0; i < numHilos; i++){
            int inicio = i * segSize;
            int fin = (i == numHilos - 1) ? DIAS : inicio + segSize;
            hilos[i] = new Actividad1(temp, inicio, fin);
            hilos[i].start();
        }

        double sumaPromedios = 0;
        for (Actividad1 hilo : hilos){
            hilo.join();
            sumaPromedios += hilo.getPromedio();
        }

        double promedioFinal = sumaPromedios / numHilos;

        System.out.println("Temperatura promedio de los últimos 50 años: " + promedioFinal + " ºC.");
    }
}