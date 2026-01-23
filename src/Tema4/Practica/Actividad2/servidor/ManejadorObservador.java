package Tema4.Practica.Actividad2.servidor;

import Tema4.Practica.Actividad2.modelos.UsuarioObservador;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;
import java.util.Map;

public class ManejadorObservador implements Runnable {

    private final Socket socket;
    private final Map<String, UsuarioObservador> observadores;
    private final Map<String, List<Socket>> suscriptores;

    public ManejadorObservador(Socket socket, Map<String, UsuarioObservador> observadores,
                               Map<String, List<Socket>> suscriptores) {
        this.socket = socket;
        this.observadores = observadores;
        this.suscriptores = suscriptores;
    }

    public void run() {
        try {
            BufferedReader entrada = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            PrintWriter salida = new PrintWriter(socket.getOutputStream());

            String login = entrada.readLine();
            String[] partes = login.split(" ");

            if (partes[0].equalsIgnoreCase("LOGIN")) {
                String usuario = partes[1];
                String pass = partes[2];

                observadores.putIfAbsent(usuario, new UsuarioObservador(usuario, pass));

                salida.println("OK LOGGED");

                boolean continuar = true;

                while (continuar) {
                    String linea = entrada.readLine();
                    if (linea == null) continuar = false;
                    else {

                        partes = linea.split(" ");

                        switch (partes[0].toUpperCase()) {
                            case "SUSCRIBE" -> {
                                String idSensor = partes[1];
                                suscriptores.get(idSensor).add(socket);
                                salida.println("OK SUSCRIBED " + idSensor);
                            }

                            case "UNSUSCRIBE" -> {
                                String idSensor = partes[1];
                                suscriptores.get(idSensor).remove(socket);
                                salida.println("OK UNSUSCRIBED " + idSensor);
                            }

                            case "QUIT" -> {
                                salida.println("BYE");
                                socket.close();
                                continuar = false;
                            }

                            default -> salida.println("ERROR UNKNOWN_COMMAND");
                        }
                    }
                }
            } else {
                salida.println("ERROR LOGIN_REQUIRED");
                socket.close();
            }
        } catch (IOException e) {
            System.out.println("Observador desconectado: " + socket.getInetAddress());
        }
    }
}
