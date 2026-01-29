package Tema4.Practica.Actividad2;

import Tema4.Practica.Actividad2.modelos.SensorInfo;
import Tema4.Practica.Actividad2.observadores.ClienteObservador;
import Tema4.Practica.Actividad2.sensores.SensorSimulado;
import Tema4.Practica.Actividad2.servidor.ServidorControlTCP;
import Tema4.Practica.Actividad2.servidor.ServidorDatosUDP;
import Tema4.Practica.Actividad2.util.Puertos;

/**
 * Clase Main para la Actividad 2 de la práctica del tema 4 que actúa como punto de entrada del sistema distribuido.
 * Su función consiste en inicializar y poner en funcionamiento todos los componentes necesarios para simular un
 * entorno completo de monitorización basado en sensores y observadores, utilizando una arquitectura mixta TCP/UDP.
 *
 * Esta clase coordina:
 * <ul>
 *     <li>El servidor TCP encargado del plano de control (registro, login, suscripciones).</li>
 *     <li>El servidor UDP encargado del plano de datos (lecturas y alertas en tiempo real).</li>
 *     <li>La creación de sensores simulados que envían lecturas periódicas.</li>
 *     <li>La creación de observadores que se suscriben a sensores y reciben notificaciones.</li>
 * </ul>
 *
 * Su ejecución permite comprobar el funcionamiento completo del sistema sin necesidad de hardware real, facilitando
 * la validación del protocolo, la comunicación entre componentes y la gestión de eventos.
 *
 * @author Guillermo Martín Chippirraz
 * @version v4.2
 */
public class Main {

    /**
     * Método principal del programa.
     * La línea de ejecución sigue la siguiente secuencia:
     *
     * <ol>
     *     <li>
     *         <strong>Inicialización del host:</strong>
     *         <ul>
     *             <li>Se define la dirección del servidor como <code>localhost</code>.</li>
     *         </ul>
     *     </li>
     *
     *     <li>
     *         <strong>Arranque del ServidorControlTCP:</strong>
     *         <ul>
     *             <li>Se crea un objeto <code>ServidorControlTCP</code> con el host y el puerto TCP definido.</li>
     *             <li>Se ejecuta en un hilo independiente para permitir múltiples conexiones simultáneas.</li>
     *         </ul>
     *     </li>
     *
     *     <li>
     *         <strong>Arranque del ServidorDatosUDP:</strong>
     *         <ul>
     *             <li>Se crea un objeto <code>ServidorDatosUDP</code> utilizando el puerto UDP y las estructuras
     *             compartidas del servidor TCP (sensores y suscriptores).</li>
     *             <li>Se ejecuta en un hilo independiente para recibir y distribuir lecturas en tiempo real.</li>
     *         </ul>
     *     </li>
     *
     *     <li>
     *         <strong>Creación de sensores simulados:</strong>
     *         <ul>
     *             <li>Se instancian dos objetos <code>SensorInfo</code> con sus respectivos intervalos.</li>
     *             <li>Se crean hilos que ejecutan <code>SensorSimulado</code> para cada sensor.</li>
     *             <li>Los sensores se registran vía TCP y envían lecturas vía UDP.</li>
     *         </ul>
     *     </li>
     *
     *     <li>
     *         <strong>Creación de observadores:</strong>
     *         <ul>
     *             <li>Se crean dos clientes observadores con credenciales y puertos UDP distintos.</li>
     *             <li>Se ejecutan en hilos independientes.</li>
     *             <li>Los observadores pueden suscribirse a sensores y recibir notificaciones.</li>
     *         </ul>
     *     </li>
     * </ol>
     *
     * Este método no devuelve ningún valor y su ejecución continúa indefinidamente mientras los hilos del sistema
     * permanezcan activos.
     *
     * @since v4.1.1
     */
    public static void main(String[] args) {

        String host = "localhost";

        ServidorControlTCP servidorControl = new ServidorControlTCP(host, Puertos.TCP_CONTROL);
        Thread tControl = new Thread(servidorControl);
        tControl.start();

        ServidorDatosUDP servidorDatos = new ServidorDatosUDP(
                Puertos.UDP_DATOS,
                servidorControl.getSensores(),
                servidorControl.getSuscriptoresUdp()
        );
        Thread tDatos = new Thread(servidorDatos);
        tDatos.start();

        SensorInfo s1 = new SensorInfo("TEMP01", "temperatura", 2000);
        SensorInfo s2 = new SensorInfo("HUM01", "humedad", 3000);

        new Thread(new SensorSimulado(s1, host, Puertos.TCP_CONTROL, Puertos.UDP_DATOS)).start();
        new Thread(new SensorSimulado(s2, host, Puertos.TCP_CONTROL, Puertos.UDP_DATOS)).start();

        new Thread(new ClienteObservador(host, Puertos.TCP_CONTROL, "juan", "1234", 7001)).start();
        new Thread(new ClienteObservador(host, Puertos.TCP_CONTROL, "maria", "abcd", 7002)).start();
    }
}
