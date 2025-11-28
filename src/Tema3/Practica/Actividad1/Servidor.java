package Tema3.Practica.Actividad1;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

/**
 * Clase para el servidor de la Actividad 1.
 * @author Guillermo Martín Chippirraz
 * @version v3.1
 */
public class Servidor {
    private static int COUNTER = 0;
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket();
            InetSocketAddress addr = new InetSocketAddress("localhost", 5555);
            serverSocket.bind(addr);
            ArrayList<Socket> clientesConectados = new ArrayList<>();
            Socket newSocket = serverSocket.accept();
            clientesConectados.add(newSocket);
            COUNTER++;
            InputStream is = newSocket.getInputStream();
            OutputStream os = newSocket.getOutputStream();
            byte[] mensaje = new byte[18];
            is.read(mensaje);
            os.write(String.valueOf(COUNTER).getBytes());
            System.out.println(new String(mensaje, StandardCharsets.UTF_8));
            newSocket.close();
            serverSocket.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
