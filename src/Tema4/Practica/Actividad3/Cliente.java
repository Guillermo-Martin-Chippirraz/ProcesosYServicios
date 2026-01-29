package Tema4.Practica.Actividad3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Cliente implements Runnable {
    private final String host;
    private final int puerto;

    public Cliente(String host, int puerto) {
        this.host = host;
        this.puerto = puerto;
    }

    @Override
    public void run() {
        try (Socket socket = new Socket(host, puerto)){
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            Scanner sc = new Scanner(System.in);

            while (true) {
                System.out.println(in.readLine());
                String pais = sc.nextLine();
                out.println(pais);

                String resp = in.readLine();
                System.out.println(resp);

                if (resp.equals("OK")) {
                    System.out.println(in.readLine());
                    boolean consultando = true;
                    while (consultando) {
                        String cmd = sc.nextLine();
                        out.println(cmd);

                        String r = in.readLine();
                        System.out.println(r);

                        if (cmd.equalsIgnoreCase("QUIT"))
                            consultando = false;
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Cliente desconectado");
        }
    }
}
