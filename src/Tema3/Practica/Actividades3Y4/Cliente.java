package Tema3.Practica.Actividades3Y4;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

/**
 * Clase que modela un cliente del sistema para las actividades 3 y 4 de la práctica del tema 3.
 *
 * @author Guillermo Martín Chippirraz
 * @version v3.5.2
 */
public class Cliente implements Runnable{
    private String id;
    private boolean admin;
    private static final int PUERTO = 5555;
    private static final String HOSTNAME = "localhost";

    /**
     * Constructor por parámetros.
     * @since v3.5
     * @param id Identificador del cliente.
     * @param admin Booleano que indica si el cliente posee privilegios administrativos.
     */
    public Cliente(String id, boolean admin){
        this.id = id;
        this.admin = admin;
    }

    /**
     * Getter del atributo admin.
     * @since v3.5
     * @return true si el cliente es administrador, false en caso contrario.
     */
    public boolean isAdmin(){
        return admin;
    }

    /**
     * Getter del atributo id.
     * @since v3.5
     * @return String almacenado en el atributo id.
     */
    public String getId(){
        return id;
    }

    /**
     * Método run implementado de la interfaz Runnable.
     * Contiene la lógica de comunicación entre el cliente y el servidor.
     *
     * La línea de ejecución es la siguiente:
     * <ol>
     *     <li>
     *         Se declara un objeto de la clase Scanner y se inicializa mediante su constructor por defecto,
     *         permitiendo la lectura de texto desde la entrada estándar.
     *     </li>
     *     <li>
     *         Se abre un bloque try-with-resources declarando un objeto Socket client, inicializado mediante
     *         el constructor por parámetros introduciendo el String HOSTNAME como valor del parámetro host
     *         y el entero PUERTO como valor del parámetro port.
     *     </li>
     *     <li>
     *         Dentro del bloque:
     *         <ol>
     *             <li>
     *                 Se declara un objeto DataInputStream dis, inicializado mediante su constructor por
     *                 parámetros introduciendo el InputStream devuelto por client.getInputStream().
     *             </li>
     *             <li>
     *                 Se declara un objeto DataOutputStream dos, inicializado mediante su constructor por
     *                 parámetros introduciendo el OutputStream devuelto por client.getOutputStream().
     *             </li>
     *             <li>
     *                 Se envía el primer mensaje al servidor mediante el método writeUTF() invocado por dos,
     *                 introduciendo el String almacenado en el atributo id como valor del parámetro str.
     *             </li>
     *             <li>
     *                 Se imprime por consola la primera respuesta del servidor, devuelta por el método readUTF()
     *                 invocado por dis.
     *             </li>
     *             <li>
     *                 Se declara un String authRespuesta y un boolean salir inicializado a false.
     *             </li>
     *             <li>
     *                 Se abre un bucle while cuya condición es la negación del boolean salir. En cada iteración:
     *                 <ol>
     *                     <li>
     *                         Se envía al servidor el texto introducido por el usuario mediante scan.nextLine(),
     *                         utilizando el método writeUTF() invocado por dos.
     *                     </li>
     *                     <li>
     *                         Se asigna a authRespuesta el valor devuelto por readUTF() invocado por dis.
     *                     </li>
     *                     <li>
     *                         Se imprime por consola el contenido de authRespuesta.
     *                     </li>
     *                     <li>
     *                         Se abre una estructura condicional:
     *                         <ol>
     *                             <li>
     *                                 Si authRespuesta comienza con la cadena "Bienvenido", se asigna true al
     *                                 boolean salir, indicando autenticación correcta.
     *                             </li>
     *                             <li>
     *                                 Si authRespuesta comienza con la cadena "Demasiados", se invoca el método
     *                                 close() sobre el objeto client y se asigna true al boolean salir, forzando
     *                                 la salida del bucle.
     *                             </li>
     *                         </ol>
     *                     </li>
     *                 </ol>
     *             </li>
     *             <li>
     *                 Tras salir del bucle de autenticación, se imprime por consola la siguiente respuesta del
     *                 servidor mediante readUTF() invocado por dis.
     *             </li>
     *             <li>
     *                 Se envía al servidor el texto introducido por el usuario mediante writeUTF() invocado por dos.
     *             </li>
     *             <li>
     *                 Se imprime por consola la última respuesta del servidor mediante readUTF() invocado por dis.
     *             </li>
     *         </ol>
     *     </li>
     *     <li>
     *         En el bloque catch se controla la excepción IOException. En caso de producirse:
     *         <ol>
     *             <li>Se invoca el método printStackTrace() sobre el objeto e.</li>
     *         </ol>
     *     </li>
     * </ol>
     *
     * @since v3.5
     */
    public void run(){
        Scanner scan = new Scanner(System.in);
        try(Socket client = new Socket(HOSTNAME, PUERTO)){
            DataInputStream dis = new DataInputStream(client.getInputStream());
            DataOutputStream dos = new DataOutputStream(client.getOutputStream());

            dos.writeUTF(id);
            System.out.println(dis.readUTF());

            String authRespuesta;
            boolean salir = false;
            while (!salir){
                dos.writeUTF(scan.nextLine());
                authRespuesta = dis.readUTF();
                System.out.println(authRespuesta);

                if (authRespuesta.startsWith("Bienvenido")) {
                    salir = true; // autenticación correcta
                } else if (authRespuesta.startsWith("Demasiados")) {
                    client.close();
                    salir = true; // fuerza salida
                }
            }

            System.out.println(dis.readUTF());
            dos.writeUTF(scan.nextLine());
            System.out.println(dis.readUTF());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
