package Tema2.Actividades;

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

        Actividad3 dado1 = new Actividad3();
        Actividad3 dado2 = new Actividad3();
        Actividad3 dado3 = new Actividad3();

        dado1.setName("Hilo 1");
        dado2.setName("Hilo 2");
        dado3.setName("Hilo 3");

        dado1.start();
        dado2.start();
        dado3.start();

        try{
            dado1.join();
            dado2.join();
            dado3.join();
        }catch (InterruptedException e){
            System.out.println("Error al esperar los hilos.");
        }

        int suma = dado1.getResultado() + dado2.getResultado() + dado3.getResultado();
        System.out.println("Suma total de las tiradas: " + suma);
    }
}