import java.io.*;

public class Actividad3 {
    public static void main(String[] args){
        if(args.length != 1)
            System.out.println("Uso: java ListadoDirectorioCSV <ruta_del_directorio>");
        else {
            String rutaDirectorio = args[0];
            File directorio = new File(rutaDirectorio);

            if (!directorio.exists() || !directorio.isDirectory())
                System.out.println("No se pudo acceder al contenido del directorio");
            else {
                File[] archivos = directorio.listFiles();
                if (archivos == null){
                    System.out.println("No se pudo acceder al contenido del directorio.");
                }else {
                    String nombreArchivoCSV = "listado_directorio.csv";
                    try(BufferedWriter writer = new BufferedWriter(new FileWriter(nombreArchivoCSV))){
                        for (File archivo : archivos){
                            String nombre = archivo.getName();
                            String tipo = archivo.isDirectory() ? "Directorio" : "Archivo";
                            long tamaño = archivo.length();
                            String permisos = obtenerPermisos(archivo);

                            String linea = nombre + "," + tipo + "," + tamaño + "," + permisos;
                            writer.write(linea);
                            writer.newLine();
                        }
                    }catch (IOException e){
                        System.out.println("Error al escribir el archivo CSV: " + e.getMessage());
                    }
                }
            }
        }
    }

    private static String obtenerPermisos(File path){
        String permisos = "";

        permisos += path.canRead() ? "r" : "-";
        permisos += path.canWrite() ? "w" : "-";
        permisos += path.canExecute() ? "x" : "-";

        return permisos;
    }
}
