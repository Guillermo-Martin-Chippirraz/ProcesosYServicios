package Tema2.Actividades.Actividad5;

public class Cliente implements Runnable{
    private CuentaBancaria cuenta;

    public Cliente(CuentaBancaria cuenta){
        setCuenta(cuenta);
    }

    public CuentaBancaria getCuenta() {
        return cuenta;
    }

    public void setCuenta(CuentaBancaria cuenta) {
        this.cuenta = cuenta;
    }

    public void run(){
        for (int i = 0; i < 3; i++){
            double cantidadDeposito = Math.random() * 1000;
            cuenta.depositar(cantidadDeposito);
            double cantidadRetiro = Math.random() * 500;
            cuenta.retirar(cantidadRetiro);
        }
    }
}
