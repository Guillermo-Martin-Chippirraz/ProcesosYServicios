package Tema4.Practica.Actividad2.modelos;

import java.time.LocalDateTime;

/**
 * Modelo Alerta para la Actividad2 del tema 4 que representa una alerta relacionada con valores anómalos detectados
 * por un sensor.
 * @author Guillermo Martín Chippirraz
 * @version v4.2
 */
public class Alerta {

    private final String idSensor;
    private final LocalDateTime timestamp;
    private final double valor;
    private final String descripcion;

    /**
     * Constructor por parámetros.
     * @since v4.1.1
     * @param idSensor String que representa a un único sensor específico.
     * @param timestamp Objeto de la clase LocalDateTime que representa el instante en que se detecta la alerta.
     * @param valor Double que cuantifica el valor anómalo medido por el sensor.
     * @param descripcion String que describe la alerta emitida por el sensor con su respectivo id, timestamp y valor.
     */
    public Alerta(String idSensor, LocalDateTime timestamp, double valor, String descripcion) {
        this.idSensor = idSensor;
        this.timestamp = timestamp;
        this.valor = valor;
        this.descripcion = descripcion;
    }

    /**
     * Getter del atributo idSensor.
     * @since v4.1.1
     * @return String almacenado en el atributo idSensor.
     */
    public String getIdSensor() { return idSensor; }

    /**
     * Getter del atributo timestamp.
     * @since v4.1.1
     * @return Objeto de la clase LocalDateTime alnacenado en el atributo timestamp.
     */
    public LocalDateTime getTimestamp() { return timestamp; }

    /**
     * Getter del atributo valor.
     * @since v4.1.1
     * @return Racional almacenado en el atributo valor.
     */
    public double getValor() { return valor; }

    /**
     * Getter del atributo descripción.
     * @since v4.1.1
     * @return String almacenado en el atributo descripción.
     */
    public String getDescripcion() { return descripcion; }
}
