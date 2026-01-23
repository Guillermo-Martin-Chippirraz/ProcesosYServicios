package Tema4.Practica.Actividad2.modelos;

public class SensorInfo {
    private String id;
    private String tipo;
    private int intervaloEnvioMs;
    private double umbralMin;
    private double umbralMax;

    public SensorInfo() {
        id = tipo = "";
        intervaloEnvioMs = -1;
        umbralMax = umbralMin = -1;
    }

    public SensorInfo(String id, String tipo, int intervaloEnvioMs, double umbralMin, double umbralMax) {
        setId(id);
        setTipo(tipo);
        setIntervaloEnvioMs(intervaloEnvioMs);
        setUmbralMin(umbralMin);
        setUmbralMax(umbralMax);
    }

    public SensorInfo(String id, String tipo, int intervalo) {
        setId(id);
        setTipo(tipo);
        setIntervaloEnvioMs(intervaloEnvioMs);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getIntervaloEnvioMs() {
        return intervaloEnvioMs;
    }

    public void setIntervaloEnvioMs(int intervaloEnvioMs) {
        this.intervaloEnvioMs = intervaloEnvioMs;
    }

    public double getUmbralMin() {
        return umbralMin;
    }

    public void setUmbralMin(double umbralMin) {
        if (umbralMax != -1 && umbralMax > umbralMin && umbralMin > 0)
            this.umbralMin = umbralMin;
        else this.umbralMin = -1;
    }

    public double getUmbralMax() {
        return umbralMax;
    }

    public void setUmbralMax(double umbralMax) {
        if (umbralMin != -1 && umbralMax > umbralMin && umbralMax > 0)
            this.umbralMax = umbralMax;
        else this.umbralMax = -1;
    }
}
