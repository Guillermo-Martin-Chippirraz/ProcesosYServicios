package Tema2.Practica.Actividad4;

/**
 * Clase Servidor para la actividad 4 de la práctica 2
 * @author Guillermo Martín Chippirraz
 * @version v2.4
 * @see Dispositivo
 */
public class Servidor implements Runnable{
    private final Dispositivo dispositivo;

    /**
     * Constructor por parámteros
     * @since v2.4
     * @param dispositivo Objeto de la clase Dispositivo que se va a copiar superficialmente en el atributo dispositivo
     */
    public Servidor(Dispositivo dispositivo){
        this.dispositivo = dispositivo;
    }

    /**
     * Implementación del método run de la interfaz Runnable.
     * En el bloque try se instancia a través del atributo dispositivo al método procesarSolicitudes.
     * En el bloque catch, se interrumpe el hilo para controlar la excepción por interrupción del proceso.
     * @since v2.4
     * @see Dispositivo#procesarSolicitudes()
     */
    public void run(){
        try {
            dispositivo.procesarSolicitudes();
        }catch (InterruptedException e){
            Thread.currentThread().interrupt();
        }
    }
}
