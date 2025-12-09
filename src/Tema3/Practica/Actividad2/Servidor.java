package Tema3.Practica.Actividad2;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;


public class Servidor implements Runnable {
    private Biblioteca biblioteca;
    private final Random random = new Random();
    private final ArrayList<Cliente> clientes;

    public Servidor() {
        this.biblioteca = new Biblioteca();
        this.clientes = new ArrayList<>();
    }

    public Servidor(Biblioteca biblioteca) {
        this.biblioteca = biblioteca;
        this.clientes = new ArrayList<>();
    }

    public ArrayList<Cliente> getClientes() {
        return clientes;
    }

    @Override
    public void run() {
        try (ServerSocket socketServidor = new ServerSocket()) {
            InetSocketAddress add = new InetSocketAddress("localhost", 5555);
            socketServidor.bind(add);
            System.out.println("Servidor conectado en " + add);

            while (true) {
                try (Socket cliente = socketServidor.accept()) {
                    DataOutputStream dos = new DataOutputStream(cliente.getOutputStream());
                    DataInputStream dis = new DataInputStream(cliente.getInputStream());

                    int idCliente = dis.readInt();
                    System.out.println("El cliente " + idCliente + " se ha conectado satisfactoriamente.");

                    dos.writeUTF("Seleccione el libro que desee solicitar");
                    String titulo = dis.readUTF();
                    Biblioteca.Libro libro = biblioteca.prestarLibro(titulo);

                    if (libro.getGenero().isEmpty() || libro.getTitulo().isEmpty()) {
                        dos.writeUTF("El libro no está disponible. Hasta siempre papanatas.");
                    } else {
                        dos.writeUTF("El libro ha sido encontrado.");
                        // Enviar libro como campos separados
                        dos.writeUTF(libro.getTitulo());
                        dos.writeUTF(libro.getGenero());

                        int tiempo = random.nextInt(15000) + 10000;
                        dos.writeInt(tiempo);

                        Thread.sleep(tiempo);

                        // Recibir devolución
                        String tituloDevuelto = dis.readUTF();
                        String generoDevuelto = dis.readUTF();
                        Biblioteca.Libro libroDevuelto = new Biblioteca.Libro();
                        libroDevuelto.setTitulo(tituloDevuelto);
                        libroDevuelto.setGenero(generoDevuelto);

                        if (libroDevuelto.getTitulo().isEmpty() || libroDevuelto.getGenero().isEmpty()) {
                            System.out.println("El cliente no ha devuelto el libro.");
                        } else {
                            biblioteca.devolucionLibro(libroDevuelto);
                            System.out.println("El libro ha sido devuelto satisfactoriamente.");
                        }
                    }
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                    Thread.currentThread().interrupt();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
