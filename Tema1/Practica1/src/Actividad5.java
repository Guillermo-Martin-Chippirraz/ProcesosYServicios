import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

public class Actividad5 {
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
                }else System.err.println("Error al copiar el archivo. Código: ");

            }catch (IOException | InterruptedException e){
                System.err.println("Error: " + e.getMessage());
            }
        }else System.err.println("Alguna de las rutas introducidas no existe.");

    }
}
