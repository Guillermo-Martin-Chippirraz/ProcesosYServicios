package Tema4.Practica.Actividad2.modelos;

import java.time.LocalDateTime;

/**
 * Modelo LecturaSensor para la Actividad 2 de la práctica del tema 4 que representa un valor normal medido por un
 * sensor en un instante concreto del tiempo.
 * Esta clase encapsula la información básica asociada a una lectura válida generada por un sensor registrado en el
 * sistema, incluyendo su identificador, el momento de la medición y el valor numérico obtenido.
 * Representa el caso general de funcionamiento correcto del sensor, en contraposición a la clase Alerta, que modela
 * situaciones anómalas o fuera de umbral.
 *
 * @author Guillermo Martín Chippirraz
 * @version v4.2
 */
public class LecturaSensor {

    private final String idSensor;
    private final LocalDateTime timestamp;
    private final double valor;

    /**
     * Constructor por parámetros.
     * Inicializa una nueva lectura asociada a un sensor concreto, registrando tanto el instante de la medición como
     * el valor obtenido.
     *
     * @since v4.1.1
     * @param idSensor String que identifica de forma única al sensor que genera la lectura.
     * @param timestamp Objeto de la clase LocalDateTime que representa el instante exacto en que se toma la lectura.
     * @param valor Double que cuantifica el valor medido por el sensor en el momento indicado.
     */
    public LecturaSensor(String idSensor, LocalDateTime timestamp, double valor) {
        this.idSensor = idSensor;
        this.timestamp = timestamp;
        this.valor = valor;
    }

    /**
     * Getter del atributo idSensor.
     * Devuelve el identificador del sensor que generó esta lectura.
     *
     * @since v4.1.1
     * @return String almacenado en el atributo idSensor.
     */
    public String getIdSensor() { return idSensor; }

    /**
     * Getter del atributo timestamp.
     * Proporciona el instante temporal en el que se registró la lectura.
     *
     * @since v4.1.1
     * @return Objeto de la clase LocalDateTime almacenado en el atributo timestamp.
     */
    public LocalDateTime getTimestamp() { return timestamp; }

    /**
     * Getter del atributo valor.
     * Devuelve el valor numérico medido por el sensor.
     *
     * @since v4.1.1
     * @return Racional almacenado en el atributo valor.
     */
    public double getValor() { return valor; }
}
