package Tema3.Practica.Actividad3;

import java.io.*;
import java.util.ArrayList;

public class Gestor {
    private ArrayList<CuentaBancaria> cuentas;

    public Gestor(){
        cuentas = new ArrayList<>();
    }

    public Gestor(ArrayList<CuentaBancaria> cuentas){
        setCuentas(cuentas);
    }

    public ArrayList<CuentaBancaria> getCuentas() {
        return cuentas;
    }

    public void setCuentas(ArrayList<CuentaBancaria> cuentas) {
        this.cuentas = new ArrayList<>(cuentas);
    }


    public CuentaBancaria getByNum(String numeroDeCuenta){
        for (CuentaBancaria cuenta : cuentas){
            if (numeroDeCuenta.equals(cuenta.getNumeroDeCuenta()))
                return cuenta;
        }
        return new CuentaBancaria();
    }

    public synchronized float consultar(CuentaBancaria cuentaBancaria){
        if (cuentas.contains(cuentaBancaria))
            return cuentaBancaria.getSaldoActual();
        return -1f;
    }

    public synchronized String ingresar(CuentaBancaria cuentaBancaria, float cantidad){
        if (cuentas.contains(cuentaBancaria)){
            if (cantidad >= 0f && cantidad*1000 % 100 == 0) {
                cuentaBancaria.setSaldoActual(cuentaBancaria.getSaldoActual() + cantidad);
                return "Se han ingresado " + cantidad + " € en la cuenta " + cuentaBancaria.getNumeroDeCuenta();
            }
            return "Debe ingresar un número válido.";
        }
        return "La cuenta introducida no existe.";
    }

    public synchronized String retirar(CuentaBancaria cuentaBancaria, float cantidad){
        if (cuentas.contains(cuentaBancaria)){
            if (cantidad >= 0f && cantidad*1000 % 100 == 0 && cuentaBancaria.getSaldoActual() >= cantidad){
                cuentaBancaria.setSaldoActual(cuentaBancaria.getSaldoActual() - cantidad);
                return "Se han retirado " + cantidad + " € de la cuenta " + cuentaBancaria.getNumeroDeCuenta();
            }else if (cuentaBancaria.getSaldoActual() < cantidad) return "No hay dinero suficiente en la cuenta.";
            return "Debe ingresar un número válido.";
        }
        return "La cuenta introducida no existe.";
    }

    public synchronized String transferir(CuentaBancaria cuentaOrigen, CuentaBancaria cuentaDestino, float cantidad){
        if (!cuentas.contains(cuentaOrigen) || !cuentas.contains(cuentaDestino)) {
            return "Alguna de las cuentas no existe.";
        }
        if (cantidad <= 0) return "Cantidad inválida.";

        if (cuentaOrigen.getSaldoActual() < cantidad) return "No hay dinero suficiente en la cuenta origen.";

        cuentaOrigen.setSaldoActual(cuentaOrigen.getSaldoActual() - cantidad);
        cuentaDestino.setSaldoActual(cuentaDestino.getSaldoActual() + cantidad);

        return "Se han transferido " + cantidad + " € desde la cuenta " +
                cuentaOrigen.getNumeroDeCuenta() + " hasta la cuenta " + cuentaDestino.getNumeroDeCuenta();
    }

    public void guardarCuentas(String ruta) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ruta))) {
            for (CuentaBancaria c : cuentas) {
                bw.write(c.getNumeroDeCuenta() + ";" + c.getSaldoActual());
                bw.newLine();
            }
        }
    }

    public void cargarCuentas(String ruta) throws IOException {
        cuentas.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(ruta))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(";");
                cuentas.add(new CuentaBancaria(partes[0], Float.parseFloat(partes[1])));
            }
        }
    }
}
