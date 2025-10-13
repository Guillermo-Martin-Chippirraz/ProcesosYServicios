package Tema1.Practica;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * Clase para la Actividad6.
 * @author Guillermo Martín Chippirraz
 * @version v1.5
 */
public class Actividad6 {
    /**
     * Método para dar al usuario a elegir una opción. Contiene un switch para cada opción disponible:
     * <ol>
     *     <li>
     *         En caso de seleccionar la opción 1, se solicita al usuario introducir por consola la ruta al directorio
     *         y se le da a elegir entre las opciones disponibles, la cual también debe introducir por teclado.
     *         <br>
     *         Una vez introducido, se devuelve el resultado del método listar.
     *     </li>
     *     <li>
     *         En caso de seleccionar la opción 2, se solicita al usuario introducir por consola una opción entre las
     *         ofrecidas, posteriormente, también se le solicita introducir las rutas de origen y destino por teclado.
     *         <br>
     *         Una vez introducido, se devuelve el resultado del método copia.
     *     </li>
     *     <li>
     *         En caso de seleccionar la opción 3, se solicita al usuario introducir la ruta al archivo que se va a
     *         comprimir y se le da a elegir entre las opciones disponibles, la cual también debe introducir por teclado.
     *         <br>
     *         Una vez introducido, se devuelve el resultado del método comprimir.
     *     </li>
     *     <li>
     *         En caso de seleccionar la opción 4, se solicita al usuario introducir la url del archivo que se va a
     *         descargar y la ruta del directorio en el que se va a descargar.
     *         <br>
     *         Una vez introducido, se devuelve el resultado del método descargar.
     *     </li>
     *     <li>
     *         En cualquier otro caso, se envía un mensaje aleccionador y se devuelve un array vacío.
     *     </li>
     * </ol>
     * @see #listar(String, String)
     * @see #copia(String, String, String)
     * @see #comprimir(String, String)
     * @see #descarga(String, String)
     * @param option Entero con la opción a seleccionar del menú.
     * @return Resultado de cualquiera de los métodos referenciados.
     */
    public static String[] menu(int option){
        Scanner scan = new Scanner(System.in);
        String archivo;
        switch (option){
            case 1:
                String tipoListado;
                System.out.println("Ha seleccionado \"Listar un directorio\"." +
                        "\nPor favor, escriba la ruta al directorio:");
                archivo = scan.nextLine();
                System.out.println("A continuación, introduzca el tipo de listado que desea para este directrio:" +
                        "\n-s: Listado simple." +
                        "\n-d: Listado detallado." +
                        "\n-t: Listado por tipos." +
                        "\n-m: Listado ordenado por fecha de última modificación.");
                tipoListado = scan.nextLine();
                return listar(archivo, tipoListado);
            case 2:
                String rutaDestino;
                String tipoCopia;
                System.out.println("Ha seleccionado \"Copiar fichero o directorio\"." +
                        "\nPor favor, seleccione una opción:" +
                        "\n-f: Fichero concreto." +
                        "\n-ds: Copia simple de directorio." +
                        "\n-dr: Copia recursiva de directorio.");
                tipoCopia = scan.nextLine();
                System.out.println("A continuación, escriba la ruta del archivo que desee copiar:");
                archivo = scan.nextLine();
                System.out.println("Gracias. A continuación, por favor, introduzca la ruta en la que deseea copiar el " +
                        "archivo");
                rutaDestino = scan.nextLine();
                return copia(tipoCopia, archivo, rutaDestino);
            case 3:
                String tipoCompresion;
                System.out.println("Ha seleccionado \"Comprimir fichero o directorio\"." +
                        "\nPor favor, escriba la ruta del archivo o directorio:");
                archivo = scan.nextLine();
                System.out.println("A continuación, seleccione una opción:" +
                        "\n-n: Compresión por defecto." +
                        "\n-c: Compresión dirigida.");
                tipoCompresion = scan.nextLine();
                return comprimir(archivo, tipoCompresion);
            case 4:
                String url;
                String destino;
                System.out.println("Ha seleccionado \"Descargar desde una URL\"." +
                        "\nPor favor, escriba la URL del fichero:");
                url = scan.nextLine();
                System.out.println("A continuación, introduzca la ruta de destino.");
                destino = scan.nextLine();
                return descarga(url, destino);
            default:
                System.out.println("Ha seleccionado \"Salir del menú\"." +
                        "\nEspero que haya disfrutado haciendo perder el tiempo a este pobre software.");
        }
        return new String[]{};
    }

    /**
     * Método para generar el comando para listar archivos. Si el directorio existe y es un directorio, se comprueba
     * por un switch la opción introducida, devolviendo el array de comandos correspondiente, y un mensaje de error en
     * cualquier otro caso. Se envía un mensaje de error en otro caso.
     * @param archivo String con la ruta al directorio que se va a listar.
     * @param opcion String con la opción seleccionada.
     * @return Array de Strings con el comando o array vacío en caso de error.
     */
    public static String[] listar(String archivo, String opcion){
        File directorio = new File(archivo);
        if (directorio.exists() && directorio.isDirectory()){
            switch (opcion){
                case "-s":
                    return new String[]{"ls", directorio.getAbsolutePath()};
                case "-d":
                    return new String[]{"ls", "-l", directorio.getAbsolutePath()};
                case "-t":
                    return new String[]{"ls", "-X", directorio.getAbsolutePath()};
                case "-m":
                    return new String[]{"ls", "-lt", directorio.getAbsolutePath()};
                default:
                    System.err.println("Error, el comando introducido no existe o no es válido.");
            }
        }else System.err.println("Error, no se ha introducido un directorio.");

        return new String[]{};

    }

    /**
     * Método para generar el comando para copiar archivos. Según el tipo de copia, se comprueba si el archivo es
     * del tipo correspondiente, devolviendo el array de comandos adecuado o imprimiendo un mensaje de error según el
     * resultado de la comprobación. Se imprime un mensaje de error si el tipo de copia no existe.
     * @param tipo String con el tipo de copia seleccionada
     * @param rutaOrigen String con la ruta al archivo original
     * @param rutaDestino String con la ruta al directorio en el que se va a copiar el archivo.
     * @return Array de Strings con el comando correspondiente al tipo de copia y las rutas o vacío en caso de error.
     */
    public static String[] copia(String tipo, String rutaOrigen, String rutaDestino){
        File originPath = new File(rutaOrigen);
        File destinyPath = new File(rutaDestino);
        switch (tipo){
            case "-f":
                if (originPath.isFile()) return new String[]{"cp", originPath.getAbsolutePath(),
                        destinyPath.getAbsolutePath()};
                else System.err.println("Error, se debe introducir un fichero para copiar con la opción \"-f\".");
                break;
            case "-ds":
                if (originPath.isDirectory()) return new String[]{"cp", "-d", originPath.getAbsolutePath(),
                        destinyPath.getAbsolutePath()};
                else System.err.println("Error, se debe introducir un fichero para copiar con la opción \"-ds\".");
                break;
            case "-dr":
                if (originPath.isDirectory()) return new String[]{"cp", "-r", originPath.getAbsolutePath(),
                        destinyPath.getAbsolutePath()};
                else System.err.println("Error, se debe introducir un fichero para copiar con la opción \"-dr\".");
                break;
            default:
                System.err.println("Error, la opción seleccionada no existe.");
        }
        return new String[]{};
    }

    /**
     * Método para generar el comando de compresión de archivos. Se comprueba si el archivo es un directorio o un
     * fichero y se comprueba el tipo de compresión en cada caso. Para la compresión normal se devuelve un array de
     * comandos u otro según si el archivo es un fichero o un directorio. Por otro lado, en la compresión dirigida,
     * se solicita al usuario que introduzca la ruta de la carpeta en la que se va a guardar la compresión, asegurando
     * que la ruta sea un directorio. Tras ello, se genera el comando de forma análoga al caso por defecto, con esta
     * modificación. Se imprimen mensajes de error según si la opción no existe o si la ruta no pertenece a un fichero
     * o directorio.
     * @param archivo String con la ruta al archivo que se va a comprimir.
     * @param tipo String con el tipo de compresión seleccionado.
     * @return Array de String con el comando para compresión o vacío en caso de error.
     */
    public static String[] comprimir(String archivo, String tipo){
        File ruta = new File(archivo);
        if (ruta.isDirectory() || ruta.isFile()){
            switch (tipo){
                case "-n":
                    return ruta.isFile() ? new String[]{"zip", ruta.getName() + ".zip", ruta.getAbsolutePath()} :
                            new String[]{"zip", "-r", ruta.getName() + ".zip", ruta.getAbsolutePath()};
                case "-c":
                    Scanner scan = new Scanner(System.in);
                    String carpeta;
                    System.out.println("Por favor, introduzca la carpeta de destino:");
                    carpeta = scan.nextLine();
                    while (!new File(carpeta).isDirectory()){
                        System.out.println("Por favor, introduzca una carpeta de destino válida:");
                        carpeta = scan.nextLine();
                    }
                    return ruta.isFile() ? new String[]{"zip", carpeta + "/" + ruta.getName() + ".zip",
                            ruta.getAbsolutePath()} :
                            new String[]{"zip", "-r", carpeta + "/" + ruta.getName() + ".zip", ruta.getAbsolutePath()};
                default:
                    System.err.println("Error, la opción introducida no existe.");
            }
        }else System.err.println("Error, la ruta introducida no pertenece a un fichero o directorio");

        return new String[]{};
    }

    /**
     * Método que comprueba a través de un proceso si la orden curl está instalada en el so.
     * @return Booleano con el resultado de comprobar el código de finalización del proceso o false en caso de
     * excepción.
     */
    private static boolean comprobarCurl(){
        try {
            Process compruebaCurl = new ProcessBuilder("curl", "--version").start();
            return compruebaCurl.waitFor() == 0;
        }catch (IOException | InterruptedException e){
            return false;
        }
    }

    /**
     * Método que instala curl a través de un proceso con un comando en bash.
     * @return Booleano con el resultado de comprobar el código de finalización, que sólo devuelve true en caso de
     * éxito y false sólo en caso de excepción.
     */
    private static boolean instalarCurl(){
        String[] comandos = {
                "sudo apt-get update && sudo apt-get install -y curl",
                "sudo yum install -y curl"
        };

        for (String comando : comandos){
            try {
                Process instalaCurl = new ProcessBuilder("bash", "-c", comando).inheritIO().start();
                if (instalaCurl.waitFor() == 0) return true;
            }catch (IOException | InterruptedException e){
                // Ignora y prueba el siguiente comando
            }
        }
        return false;
    }

    /**
     * Método que genera el comando de descarga desde la url. Comprueba si curl está instalado y lo instala de no ser
     * así. Posteriormente, comprueba si la ruta da a un directorio existente imprimendo un mensaje de error de no ser
     * así.
     * @param url String con la url de descarga.
     * @param destino String con la ruta al directorio en el que se va a descargar el url.
     * @return Array de String con el comando de descarga o vacío en caso de error.
     */
    public static String[] descarga(String url, String destino){
        if (!comprobarCurl()){
            System.out.println("curl no está instalado. Intentando instalar...");
            if (!instalarCurl()){
                System.out.println("No se pudo instalar curl. Abortando.");
                return new String[]{};
            }
        }
        File directorio = new File(destino);
        if (!directorio.exists() || !directorio.isDirectory()){
            System.out.println("El directorio introducido no es válido.");
            return new String[]{};
        }

        return new String[]{"curl", "-o", directorio.getAbsolutePath(), url};
    }

    /**
     * En el main sucede lo siguiente:
     * <ol>
     *     <li>
     *         Se declara un array de String en el que se almacenará el comando, un entero para la opcion y un objeto de
     *         la clase Scanner para permitir al usuario introducir texto por teclado.
     *     </li>
     *     <li>
     *         Se imprimen las opciones disponibles para el usuario y se almacena en el entero opción el siguiente
     *         entero que escriba.
     *     </li>
     *     <li>
     *         Se almacena en el array de String comandos el retorno del método menu con la opción seleccionada.
     *     </li>
     *     <li>
     *         Se comprueba si el array de String está vacío y se cierra el programa si es así.
     *     </li>
     *     <li>
     *         Se declara un String y asigna el valor «n».
     *     </li>
     *     <li>
     *         En el do:
     *         <ol>
     *             <li>
     *                 Se inicializa un objeto de la clase ProcessBuilder con el contenido del array de String comandos.
     *             </li>
     *             <li>
     *                 En el try:
     *                 <ol>
     *                     <li>
     *                         Se redirige la salida del comando para que se muestre por pantalla con inheritIO().
     *                     </li>
     *                     <li>
     *                         Se inicializa el proceso y se almacena en un objeto de la clase Process.
     *                     </li>
     *                     <li>
     *                         Se almacena en un entero el código de finalización de waitFor().
     *                     </li>
     *                     <li>
     *                         Se imprime un mensaje de éxito en caso de ser así.
     *                     </li>
     *                 </ol>
     *                 En el catch:
     *                 <ol>
     *                     <il>
     *                         Se hace control de excepción de entrada/salida o se interrumpe el proceso
     *                     </il>
     *                     <li>
     *                         Se imprime mensaje de error.
     *                     </li>
     *                     <li>
     *                         Se comprueba si la opción seleccionada era una descarga y se ofrece al usuario la opción
     *                         de volver a intentarlo.
     *                     </li>
     *                 </ol>
     *             </li>
     *         </ol>
     *     </li>
     *     En el while se comprueba si la opción era de descarga y si el usuario ha aceptado volver a intentar,
     *     reiniciando el bucle.
     * </ol>
     * @param args Array de Strings que debe estar vacío.
     */
    public static void main(String[] args) {
        String[] comandos;
        int opcion;
        Scanner scan = new Scanner(System.in);
        System.out.println("Por favor, introduzca una opción:" +
                "\n1: Listar un directorio." +
                "\n2: Copiar un fichero o directorio." +
                "\n3: Comprimir un fichero o directorio." +
                "\n4: Descargar un fichero desde una URL." +
                "\nCualquier otro número: Salir del programa.");
        opcion = scan.nextInt();
        comandos = menu(opcion);

        if (comandos.length == 0) System.exit(1);

        String selector = "n";

        do {
            ProcessBuilder accion = new ProcessBuilder(comandos);
            try {
                accion.inheritIO();
                Process process = accion.start();
                int exitCode = process.waitFor();
                if (exitCode == 0){
                    System.out.println("Operación realizada con éxito.");
                }
            }catch (IOException | InterruptedException e){
                e.printStackTrace();
                if (opcion == 4){
                    System.out.println("Error al descargar el archivo." +
                            "\n¿Desea reintentar? (s/n):");
                    selector = scan.nextLine().toLowerCase();
                }
            }
        }while (opcion == 4 && selector.equals("s"));

    }
}
