package Tema4.Practica.Actividad2.modelos;

import java.time.LocalDateTime;

public class Alerta {
    private String idSensor;
    private LocalDateTime timestamp;
    private double valor;
    private String descripcion;

    public Alerta() {
        idSensor = descripcion = "";
        timestamp = null;
        valor = -1;
    }

    public Alerta(String idSensor, LocalDateTime timestamp, double valor, String descripcion) {
        setIdSensor(idSensor);
        setTimestamp(timestamp);
        setValor(valor);
        setDescripcion(descripcion);
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
