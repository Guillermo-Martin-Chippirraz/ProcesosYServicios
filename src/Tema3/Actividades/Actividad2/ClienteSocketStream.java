package Tema3.Actividades.Actividad2;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

public class ClienteSocketStream {
    public static void main(String[] args) {
        if (args[0].isEmpty())
            System.out.println("Uso: ClienteSocketStream <mensaje>");
        else {
            try {
                Socket clientSocket = new Socket();
                InetSocketAddress addr = new InetSocketAddress("localhost", 5555);
                clientSocket.connect(addr);
                OutputStream os = clientSocket.getOutputStream();
                String mensaje = args[0];
                os.write(mensaje.getBytes());
                clientSocket.close();
            }catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
