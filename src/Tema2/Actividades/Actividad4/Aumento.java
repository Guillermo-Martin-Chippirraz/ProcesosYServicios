package Tema2.Actividades.Actividad4;

import java.util.Random;

public class Aumento implements Runnable{
    private final Contador contador;
    private final int repeticiones;
    private final Random random = new Random();

    public Aumento(Contador contador, int repeticiones){
        this.contador = contador;
        this.repeticiones = repeticiones;
    }

    public void run(){
        for (int i = 0; i < repeticiones; i++){
            int aleatorio = random.nextInt(9) + 1;
            int actual = contador.getValue();
            int incremento;

            incremento = (actual % 2 == 0) ? aleatorio * 100 : aleatorio * 500;

            if (Contador.esPrimo(actual)) incremento /= 2;

            contador.incrementar(incremento);

            try {
                Thread.sleep(100);
            }catch (InterruptedException e){
                Thread.currentThread().interrupt();
            }
        }
    }
}
