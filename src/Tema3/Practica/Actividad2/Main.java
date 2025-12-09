package Tema3.Practica.Actividad2;

import java.util.ArrayList;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        ArrayList<Biblioteca.Libro> libros = new ArrayList<>();
        ArrayList<String> generos = new ArrayList<>();
        generos.add("Fantasía");
        generos.add("Ciencia Ficción");
        generos.add("Romance");
        generos.add("Drama");
        generos.add("Policiaca");
        generos.add("Historica");
        generos.add("Biográfica");
        generos.add("");
        Random random = new Random();
        for (int i = 1; i<6; i++){
            String genero = generos.get(random.nextInt(0, 8));
            libros.add(new Biblioteca.Libro("Libro " + i, genero));
        }
        Biblioteca biblioteca = new Biblioteca(libros);
        Servidor servidor = new Servidor(biblioteca);
        Thread servidorThread = new Thread(servidor);
        servidorThread.start();
        for (int i = 1; i<6; i++){
            Thread cliente = new Thread(new Cliente(i));
            cliente.start();
        }
    }
}
