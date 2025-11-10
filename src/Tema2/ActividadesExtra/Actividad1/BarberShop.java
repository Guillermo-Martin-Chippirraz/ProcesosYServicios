package Tema2.ActividadesExtra.Actividad1;

import java.util.ArrayList;

public class BarberShop {
    private boolean wokenBarber = true;
    private final int chairs;
    private ArrayList<Integer> waitingRoom;

    public BarberShop(int chairs){
        this.chairs = chairs;
        waitingRoom = new ArrayList<Integer>();
    }

    public boolean isWokenBarber() {
        return wokenBarber;
    }

    public void setWokenBarber(boolean wokenBarber) {
        this.wokenBarber = wokenBarber;
    }

    public int getChairs() {
        return chairs;
    }

    public ArrayList<Integer> getWaitingRoom() {
        return waitingRoom;
    }

    public void setWaitingRoom(ArrayList<Integer> waitingRoom) {
        this.waitingRoom = waitingRoom;
    }

    public synchronized void clientArrives(int clienteId){
        if (waitingRoom.size() < chairs){
            waitingRoom.add(clienteId);
            System.out.println("Cliente " + clienteId + " entra y se sienta. En espera: " +
                    waitingRoom.size());
            notifyAll();
        }else
            System.out.println("Cliente " + clienteId + " se va, no hay sillas libres.");


    }

    public synchronized int nextClient(){
        while (waitingRoom.isEmpty()){
            try {
                System.out.println("El barbero está dormido esperando clientes...");
                wait();
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
        int clienteId = waitingRoom.remove(0);
        wokenBarber = false;
        return clienteId;
    }

    public synchronized void wakeBarber(){
        wokenBarber = true;
    }
}
