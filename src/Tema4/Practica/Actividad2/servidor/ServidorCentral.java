package Tema4.Practica.Actividad2.servidor;

import Tema4.Practica.Actividad2.modelos.SensorInfo;
import Tema4.Practica.Actividad2.modelos.UsuarioObservador;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ServidorCentral implements Runnable{
    private Map<String, SensorInfo> sensoresRegistrados;
    private Map<String, List<Socket>> suscriptoresPorSensor;
    private Map<String, UsuarioObservador> observadoresAutenticados;

    private final String host;
    private final  int puerto;
    public ServidorCentral() {
        sensoresRegistrados = null;
        suscriptoresPorSensor = null;
        observadoresAutenticados = null;
        host = "";
        puerto = 0;
    }

    public ServidorCentral(String host, int puerto) {
        this.puerto = puerto;
        this.host = host;
        this.sensoresRegistrados = new ConcurrentHashMap<>();
        this.suscriptoresPorSensor = new ConcurrentHashMap<>();
        this.observadoresAutenticados = new ConcurrentHashMap<>();
    }

    public void run() {
        try(ServerSocket servidor = new ServerSocket()) {

            InetSocketAddress addr = new InetSocketAddress(host, puerto);
            servidor.bind(addr);
            System.out.println("ServidorCentral escuchando en " + host + ":" + puerto);

            while (true) {
                Socket cliente = servidor.accept();
                System.out.println("Conexión entrante: " + cliente.getInetAddress());

                BufferedReader entrada = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
                PrintWriter salida = new PrintWriter(cliente.getOutputStream());

                String tipo = entrada.readLine();

                if (tipo != null) {

                    switch (tipo.toUpperCase()) {
                        case "SENSOR" -> {
                            ManejadorSensor manejador = new ManejadorSensor(cliente, sensoresRegistrados,
                                    suscriptoresPorSensor);
                            new Thread(manejador).start();
                        }
                        case "OBSERVADOR" -> {
                            ManejadorObservador manejador = new ManejadorObservador(cliente, observadoresAutenticados,
                                    suscriptoresPorSensor);
                            new Thread(manejador).start();
                        }
                        default -> {
                            salida.println("ERROR Tipo de cliente no reconocido");
                            cliente.close();
                        }
                    }
                } else cliente.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
