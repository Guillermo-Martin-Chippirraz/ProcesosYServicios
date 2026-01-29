package Tema4.Practica.Actividad2.servidor;

import Tema4.Practica.Actividad2.modelos.Alerta;
import Tema4.Practica.Actividad2.modelos.LecturaSensor;
import Tema4.Practica.Actividad2.modelos.SensorInfo;
import Tema4.Practica.Actividad2.util.LoggerSistema;

import java.io.*;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * Clase ManejadorSensor, implementación de la interfaz Runnable, para la Actividad 2 de la práctica del tema 4 que
 * representa el componente encargado de gestionar la comunicación entre el servidor y un sensor conectado mediante
 * el protocolo TCP.
 *
 * Esta clase procesa los comandos enviados por el sensor, permitiendo:
 * <ul>
 *     <li>Registrar un nuevo sensor en el sistema.</li>
 *     <li>Configurar los umbrales mínimo y máximo del sensor.</li>
 *     <li>Recibir lecturas periódicas enviadas por el sensor.</li>
 *     <li>Detectar valores fuera de umbral y generar alertas.</li>
 *     <li>Distribuir lecturas y alertas a los observadores suscritos.</li>
 * </ul>
 *
 * Su función es esencial dentro del sistema distribuido, ya que actúa como intermediario entre los sensores físicos
 * (o simulados) y el servidor central, garantizando la correcta interpretación de los comandos y la propagación de
 * los datos hacia los observadores.
 *
 * @author Guillermo Martín Chippirraz
 * @version v4.2
 */
public class ManejadorSensor implements Runnable {

    private final Socket socket;
    private final Map<String, SensorInfo> sensores;
    private final Map<String, List<Socket>> suscriptores;
    private final DistribuidorNotificaciones distribuidor;

    /**
     * Constructor por parámetros.
     * Inicializa el manejador asociándolo al socket del sensor y a las estructuras compartidas del servidor que
     * almacenan la información de sensores registrados y las listas de suscriptores.
     *
     * @since v4.1.1
     * @param socket Socket asociado al sensor conectado.
     * @param sensores Mapa que almacena la información de todos los sensores registrados.
     * @param suscriptores Mapa que relaciona cada sensor con la lista de observadores suscritos.
     * @param distribuidor Objeto encargado de distribuir lecturas y alertas a los observadores.
     */
    public ManejadorSensor(Socket socket,
                           Map<String, SensorInfo> sensores,
                           Map<String, List<Socket>> suscriptores,
                           DistribuidorNotificaciones distribuidor) {

        this.socket = socket;
        this.sensores = sensores;
        this.suscriptores = suscriptores;
        this.distribuidor = distribuidor;
    }

    /**
     * Implementación del método run de la interfaz Runnable.
     * La línea de ejecución sigue la siguiente secuencia:
     *
     * <ol>
     *     <li>
     *         <strong>Inicialización de flujos:</strong>
     *         <ul>
     *             <li>Se crea un <code>BufferedReader</code> para recibir comandos del sensor.</li>
     *             <li>Se crea un <code>PrintWriter</code> para enviar respuestas al sensor.</li>
     *             <li>Se registra en el log la conexión del sensor.</li>
     *         </ul>
     *     </li>
     *
     *     <li>
     *         <strong>Bucle principal de escucha:</strong>
     *         <ul>
     *             <li>Se mantiene activo mientras el sensor continúe enviando datos.</li>
     *             <li>En cada iteración se lee una línea enviada por el sensor.</li>
     *             <li>Si la línea es nula, se interpreta como desconexión.</li>
     *         </ul>
     *     </li>
     *
     *     <li>
     *         <strong>Procesamiento de comandos:</strong>
     *         El comando recibido se identifica mediante su primera palabra:
     *
     *         <ol>
     *             <li>
     *                 <strong>REGISTER</strong>
     *                 <ul>
     *                     <li>Registra un nuevo sensor en el sistema.</li>
     *                     <li>Inicializa su lista de suscriptores si no existía.</li>
     *                     <li>Responde con <code>OK REGISTERED</code>.</li>
     *                 </ul>
     *             </li>
     *
     *             <li>
     *                 <strong>CONFIG</strong>
     *                 <ul>
     *                     <li>Configura los umbrales mínimo y máximo del sensor.</li>
     *                     <li>Si el sensor no existe, responde con <code>ERROR SENSOR_NOT_FOUND</code>.</li>
     *                 </ul>
     *             </li>
     *
     *             <li>
     *                 <strong>DATA</strong>
     *                 <ul>
     *                     <li>Recibe una lectura enviada por el sensor.</li>
     *                     <li>Genera un objeto <code>LecturaSensor</code> con la información recibida.</li>
     *                     <li>Distribuye la lectura a los observadores suscritos.</li>
     *                     <li>Comprueba si el valor está fuera de umbral.</li>
     *                     <li>Si es anómalo, genera un objeto <code>Alerta</code> y lo distribuye.</li>
     *                 </ul>
     *             </li>
     *
     *             <li>
     *                 <strong>Comando desconocido</strong>
     *                 <ul>
     *                     <li>Responde con <code>ERROR UNKNOWN_COMMAND</code>.</li>
     *                 </ul>
     *             </li>
     *         </ol>
     *     </li>
     *
     *     <li>
     *         <strong>Gestión de excepciones:</strong>
     *         <ul>
     *             <li>Si ocurre un error de E/S, se registra la desconexión del sensor.</li>
     *         </ul>
     *     </li>
     * </ol>
     *
     * @since v4.1.1
     */
    @Override
    public void run() {
        BufferedReader entrada = null;
        PrintWriter salida = null;
        try {
            entrada = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            salida = new PrintWriter(socket.getOutputStream(), true);

            LoggerSistema.info("Sensor conectado desde " + socket.getInetAddress());

            boolean activo = true;
            while (activo) {
                String linea = entrada.readLine();
                if (linea == null) {
                    activo = false;
                } else {
                    LoggerSistema.info("Comando sensor: " + linea);
                    String[] p = linea.split(" ");
                    String comando = p[0].toUpperCase();

                    if ("REGISTER".equals(comando)) {
                        String id = p[1];
                        String tipo = p[2];
                        int intervalo = Integer.parseInt(p[3]);
                        sensores.put(id, new SensorInfo(id, tipo, intervalo));
                        if (!suscriptores.containsKey(id)) {
                            suscriptores.put(id, new java.util.ArrayList<>());
                        }
                        salida.println("OK REGISTERED");

                    } else if ("CONFIG".equals(comando)) {
                        String id = p[1];
                        double min = Double.parseDouble(p[2]);
                        double max = Double.parseDouble(p[3]);
                        SensorInfo info = sensores.get(id);
                        if (info != null) {
                            info.setUmbralMin(min);
                            info.setUmbralMax(max);
                            salida.println("OK CONFIGURED");
                        } else {
                            salida.println("ERROR SENSOR_NOT_FOUND");
                        }

                    } else if ("DATA".equals(comando)) {
                        String id = p[1];
                        double valor = Double.parseDouble(p[2]);
                        SensorInfo info = sensores.get(id);
                        if (info != null) {
                            LecturaSensor lectura = new LecturaSensor(id, LocalDateTime.now(), valor);
                            distribuidor.enviarLectura(lectura);

                            boolean fuera = valor < info.getUmbralMin() || valor > info.getUmbralMax();
                            if (fuera) {
                                Alerta alerta = new Alerta(id, LocalDateTime.now(), valor, "FUERA_DE_UMBRAL");
                                distribuidor.enviarAlerta(alerta);
                            }
                        } else {
                            salida.println("ERROR SENSOR_NOT_FOUND");
                        }

                    } else {
                        salida.println("ERROR UNKNOWN_COMMAND");
                    }
                }
            }

        } catch (IOException e) {
            LoggerSistema.warn("Sensor desconectado.");
        }
    }
}
