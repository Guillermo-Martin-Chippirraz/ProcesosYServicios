package Tema3.Practica.Actividad2;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;

/**
 * Clase para el servidor, implementación de Runnable, de la actividad 2 de la práctica del tema 3. Gestiona la
 * biblioteca.
 * @author Guillermo Martín Chippirraz
 * @version 3.5.1
 * @see Biblioteca
 * @see Random
 *
 */
public class Servidor implements Runnable {
    private Biblioteca biblioteca;
    private final Random random = new Random();
    private final ArrayList<Cliente> clientes;

    /**
     * Constructor por defecto
     * @since 3.2.4
     */
    public Servidor() {
        this.biblioteca = new Biblioteca();
        this.clientes = new ArrayList<>();
    }

    /**
     * Constructor por parámetros.
     * @since v3.2.4
     * @param biblioteca Objeto de la clase Biblioteca que será copiado superficialmente
     */
    public Servidor(Biblioteca biblioteca) {
        this.biblioteca = biblioteca;
        this.clientes = new ArrayList<>();
    }

    /**
     * Getter del atributo clientes.
     * @since v3.2.4
     * @return ArrayList de objetos de la clase Cliente.
     */
    public ArrayList<Cliente> getClientes() {
        return clientes;
    }

    /**
     * Método run implementado de la interfaz Runnable. A continuación se describe la línea de ejecución:
     * <ol>
     *     <li>
     *         Se abre un bloque try-with-resources, declarándose el ServerSocket socketServidor como recurso,
     *         inicializado éste a través del constructor por defecto. En este bloque:
     *         <ol>
     *             <li>
     *                 Se declara un objeto InetSocketAddress add y se inicializa a través del constructor por parámetros,
     *                 introduciendo la cadena de texto "localhost" como valor del parámetro hostname y el entero 5555
     *                 como valor del parámetro port.
     *             </li>
     *             <li>
     *                 Se invoca el método bind() a través del ServerSocket socketServidor introduciendo el
     *                 InetSocketAddress add como valor del parámetro endpoint.
     *             </li>
     *             <li>
     *                 Se imprime un mensaje por consola indicando que el servidor ha sido conectado en la dirección
     *                 indicada.
     *             </li>
     *             <li>
     *                 Se abre un bucle infinito while con el booleano true como condición. En este bucle:
     *                 <ol>
     *                     <li>
     *                         Se abre un bloque try-with-resources y se declara el Socket cliente, inicializado como
     *                         la copia superficial del retorno del método accept() invocado por el ServerSocket
     *                         socketServidor. En este bloque:
     *                         <ol>
     *                             <li>
     *                                 Se declara el DataOutputStream dos a través del constructor por parámetros con
     *                                 el OutputStream devuelto por el getter getOutputStream() invocado por el Socket
     *                                 cliente como valor del parámetro out.
     *                             </li>
     *                             <li>
     *                                 Se declara el DataInputStream dis a través del constructor por parámetros con el
     *                                 InputStream devuelto por el getter getInputStream() invocado por el Socket
     *                                 cliente como valor del parámetro in.
     *                             </li>
     *                             <li>
     *                                 Se declara el ObjectOutputStream oos a través del constructor por parámetros con
     *                                 el OutputStream devuelto por el getter getOutputStream() invocado por el Socket
     *                                 cliente como valor del parámetro out. Este flujo se utilizará exclusivamente para
     *                                 el envío de objetos serializables.
     *                             </li>
     *                             <li>
     *                                 Se declara el ObjectInputStream ois a través del constructor por parámetros con
     *                                 el InputStream devuelto por el getter getInputStream() invocado por el Socket
     *                                 cliente como valor del parámetro in. Este flujo se utilizará exclusivamente para
     *                                 la recepción de objetos serializables.
     *                             </li>
     *                             <li>
     *                                 Se declara el int idCliente y se le asigna el primer mensaje del cliente devuelto
     *                                 por el método readInt() invocado por el DataInputStream dis.
     *                             </li>
     *                             <li>
     *                                 Se imprime por pantalla un mensaje de retroalimentación para indicar que el
     *                                 cliente pudo conectarse.
     *                             </li>
     *                             <li>
     *                                 Se envía el primer mensaje al cliente a través del método writeUTF() invocado
     *                                 por el DataOutputStream dos y se envía una cadena de texto como valor del
     *                                 parámetro str.
     *                             </li>
     *                             <li>
     *                                 Se declara el String titulo y se le asigna el segundo mensaje del cliente,
     *                                 devuelto por el método readUTF() invocado por el DataInputStream dis.
     *                             </li>
     *                             <li>
     *                                 Se declara un objeto de la clase Libro, anidada a la clase Biblioteca, libro, y
     *                                 se inicializa como la copia superficial del retorno del método sincronizado
     *                                 prestarLibro(), invocado por el objeto Biblioteca biblioteca y con el String
     *                                 titulo como valor del parámetro titulo.
     *                             </li>
     *                             <li>
     *                                 Se abre una estructura if con el valor de verdad de la disyunción del retorno del
     *                                 método isEmpty() invocado por el String devuelto por el getter getGenero()
     *                                 invocado por el Biblioteca.Libro libro y el retorno del método isEmpty() invocado
     *                                 por el String devuelto por el getter getTitulo() invocado por el Biblioteca.Libro
     *                                 libro como condición de entrada al bloque. En el bloque de código:
     *                                 <ol>
     *                                     <li>
     *                                         Se envía el segundo mensaje al cliente a través del método writeUTF()
     *                                         invocado por el DataOutputStream dos con una cadena de texto como valor
     *                                         del parámetro str.
     *                                     </li>
     *                                 </ol>
     *                                 En caso contrario, en el bloque else:
     *                                 <ol>
     *                                     <li>
     *                                         Se envía el segundo mensaje al cliente con una cadena de texto indicando
     *                                         éxito.
     *                                     </li>
     *                                     <li>
     *                                         Se envía el retorno del getter getTitulo() invocado por el
     *                                         Biblioteca.Libro libro como tercer mensaje al cliente.
     *                                     </li>
     *                                     <li>
     *                                         Se envía el retorno del getter getGenero() invocado por el
     *                                         Biblioteca.Libro libro como cuarto mensaje al cliente.
     *                                     </li>
     *                                     <li>
     *                                         Se envía el objeto Biblioteca.Libro libro al cliente a través del método
     *                                         writeObject() invocado por el ObjectOutputStream oos.
     *                                     </li>
     *                                     <li>
     *                                         Se declara el int tiempo y se le asigna el total de la suma del entero
     *                                         devuelto por el método nextInt(), invocado por el atributo final random,
     *                                         con el entero 15000 como valor del parámetro bound y el entero 10000.
     *                                     </li>
     *                                     <li>
     *                                         Se envía el int tiempo como quinto mensaje al cliente.
     *                                     </li>
     *                                     <li>
     *                                         Se invoca el método estático sleep() de la clase Thread con el int tiempo
     *                                         como valor del parámetro millis.
     *                                     </li>
     *                                     <li>
     *                                         Se declara el String tituloDevuelto y se le asigna el tercer mensaje
     *                                         recibido desde el cliente.
     *                                     </li>
     *                                     <li>
     *                                         Se declara el String generoDevuelto y se le asigna el cuarto mensaje
     *                                         recibido desde el cliente.
     *                                     </li>
     *                                     <li>
     *                                         Se declara el Biblioteca.Libro libroDevuelto y se inicializa como la copia
     *                                         superficial del retorno del método readObject() invocado por el
     *                                         ObjectInputStream ois.
     *                                     </li>
     *                                     <li>
     *                                         Se abre un bloque if con el valor de verdad de la disyunción del booleano
     *                                         devuelto por el método isEmpty() invocado por el String devuelto por el
     *                                         getter getTitulo() invocado por el Biblioteca.Libro libroDevuelto y el
     *                                         booleano devuelto por el método isEmpty() invocado por el String devuelto
     *                                         por el getter getGenero() invocado por el Biblioteca.Libro libroDevuelto.
     *                                         De ser así:
     *                                         <ol>
     *                                             <li>
     *                                                 Se imprime por pantalla un mensaje indicando fracaso en la operación.
     *                                             </li>
     *                                         </ol>
     *                                         En caso contrario, en el bloque else:
     *                                         <ol>
     *                                             <li>
     *                                                 Se invoca el método devolucionLibro() a través del objeto Biblioteca
     *                                                 biblioteca y se introduce el Biblioteca.Libro libroDevuelto como
     *                                                 valor del parámetro libro.
     *                                             </li>
     *                                             <li>
     *                                                 Se imprime por pantalla un mensaje de éxito.
     *                                             </li>
     *                                         </ol>
     *                                     </li>
     *                                 </ol>
     *                             </li>
     *                         </ol>
     *                     </li>
     *                     <li>
     *                         Se invoca el método close() a través del DataInputStream dis.
     *                     </li>
     *                     <li>
     *                         Se invoca el método close() a través del DataOutputStream dos.
     *                     </li>
     *                     <li>
     *                         Se invoca el método close() a través del ObjectInputStream ois.
     *                     </li>
     *                     <li>
     *                         Se invoca el método close() a través del ObjectOutputStream oos.
     *                     </li>
     *                 </ol>
     *                 En el catch se controlan las excepciones de entrada/salida de datos, de interrupción de hilos y de
     *                 deserialización de objetos a través del error e. En este bloque:
     *                 <ol>
     *                     <li>
     *                         Se invoca el método printStackTrace() a través del IOException | InterruptedException |
     *                         ClassNotFoundException e.
     *                     </li>
     *                     <li>
     *                         Se invoca el método interrupt() a través del objeto de la clase Thread devuelto por el
     *                         método estático currentThread() de la clase Thread.
     *                     </li>
     *                 </ol>
     *             </li>
     *         </ol>
     *         En el catch se controlan las excepciones de entrada/salida de datos a través del IOException e. En este
     *         bloque:
     *         <ol>
     *             <li>
     *                 Se invoca el método printStackTrace() a través del IOException e.
     *             </li>
     *         </ol>
     *     </li>
     * </ol>
     * @since v3.5.1
     * @see Biblioteca.Libro
     * @see Biblioteca.Libro#getGenero()
     * @see Biblioteca.Libro#getTitulo()
     * @see Biblioteca.Libro#setTitulo(String)
     * @see Biblioteca.Libro#setGenero(String)
     * @see ObjectOutputStream#writeObject(Object)
     * @see ObjectInputStream#readObject()
     */
    @Override
    public void run() {
        try (ServerSocket socketServidor = new ServerSocket()) {

            InetSocketAddress add = new InetSocketAddress("localhost", 5555);
            socketServidor.bind(add);
            System.out.println("Servidor conectado en " + add);

            while (true) {
                try (Socket cliente = socketServidor.accept()) {

                    // PRIMERO los streams de datos
                    DataOutputStream dos = new DataOutputStream(cliente.getOutputStream());
                    DataInputStream dis = new DataInputStream(cliente.getInputStream());

                    // DESPUÉS los streams de objetos
                    ObjectOutputStream oos = new ObjectOutputStream(cliente.getOutputStream());
                    ObjectInputStream ois = new ObjectInputStream(cliente.getInputStream());

                    int idCliente = dis.readInt();
                    System.out.println("El cliente " + idCliente + " se ha conectado satisfactoriamente.");

                    // Mensaje 1
                    dos.writeUTF("Seleccione el libro que desee solicitar");

                    // Mensaje 2 (cliente envía título)
                    String titulo = dis.readUTF();

                    Biblioteca.Libro libro = biblioteca.prestarLibro(titulo);

                    if (libro.getGenero().isEmpty() || libro.getTitulo().isEmpty()) {

                        // Mensaje 3 (fallo)
                        dos.writeUTF("El libro no está disponible. Hasta siempre papanatas.");

                    } else {

                        // Mensaje 3 (éxito)
                        dos.writeUTF("El libro ha sido encontrado.");

                        // Mensaje 4 y 5 (título y género)
                        dos.writeUTF(libro.getTitulo());
                        dos.writeUTF(libro.getGenero());

                        // Enviar OBJETO libro
                        oos.writeObject(libro);
                        oos.flush();

                        // Mensaje 6 (tiempo)
                        int tiempo = random.nextInt(15000) + 10000;
                        dos.writeInt(tiempo);

                        Thread.sleep(tiempo);

                        // Mensaje 7 y 8 (título y género devueltos)
                        String tituloDevuelto = dis.readUTF();
                        String generoDevuelto = dis.readUTF();

                        // Recibir OBJETO libro devuelto
                        Biblioteca.Libro libroDevuelto = (Biblioteca.Libro) ois.readObject();

                        if (tituloDevuelto.isEmpty() || generoDevuelto.isEmpty()) {
                            System.out.println("El cliente no ha devuelto el libro.");
                        } else {
                            biblioteca.devolucionLibro(libroDevuelto);
                            System.out.println("El libro ha sido devuelto satisfactoriamente.");
                        }
                    }

                    dis.close();
                    dos.close();
                    ois.close();
                    oos.close();

                } catch (IOException | InterruptedException | ClassNotFoundException e) {
                    e.printStackTrace();
                    Thread.currentThread().interrupt();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
