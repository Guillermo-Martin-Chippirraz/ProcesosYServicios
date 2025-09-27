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
        String salidaCSV = new File("listado.csv").getAbsolutePath();

        // Comando para generar el CSV usando bash y redirigir a archivo
        /*
        bash -c: Ejecuta el comando que viene después entre comillas dobles
        ls -l: Lista los archivos del directorio en formato largo
        | tail -n +2: Elimina la primera línea de lo que devuelve ls -l, que suele ser total N
        | awk '{}': Procesa cada línea con awk para extraer y manipular los campos
        perm=$1: guarda los permisos del archivo
        size=$5: guarda el tamaño en bytes
        type=(substr(perm,1,1)==\"d\" ? \"directorio\" : \"archivo\"): extrae el primer carácter de los permisos y si
        es d, almacena la palabra directorio, si no, almacena la palabra archivo
        name=""; for(i=9;i=NF;i++) name=name $i " ";: Reconstruye el nombre del archivo desde el campo 9 hasta el final,
        indicado por NF, esto permite incluir espacios
        name=substr(name,1,length(name)-1);: Elimina el espacio extra al final
        print name "," size "," perm "," type: Imprime en formato CSV los datos
        > salidaCSV: Redirige la salida al archivo CSV
         */
        String comando = "bash -c \"ls -l " + directorio + " | tail -n +2 | awk '{perm=$1; size=$5; " +
                "type=(substr(perm, 1, 1)==\"d\" ? \"directorio\" : \"archivo\"); name=\"\"; for(i=9; i<=NF; i++) " +
                "name=name $i \" \"; name=substr(name,1,length(name)-1; print name \",\" size \",\" perm \",\" type}' " +
                "> " + salidaCSV + "\"";

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
