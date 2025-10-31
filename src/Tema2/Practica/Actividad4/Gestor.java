package Tema2.Practica.Actividad4;

/**
 * Clase Gestor para la actividad 4 de la práctica del tema 2
 * @author Guillermo Martín Chippirraz
 * @version v2.5.1
 * @see Dispositivo
 * @see Solicitud
 */
public class Gestor {
    private final Dispositivo nvme;
    private final Dispositivo hdd;

    /**
     * Constructor por defecto
     * @since v2.4
     */
    public Gestor(){
        nvme = hdd = null;
    }

    /**
     * Constructor por parámetros
     * @since v2.4
     * @param nvme Objeto de la clase Dispositivo el cual será copiado superficialmente por el atributo nvme
     * @param hdd Objeto de la clase Dispositivo el cual será copiado superficialmente por el atributo hdd
     */
    public Gestor(Dispositivo nvme, Dispositivo hdd){
        this.nvme = nvme;
        this.hdd = hdd;
    }

    /**
     * Método para el procesamiento de solicitudes almacenadas en el buffer del dispositivo. Pasos:
     * <ol>
     *     <li>
     *         Se comprueba si el valor almacenado en el atributo «count» es mayor que cero, lo que indica que hay solicitudes pendientes.
     *     </li>
     *     <li>
     *         Si hay solicitudes:
     *         <ol>
     *             <li>
     *                 Se declara un objeto de la clase Solicitud y se le asigna la copia superficial del primer elemento del buffer.
     *             </li>
     *             <li>
     *                 Se inicia un bucle for que desplaza todos los elementos del buffer una posición hacia la izquierda, sobrescribiendo la solicitud procesada.
     *             </li>
     *             <li>
     *                 En la última posición del buffer (correspondiente a «count - 1»), se almacena una nueva instancia por defecto de la clase Solicitud.
     *             </li>
     *             <li>
     *                 Se decrementa el valor del atributo «count» en uno, actualizando el número de solicitudes pendientes.
     *             </li>
     *             <li>
     *                 Se declara una variable String «prioridad» que se inicializa como «Baja». Si la solicitud procesada es de alta prioridad, se cambia a «Alta».
     *             </li>
     *             <li>
     *                 Se imprime por pantalla el mensaje indicando que el dispositivo está procesando la solicitud, incluyendo su id y prioridad.
     *             </li>
     *             <li>
     *                 Se bloquea el hilo mediante el método sleep() durante el tiempo indicado por la duración de la solicitud multiplicada por 100.
     *             </li>
     *             <li>
     *                 Se llama al método notifyAll() para despertar a los hilos que pudieran estar esperando espacio en el buffer.
     *             </li>
     *         </ol>
     *     </li>
     * </ol>
     * @since v2.4
     * @see Solicitud#Solicitud()
     * @see Solicitud#esAltaPrioridad()
     * @see Solicitud#getId()
     * @see Solicitud#getDuracion()
     * @throws InterruptedException En caso de interrupción del hilo durante el procesamiento de la solicitud
     */
    public void procesarSolicitud(Solicitud s) throws InterruptedException {
        boolean procesada = false;

        synchronized (nvme) {
            if (nvme.hayEspacio()) {
                if (nvme.agregar(s)) {
                    procesada = true;
                    System.out.println("La solicitud con id " + s.getId() + " ha sido agregada al servidor NVMe");
                }
            }
        }

        if (!procesada) {
            synchronized (hdd) {
                if (hdd.hayEspacio()) {
                    if (hdd.agregar(s)) {
                        procesada = true;
                        System.out.println("La solicitud con id " + s.getId() + " ha sido agregada al servidor HDD");
                    }
                }

                // Si sigue sin procesarse y es de alta prioridad, intenta reemplazar
                if (!procesada && s.esAltaPrioridad()) {
                    if (hdd.reemplazarBajaPrioridad(s)) {
                        procesada = true;
                        System.out.println("La solicitud con id " + s.getId() + " ha reemplazado una solicitud de baja prioridad en HDD");
                    }
                }

                hdd.notifyAll(); // Notifica a servidores que puedan estar esperando
            }
        }

        if (!procesada) {
            synchronized (s) {
                s.wait();
                System.out.println("Solicitud " + s.getId() + " en espera.");
            }
        }
    }



    /**
     * Método para la reubicación de solicitudes. Pasos:
     * <ol>
     *     <li>
     *         Se declara la variable booleana «true» y se asigna el valor true.
     *     </li>
     *     <li>
     *         Se inicializa un bucle while con la variable «seguir» como condición:
     *         <ol>
     *             <li>
     *                 Se instancia una solicitud y se almacena un nulo.
     *             </li>
     *             <li>
     *                 Se sincroniza el objeto «hdd» y se almacena el resultado del método extraerAltaPrioridad() como
     *                 copia superficial al objeto «s»
     *             </li>
     *             <li>
     *                 Se declara una variable booleana «espacioNvme» y se asigna el valor false.
     *             </li>
     *             <li>
     *                 Se sincroniza el objeto «nvme» y se almacena en la variable «espacioNvme» el resultado del
     *                 método hayEspacio().
     *             </li>
     *             <li>
     *                 Si el objeto «s» es no nulo y hay espacio, se sincroniza el objeto «nvme» y se agrega el objeto
     *                 «s».
     *             </li>
     *             <li>
     *                 Si el objeto «s» es nulo o si no hay espacio, se pone la variable «seguir» a false.
     *             </li>
     *         </ol>
     *     </li>
     * </ol>
     * @since v2.4
     * @see Dispositivo#extraerAltaPrioridad()
     * @see Dispositivo#hayEspacio()
     * @see Dispositivo#agregar(Solicitud)
     */
    public void reubicarSolicitudes(){
        boolean seguir = true;

        while (seguir){
            Solicitud s = null;
            synchronized (hdd){
                s = hdd.extraerAltaPrioridad();
            }

            boolean espacioNvme = false;
            synchronized (nvme){
                espacioNvme = nvme.hayEspacio();
            }

            if (s != null && espacioNvme){
                synchronized (nvme){
                    nvme.agregar(s);
                }
            }


            if (s == null || !espacioNvme){
                seguir = false;
            }
        }
    }
}
