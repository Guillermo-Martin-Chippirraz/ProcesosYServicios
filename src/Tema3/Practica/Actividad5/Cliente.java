package Tema3.Practica.Actividad5;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class Cliente {
    private String alias;
    private InetAddress servidor;
    private int puerto;
    private DatagramSocket socket;

    public Cliente(){
        alias = "";
        servidor = null;
        puerto = 0;
        socket = null;
    }

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

    public void iniciar() {
        enviar ("JOIN:" + alias);

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

    public void enviar(String mensaje) {
        byte[] datos = mensaje.getBytes();
        try {
            DatagramPacket paquete = new DatagramPacket(datos, datos.length);
            socket.send(paquete);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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
