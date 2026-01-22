package Tema4.Practica.Actividad1;

import Tema4.Practica.Actividad1.modelos.Mensaje;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Implementación de la interfaz Runnable. Simula el comportamiento de un cliente dentro del
 * servicio de mensajería, permitiendo enviar y consultar mensajes almacenados en el servidor.
 *
 * La clase establece una conexión con el servidor, envía peticiones según las acciones del usuario
 * y procesa las respuestas recibidas.
 *
 * @author Guillermo Martín Chippirraz
 * @version v4.0
 * @see Mensaje
 */
public class Cliente implements Runnable{
    private String host;
    private int puerto;
    private String nombre;

    /**
     * Constructor por defecto.
     * Inicializa los atributos con valores no válidos o vacíos.
     *
     * @since v4.0
     */
    public Cliente() {
        host = nombre = "";
        puerto = -1;
    }

    /**
     * Constructor por parámetros.
     *
     * @param host Dirección del servidor al que se conectará el cliente.
     * @param puerto Puerto del servidor.
     * @param nombre Nombre del usuario que utilizará el cliente.
     * @since v4.0
     */
    public Cliente(String host, int puerto, String nombre) {
        this.host = host;
        this.puerto = puerto;
        this.nombre = nombre;
    }

    /**
     * Constructor por parámetros sin nombre.
     *
     * @param host Dirección del servidor.
     * @param puerto Puerto del servidor.
     * @since v4.0
     */
    public Cliente(String host, int puerto) {
        this.host = host;
        this.puerto = puerto;
        this.nombre = "";
    }

    /**
     * Getter del host.
     * @since v4.0
     * @return String almacenado en el atributo «host»
     */
    public String getHost() { return host; }

    /**
     * Setter del host.
     * @since v4.0
     * @param host String a almacenar en el atributo «host»
     */
    public void setHost(String host) { this.host = host; }

    /**
     * Getter del puerto.
     * @since v4.0
     * @return Entero almacenado en el atributo «puerto»
     */    public int getPuerto() { return puerto; }

    /**
     * Setter del puerto.
     * @since v4.0
     * @param puerto Entero a almacenar en el atributo «puerto»
     */    public void setPuerto(int puerto) { this.puerto = puerto; }

    /**
     * Getter del nombre.
     * @since v4.0
     * @return String almacenado en el atributo «nombre»
     */    public String getNombre() { return nombre; }

    /**
     * Setter del nombre.
     * @since v4.0
     * @param nombre String a almacenar en el atributo «nombre»
     */    public void setNombre(String nombre) { this.nombre = nombre; }

    /**
     * Implementación del método run de la interfaz Runnable.
     * La línea de ejecución es la siguiente:
     *
     * <ol>
     *     <li>
     *         En los resources del try-with-resources se declara un objeto de la clase Socket «cliente»,
     *         inicializándose mediante el constructor por defecto. En este bloque:
     *         <ol>
     *             <li>
     *                 Se declara un objeto de la clase InetSocketAddress «addr», inicializándose mediante
     *                 el constructor por parámetros con los atributos «host» y «puerto». Este objeto
     *                 representa la dirección del servidor al que se desea conectar el cliente.
     *             </li>
     *             <li>
     *                 El Socket «cliente» invoca al método connect(addr), estableciendo la conexión con el
     *                 servidor. Esta llamada es bloqueante hasta que la conexión se completa.
     *             </li>
     *             <li>
     *                 Se declara un objeto BufferedReader «entrada», inicializándose mediante un
     *                 InputStreamReader asociado al flujo de entrada del Socket «cliente». Permite la
     *                 lectura secuencial de cadenas enviadas por el servidor.
     *             </li>
     *             <li>
     *                 Se declara un objeto PrintWriter «salida», inicializándose con el flujo de salida
     *                 del Socket «cliente» y habilitando el auto-flush. Permite enviar cadenas al servidor.
     *             </li>
     *             <li>
     *                 Se declara un objeto ObjectInputStream «entradaMensajes», inicializándose con el
     *                 flujo de entrada del Socket «cliente». Permite recibir colecciones serializadas de
     *                 objetos Mensaje.
     *             </li>
     *             <li>
     *                 Se declara un objeto Scanner «sc», inicializándose con System.in para permitir la
     *                 lectura de datos introducidos por el usuario desde la consola.
     *             </li>
     *             <li>
     *                 Se comprueba si el atributo «nombre» está vacío o compuesto únicamente por espacios.
     *                 En tal caso, se solicita al usuario que introduzca su nombre mediante el Scanner.
     *                 Posteriormente, se envía dicho nombre al servidor mediante el PrintWriter «salida».
     *             </li>
     *             <li>
     *                 Se muestra por consola un menú informativo con las opciones disponibles para el
     *                 usuario: consultar mensajes, enviar un mensaje o salir del sistema.
     *             </li>
     *             <li>
     *                 Se declara una variable String «opcion». Se inicia un bucle do-while que se ejecutará
     *                 mientras el usuario no introduzca la opción «q». En cada iteración:
     *                 <ol>
     *                     <li>
     *                         Se lee desde consola la opción seleccionada por el usuario y se envía al
     *                         servidor mediante el PrintWriter «salida».
     *                     </li>
     *                     <li>
     *                         Se evalúa el contenido de «opcion» mediante una estructura switch:
     *                         <ol>
     *                             <li>
     *                                 Caso «consultar»:
     *                                 <ul>
     *                                     <li>Se recibe desde el ObjectInputStream «entradaMensajes» una
     *                                     colección ArrayList&lt;Mensaje&gt; enviada por el servidor.</li>
     *                                     <li>Se muestra por consola el buzón del usuario.</li>
     *                                     <li>Si la colección está vacía, se informa al usuario. En caso
     *                                     contrario, se invoca al método privado muestraMensajes().</li>
     *                                 </ul>
     *                             </li>
     *                             <li>
     *                                 Caso «enviar»:
     *                                 <ul>
     *                                     <li>Se solicita al usuario el destino del mensaje, el asunto y el
     *                                     contenido, enviándose cada uno al servidor mediante «salida».</li>
     *                                     <li>Se informa al usuario de que el mensaje ha sido enviado.</li>
     *                                 </ul>
     *                             </li>
     *                             <li>
     *                                 Caso «q»:
     *                                 <ul>
     *                                     <li>Se muestra un mensaje de despedida por consola.</li>
     *                                 </ul>
     *                             </li>
     *                             <li>
     *                                 Caso por defecto:
     *                                 <ul>
     *                                     <li>Se lee desde «entrada» un mensaje enviado por el servidor
     *                                     indicando que la opción no existe.</li>
     *                                     <li>Se muestra dicho mensaje por consola.</li>
     *                                 </ul>
     *                             </li>
     *                         </ol>
     *                     </li>
     *                 </ol>
     *             </li>
     *         </ol>
     *     </li>
     * </ol>
     *
     * @since v4.0
     * @see #muestraMensajes(ArrayList)
     */
    public void run() {
        try(Socket cliente = new Socket()) {
            InetSocketAddress addr = new InetSocketAddress(host, puerto);
            cliente.connect(addr);
            BufferedReader entrada = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
            PrintWriter salida = new PrintWriter(cliente.getOutputStream(), true);
            ObjectInputStream entradaMensajes = new ObjectInputStream(cliente.getInputStream());
            Scanner sc = new Scanner(System.in);

            if (nombre.isEmpty() || nombre.isBlank()){
                System.out.println("Por favor, escriba su nombre");
                setNombre(sc.nextLine());
            }
            salida.println(nombre);
            System.out.println("Bienvenido/a al servicio de mensajería. Seleccione una de las siguientes opciones para" +
                    "continuar:" +
                    "\n Consultar; Busca mensajes nuevos en el buzón." +
                    "\n Enviar: Enviar un nuevo mensaje a otro usuario." +
                    "\n Q: Salir.");
            String opcion;
            do {
                opcion = sc.nextLine();
                salida.println(opcion);
                switch (opcion.toLowerCase()) {
                    case "consultar" -> {
                        ArrayList<Mensaje> mensajes = (ArrayList<Mensaje>) entradaMensajes.readObject();
                        System.out.println("------ Buzón ------");
                        if (mensajes.isEmpty()) {
                            System.out.println("Sin mensajes nuevos.");
                        } else muestraMensajes(mensajes);
                        System.out.println("Siguiente operación: ");
                    }
                    case "enviar" -> {
                        System.out.print("Destino: ");
                        String destino = sc.nextLine();
                        salida.println(destino);
                        System.out.println("Asunto: ");
                        String asunto = sc.nextLine();
                        salida.println(asunto);
                        System.out.println("Contenido: ");
                        String contenido = sc.nextLine();
                        salida.println(contenido);
                        System.out.println("Mensaje enviado con éxito.");
                        System.out.println("Siguiente operación: ");
                    }
                    case "q" -> {
                        System.out.println("Esperamos que haya tenido una experiencia agradable");
                    }
                    default -> {
                        System.out.println(entrada.readLine());
                        System.out.println("Siguiente operación: ");
                    }
                }
            } while (!opcion.toLowerCase().equals("q"));
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Método privado encargado de mostrar por consola el contenido de una colección de mensajes.
     *
     * @param mensajes Colección de objetos Mensaje a mostrar.
     * @since v4.0
     * @see Mensaje#toString()
     */
    private void muestraMensajes(ArrayList<Mensaje> mensajes) {
        for (Mensaje mensaje : mensajes) {
            System.out.println(mensaje.toString());
        }
    }
}
