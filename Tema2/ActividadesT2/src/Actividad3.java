import java.util.Random;

public class Actividad3 extends Thread{
    private int resultado;

    public int getResultado() {
        return resultado;
    }

    public void run(){
        Random rand = new Random();
        resultado = rand.nextInt(6) + 1;
        System.out.println(Thread.currentThread().getName() + " tiró: " + resultado);
    }
}
