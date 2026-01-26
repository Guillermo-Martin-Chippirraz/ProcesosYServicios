package Tema4.Practica.Actividad2.observadores;

import Tema4.Practica.Actividad2.util.LoggerSistema;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class ClienteObservador implements Runnable {

    private final String host;
    private final int puerto;
    private final String usuario;
    private final String password;

    public ClienteObservador(String host, int puerto, String usuario, String password) {
        this.host = host;
        this.puerto = puerto;
        this.usuario = usuario;
        this.password = password;
    }

    @Override
    public void run() {
        try {
            Socket socket = new Socket();
            socket.connect(new InetSocketAddress(host, puerto));

            PrintWriter salida = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader entrada = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));

            Scanner sc = new Scanner(System.in);

            salida.println("OBSERVADOR");

            salida.println("LOGIN " + usuario + " " + password);
            String resp = entrada.readLine();
            System.out.println("Servidor: " + resp);

            Thread listener = new Thread(() -> {
                boolean activoListener = true;
                while (activoListener) {
                    try {
                        String linea = entrada.readLine();
                        if (linea == null) {
                            activoListener = false;
                        } else {
                            System.out.println("[NOTIFICACIÓN] " + linea);
                        }
                    } catch (IOException e) {
                        activoListener = false;
                    }
                }
            });
            listener.start();

            boolean activo = true;
            while (activo) {
                System.out.println("Comandos: SUBSCRIBE <id>, UNSUBSCRIBE <id>, QUIT");
                String comando = sc.nextLine();
                salida.println(comando);
                String cmd = comando.toUpperCase();
                if ("QUIT".equals(cmd)) {
                    System.out.println("Cerrando sesión...");
                    activo = false;
                }
            }

        } catch (IOException e) {
            LoggerSistema.warn("Observador desconectado.");
        }
    }
}
