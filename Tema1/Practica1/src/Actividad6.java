import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Actividad6 {
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

    private static boolean comprobarCurl(){
        try {
            Process compruebaCurl = new ProcessBuilder("curl", "--version").start();
            return compruebaCurl.waitFor() == 0;
        }catch (IOException | InterruptedException e){
            return false;
        }
    }

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
