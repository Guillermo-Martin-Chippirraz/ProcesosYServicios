package Tema4.Practica.Actividad2.modelos;


public class UsuarioObservador {

    private final String usuario;
    private final String password;

    public UsuarioObservador(String usuario, String password) {
        this.usuario = usuario;
        this.password = password;
    }

    public String getUsuario() { return usuario; }
    public String getPassword() { return password; }
}

