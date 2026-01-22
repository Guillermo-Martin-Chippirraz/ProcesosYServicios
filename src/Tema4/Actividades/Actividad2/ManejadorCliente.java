package Tema4.Actividades.Actividad2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ManejadorCliente implements Runnable{
    private Socket socket;
    private Calculadora calculadora;

    public ManejadorCliente(Socket socket, Calculadora calculadora) {
        this.socket = socket;
        this.calculadora = calculadora;
    }

    public void run() {
        try {
            BufferedReader entrada = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            PrintWriter salida = new PrintWriter(socket.getOutputStream());
            boolean continuar = true;
            while (continuar) {
                String operacion = entrada.readLine();

                if (operacion == null) continuar = false;
                else {
                    operacion = operacion.toUpperCase();

                    if (operacion.equals("X")){
                        salida.println("BYE");
                        continuar = false;
                    } else {
                        if (!esOperacionValida(operacion)) {
                            salida.println("ERROR");
                            continuar = false;
                        } else {
                            salida.println("OK");

                            double a = Double.parseDouble(entrada.readLine());
                            double b = Double.parseDouble(entrada.readLine());

                            String resultado = ejecutarOperacion(operacion, a, b);
                            salida.println(resultado);
                        }
                    }

                }
            }
            salida.close();
            entrada.close();
            socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean esOperacionValida(String op) {
        return switch (op) {
            case "SUMA", "RESTA", "PRODUCTO", "DIVISION",
                 "MODULO", "POTENCIA", "RAIZ", "LOGARITMO" -> true;
            default -> false;
        };
    }
    private String ejecutarOperacion(String op, double a, double b) {

            return switch (op) {
                case "SUMA" -> String.valueOf(calculadora.suma(a, b));
                case "RESTA" -> String.valueOf(calculadora.diferencia(a, b));
                case "PRODUCTO" -> String.valueOf(calculadora.producto(a, b));
                case "DIVISION" -> {
                    Double r = calculadora.division(a, b);
                    yield (r == null) ? "ERROR: División no válida" : r.toString();
                }
                case "MODULO" -> String.valueOf(calculadora.modulo(a, b));
                case "POTENCIA" -> String.valueOf(calculadora.potencia(a, b));
                case "RAIZ" -> String.valueOf(calculadora.raiz(a, b));
                case "LOGARITMO" -> {
                    Double r = calculadora.logaritmo(a, b);
                    yield (r == null) ? "ERROR: Logaritmo no válido" : r.toString();
                }
                default -> "ERROR: Operación no soportada";
            };
    }
}
