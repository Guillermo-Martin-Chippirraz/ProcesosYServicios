package Tema3.Practica.Actividad2;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Random;

/**
 * Clase que simula la biblioteca de la actividad 2 de la práctica del tema 3
 * @author Guillermo Martín Chippirraz
 * @version v3.2
 */
public class Biblioteca implements Serializable{
    /**
     * Clase anidada a la clase biblioteca para libros.
     * @author Guillermo Martín Chippirraz
     * @version v3.2
     */
    public class Libro implements Serializable{
        private String titulo;
        private String genero;

        /**
         * Constructor por defecto.
         * @since v3.2
         */
        public Libro(){
            titulo = genero = "";
        }

        /**
         * Constructor por parámetros.
         * @since v3.2
         * @see #setTitulo(String)
         * @see #setGenero(String)
         * @param titulo String que se enviará al setter del atributo titulo.
         * @param genero String que se enviará al setter del atributo genero.
         */
        public Libro(String titulo, String genero){
            setTitulo(titulo);
            setGenero(genero);
        }

        /**
         * Getter del atributo titulo.
         * @since v3.2
         * @return String almacenado en el atributo titulo.
         */
        public String getTitulo() {
            return titulo;
        }

        /**
         * Setter del atributo titulo.
         * Transforma el String recibido por parámetros para que siga el formato adecuado (Inicial de cada palabra en mayúsculas)
         * y, posteriormente, compara con los valores admitidos. Si coincide con alguno de ellos, el String resultante será
         * almacenado en el atributo titulo. En caso contrario, se almacenará el valor por defecto.
         * @param titulo String a transformar, validar y almacenar, en ese orden, en el atributo titulo.
         */
        public void setTitulo(String titulo) {
            if (!titulo.contains(" "))
                titulo = titulo.substring(0, 1).toUpperCase() + titulo.substring(1, genero.length()-1).toLowerCase();
            else {
                String [] palabras = titulo.split(" ");
                titulo = "";
                for(String palabra : palabras){
                    titulo.concat(palabra.substring(0,1).toUpperCase() + palabra.substring(1, palabra.length() - 1).toLowerCase());
                }
            }
            this.titulo = titulo;
        }

        /**
         * Getter del atributo genero.
         * @since v3.2
         * @return String almacenado en el atributo genero.
         */
        public String getGenero(){
            return genero;
        }

        /**
         * Setter del atributo genero.
         * Transforma el String recibido por parámetros para que siga el formato adecuado (Inicial de cada palabra en mayúsculas)
         * y, posteriormente, compara con los valores admitidos. Si coincide con alguno de ellos, el String resultante será
         * almacenado en el atributo genero. En caso contrario, se almacenará el valor por defecto.
         * @since v3.2
         * @param genero String a transformar, validar y almacenar, en ese orden, en el atributo genero.
         */
        public void setGenero(String genero){
            if (!genero.contains(" "))
                genero = genero.substring(0, 1).toUpperCase() + genero.substring(1, genero.length()-1).toLowerCase();
            else {
                String [] palabras = genero.split(" ");
                genero = "";
                for(String palabra : palabras){
                    genero.concat(palabra.substring(0,1).toUpperCase() + palabra.substring(1, palabra.length() - 1).toLowerCase());
                }
            }
            switch (genero){
                case "Fantasía, Ciencia Ficción, Romance, Drama, Policíaca, Histórica, Biografía":
                    this.genero = genero;
                    break;
                default:
                    this.genero = "";
            }
        }

        /**
         * Método toString heredado de la clase Object.
         * @return JSON con el valor de cada atributo almacenado del objeto llamador.
         */
        @Override
        public String toString() {
            return "Libro{" +
                    "titulo='" + titulo + '\'' +
                    ", genero='" + genero + '\'' +
                    '}';
        }
    }
    private int cantidadLibros;
    private ArrayList<Libro> libros;
    private final Random random = new Random();

    /**
     * Constructor por defecto
     * @since v3.2
     */
    public Biblioteca(){
        cantidadLibros = -1;
        libros = new ArrayList<>();
    }

    /**
     * Constructor por parámetros. Se utiliza la longitud del ArrayList almacenado en el atributo libros como parámetro
     * introducido en el setter del atributo cantidadLibros.
     * @since v3.2
     * @see #setLibros(ArrayList)
     * @see #setCantidadLibros(int)
     * @see Libro
     * @param libros ArrayList de objetos de la clase anidada Libro que se enviará al setter del atributo libros
     */
    public Biblioteca(ArrayList<Libro> libros){
        setLibros(libros);
        setCantidadLibros(this.libros.size());
    }

    /**
     * Getter del atributo libros.
     * @since v3.2
     * @see Libro
     * @return ArrayList almacenado en el atributo libros.
     */
    public ArrayList<Libro> getLibros(){
        return libros;
    }

    /**
     * Setter del atributo libros. Evalúa si los atributos titulo y genero de los objetos almacenados en el ArrayList introducido
     * por parámetros es el valor por defecto previo a almacenarlo en el ArrayList correspondiente al atributo libros.
     * @since v3.2
     * @see Libro
     * @see Libro#getTitulo()
     * @see Libro#getGenero()
     * @param libros ArrayList de objetos de la clase Libro cuyos objetos se validarán y almacenarán en el atributo libros.
     */
    public void setLibros(ArrayList<Libro> libros){
        this.libros = new ArrayList<>();
        for (Libro libro : libros){
            if(!libro.getGenero().isEmpty() && !libro.getTitulo().isEmpty()) this.libros.add(libro);
        }
    }

    /**
     * Getter del atributo cantidadLibros.
     * @since v3.2
     * @return Entero almacenado en el atributo cantidadLibros.
     */
    public int getCantidadLibros(){
        return cantidadLibros;
    }

    /**
     * Setter del atributo cantidadLibros. Evalúa si el entero introducido por parámetros es no negativo previo a
     * almacenarlo en el atributo cantidadLibros.
     * @since v3.2
     * @param cantidadLibros Entero a validar y almacenar en el atributo cantidadLibros.
     */
    public void setCantidadLibros(int cantidadLibros){
        if (cantidadLibros >= 0)
            this.cantidadLibros = cantidadLibros;
        else this.cantidadLibros = -1;
    }

    /**
     * TODO Documentar
     * @param idCliente
     * @param titulo
     * @return
     */
    public synchronized String prestarLibro(int idCliente, String titulo){
        if (libros.isEmpty()){
            System.out.println("Bibliotecario sabrosón: No hay libroh, mi amol, solo masibón.");
            try{
                wait();
            }catch(InterruptedException e){
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        }
        boolean libroEnStock = false;
        String datosLibro = "";
        for (Libro libro : libros){
            if (libro.getTitulo().equals(titulo)){
                libroEnStock = true;
                datosLibro = libro.toString();
                libros.remove(libro);
            }
        }
        if (!libroEnStock){
            System.out.println("Bibliotecario sabrosón: Lo siento, mi amol, el libro que buhcah no lo han devuerto.");
            try{
                wait();
            }catch(InterruptedException e){
                e.printStackTrace();;
                Thread.currentThread().interrupt();
            }
        }

        System.out.println("Bibliotecario sabrosón: Aquí tieneh tu libro, mi amol.");
        long fechaLimiteDevolucion = random.nextLong(15) + 10;
        notifyAll();
        String datosPeticion = "Peticion{" +
                "idCliente='" + idCliente + '\'' +
                "Solicitud='" + datosLibro + '\'' +
                "Fecha de solicitud='" + LocalDateTime.now() + '\'' +
                "Fecha límite de devolución='" + LocalDateTime.ofInstant(Instant.now().plusMillis(fechaLimiteDevolucion), ZoneId.of("Europe/Madrid")) + '\'' +
                "}";

        return datosPeticion;
    }

}
