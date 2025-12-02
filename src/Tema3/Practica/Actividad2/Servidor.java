package Tema3.Practica.Actividad2;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

public class Servidor implements Runnable{
    private Biblioteca biblioteca;
    private final Random random;

    public Servidor(){
        biblioteca = new Biblioteca();
    }

    public Servidor(Biblioteca biblioteca){
        this.biblioteca = biblioteca;
    }

    public void run(){
        int idCliente;
        try(ServerSocket socketServidor = new ServerSocket()){
            InetSocketAddress add = new InetSocketAddress("localhost", 5555);
            socketServidor.bind(add);
            Socket cliente = socketServidor.accept();
            DataOutputStream dos = new DataOutputStream(cliente.getOutputStream());
            DataInputStream dis = new DataInputStream(cliente.getInputStream());
            idCliente = dis.readInt();
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("peticionC" + idCliente + ".pet"));
            ObjectInputStream iis = new ObjectInputStream(new FileInputStream("devolucionC" + idCliente + ".dev"));
            dos.writeUTF("Seleccione el libro que desee solicitar");
            String titulo = dis.readUTF();
            Biblioteca.Libro libro = biblioteca.prestarLibro(titulo);
            if (libro.getGenero().isEmpty() || libro.getTitulo().isEmpty()){
                dos.writeUTF("El libro no está disponible. Hasta siempre papanatas.");
                cliente.close();
            }else {
                try {
                    Thread.sleep();
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
