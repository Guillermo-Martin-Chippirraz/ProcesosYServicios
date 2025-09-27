import java.io.File;
import java.io.IOException;

public class Actividad3 {
    public static void main(String[] args) {
        if (args.length != 1){
            System.err.println("Uso: java Actividad3 <directorio>");
            System.exit(1);
        }

        String directorio = args[0];
        File dir = new File(directorio);

        if (!dir.exists() || !dir.isDirectory()){
            System.err.println("El directorio especificado no existe o no es válido.");
            System.exit(1);
        }

        // Ruta del archivo CSV de salida
        String salidaCSV = "listado.csv";

        // Comando para generar el CSV usando bash y redirigir a archivo
        String comando = String.format("bash -c \"ls -l '%s' | tail -n +2 | awk '{perm=$1; size=$5; name=$9; " +
                "); print name \",\" size \",\" perm}' > '%s'\"", directorio, salidaCSV);

        try{
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
