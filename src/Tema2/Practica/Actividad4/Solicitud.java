package Tema2.Practica.Actividad4;

/**
 * Clase Solicitud para la actividad 4 de la práctica 2
 * @author Guillermo Martín Chippirraz
 * @version 2.5.1
 * @see Gestor
 */
public class Solicitud implements Runnable{
    private final String id;
    private final int duracion;
    private final boolean altaPrioridad;
    private final Gestor gestor;

    /**
     * Constructor por defecto.
     * @since v2.4.1
     */
    public Solicitud(){
        id = "";
        duracion = 0;
        altaPrioridad = false;
        gestor = null;
    }

    /**
     * Constructor por parámetros
     * @since v2.4.1
     * @param id String que se asignará al atributo id
     * @param duracion Entero que se asignará al atributo duracion
     * @param altaPrioridad Booleano que se asignará en el atributo altaPrioridad
     * @param gestor Objeto de la clase Gestor que se copiará superficialmente en el atributo gestor.
     */
    public Solicitud(String id, int duracion, boolean altaPrioridad, Gestor gestor){
        this.id = id;
        this.duracion = duracion;
        this.altaPrioridad = altaPrioridad;
        this.gestor = gestor;
    }

    /**
     * Getter del atributo id
     * @since v2.4
     * @return String almacenado en el atributo id
     */
    public String getId() {
        return id;
    }

    /**
     * Getter del atributo duracion
     * @since v2.4
     * @return Entero almacenado en el atributo duracion
     */
    public int getDuracion() {
        return duracion;
    }

    /**
     * Getter del atributo altaPrioridad
     * @since v2.4
     * @return Booleano almacenado en el atributo altaPrioridad
     */
    public boolean esAltaPrioridad() {
        return altaPrioridad;
    }

    /**
     * Getter del atributo gestor
     * @since v2.4
     * @return Objeto de la clase Gestor almacenado en el atributo gestor
     */
    public Gestor getGestor() {
        return gestor;
    }

    /**
     * Método para la ejecución continua del servidor. Pasos:
     * <ol>
     *     <li>
     *         Se inicia un bucle infinito que se ejecuta mientras el hilo no haya sido interrumpido.
     *     </li>
     *     <li>
     *         En cada iteración del bucle:
     *         <ol>
     *             <li>
     *                 Se llama al método procesarSolicitudes() del objeto Dispositivo almacenado en el atributo dispositivo.
     *             </li>
     *             <li>
     *                 Se bloquea el hilo durante 100 milisegundos para evitar un consumo excesivo de CPU.
     *             </li>
     *         </ol>
     *     </li>
     *     <li>
     *         Si se produce una excepción de tipo InterruptedException:
     *         <ol>
     *             <li>
     *                 Se marca el hilo como interrumpido mediante el método interrupt().
     *             </li>
     *             <li>
     *                 Se rompe el bucle para finalizar la ejecución del hilo de forma controlada.
     *             </li>
     *         </ol>
     *     </li>
     * </ol>
     * @since v2.4.2
     * @see Dispositivo#procesarSolicitudes()
     * @throws InterruptedException En caso de interrupción del hilo durante el procesamiento o la pausa
     */
    public void run(){
        try {
            gestor.procesarSolicitud(this);
        }catch (InterruptedException e){
            Thread.currentThread().interrupt();
        }
    }
}
