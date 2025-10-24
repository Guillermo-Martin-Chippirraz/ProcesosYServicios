package Tema2.Actividades.Actividad7;

public class Monitor {
    private int numero = 0;
    private boolean disponible = false;

    private Monitor(){
        numero = 0;
    }

    public synchronized int consumir(){
        while (!disponible){
            try {
                wait();
            }catch (InterruptedException e){
                Thread.currentThread().interrupt();
            }
        }
        disponible = false;
        notifyAll();
        return numero;
    }

    public synchronized void producir(int num){
        while (disponible){
            try {
                wait();
            }catch (InterruptedException e){
                Thread.currentThread().interrupt();
            }
        }
        numero = num;
        disponible = true;
        notifyAll();
    }
}
