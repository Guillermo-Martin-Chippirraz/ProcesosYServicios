package Tema2.ActividadesExtra.Actividad1;

public class Client implements Runnable{
    private BarberShop barberShop;
    private int idCliente;

    public  Client(BarberShop barberShop, int idCliente){
        this.barberShop = barberShop;
        this.idCliente = idCliente;
    }

    public void run(){
        try {
            Thread.sleep((int) (Math.random() * 5000));
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        barberShop.clientArrives(idCliente);
    }
}
