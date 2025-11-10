package Tema2.ActividadesExtra.Actividad1;

public class Main {
    public static void main(String[] args) {
        int sillas = 3;
        int clientes = 10;

        BarberShop barberShop = new BarberShop(sillas);

        Thread barbero = new Thread(new Barber(barberShop));

        for (int i = 1; i <= clientes; i++){
            Thread cliente = new Thread(new Client(barberShop, i));
            cliente.start();
        }
    }
}
