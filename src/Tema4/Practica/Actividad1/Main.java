package Tema4.Practica.Actividad1;

import Tema4.Practica.Actividad1.modelos.Mensaje;

import java.util.HashMap;

/**
 * Clase principal del programa. Contiene el método main, punto de entrada de la aplicación.
 * Su función es inicializar el buzón de mensajes, crear el hilo del servidor y lanzar varios
 * clientes simulados que interactúan con él.
 *
 * La línea de ejecución es la siguiente:
 *
 * <ol>
 *     <li>
 *         Se declara e inicializa un objeto de la clase HashMap «buzon», cuya clave es un Integer
 *         que identifica el mensaje por orden de llegada, y cuyo valor es un objeto de la clase
 *         Mensaje. Este buzón será compartido por el servidor para almacenar los mensajes enviados
 *         por los clientes.
 *     </li>
 *
 *     <li>
 *         Se declara un objeto de la clase Thread «servidor», inicializándose mediante el
 *         constructor por parámetros de la clase Servidor. En dicho constructor:
 *         <ul>
 *             <li>Se pasa el buzón previamente creado.</li>
 *             <li>Se indica el host «localhost».</li>
 *             <li>Se especifica el puerto «5555».</li>
 *         </ul>
 *         A continuación, se invoca al método start() del hilo «servidor», iniciando su ejecución
 *         concurrente. El servidor queda a la espera de conexiones entrantes.
 *     </li>
 *
 *     <li>
 *         Se inicializa un bucle for que se ejecuta tres veces. En cada iteración:
 *         <ol>
 *             <li>
 *                 Se declara un objeto de la clase Thread «cliente», inicializándose mediante el
 *                 constructor por parámetros de la clase Cliente. En dicho constructor:
 *                 <ul>
 *                     <li>Se indica el host «localhost».</li>
 *                     <li>Se especifica el puerto «5555».</li>
 *                 </ul>
 *             </li>
 *             <li>
 *                 Se invoca al método start() del hilo «cliente», iniciando su ejecución concurrente.
 *                 Cada cliente se conectará al servidor, enviará peticiones y procesará las respuestas
 *                 según su propia lógica interna.
 *             </li>
 *         </ol>
 *     </li>
 * </ol>
 *
 * De esta forma, la clase Main coordina la creación del servidor y de varios clientes, simulando
 * un entorno de mensajería concurrente en el que múltiples usuarios interactúan con un único
 * servidor centralizado.
 *
 * @author Guillermo Martín Chippirraz
 * @version v4.0
 * @see Servidor
 * @see Cliente
 * @see Mensaje
 */
public class Main {
    public static void main(String[] args) {
        HashMap<Integer, Mensaje> buzon = new HashMap<>();
        Thread servidor = new Thread(new Servidor(buzon, "localhost", 5555));
        servidor.start();

        for (int i = 0; i < 3; i++) {
            Thread cliente = new Thread(new Cliente("localhost", 5555));
            cliente.start();
        }
    }
}
