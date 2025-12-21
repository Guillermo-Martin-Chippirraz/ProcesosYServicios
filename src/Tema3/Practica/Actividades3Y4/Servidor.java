package Tema3.Practica.Actividades3Y4;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Clase que modela el servidor para las actividades 3 y 4 de la práctica del tema 3.
 *
 * @author Guillermo Martín Chippirraz
 * @version v3.5.2
 * @see Gestor
 * @see Usuario
 * @see CuentaBancaria
 */
public class Servidor implements Runnable {
    private Gestor gestor;
    private static final int PUERTO = 5555;
    private static final String HOSTNAME = "localhost";

    // Usuarios registrados
    private ArrayList<Usuario> usuarios = new ArrayList<>();

    // Historial
    private ArrayList<String> historialGlobal = new ArrayList<>();
    private HashMap<String, ArrayList<String>> historialUsuarios = new HashMap<>();

    /**
     * Constructor por defecto.
     * Inicializa el atributo gestor mediante su constructor por defecto e invoca el método
     * inicializarUsuarios() para registrar los usuarios disponibles.
     *
     * <ol>
     *     <li>Se asigna un nuevo objeto Gestor al atributo gestor.</li>
     *     <li>Se invoca el método inicializarUsuarios().</li>
     * </ol>
     *
     * @since v3.5
     * @see #inicializarUsuarios()
     */
    public Servidor() {
        gestor = new Gestor();
        inicializarUsuarios();
    }

    /**
     * Constructor por parámetros.
     * Inicializa el atributo gestor con el objeto recibido e invoca el método inicializarUsuarios().
     *
     * <ol>
     *     <li>Se asigna el objeto gestor recibido al atributo gestor.</li>
     *     <li>Se invoca el método inicializarUsuarios().</li>
     * </ol>
     * @since v3.5
     * @see #inicializarUsuarios()
     * @param gestor Objeto Gestor que gestionará las cuentas bancarias.
     */
    public Servidor(Gestor gestor) {
        this.gestor = gestor;
        inicializarUsuarios();
    }

    /**
     * Getter del atributo gestor.
     * @since v3.5
     * @return Objeto Gestor almacenado en el atributo gestor.
     */
    public Gestor getGestor() {
        return gestor;
    }

    /**
     * Método inicializarUsuarios.
     * Registra los usuarios disponibles en el sistema.
     *
     * <ol>
     *     <li>Se añaden tres objetos Usuario al ArrayList usuarios: un administrador y dos usuarios estándar.</li>
     * </ol>
     *
     * @since v3.5
     * @see Usuario
     */
    private void inicializarUsuarios() {
        usuarios.add(new Usuario("admin", "admin123", true));
        usuarios.add(new Usuario("user1", "pass1", false));
        usuarios.add(new Usuario("user2", "pass2", false));
    }

    /**
     * Método registrarOperacion.
     * Registra una operación realizada por un usuario tanto en el historial global como en el historial
     * individual del usuario.
     *
     * <ol>
     *     <li>Se añade al historial global una cadena formada por el nombre del usuario y el comando ejecutado.</li>
     *     <li>Se invoca putIfAbsent() sobre historialUsuarios para inicializar la lista del usuario si no existe.</li>
     *     <li>Se añade el comando a la lista correspondiente al usuario.</li>
     * </ol>
     * @since v3.5
     * @param usuario Usuario que ha realizado la operación.
     * @param comando Comando ejecutado por el usuario.
     */
    private void registrarOperacion(Usuario usuario, String comando) {
        historialGlobal.add(usuario.getNombre() + ": " + comando);
        historialUsuarios.putIfAbsent(usuario.getNombre(), new ArrayList<>());
        historialUsuarios.get(usuario.getNombre()).add(comando);
    }

    /**
     * Método run implementado de la interfaz Runnable.
     * Contiene toda la lógica del servidor: apertura del socket, autenticación de usuarios, lectura de
     * comandos, ejecución de operaciones bancarias y envío de respuestas al cliente.
     *
     * La línea de ejecución es la siguiente:
     * <ol>
     *     <li>
     *         Se abre un bloque try-with-resources declarando un objeto ServerSocket serverSocket,
     *         inicializado mediante su constructor por defecto.
     *     </li>
     *     <li>
     *         Se declara un objeto InetSocketAddress add, inicializado mediante su constructor por parámetros
     *         introduciendo HOSTNAME como valor del parámetro hostname y PUERTO como valor del parámetro port.
     *     </li>
     *     <li>
     *         Se invoca el método bind() sobre serverSocket introduciendo add como parámetro.
     *     </li>
     *     <li>
     *         Se abre un bucle while infinito. En cada iteración:
     *         <ol>
     *             <li>
     *                 Se declara un objeto Socket cliente, inicializado mediante el método accept() invocado
     *                 sobre serverSocket.
     *             </li>
     *             <li>
     *                 Se declaran los objetos DataInputStream dis y DataOutputStream dos, inicializados mediante
     *                 los métodos getInputStream() y getOutputStream() invocados sobre cliente.
     *             </li>
     *             <li>
     *                 Se envía al cliente el mensaje "Introduzca usuario:" mediante writeUTF().
     *             </li>
     *             <li>
     *                 Se recibe el nombre de usuario mediante readUTF().
     *             </li>
     *             <li>
     *                 Se envía al cliente el mensaje "Introduzca contraseña:" mediante writeUTF().
     *             </li>
     *             <li>
     *                 Se recibe la contraseña mediante readUTF().
     *             </li>
     *             <li>
     *                 Se declara un objeto Usuario autenticado inicializado a null.
     *             </li>
     *             <li>
     *                 Se recorre el ArrayList usuarios mediante un bucle for-each. En cada iteración:
     *                 <ol>
     *                     <li>Se comparan el nombre y la contraseña recibidos con los del objeto Usuario iterado.</li>
     *                     <li>Si coinciden, se asigna dicho objeto al atributo autenticado.</li>
     *                 </ol>
     *             </li>
     *             <li>
     *                 Se abre una estructura if para evaluar si autenticado es null.
     *                 <ol>
     *                     <li>
     *                         Si es null, se envía al cliente el mensaje "Credenciales inválidas. Conexión cerrada."
     *                         y se invoca el método close() sobre cliente.
     *                     </li>
     *                     <li>
     *                         En caso contrario:
     *                         <ol>
     *                             <li>
     *                                 Se envía un mensaje de bienvenida al cliente, indicando si es administrador.
     *                             </li>
     *                             <li>
     *                                 Se envía al cliente un menú de operaciones disponibles.
     *                             </li>
     *                             <li>
     *                                 Se recibe el comando introducido por el cliente mediante readUTF().
     *                             </li>
     *                             <li>
     *                                 Se divide el comando mediante split(" ") y se almacena en un array partes.
     *                             </li>
     *                             <li>
     *                                 Se declara un String response inicializado como cadena vacía.
     *                             </li>
     *                             <li>
     *                                 Se abre una estructura condicional que evalúa partes[0] para determinar
     *                                 la operación solicitada:
     *                                 <ol>
     *                                     <li>
     *                                         CONSULTAR: se obtiene la cuenta mediante getByNum(), se consulta
     *                                         el saldo mediante consultar() y se construye la respuesta.
     *                                     </li>
     *                                     <li>
     *                                         INGRESAR: se obtiene la cuenta y se invoca ingresar().
     *                                     </li>
     *                                     <li>
     *                                         RETIRAR: se obtiene la cuenta y se invoca retirar().
     *                                     </li>
     *                                     <li>
     *                                         TRANSFERIR: se obtienen las dos cuentas y se invoca transferir().
     *                                     </li>
     *                                     <li>
     *                                         HISTORIAL:
     *                                         <ol>
     *                                             <li>
     *                                                 Si el usuario es administrador, se envía línea por línea
     *                                                 el historial global.
     *                                             </li>
     *                                             <li>
     *                                                 Si no lo es, se envía su historial individual.
     *                                             </li>
     *                                         </ol>
     *                                     </li>
     *                                     <li>
     *                                         GUARDAR: se invoca guardarCuentas() dentro de un bloque try-catch.
     *                                     </li>
     *                                     <li>
     *                                         CARGAR: se invoca cargarCuentas() dentro de un bloque try-catch.
     *                                     </li>
     *                                     <li>
     *                                         En cualquier otro caso, se asigna a response un mensaje indicando
     *                                         que el comando no existe.
     *                                     </li>
     *                                 </ol>
     *                             </li>
     *                             <li>
     *                                 Se invoca registrarOperacion() para almacenar la operación realizada.
     *                             </li>
     *                             <li>
     *                                 Se envía al cliente la respuesta final mediante writeUTF().
     *                             </li>
     *                             <li>
     *                                 Se invoca el método close() sobre cliente.
     *                             </li>
     *                         </ol>
     *                     </li>
     *                 </ol>
     *             </li>
     *         </ol>
     *     </li>
     *     <li>
     *         En el bloque catch se controla la excepción IOException. En caso de producirse:
     *         <ol>
     *             <li>Se invoca el método printStackTrace() sobre el objeto e.</li>
     *         </ol>
     *     </li>
     *     <li>
     *         En el bloque finally se imprime por consola el mensaje "Conexión finalizada.".
     *     </li>
     * </ol>
     *
     * @since v3.5
     * @see Gestor
     * @see Usuario
     */
    public void run() {
        try (ServerSocket serverSocket = new ServerSocket()) {
            InetSocketAddress add = new InetSocketAddress(HOSTNAME, PUERTO);
            serverSocket.bind(add);
            while (true) {
                Socket cliente = serverSocket.accept();
                DataInputStream dis = new DataInputStream(cliente.getInputStream());
                DataOutputStream dos = new DataOutputStream(cliente.getOutputStream());

                // Autenticación
                dos.writeUTF("Introduzca usuario: ");
                String user = dis.readUTF();
                dos.writeUTF("Introduzca contraseña: ");
                String pass = dis.readUTF();

                Usuario autenticado = null;
                for (Usuario u : usuarios) {
                    if (u.getNombre().equals(user) && u.getPassword().equals(pass)) {
                        autenticado = u;
                    }
                }

                if (autenticado == null) {
                    dos.writeUTF("Credenciales inválidas. Conexión cerrada.");
                    cliente.close();
                } else {
                    dos.writeUTF("Bienvenido " + autenticado.getNombre() +
                            (autenticado.isAdmin() ? " (ADMIN)" : ""));

                    dos.writeUTF("Por favor, seleccione alguna de las siguientes operaciones\n" +
                            "1) CONSULTAR <numCuenta>\n" +
                            "2) INGRESAR <numCuenta> <cantidad>\n" +
                            "3) RETIRAR <numCuenta> <cantidad>\n" +
                            "4) TRANSFERIR <cuentaOrigen> <cuentaDestino> <cantidad>\n" +
                            "5) HISTORIAL\n" +
                            "6) GUARDAR <fichero>\n" +
                            "7) CARGAR <fichero>");

                    String opcion = dis.readUTF();
                    System.out.println("Comando recibido: " + opcion);
                    String[] partes = opcion.split(" ");
                    String response = "";

                    if (partes[0].equalsIgnoreCase("CONSULTAR")) {
                        CuentaBancaria cuenta = gestor.getByNum(partes[1]);
                        float saldo = gestor.consultar(cuenta);
                        response = "Saldo disponible: " + saldo + " €";
                        registrarOperacion(autenticado, opcion);

                    } else if (partes[0].equalsIgnoreCase("INGRESAR")) {
                        CuentaBancaria cuenta = gestor.getByNum(partes[1]);
                        response = gestor.ingresar(cuenta, Float.parseFloat(partes[2]));
                        registrarOperacion(autenticado, opcion);

                    } else if (partes[0].equalsIgnoreCase("RETIRAR")) {
                        CuentaBancaria cuenta = gestor.getByNum(partes[1]);
                        response = gestor.retirar(cuenta, Float.parseFloat(partes[2]));
                        registrarOperacion(autenticado, opcion);

                    } else if (partes[0].equalsIgnoreCase("TRANSFERIR")) {
                        CuentaBancaria origen = gestor.getByNum(partes[1]);
                        CuentaBancaria destino = gestor.getByNum(partes[2]);
                        response = gestor.transferir(origen, destino, Float.parseFloat(partes[3]));
                        registrarOperacion(autenticado, opcion);

                    } else if (partes[0].equalsIgnoreCase("HISTORIAL")) {
                        if (autenticado.isAdmin()) {
                            for (String op : historialGlobal) {
                                dos.writeUTF(op);
                            }
                        } else {
                            ArrayList<String> hist = historialUsuarios.get(autenticado.getNombre());
                            if (hist != null) {
                                for (String op : hist) {
                                    dos.writeUTF(op);
                                }
                            } else {
                                dos.writeUTF("No hay operaciones registradas.");
                            }
                        }
                        response = "Historial mostrado.";
                        registrarOperacion(autenticado, opcion);

                    } else if (partes[0].equalsIgnoreCase("GUARDAR")) {
                        try {
                            gestor.guardarCuentas(partes[1]);
                            response = "Cuentas guardadas en " + partes[1];
                        } catch (IOException e) {
                            response = "Error al guardar: " + e.getMessage();
                        }
                        registrarOperacion(autenticado, opcion);

                    } else if (partes[0].equalsIgnoreCase("CARGAR")) {
                        try {
                            gestor.cargarCuentas(partes[1]);
                            response = "Cuentas cargadas desde " + partes[1];
                        } catch (IOException e) {
                            response = "Error al cargar: " + e.getMessage();
                        }
                        registrarOperacion(autenticado, opcion);

                    } else {
                        response = "El comando introducido no existe.";
                    }

                    dos.writeUTF(response);
                    cliente.close();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            System.out.println("Conexión finalizada.");
        }
    }
}
