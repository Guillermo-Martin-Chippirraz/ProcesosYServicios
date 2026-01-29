package Tema4.Practica.Actividad2.servidor;

import Tema4.Practica.Actividad2.modelos.SensorInfo;
import Tema4.Practica.Actividad2.modelos.UsuarioObservador;
import Tema4.Practica.Actividad2.util.LoggerSistema;
import Tema4.Practica.Actividad2.util.Puertos;

import java.io.*;
import java.net.*;
import java.util.*;

/**
 * Clase ServidorControlTCP, implementación de la interfaz Runnable, para la Actividad 2 de la práctica del tema 4 que
 * representa el servidor encargado de gestionar el canal de control mediante el protocolo TCP. Este servidor actúa
 * como punto central de coordinación entre sensores y observadores, procesando operaciones de registro, autenticación,
 * configuración y suscripción.
 * Su función principal consiste en:
 * <ul>
 *     <li>Recibir conexiones entrantes de sensores y observadores.</li>
 *     <li>Diferenciar el tipo de cliente conectado.</li>
 *     <li>Gestionar el registro y configuración de sensores.</li>
 *     <li>Gestionar el login y las suscripciones de observadores.</li>
 *     <li>Proporcionar a los sensores el puerto UDP para el envío de datos.</li>
 *     <li>Registrar los puertos UDP de los observadores para recibir notificaciones.</li>
 * </ul>
 *
 * Este servidor forma parte del diseño mixto TCP/UDP del sistema, donde TCP se utiliza exclusivamente para operaciones
 * de control y UDP para la transmisión de datos y alertas en tiempo real.
 *
 * @author Guillermo Martín Chippirraz
 * @version v4.2
 */
public class ServidorControlTCP implements Runnable {

    private final String host;
    private final int puerto;

    private final Map<String, SensorInfo> sensores = new HashMap<>();
    private final Map<String, UsuarioObservador> observadores = new HashMap<>();
    private final Map<String, List<InetSocketAddress>> suscriptoresUdp = new HashMap<>();

    /**
     * Constructor por parámetros.
     * Inicializa el servidor configurando la dirección y el puerto TCP en el que escuchará conexiones entrantes.
     *
     * @since v4.2
     * @param host Dirección IP o nombre del host donde se ejecutará el servidor.
     * @param puerto Puerto TCP destinado al canal de control.
     */
    public ServidorControlTCP(String host, int puerto) {
        this.host = host;
        this.puerto = puerto;
    }

    /**
     * Getter del mapa de sensores registrados.
     *
     * @since v4.2
     * @return Mapa que relaciona identificadores de sensores con sus objetos SensorInfo.
     */
    public Map<String, SensorInfo> getSensores() { return sensores; }

    /**
     * Getter del mapa de suscriptores UDP.
     *
     * @since v4.2
     * @return Mapa que relaciona identificadores de sensores con listas de direcciones UDP de observadores.
     */
    public Map<String, List<InetSocketAddress>> getSuscriptoresUdp() { return suscriptoresUdp; }

    /**
     * Implementación del método run de la interfaz Runnable.
     * La línea de ejecución sigue la siguiente secuencia:
     *
     * <ol>
     *     <li>
     *         <strong>Inicialización del servidor TCP:</strong>
     *         <ul>
     *             <li>Se crea un objeto <code>ServerSocket</code>.</li>
     *             <li>Se vincula al host y puerto configurados mediante <code>bind()</code>.</li>
     *             <li>Se registra en el log el inicio del servidor.</li>
     *         </ul>
     *     </li>
     *
     *     <li>
     *         <strong>Bucle principal de aceptación:</strong>
     *         <ul>
     *             <li>El servidor permanece en escucha mediante <code>accept()</code>.</li>
     *             <li>Cada conexión entrante se delega a un nuevo hilo mediante una expresión lambda.</li>
     *         </ul>
     *     </li>
     *
     *     <li>
     *         <strong>Gestión de excepciones:</strong>
     *         <ul>
     *             <li>Si ocurre un error de E/S, se registra mediante <code>LoggerSistema.error()</code>.</li>
     *         </ul>
     *     </li>
     * </ol>
     *
     * @since v4.2
     */
    @Override
    public void run() {
        try (ServerSocket servidor = new ServerSocket()) {
            servidor.bind(new InetSocketAddress(host, puerto));
            LoggerSistema.info("ServidorControlTCP en " + host + ":" + puerto);

            while (true) {
                Socket cliente = servidor.accept();
                new Thread(() -> manejarCliente(cliente)).start();
            }
        } catch (IOException e) {
            LoggerSistema.error("Error en ServidorControlTCP");
        }
    }

    /**
     * Método privado encargado de gestionar una conexión TCP entrante, identificando el tipo de cliente
     * (sensor u observador) y delegando el procesamiento al método correspondiente.
     *
     * @since v4.2
     * @param cliente Socket asociado al cliente conectado.
     */
    private void manejarCliente(Socket cliente) {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
            PrintWriter out = new PrintWriter(cliente.getOutputStream(), true);

            String tipo = in.readLine();
            if ("SENSOR".equalsIgnoreCase(tipo)) {
                manejarSensorTCP(in, out, cliente);
            } else if ("OBSERVADOR".equalsIgnoreCase(tipo)) {
                manejarObservadorTCP(in, out, cliente);
            } else {
                out.println("ERROR UNKNOWN_TYPE");
                cliente.close();
            }
        } catch (IOException e) {
            LoggerSistema.warn("Cliente TCP desconectado");
        }
    }

    /**
     * Método privado encargado de gestionar la comunicación inicial con un sensor mediante TCP.
     * Procesa los comandos:
     * <ul>
     *     <li><strong>REGISTER</strong>: registra el sensor y devuelve el puerto UDP del servidor.</li>
     *     <li><strong>CONFIG</strong>: configura los umbrales mínimo y máximo del sensor.</li>
     * </ul>
     *
     * Tras esta fase inicial, el sensor enviará sus lecturas exclusivamente mediante UDP.
     *
     * @since v4.2
     * @param in Flujo de entrada asociado al sensor.
     * @param out Flujo de salida para enviar respuestas al sensor.
     * @param socket Socket del sensor conectado.
     * @throws IOException Si ocurre un error en la comunicación.
     */
    private void manejarSensorTCP(BufferedReader in, PrintWriter out, Socket socket) throws IOException {
        LoggerSistema.info("Sensor TCP desde " + socket.getInetAddress());

        String linea = in.readLine(); // REGISTER
        String[] p = linea.split(" ");
        String id = p[1];
        String tipo = p[2];
        int intervalo = Integer.parseInt(p[3]);
        SensorInfo info = new SensorInfo(id, tipo, intervalo);
        sensores.put(id, info);
        if (!suscriptoresUdp.containsKey(id)) {
            suscriptoresUdp.put(id, new ArrayList<>());
        }
        out.println("OK REGISTERED " + Puertos.UDP_DATOS);

        linea = in.readLine(); // CONFIG
        p = linea.split(" ");
        double min = Double.parseDouble(p[2]);
        double max = Double.parseDouble(p[3]);
        info.setUmbralMin(min);
        info.setUmbralMax(max);
        out.println("OK CONFIGURED");
    }

    /**
     * Método privado encargado de gestionar la comunicación con un observador mediante TCP.
     * Procesa los comandos:
     * <ul>
     *     <li><strong>LOGIN</strong>: registra o valida al observador.</li>
     *     <li><strong>UDPPORT</strong>: registra el puerto UDP donde escuchará notificaciones.</li>
     *     <li><strong>SUBSCRIBE</strong>: añade al observador a la lista de suscriptores de un sensor.</li>
     *     <li><strong>UNSUBSCRIBE</strong>: elimina al observador de la lista de suscriptores.</li>
     *     <li><strong>QUIT</strong>: finaliza la sesión del observador.</li>
     * </ul>
     *
     * @since v4.2
     * @param in Flujo de entrada asociado al observador.
     * @param out Flujo de salida para enviar respuestas al observador.
     * @param socket Socket del observador conectado.
     * @throws IOException Si ocurre un error en la comunicación.
     */
    private void manejarObservadorTCP(BufferedReader in, PrintWriter out, Socket socket) throws IOException {
        LoggerSistema.info("Observador TCP desde " + socket.getInetAddress());

        String login = in.readLine();
        String[] p = login.split(" ");
        String usuario = p[1];
        String pass = p[2];
        observadores.putIfAbsent(usuario, new UsuarioObservador(usuario, pass));
        out.println("OK LOGGED");

        String linea = in.readLine(); // UDPPORT <puerto>
        p = linea.split(" ");
        int puertoUdpObs = Integer.parseInt(p[1]);
        InetAddress dir = socket.getInetAddress();

        boolean activo = true;
        while (activo) {
            linea = in.readLine();
            if (linea == null) {
                activo = false;
            } else {
                String[] cmd = linea.split(" ");
                String c = cmd[0].toUpperCase();
                if ("SUBSCRIBE".equals(c)) {
                    String idSensor = cmd[1];
                    List<InetSocketAddress> lista = suscriptoresUdp.get(idSensor);
                    if (lista != null) {
                        lista.add(new InetSocketAddress(dir, puertoUdpObs));
                        out.println("OK SUBSCRIBED " + idSensor);
                    } else {
                        out.println("ERROR SENSOR_NOT_FOUND");
                    }
                } else if ("UNSUBSCRIBE".equals(c)) {
                    String idSensor = cmd[1];
                    List<InetSocketAddress> lista = suscriptoresUdp.get(idSensor);
                    if (lista != null) {
                        lista.removeIf(addr -> addr.getAddress().equals(dir) && addr.getPort() == puertoUdpObs);
                        out.println("OK UNSUBSCRIBED " + idSensor);
                    } else {
                        out.println("ERROR SENSOR_NOT_FOUND");
                    }
                } else if ("QUIT".equals(c)) {
                    out.println("BYE");
                    activo = false;
                } else {
                    out.println("ERROR UNKNOWN_COMMAND");
                }
            }
        }
    }
}
