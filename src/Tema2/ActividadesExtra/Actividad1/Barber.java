package Tema2.ActividadesExtra.Actividad1;

public class Barber implements Runnable{
    private BarberShop barberShop;

    public Barber(BarberShop barberShop){
        this.barberShop = barberShop;
    }

    public void run(){
        while (true){
            int idCliente = barberShop.nextClient();
            attendClient(idCliente);
            barberShop.wakeBarber();
        }
    }

    private void attendClient(int idCliente){
        System.out.println("El barbero está cortando el pelo al cliente " + idCliente);
        try {
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        System.out.println("El cliente " + idCliente + " ha terminado su corte.");
    }
}
