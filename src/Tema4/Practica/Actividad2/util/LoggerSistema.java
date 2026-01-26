package Tema4.Practica.Actividad2.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class LoggerSistema {

    private static final DateTimeFormatter formato =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private LoggerSistema() {}

    public static synchronized void info(String mensaje) {
        System.out.println("[INFO " + timestamp() + "] " + mensaje);
    }

    public static synchronized void warn(String mensaje) {
        System.out.println("[WARN " + timestamp() + "] " + mensaje);
    }

    public static synchronized void error(String mensaje) {
        System.err.println("[ERROR " + timestamp() + "] " + mensaje);
    }

    private static String timestamp() {
        return LocalDateTime.now().format(formato);
    }
}
