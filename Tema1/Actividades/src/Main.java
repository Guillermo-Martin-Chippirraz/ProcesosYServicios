import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        //Actividad 7
        /*
        ProcessBuilder pb = new ProcessBuilder("gnome-calculator");
        try{
            pb.start();
        }catch (IOException e){
            e.printStackTrace();
        }
         */


        //Actividad 8
        /*
        ProcessBuilder pb = new ProcessBuilder("ping", "-c", "4", "google.com");
        pb.inheritIO();
        try {
            Process p = pb.start();
            p.waitFor();
            }
        }catch (IOException | InterruptedException e){
            throw new RuntimeException(e);
        }
        */

        //Actividad 9

        List<String> orden;
        String comando;
        Scanner scan = new Scanner(System.in);
        ProcessBuilder pb;
        System.out.println("Por favor, introduzca un comando: ");
        comando = scan.next();
        if (comando.contains(" ")){
            String [] ordenes = comando.split(" ");
            orden = List.of(comando);
            pb = new ProcessBuilder(orden);
        }else pb = new ProcessBuilder(comando);

        pb.inheritIO();
        try{
            Process p = pb.start();
            int exitCode = p.waitFor();
            System.out.println("\nEl comando finalizó con el código de salida: " + exitCode);
        }catch (IOException | InterruptedException e){
            throw new RuntimeException(e);
        }



        //Actividad 10
        /*
        String directorio;
        String comando;
        Scanner scan = new Scanner(System.in);
        ProcessBuilder directorioActual;
        ProcessBuilder pb;
        System.out.println("Por favor, introduzca un directorio:");
        directorio = scan.next();
        while (!(new File(directorio).isDirectory())){
            System.out.println("Por favor, introduzca un directorio válido.");
            directorio = scan.nextLine();
        }
        System.out.println(directorio);
        directorioActual = new ProcessBuilder("cd", directorio);
        System.out.println("Por favor, introduzca un comando: ");
        comando = scan.next();
        if (comando.contains(" ")){
            String [] ordenes = comando.split(" ");
            pb = new ProcessBuilder(ordenes);
        }else pb = new ProcessBuilder(comando);

        pb.inheritIO();
        try{
            Process pDirectorio = directorioActual.start();
            Process p = pb.start();
            int directoryExitCode = pDirectorio.waitFor();
            int exitCode = p.waitFor();
            System.out.println("\nEl directorio ha sido creado con éxito. Código: " +
                    directoryExitCode +
                    "\nEl comando finalizó con el código de salida: " + exitCode);
        }catch (IOException | InterruptedException e){
            throw new RuntimeException(e);
        }
         */
    }
}