package Tema3.Practica.Actividad5;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketAddress;
import java.util.HashMap;
import java.util.Map;

/**
 * Clase que modela el servidor UDP para la Actividad 5 de la práctica del tema 3.
 *
 * @author Guillermo Martín Chippirraz
 * @version v3.5.2
 */
public class Servidor {
    public static final int PUERTO = 5555;
    private DatagramSocket socket;
    private Map<String, SocketAddress> clientes;

    /**
     * Constructor por defecto.
     * Inicializa el socket en el puerto indicado y la colección de clientes.
     *
     * <ol>
     *     <li>Se intenta inicializar el DatagramSocket con el puerto PUERTO.</li>
     *     <li>Se inicializa el HashMap clientes.</li>
     *     <li>Se imprime un mensaje indicando que el servidor está escuchando.</li>
     *     <li>En caso de excepción, se imprime la traza del error.</li>
     * </ol>
     *
     * @since v3.5
     */
    public Servidor(){
        try{
            socket = new DatagramSocket(PUERTO);
            clientes = new HashMap<>();
            System.out.println("Servidor UDP escuchando en puerto " + PUERTO);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Método iniciar.
     * Contiene el bucle principal del servidor, encargado de recibir datagramas y procesar
     * los comandos enviados por los clientes.
     *
     * La línea de ejecución es la siguiente:
     * <ol>
     *     <li>Se declara un buffer de bytes de tamaño 1024.</li>
     *     <li>Se abre un bucle while infinito.</li>
     *     <li>
     *         En cada iteración:
     *         <ol>
     *             <li>Se declara un DatagramPacket paquete asociado al buffer.</li>
     *             <li>Se invoca socket.receive(paquete) para recibir un mensaje.</li>
     *             <li>Se convierte el contenido del paquete a String.</li>
     *             <li>Se obtiene la dirección del remitente mediante getSocketAddress().</li>
     *             <li>
     *                 Se evalúa el contenido del mensaje:
     *                 <ol>
     *                     <li>
     *                         Si comienza por "JOIN:", se extrae el alias, se registra en el HashMap
     *                         clientes y se envía un mensaje de bienvenida al usuario y un aviso al resto.
     *                     </li>
     *                     <li>
     *                         Si comienza por "LEAVE:", se elimina el alias del HashMap y se avisa al resto.
     *                     </li>
     *                     <li>
     *                         En cualquier otro caso, se trata como un mensaje normal y se difunde a todos.
     *                     </li>
     *                 </ol>
     *             </li>
     *         </ol>
     *     </li>
     *     <li>En caso de excepción, se imprime la traza del error.</li>
     * </ol>
     *
     * @since v3.5
     */
    public void iniciar(){
        byte[] buffer = new byte[1024];
        try {
            while (true) {
                DatagramPacket paquete = new DatagramPacket(buffer, buffer.length);
                socket.receive(paquete);
                String mensaje = new String(paquete.getData(), 0, paquete.getLength());

                SocketAddress direccion = paquete.getSocketAddress();

                if (mensaje.startsWith("JOIN:")) {
                    String alias = mensaje.substring(5);
                    clientes.put(alias, direccion);
                    enviarATodos(">> " + alias + " se ha unido al chat.");
                    enviarMensaje("Bienvenido al chat, " + alias, direccion);

                } else if (mensaje.startsWith("LEAVE:")) {
                    String alias = mensaje.substring(6);
                    clientes.remove(alias);
                    enviarATodos(">> " + alias + " ha salido del chat.");

                } else {
                    enviarATodos(mensaje);
                }
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Método enviarATodos.
     * Envía un mensaje a todos los clientes registrados.
     *
     * <ol>
     *     <li>Se recorre el HashMap clientes mediante un bucle for-each.</li>
     *     <li>En cada iteración, se invoca enviarMensaje() con la dirección correspondiente.</li>
     * </ol>
     * @since v3.5
     * @param mensaje Mensaje a difundir.
     */
    public void enviarATodos(String mensaje){
        for (SocketAddress direccion : clientes.values()){
            enviarMensaje(mensaje, direccion);
        }
    }

    /**
     * Método enviarMensaje.
     * Envía un mensaje a un cliente concreto mediante un datagrama.
     *
     * <ol>
     *     <li>Se convierte el mensaje a un array de bytes.</li>
     *     <li>Se declara un DatagramPacket paquete incluyendo datos, longitud y dirección destino.</li>
     *     <li>Se invoca socket.send(paquete) para enviar el datagrama.</li>
     *     <li>En caso de excepción, se imprime la traza del error.</li>
     * </ol>
     * @since v3.5
     * @param mensaje Mensaje a enviar.
     * @param direccion Dirección del cliente destino.
     */
    public void enviarMensaje(String mensaje, SocketAddress direccion){
        try {
            byte[] datos = mensaje.getBytes();
            DatagramPacket paquete = new DatagramPacket(datos, datos.length, direccion);
            socket.send(paquete);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Método main.
     * Crea un objeto Servidor e invoca su método iniciar().
     * @since v3.5
     * @param args Argumentos de línea de comandos.
     */
    public static void main(String[] args) {
        new Servidor().iniciar();
    }
}
