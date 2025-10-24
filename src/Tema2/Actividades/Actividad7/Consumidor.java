package Tema2.Actividades.Actividad7;

public class Consumidor implements Runnable{
    private Monitor bandeja;

    public Consumidor(Monitor s){
        bandeja = s;
    }

    public void run(){
        int num;

        for (int i = 0; i < 5; i++){
            num = bandeja.consumir();;
            System.out.println("Número cogido: " + num);
            try {
                Thread.sleep((int) (Math.random()*1000));
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }
}
