package Tema3.Practica.Actividad2;

import java.util.ArrayList;
import java.util.Random;

/**
 * Clase principal de la actividad 2 de la práctica del tema 3. Contiene el método main, punto de entrada de la
 * aplicación, encargado de inicializar la biblioteca, generar libros aleatorios, crear el servidor y lanzar varios
 * clientes concurrentes.
 * @author Guillermo Martín Chippirraz
 * @version v3.5.1
 */
public class Main {
    /**
     *
     * La línea de ejecución del método main es la siguiente:
     * <ol>
     *     <li>
     *         Se declara un objeto de la clase ArrayList parametrizado con Biblioteca.Libro y se inicializa a través del
     *         constructor por defecto. Este ArrayList almacenará los libros que formarán parte de la biblioteca.
     *     </li>
     *     <li>
     *         Se declara un objeto de la clase ArrayList parametrizado con String y se inicializa a través del constructor
     *         por defecto. Este ArrayList almacenará los géneros válidos que podrán asignarse a los libros generados.
     *     </li>
     *     <li>
     *         Se añaden manualmente al ArrayList generos varias cadenas de texto correspondientes a géneros literarios
     *         válidos, incluyendo un String vacío para simular la posibilidad de que un libro sea inválido.
     *     </li>
     *     <li>
     *         Se declara un objeto de la clase Random y se inicializa a través del constructor por defecto.
     *     </li>
     *     <li>
     *         Se abre un bucle for cuyo contador i toma valores desde 1 hasta 5 (ambos inclusive). En cada iteración:
     *         <ol>
     *             <li>
     *                 Se declara un String genero y se le asigna un valor aleatorio obtenido del ArrayList generos mediante
     *                 el método get(), introduciendo como parámetro un entero devuelto por el método nextInt() invocado por
     *                 el objeto Random random, con los valores 0 y 8 como parámetros bound inferior y superior.
     *             </li>
     *             <li>
     *                 Se invoca el constructor por parámetros de la clase Biblioteca.Libro introduciendo como primer
     *                 parámetro el String "Libro " concatenado con el valor del contador i, y como segundo parámetro el
     *                 String genero obtenido aleatoriamente.
     *             </li>
     *             <li>
     *                 Se añade el objeto Biblioteca.Libro recién creado al ArrayList libros mediante el método add().
     *             </li>
     *         </ol>
     *     </li>
     *     <li>
     *         Se declara un objeto de la clase Biblioteca y se inicializa a través del constructor por parámetros,
     *         introduciendo el ArrayList libros como valor del parámetro libros. Este constructor validará los libros
     *         recibidos y almacenará únicamente aquellos cuyos atributos sean válidos.
     *     </li>
     *     <li>
     *         Se declara un objeto de la clase Servidor y se inicializa a través del constructor por parámetros,
     *         introduciendo el objeto Biblioteca biblioteca como valor del parámetro biblioteca.
     *     </li>
     *     <li>
     *         Se declara un objeto de la clase Thread denominado servidorThread y se inicializa a través del constructor
     *         por parámetros, introduciendo el objeto Servidor servidor como valor del parámetro target.
     *     </li>
     *     <li>
     *         Se invoca el método start() a través del objeto servidorThread, iniciando así la ejecución concurrente del
     *         servidor.
     *     </li>
     *     <li>
     *         Se abre un segundo bucle for cuyo contador i toma valores desde 1 hasta 5 (ambos inclusive). En cada
     *         iteración:
     *         <ol>
     *             <li>
     *                 Se declara un objeto de la clase Thread denominado cliente y se inicializa a través del constructor
     *                 por parámetros, introduciendo como valor del parámetro target un nuevo objeto de la clase Cliente,
     *                 inicializado a través de su constructor por parámetros con el entero i como valor del parámetro
     *                 idCliente.
     *             </li>
     *             <li>
     *                 Se invoca el método start() a través del objeto Thread cliente, iniciando así la ejecución concurrente
     *                 del cliente correspondiente.
     *             </li>
     *         </ol>
     *     </li>
     * </ol>
     *
     * @since v3.2.4
     * @see Biblioteca
     * @see Biblioteca.Libro
     * @see Servidor
     * @see Cliente
     * @see Thread
     * @see Random
     * @param args Argumentos de la línea de comandos
     */
    public static void main(String[] args) {
        ArrayList<Biblioteca.Libro> libros = new ArrayList<>();
        ArrayList<String> generos = new ArrayList<>();
        generos.add("Fantasía");
        generos.add("Ciencia Ficción");
        generos.add("Romance");
        generos.add("Drama");
        generos.add("Policiaca");
        generos.add("Historica");
        generos.add("Biográfica");
        generos.add("");
        Random random = new Random();
        for (int i = 1; i<6; i++){
            String genero = generos.get(random.nextInt(0, 8));
            libros.add(new Biblioteca.Libro("Libro " + i, genero));
        }
        Biblioteca biblioteca = new Biblioteca(libros);
        Servidor servidor = new Servidor(biblioteca);
        Thread servidorThread = new Thread(servidor);
        servidorThread.start();
        for (int i = 1; i<6; i++){
            Thread cliente = new Thread(new Cliente(i));
            cliente.start();
        }
    }
}
