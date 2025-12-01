package Tema3.Actividades.Actividad2;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.ServerSocket;
import java.nio.charset.StandardCharsets;

public class ServidorSocketStream {
    public static void main(String[] args) {
        if (args[0].isEmpty()){
            System.out.println("Uso: ServidorSocketStream <puerto>");
        }else {
            int puerto = Integer.parseInt(args[0]);
            try {
                ServerSocket serverSocket = new ServerSocket();
                InetSocketAddress addr = new InetSocketAddress("localhost", puerto);
                serverSocket.bind(addr);
                Socket newSocket = serverSocket.accept();
                InputStream is = newSocket.getInputStream();
                byte[] mensaje = new byte[25];
                is.read(mensaje);
                String s = new String(mensaje, StandardCharsets.UTF_8);
                System.out.println(s);
                newSocket.close();
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}