package Tema3.Actividades.Actividad5;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Cliente {
    public static void main(String[] args) {
        final String HOST = "localhost";
        final int PUERTO = 5555;
        Scanner scanner = new Scanner(System.in);

        try (Socket socket = new Socket(HOST, PUERTO)) {
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

            System.out.println(dis.readUTF()); // Mensaje de bienvenida

            String respuesta;
            int intento;

            do {
                System.out.print("Tu intento: ");
                intento = scanner.nextInt();
                dos.writeInt(intento);

                respuesta = dis.readUTF();
                System.out.println("Servidor: " + respuesta);
            } while (!respuesta.equals("Correcto") && intento != -1);

            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
