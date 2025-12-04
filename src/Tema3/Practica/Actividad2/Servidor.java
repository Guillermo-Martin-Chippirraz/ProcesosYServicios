package Tema3.Practica.Actividad2;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;

public class Servidor implements Runnable{
    private Biblioteca biblioteca;
    private final Random random = new Random();
    private ArrayList<Cliente> clientes;

    public Servidor(){
        biblioteca = new Biblioteca();
    }

    public Servidor(Biblioteca biblioteca){
        this.biblioteca = biblioteca;
    }

    public ArrayList<Cliente> getClientes(){
        return clientes;
    }

    public void run(){
        int idCliente;
        try(ServerSocket socketServidor = new ServerSocket()){
            InetSocketAddress add = new InetSocketAddress("localhost", 5555);
            socketServidor.bind(add);
            System.out.println("Servidor conectado");
            while (!clientes.isEmpty()){
                Socket cliente = socketServidor.accept();
                DataOutputStream dos = new DataOutputStream(cliente.getOutputStream());
                DataInputStream dis = new DataInputStream(cliente.getInputStream());
                idCliente = dis.readInt();
                System.out.println("El cliente " + idCliente + " se ha conectado satisfactoriamente.");
                ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("peticionC" + idCliente + ".pet"));
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream("devolucionC" + idCliente + ".dev"));
                dos.writeUTF("Seleccione el libro que desee solicitar");
                String titulo = dis.readUTF();
                Biblioteca.Libro libro = biblioteca.prestarLibro(titulo);
                if (libro.getGenero().isEmpty() || libro.getTitulo().isEmpty()){
                    dos.writeUTF("El libro no está disponible. Hasta siempre papanatas.");
                    cliente.close();
                    clientes.remove(0);
                }else {
                    try {
                        dos.writeUTF("El libro ha sido encontrado.");
                        oos.writeObject(libro);
                        System.out.println("El libro " + libro.getTitulo() + " ha sido enviado con éxito.");
                        int tiempo = random.nextInt(15000) + 10000;
                        dos.writeInt(tiempo);
                        Thread.sleep(tiempo);
                        Biblioteca.Libro libroDevuelto = (Biblioteca.Libro) ois.readObject();
                        if (libroDevuelto.getTitulo().isEmpty() || libroDevuelto.getGenero().isEmpty()){
                            System.out.println("El mu joputa no ha devuelto el libro.");
                        }else {
                            biblioteca.devolucionLibro(libroDevuelto);
                            System.out.println("El libro ha sido devuelto satisfactoriamente.");
                        }
                    }catch (IOException | ClassNotFoundException | InterruptedException e){
                        e.printStackTrace();
                        Thread.currentThread().interrupt();
                    }
                }
                cliente.close();
                clientes.remove(0);
            }
            System.out.println("Servicio finalizado.");
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
