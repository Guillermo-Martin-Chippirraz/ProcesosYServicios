package Tema3.Actividades;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Actividad3 {
    public static void main(String[] args){
        if (args.length <= 2)
            System.out.println("Uso: Actividad3 <mensaje> <host>");
        else {
            try {
                DatagramSocket datagramSocket = new DatagramSocket();
                String mensaje = args[0];
                InetAddress addr = InetAddress.getByName(args[1]);
                DatagramPacket datagrama =
                        new DatagramPacket(mensaje.getBytes(),
                                mensaje.getBytes().length,
                                addr, 5555);
                datagramSocket.send(datagrama);
                datagramSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
