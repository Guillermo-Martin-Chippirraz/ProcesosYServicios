import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Clase para la actividad 4.
 * @author Guillermo Martín Chippirraz
 * @version v1.4
 */
public class Actividad4 {
    /**
     * En el main se llevan a cabo las siguientes operaciones:
     * <ol>
     *     <li>
     *         Se declara un String y se le asigna el valor obtenido en minúsculas del método getProperty("os.name"),
     *         el da el nombre del sistema operativo.
     *     </li>
     *     <li>
     *         Se declara un booleano y se le asigna el resultado de comprobar que el String os contiene el String win,
     *         lo cual indica que se está usando windows.
     *     </li>
     *     <li>
     *         En el try se declara un objeto de la clase BufferedReader con el FileReader del fichero con el nombre
     *         "listado.csv". A continuación:
     *         <ol>
     *             <li>
     *                 Se declara un String llamado linea.
     *             </li>
     *             <li>
     *                 Se declara un String en el que se almacena la ruta de la primera línea usando el método readLine(),
     *                 al cuál le aplico el método split(" ") y extraigo el String almacenado en la primera celda.
     *             </li>
     *             <li>
     *                 Me salto la cabecera usando el método readLine()
     *             </li>
     *             <li>
     *                 En el bucle while se asigna a la variable línea el resultado de readLine() hasta que sea nulo y
     *                 se ejecuta lo siguiente en su interior:
     *                 <ol>
     *                     <li>
     *                         Se declara el array de String y se almacena el split del String linea usando "," como
     *                         separador.
     *                     </li>
     *                     <li>
     *                         Declaro un String nombre y almaceno el valor de campos[0] sin espacios usando trim()
     *                     </li>
     *                     <li>
     *                         Declaro un String tipo y almacena el valor de campos[1] sin espacios uasndo trim()
     *                     </li>
     *                     <li>
     *                         En la condición del if se busca si el tipo es un directorio, si lo es, se imprime por
     *                         pantalla y se envía al método listarContenidoDirectorio.
     *                     </li>
     *                     <li>
     *                         En el primer else if se comprueba a través del método esFicheroTexto si el fichero es un
     *                         .txt
     *                     </li>
     *                 </ol>
     *             </li>
     *         </ol>
     *     </li>
     *
     * </ol>
     * @param args
     */
    public static void main(String[] args) {
        String os = System.getProperty("os.name").toLowerCase();
        boolean esWindows = os.contains("win");

        try (BufferedReader br = new BufferedReader(new FileReader("listado.csv"))){
            String linea;
            String rutaPadre = br.readLine().split(" ")[1];
            br.readLine();
            while ((linea = br.readLine()) != null){
                String[] campos = linea.split(",");
                if (campos.length >= 4){
                    String nombre = campos[0].trim();
                    String tipo = campos[1].trim();
                    File archivo = new File(rutaPadre + "/" + nombre);
                    String rutaAbsoluta = archivo.getAbsolutePath();

                    if (tipo.equals("directorio")){
                        System.out.println("Directorio: " + nombre);
                        listarContenidoDirectorio(archivo);
                    }else if (esFicheroTexto(nombre)){
                        System.out.println("Fichero de texto: " + nombre);
                        mostrarContenidoFichero(archivo);
                    }else if (esEjecutable(archivo, esWindows)){
                        System.out.println("Ejecutable: " + nombre);
                        System.out.println("Ruta absoluta: " + rutaAbsoluta);
                    }else System.out.println("Tipo desconocido: " + nombre);

                    System.out.println("------------------------------------");

                }
            }
        }catch (IOException e){
            System.err.println("Error al leer el archivo CSV: " + e.getMessage());
        }
    }

    private static boolean esFicheroTexto(String nombre){
        return nombre.endsWith(".txt") || nombre.endsWith(".csv") || nombre.endsWith(".log") || nombre.endsWith(".md");
    }

    private static boolean esEjecutable(File archivo, boolean esWindows){
        if (esWindows){
            String nombre = archivo.getName().toLowerCase();
            return nombre.endsWith(".exe") || nombre.endsWith(".bat") || nombre.endsWith(".cmd");
        }else return archivo.exists() && archivo.canExecute();
    }

    private static void mostrarContenidoFichero(File archivo){
        if (archivo.exists()){
            try (BufferedReader br = new BufferedReader(new FileReader(archivo))){
                String linea;
                while ((linea = br.readLine()) != null){
                    System.out.println(linea);
                }
            }catch (IOException e){
                System.out.println("No se pudo leer el fichero: " + archivo.getName());
            }
        }else System.out.println("El fichero no existe: " + archivo.getName());
    }

    private static void listarContenidoDirectorio(File dir){
        if (dir.exists() && dir.isDirectory()){
            String[] contenido = dir.list();
            if (contenido != null){
                for (String item : contenido){
                    System.out.println(" - " + item);
                }
            }
        }
    }
}
