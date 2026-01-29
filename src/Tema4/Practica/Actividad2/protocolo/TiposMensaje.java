package Tema4.Practica.Actividad2.protocolo;

/**
 * Clase utilitaria TiposMensaje para la Actividad 2 de la práctica del tema 4 que centraliza todas las constantes
 * simbólicas utilizadas en el protocolo de comunicación entre sensores, observadores y servidor.
 * Esta clase define de forma unificada los distintos tipos de mensajes que pueden intercambiarse en el sistema,
 * garantizando coherencia en la nomenclatura y evitando el uso de literales dispersos en el código.
 * Al actuar como repositorio de constantes, facilita la mantenibilidad del protocolo y reduce la probabilidad de
 * errores tipográficos o inconsistencias en la comunicación.
 *
 * Los tipos definidos abarcan tanto mensajes de identificación, registro y configuración, como operaciones de
 * suscripción, envío de datos, notificaciones y alertas.
 *
 * @author Guillermo Martín Chippirraz
 * @version v4.2
 */
public final class TiposMensaje {

    /** Identificador del tipo de cliente sensor. */
    public static final String SENSOR = "SENSOR";

    /** Identificador del tipo de cliente observador. */
    public static final String OBSERVADOR = "OBSERVADOR";

    /** Mensaje utilizado para registrar un sensor en el sistema. */
    public static final String REGISTER = "REGISTER";

    /** Mensaje utilizado para configurar los umbrales de un sensor. */
    public static final String CONFIG = "CONFIG";

    /** Mensaje utilizado por los sensores para enviar lecturas normales. */
    public static final String DATA = "DATA";

    /** Mensaje utilizado por los observadores para iniciar sesión. */
    public static final String LOGIN = "LOGIN";

    /** Mensaje utilizado por los observadores para suscribirse a un sensor. */
    public static final String SUBSCRIBE = "SUBSCRIBE";

    /** Mensaje utilizado por los observadores para cancelar su suscripción a un sensor. */
    public static final String UNSUBSCRIBE = "UNSUBSCRIBE";

    /** Mensaje utilizado por los observadores para cerrar su sesión. */
    public static final String QUIT = "QUIT";

    /** Mensaje enviado por el servidor para notificar una lectura normal. */
    public static final String NOTIFY = "NOTIFY";

    /** Mensaje enviado por el servidor para notificar una alerta. */
    public static final String ALERT = "ALERT";

    /** Mensaje genérico para indicar errores en el protocolo. */
    public static final String ERROR = "ERROR";

    /**
     * Constructor privado para evitar la instanciación de la clase, dado que únicamente contiene constantes
     * estáticas relacionadas con el protocolo.
     *
     * @since v4.1.1
     */
    private TiposMensaje() {}
}
