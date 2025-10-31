package Tema2.Practica.Actividad4;

/**
 * Clase Gestor para la actividad 4 de la práctica del tema 2
 * @author Guillermo Martín Chippirraz
 * @version v2.5
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
     * Método para el procesamiento de solicitudes. Pasos:
     * <ol>
     *     <li>
     *         Se declara una variable «procesada» y se le asigna false
     *     </li>
     *     <li>
     *         Si la Solicitud introducida por parámetros es de alta prioridad:
     *         <ol>
     *             <li>
     *                 Se sincroniza el objeto «nvme», se comprueba si hay espacio y se agrega. Si ha sido posible pasar
     *                 por esos filtros de verigicación, la variable «procesasda» se asigna a true.
     *             </li>
     *             <li>
     *                 Se comprueba el valor de «procesada» y si es false, se sincroniza el objeto «hdd» y se actúa de
     *                 forma similar al caso del objeto «nvme».
     *             </li>
     *             <li>
     *                 En caso de que «procesada» siga siendo false, se reemplaza la prioridad de la solicitud a
     *                 prioridad baja y se cambia el valor de «procesada» a true.
     *             </li>
     *             <li>
     *                 Si no ha sido posible nada de esto, se sincroniza la solicitud y se la pone en espera.
     *             </li>
     *         </ol>
     *     </li>
     *     <li>
     *         En caso contrario, se intenta agregar al objeto «hdd» igual que antes y si no es posible, se pone en
     *         espera la solicitud.
     *     </li>
     * </ol>
     * @since v2.4
     * @see Solicitud#esAltaPrioridad()
     * @see Dispositivo#hayEspacio()
     * @see Dispositivo#agregar(Solicitud)
     * @see Dispositivo#reemplazarBajaPrioridad(Solicitud)
     * @param s Objeto de la clase solicitud que se va a intentar procesar
     * @throws InterruptedException En caso de interrupción del proceso se lanza una excepción
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
