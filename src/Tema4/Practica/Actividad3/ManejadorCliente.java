package Tema4.Practica.Actividad3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Clase ManejadorCliente, implementación de la interfaz Runnable, para la Actividad 3 de la práctica del tema 4 que
 * representa el componente encargado de gestionar la comunicación TCP entre el servidor y un cliente individual.
 *
 * Su función principal consiste en procesar las solicitudes enviadas por el cliente, validar el país consultado a
 * través del servicio RESTCountries y ofrecer un conjunto de consultas específicas sobre dicho país. Este manejador
 * actúa como intermediario entre el cliente y la API externa, filtrando la información y devolviéndola en un formato
 * legible y simplificado.
 *
 * El manejador permite:
 * <ul>
 *     <li>Solicitar al cliente el nombre de un país.</li>
 *     <li>Validar la existencia del país mediante <code>RestCountriesAPI</code>.</li>
 *     <li>Enviar un menú de opciones si el país es válido.</li>
 *     <li>Responder a consultas sobre código, lenguaje, moneda, capital y teléfono.</li>
 *     <li>Finalizar la sesión cuando el cliente envía <code>QUIT</code>.</li>
 * </ul>
 *
 * Su diseño garantiza una interacción secuencial y controlada, manteniendo la conexión activa mientras el cliente
 * continúe realizando consultas válidas.
 *
 * @author Guillermo Martín Chippirraz
 * @version v4.4
 */
public class ManejadorCliente implements Runnable {

    private final Socket socket;

    /**
     * Constructor por parámetros.
     * Inicializa el manejador asociándolo al socket del cliente conectado.
     *
     * @since v4.4
     * @param socket Socket asociado al cliente que ha establecido la conexión con el servidor.
     */
    public ManejadorCliente(Socket socket) {
        this.socket = socket;
    }

    /**
     * Implementación del método run de la interfaz Runnable.
     * La línea de ejecución sigue la siguiente secuencia:
     *
     * <ol>
     *     <li>
     *         <strong>Inicialización de flujos:</strong>
     *         <ul>
     *             <li>Se crea un <code>BufferedReader</code> para recibir mensajes del cliente.</li>
     *             <li>Se crea un <code>PrintWriter</code> para enviar respuestas al cliente.</li>
     *         </ul>
     *     </li>
     *
     *     <li>
     *         <strong>Bucle principal de interacción:</strong>
     *         <ul>
     *             <li>El servidor solicita al cliente el nombre de un país.</li>
     *             <li>El cliente envía el nombre y el servidor lo procesa.</li>
     *             <li>Se consulta la API RESTCountries mediante <code>RestCountriesAPI.buscarPais()</code>.</li>
     *         </ul>
     *     </li>
     *
     *     <li>
     *         <strong>Validación del país:</strong>
     *         <ul>
     *             <li>Si el país existe, se envía <code>OK</code> y un menú de opciones.</li>
     *             <li>Si no existe, se envía <code>ERROR PAIS_NO_ENCONTRADO</code> y se finaliza la conexión.</li>
     *         </ul>
     *     </li>
     *
     *     <li>
     *         <strong>Fase de consultas:</strong>
     *         <ol>
     *             <li>Se inicia un bucle interno donde el cliente envía comandos.</li>
     *             <li>
     *                 En cada iteración:
     *                 <ul>
     *                     <li>Se interpreta el comando recibido.</li>
     *                     <li>Se responde con el atributo correspondiente del país.</li>
     *                     <li>Si el cliente envía <code>QUIT</code>, se envía <code>ADIOS</code> y se cierran ambos bucles.</li>
     *                     <li>Si el comando no es válido, se envía un mensaje de error.</li>
     *                 </ul>
     *             </li>
     *         </ol>
     *     </li>
     *
     *     <li>
     *         <strong>Cierre de la conexión:</strong>
     *         <ul>
     *             <li>Una vez finalizada la interacción, se cierra el socket del cliente.</li>
     *         </ul>
     *     </li>
     * </ol>
     *
     * En caso de producirse una excepción durante la ejecución, se muestra un mensaje informativo indicando la
     * desconexión del cliente.
     *
     * @since v4.4
     * @see CountryData
     * @see RestCountriesAPI#buscarPais(String)
     */
    @Override
    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            boolean activo = true;

            while (activo) {
                out.println("Introduce país: ");
                String linea = in.readLine();
                if (linea != null) {
                    String pais = linea.trim();
                    CountryData data = RestCountriesAPI.buscarPais(pais);

                    if (data != null) {
                        out.println("OK");
                        out.println("OPCIONES: CODIGO, LENGUAJE, MONEDA, CAPITAL, TELEFONO, QUIT");

                        boolean consultado = true;
                        while (consultado) {
                            String cmd = in.readLine();
                            if (cmd != null) {
                                switch (cmd.toUpperCase()) {
                                    case "CODIGO" -> out.println(data.codigo());
                                    case "LENGUAJE" -> out.println(data.lenguaje());
                                    case "MONEDA" -> out.println(data.moneda());
                                    case "CAPITAL" -> out.println(data.capital());
                                    case "TELEFONO" -> out.println(data.telefono());
                                    case "QUIT" -> {
                                        out.println("ADIOS");
                                        consultado = false;
                                        activo = false;
                                    }
                                    default -> out.println("ERROR OPCION NO VALIDA.");
                                }
                            } else {
                                consultado = false;
                            }
                        }
                    } else {
                        out.println("ERROR PAIS_NO_ENCONTRADO");
                        activo = false;
                    }
                }
            }
            socket.close();
        } catch (IOException e) {
            System.out.println("Cliente desconectado");
        }
    }
}
