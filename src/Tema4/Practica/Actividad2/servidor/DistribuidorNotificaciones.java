package Tema4.Practica.Actividad2.servidor;

import Tema4.Practica.Actividad2.modelos.Alerta;
import Tema4.Practica.Actividad2.modelos.LecturaSensor;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;
import java.util.Map;

public class DistribuidorNotificaciones {

    private final Map<String, List<Socket>> suscriptores;

    public DistribuidorNotificaciones(Map<String, List<Socket>> suscriptores) {
        this.suscriptores = suscriptores;
    }

    public void enviarLectura(LecturaSensor lectura) {
        List<Socket> lista = suscriptores.get(lectura.getIdSensor());
        if (lista != null) {
            String mensaje = "NOTIFY " +
                    lectura.getIdSensor() + " " +
                    lectura.getTimestamp() + " " +
                    lectura.getValor();

            enviarAObservadores(lista, mensaje);
        }
    }

    public void enviarAlerta(Alerta alerta) {
        List<Socket> lista = suscriptores.get(alerta.getIdSensor());
        if (lista != null) {

            String mensaje = "ALERT " +
                    alerta.getIdSensor() + " " +
                    alerta.getTimestamp() + " " +
                    alerta.getValor() + " " +
                    alerta.getDescripcion();

            enviarAObservadores(lista, mensaje);
        }
    }

    private void enviarAObservadores(List<Socket> lista, String mensaje) {
        lista.removeIf(socket -> {
            try {
                PrintWriter out = new PrintWriter(socket.getOutputStream());
                out.println(mensaje);
                return false;
            } catch (IOException e) {
                return true;
            }
        });
    }
}
