package Tema2.Practica.Actividad4;

public class Dispositivo {
    private final String nombre;
    private final int capacidad;
    private final Solicitud[] buffer;
    private int count = 0;

    public Dispositivo(){
        nombre = "";
        capacidad = 0;
        buffer = null;
    }

    public Dispositivo(String nombre, int capacidad){
        this.nombre = nombre;
        this.capacidad = capacidad;
        this.buffer = new Solicitud[capacidad];
    }

    public String getNombre() {
        return nombre;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public Solicitud[] getBuffer() {
        return buffer;
    }

    public synchronized boolean agregar(Solicitud s){
        if(count < capacidad){
            int index = count++;
            buffer[index] = s;
            notifyAll();
            return true;
        }
        return false;
    }

    public synchronized boolean reemplazarBajaPrioridad(Solicitud nueva){
        for (int i = 0; i < count; i++){
            if (buffer[i].esAltaPrioridad()){
                buffer[i] = nueva;
                notifyAll();
                return true;
            }
        }
        return false;
    }

    public synchronized Solicitud extraerAltaPrioridad(){
        for (int i = 0; i < count; i++){
            if (buffer[i].esAltaPrioridad()){
                Solicitud s = buffer[i];
                for (int j = i; j < count - 1; j++){
                    buffer[j] = buffer[j+1];
                }
                int index = count--;
                buffer[index] = null;
                return s;
            }
        }
        return null;
    }

    public synchronized boolean hayEspacio(){
        return count < capacidad;
    }

    public synchronized void procesarSolicitudes() throws InterruptedException{
        while (true){
            while (count == 0) wait();
            Solicitud s = buffer[0];
            for (int i = 0; i < count - 1; i++){
                buffer[i] = buffer[i + 1];
            }
            buffer[--count] = null;
            System.out.println(nombre + " procesando " + s.getId());
            Thread.sleep(s.getDuracion() * 100);
        }
    }
}
