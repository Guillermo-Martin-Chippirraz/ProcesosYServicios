package Tema4.Practica.Actividad2.modelos;

/**
 * Modelo SensorInfo para la Actividad 2 de la práctica del tema 4 que representa la información estructural y
 * operativa asociada a un sensor registrado en el sistema.
 * Esta clase encapsula los datos esenciales que definen a un sensor: su identificador único, el tipo de magnitud
 * que mide, el intervalo de muestreo y los umbrales mínimo y máximo que delimitan el rango considerado normal.
 * Constituye la base para la gestión de sensores dentro del sistema, permitiendo tanto su registro como la
 * configuración de parámetros de funcionamiento.
 *
 * @author Guillermo Martín Chippirraz
 * @version v4.2
 */
public class SensorInfo {

    private String id;
    private String tipo;
    private int intervaloMs;
    private double umbralMin;
    private double umbralMax;

    /**
     * Constructor por parámetros.
     * Inicializa un nuevo sensor con su identificador, tipo y frecuencia de muestreo, asignando además valores
     * iniciales por defecto para los umbrales mínimo y máximo.
     *
     * @since v4.1.1
     * @param id String que identifica de forma única al sensor dentro del sistema.
     * @param tipo String que describe la magnitud o categoría de medición del sensor.
     * @param intervaloMs Entero que representa el intervalo de muestreo en milisegundos.
     */
    public SensorInfo(String id, String tipo, int intervaloMs) {
        this.id = id;
        this.tipo = tipo;
        this.intervaloMs = intervaloMs;
        this.umbralMin = Double.MIN_VALUE;
        this.umbralMax = Double.MAX_VALUE;
    }

    /**
     * Getter del atributo id.
     * Devuelve el identificador único del sensor.
     *
     * @since v4.1.1
     * @return String almacenado en el atributo id.
     */
    public String getId() { return id; }

    /**
     * Getter del atributo tipo.
     * Proporciona la categoría o magnitud que mide el sensor.
     *
     * @since v4.1.1
     * @return String almacenado en el atributo tipo.
     */
    public String getTipo() { return tipo; }

    /**
     * Getter del atributo intervaloMs.
     * Indica el intervalo de muestreo configurado para el sensor.
     *
     * @since v4.1.1
     * @return Entero almacenado en el atributo intervaloMs.
     */
    public int getIntervaloMs() { return intervaloMs; }

    /**
     * Getter del atributo umbralMin.
     * Devuelve el valor mínimo permitido antes de considerar una lectura como anómala.
     *
     * @since v4.1.1
     * @return Racional almacenado en el atributo umbralMin.
     */
    public double getUmbralMin() { return umbralMin; }

    /**
     * Getter del atributo umbralMax.
     * Devuelve el valor máximo permitido antes de considerar una lectura como anómala.
     *
     * @since v4.1.1
     * @return Racional almacenado en el atributo umbralMax.
     */
    public double getUmbralMax() { return umbralMax; }

    /**
     * Setter del atributo umbralMin.
     * Permite modificar el umbral mínimo del sensor.
     *
     * @since v4.1.1
     * @param umbralMin Nuevo valor mínimo permitido para las lecturas del sensor.
     */
    public void setUmbralMin(double umbralMin) { this.umbralMin = umbralMin; }

    /**
     * Setter del atributo umbralMax.
     * Permite modificar el umbral máximo del sensor.
     *
     * @since v4.1.1
     * @param umbralMax Nuevo valor máximo permitido para las lecturas del sensor.
     */
    public void setUmbralMax(double umbralMax) { this.umbralMax = umbralMax; }
}
