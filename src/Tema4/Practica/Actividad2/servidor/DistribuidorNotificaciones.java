package Tema4.Practica.Actividad2.servidor;

import Tema4.Practica.Actividad2.modelos.Alerta;
import Tema4.Practica.Actividad2.modelos.LecturaSensor;
import Tema4.Practica.Actividad2.protocolo.MensajeProtocolo;

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
        if (lista == null) {
            return;
        }
        String mensaje = MensajeProtocolo.notificacionLectura(
                lectura.getIdSensor(),
                lectura.getTimestamp(),
                lectura.getValor()
        );
        enviarA(lista, mensaje);
    }

    public void enviarAlerta(Alerta alerta) {
        List<Socket> lista = suscriptores.get(alerta.getIdSensor());
        if (lista == null) {
            return;
        }
        String mensaje = MensajeProtocolo.notificacionAlerta(
                alerta.getIdSensor(),
                alerta.getTimestamp(),
                alerta.getValor(),
                alerta.getDescripcion()
        );
        enviarA(lista, mensaje);
    }

    private void enviarA(List<Socket> lista, String mensaje) {
        int i = 0;
        while (i < lista.size()) {
            Socket socket = lista.get(i);
            boolean eliminar = false;
            try {
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                out.println(mensaje);
            } catch (IOException e) {
                eliminar = true;
            }
            if (eliminar) {
                lista.remove(i);
            } else {
                i = i + 1;
            }
        }
    }
}
