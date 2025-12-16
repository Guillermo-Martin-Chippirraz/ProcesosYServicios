package Tema3.Practica.Actividad3;

import java.io.Serializable;

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
        if (saldoActual >= 0) {
            this.saldoActual = Math.round(saldoActual * 100f) / 100f;
        } else {
            this.saldoActual = 0f; // nunca saldo negativo
        }
    }
}
