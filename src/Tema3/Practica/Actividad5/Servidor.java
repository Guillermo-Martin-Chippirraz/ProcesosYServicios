package Tema3.Practica.Actividad5;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.HashMap;
import java.util.Map;

public class Servidor {
    public static final int PUERTO = 5555;
    private DatagramSocket socket;
    private Map<String, SocketAddress> clientes;

    public Servidor(){
        try{
            socket = new DatagramSocket(PUERTO);
            clientes = new HashMap<>();
            System.out.println("Servidor UDP escuchando en puerto " + PUERTO);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void iniciar(){
        byte[] buffer = new byte[1024];
        try {
            while (true) {
                DatagramPacket paquete = new DatagramPacket(buffer, buffer.length);
                socket.receive(paquete);
                String mensaje = new String(paquete.getData(), 0, paquete.getLength());

                SocketAddress direccion = paquete.getSocketAddress();

                if (mensaje.startsWith("JOIN:")) {
                    String alias = mensaje.substring(5);
                    clientes.put(alias, direccion);
                    enviarATodos(">> " + alias + " se ha unido al chat.");
                    enviarMensaje("Bienvenido al chat, " + alias, direccion);
                } else if (mensaje.startsWith("LEAVE:")) {
                    String alias = mensaje.substring(6);
                    clientes.remove(alias);
                    enviarATodos(">> " + alias + " ha salido del chat.");
                } else {
                    enviarATodos(mensaje);
                }
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void enviarATodos(String mensaje){
        for (SocketAddress direccion : clientes.values()){
            enviarMensaje(mensaje, direccion);
        }
    }

    public void enviarMensaje(String mensaje, SocketAddress direccion){
        try {
            byte[] datos = mensaje.getBytes();
            DatagramPacket paquete = new DatagramPacket(datos, datos.length);
            socket.send(paquete);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Servidor().iniciar();
    }
}
