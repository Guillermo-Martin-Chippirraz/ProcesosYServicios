package Tema3.Practica.Actividad3;

import java.util.ArrayList;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        final Random random = new Random();
        ArrayList<CuentaBancaria> cuentas = new ArrayList<>();
        for (int i = 0; i < 3; i++){
            float saldo = random.nextFloat();
            String entero = String.valueOf(saldo).split("\\.")[0];
            String decimal = String.valueOf(saldo).split("\\.")[1];
            saldo = Float.parseFloat(entero + decimal.substring(0, 1) + "F");
            cuentas.add(new CuentaBancaria("Cuenta " + i + 1, saldo));
        }

        Gestor gestor = new Gestor(cuentas);
        Servidor servidor = new Servidor(gestor);
        Thread servidorThread = new Thread(servidor);
        servidorThread.start();
        for (int i = 0; i < 3; i++){
            new Thread(new Cliente("Cliente " + i + 1, servidor)).start();
        }
    }
}
