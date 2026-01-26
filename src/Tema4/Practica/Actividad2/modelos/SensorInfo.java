package Tema4.Practica.Actividad2.modelos;

public class SensorInfo {

    private String id;
    private String tipo;
    private int intervaloMs;
    private double umbralMin;
    private double umbralMax;

    public SensorInfo(String id, String tipo, int intervaloMs) {
        this.id = id;
        this.tipo = tipo;
        this.intervaloMs = intervaloMs;
        this.umbralMin = Double.MIN_VALUE;
        this.umbralMax = Double.MAX_VALUE;
    }

    public String getId() { return id; }
    public String getTipo() { return tipo; }
    public int getIntervaloMs() { return intervaloMs; }

    public double getUmbralMin() { return umbralMin; }
    public double getUmbralMax() { return umbralMax; }

    public void setUmbralMin(double umbralMin) { this.umbralMin = umbralMin; }
    public void setUmbralMax(double umbralMax) { this.umbralMax = umbralMax; }
}
