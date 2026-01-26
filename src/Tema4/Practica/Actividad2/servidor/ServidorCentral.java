package Tema4.Practica.Actividad2.servidor;

import Tema4.Practica.Actividad2.modelos.SensorInfo;
import Tema4.Practica.Actividad2.modelos.UsuarioObservador;
import Tema4.Practica.Actividad2.util.LoggerSistema;

import java.io.*;
import java.net.*;
import java.util.*;

public class ServidorCentral implements Runnable {

    private final String host;
    private final int puerto;

    private final Map<String, SensorInfo> sensores = new HashMap<>();
    private final Map<String, UsuarioObservador> observadores = new HashMap<>();
    private final Map<String, List<Socket>> suscriptores = new HashMap<>();

    private final DistribuidorNotificaciones distribuidor =
            new DistribuidorNotificaciones(suscriptores);

    public ServidorCentral(String host, int puerto) {
        this.host = host;
        this.puerto = puerto;
    }

    @Override
    public void run() {
        try {
            ServerSocket servidor = new ServerSocket();
            servidor.bind(new InetSocketAddress(host, puerto));
            LoggerSistema.info("ServidorCentral escuchando en " + host + ":" + puerto);

            boolean activo = true;
            while (activo) {
                Socket cliente = servidor.accept();
                BufferedReader entrada = new BufferedReader(
                        new InputStreamReader(cliente.getInputStream()));
                PrintWriter salida = new PrintWriter(cliente.getOutputStream(), true);

                String tipo = entrada.readLine();
                String t = tipo.toUpperCase();

                if ("SENSOR".equals(t)) {
                    Thread h = new Thread(new ManejadorSensor(
                            cliente, sensores, suscriptores, distribuidor));
                    h.start();
                } else if ("OBSERVADOR".equals(t)) {
                    Thread h = new Thread(new ManejadorObservador(
                            cliente, observadores, suscriptores));
                    h.start();
                } else {
                    salida.println("ERROR UNKNOWN_TYPE");
                    cliente.close();
                }
            }

        } catch (IOException e) {
            LoggerSistema.error("Error en ServidorCentral");
        }
    }
}
