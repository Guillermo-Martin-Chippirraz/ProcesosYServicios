import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Actividad4 {
    public static void main(String[] args) {
        String archivoCSV = "listado.csv";
        String os = System.getProperty("os.name").toLowerCase();
        boolean esWindows = os.contains("win");

        try (BufferedReader br = new BufferedReader(new FileReader("listado.csv"))){
            String linea;
            while ((linea = br.readLine()) != null){
                String[] campos = linea.split(",");
                if (campos.length >= 4){
                    String nombre = campos[0].trim();
                    String tipo = campos[1].trim();
                    File archivo = new File(nombre);
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
