package Tema4.Practica.Actividad3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 * Clase Cliente, implementación de la interfaz Runnable, para la Actividad 3 de la práctica del tema 4 que
 * representa a un cliente capaz de interactuar con el servidor mediante un canal TCP. Su función principal es
 * solicitar información sobre países al servidor, validando primero la existencia del país y, posteriormente,
 * permitiendo al usuario realizar consultas específicas sobre dicho país.
 *
 * Esta clase encapsula el comportamiento completo de un cliente dentro del sistema, permitiendo:
 * <ul>
 *     <li>Establecer una conexión TCP con el servidor.</li>
 *     <li>Enviar el nombre de un país para su validación.</li>
 *     <li>Recibir la confirmación o error correspondiente.</li>
 *     <li>Realizar consultas sobre atributos del país validado (código, capital, moneda, etc.).</li>
 *     <li>Finalizar la sesión mediante el comando <code>QUIT</code>.</li>
 * </ul>
 *
 * Su diseño combina comunicación en red y lectura interactiva desde consola, proporcionando una simulación
 * completa del comportamiento de un cliente real que consulta información filtrada desde un servicio externo
 * (RESTCountries) a través del servidor.
 *
 * @author Guillermo Martín Chippirraz
 * @version v4.4
 */
public class Cliente implements Runnable {

    private final String host;
    private final int puerto;

    /**
     * Constructor por parámetros.
     * Inicializa un nuevo cliente configurando la dirección del servidor y el puerto TCP al que se conectará.
     *
     * @since v4.4
     * @param host Dirección del servidor al que se conectará el cliente.
     * @param puerto Puerto TCP del servidor destinado a recibir solicitudes.
     */
    public Cliente(String host, int puerto) {
        this.host = host;
        this.puerto = puerto;
    }

    /**
     * Implementación del método run de la interfaz Runnable.
     * La línea de ejecución sigue la siguiente secuencia:
     *
     * <ol>
     *     <li>
     *         <strong>Establecimiento de la conexión TCP:</strong>
     *         <ul>
     *             <li>Se crea un objeto <code>Socket</code> conectado al servidor.</li>
     *             <li>Se inicializan los flujos de entrada y salida (<code>BufferedReader</code> y <code>PrintWriter</code>).</li>
     *             <li>Se declara un objeto <code>Scanner</code> para leer datos desde consola.</li>
     *         </ul>
     *     </li>
     *
     *     <li>
     *         <strong>Solicitud inicial del país:</strong>
     *         <ul>
     *             <li>El servidor envía un mensaje solicitando el nombre del país.</li>
     *             <li>El cliente lo muestra por consola y envía el país introducido por el usuario.</li>
     *             <li>El servidor responde indicando si el país es válido (<code>OK</code>) o no.</li>
     *         </ul>
     *     </li>
     *
     *     <li>
     *         <strong>Fase de consultas:</strong>
     *         <ol>
     *             <li>Si el país es válido, el servidor envía un menú con las opciones disponibles.</li>
     *             <li>Se inicia un bucle interno donde el usuario introduce comandos.</li>
     *             <li>
     *                 En cada iteración:
     *                 <ul>
     *                     <li>El cliente envía el comando al servidor.</li>
     *                     <li>Recibe la respuesta filtrada (código, capital, moneda, etc.).</li>
     *                     <li>Si el usuario introduce <code>QUIT</code>, se abandona la fase de consultas.</li>
     *                 </ul>
     *             </li>
     *         </ol>
     *     </li>
     *
     *     <li>
     *         <strong>Persistencia del ciclo:</strong>
     *         <ul>
     *             <li>Tras finalizar las consultas, el cliente vuelve a solicitar un país.</li>
     *             <li>El proceso continúa hasta que el usuario cierre la aplicación.</li>
     *         </ul>
     *     </li>
     * </ol>
     *
     * En caso de producirse una excepción durante la ejecución, se muestra un mensaje informativo indicando la
     * desconexión del cliente.
     *
     * @since v4.4
     */
    @Override
    public void run() {
        try (Socket socket = new Socket(host, puerto)) {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            Scanner sc = new Scanner(System.in);

            while (true) {
                System.out.println(in.readLine());
                String pais = sc.nextLine();
                out.println(pais);

                String resp = in.readLine();
                System.out.println(resp);

                if (resp.equals("OK")) {
                    System.out.println(in.readLine());
                    boolean consultando = true;
                    while (consultando) {
                        String cmd = sc.nextLine();
                        out.println(cmd);

                        String r = in.readLine();
                        System.out.println(r);

                        if (cmd.equalsIgnoreCase("QUIT"))
                            consultando = false;
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Cliente desconectado");
        }
    }
}
