package Tema2.Practica.Actividad3;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Shop shop = new Shop(100);
        ArrayList<Thread> clientela = new ArrayList<>();

        for (int i = 0; i < 300; i++){
            Thread cliente = new Thread(new Client(shop), "Cliente " + i);
            clientela.add(cliente);
            cliente.start();
        }

        for (Thread cliente : clientela){
            try {
                cliente.join();
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }
}
