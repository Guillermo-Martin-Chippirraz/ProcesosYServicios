package Tema4.Practica.Actividad2.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Clase utilitaria LoggerSistema para la Actividad 2 de la práctica del tema 4 que proporciona un mecanismo sencillo
 * y centralizado para la generación de mensajes de registro (logs) dentro del sistema. Permite imprimir mensajes
 * informativos, de advertencia y de error, cada uno acompañado de una marca temporal formateada de manera uniforme.
 *
 * Esta clase facilita el seguimiento del comportamiento del sistema durante su ejecución, permitiendo identificar
 * eventos relevantes, advertencias y fallos de forma clara y estructurada. Al tratarse de una clase de utilidad,
 * todos sus métodos son estáticos y no permite la creación de instancias.
 *
 * El uso de sincronización en los métodos garantiza que los mensajes se impriman de forma ordenada incluso en
 * entornos concurrentes, evitando solapamientos en la salida estándar.
 *
 * @author
 * Guillermo Martín Chippirraz
 * @version v4.2
 */
public final class LoggerSistema {

    /** Formato utilizado para generar las marcas temporales de los mensajes de registro. */
    private static final DateTimeFormatter formato =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * Constructor privado para evitar la instanciación de la clase, dado que únicamente contiene métodos estáticos.
     *
     * @since v4.1.1
     */
    private LoggerSistema() {}

    /**
     * Imprime un mensaje informativo en la salida estándar, precedido por la etiqueta <code>[INFO]</code> y una marca
     * temporal generada automáticamente.
     *
     * @since v4.1.1
     * @param mensaje String que contiene el mensaje informativo a registrar.
     */
    public static synchronized void info(String mensaje) {
        System.out.println("[INFO " + timestamp() + "] " + mensaje);
    }

    /**
     * Imprime un mensaje de advertencia en la salida estándar, precedido por la etiqueta <code>[WARN]</code> y una
     * marca temporal generada automáticamente.
     *
     * @since v4.1.1
     * @param mensaje String que contiene el mensaje de advertencia a registrar.
     */
    public static synchronized void warn(String mensaje) {
        System.out.println("[WARN " + timestamp() + "] " + mensaje);
    }

    /**
     * Imprime un mensaje de error en la salida de error estándar, precedido por la etiqueta <code>[ERROR]</code> y una
     * marca temporal generada automáticamente.
     *
     * @since v4.1.1
     * @param mensaje String que contiene el mensaje de error a registrar.
     */
    public static synchronized void error(String mensaje) {
        System.err.println("[ERROR " + timestamp() + "] " + mensaje);
    }

    /**
     * Genera una marca temporal formateada según el patrón definido en la clase.
     * Este método es utilizado internamente por los métodos de registro para añadir información temporal a cada
     * mensaje.
     *
     * @since v4.1.1
     * @return String que representa la fecha y hora actual formateada.
     */
    private static String timestamp() {
        return LocalDateTime.now().format(formato);
    }
}
