package Tema2.Actividades;

public class Actividad2 implements Runnable{
    private String mensaje;

    public Actividad2(String mensaje){
        this.mensaje = mensaje;
    }

    public void run(){
        for (int i = 1; i < 6; i++){
            System.out.println(mensaje + " - iteración " + i);
        }
    }
}
