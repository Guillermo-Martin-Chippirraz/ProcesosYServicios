package Tema4.Practica.Actividad2.servidor;

import Tema4.Practica.Actividad2.modelos.Alerta;
import Tema4.Practica.Actividad2.modelos.LecturaSensor;
import Tema4.Practica.Actividad2.modelos.SensorInfo;
import Tema4.Practica.Actividad2.protocolo.MensajeProtocolo;
import Tema4.Practica.Actividad2.util.LoggerSistema;

import java.io.IOException;
import java.net.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * Clase ServidorDatosUDP, implementación de la interfaz Runnable, para la Actividad 2 de la práctica del tema 4 que
 * representa el servidor encargado de gestionar el canal de datos mediante el protocolo UDP. Este servidor recibe
 * lecturas enviadas por los sensores y distribuye dichas lecturas —o alertas derivadas de ellas— a los observadores
 * suscritos.
 *
 * Su función principal consiste en:
 * <ul>
 *     <li>Escuchar continuamente en un puerto UDP configurado.</li>
 *     <li>Recibir mensajes de tipo <code>DATA</code> enviados por los sensores.</li>
 *     <li>Validar la existencia del sensor asociado a la lectura.</li>
 *     <li>Generar objetos <code>LecturaSensor</code> y distribuirlos a los observadores.</li>
 *     <li>Detectar valores fuera de umbral y generar objetos <code>Alerta</code>.</li>
 *     <li>Enviar dichas alertas a los observadores suscritos mediante UDP.</li>
 * </ul>
 *
 * Este servidor complementa al ServidorControlTCP, formando juntos la arquitectura mixta TCP/UDP del sistema.
 * Mientras TCP gestiona el control y las suscripciones, UDP se encarga de la transmisión eficiente de datos en tiempo real.
 *
 * @author Guillermo Martín Chippirraz
 * @version v4.2
 */
public class ServidorDatosUDP implements Runnable {

    private final int puerto;
    private final Map<String, SensorInfo> sensores;
    private final Map<String, List<InetSocketAddress>> suscriptoresUdp;

    /**
     * Constructor por parámetros.
     * Inicializa el servidor UDP configurando el puerto de escucha y las estructuras compartidas que contienen la
     * información de sensores y suscriptores.
     *
     * @since v4.2
     * @param puerto Puerto UDP en el que el servidor escuchará mensajes entrantes.
     * @param sensores Mapa que relaciona identificadores de sensores con sus objetos SensorInfo.
     * @param suscriptoresUdp Mapa que relaciona sensores con las direcciones UDP de los observadores suscritos.
     */
    public ServidorDatosUDP(int puerto,
                            Map<String, SensorInfo> sensores,
                            Map<String, List<InetSocketAddress>> suscriptoresUdp) {
        this.puerto = puerto;
        this.sensores = sensores;
        this.suscriptoresUdp = suscriptoresUdp;
    }

    /**
     * Implementación del método run de la interfaz Runnable.
     * La línea de ejecución sigue la siguiente secuencia:
     *
     * <ol>
     *     <li>
     *         <strong>Inicialización del socket UDP:</strong>
     *         <ul>
     *             <li>Se crea un objeto <code>DatagramSocket</code> asociado al puerto configurado.</li>
     *             <li>Se registra en el log el inicio del servidor UDP.</li>
     *         </ul>
     *     </li>
     *
     *     <li>
     *         <strong>Bucle principal de escucha:</strong>
     *         <ul>
     *             <li>Se declara un buffer de bytes para recibir paquetes.</li>
     *             <li>En cada iteración se recibe un paquete mediante <code>socket.receive()</code>.</li>
     *             <li>Se extrae el mensaje recibido y se registra en el log.</li>
     *         </ul>
     *     </li>
     *
     *     <li>
     *         <strong>Procesamiento de mensajes:</strong>
     *         <ul>
     *             <li>Se divide el mensaje en tokens mediante <code>split()</code>.</li>
     *             <li>Si el mensaje comienza por <code>DATA</code>:
     *                 <ul>
     *                     <li>Se obtiene el identificador del sensor y el valor medido.</li>
     *                     <li>Se valida la existencia del sensor en el mapa.</li>
     *                     <li>Se genera un objeto <code>LecturaSensor</code>.</li>
     *                     <li>Se envía la lectura a los observadores suscritos.</li>
     *                     <li>Se comprueba si el valor está fuera de umbral.</li>
     *                     <li>Si es anómalo, se genera un objeto <code>Alerta</code> y se distribuye.</li>
     *                 </ul>
     *             </li>
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
        try (DatagramSocket socket = new DatagramSocket(puerto)) {
            LoggerSistema.info("ServidorDatosUDP en puerto " + puerto);
            byte[] buffer = new byte[1024];

            while (true) {
                DatagramPacket paquete = new DatagramPacket(buffer, buffer.length);
                socket.receive(paquete);
                String msg = new String(paquete.getData(), 0, paquete.getLength());
                LoggerSistema.info("UDP recibido: " + msg);

                String[] p = msg.split(" ");
                if ("DATA".equalsIgnoreCase(p[0])) {
                    String id = p[1];
                    double valor = Double.parseDouble(p[2]);
                    SensorInfo info = sensores.get(id);
                    if (info != null) {
                        LecturaSensor lectura = new LecturaSensor(id, LocalDateTime.now(), valor);
                        enviarNotificacion(socket, lectura);

                        boolean fuera = valor < info.getUmbralMin() || valor > info.getUmbralMax();
                        if (fuera) {
                            Alerta alerta = new Alerta(id, LocalDateTime.now(), valor, "FUERA_DE_UMBRAL");
                            enviarAlerta(socket, alerta);
                        }
                    }
                }
            }
        } catch (IOException e) {
            LoggerSistema.error("Error en ServidorDatosUDP");
        }
    }

    /**
     * Método privado encargado de enviar una notificación de lectura normal a todos los observadores suscritos al
     * sensor correspondiente. El envío se realiza mediante paquetes UDP.
     *
     * @since v4.2
     * @param socket Socket UDP utilizado para enviar los paquetes.
     * @param lectura Objeto de la clase LecturaSensor que contiene la información de la lectura recibida.
     * @throws IOException Si ocurre un error durante el envío del paquete.
     */
    private void enviarNotificacion(DatagramSocket socket, LecturaSensor lectura) throws IOException {
        List<InetSocketAddress> lista = suscriptoresUdp.get(lectura.getIdSensor());
        if (lista == null) {
            return;
        }
        String mensaje = MensajeProtocolo.notificacionLectura(
                lectura.getIdSensor(),
                lectura.getTimestamp(),
                lectura.getValor()
        );
        byte[] datos = mensaje.getBytes();
        for (InetSocketAddress addr : lista) {
            DatagramPacket p = new DatagramPacket(datos, datos.length, addr.getAddress(), addr.getPort());
            socket.send(p);
        }
    }

    /**
     * Método privado encargado de enviar una alerta a todos los observadores suscritos al sensor correspondiente.
     * El envío se realiza mediante paquetes UDP.
     *
     * @since v4.2
     * @param socket Socket UDP utilizado para enviar los paquetes.
     * @param alerta Objeto de la clase Alerta que contiene la información de la anomalía detectada.
     * @throws IOException Si ocurre un error durante el envío del paquete.
     */
    private void enviarAlerta(DatagramSocket socket, Alerta alerta) throws IOException {
        List<InetSocketAddress> lista = suscriptoresUdp.get(alerta.getIdSensor());
        if (lista == null) {
            return;
        }
        String mensaje = MensajeProtocolo.notificacionAlerta(
                alerta.getIdSensor(),
                alerta.getTimestamp(),
                alerta.getValor(),
                alerta.getDescripcion()
        );
        byte[] datos = mensaje.getBytes();
        for (InetSocketAddress addr : lista) {
            DatagramPacket p = new DatagramPacket(datos, datos.length, addr.getAddress(), addr.getPort());
            socket.send(p);
        }
    }
}
