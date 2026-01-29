package Tema4.Practica.Actividad2.modelos;

/**
 * Modelo UsuarioObservador para la Actividad 2 de la práctica del tema 4 que representa a un usuario del sistema
 * encargado de observar, supervisar o monitorizar las lecturas y alertas generadas por los sensores registrados.
 * Esta clase encapsula la información básica necesaria para identificar y autenticar a un observador dentro del
 * sistema, permitiendo su posterior suscripción a sensores y recepción de notificaciones.
 * Constituye un elemento fundamental en la gestión de usuarios que interactúan con el sistema desde el plano de
 * supervisión.
 *
 * @author Guillermo Martín Chippirraz
 * @version v4.2
 */
public class UsuarioObservador {

    private final String usuario;
    private final String password;

    /**
     * Constructor por parámetros.
     * Inicializa un nuevo observador proporcionando su nombre de usuario y contraseña, los cuales serán utilizados
     * para su identificación y autenticación dentro del sistema.
     *
     * @since v4.1.1
     * @param usuario String que identifica de forma única al observador dentro del sistema.
     * @param password String que representa la clave de acceso asociada al observador.
     */
    public UsuarioObservador(String usuario, String password) {
        this.usuario = usuario;
        this.password = password;
    }

    /**
     * Getter del atributo usuario.
     * Devuelve el nombre de usuario asociado al observador.
     *
     * @since v4.1.1
     * @return String almacenado en el atributo usuario.
     */
    public String getUsuario() { return usuario; }

    /**
     * Getter del atributo password.
     * Proporciona la contraseña registrada para el observador.
     *
     * @since v4.1.1
     * @return String almacenado en el atributo password.
     */
    public String getPassword() { return password; }
}
