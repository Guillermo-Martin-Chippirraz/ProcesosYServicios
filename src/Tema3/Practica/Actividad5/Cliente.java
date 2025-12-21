package Tema3.Practica.Actividad5;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

/**
 * Clase que modela un cliente UDP para la Actividad 5 de la práctica del tema 3.
 *
 * @author Guillermo
 * @version v3.5
 */
public class Cliente {
    private String alias;
    private InetAddress servidor;
    private int puerto;
    private DatagramSocket socket;

    /**
     * Constructor por defecto.
     * @since v3.5
     */
    public Cliente(){
        alias = "";
        servidor = null;
        puerto = 0;
        socket = null;
    }

    /**
     * Constructor por parámetros.
     * Inicializa el alias, la dirección del servidor, el puerto y el socket UDP.
     *
     * <ol>
     *     <li>Se asigna el alias recibido al atributo alias.</li>
     *     <li>Se obtiene la dirección IP del servidor mediante InetAddress.getByName().</li>
     *     <li>Se asigna el puerto recibido al atributo puerto.</li>
     *     <li>Se inicializa el DatagramSocket del cliente.</li>
     *     <li>En caso de excepción, se imprime la traza del error.</li>
     * </ol>
     * @since v3.5
     * @param alias Nombre del usuario.
     * @param host Dirección del servidor.
     * @param puerto Puerto del servidor.
     */
    public Cliente(String alias, String host, int puerto){
        try {
            this.alias = alias;
            this.servidor = InetAddress.getByName(host);
            this.puerto = puerto;
            this.socket = new DatagramSocket();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Método iniciar.
     * Gestiona la lógica principal del cliente: unirse al chat, recibir mensajes y
     * enviar mensajes al servidor.
     *
     * La línea de ejecución es la siguiente:
     * <ol>
     *     <li>Se envía al servidor el mensaje JOIN:alias mediante el método enviar().</li>
     *     <li>
     *         Se declara un hilo receptor que:
     *         <ol>
     *             <li>Declara un buffer de 1024 bytes.</li>
     *             <li>Abre un bucle while infinito.</li>
     *             <li>Recibe datagramas mediante socket.receive().</li>
     *             <li>Convierte los datos recibidos a String.</li>
     *             <li>Imprime el mensaje por consola.</li>
     *         </ol>
     *     </li>
     *     <li>Se inicia el hilo receptor mediante start().</li>
     *     <li>
     *         Se declara un Scanner para leer la entrada del usuario.
     *     </li>
     *     <li>
     *         Se abre un bucle while que finaliza cuando el usuario escribe "/salir".
     *         <ol>
     *             <li>Si el usuario escribe "/salir", se envía LEAVE:alias y se cierra el socket.</li>
     *             <li>En caso contrario, se envía alias + ": " + mensaje.</li>
     *         </ol>
     *     </li>
     * </ol>
     *
     * @since v3.5
     */
    public void iniciar() {
        enviar("JOIN:" + alias);

        Thread receptor = new Thread(() -> {
            byte[] buffer = new byte[1024];
            try {
                while (true) {
                    DatagramPacket paquete = new DatagramPacket(buffer, buffer.length);
                    socket.receive(paquete);
                    String mensaje = new String(paquete.getData(), 0, paquete.getLength());
                    System.out.println(mensaje);
                }
            } catch (IOException e) {
                System.out.println("Receptor cerrado.");
            }
        });
        receptor.start();

        Scanner scan = new Scanner(System.in);
        boolean salir = false;
        while (!salir) {
            String texto = scan.nextLine();
            if (texto.equalsIgnoreCase("/salir")) {
                enviar("LEAVE:" + alias);
                socket.close();
                salir = true;
            } else {
                enviar(alias + ": " + texto);
            }
        }
    }

    /**
     * Método enviar.
     * Envía un mensaje al servidor mediante un datagrama UDP.
     *
     * <ol>
     *     <li>Se convierte el mensaje a un array de bytes.</li>
     *     <li>
     *         Se declara un DatagramPacket paquete incluyendo:
     *         <ol>
     *             <li>El array de bytes.</li>
     *             <li>La longitud del mensaje.</li>
     *             <li>La dirección del servidor.</li>
     *             <li>El puerto del servidor.</li>
     *         </ol>
     *     </li>
     *     <li>Se envía el paquete mediante socket.send().</li>
     *     <li>En caso de excepción, se imprime la traza del error.</li>
     * </ol>
     * @since v3.5
     * @param mensaje Mensaje a enviar al servidor.
     */
    public void enviar(String mensaje) {
        byte[] datos = mensaje.getBytes();
        try {
            DatagramPacket paquete = new DatagramPacket(datos, datos.length, servidor, puerto);
            socket.send(paquete);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Método main.
     * Solicita al usuario su alias y la dirección del servidor, crea un objeto Cliente
     * y llama al método iniciar().
     * @since v3.5
     * @param args Argumentos de línea de comandos.
     */
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Alias: ");
        String alias = scan.nextLine();
        System.out.println("Servidor (host): ");
        String host = scan.nextLine();
        Cliente cliente = new Cliente(alias, host, 5555);
        cliente.iniciar();
    }
}
