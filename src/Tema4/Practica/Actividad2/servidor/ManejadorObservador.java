package Tema4.Practica.Actividad2.servidor;

import Tema4.Practica.Actividad2.modelos.UsuarioObservador;
import Tema4.Practica.Actividad2.util.LoggerSistema;

import java.io.*;
import java.net.Socket;
import java.util.List;
import java.util.Map;

public class ManejadorObservador implements Runnable {

    private final Socket socket;
    private final Map<String, UsuarioObservador> observadores;
    private final Map<String, List<Socket>> suscriptores;

    public ManejadorObservador(Socket socket,
                               Map<String, UsuarioObservador> observadores,
                               Map<String, List<Socket>> suscriptores) {

        this.socket = socket;
        this.observadores = observadores;
        this.suscriptores = suscriptores;
    }

    @Override
    public void run() {
        try {
            BufferedReader entrada = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            PrintWriter salida = new PrintWriter(socket.getOutputStream(), true);

            LoggerSistema.info("Observador conectado desde " + socket.getInetAddress());

            String login = entrada.readLine();
            String[] p = login.split(" ");
            String comandoLogin = p[0].toUpperCase();
            boolean loginCorrecto = "LOGIN".equals(comandoLogin);

            if (loginCorrecto) {
                String usuario = p[1];
                String pass = p[2];
                if (!observadores.containsKey(usuario)) {
                    observadores.put(usuario, new UsuarioObservador(usuario, pass));
                }
                salida.println("OK LOGGED");

                boolean activo = true;
                while (activo) {
                    String linea = entrada.readLine();
                    if (linea == null) {
                        activo = false;
                    } else {
                        String[] partes = linea.split(" ");
                        String cmd = partes[0].toUpperCase();

                        if ("SUBSCRIBE".equals(cmd)) {
                            String idSensor = partes[1];
                            List<Socket> lista = suscriptores.get(idSensor);
                            if (lista != null) {
                                lista.add(socket);
                                salida.println("OK SUBSCRIBED " + idSensor);
                            } else {
                                salida.println("ERROR SENSOR_NOT_FOUND");
                            }
                        } else if ("UNSUBSCRIBE".equals(cmd)) {
                            String idSensor = partes[1];
                            List<Socket> lista = suscriptores.get(idSensor);
                            if (lista != null) {
                                int i = 0;
                                while (i < lista.size()) {
                                    Socket s = lista.get(i);
                                    boolean mismo = s == socket;
                                    if (mismo) {
                                        lista.remove(i);
                                    } else {
                                        i = i + 1;
                                    }
                                }
                                salida.println("OK UNSUBSCRIBED " + idSensor);
                            } else {
                                salida.println("ERROR SENSOR_NOT_FOUND");
                            }
                        } else if ("QUIT".equals(cmd)) {
                            salida.println("BYE");
                            activo = false;
                        } else {
                            salida.println("ERROR UNKNOWN_COMMAND");
                        }
                    }
                }
            } else {
                salida.println("ERROR LOGIN_REQUIRED");
            }

        } catch (IOException e) {
            LoggerSistema.warn("Observador desconectado.");
        }
    }
}
