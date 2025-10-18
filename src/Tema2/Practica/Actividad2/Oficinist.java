package Tema2.Practica.Actividad2;

/**
 * Clase Oficinista para la práctica del Tema 2
 * @author Guillermo Martín Chippirraz
 * @version v2.2
 * @see Store
 */
public class Oficinist implements Runnable{
    private final Store store;
    public int packsToStore;

    /**
     * Constructor por defecto
     * @since v2.2
     */
    public Oficinist(){
        store = new Store();
        packsToStore = 0;
    }

    /**
     * Constructor por parámetros
     * @since v2.2
     * @see #setPacksToStore(int)
     * @param store Objeto de la clase Store a almacenar en el atributo store
     * @param packsToStore Entero a enviar al setter del atributo packsToStore
     */
    public Oficinist(Store store, int packsToStore){
        this.store = store;
        setPacksToStore(packsToStore);
    }

    /**
     * Getter del atributo packsToStore
     * @since v2.2
     * @return Entero almacenado en el atributo packsToStore
     */
    public int getPacksToStore(){
        return packsToStore;
    }

    /**
     * Setter del atributo packsToStore
     * @since v2.2
     * @param packsToStore
     */
    public void setPacksToStore(int packsToStore){
        this.packsToStore = packsToStore;
    }

    /**
     * Implementación del método run de la interfaz Runnable.
     * Comprueba cuántos paquetes quedan por almacenar y, si es mayor que cero, almacena los paquetes de uno en uno,
     * reduciendo en uno la cantidad de paquetes a almacenar y se produce una espera de 10 segundos entre operaciones,
     * simulando la torpeza del oficinista y la calidad del programa que usa para asignar el paquete en el almacén.
     * Envía un mensaje de confirmación si todos los paquetes han sido correctamente entregados.
     *
     * @since v2.2
     * @see #getPacksToStore()
     * @see Store#agregarPaquete()
     * @see #setPacksToStore(int)
     */
    public void run(){
        while (getPacksToStore() > 0){
            store.agregarPaquete();
            setPacksToStore(getPacksToStore() - 1);
            try {
                Thread.sleep(10000);
            }catch (InterruptedException e){
                Thread.currentThread().interrupt();
            }
        }

        if (getPacksToStore() == 0)
            System.out.println("Todos los paquetes han sido almacenados correctamente.");
    }
}
