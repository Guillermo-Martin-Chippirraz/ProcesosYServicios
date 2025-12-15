package Tema3.Practica.Actividad3;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class Cliente implements Runnable{
    private Servidor servidor;
    private String id;
    private static final int PUERTO = 5555;
    private static final String HOSTNAME = "localhost/";

    public Cliente(){
        id = "";
        servidor = new Servidor();
    }

    public Cliente(String id, Servidor servidor){
        this.id = id;
        this.servidor = servidor;
    }

    public Servidor getServidor(){
        return servidor;
    }

    public void run(){
        Scanner scan = new Scanner(System.in);
        try(Socket client = new Socket()){
            InetSocketAddress add = new InetSocketAddress(HOSTNAME, PUERTO);
            client.connect(add);
            DataInputStream dis = new DataInputStream(client.getInputStream());
            DataOutputStream dos = new DataOutputStream(client.getOutputStream());
            dos.writeUTF(id);
            System.out.println(dis.readUTF());
            String authRespuesta;
            do{
                dos.writeUTF(scan.nextLine());
                authRespuesta = dis.readUTF();
                System.out.println(authRespuesta);
                if (authRespuesta.startsWith("Demasiados")) client.close();
            }while (authRespuesta.startsWith("Bienvenido"));
            System.out.println(dis.readUTF());
            dos.writeUTF(scan.nextLine());
            String comandoRespuesta = dis.readUTF();
            System.out.println(comandoRespuesta);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
