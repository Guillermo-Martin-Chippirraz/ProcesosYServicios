package Tema2.ActividadesExtra.Actividad2;

public class Filosofo implements Runnable{
    private int id;
    private Mesa mesa;

    public Filosofo(int id, Mesa mesa){
        this.id = id;
        this.mesa = mesa;
    }

    private void pensar(){
        System.out.println("Filosofo " + id + " está pensando...");
        try {
            Thread.sleep((int) (Math.random() * 4000));
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    private void comer(){
        System.out.println("Filósofo " + id + " está comiendo...");
        try {
            Thread.sleep((int) (Math.random() * 3000));
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        System.out.println("Filósofo " + id + " ha terminado de comer.");
    }

    public void run(){
        while (true){
            pensar();
            mesa.tomarCubierto(id);
            comer();
            mesa.dejarCubiertos(id);
        }
    }
}
