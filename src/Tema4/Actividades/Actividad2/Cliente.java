package Tema4.Actividades.Actividad2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class Cliente implements Runnable{
    private String host;
    private int puerto;

    public Cliente(String host, int puerto) {
        this.host = host;
        this.puerto = puerto;
    }

    public void run() {
        try (Socket socket = new Socket()){
            InetSocketAddress addr = new InetSocketAddress(host, puerto);
            socket.connect(addr);
            PrintWriter salida = new PrintWriter(socket.getOutputStream());
            BufferedReader entrada = new BufferedReader(
                    new InputStreamReader(socket.getInputStream())
            );

            Scanner sc = new Scanner(System.in);
            boolean continuar = true;
            while (continuar) {
                System.out.println("Operaciones: SUMA RESTA PRODUCTO DIVISION MODULO POTENCIA RAIZ LOGARITMO");
                System.out.println("Introduce operación o X para salir");
                String op = sc.nextLine();

                if (op.equalsIgnoreCase("X")) {
                    System.out.println("Servidor: " + entrada.readLine());
                    continuar = false;
                } else {
                    String respuesta = entrada.readLine();

                    if (respuesta.equals("ERROR")) {
                        System.out.println("Operación no válida");
                        continuar = false;
                    }else {
                        System.out.println("Primer número: ");
                        double a = sc.nextDouble();

                        System.out.println("Segundo número: ");
                        double b = sc.nextDouble();


                        salida.println(a);
                        salida.println(b);

                        String resultado = entrada.readLine();
                        System.out.println("Resultado: " + resultado);
                    }
                }
            }
            
            entrada.close();
            salida.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
