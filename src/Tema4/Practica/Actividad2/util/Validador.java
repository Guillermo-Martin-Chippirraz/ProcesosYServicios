package Tema4.Practica.Actividad2.util;
public final class Validador {

    private Validador() {}

    public static boolean idSensorValido(String id) {
        return id != null && !id.isBlank();
    }

    public static boolean usuarioValido(String usuario) {
        return usuario != null && !usuario.isBlank();
    }

    public static boolean passwordValida(String password) {
        return password != null && password.length() >= 3;
    }

    public static boolean esNumero(String s) {
        boolean valido = false;
        try {
            Double.parseDouble(s);
            valido = true;
        } catch (Exception e) {
            valido = false;
        }
        return valido;
    }
}
