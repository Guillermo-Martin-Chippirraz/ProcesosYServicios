package Tema4.Practica.Actividad2.util;

/**
 * Clase utilitaria Puertos para la Actividad 2 de la práctica del tema 4 que centraliza la definición de los puertos
 * utilizados por el sistema para la comunicación entre los distintos componentes. Esta clase establece de forma
 * explícita los puertos asignados al canal de control mediante TCP y al canal de transmisión de datos mediante UDP.
 *
 * Su finalidad es proporcionar un punto único de referencia para la configuración de los puertos, evitando la
 * dispersión de valores numéricos en el código y facilitando la mantenibilidad del sistema. Al tratarse de una clase
 * de utilidad, únicamente contiene constantes estáticas y no permite la creación de instancias.
 *
 * La separación entre el puerto de control y el puerto de datos responde al diseño del sistema, que distingue entre
 * operaciones de gestión (registro, login, suscripciones) y operaciones de envío de lecturas o alertas en tiempo real.
 *
 * @author Guillermo Martín Chippirraz
 * @version v4.2
 */
public final class Puertos {

    /** Puerto utilizado para la comunicación de control mediante el protocolo TCP. */
    public static final int TCP_CONTROL = 5555;

    /** Puerto utilizado para la transmisión de datos y alertas mediante el protocolo UDP. */
    public static final int UDP_DATOS = 6000;

    /**
     * Constructor privado para evitar la instanciación de la clase, dado que únicamente contiene constantes estáticas.
     *
     * @since v4.2
     */
    private Puertos() {}
}
