package Tema3.Practica.Actividad2;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;

/**
 * Clase que simula la biblioteca de la actividad 2 de la práctica del tema 3
 * @author Guillermo Martín Chippirraz
 * @version v3.2.4
 */
public class Biblioteca implements Serializable{
    /**
     * Clase anidada a la clase biblioteca para libros.
     * @author Guillermo Martín Chippirraz
     * @version v3.2
     */
    public static class Libro implements Serializable{
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
         * @since v3.2.4
         * @param titulo String a transformar, validar y almacenar, en ese orden, en el atributo titulo.
         */
        public void setTitulo(String titulo) {
            if (titulo == null || titulo.isEmpty()) {
                this.titulo = titulo;
                return;
            }

            if (!titulo.contains(" ")) {
                titulo = titulo.substring(0, 1).toUpperCase() + titulo.substring(1).toLowerCase();
            } else {
                String[] palabras = titulo.split(" ");
                StringBuilder sb = new StringBuilder();
                for (String palabra : palabras) {
                    if (!palabra.isEmpty()) {
                        sb.append(palabra.substring(0, 1).toUpperCase());
                        if (palabra.length() > 1) {
                            sb.append(palabra.substring(1).toLowerCase());
                        }
                        sb.append(" ");
                    }
                }
                titulo = sb.toString().trim();
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
         * @since v3.2.4
         * @param genero String a transformar, validar y almacenar, en ese orden, en el atributo genero.
         */
        public void setGenero(String genero) {
            if (genero == null || genero.isEmpty()) {
                this.genero = "";
                return;
            }

            // Normalizar capitalización
            String resultado;
            if (!genero.contains(" ")) {
                resultado = genero.substring(0, 1).toUpperCase() + genero.substring(1).toLowerCase();
            } else {
                String[] palabras = genero.split(" ");
                StringBuilder sb = new StringBuilder();
                for (String palabra : palabras) {
                    if (!palabra.isEmpty()) {
                        sb.append(palabra.substring(0, 1).toUpperCase());
                        if (palabra.length() > 1) {
                            sb.append(palabra.substring(1).toLowerCase());
                        }
                        sb.append(" ");
                    }
                }
                resultado = sb.toString().trim();
            }

            switch (resultado) {
                case "Fantasía", "Ciencia Ficción", "Romance", "Drama", "Policíaca", "Histórica", "Biográfica":
                    this.genero = resultado;
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
     * Método sincronizado prestarLibro.
     * Secuencia de ejecución:
     * <ol>
     *     <li>
     *         Se evalúa si el atributo libros está vacío. De ser así:
     *         <ol>
     *             <li>Se imprime un mensaje de retroalimentación por pantalla.</li>
     *             <li>Se retorna un nuevo objeto Libro vacío.</li>
     *         </ol>
     *     </li>
     *     <li>
     *         Se declara la variable boolean libroEnStock y se le asigna false.
     *     </li>
     *     <li>
     *         Se declara el objeto de la clase Libro libroPedido y se inicializa con el constructor por defecto.
     *     </li>
     *     <li>
     *         Se recorre el atributo libros mediante un iterador. Si el atributo titulo de un objeto coincide con el
     *         valor del parámetro titulo:
     *         <ol>
     *             <li>Se asigna true a la variable libroEnStock.</li>
     *             <li>Se copia superficialmente dicho objeto en la variable libroPedido.</li>
     *             <li>Se elimina el objeto de la colección mediante el iterador.</li>
     *             <li>Se rompe el bucle para evitar iteraciones innecesarias.</li>
     *         </ol>
     *     </li>
     *     <li>
     *         Si libroEnStock es false:
     *         <ol>
     *             <li>Se imprime un mensaje de retroalimentación.</li>
     *             <li>Se retorna libroPedido (vacío).</li>
     *         </ol>
     *     </li>
     *     <li>
     *         Se imprime un mensaje de éxito indicando que el libro ha sido prestado.
     *     </li>
     *     <li>
     *         Se invoca al método notifyAll() para despertar posibles hilos en espera.
     *     </li>
     * </ol>
     * @since v3.2.4
     * @see Libro
     * @see Libro#getTitulo()
     * @param titulo String que se usará como filtro para buscar el objeto de la clase Libro a solicitar.
     * @return Objeto de la clase Libro solicitado
     */
    public synchronized Libro prestarLibro(String titulo) {
        if (libros.isEmpty()) {
            System.out.println("Bibliotecario sabrosón: No hay libroh, mi amol, solo masibón.");
            return new Libro();
        }

        boolean libroEnStock = false;
        Libro libroPedido = new Libro();

        // Uso de la clase Iterator para evitar ConcurrentModificationException
        Iterator<Libro> it = libros.iterator();
        while (it.hasNext()) {
            Libro libro = it.next();
            if (libro.getTitulo().equals(titulo)) {
                libroEnStock = true;
                libroPedido = libro;
                it.remove(); // eliminación segura
                break;       // salir del bucle tras encontrar el libro
            }
        }

        if (!libroEnStock) {
            System.out.println("Bibliotecario sabrosón: Lo siento, mi amol, el libro que buhcah no lo han devuerto.");
            return libroPedido;
        }

        System.out.println("Bibliotecario sabrosón: Aquí tieneh tu libro, mi amol.");
        notifyAll();
        return libroPedido;
    }

    /**
     * Método sincronizado devolucionLibro.
     * Secuencia de ejecución:
     * <ol>
     *     <li>
     *         Se añade el objeto Libro recibido como parámetro al atributo libros.
     *     </li>
     *     <li>
     *         Se imprime un mensaje de retroalimentación indicando que el libro ha sido devuelto.
     *     </li>
     *     <li>
     *         Se invoca al método notifyAll() para despertar posibles hilos en espera.
     *     </li>
     * </ol>
     * @since v3.2.4
     * @see Libro
     * @param libro Objeto de la clase Libro que se devuelve a la biblioteca.
     */
    public synchronized void devolucionLibro(Libro libro) {
        libros.add(libro);
        System.out.println("Grasiah pol devolvelo, mi amol...");
        notifyAll();
    }

}
