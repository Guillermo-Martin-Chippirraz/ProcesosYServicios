package Tema3.Practica.Actividad2;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class Cliente implements Runnable{
    private int idCliente;
    private Servidor servidor;

    public Cliente(){
        idCliente = -1;
        servidor = null;
    }
    public Cliente(int idCliente, Servidor servidor){
        this.idCliente = idCliente;
        this.servidor = servidor;
    }

    public void run(){
        Scanner scan = new Scanner(System.in);
        try(Socket cliente = new Socket()){
            InetSocketAddress add = new InetSocketAddress("localhost", 5555);
            cliente.connect(add);
            servidor.getClientes().add(this);
            System.out.println("Conexión realizada satisfactoriamente.");
            DataInputStream dis = new DataInputStream(cliente.getInputStream());
            DataOutputStream dos = new DataOutputStream(cliente.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream("peticionC" + idCliente + ".pet"));
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("devolucionC" + idCliente + ".dev"));
            dos.writeInt(idCliente);
            System.out.println(dis.readUTF());
            dos.writeUTF(scan.nextLine());
            String respuesta = dis.readUTF();
            if (respuesta.contains("encontrado")){
                Biblioteca.Libro libro = (Biblioteca.Libro) ois.readObject();
                System.out.println("Qué libro tan interesante, me lo voy a leer.");
                System.out.println(libro.toString());
                int tiempo = dis.readInt();
                try {
                    Thread.sleep(tiempo);
                }catch (InterruptedException e){
                    e.printStackTrace();
                    Thread.currentThread().interrupt();
                    servidor.getClientes().remove(this);
                }

                if (Math.random() > 0.05){
                    oos.writeObject(libro);
                }else {
                    oos.writeObject(new Biblioteca.Libro());
                    System.out.println("Po me lo voy a quedá");
                    cliente.close();
                }
            }else {
                System.out.println(dis.readUTF());
                cliente.close();
            }
        }catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }
}
