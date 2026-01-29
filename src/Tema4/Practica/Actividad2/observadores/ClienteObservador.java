package Tema4.Practica.Actividad2.observadores;

import Tema4.Practica.Actividad2.util.LoggerSistema;

import java.io.*;
import java.net.*;
import java.util.Scanner;

/**
 * Clase ClienteObservador, implementación de la interfaz Runnable, para la Actividad 2 de la práctica del tema 4 que
 * representa a un cliente observador capaz de interactuar con el servidor mediante dos canales diferenciados:
 * un canal TCP para operaciones de control (login, suscripciones y comandos) y un canal UDP para la recepción de
 * notificaciones en tiempo real (lecturas y alertas generadas por los sensores).
 *
 * Esta clase encapsula el comportamiento completo de un observador dentro del sistema distribuido, permitiendo:
 * <ul>
 *     <li>Establecer una conexión TCP con el servidor para autenticarse y enviar comandos.</li>
 *     <li>Escuchar de forma concurrente en un puerto UDP local para recibir notificaciones.</li>
 *     <li>Suscribirse y desuscribirse dinámicamente a sensores concretos.</li>
 *     <li>Finalizar la sesión mediante el comando <code>QUIT</code>.</li>
 * </ul>
 *
 * Su diseño combina concurrencia, comunicación en red y lectura interactiva desde consola, proporcionando una
 * simulación completa del comportamiento de un cliente real dentro del sistema.
 *
 * @author Guillermo Martín Chippirraz
 * @version v4.2
 */
public class ClienteObservador implements Runnable {

    private final String host;
    private final int puertoTcp;
    private final String usuario;
    private final String password;
    private final int puertoUdpLocal;

    /**
     * Constructor por parámetros.
     * Inicializa un nuevo cliente observador configurando la dirección del servidor, el puerto TCP para el canal de
     * control, las credenciales del usuario y el puerto UDP local donde se recibirán las notificaciones.
     *
     * @since v4.1.1
     * @param host Dirección del servidor al que se conectará el observador.
     * @param puertoTcp Puerto TCP del servidor destinado al canal de control.
     * @param usuario Nombre de usuario utilizado para el proceso de autenticación.
     * @param password Contraseña asociada al usuario.
     * @param puertoUdpLocal Puerto local donde el observador escuchará notificaciones UDP.
     */
    public ClienteObservador(String host, int puertoTcp, String usuario, String password, int puertoUdpLocal) {
        this.host = host;
        this.puertoTcp = puertoTcp;
        this.usuario = usuario;
        this.password = password;
        this.puertoUdpLocal = puertoUdpLocal;
    }

    /**
     * Implementación del método run de la interfaz Runnable.
     * La línea de ejecución se divide en dos bloques principales:
     *
     * <ol>
     *     <li>
     *         <strong>Inicialización del canal UDP:</strong>
     *         <ol>
     *             <li>Se crea un objeto <code>DatagramSocket</code> asociado al puerto UDP local del observador.</li>
     *             <li>Se lanza un hilo secundario encargado de escuchar continuamente mensajes UDP.</li>
     *             <li>
     *                 En cada iteración del hilo:
     *                 <ul>
     *                     <li>Se recibe un paquete UDP mediante <code>udp.receive()</code>.</li>
     *                     <li>Se extrae el contenido del mensaje y se muestra por consola.</li>
     *                     <li>Si ocurre una excepción, se detiene el hilo de escucha.</li>
     *                 </ul>
     *             </li>
     *         </ol>
     *     </li>
     *
     *     <li>
     *         <strong>Inicialización del canal TCP:</strong>
     *         <ol>
     *             <li>Se crea un objeto <code>Socket</code> y se conecta al servidor mediante <code>connect()</code>.</li>
     *             <li>Se inicializan los flujos de entrada y salida (<code>BufferedReader</code> y <code>PrintWriter</code>).</li>
     *             <li>Se declara un objeto <code>Scanner</code> para leer comandos desde consola.</li>
     *             <li>Se envía al servidor:
     *                 <ul>
     *                     <li>El tipo de cliente (<code>OBSERVADOR</code>).</li>
     *                     <li>Las credenciales de acceso (<code>LOGIN usuario password</code>).</li>
     *                     <li>El puerto UDP local (<code>UDPPORT</code>).</li>
     *                 </ul>
     *             </li>
     *             <li>Se inicia un bucle que permite al usuario introducir comandos:</li>
     *             <li>
     *                 En cada iteración:
     *                 <ul>
     *                     <li>Se muestra un menú con los comandos disponibles.</li>
     *                     <li>Se lee el comando desde consola y se envía al servidor.</li>
     *                     <li>Si el comando comienza por <code>QUIT</code>, se finaliza el bucle.</li>
     *                     <li>En caso contrario, se recibe y muestra la respuesta del servidor.</li>
     *                 </ul>
     *             </li>
     *         </ol>
     *     </li>
     * </ol>
     *
     * En caso de producirse una excepción durante la ejecución, se registra un mensaje de advertencia mediante
     * <code>LoggerSistema.warn()</code>.
     *
     * @since v4.1.1
     */
    @Override
    public void run() {
        try (DatagramSocket udp = new DatagramSocket(puertoUdpLocal)) {

            Thread listener = new Thread(() -> {
                byte[] buffer = new byte[1024];
                boolean activo = true;
                while (activo) {
                    try {
                        DatagramPacket p = new DatagramPacket(buffer, buffer.length);
                        udp.receive(p);
                        String msg = new String(p.getData(), 0, p.getLength());
                        System.out.println("[OBS " + usuario + " UDP] " + msg);
                    } catch (IOException e) {
                        activo = false;
                    }
                }
            });
            listener.start();

            try (Socket tcp = new Socket()) {
                tcp.connect(new InetSocketAddress(host, puertoTcp));
                PrintWriter out = new PrintWriter(tcp.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(tcp.getInputStream()));
                Scanner sc = new Scanner(System.in);

                out.println("OBSERVADOR");
                out.println("LOGIN " + usuario + " " + password);
                System.out.println("[OBS " + usuario + " TCP] " + in.readLine());

                out.println("UDPPORT " + puertoUdpLocal);

                boolean activo = true;
                while (activo) {
                    System.out.println("[" + usuario + "] Comandos: SUBSCRIBE <id>, UNSUBSCRIBE <id>, QUIT");
                    String cmd = sc.nextLine();
                    out.println(cmd);
                    if (cmd.toUpperCase().startsWith("QUIT")) {
                        activo = false;
                    } else {
                        String resp = in.readLine();
                        System.out.println("[OBS " + usuario + " TCP] " + resp);
                    }
                }
            }

        } catch (IOException e) {
            LoggerSistema.warn("Observador " + usuario + " error");
        }
    }
}
