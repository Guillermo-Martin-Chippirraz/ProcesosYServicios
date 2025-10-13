package Tema1.Practica;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

/**
 * Clase para la actividad 5.
 * @author Guillermo Martín Chippirraz
 * @version v1.5
 */
public class Actividad5 {
    /**
     * En el main se llevan a cabo las siguientes operaciones:
     * <ol>
     *     <li>
     *         Se comprueba si la longitud del parámetro args es diferente de 2, enviándose un mensaje de error y
     *         cerrando el programa en caso de ser así.
     *     </li>
     *     <li>
     *         Se inicializa un objeto de la clase File para la ruta de origen y otro para la ruta de Destino. Se
     *         declara un objeto pb de la clase ProcessBuilder.
     *     </li>
     *     <li>
     *         Se comprueba si las rutas dan a un archivo existente, en caso de ser así:
     *         <ol>
     *             <li>
     *                 Se comprueba si la ruta de origen da a un fichero o a un directorio y se inicializa el objeto
     *                 de ProcessBuilder según el comando correspondiente.
     *             </li>
     *             <li>
     *                 En el try:
     *                 <ol>
     *                     <li>
     *                         Se inicializa un objeto de la clase Date para indicar el momento en que inicia el proceso.
     *                     </li>
     *                     <li>
     *                         Se crea un objeto de la clase Process donde se ejecuta el proceso pb.
     *                     </li>
     *                     <li>
     *                         Se almacena en un entero el resultado del método waitFor().
     *                     </li>
     *                     <li>
     *                         Se inicializa otro objeto de la clase Date, esta vez, para indicar el momento en que
     *                         finaliza el proceso.
     *                     </li>
     *                     <li>
     *                         Una vez se comprueba que el proceso tuvo éxito:
     *                         <ol>
     *                             <li>
     *                                 Se almacena en un long la diferencia en ms entre el instante de inicio y el de
     *      *                             finalización del proceso.
     *                             </li>
     *                             <li>
     *                                 Se crea un objeto File para almacenar el fichero txt que contendrá el tiempo.
     *                             </li>
     *                             <li>
     *                                 En el try:
     *                                 <ol>
     *                                     <li>
     *                                         Se inicializa un objeto BufferedWriter a partir de un FileWriter inicializado
     *      *                                     desde el File archivoTiempo.
     *                                     </li>
     *                                     <li>
     *                                         Se introduce el long duración en el archivoTiempo
     *                                     </li>
     *                                 </ol>
     *
     *                                 En el catch:
     *                                 <ol>
     *                                     <li>
     *                                          Se toman las excepciones de flujo de entrada/salida.
     *                                     </li>
     *                                     <li>
     *                                         Se devuelve un mensaje de error con el getMessage del control de
     *                                         excepciones.
     *                                     </li>
     *                                 </ol>
     *                             </li>
     *                             <li>
     *                                 Se imprime un mensaje indicando que el proceso se ha completado la duración del
     *                                 proceso y otro mensaje indicando el path en el que se ha guardado el log de
     *                                 tiempo.
     *                             </li>
     *                         </ol>
     *                     </li>
     *                     <li>
     *                         Si no ha tenido éxito, se imprime un mensaje con el código de finalización.
     *                     </li>
     *                 </ol>
     *
     *                 En el catch:
     *                 <ol>
     *                     <li>
     *                         Se hace control de excepciones de flujo de entrada/salida y de interrupción del proceso.
     *                     </li>
     *                     <li>
     *                         Se imprime un mensaje de error con el método getMessage().
     *                     </li>
     *                 </ol>
     *             </li>
     *         </ol>
     *     </li>
     *     <li>
     *         En caso de que las rutas no lleven a ningún archivo, se envía un mensaje de error.
     *     </li>
     * </ol>
     *
     * @param args Array de Strings que sólo debe contener el path de origen y el de destino.
     */
    public static void main(String[] args) {
        if (args.length != 2){
            System.err.println("Uso: java Actividad5 <origin_path> <destiny_path>");
            System.exit(1);
        }

        File rutaOrigen = new File(args[0]);
        File rutaDestino = new File(args[1]);
        ProcessBuilder pb;

        if (rutaOrigen.exists() && rutaDestino.exists()){
            if (rutaOrigen.isFile()) pb = new ProcessBuilder("cp", args[0], args[1]);
            else pb = new ProcessBuilder("cp", "-r", args[0], args[1]);
            try{
                Date inicio = new Date();

                Process proceso = pb.start();

                int exitCode = proceso.waitFor();
                Date fin = new Date();

                if (exitCode == 0){
                    long duracion = fin.getTime() - inicio.getTime();

                    File archivoTiempo = new File(rutaDestino.getParent(), "tiempo_copia.txt");
                    try(BufferedWriter writer = new BufferedWriter(new FileWriter(archivoTiempo))){
                        writer.write("Tiempo de copia: " + duracion + " ms");
                    }catch (IOException e) {
                        System.err.println("Error: " + e.getMessage());
                    }

                    System.out.println("Copia completada en " + duracion + " ms.");
                    System.out.println("Tiempo guardado en: " + archivoTiempo.getAbsolutePath());
                }else System.err.println("Error al copiar el archivo. Código: " + exitCode);

            }catch (IOException | InterruptedException e){
                System.err.println("Error: " + e.getMessage());
            }
        }else System.err.println("Alguna de las rutas introducidas no existe.");

    }
}
