package Tema2.Actividades.Actividad4;

public class Contador {
    private int value = 0;

    public synchronized int getValue(){
        return value;
    }

    public synchronized void incrementar(int incremento){
        value += incremento;
        System.out.println(Thread.currentThread().getName() + " incrementó en " + incremento);
    }

    public static boolean esPrimo(int n){
        if (n <= 1) return false;

        for (int i = 2; i <= Math.sqrt(n); i++){
            if (n % i == 0) return false;
        }
        return true;
    }
}
