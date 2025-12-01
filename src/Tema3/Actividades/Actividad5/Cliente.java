package Tema3.Actividades.Actividad5;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Cliente {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        try {
            Socket clientSocket = new Socket();
            InetSocketAddress addr = new InetSocketAddress("localhost", 5555);
            clientSocket.connect(addr);
            InputStream is = clientSocket.getInputStream();
            DataInputStream dis = new DataInputStream(is);
            OutputStream os = clientSocket.getOutputStream();
            DataOutputStream dos = new DataOutputStream(os);
            String pregunta = dis.readUTF();
            System.out.println(pregunta);
            String respuesta;
            int numeroCliente = scan.nextInt();
            dos.writeInt(numeroCliente);
            respuesta = dis.readUTF();
            while (respuesta.endsWith("...") || numeroCliente != -1){
                numeroCliente = scan.nextInt();
                dos.writeInt(numeroCliente);
                respuesta = dis.readUTF();
            }
            if (respuesta.endsWith("*")){
                System.out.println(dis.readUTF());
            }else {
                System.out.println(dis.readUTF());
                System.out.println(dis.readInt());
                System.out.println(dis.readUTF());
            }
            clientSocket.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
