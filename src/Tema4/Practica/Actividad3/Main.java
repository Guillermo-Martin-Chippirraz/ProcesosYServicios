package Tema4.Practica.Actividad3;

/**
 * Clase Main para la Actividad 3 de la práctica del tema 4 que actúa como punto de entrada de la
 * aplicación cliente-servidor. Su función consiste en inicializar el servidor encargado de atender
 * las solicitudes de información sobre países y, tras un breve intervalo, lanzar un cliente que
 * interactuará con dicho servidor.
 *
 * Esta clase permite:
 * <ul>
 *     <li>Crear y ejecutar el servidor TCP en un hilo independiente.</li>
 *     <li>Garantizar un pequeño retardo para asegurar que el servidor esté completamente operativo.</li>
 *     <li>Crear y ejecutar un cliente que enviará peticiones al servidor.</li>
 * </ul>
 *
 * Su diseño facilita la ejecución inmediata del sistema completo sin necesidad de lanzar los
 * componentes por separado, permitiendo una prueba rápida y funcional del flujo de comunicación.
 *
 * @author Guillermo Martín Chippirraz
 * @version v4.4
 */
public class Main {

    /**
     * Método principal del programa.
     * La línea de ejecución sigue la siguiente secuencia:
     *
     * <ol>
     *     <li>
     *         <strong>Inicialización del servidor:</strong>
     *         <ul>
     *             <li>Se define el puerto TCP donde escuchará el servidor.</li>
     *             <li>Se crea una instancia de <code>Servidor</code>.</li>
     *             <li>Se ejecuta en un hilo independiente para permitir conexiones concurrentes.</li>
     *         </ul>
     *     </li>
     *
     *     <li>
     *         <strong>Pausa breve:</strong>
     *         <ul>
     *             <li>Se introduce un retardo de 500 ms para asegurar que el servidor esté activo antes de
     *             iniciar el cliente.</li>
     *         </ul>
     *     </li>
     *
     *     <li>
     *         <strong>Inicialización del cliente:</strong>
     *         <ul>
     *             <li>Se crea una instancia de <code>Cliente</code> apuntando al servidor local.</li>
     *             <li>Se ejecuta en un hilo independiente.</li>
     *         </ul>
     *     </li>
     * </ol>
     *
     * Este método no devuelve ningún valor y su ejecución continúa mientras los hilos del servidor
     * y del cliente permanezcan activos.
     *
     * @since v4.4
     * @see Servidor
     * @see Servidor#Servidor(int)
     * @see Cliente
     * @see Cliente#Cliente(String, int)
     */
    public static void main(String[] args) {

        int puerto = 5555;

        Servidor servidor = new Servidor(puerto);
        Thread tServidor = new Thread(servidor);
        tServidor.start();

        try {
            Thread.sleep(500);
        } catch (InterruptedException ignorar) {}

        Cliente cliente = new Cliente("localhost", puerto);
        Thread tCliente = new Thread(cliente);
        tCliente.start();
    }
}
