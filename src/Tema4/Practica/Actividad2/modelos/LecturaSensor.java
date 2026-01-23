package Tema4.Practica.Actividad2.modelos;

import java.time.LocalDateTime;

public class LecturaSensor {
    private String idSensor;
    private LocalDateTime timestamp;
    private double valor;

    public LecturaSensor() {
        idSensor = "";
        timestamp = null;
        valor = -1;
    }

    public LecturaSensor(String idSensor, LocalDateTime timestamp, double valor) {
        setIdSensor(idSensor);
        setTimestamp(timestamp);
        setValor(valor);
    }

    public String getIdSensor() {
        return idSensor;
    }

    public void setIdSensor(String idSensor) {
        this.idSensor = idSensor;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }
}
