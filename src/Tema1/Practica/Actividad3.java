package Tema1.Practica;

import java.io.File;
import java.io.IOException;

/**
 * Clase para la actividad 3.
 *
 * @author Guillermo Martín Chippirraz
 * @version v1.4
 */
public class Actividad3 {
    /**
     * En este main se llevan a cabo las siguientes operaciones:
     * <ol>
     *     <li>
     *         Comprueba si el número de argumentos es 1, en caso de no serlo, envía un mensaje de error con el
     *         uso y sale del programa.
     *     </li>
     *     <li>
     *         Almacenamos la ruta al directorio en un String a partir del primer (y único) argumento. Declaramos un
     *         objeto de la clase File y lo inicializamos con la ruta almacenada en directorio.
     *     </li>
     *     <li>
     *         Comprobamos si el objeto de la clase File existe y es un directorio. En caso de no serlo, envía un código
     *         de error y sale del programa.
     *     </li>
     *     <li>
     *         Declara y almacena en un String la ruta absoluta al nuevo csv.
     *     </li>
     *     <li>
     *         Declara y almacena en un string el comando que le vamos a pasar al constructor de la clase ProcessBuilder.
     *         El código bash que se almacena hace lo siguiente:
     *         <ol>
     *             <li>
     *                  echo "Ruta: " + directorio + " > " + salidaCSV: Escribe la línea «Ruta: [directorio]» en el
     *                  archivo CSV. Usa «>» para crear o sobrescribir el archivo «salidaCSV»
     *             </li>
     *             <li>
     *                 && echo "Nombre,Tamaño,Permisos,Tipo" >> " + salidaCSV: Añade una segunda línea con los
     *                 encabezados del CSV. Usa «>>» para añadir sin sobrescribir.
     *             </li>
     *             <li>
     *                 && ls -l " + directorio + " | tail -n +2 | awk '...' >> " + salidaCSV: Ejecuta «ls -l» sobre el
     *                 directorio para listar archivos con detalles. «tail -n + 2» omite la primera línea (que suele ser
     *                 el total de bloques). awk procesa cada línea para extraer:
     *                 <ul>
     *                     <li>
     *                         Permisos («$1»)
     *                     </li>
     *                     <li>
     *                         Tamaño («$5»)
     *                     </li>
     *                     <li>
     *                         Tipo: si el primer carácter de permisos es «d», es un directorio, si no, es un archivo.
     *                     </li>
     *                     <li>
     *                         Nombre: se reconstruye desde el campo 9 en adelante («$9» a «NF») para manejar nombres
     *                         con espacios.
     *                     </li>
     *                 </ul>
     *             </li>
     *             <li>
     *                 Finalmente, imprime cada línea en formato CSV.
     *             </li>
     *         </ol>
     *
     *         El resultado final se verá así:
     *         Ruta: /home/usuario/documentos
     *         Nombre,Tamaño,Permisos,Tipo
     *         archivo1.txt,2048,-rw-r--r--,archivo
     *         mi carpeta,4096,drwxr-xr-x,directorio
     *     </li>
     *     <li>
     *         El try contiene:
     *         <ol>
     *             <li>
     *                 Creo el objeto de la clase ProcessBuilder con lo siguiente "bash", "-c", comando.
     *             </li>
     *             <li>
     *                 Con inheritIO() se redirige la salida a la terminal.
     *             </li>
     *             <li>
     *                 Almaceno en un objeto de la clase Process el retorno del método pb.start().
     *             </li>
     *             <li>
     *                 Con waitFor() se devuelve un código de salida.
     *             </li>
     *         </ol>
     *         El catch simplemente incluye las excepciones típicas de salida y entrada, y de interrupción.
     *     </li>
     * </ol>
     * @param args Array de objetos de la clase String que deberá contener únicamente la ruta al directorio.
     */
    public static void main(String[] args) {
        if (args.length != 1){
            System.err.println("Uso: java Tema1.Practica.Actividad3 <directorio>");
            System.exit(1);
        }

        String directorio = args[0];
        File dir = new File(directorio);

        if (!dir.exists() || !dir.isDirectory()){
            System.err.println("El directorio especificado no existe o no es válido.");
            System.exit(1);
        }

        // Ruta del archivo CSV de salida
        String salidaCSV = new File("listado.csv").getAbsolutePath();

        // Comando para generar el CSV usando bash y redirigir a archivo
        String comando = "echo \"Ruta: " + directorio + "\" > " + salidaCSV +
                " && echo \"Nombre,Tamaño,Permisos,Tipo\" >> " + salidaCSV +
                " && ls -l " + directorio + " | tail -n +2 | awk '" +
                "{perm=$1; size=$5; type=(substr(perm,1,1)==\"d\" ? \"directorio\" : \"archivo\"); name=\"\"; " +
                "for(i=9;i<=NF;i++) name=name $i \" \"; name=substr(name,1,length(name)-1); " +
                "print name \",\" size \",\" perm \",\" type}' >> " + salidaCSV;

        try {
            ProcessBuilder pb = new ProcessBuilder("bash", "-c", comando);
            pb.inheritIO(); //Redirige la salida del proceso al terminal
            Process proceso = pb.start();
            proceso.waitFor();
            System.out.println("Listado generado en: " + salidaCSV);
        }catch (IOException | InterruptedException e){
            e.printStackTrace();
        }
    }
}
