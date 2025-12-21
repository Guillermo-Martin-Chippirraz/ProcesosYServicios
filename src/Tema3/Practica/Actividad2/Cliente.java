package Tema3.Practica.Actividad2;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

/**
 * Clase para el cliente, implementada a partir de Runnable, de la actividad 2 de la práctica del tema 3.
 * @author Guillermo Martín Chippirraz
 * @version 3.5.1
 */
public class Cliente implements Runnable {
    private int idCliente;

    /**
     * Constructor por parámetros.
     * @since v3.2.4
     * @param idCliente Entero que se asigna al atributo idCliente.
     */
    public Cliente(int idCliente) {
        this.idCliente = idCliente;
    }

    /**
     * Getter del atributo idCliente.
     * @since v3.2.4
     * @return Entero almacenado en el atributo idCliente.
     */
    public int getIdCliente() {
        return idCliente;
    }

    /**
     * Setter del atributo idCliente.
     * @since v3.2.4
     * @param idCliente Entero que se asigna al atributo idCliente.
     */
    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    /**
     * Método run implementado de la interfaz Runnable.
     * La línea de ejecución es la siguiente:
     * <ol>
     *     <li>
     *         Se declara un objeto de la clase Scanner y se inicializa a través del constructor por parámetros.
     *     </li>
     *     <li>
     *         Se abre un bloque try-with-resources, declarándose el recurso cliente como objeto de la clase Socket,
     *         a través del constructor por defecto. En el bloque de código:
     *         <ol>
     *             <li>
     *                 Se declara el objeto add de la clase InetSocketAddress, inicializado a través del constructor
     *                 por parámetros con el String "localhost" como parámetro hostname y el entero 5555 como parámetro
     *                 port.
     *             </li>
     *             <li>
     *                 A continuación, se conecta el Socket al puerto invocando el método connect() con el Socket
     *                 cliente e introduciendo el InetSocketAddress add como parámetro endpoint.
     *             </li>
     *             <li>
     *                 Se imprime un mensaje por pantalla confirmando al cliente la conexión con éxito al servidor.
     *             </li>
     *             <li>
     *                 Se declara el objeto dos de la clase DataOutputStream y se inicializa a través de su constructor
     *                 por parámetros introduciendo el retorno del método getOutputStream(), invocado a través del
     *                 Socket cliente, como valor del parámetro out.
     *             </li>
     *             <li>
     *                 Se declara el objeto dis de la clase DataInputStream y se inicializa a través de su constructor
     *                 por parámetros introduciendo el retorno del método getInputStream(), invocado a través del
     *                 Socket cliente, como valor del parámetro in.
     *             </li>
     *             <li>
     *                 Se declara el objeto oos de la clase ObjectOutputStream y se inicializa a través de su constructor
     *                 por parámetros introduciendo el retorno del método getOutputStream(), invocado a través del
     *                 Socket cliente, como valor del parámetro out. Este flujo se utilizará exclusivamente para el envío
     *                 de objetos serializables.
     *             </li>
     *             <li>
     *                 Se declara el objeto ois de la clase ObjectInputStream y se inicializa a través de su constructor
     *                 por parámetros introduciendo el retorno del método getInputStream(), invocado a través del
     *                 Socket cliente, como valor del parámetro in. Este flujo se utilizará exclusivamente para la
     *                 recepción de objetos serializables.
     *             </li>
     *             <li>
     *                 Se envía el primer mensaje al servidor, invocando el método writeInt() a través del
     *                 DataOutputStream dos y con el entero almacenado en el atributo idCliente como valor del parámetro v.
     *             </li>
     *             <li>
     *                 Se imprime en consola la primera respuesta del servidor a través del método readUTF(), invocado
     *                 por el DataInputStream dis.
     *             </li>
     *             <li>
     *                 Se declara el String titulo y se asigna el valor devuelto por el método nextLine(), invocado por
     *                 el Scanner scan, de tal forma que solicita al usuario introducir texto.
     *             </li>
     *             <li>
     *                 Se envía el segundo mensaje al servidor a través del método writeUTF(), invocado por el
     *                 DataOutputStream dos, con la cadena de texto almacenada en el String titulo como valor del
     *                 parámetro str.
     *             </li>
     *             <li>
     *                 Se declara el String respuesta y se le asigna la segunda respuesta del servidor, devuelta por el
     *                 método readUTF(), invocado por el DataInputStream dis.
     *             </li>
     *             <li>
     *                 Se imprime en consola la cadena de texto almacenada en el String respuesta.
     *             </li>
     *             <li>
     *                 Se abre un bloque if con el resultado de comprobar que en el String respuesta se encuentra una
     *                 cadena de texto que coincida con la cadena "encontrado" como condición. En caso de ser así,
     *                 se sigue la siguiente línea de ejecución:
     *                 <ol>
     *                     <li>
     *                         Se declara el String tituloLibro y se almacena la tercera respuesta del servidor.
     *                     </li>
     *                     <li>
     *                         Se declara el String generoLibro y se almacena la cuarta respuesta del servidor.
     *                     </li>
     *                     <li>
     *                         Se declara el objeto libro de la clase anidada Libro de la clase Biblioteca y se
     *                         inicializa como la copia superficial del retorno del método readObject() invocado por el
     *                         ObjectInputStream ois.
     *                     </li>
     *                     <li>
     *                         Se imprime por pantalla el objeto Biblioteca.Libro libro recibido.
     *                     </li>
     *                     <li>
     *                         Se declara el int tiempo y se almacena la quinta respuesta del servidor.
     *                     </li>
     *                     <li>
     *                         Se invoca el método estático sleep() de la clase Thread con el entero almacenado en el int
     *                         tiempo como valor del parámetro millis.
     *                     </li>
     *                     <li>
     *                         Se abre un bloque if con la afirmación del retorno del método estático random() de la
     *                         clase Math es mayor que 0.05 como condición. En caso de ser así, se sigue la siguiente
     *                         línea de ejecución:
     *                         <ol>
     *                             <li>
     *                                 Se envía el tercer mensaje al servidor con el retorno del getter del atributo
     *                                 titulo de la clase Libro, invocado a través del Libro libro, como contenido del
     *                                 mensaje.
     *                             </li>
     *                             <li>
     *                                 Se envía el cuarto mensaje al servidor con el retorno del getter del atributo
     *                                 genero de la clase Libro, invocado a través del Libro libro, como contenido del
     *                                 mensaje.
     *                             </li>
     *                             <li>
     *                                 Se envía el objeto Biblioteca.Libro libro al servidor a través del método
     *                                 writeObject() invocado por el ObjectOutputStream oos.
     *                             </li>
     *                             <li>
     *                                 Se imprime un mensaje de confirmación en consola.
     *                             </li>
     *                         </ol>
     *                         En cualquier otro caso, en el bloque else:
     *                         <ol>
     *                             <li>
     *                                 Se envía el tercer mensaje al servidor con la cadena vacía como contenido del
     *                                 mensaje.
     *                             </li>
     *                             <li>
     *                                 Se envía el cuarto mensaje al servidor con la cadena vacía como contenido del
     *                                 mensaje.
     *                             </li>
     *                             <li>
     *                                 Se envía un objeto Biblioteca.Libro vacío al servidor a través del método
     *                                 writeObject() invocado por el ObjectOutputStream oos.
     *                             </li>
     *                             <li>
     *                                 Se imprime un mensaje de error en consola.
     *                             </li>
     *                         </ol>
     *                     </li>
     *                 </ol>
     *             </li>
     *             <li>
     *                 Se cierra el DataInputStream dis invocando el método close().
     *             </li>
     *             <li>
     *                 Se cierra el DataOutputStream dos invocando el método close().
     *             </li>
     *             <li>
     *                 Se cierra el ObjectInputStream ois invocando el método close().
     *             </li>
     *             <li>
     *                 Se cierra el ObjectOutputStream oos invocando el método close().
     *             </li>
     *         </ol>
     *         En el catch se controlan las excepciones de entrada o salida de datos, de interrupción de hilos y de
     *         deserialización de objetos a través de la excepción e. En caso de darse una excepción:
     *         <ol>
     *             <li>
     *                 Se invoca el método printStackTrace() a través del objeto e.
     *             </li>
     *             <li>
     *                 Se invoca el método interrupt() invocado por el objeto devuelto por el método estático
     *                 currentThread() de la clase Thread.
     *             </li>
     *         </ol>
     *     </li>
     * </ol>
     * @since v3.5.1
     * @see Biblioteca.Libro
     * @see Biblioteca.Libro#setTitulo(String)
     * @see Biblioteca.Libro#setGenero(String)
     * @see ObjectOutputStream#writeObject(Object)
     * @see ObjectInputStream#readObject()
     */
    @Override
    public void run() {
        Scanner scan = new Scanner(System.in);

        try (Socket cliente = new Socket()) {

            InetSocketAddress add = new InetSocketAddress("localhost", 5555);
            cliente.connect(add);
            System.out.println("Cliente " + idCliente + " conectado al servidor.");

            // PRIMERO streams de datos
            DataOutputStream dos = new DataOutputStream(cliente.getOutputStream());
            DataInputStream dis = new DataInputStream(cliente.getInputStream());

            // DESPUÉS streams de objetos
            ObjectOutputStream oos = new ObjectOutputStream(cliente.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(cliente.getInputStream());

            // Mensaje 1
            dos.writeInt(idCliente);

            // Mensaje 2 recibido
            System.out.println(dis.readUTF());

            // Mensaje 3
            String titulo = scan.nextLine();
            dos.writeUTF(titulo);

            // Mensaje 4 recibido
            String respuesta = dis.readUTF();
            System.out.println(respuesta);

            if (respuesta.contains("encontrado")) {

                // Mensaje 5 y 6
                String tituloLibro = dis.readUTF();
                String generoLibro = dis.readUTF();

                // Recibir OBJETO libro
                Biblioteca.Libro libro = (Biblioteca.Libro) ois.readObject();

                System.out.println("Qué libro tan interesante, me lo voy a leer:");
                System.out.println(libro);

                // Mensaje 7
                int tiempo = dis.readInt();
                Thread.sleep(tiempo);

                if (Math.random() > 0.05) {

                    // Mensaje 8 y 9
                    dos.writeUTF(libro.getTitulo());
                    dos.writeUTF(libro.getGenero());

                    // Enviar OBJETO libro devuelto
                    oos.writeObject(libro);
                    oos.flush();

                    System.out.println("He devuelto el libro correctamente.");

                } else {

                    dos.writeUTF("");
                    dos.writeUTF("");

                    // Enviar libro vacío
                    oos.writeObject(new Biblioteca.Libro());
                    oos.flush();

                    System.out.println("Po me lo voy a quedá");
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
}