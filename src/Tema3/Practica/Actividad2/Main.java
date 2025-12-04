package Tema3.Practica.Actividad2;

import java.util.ArrayList;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        ArrayList<Biblioteca.Libro> libros = new ArrayList<>();
        ArrayList<String> generos;
        Random random = new Random();
        for (int i = 1; i<6; i++){
            String genero;

            libros.add(new Biblioteca.Libro("Libro " + i, ""));
        }
        Biblioteca biblioteca = new Biblioteca();
    }
}
