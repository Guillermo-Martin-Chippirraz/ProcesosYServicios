package Tema3.Actividades.Actividad5;

import java.io.*;
import java.net.*;
import java.util.Random;

public class Servidor {
    public static void main(String[] args) {
        final int PUERTO = 5555;
        Random random = new Random();

        try (ServerSocket serverSocket = new ServerSocket(PUERTO)) {
            System.out.println("Servidor iniciado en el puerto " + PUERTO);
            Socket socket = serverSocket.accept();
            System.out.println("Cliente conectado.");

            int numeroSecreto = random.nextInt(100) + 1;
            System.out.println("Número secreto: " + numeroSecreto);

            DataInputStream dis = new DataInputStream(socket.getInputStream());
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

            dos.writeUTF("¡Bienvenido! Adivina el número entre 1 y 100. Escribe -1 para salir.");

            int intento;
            do {
                intento = dis.readInt();
                if (intento == -1) {
                    dos.writeUTF("Juego terminado por el usuario.");
                    break;
                } else if (intento < numeroSecreto) {
                    dos.writeUTF("Mayor");
                } else if (intento > numeroSecreto) {
                    dos.writeUTF("Menor");
                } else {
                    dos.writeUTF("Correcto");
                }
            } while (intento != numeroSecreto);

            socket.close();
            System.out.println("Conexión cerrada.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
