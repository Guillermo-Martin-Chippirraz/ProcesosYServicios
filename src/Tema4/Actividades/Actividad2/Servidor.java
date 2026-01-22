package Tema4.Actividades.Actividad2;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicReference;

public class Servidor implements Runnable{

    private int puerto;

    public Servidor(int puerto) {
        this.puerto = puerto;
    }

    public void run() {
        Calculadora calc = new Calculadora();

        try(ServerSocket servidor = new ServerSocket()) {
            InetSocketAddress addr = new InetSocketAddress("localhost", puerto);
            servidor.bind(addr);
            System.out.println("Servidor escuchando en puerto " + puerto);

            while (true) {
                Socket socket = servidor.accept();
                System.out.println("Cliente conectado: " + socket.getInetAddress());

                ManejadorCliente manejador = new ManejadorCliente(socket, calc);
                new Thread(manejador).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
