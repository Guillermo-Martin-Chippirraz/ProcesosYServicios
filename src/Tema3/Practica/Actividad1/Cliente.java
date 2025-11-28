package Tema3.Practica.Actividad1;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

/**
 * Clase para el cliente de la Actividad 1.
 * @author Guillermo Martín Chippirraz
 * @version v3.1
 */
public class Cliente {
    public static void main(String[] args) {
        try {
            Socket cliente = new Socket();
            InetSocketAddress addr = new InetSocketAddress("localhost", 5555);
            cliente.connect(addr);
            InputStream is = cliente.getInputStream();
            OutputStream os = cliente.getOutputStream();
            String mensaje = "conexión correcta";
            if (cliente.isConnected())
                os.write(mensaje.getBytes());
            byte[] indice = new byte[1];
            is.read(indice);
            String connectionResult = new String(indice, StandardCharsets.UTF_8);
            System.out.println(connectionResult);
            cliente.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
