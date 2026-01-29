package Tema4.Practica.Actividad2.servidor;

import Tema4.Practica.Actividad2.modelos.Alerta;
import Tema4.Practica.Actividad2.modelos.LecturaSensor;
import Tema4.Practica.Actividad2.protocolo.MensajeProtocolo;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;
import java.util.Map;

/**
 * Clase DistribuidorNotificaciones para la Actividad 2 de la práctica del tema 4 que se encarga de gestionar el envío
 * de notificaciones a los observadores suscritos a cada sensor. Su función principal consiste en distribuir tanto
 * lecturas normales como alertas a todos los clientes que hayan solicitado recibir información de un sensor concreto.
 *
 * Esta clase actúa como un componente intermedio entre el servidor y los observadores, garantizando que cada mensaje
 * generado por un sensor llegue únicamente a los destinatarios correspondientes. Además, gestiona la eliminación de
 * sockets inactivos o desconectados para mantener la integridad de las listas de suscriptores.
 *
 * Al centralizar la lógica de distribución, se mejora la cohesión del sistema y se evita duplicación de código en
 * otros módulos del servidor.
 *
 * @author Guillermo Martín Chippirraz
 * @version v4.2
 */
public class DistribuidorNotificaciones {

    private final Map<String, List<Socket>> suscriptores;

    /**
     * Constructor por parámetros.
     * Inicializa el distribuidor con la estructura de datos que almacena las listas de suscriptores asociadas a cada
     * sensor registrado en el sistema.
     *
     * @since v4.1.1
     * @param suscriptores Mapa que relaciona el identificador de cada sensor con la lista de sockets de los
     *                     observadores suscritos a él.
     */
    public DistribuidorNotificaciones(Map<String, List<Socket>> suscriptores) {
        this.suscriptores = suscriptores;
    }

    /**
     * Envía una notificación de lectura normal a todos los observadores suscritos al sensor correspondiente.
     * Si el sensor no tiene suscriptores, la operación no realiza ninguna acción.
     *
     * @since v4.1.1
     * @param lectura Objeto de la clase LecturaSensor que contiene la información de la lectura generada.
     */
    public void enviarLectura(LecturaSensor lectura) {
        List<Socket> lista = suscriptores.get(lectura.getIdSensor());
        if (lista == null) {
            return;
        }
        String mensaje = MensajeProtocolo.notificacionLectura(
                lectura.getIdSensor(),
                lectura.getTimestamp(),
                lectura.getValor()
        );
        enviarA(lista, mensaje);
    }

    /**
     * Envía una notificación de alerta a todos los observadores suscritos al sensor correspondiente.
     * Si el sensor no tiene suscriptores, la operación no realiza ninguna acción.
     *
     * @since v4.1.1
     * @param alerta Objeto de la clase Alerta que contiene la información de la anomalía detectada.
     */
    public void enviarAlerta(Alerta alerta) {
        List<Socket> lista = suscriptores.get(alerta.getIdSensor());
        if (lista == null) {
            return;
        }
        String mensaje = MensajeProtocolo.notificacionAlerta(
                alerta.getIdSensor(),
                alerta.getTimestamp(),
                alerta.getValor(),
                alerta.getDescripcion()
        );
        enviarA(lista, mensaje);
    }

    /**
     * Método auxiliar encargado de enviar un mensaje concreto a todos los sockets incluidos en la lista de
     * suscriptores. Si alguno de los sockets produce un error de E/S durante el envío, se considera desconectado y se
     * elimina de la lista para evitar intentos futuros de comunicación fallida.
     *
     * @since v4.1.1
     * @param lista Lista de sockets correspondiente a los observadores suscritos a un sensor.
     * @param mensaje String que contiene el mensaje formateado según el protocolo.
     */
    private void enviarA(List<Socket> lista, String mensaje) {
        int i = 0;
        while (i < lista.size()) {
            Socket socket = lista.get(i);
            boolean eliminar = false;
            try {
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                out.println(mensaje);
            } catch (IOException e) {
                eliminar = true;
            }
            if (eliminar) {
                lista.remove(i);
            } else {
                i = i + 1;
            }
        }
    }
}
