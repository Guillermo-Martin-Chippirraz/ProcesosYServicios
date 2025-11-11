package Tema2.ActividadesExtra.Actividad2;

public class Main {
    public static void main(String[] args) {
        Mesa mesa = new Mesa(5);

        for (int i = 0; i < 5; i++){
            new Thread(new Filosofo(i+1, mesa)).start();
        }
    }
}
