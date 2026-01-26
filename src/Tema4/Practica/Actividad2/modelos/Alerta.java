package Tema4.Practica.Actividad2.modelos;

import java.time.LocalDateTime;

public class Alerta {

    private final String idSensor;
    private final LocalDateTime timestamp;
    private final double valor;
    private final String descripcion;

    public Alerta(String idSensor, LocalDateTime timestamp, double valor, String descripcion) {
        this.idSensor = idSensor;
        this.timestamp = timestamp;
        this.valor = valor;
        this.descripcion = descripcion;
    }

    public String getIdSensor() { return idSensor; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public double getValor() { return valor; }
    public String getDescripcion() { return descripcion; }
}
