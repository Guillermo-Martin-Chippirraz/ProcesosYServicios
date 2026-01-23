package Tema4.Practica.Actividad2.modelos;

import java.util.List;

public class UsuarioObservador {
    private String nombreUsuario;
    private String password;
    private List<String> sensoresSuscritos;

    public UsuarioObservador() {
        nombreUsuario = password = "";
        sensoresSuscritos = null;
    }

    public UsuarioObservador(String nombreUsuario, String password, List<String> sensoresSuscritos) {
        setNombreUsuario(nombreUsuario);
        setPassword(password);
        setSensoresSuscritos(sensoresSuscritos);
    }

    public UsuarioObservador(String usuario, String pass) {
        setNombreUsuario(usuario);
        setPassword(pass);
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<String> getSensoresSuscritos() {
        return sensoresSuscritos;
    }

    public void setSensoresSuscritos(List<String> sensoresSuscritos) {
        this.sensoresSuscritos = sensoresSuscritos;
    }
}
