package Tema3.Practica.Actividad3;

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
        for (CuentaBancaria cuentaBancaria : cuentas){
            if (cuentaBancaria.getSaldoActual() != -1f)
                this.cuentas.add(cuentaBancaria);
        }
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
        String retiro = retirar(cuentaOrigen, cantidad);
        String ingreso = ingresar(cuentaDestino, cantidad);

        if (retiro.startsWith("Se han retirado")){
            if (ingreso.startsWith("Se han ingresado")) return "Se han transferido " + cantidad + " € desde la cuenta " +
                    cuentaOrigen.getNumeroDeCuenta() + " hasta la cuenta " + cuentaDestino.getNumeroDeCuenta() + " con éxito";
            else if (ingreso.startsWith("Debe")) return ingreso;
            else return "La cuenta con número " + cuentaDestino.getNumeroDeCuenta() + " no existe.";
        } else if (retiro.startsWith("Debe")) return retiro;
        else return "La cuenta con número " + cuentaOrigen.getNumeroDeCuenta() + " no existe.";
    }
}
