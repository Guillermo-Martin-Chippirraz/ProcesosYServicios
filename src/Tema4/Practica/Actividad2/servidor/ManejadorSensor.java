package Tema4.Practica.Actividad2.servidor;

import Tema4.Practica.Actividad2.modelos.Alerta;
import Tema4.Practica.Actividad2.modelos.LecturaSensor;
import Tema4.Practica.Actividad2.modelos.SensorInfo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ManejadorSensor implements Runnable{

    private final Socket socket;
    private final Map<String, SensorInfo> sensoresRegistrados;
    private final Map<String, List<Socket>> suscriptores;

    public ManejadorSensor(Socket socket, Map<String, SensorInfo> sensoresRegistrados,
                               Map<String, List<Socket>> suscriptores) {
        this.socket = socket;
        this.sensoresRegistrados = sensoresRegistrados;
        this.suscriptores = suscriptores;
    }

    public void run() {
        try {
            BufferedReader entrada = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            PrintWriter salida = new PrintWriter(socket.getOutputStream());

            boolean continuar = true;
            while (continuar) {
                String linea = entrada.readLine();
                if (linea == null) continuar = false;
                else {
                    String[] partes = linea.split(" ");

                    switch (partes[0].toUpperCase()) {
                        case "REGISTER" -> {
                            String id = partes[1];
                            String tipo = partes[2];
                            int intervalo = Integer.parseInt(partes[3]);

                            sensoresRegistrados.put(id,
                                    new SensorInfo(id, tipo, intervalo));

                            suscriptores.putIfAbsent(id, new ArrayList<>());

                            salida.println("OK REGISTERED");
                        }

                        case "CONFIG" -> {
                            String id = partes[1];
                            double min = Double.parseDouble(partes[2]);
                            double max = Double.parseDouble(partes[3]);

                            SensorInfo info = sensoresRegistrados.get(id);
                            if (info != null) {
                                info.setUmbralMin(min);
                                info.setUmbralMax(max);
                                salida.println("OK CONGURED");
                            } else {
                                salida.println("ERROR SENSOR_NOT_FOUND");
                            }
                        }

                        case "DATA" -> {
                            String id = partes[1];
                            double valor = Double.parseDouble(partes[2]);
                            LocalDateTime ts = LocalDateTime.now();

                            SensorInfo info = sensoresRegistrados.get(id);
                            DistribuidorNotificaciones distribuidor = new DistribuidorNotificaciones(suscriptores);
                            if (info != null) {
                                distribuidor.enviarLectura(new LecturaSensor(id, ts, valor));

                                if (valor < info.getUmbralMin() || valor > info.getUmbralMax())
                                    distribuidor.enviarAlerta(new Alerta(id, ts, valor, "Fuera de umbral"));
                            } else {
                                salida.println("ERROR SENSOR_NOT_FOUND");
                                continuar = false;
                            }
                        }

                        default -> salida.println("ERROR UNKNOWN_COMMAND");
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Sensor desconectado: " + socket.getInetAddress());
        }
    }

    private void notificarLectura(String id, LocalDateTime ts, double valor) {
        List<Socket> lista = suscriptores.get(id);
        if (lista != null) {
            for (Socket obs : lista) {
                try {
                    PrintWriter out = new PrintWriter(obs.getOutputStream());
                    out.println("NOTIFY " + id + " " + ts + " " + valor);
                } catch (IOException ignorar) {}
            }
        }
    }

    private void notificarAlerta(String id, LocalDateTime ts, double valor) {
        List<Socket> lista = suscriptores.get(id);
        if (lista != null) {
            for (Socket obs : lista) {
                try {
                    PrintWriter out = new PrintWriter(obs.getOutputStream());
                    out.println("ALERT " + id + " " + ts + " " + valor);
                } catch (IOException ignorar) {}
            }
        }
    }
}
