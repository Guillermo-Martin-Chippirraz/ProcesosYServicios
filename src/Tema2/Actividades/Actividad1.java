package Tema2.Actividades;

public class Actividad1 extends Thread{
    private String mensaje;

    public Actividad1(String mensaje){
        this.mensaje = mensaje;
    }

    public void run(){
        for (int i = 1; i <= 5; i++){
            System.out.println(mensaje + " - iteración " + i);
        }
    }
}
