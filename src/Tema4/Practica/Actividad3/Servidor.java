package Tema4.Practica.Actividad3;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor implements Runnable{
    private final int puerto;

    public Servidor(int puerto) {
        this.puerto = puerto;
    }

    @Override
    public void run() {
        try (ServerSocket servidor = new ServerSocket(puerto)) {
            System.out.println("Servidor en puerto " + puerto);

            while (true) {
                Socket cliente = servidor.accept();
                new Thread(new ManejadorCliente(cliente)).start();
            }
        } catch (IOException e) {
            System.out.println("Error en servidor");
        }
    }
}
