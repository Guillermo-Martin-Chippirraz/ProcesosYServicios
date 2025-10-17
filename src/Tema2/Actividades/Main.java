package Tema2.Actividades;

import Tema2.Actividades.Actividad4.Aumento;
import Tema2.Actividades.Actividad4.Contador;
import Tema2.Actividades.Actividad5.Cliente;
import Tema2.Actividades.Actividad5.CuentaBancaria;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

//        Tema2.Actividades.Actividad1 hilo1 = new Tema2.Actividades.Actividad1("Primer hilo");
//        Tema2.Actividades.Actividad1 hilo2 = new Tema2.Actividades.Actividad1("Segundo hilo");
//        Tema2.Actividades.Actividad1 hilo3 = new Tema2.Actividades.Actividad1("Tercer hilo");
//
//        hilo1.start();
//        hilo2.start();
//        hilo3.start();

//        Thread hilo1 = new Thread(new Tema2.Actividades.Actividad2("Primer hilo"));
//        Thread hilo2 = new Thread(new Tema2.Actividades.Actividad2("Segundo hilo"));
//        Thread hilo3 = new Thread(new Tema2.Actividades.Actividad2("Tercer hilo"));
//
//        hilo1.start();
//        hilo2.start();
//        hilo3.start();

//        Actividad3 dado1 = new Actividad3();
//        Actividad3 dado2 = new Actividad3();
//        Actividad3 dado3 = new Actividad3();
//
//        dado1.setName("Hilo 1");
//        dado2.setName("Hilo 2");
//        dado3.setName("Hilo 3");
//
//        dado1.start();
//        dado2.start();
//        dado3.start();
//
//        try{
//            dado1.join();
//            dado2.join();
//            dado3.join();
//        }catch (InterruptedException e){
//            System.out.println("Error al esperar los hilos.");
//        }
//
//        int suma = dado1.getResultado() + dado2.getResultado() + dado3.getResultado();
//        System.out.println("Suma total de las tiradas: " + suma);

//        Contador contador = new Contador();
//
//        Thread hilo1 = new Thread(new Aumento(contador, 5), "Hilo-1");
//        Thread hilo2 = new Thread(new Aumento(contador, 5), "Hilo-2");
//        Thread hilo3 = new Thread(new Aumento(contador, 5), "Hilo-3");
//
//        try {
//            hilo1.join();
//            hilo2.join();
//            hilo3.join();
//
//            hilo1.start();
//            hilo2.start();
//            hilo3.start();
//        }catch (InterruptedException e){
//            Thread.currentThread().interrupt();
//        }
//
//        System.out.println("Valor final del contador: " + contador.getValue());

//        CuentaBancaria cuenta = new CuentaBancaria(1000);
//
//        Thread cliente1 = new Thread(new Cliente(cuenta));
//        Thread cliente2 = new Thread(new Cliente(cuenta));
//
//        cliente1.start();
//        cliente2.start();

        Object lock = new Object();

        Thread hiloHola = new Thread(new Actividad6("Hola", lock, true));
        Thread hiloAdios = new Thread(new Actividad6("Adiós", lock, true));

        hiloAdios.start();
        hiloHola.start();
    }
}