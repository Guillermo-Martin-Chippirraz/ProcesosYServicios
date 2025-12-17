package Tema3.Practica.Actividades3Y4;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Cliente implements Runnable{
    private String id;
    private boolean admin;
    private static final int PUERTO = 5555;
    private static final String HOSTNAME = "localhost";

    public Cliente(String id, boolean admin){
        this.id = id;
        this.admin = admin;
    }

    public boolean isAdmin(){
        return admin;
    }

    public String getId(){
        return id;
    }

    public void run(){
        Scanner scan = new Scanner(System.in);
        try(Socket client = new Socket(HOSTNAME, PUERTO)){
            DataInputStream dis = new DataInputStream(client.getInputStream());
            DataOutputStream dos = new DataOutputStream(client.getOutputStream());

            dos.writeUTF(id);
            System.out.println(dis.readUTF());

            String authRespuesta;
            boolean salir = false;
            while (!salir){
                dos.writeUTF(scan.nextLine());
                authRespuesta = dis.readUTF();
                System.out.println(authRespuesta);

                if (authRespuesta.startsWith("Bienvenido")) {
                    salir = true; // autenticación correcta
                } else if (authRespuesta.startsWith("Demasiados")) {
                    client.close();
                    salir = true; // fuerza salida
                }
            }

            System.out.println(dis.readUTF());
            dos.writeUTF(scan.nextLine());
            System.out.println(dis.readUTF());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
