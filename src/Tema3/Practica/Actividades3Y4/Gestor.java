package Tema3.Practica.Actividades3Y4;

import java.io.*;
import java.util.ArrayList;

/**
 * Clase que gestiona un conjunto de cuentas bancarias para las actividades 3 y 4 de la práctica del tema 3.
 * @author Guillermo Martín Chippirraz
 * @version v3.5
 * @see CuentaBancaria
 */
public class Gestor {
    private ArrayList<CuentaBancaria> cuentas;

    /**
     * Constructor por defecto.
     * @since v3.5
     */
    public Gestor(){
        cuentas = new ArrayList<>();
    }

    /**
     * Constructor por parámetros.
     * @since v3.5
     * @see #setCuentas(ArrayList)
     * @param cuentas Colección de cuentas bancarias a gestionar.
     */
    public Gestor(ArrayList<CuentaBancaria> cuentas){
        setCuentas(cuentas);
    }

    /**
     * Getter del atributo cuentas.
     * @since v3.5
     * @return ArrayList almacenado en el atributo cuentas.
     */
    public ArrayList<CuentaBancaria> getCuentas() {
        return cuentas;
    }

    /**
     * Setter del atributo cuentas.
     * @since v3.5
     * @param cuentas Colección de cuentas bancarias a almacenar.
     */
    public void setCuentas(ArrayList<CuentaBancaria> cuentas) {
        this.cuentas = new ArrayList<>(cuentas);
    }

    /**
     * Método getByNum.
     * Busca una cuenta bancaria cuyo número coincida con el String recibido por parámetros.
     *
     * <ol>
     *     <li>Se recorre el ArrayList cuentas mediante un bucle for-each.</li>
     *     <li>En cada iteración, se compara el número recibido con el número de cuenta del objeto iterado.</li>
     *     <li>Si coincide, se retorna dicho objeto CuentaBancaria.</li>
     *     <li>Si finaliza el bucle sin coincidencias, se retorna un nuevo objeto CuentaBancaria vacío.</li>
     * </ol>
     * @since v3.5
     * @see CuentaBancaria
     * @param numeroDeCuenta Número de cuenta a buscar.
     * @return Objeto CuentaBancaria encontrado o uno vacío si no existe.
     */
    public CuentaBancaria getByNum(String numeroDeCuenta){
        for (CuentaBancaria cuenta : cuentas){
            if (numeroDeCuenta.equals(cuenta.getNumeroDeCuenta()))
                return cuenta;
        }
        return new CuentaBancaria();
    }

    /**
     * Método sincronizado consultar.
     * Devuelve el saldo actual de una cuenta bancaria si esta pertenece al gestor.
     *
     * <ol>
     *     <li>Se evalúa si el ArrayList cuentas contiene el objeto CuentaBancaria recibido.</li>
     *     <li>De ser así, se retorna el valor devuelto por getSaldoActual().</li>
     *     <li>En caso contrario, se retorna -1f para indicar error.</li>
     * </ol>
     * @since v3.5
     * @see CuentaBancaria#getSaldoActual()
     * @param cuentaBancaria Cuenta bancaria cuyo saldo se desea consultar.
     * @return Saldo actual o -1f si la cuenta no existe.
     */
    public synchronized float consultar(CuentaBancaria cuentaBancaria){
        if (cuentas.contains(cuentaBancaria))
            return cuentaBancaria.getSaldoActual();
        return -1f;
    }

    /**
     * Método sincronizado ingresar.
     * Ingresa una cantidad de dinero en la cuenta indicada, siempre que sea válida.
     *
     * <ol>
     *     <li>Se evalúa si la cuenta existe dentro del ArrayList cuentas.</li>
     *     <li>Se evalúa si la cantidad es mayor o igual que 0 y tiene como máximo dos decimales.</li>
     *     <li>De ser así:
     *         <ol>
     *             <li>Se suma la cantidad al saldo actual mediante el setter setSaldoActual().</li>
     *             <li>Se retorna un mensaje indicando éxito.</li>
     *         </ol>
     *     </li>
     *     <li>Si la cantidad no es válida, se retorna un mensaje de error.</li>
     *     <li>Si la cuenta no existe, se retorna un mensaje indicándolo.</li>
     * </ol>
     * @since v3.5
     * @see CuentaBancaria#setSaldoActual(float)
     * @param cuentaBancaria Cuenta bancaria donde se realizará el ingreso.
     * @param cantidad Cantidad a ingresar.
     * @return Mensaje indicando el resultado de la operación.
     */
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

    /**
     * Método sincronizado retirar.
     * Retira una cantidad de dinero de la cuenta indicada, siempre que sea válida y exista saldo suficiente.
     *
     * <ol>
     *     <li>Se evalúa si la cuenta existe dentro del ArrayList cuentas.</li>
     *     <li>Se evalúa si la cantidad es válida y tiene como máximo dos decimales.</li>
     *     <li>Se evalúa si el saldo actual es suficiente.</li>
     *     <li>De ser así:
     *         <ol>
     *             <li>Se resta la cantidad al saldo actual mediante el setter setSaldoActual().</li>
     *             <li>Se retorna un mensaje indicando éxito.</li>
     *         </ol>
     *     </li>
     *     <li>Si no hay saldo suficiente, se retorna un mensaje indicándolo.</li>
     *     <li>Si la cantidad no es válida, se retorna un mensaje de error.</li>
     *     <li>Si la cuenta no existe, se retorna un mensaje indicándolo.</li>
     * </ol>
     * @since v3.5
     * @see CuentaBancaria#setSaldoActual(float)
     * @param cuentaBancaria Cuenta bancaria desde la que se retirará dinero.
     * @param cantidad Cantidad a retirar.
     * @return Mensaje indicando el resultado de la operación.
     */
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

    /**
     * Método sincronizado transferir.
     * Transfiere una cantidad de dinero desde una cuenta origen hacia una cuenta destino.
     *
     * <ol>
     *     <li>Se evalúa si ambas cuentas existen dentro del ArrayList cuentas.</li>
     *     <li>Se evalúa si la cantidad es mayor que 0.</li>
     *     <li>Se evalúa si la cuenta origen dispone de saldo suficiente.</li>
     *     <li>De ser así:
     *         <ol>
     *             <li>Se resta la cantidad al saldo de la cuenta origen mediante el setter setSaldoActual().</li>
     *             <li>Se suma la cantidad al saldo de la cuenta destino mediante el setter setSaldoActual().</li>
     *             <li>Se retorna un mensaje indicando éxito.</li>
     *         </ol>
     *     </li>
     *     <li>Si alguna cuenta no existe, se retorna un mensaje indicándolo.</li>
     *     <li>Si la cantidad es inválida, se retorna un mensaje de error.</li>
     *     <li>Si no hay saldo suficiente en la cuenta origen, se retorna un mensaje indicándolo.</li>
     * </ol>
     * @since v3.5
     * @see CuentaBancaria#setSaldoActual(float)
     * @param cuentaOrigen Cuenta desde la que se enviará el dinero.
     * @param cuentaDestino Cuenta que recibirá el dinero.
     * @param cantidad Cantidad a transferir.
     * @return Mensaje indicando el resultado de la operación.
     */
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

    /**
     * Método guardarCuentas.
     * Guarda todas las cuentas bancarias en un fichero de texto en formato separado por ';'.
     *
     * <ol>
     *     <li>Se abre un bloque try-with-resources declarando un BufferedWriter asociado a la ruta indicada.</li>
     *     <li>Se recorre el ArrayList cuentas mediante un bucle for-each.</li>
     *     <li>En cada iteración:
     *         <ol>
     *             <li>Se escribe en el fichero el número de cuenta y el saldo separados por ';'.</li>
     *             <li>Se invoca el método newLine() para saltar a la siguiente línea.</li>
     *         </ol>
     *     </li>
     * </ol>
     * @since v3.5
     * @param ruta Ruta del fichero donde se guardarán las cuentas.
     * @throws IOException Si ocurre un error de escritura en el fichero.
     */
    public void guardarCuentas(String ruta) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ruta))) {
            for (CuentaBancaria c : cuentas) {
                bw.write(c.getNumeroDeCuenta() + ";" + c.getSaldoActual());
                bw.newLine();
            }
        }
    }

    /**
     * Método cargarCuentas.
     * Carga las cuentas bancarias desde un fichero de texto previamente guardado.
     *
     * <ol>
     *     <li>Se limpia el ArrayList cuentas mediante el método clear().</li>
     *     <li>Se abre un bloque try-with-resources declarando un BufferedReader asociado a la ruta indicada.</li>
     *     <li>Se declara un String linea.</li>
     *     <li>Se abre un bucle while que itera mientras el método readLine() no devuelva null.</li>
     *     <li>En cada iteración:
     *         <ol>
     *             <li>Se divide la línea utilizando el método split(";"), obteniendo un array de Strings.</li>
     *             <li>Se crea un nuevo objeto CuentaBancaria utilizando como número de cuenta la primera posición
     *                 del array y como saldo el resultado de parsear a float la segunda posición.</li>
     *             <li>Se añade el objeto CuentaBancaria creado al ArrayList cuentas mediante el método add().</li>
     *         </ol>
     *     </li>
     * </ol>
     * @since v3.5
     * @see CuentaBancaria
     * @param ruta Ruta del fichero desde el que se cargarán las cuentas.
     * @throws IOException Si ocurre un error de lectura en el fichero.
     */
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