package Tema4.Practica.Actividad1.modelos;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Clase modelo para Mensaje. Representa un mensaje real enviado por un usuario a otro y almacenado por el servidor.
 *
 * @author Guillermo Martín Chippirraz
 * @version v4.0
 */
public class Mensaje implements Serializable {
    private String origen;
    private String destino;
    private String asunto;
    private String contenido;
    private LocalDateTime fechaYHora;

    /**
     * Constructor por defecto.
     * @since v4.0
     */
    public Mensaje() {
        origen = destino = asunto = contenido = "";
        fechaYHora = null;
    }

    /**
     * Constructor por parámetros. Envía los diferentes parámetros a sus correspondientes setters.
     * @since v4.0
     * @see #setOrigen(String)
     * @see #setDestino(String)
     * @see #setAsunto(String)
     * @see #setContenido(String)
     * @see #setFechaYHora(LocalDateTime)
     * @param origen String que representa al usuario que envía el mensaje.
     * @param destino String que representa al usuario que recibirá el mensaje.
     * @param asunto String que titula el mensaje.
     * @param contenido String que representa el contenido del mensaje.
     * @param fechaYHora Objeto de la clase LocalDateTime que almacena la fecha y la hora a la que se envía el mensaje.
     */
    public Mensaje(String origen, String destino, String asunto, String contenido, LocalDateTime fechaYHora){
        setOrigen(origen);
        setDestino(destino);
        setAsunto(asunto);
        setContenido(contenido);
        setFechaYHora(fechaYHora);
    }

    /**
     * Getter del atributo origen.
     * @since v4.0
     * @return String almacenado en el atributo origen.
     */
    public String getOrigen() {
        return origen;
    }

    /**
     * Setter del atributo origen.
     * @since v4.0
     * @param origen String que se almacenará en el atributo origen.
     */
    public void setOrigen(String origen) {
        this.origen = origen;
    }


    /**
     * Getter del atributo destino.
     * @since v4.0
     * @return String almacenado en el atributo destino.
     */
    public String getDestino() {
        return destino;
    }

    /**
     * Setter del atributo destino.
     * @since v4.0
     * @param destino String que se almacenará en el atributo destino.
     */
    public void setDestino(String destino) {
        this.destino = destino;
    }


    /**
     * Getter del atributo asunto.
     * @since v4.0
     * @return String almacenado en el atributo asunto.
     */
    public String getAsunto() {
        return asunto;
    }

    /**
     * Setter del atributo asunto.
     * @since v4.0
     * @param asunto String que se almacenará en el atributo asunto.
     */
    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }


    /**
     * Getter del atributo contenido.
     * @since v4.0
     * @return String almacenado en el atributo contenido.
     */
    public String getContenido() {
        return contenido;
    }

    /**
     * Setter del atributo contenido.
     * @since v4.0
     * @param contenido String que se almacenará en el atributo contenido.
     */
    public void setContenido(String contenido) {
        this.contenido = contenido;
    }


    /**
     * Getter del atributo fechaYHora.
     * @since v4.0
     * @return Objeto de la clase LocalDateTime almacenado en el atributo fechaYHora.
     */
    public LocalDateTime getFechaYHora() {
        return fechaYHora;
    }

    /**
     * Setter del atributo fechaYHora.
     * @since v4.0
     * @param fechaYHora Objeto de la clase LocalDateTime que se almacenará en el atributo fechaYHora.
     */
    public void setFechaYHora(LocalDateTime fechaYHora) {
        this.fechaYHora = LocalDateTime.from(fechaYHora);
    }

    /**
     * Implementación del método correspondiente de la clase Object.
     * @return JSON con los elementos del mensaje.
     */
    @Override
    public String toString() {
        return "Mensaje{" +
                "origen='" + origen + '\'' +
                ", destino='" + destino + '\'' +
                ", asunto='" + asunto + '\'' +
                ", contenido='" + contenido + '\'' +
                ", fechaYHora=" + fechaYHora +
                '}';
    }
}
