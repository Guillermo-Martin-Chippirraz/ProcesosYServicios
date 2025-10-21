package Tema2.Practica.Actividad4;

public class Solicitud implements Runnable{
    private final String id;
    private final int duracion;
    private final boolean altaPrioridad;
    private final Gestor gestor;

    public Solicitud(String id, int duracion, boolean altaPrioridad, Gestor gestor){
        this.id = id;
        this.duracion = duracion;
        this.altaPrioridad = altaPrioridad;
        this.gestor = gestor;
    }

    public String getId() {
        return id;
    }

    public int getDuracion() {
        return duracion;
    }

    public boolean esAltaPrioridad() {
        return altaPrioridad;
    }

    public Gestor getGestor() {
        return gestor;
    }

    public void run(){
        try {
            gestor.procesarSolicitud(this);
        }catch (InterruptedException e){
            Thread.currentThread().interrupt();
        }
    }
}
