package Tema4.Practica.Actividad2.sensores;

import Tema4.Practica.Actividad2.modelos.SensorInfo;
import Tema4.Practica.Actividad2.util.LoggerSistema;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Random;

public class SensorSimulado implements Runnable {

    private final SensorInfo info;
    private final String host;
    private final int puerto;
    private final Random random = new Random();

    public SensorSimulado(SensorInfo info, String host, int puerto) {
        this.info = info;
        this.host = host;
        this.puerto = puerto;
    }

    @Override
    public void run() {
        try {
            Socket socket = new Socket();
            socket.connect(new InetSocketAddress(host, puerto));

            PrintWriter salida = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader entrada = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));

            salida.println("SENSOR");

            salida.println("REGISTER " + info.getId() + " " + info.getTipo() + " " + info.getIntervaloMs());
            entrada.readLine();

            salida.println("CONFIG " + info.getId() + " " + info.getUmbralMin() + " " + info.getUmbralMax());
            entrada.readLine();

            boolean activo = true;
            while (activo) {
                double valor = random.nextDouble(100);
                salida.println("DATA " + info.getId() + " " + valor);
                Thread.sleep(info.getIntervaloMs());
            }

        } catch (Exception e) {
            LoggerSistema.warn("Sensor " + info.getId() + " desconectado.");
        }
    }
}
