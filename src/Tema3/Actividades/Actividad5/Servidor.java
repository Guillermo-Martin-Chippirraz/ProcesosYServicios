package Tema3.Actividades.Actividad5;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

public class Servidor {
    private static final Random random = new Random();
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket();
            InetSocketAddress addr = new InetSocketAddress("localhost", 5555);
            serverSocket.bind(addr);
            Socket newSocket = serverSocket.accept();
            InputStream is = newSocket.getInputStream();
            DataInputStream dis = new DataInputStream(is);
            OutputStream os = newSocket.getOutputStream();
            DataOutputStream dos = new DataOutputStream(os);
            int numero = random.nextInt(100) + 1;
            String pregunta = "Te desafío. ¿Qué número estoy pensando del 1 al 100?";
            dos.writeUTF(pregunta);
            int respuesta = dis.readInt();
            while (respuesta != numero || respuesta == -1){
                String malaRespuesta = "Me temo que ese no es el número, vuelve a intentarlo...";
                dos.writeUTF(malaRespuesta);
            }
            if (respuesta == numero){
                String buenaRespuesta = "Felicidades, ha acertado el número *aplausos lentos y sonoros*";
                dos.writeUTF(buenaRespuesta);
            }else {
                dos.writeUTF("Jajajaja, una desgracia, me temo que el número correcto es: ");
                dos.writeInt(numero);
                dos.writeUTF("Adiós, pringado.");
            }
            serverSocket.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
