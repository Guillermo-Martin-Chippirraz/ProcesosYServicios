package Tema4.Practica.Actividad2.modelos;

import java.time.LocalDateTime;

public class LecturaSensor {

    private final String idSensor;
    private final LocalDateTime timestamp;
    private final double valor;

    public LecturaSensor(String idSensor, LocalDateTime timestamp, double valor) {
        this.idSensor = idSensor;
        this.timestamp = timestamp;
        this.valor = valor;
    }

    public String getIdSensor() { return idSensor; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public double getValor() { return valor; }
}
