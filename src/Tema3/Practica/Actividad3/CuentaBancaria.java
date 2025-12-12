package Tema3.Practica.Actividad3;

public class CuentaBancaria {
    private String numeroDeCuenta;
    private float saldoActual;

    public CuentaBancaria(){
        numeroDeCuenta = "";
        saldoActual = 0f;
    }

    public CuentaBancaria(String numeroDeCuenta, float saldoActual){
        setNumeroDeCuenta(numeroDeCuenta);
        setSaldoActual(saldoActual);
    }

    public String getNumeroDeCuenta() {
        return numeroDeCuenta;
    }

    public void setNumeroDeCuenta(String numeroDeCuenta) {
        this.numeroDeCuenta = numeroDeCuenta;
    }

    public float getSaldoActual() {
        return saldoActual;
    }

    public void setSaldoActual(float saldoActual) {
        this.saldoActual = (saldoActual*1000 % 100 == 0) ? saldoActual : -1f;
    }
}
