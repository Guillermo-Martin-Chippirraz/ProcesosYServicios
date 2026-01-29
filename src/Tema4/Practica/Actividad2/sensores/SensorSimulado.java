package Tema4.Practica.Actividad2.sensores;

import Tema4.Practica.Actividad2.modelos.SensorInfo;
import Tema4.Practica.Actividad2.util.LoggerSistema;

import java.io.*;
import java.net.*;
import java.util.Random;

/**
 * Clase SensorSimulado, implementación de la interfaz Runnable, para la Actividad 2 de la práctica del tema 4 que
 * representa un sensor virtual capaz de interactuar con el sistema distribuido mediante dos canales diferenciados:
 * un canal TCP para su registro y configuración inicial, y un canal UDP para el envío periódico de lecturas.
 *
 * Esta clase permite simular el comportamiento de un sensor físico real, proporcionando:
 * <ul>
 *     <li>Registro automático en el servidor mediante TCP.</li>
 *     <li>Configuración de umbrales mínimo y máximo.</li>
 *     <li>Generación de valores aleatorios dentro de un rango determinado.</li>
 *     <li>Envío periódico de lecturas mediante paquetes UDP.</li>
 * </ul>
 *
 * Su uso facilita la validación del sistema sin necesidad de hardware real, permitiendo probar la recepción de datos,
 * la detección de alertas y la distribución de notificaciones a observadores.
 *
 * @author Guillermo Martín Chippirraz
 * @version v4.2
 */
public class SensorSimulado implements Runnable {

    private final SensorInfo info;
    private final String host;
    private final int puertoTcp;
    private final int puertoUdpServidor;
    private final Random random = new Random();

    /**
     * Constructor por parámetros.
     * Inicializa un sensor simulado configurando su información básica, la dirección del servidor y los puertos
     * utilizados para la comunicación TCP y UDP.
     *
     * @since v4.2
     * @param info Objeto SensorInfo que contiene los datos estructurales del sensor.
     * @param host Dirección del servidor al que se conectará el sensor.
     * @param puertoTcp Puerto TCP utilizado para el registro y configuración.
     * @param puertoUdpServidor Puerto UDP del servidor al que se enviarán las lecturas.
     */
    public SensorSimulado(SensorInfo info, String host, int puertoTcp, int puertoUdpServidor) {
        this.info = info;
        this.host = host;
        this.puertoTcp = puertoTcp;
        this.puertoUdpServidor = puertoUdpServidor;
    }

    /**
     * Implementación del método run de la interfaz Runnable.
     * La línea de ejecución se divide en dos fases claramente diferenciadas:
     *
     * <ol>
     *     <li>
     *         <strong>Fase 1: Comunicación TCP (registro y configuración)</strong>
     *         <ol>
     *             <li>Se crea un objeto <code>Socket</code> y se conecta al servidor mediante <code>connect()</code>.</li>
     *             <li>Se inicializan los flujos de entrada y salida (<code>BufferedReader</code> y <code>PrintWriter</code>).</li>
     *             <li>Se envía el identificador de tipo <code>SENSOR</code>.</li>
     *             <li>Se envía el comando <code>REGISTER</code> con el id, tipo e intervalo del sensor.</li>
     *             <li>Se recibe y muestra la respuesta del servidor.</li>
     *             <li>Se envía el comando <code>CONFIG</code> con los umbrales mínimo y máximo.</li>
     *             <li>Se recibe y muestra la respuesta del servidor.</li>
     *         </ol>
     *         En caso de error, se registra un aviso mediante <code>LoggerSistema.warn()</code>.
     *     </li>
     *
     *     <li>
     *         <strong>Fase 2: Comunicación UDP (envío periódico de lecturas)</strong>
     *         <ol>
     *             <li>Se crea un objeto <code>DatagramSocket</code> para el envío de paquetes UDP.</li>
     *             <li>Se obtiene la dirección del servidor mediante <code>InetAddress.getByName()</code>.</li>
     *             <li>Se inicia un bucle infinito que:
     *                 <ul>
     *                     <li>Genera un valor aleatorio entre 0 y 100.</li>
     *                     <li>Construye un mensaje <code>DATA</code> con el id del sensor y el valor generado.</li>
     *                     <li>Envía el mensaje al servidor mediante un <code>DatagramPacket</code>.</li>
     *                     <li>Espera el intervalo configurado antes de enviar la siguiente lectura.</li>
     *                 </ul>
     *             </li>
     *         </ol>
     *         Si ocurre una excepción, se registra un aviso indicando fallo en el canal UDP.
     *     </li>
     * </ol>
     *
     * @since v4.2
     */
    @Override
    public void run() {
        try (Socket tcp = new Socket()) {
            tcp.connect(new InetSocketAddress(host, puertoTcp));
            PrintWriter out = new PrintWriter(tcp.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(tcp.getInputStream()));

            out.println("SENSOR");
            out.println("REGISTER " + info.getId() + " " + info.getTipo() + " " + info.getIntervaloMs());
            String resp = in.readLine();
            System.out.println("[Sensor " + info.getId() + "] " + resp);

            out.println("CONFIG " + info.getId() + " " + info.getUmbralMin() + " " + info.getUmbralMax());
            resp = in.readLine();
            System.out.println("[Sensor " + info.getId() + "] " + resp);

        } catch (IOException e) {
            LoggerSistema.warn("Sensor " + info.getId() + " fallo TCP");
        }

        try (DatagramSocket udp = new DatagramSocket()) {
            InetAddress dirServidor = InetAddress.getByName(host);
            while (true) {
                double valor = random.nextDouble(100);
                String msg = "DATA " + info.getId() + " " + valor;
                byte[] datos = msg.getBytes();
                DatagramPacket p = new DatagramPacket(datos, datos.length, dirServidor, puertoUdpServidor);
                udp.send(p);
                Thread.sleep(info.getIntervaloMs());
            }
        } catch (Exception e) {
            LoggerSistema.warn("Sensor " + info.getId() + " fallo UDP");
        }
    }
}
