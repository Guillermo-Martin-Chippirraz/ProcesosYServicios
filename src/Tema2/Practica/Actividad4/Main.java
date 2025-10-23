package Tema2.Practica.Actividad4;

/**
 * Clase Main para la actividad 4 de la práctica 2
 */
public class Main {
    public static void main(String[] args) {
        Dispositivo nvme = new Dispositivo("NVMe", 3);
        Dispositivo hdd = new Dispositivo("HDD", 10);
        Gestor gestor = new Gestor(nvme, hdd);
        ThreadGroup solicitudesGrupo = new ThreadGroup("Solicitudes");

        // Lanzar servidores
        new Thread(new Servidor(nvme)).start();
        new Thread(new Servidor(hdd)).start();

        // Generar solicitudes
        for (int i = 0; i < 30; i++){
            boolean alta = Math.random() < 0.5;
            Solicitud s = new Solicitud("S" + i, (int) (Math.random() * 5 + 1), alta, gestor);
            new Thread(solicitudesGrupo, s).start();
            try {
                Thread.sleep(300);
            }catch (InterruptedException e){
                Thread.currentThread().interrupt();
            }

            //Reubicar si hay nuevo espacio en NVMe
            gestor.reubicarSolicitudes();
        }
    }
}
