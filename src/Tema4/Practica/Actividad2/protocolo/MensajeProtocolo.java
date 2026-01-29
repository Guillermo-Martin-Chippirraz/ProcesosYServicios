package Tema4.Practica.Actividad2.protocolo;

import java.time.LocalDateTime;

/**
 * Clase utilitaria MensajeProtocolo para la Actividad 2 de la práctica del tema 4 que centraliza la construcción y
 * el análisis de los mensajes utilizados en el sistema de comunicación entre sensores, observadores y servidor.
 * Esta clase proporciona métodos estáticos destinados a generar mensajes formateados según el protocolo definido,
 * tanto para notificaciones de lecturas normales como para alertas, así como una función auxiliar para descomponer
 * mensajes recibidos.
 * Al ser una clase de utilidad, no permite instanciación y actúa como punto común para garantizar coherencia en el
 * formato de los mensajes intercambiados.
 *
 * @author Guillermo Martín Chippirraz
 * @version v4.2
 */
public final class MensajeProtocolo {

    /**
     * Constructor privado para evitar la instanciación de la clase, dado que únicamente contiene métodos estáticos.
     *
     * @since v4.1.1
     */
    private MensajeProtocolo() {}

    /**
     * Genera un mensaje de notificación de lectura normal siguiendo el formato del protocolo.
     * El mensaje resultante contiene el identificador del sensor, el instante de la lectura y el valor medido.
     *
     * @since v4.1.1
     * @param id String que identifica al sensor que genera la lectura.
     * @param ts Objeto de la clase LocalDateTime que representa el instante en que se tomó la lectura.
     * @param valor Double que cuantifica el valor medido por el sensor.
     * @return String formateado según el protocolo, representando una notificación de lectura.
     */
    public static String notificacionLectura(String id, LocalDateTime ts, double valor) {
        return TiposMensaje.NOTIFY + " " + id + " " + ts + " " + valor;
    }

    /**
     * Genera un mensaje de alerta siguiendo el formato del protocolo.
     * El mensaje resultante incluye el identificador del sensor, el instante de la detección, el valor anómalo y una
     * descripción asociada a la alerta.
     *
     * @since v4.1.1
     * @param id String que identifica al sensor que genera la alerta.
     * @param ts Objeto de la clase LocalDateTime que representa el instante en que se detectó la anomalía.
     * @param valor Double que cuantifica el valor anómalo detectado.
     * @param descripcion String que describe la naturaleza de la alerta.
     * @return String formateado según el protocolo, representando una alerta generada por un sensor.
     */
    public static String notificacionAlerta(String id, LocalDateTime ts, double valor, String descripcion) {
        return TiposMensaje.ALERT + " " + id + " " + ts + " " + valor + " " + descripcion;
    }

    /**
     * Método auxiliar para descomponer un mensaje recibido en sus distintas partes, separadas por espacios.
     * Facilita el análisis y procesamiento de los mensajes entrantes por parte del servidor o los clientes.
     *
     * @since v4.1.1
     * @param mensaje String que contiene el mensaje completo recibido.
     * @return Array de Strings resultante de dividir el mensaje por espacios.
     */
    public static String[] parsear(String mensaje) {
        return mensaje.split(" ");
    }
}
