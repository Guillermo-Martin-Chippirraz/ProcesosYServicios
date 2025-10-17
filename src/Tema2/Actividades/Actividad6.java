package Tema2.Actividades;

public class Actividad6 implements Runnable{
    private String mensaje;
    private final Object lock;
    private final boolean esHola;
    private static boolean holaImpreso = false;

    public Actividad6(String mensaje, Object lock, boolean esHola){
        this.mensaje = mensaje;
        this.lock = lock;
        this.esHola = esHola;
    }

    public void run(){
        synchronized (lock){
            if (!esHola){
                while (!holaImpreso){
                    try {
                        lock.wait();
                    }catch (InterruptedException e){
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }

        System.out.println(mensaje);

        if (esHola){
            holaImpreso = true;
            lock.notify();
        }
    }
}
