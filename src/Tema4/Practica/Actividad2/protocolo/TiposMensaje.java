package Tema4.Practica.Actividad2.protocolo;

public final class TiposMensaje {

    public static final String SENSOR = "SENSOR";
    public static final String OBSERVADOR = "OBSERVADOR";

    public static final String REGISTER = "REGISTER";
    public static final String CONFIG = "CONFIG";
    public static final String DATA = "DATA";

    public static final String LOGIN = "LOGIN";
    public static final String SUBSCRIBE = "SUBSCRIBE";
    public static final String UNSUBSCRIBE = "UNSUBSCRIBE";
    public static final String QUIT = "QUIT";

    public static final String NOTIFY = "NOTIFY";
    public static final String ALERT = "ALERT";

    public static final String ERROR = "ERROR";

    private TiposMensaje() {}
}
