package Tema4.Practica.Actividad1;

import Tema4.Practica.Actividad1.modelos.Mensaje;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Implementación de la interfaz Runnable. Simula el servidor de un servicio de mensajería.
 *
 * @author Guillermo Martín Chippirraz
 * @see Mensaje
 * @version v4.0
 */
public class Servidor implements Runnable{
    private HashMap<Integer, Mensaje> buzon;
    private String host;
    private int puerto;

    /**
     * Constructor por defecto.
     * @since v4.0
     */
    public Servidor(){
        buzon = new HashMap<>();
        host = "";
        puerto = -1;
    }

    /**
     * Constructor por parámetros.
     * @since v4.0
     * @param buzon Hashmap<Integer, HashMap<String, Mensaje>> que identifica, por orden de llegada, los mensajes a
     *              almacenar por el servidor, enviados por los usuarios, y recuperados por el usuario correspondiente.
     * @param host String que representa el host desde el que el servidor estará escuchando.
     * @param puerto Entero que representa el puerto desde el que el servidor estará escuchando.
     */
    public Servidor(HashMap<Integer, Mensaje> buzon, String host, int puerto){
        this.buzon = buzon;
        this.host = host;
        this.puerto = puerto;
    }

    /**
     * Implementación del método run de la interfaz Runnable.
     * La línea de ejecución es la siguiente:
     * <ol>
     *     <li>
     *         En los resources del try, se declara un objeto de la clase ServerSocket «servidor»,
     *         inicializándose mediante el constructor por defecto. En este bloque:
     *         <ol>
     *             <li>
     *                 Se declara un objeto de la clase InetSocketAddress «addr», inicializándose a través
     *                 del constructor por parámetros con los atributos «host» y «puerto» como argumentos.
     *             </li>
     *             <li>
     *                 El ServerSocket «servidor» invoca al método bind, recibiendo el objeto
     *                 InetSocketAddress «addr» como parámetro, quedando así asociado a la dirección y puerto
     *                 especificados.
     *             </li>
     *             <li>
     *                 Se inicializa un bucle while infinito. En dicho bloque:
     *                 <ol>
     *                     <li>
     *                         Se declara un objeto de la clase Socket «cliente», inicializándose mediante
     *                         la copia superficial del objeto devuelto por la invocación al método accept()
     *                         del ServerSocket «servidor». Esta llamada bloquea la ejecución hasta que un
     *                         cliente establece conexión.
     *                     </li>
     *                     <li>
     *                         Se declara un objeto de la clase BufferedReader «entrada», inicializándose
     *                         mediante un InputStreamReader asociado al flujo de entrada del Socket «cliente».
     *                         Este objeto permitirá la lectura secuencial de cadenas enviadas por el cliente.
     *                     </li>
     *                     <li>
     *                         Se declara un objeto de la clase ObjectOutputStream «envio», inicializándose
     *                         con el flujo de salida del Socket «cliente». Este objeto permitirá la
     *                         serialización y envío de colecciones de objetos de tipo Mensaje.
     *                     </li>
     *                     <li>
     *                         Se declara un objeto de la clase PrintWriter «salida», inicializándose con el
     *                         flujo de salida del Socket «cliente» y habilitando el auto-flush. Este objeto
     *                         permite el envío de cadenas de texto al cliente.
     *                     </li>
     *                     <li>
     *                         Se muestra por consola un mensaje informativo indicando la dirección IP del
     *                         cliente conectado, obtenida mediante el método getInetAddress() del Socket
     *                         «cliente».
     *                     </li>
     *                     <li>
     *                         Se declara una variable String «nombre», inicializándose con el valor devuelto
     *                         por la lectura de la primera línea enviada por el cliente a través del
     *                         BufferedReader «entrada». Este valor identifica al usuario conectado.
     *                     </li>
     *                     <li>
     *                         Se declara una variable String «opcion», inicializándose con la lectura de la
     *                         siguiente línea enviada por el cliente, convirtiéndose inmediatamente a
     *                         minúsculas mediante el método toLowerCase(). Esta cadena representa la acción
     *                         solicitada por el cliente.
     *                     </li>
     *                     <li>
     *                         Se evalúa el contenido de «opcion» mediante una estructura switch. En función
     *                         del valor recibido:
     *                         <ol>
     *                             <li>
     *                                 Caso «consultar»:
     *                                 <ul>
     *                                     <li>Se invoca al método consultarBuzon(nombre), obteniendo una
     *                                     colección ArrayList&lt;Mensaje&gt; con los mensajes destinados al
     *                                     usuario.</li>
     *                                     <li>Se envía dicha colección al cliente mediante el
     *                                     ObjectOutputStream «envio».</li>
     *                                     <li>Se invoca al método flush() para asegurar el envío inmediato
     *                                     del objeto.</li>
     *                                 </ul>
     *                             </li>
     *                             <li>
     *                                 Caso «enviar»:
     *                                 <ul>
     *                                     <li>Se leen secuencialmente tres líneas adicionales desde
     *                                     «entrada», correspondientes al destino, asunto y contenido del
     *                                     mensaje.</li>
     *                                     <li>Se instancia un objeto de la clase Mensaje con los datos
     *                                     recibidos y la fecha/hora actual mediante LocalDateTime.now().</li>
     *                                     <li>Se invoca al método enviarMensaje(mensaje), almacenando el
     *                                     mensaje en el buzón del servidor.</li>
     *                                 </ul>
     *                             </li>
     *                             <li>
     *                                 Caso «q»:
     *                                 <ul>
     *                                     <li>Se muestra por consola un mensaje indicando el cierre de la
     *                                     conexión con el usuario identificado por «nombre».</li>
     *                                     <li>Se invoca al método close() del Socket «cliente», finalizando
     *                                     la comunicación con dicho usuario.</li>
     *                                 </ul>
     *                             </li>
     *                             <li>
     *                                 Caso por defecto:
     *                                 <ul>
     *                                     <li>Se envía al cliente un objeto Mensaje vacío mediante
     *                                     ObjectOutputStream «envio».</li>
     *                                     <li>Se fuerza el envío con flush().</li>
     *                                     <li>Se envía un mensaje de texto informando de que la opción no
     *                                     existe mediante el PrintWriter «salida».</li>
     *                                 </ul>
     *                             </li>
     *                         </ol>
     *                     </li>
     *                 </ol>
     *             </li>
     *         </ol>
     *     </li>
     * </ol>
     *
     * @since 4.0
     * @see #consultarBuzon(String)
     * @see Mensaje#Mensaje(String, String, String, String, LocalDateTime)
     * @see #enviarMensaje(Mensaje)
     */

    public void run() {
        try (ServerSocket servidor = new ServerSocket()){
            InetSocketAddress addr = new InetSocketAddress(host, puerto);
            servidor.bind(addr);

            while (true){
                Socket cliente = servidor.accept();
                BufferedReader entrada = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
                ObjectOutputStream envio = new ObjectOutputStream(cliente.getOutputStream());
                PrintWriter salida = new PrintWriter(cliente.getOutputStream(), true);

                System.out.println("Servidor conectado en " + cliente.getInetAddress());

                String nombre = entrada.readLine();

                String opcion = entrada.readLine().toLowerCase();

                switch (opcion){
                    case "consultar" -> {
                        ArrayList<Mensaje> mensajes = consultarBuzon(nombre);
                        envio.writeObject(mensajes);
                        envio.flush();
                    }
                    case "enviar" -> {
                        String destino = entrada.readLine();
                        String asunto = entrada.readLine();
                        String contenido = entrada.readLine();
                        Mensaje mensaje = new Mensaje(nombre, destino, asunto, contenido, LocalDateTime.now());
                        enviarMensaje(mensaje);
                    }
                    case "q" -> {
                        System.out.println("Cerrando conexión con el cliente " + nombre);
                        cliente.close();
                    }
                    default -> {
                        envio.writeObject(new Mensaje());
                        envio.flush();
                        salida.println("La opción no existe.");
                        salida.flush();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Método sincronizado para la recuperación de mensajes desde el buzón de forma concurrente.
     * Recorre toda la cadena de mensajes del atributo buzón y filtra usando el String introducido por parámetros.
     *
     * @since v4.0
     * @see Mensaje#getOrigen()
     * @param nombre String que representa el nombre. Clave para hallar los mensajes.
     * @return ArrayList<Mensaje> con los mensajes almacenados.
     */
    private synchronized ArrayList<Mensaje> consultarBuzon(String nombre){
        ArrayList<Mensaje> mensajes = new ArrayList<>();
        for (Integer id : buzon.keySet()){
            if (buzon.get(id).getOrigen().equals(nombre)){
                mensajes.add(buzon.get(id));
            }
        }
        return mensajes;
    }

    /**
     * Método sincronizado para el envío de mensajes.
     * Almacena un nuevo mensaje en el buzón.
     * @param mensaje Objeto de la clase mensaje a almacenar.
     */
    private synchronized void enviarMensaje(Mensaje mensaje) {
        buzon.put(buzon.size() + 1, mensaje);
    }
}
