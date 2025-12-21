package Tema3.Practica.Actividades3Y4;

/**
 * Clase principal de las actividades 3 y 4 de la práctica del tema 3.
 *
 * @author Guillermo Martín Chippirraz
 * @version  v3.5.2
 */
public class Main {
    /*** La línea de ejecución del método main es la siguiente:
     * <ol>
     *     <li>
     *         Se declara un objeto de la clase Gestor y se inicializa mediante su constructor por defecto.
     *     </li>
     *     <li>
     *         Se añaden tres objetos de la clase CuentaBancaria al ArrayList devuelto por el getter
     *         getCuentas() del objeto Gestor gestor. Cada cuenta se inicializa mediante su constructor
     *         por parámetros, introduciendo un número de cuenta y un saldo inicial.
     *         <ol>
     *             <li>Cuenta1 con saldo 10F.</li>
     *             <li>Cuenta2 con saldo 1000.27F.</li>
     *             <li>Cuenta3 con saldo 0F.</li>
     *         </ol>
     *     </li>
     *     <li>
     *         Se declara un objeto de la clase Servidor y se inicializa mediante su constructor por
     *         parámetros, introduciendo el objeto Gestor gestor como valor del parámetro gestor.
     *     </li>
     *     <li>
     *         Se declara un objeto de la clase Thread y se inicializa mediante su constructor por
     *         parámetros, introduciendo el objeto Servidor servidor como valor del parámetro target.
     *         A continuación, se invoca el método start() sobre dicho hilo, iniciando la ejecución
     *         concurrente del servidor.
     *     </li>
     *     <li>
     *         Se abre un bucle for cuyo contador i toma valores desde 1 hasta 3 (ambos inclusive).
     *         En cada iteración:
     *         <ol>
     *             <li>
     *                 Se declara un boolean admin. Si el valor devuelto por Math.random() es mayor que 0.5,
     *                 se asigna true; en caso contrario, se asigna false.
     *             </li>
     *             <li>
     *                 Se declara un objeto de la clase Thread y se inicializa mediante su constructor por
     *                 parámetros, introduciendo como valor del parámetro target un nuevo objeto de la clase
     *                 Cliente, inicializado mediante su constructor por parámetros con el String
     *                 "Cliente" concatenado con el valor del contador i como parámetro id y el boolean admin
     *                 como parámetro admin.
     *             </li>
     *             <li>
     *                 Se invoca el método start() sobre el hilo recién creado, iniciando así la ejecución
     *                 concurrente del cliente correspondiente.
     *             </li>
     *         </ol>
     *     </li>
     * </ol>
     *
     * @since v3.5
     * @see Gestor
     * @see CuentaBancaria
     * @see Servidor
     * @see Cliente
     * @param args Argumentos de la línea de comandos
     */
    public static void main(String[] args) {
        Gestor gestor = new Gestor();
        gestor.getCuentas().add(new CuentaBancaria("Cuenta1" , 10F));
        gestor.getCuentas().add(new CuentaBancaria("Cuenta2" , 1000.27F));
        gestor.getCuentas().add(new CuentaBancaria("Cuenta3" , 0F));

        Servidor servidor = new Servidor(gestor);
        new Thread(servidor).start();

        for (int i = 1; i <= 3; i++){
            boolean admin;
            if (Math.random() > 0.5){
                admin = true;
            } else admin = false;
            new Thread(new Cliente("Cliente" + i, admin)).start();
        }
    }
}
