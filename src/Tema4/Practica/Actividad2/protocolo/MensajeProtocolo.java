package Tema4.Practica.Actividad2.protocolo;

import java.time.LocalDateTime;

public final class MensajeProtocolo {

    private MensajeProtocolo() {}

    public static String notificacionLectura(String id, LocalDateTime ts, double valor) {
        return TiposMensaje.NOTIFY + " " + id + " " + ts + " " + valor;
    }

    public static String notificacionAlerta(String id, LocalDateTime ts, double valor, String descripcion) {
        return TiposMensaje.ALERT + " " + id + " " + ts + " " + valor + " " + descripcion;
    }

    public static String[] parsear(String mensaje) {
        return mensaje.split(" ");
    }
}
