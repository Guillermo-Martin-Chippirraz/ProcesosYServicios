package Tema2.Practica.Actividad4;

public class Servidor implements Runnable{
    private final Dispositivo dispositivo;

    public Servidor(Dispositivo dispositivo){
        this.dispositivo = dispositivo;
    }

    public void run(){
        try {
            dispositivo.procesarSolicitudes();
        }catch (InterruptedException e){
            Thread.currentThread().interrupt();
        }
    }
}
