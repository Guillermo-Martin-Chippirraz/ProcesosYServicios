package Tema4.Practica.Actividad3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ManejadorCliente implements Runnable{
    private final Socket socket;

    public ManejadorCliente(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            boolean activo = true;

            while (activo) {
                out.println("Introduce país: ");
                String linea = in.readLine();
                if (linea != null){
                    String pais = linea.trim();
                    CountryData data = RestCountriesAPI.buscarPais(pais);

                    if (data != null) {
                        out.println("OK");
                        out.println("OPCIONES: CODIGO, LENGUAJE, MONEDA, CAPITAL, TELEFONO, QUIT");

                        boolean consultado = true;
                        while (consultado) {
                            String cmd = in.readLine();
                            if (cmd != null) {
                                switch (cmd.toUpperCase()) {
                                    case "CODIGO" -> out.println(data.codigo());
                                    case  "LENGUAJE" -> out.println(data.lenguaje());
                                    case "MONEDA" -> out.println(data.moneda());
                                    case "CAPITAL" -> out.println(data.capital());
                                    case "TELEFONO" -> out.println(data.telefono());
                                    case "QUIT" -> {
                                        out.println("ADIOS");
                                        consultado = false;
                                        activo = false;
                                    }
                                    default -> out.println("ERROR OPCION NO VALIDA.");
                                }
                            } else {
                                consultado = false;
                            }
                        }
                    } else {
                        out.println("ERROR PAIS_NO_ENCONTRADO");
                        activo = false;
                    }
                }
            }
            socket.close();
        } catch (IOException e) {
            System.out.println("Cliente desconectado");
        }
    }
}
