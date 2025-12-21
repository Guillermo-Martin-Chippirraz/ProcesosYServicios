package Tema3.Practica.Actividades3Y4;

/**
 * Clase que modela una cuenta bancaria para las actividades 3 y 4 de la práctica del tema 3.
 *
 * @author Guillermo Martín Chippirraz
 * @version v3.5.2
 */
public class CuentaBancaria {
    private String numeroDeCuenta;
    private float saldoActual;

    /**
     * Constructor por defecto.
     * @since v3.5
     */
    public CuentaBancaria(){
        numeroDeCuenta = "";
        saldoActual = 0f;
    }

    /**
     * Constructor por parámetros.
     * @since v3.5
     * @see #setNumeroDeCuenta(String)
     * @see #setSaldoActual(float)
     * @param numeroDeCuenta String que representa el número de cuenta bancaria.
     * @param saldoActual float que representa el saldo inicial de la cuenta.
     */
    public CuentaBancaria(String numeroDeCuenta, float saldoActual){
        setNumeroDeCuenta(numeroDeCuenta);
        setSaldoActual(saldoActual);
    }

    /**
     * Getter del atributo numeroDeCuenta.
     * @since v3.5
     * @return String almacenado en el atributo numeroDeCuenta.
     */
    public String getNumeroDeCuenta() {
        return numeroDeCuenta;
    }

    /**
     * Setter del atributo numeroDeCuenta.
     * @since v3.5
     * @param numeroDeCuenta String que representa el número de cuenta bancaria.
     */
    public void setNumeroDeCuenta(String numeroDeCuenta) {
        this.numeroDeCuenta = numeroDeCuenta;
    }

    /**
     * Getter del atributo saldoActual.
     * @since v3.5
     * @return float almacenado en el atributo saldoActual.
     */
    public float getSaldoActual() {
        return saldoActual;
    }

    /**
     * Setter del atributo saldoActual.
     * Valida que el saldo no sea negativo y redondea el valor a dos decimales.
     *
     * <ol>
     *     <li>
     *         Se evalúa si el float saldoActual recibido por parámetros es mayor o igual que 0.
     *         <ol>
     *             <li>
     *                 De ser así, se redondea el valor a dos decimales mediante la expresión:
     *                 Math.round(saldoActual * 100f) / 100f.
     *             </li>
     *             <li>
     *                 Se almacena el resultado en el atributo saldoActual.
     *             </li>
     *         </ol>
     *     </li>
     *     <li>
     *         En caso contrario:
     *         <ol>
     *             <li>Se asigna el valor 0f al atributo saldoActual para evitar saldos negativos.</li>
     *         </ol>
     *     </li>
     * </ol>
     * @since v3.5
     * @param saldoActual float que representa el saldo a validar y almacenar.
     */
    public void setSaldoActual(float saldoActual) {
        if (saldoActual >= 0) {
            this.saldoActual = Math.round(saldoActual * 100f) / 100f;
        } else {
            this.saldoActual = 0f; // nunca saldo negativo
        }
    }
}
