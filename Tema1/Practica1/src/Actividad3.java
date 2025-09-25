import java.io.File;
import java.io.FileWriter;

public class Actividad3 {
    public static void directoryToFile(File directorio){
        if (directorio.exists() && directorio.isFile()){
            if (directorio.listFiles() != null){
                System.out.println("Nombre\t" +
                        "Tipo\t" +
                        "Tamaño\t" +
                        "Permisos\n");
                for (File hijo : directorio.listFiles()){
                    if (hijo.isDirectory())
                        directoryToFile(hijo);

                    System.out.println(hijo.getName() + "\t" +
                            hijo.getName().substring(hijo.getName().indexOf("."), hijo.getName().length()-1)
                    + ".");
                }
            }

        }
    }
}
