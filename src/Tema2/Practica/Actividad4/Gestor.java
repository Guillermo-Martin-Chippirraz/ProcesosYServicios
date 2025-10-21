package Tema2.Practica.Actividad4;

/**
 * Clase Gestor para la actividad 4 de la práctica del tema 2
 * @author Guillermo Martín Chippirraz
 * @version v2.4
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
     *                 Se sincroniza el objeto nvme, se comprueba
     *             </li>
     *         </ol>
     *     </li>
     * </ol>
     * @param s
     * @throws InterruptedException
     */
    public void procesarSolicitud(Solicitud s) throws InterruptedException{
        boolean procesada = false;
        if (s.esAltaPrioridad()){
            synchronized (nvme){
                if (nvme.hayEspacio()){
                    if(nvme.agregar(s))
                        procesada = true;
                }
            }

            if (!procesada){
                synchronized (hdd){
                    if (hdd.hayEspacio()){
                        if (hdd.agregar(s))
                            procesada = true;

                    }
                    if (!procesada){
                        if (hdd.reemplazarBajaPrioridad(s))
                            procesada = true;

                    }

                    if (!procesada){
                        synchronized (s){
                            s.wait();
                        }
                    }
                }
            }
        }else {
            synchronized (hdd){
                if (hdd.hayEspacio()){
                    if (hdd.agregar(s))
                        procesada = true;
                }

                if (!procesada){
                    synchronized (s){
                        s.wait();
                    }
                }
            }
        }
    }

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
