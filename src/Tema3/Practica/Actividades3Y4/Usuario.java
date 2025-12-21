package Tema3.Practica.Actividades3Y4;

/**
 * Clase que modela un usuario del sistema para las actividades 3 y 4 de la práctica del tema 3.
 * @author Guillermo Martín Chippirraz
 * @version v3.5
 */
public class Usuario {
    private String nombre;
    private String password;
    private boolean admin;

    /**
     * Constructor por parámetros.
     * @since v3.5
     * @param nombre Nombre del usuario.
     * @param password Contraseña del usuario.
     * @param admin Booleano que indica si el usuario posee privilegios administrativos.
     */
    public Usuario(String nombre, String password, boolean admin){
        this.nombre = nombre;
        this.password = password;
        this.admin = admin;
    }

    /**
     * Getter del atributo nombre.
     * @since v3.5
     * @return String almacenado en el atributo nombre.
     */
    public String getNombre(){ return nombre; }

    /**
     * Getter del atributo password.
     * @since v3.5
     * @return String almacenado en el atributo password.
     */
    public String getPassword(){ return password; }

    /**
     * Getter del atributo admin.
     * @since v3.5
     * @return true si el usuario es administrador, false en caso contrario.
     */
    public boolean isAdmin(){ return admin; }
}
