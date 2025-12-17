package Tema3.Practica.Actividades3Y4;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

public class Servidor implements Runnable {
    private Gestor gestor;
    private static final int PUERTO = 5555;
    private static final String HOSTNAME = "localhost";

    // Usuarios registrados
    private ArrayList<Usuario> usuarios = new ArrayList<>();

    // Historial
    private ArrayList<String> historialGlobal = new ArrayList<>();
    private HashMap<String, ArrayList<String>> historialUsuarios = new HashMap<>();

    public Servidor() {
        gestor = new Gestor();
        inicializarUsuarios();
    }

    public Servidor(Gestor gestor) {
        this.gestor = gestor;
        inicializarUsuarios();
    }

    public Gestor getGestor() {
        return gestor;
    }

    private void inicializarUsuarios() {
        usuarios.add(new Usuario("admin", "admin123", true));
        usuarios.add(new Usuario("user1", "pass1", false));
        usuarios.add(new Usuario("user2", "pass2", false));
    }

    private void registrarOperacion(Usuario usuario, String comando) {
        historialGlobal.add(usuario.getNombre() + ": " + comando);
        historialUsuarios.putIfAbsent(usuario.getNombre(), new ArrayList<>());
        historialUsuarios.get(usuario.getNombre()).add(comando);
    }

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
