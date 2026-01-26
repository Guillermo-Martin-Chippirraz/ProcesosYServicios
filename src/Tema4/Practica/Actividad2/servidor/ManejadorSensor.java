package Tema4.Practica.Actividad2.servidor;
import Tema4.Practica.Actividad2.modelos.Alerta;
import Tema4.Practica.Actividad2.modelos.LecturaSensor;
import Tema4.Practica.Actividad2.modelos.SensorInfo;
import Tema4.Practica.Actividad2.util.LoggerSistema;

import java.io.*;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class ManejadorSensor implements Runnable {

    private final Socket socket;
    private final Map<String, SensorInfo> sensores;
    private final Map<String, List<Socket>> suscriptores;
    private final DistribuidorNotificaciones distribuidor;

    public ManejadorSensor(Socket socket,
                           Map<String, SensorInfo> sensores,
                           Map<String, List<Socket>> suscriptores,
                           DistribuidorNotificaciones distribuidor) {

        this.socket = socket;
        this.sensores = sensores;
        this.suscriptores = suscriptores;
        this.distribuidor = distribuidor;
    }

    @Override
    public void run() {
        BufferedReader entrada = null;
        PrintWriter salida = null;
        try {
            entrada = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            salida = new PrintWriter(socket.getOutputStream(), true);

            LoggerSistema.info("Sensor conectado desde " + socket.getInetAddress());

            boolean activo = true;
            while (activo) {
                String linea = entrada.readLine();
                if (linea == null) {
                    activo = false;
                } else {
                    LoggerSistema.info("Comando sensor: " + linea);
                    String[] p = linea.split(" ");
                    String comando = p[0].toUpperCase();

                    if ("REGISTER".equals(comando)) {
                        String id = p[1];
                        String tipo = p[2];
                        int intervalo = Integer.parseInt(p[3]);
                        sensores.put(id, new SensorInfo(id, tipo, intervalo));
                        if (!suscriptores.containsKey(id)) {
                            suscriptores.put(id, new java.util.ArrayList<>());
                        }
                        salida.println("OK REGISTERED");
                    } else if ("CONFIG".equals(comando)) {
                        String id = p[1];
                        double min = Double.parseDouble(p[2]);
                        double max = Double.parseDouble(p[3]);
                        SensorInfo info = sensores.get(id);
                        if (info != null) {
                            info.setUmbralMin(min);
                            info.setUmbralMax(max);
                            salida.println("OK CONFIGURED");
                        } else {
                            salida.println("ERROR SENSOR_NOT_FOUND");
                        }
                    } else if ("DATA".equals(comando)) {
                        String id = p[1];
                        double valor = Double.parseDouble(p[2]);
                        SensorInfo info = sensores.get(id);
                        if (info != null) {
                            LecturaSensor lectura = new LecturaSensor(id, LocalDateTime.now(), valor);
                            distribuidor.enviarLectura(lectura);
                            boolean fuera = valor < info.getUmbralMin() || valor > info.getUmbralMax();
                            if (fuera) {
                                Alerta alerta = new Alerta(id, LocalDateTime.now(), valor, "FUERA_DE_UMBRAL");
                                distribuidor.enviarAlerta(alerta);
                            }
                        } else {
                            salida.println("ERROR SENSOR_NOT_FOUND");
                        }
                    } else {
                        salida.println("ERROR UNKNOWN_COMMAND");
                    }
                }
            }

        } catch (IOException e) {
            LoggerSistema.warn("Sensor desconectado.");
        }
    }
}
