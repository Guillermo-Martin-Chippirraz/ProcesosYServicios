package Tema2.ActividadesExtra.Actividad2;

public class Mesa {
    private final boolean[] cubiertos;

    public Mesa(int numFilósofos){
        cubiertos = new boolean[numFilósofos];
        for (int i = 0; i < numFilósofos; i++){
            cubiertos[i] = false;
        }
    }

    public synchronized void tomarCubierto(int idFilósofo){
        int izquierda = idFilósofo;
        int derecha = (idFilósofo + 1) % cubiertos.length;

        while (cubiertos[izquierda] || cubiertos[derecha]){
            try {
                System.out.println("Filósofo " + idFilósofo + " espera sus cubiertos...");
                wait();
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }

        cubiertos[izquierda] = true;
        cubiertos[derecha] = true;
        System.out.println("Filósofo " + idFilósofo + " ha domado los cubiertos " + izquierda + " y " + derecha);
    }

    public synchronized void dejarCubiertos(int idFilosofo){
        int izquierda = idFilosofo;
        int derecha = (idFilosofo + 1) % cubiertos.length;

        cubiertos[izquierda] = false;
        cubiertos[derecha] = false;

        System.out.println("Filósofo " + idFilosofo + " ha dejado los cubiertos " + izquierda + " y " + derecha);
        notifyAll();
    }
}
