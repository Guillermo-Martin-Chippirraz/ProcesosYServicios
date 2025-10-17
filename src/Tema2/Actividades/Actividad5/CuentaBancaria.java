package Tema2.Actividades.Actividad5;

public class CuentaBancaria {
    private double saldo;

    public CuentaBancaria(double saldoInicial){
        setSaldo(saldoInicial);
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    //Método crítico, requiere sincronización
    public synchronized void depositar(double cantidad){
        setSaldo(getSaldo() + cantidad);
    }

    //Método crítico, requiere sincronización
    public synchronized void retirar(double cantidad){
        setSaldo(getSaldo() - cantidad);
    }
}
