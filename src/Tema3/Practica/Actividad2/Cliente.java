package Tema3.Practica.Actividad2;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class Cliente implements Runnable {
    private int idCliente;

    public Cliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    @Override
    public void run() {
        Scanner scan = new Scanner(System.in);
        try (Socket cliente = new Socket()) {
            InetSocketAddress add = new InetSocketAddress("localhost", 5555);
            cliente.connect(add);
            System.out.println("Cliente " + idCliente + " conectado al servidor.");

            DataOutputStream dos = new DataOutputStream(cliente.getOutputStream());
            DataInputStream dis = new DataInputStream(cliente.getInputStream());

            // Enviar ID
            dos.writeInt(idCliente);

            // Mensaje inicial del servidor
            System.out.println(dis.readUTF());

            // Enviar título del libro
            String titulo = scan.nextLine();
            dos.writeUTF(titulo);

            // Respuesta del servidor
            String respuesta = dis.readUTF();
            System.out.println(respuesta);

            if (respuesta.contains("encontrado")) {
                // Recibir libro como campos separados
                String tituloLibro = dis.readUTF();
                String generoLibro = dis.readUTF();
                Biblioteca.Libro libro = new Biblioteca.Libro();
                libro.setTitulo(tituloLibro);
                libro.setGenero(generoLibro);

                System.out.println("Qué libro tan interesante, me lo voy a leer:");
                System.out.println(libro);

                int tiempo = dis.readInt();
                Thread.sleep(tiempo);

                // Decidir si devolver el libro o no
                if (Math.random() > 0.05) {
                    dos.writeUTF(libro.getTitulo());
                    dos.writeUTF(libro.getGenero());
                    System.out.println("He devuelto el libro correctamente.");
                } else {
                    dos.writeUTF("");
                    dos.writeUTF("");
                    System.out.println("Po me lo voy a quedá");
                }
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
    }
}

