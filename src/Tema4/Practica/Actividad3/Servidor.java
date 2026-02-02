package Tema4.Practica.Actividad3;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Clase Servidor, implementación de la interfaz Runnable, para la Actividad 3 de la práctica del tema 4 que
 * representa el servidor encargado de gestionar el canal de comunicación TCP con los clientes. Su función
 * principal consiste en aceptar conexiones entrantes y delegar la atención de cada cliente a un hilo independiente
 * mediante la creación de instancias de <code>ManejadorCliente</code>.
 *
 * Este servidor permite:
 * <ul>
 *     <li>Escuchar de forma continua en un puerto TCP determinado.</li>
 *     <li>Aceptar múltiples conexiones concurrentes.</li>
 *     <li>Crear un hilo por cliente para garantizar la atención simultánea.</li>
 *     <li>Delegar la lógica de procesamiento de peticiones en <code>ManejadorCliente</code>.</li>
 * </ul>
 *
 * Su diseño facilita la escalabilidad del sistema, permitiendo que varios clientes consulten información sobre
 * países de manera paralela sin bloquear el funcionamiento del servidor.
 *
 * @author Guillermo Martín Chippirraz
 * @version v4.4
 */
public class Servidor implements Runnable {

    private final int puerto;

    /**
     * Constructor por parámetros.
     * Inicializa el servidor configurando el puerto TCP en el que permanecerá escuchando conexiones entrantes.
     *
     * @since v4.4
     * @param puerto Puerto TCP donde se iniciará el servidor.
     */
    public Servidor(int puerto) {
        this.puerto = puerto;
    }

    /**
     * Implementación del método run de la interfaz Runnable.
     * La línea de ejecución sigue la siguiente secuencia:
     *
     * <ol>
     *     <li>
     *         <strong>Inicialización del servidor TCP:</strong>
     *         <ul>
     *             <li>Se crea un objeto <code>ServerSocket</code> asociado al puerto configurado.</li>
     *             <li>Se muestra un mensaje indicando que el servidor está activo.</li>
     *         </ul>
     *     </li>
     *
     *     <li>
     *         <strong>Bucle principal de aceptación:</strong>
     *         <ul>
     *             <li>El servidor permanece en escucha mediante <code>accept()</code>.</li>
     *             <li>Cada vez que un cliente se conecta, se crea un nuevo hilo que ejecuta un
     *             <code>ManejadorCliente</code> para gestionar la sesión.</li>
     *             <li>Este enfoque permite atender múltiples clientes de forma concurrente.</li>
     *         </ul>
     *     </li>
     *
     *     <li>
     *         <strong>Gestión de excepciones:</strong>
     *         <ul>
     *             <li>Si ocurre un error de E/S, se muestra un mensaje informativo.</li>
     *         </ul>
     *     </li>
     * </ol>
     *
     * @since v4.4
     */
    @Override
    public void run() {
        try (ServerSocket servidor = new ServerSocket(puerto)) {
            System.out.println("Servidor en puerto " + puerto);

            while (true) {
                Socket cliente = servidor.accept();
                new Thread(new ManejadorCliente(cliente)).start();
            }
        } catch (IOException e) {
            System.out.println("Error en servidor");
        }
    }
}
