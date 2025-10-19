package Tema2.Practica.Actividad2;

/**
 * Clase Main para la Actividad 2 de la práctica del tema 2
 */
public class Main {
    public static void main(String[] args) {
        Store store = new Store(0, 3);

        Thread oficinista1 = new Thread(new Oficinist(store, 15));
        Thread oficinista2 = new Thread(new Oficinist(store, 40));

        Thread repartidor1 = new Thread(new Dealer(store, 8));
        Thread repartidor2 = new Thread(new Dealer(store, 5));
        Thread repartidor3 = new Thread(new Dealer(store, 6));
        Thread repartidor4 = new Thread(new Dealer(store, 8));

        oficinista1.start();
        oficinista2.start();
        repartidor1.start();
        repartidor2.start();
        repartidor3.start();
        repartidor4.start();

        try{
            oficinista1.join();
            oficinista2.join();
            repartidor1.join();
            repartidor2.join();
            repartidor3.join();
            repartidor4.join();
        }catch (InterruptedException e){
            e.printStackTrace();
        }

    }
}
