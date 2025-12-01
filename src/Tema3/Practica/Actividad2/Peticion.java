package Tema3.Practica.Actividad2;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Random;

/**
 * Clase auxiliar para la gestionar las peticiones de la biblioteca de la actividad 2 de la práctica del tema 3.
 * @author Guillermo Martín Chippirraz
 * @see Biblioteca.Libro
 * @version v3.2.1
 */
public class Peticion implements Serializable {
    private static Random random = new Random();
    private static ArrayList<Peticion> peticiones;
    private int idPeticion;
    private int idCliente;
    private Biblioteca.Libro solicitud;
    private LocalDateTime fechaPrestado;
    private long tiempoDePrestamo;
    private LocalDateTime fechaDeDevolucion;

    /**
     * Constructor por defecto.
     * @since v3.2.1
     */
    public Peticion(){
        idPeticion = idCliente = -1;
        solicitud = new Biblioteca.Libro();
        fechaDeDevolucion = fechaPrestado = null;
        tiempoDePrestamo = -1;
    }

    /**
     * Constructor por parámetros. Se envía el tamaño del ArrayList<Peticion> almacenado en el atributo peticiones
     * aumentado en 1 al método setIdPeticion(int).
     * Se envía el valor devuelto por el método estático now() de la clase LocalDateTime al método
     * setFechaPrestado(LocalDateTime). Se envía el valor devuelto por el método nextLong(int) del atributo estático
     * random con el entero 100 introducido como parámetro aumentado en 1. Se envía el long almacenado en el atributo
     * tiempoDePrestamo al método setFechaDeDevolucion(long).
     * Se agrega el nuevo objeto en el atributo peticiones.
     * @since v3.2.1
     * @see Biblioteca.Libro
     * @see #setIdPeticion(int)
     * @see #setIdCliente(int)
     * @see #setSolicitud(Biblioteca.Libro)
     * @see #setFechaPrestado(LocalDateTime)
     * @see #setTiempoDePrestamo(long)
     * @see #setFechaDeDevolucion(long)
     * @param idCliente Entero que se enviará al método setIdCliente(int).
     * @param solicitud Objeto de la clase estática Libro anidada a la clase Biblioteca
     */
    public Peticion(int idCliente, Biblioteca.Libro solicitud){
        setIdPeticion(peticiones.size() + 1);
        setIdCliente(idCliente);
        setSolicitud(solicitud);
        setFechaPrestado(LocalDateTime.now());
        setTiempoDePrestamo(random.nextLong(15000) + 10000);
        setFechaDeDevolucion(tiempoDePrestamo);
        peticiones.add(this);
    }

    /**
     * Getter del atributo idPeticion.
     * @since v3.2.1
     * @return Entero almacenado en el atributo idPeticion.
     */
    public int getIdPeticion() {
        return idPeticion;
    }

    /**
     * Setter del atributo idPeticion. Se evalúa si el entero introducido por parámetros es mayor que el tamaño del
     * atributo peticiones. En caso de ser así, se asigna dicho valor al atributo idPeticion. En caso contrario, se
     * asigna el valor por defecto.
     * @since v3.2.1
     * @param idPeticion Entero a validar y almacenar.
     */
    public void setIdPeticion(int idPeticion) {
        if (idPeticion > peticiones.size())
            this.idPeticion = idPeticion;
        else this.idPeticion = -1;
    }

    /**
     * Getter del atributo idCliente.
     * @since v3.2.1
     * @return Entero almacenado en el atributo idCliente.
     */
    public int getIdCliente() {
        return idCliente;
    }

    /**
     * Setter del atributo idCliente. Se evalúa si el entero introducido por parámetros es positivo. En caso de ser así
     * se almacena el entero en el atributo idCliente. En caso contrario, se almacena el valor por defecto.
     * @since v3.2.1
     * @param idCliente Entero a validar y almacenar.
     */
    public void setIdCliente(int idCliente) {
        if (idCliente > 0)
            this.idCliente = idCliente;
        else this.idCliente = -1;
    }

    /**
     * Getter del atributo solicitud
     * @since v3.2.1
     * @see Biblioteca.Libro
     * @return Objeto de la clase estática Libro anidada a la clase Biblioteca almacenada en el atributo solicitud.
     */
    public Biblioteca.Libro getSolicitud() {
        return solicitud;
    }

    /**
     * Setter del atributo solicitud. Evalúa si alguno de los atributos del objeto está vacío. En caso de ser así, se
     * almacena un nulo. En caso contrario, se almacena el objeto como copia superficial en el atributo solicitud.
     * @since v3.2.1
     * @see Biblioteca.Libro
     * @see Biblioteca.Libro#getTitulo()
     * @see Biblioteca.Libro#getGenero()
     * @param solicitud Objeto de la clase estática Libro anidada a la clase Biblioteca a validar y copiar
     *                  superficialmente en el atributo solicitud.
     */
    public void setSolicitud(Biblioteca.Libro solicitud) {
        if (!(solicitud.getTitulo().isEmpty() || solicitud.getGenero().isEmpty()))
            this.solicitud = solicitud;
        else this.solicitud = null;
    }

    /**
     * Getter del atributo fechaPrestado.
     * @since v3.2.1
     * @return Objeto de la clase LocalDateTime almacenado en el atributo fechaPrestado.
     */
    public LocalDateTime getFechaPrestado() {
        return fechaPrestado;
    }

    /**
     * Setter del atributo fechaPrestado.
     * @since v3.2.1
     * @param fechaPrestado Objeto de la clase LocalDateTime que se copiará en el atributo fechaPrestado a través del
     *                      método estático from(TemporalAccesor).
     */
    public void setFechaPrestado(LocalDateTime fechaPrestado) {
        this.fechaPrestado = LocalDateTime.from(fechaPrestado);
    }

    /**
     * Getter del atributo tiempoDePrestamo.
     * @since v3.2.1
     * @return Long almacenado en el atributo tiempoDePrestamo.
     */
    public long getTiempoDePrestamo() {
        return tiempoDePrestamo;
    }

    /**
     * Setter del atributo tiepoDePrestamo.
     * @since v3.2.1
     * @param tiempoDePrestamo Long a almacenar en el atributo tiempoDePrestamo.
     */
    public void setTiempoDePrestamo(long tiempoDePrestamo) {
        this.tiempoDePrestamo = tiempoDePrestamo;
    }

    /**
     * Getter del atributo fechaDeDevolucion.
     * @since v3.2.1
     * @return Objeto de la clase LocalDateTime almacenado en el atributo fechaDeDevolucion
     */
    public LocalDateTime getFechaDeDevolucion(){
        return fechaDeDevolucion;
    }

    /**
     * Setter del atributo fechaDeDevolucion. Declara una variable long llamada tiempo y le asigna el resultado de sumar
     * el valor devuelto por el método estático parseLong del wrapper Long con el String devuelto por el método toString()
     * de la clase Instant llamado por el valor devuelto por el método estático now() de la clase Instant y el long
     * almacenado en el atributo tiempoDePrestamo. Se inicializa el atributo fechaDeDevolución con el método estático
     * from(TemporalAccessor) de la clase LocalDateTime al que se le pasa el objeto devuelto por el método estático
     * ofEpochMilli(long) de la clase Instant con la variable tiempo como parámetro por parámetros.
     * @since v3.2.1
     * @param tiempoDePrestamo Long necesario para construir la variable tiempo.
     */
    public void setFechaDeDevolucion(long tiempoDePrestamo){
        long tiempo = Long.parseLong(Instant.now().toString()) + tiempoDePrestamo;
        this.fechaDeDevolucion = LocalDateTime.from(Instant.ofEpochMilli(tiempo));
    }

    /**
     * Método toString heredado de la clase Object.
     * @return JSON con los datos del objeto llamador.
     */
    @Override
    public String toString() {
        return "Peticion{" +
                "idPeticion=" + idPeticion +
                ", idCliente=" + idCliente +
                ", solicitud=" + solicitud +
                ", fechaPrestado=" + fechaPrestado +
                ", tiempoDePrestamo=" + tiempoDePrestamo +
                ", fechaDeDevolucion=" + fechaDeDevolucion +
                '}';
    }
}
