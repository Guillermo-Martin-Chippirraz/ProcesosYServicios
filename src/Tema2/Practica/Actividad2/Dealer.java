package Tema2.Practica.Actividad2;

import java.util.Random;

/**
 * Clase Dealer para la práctica del Tema 2. SUGERENCIA: Para propósitos de evaluación, se ruega al evaluador alterar
 * ciertos parámetros para que el proceso no sea demasiado largo. Se marcarán esas líneas con un comentario.
 * @author Guillermo Martín Chippirraz
 * @version v2.3
 * @see Store
 */
public class Dealer implements Runnable{
    private final static int MAX_VEL = 60;//Velocidad máxima de circulación en km/h
    private final static int HOURSTOMILI = 3600000; //ALTERAR
    private static Random rand = new Random();
    private int workingDay; //Jornada laboral en h
    private final Store store;

    /**
     * Constructor por defecto
     * @since v2.2
     */
    public Dealer(){
        store = new Store();
        workingDay = 0;
    }

    /**
     * Constructor por parámetros
     * @since v2.2
     * @see #setWorkingDay(int)
     * @param store Objeto de la clase Store a copiar superficialmente en el atributo store
     * @param workingDay Entero a enviar al setter del atributo workingDay
     */
    public Dealer(Store store, int workingDay){
        this.store = store;
        setWorkingDay(workingDay);
    }

    /**
     * Getter del atributo workingDay
     * @since v2.2
     * @return Entero almacenado en el atributo workingDay
     */
    public int getWorkingDay(){
        return workingDay;
    }

    /**
     * Setter del atributo workingDay. Comprueba si el entero introducido por parámetros es válido y almacena dicho
     * entero en el atributo workingDay de ser así. Almacena el valor por defecto e imprime un mensaje de error
     * personalizado en caso contrario.
     * @since v2.2
     * @param workingDay Entero a validar y almacenar en el parámetro workingDay
     */
    public void setWorkingDay(int workingDay){
        if (workingDay >= 0 && workingDay <= 8)
            this.workingDay = workingDay;
        else {
            this.workingDay = 0;
            if (workingDay < 0)
                System.err.println("ERROR. LA JORNADA LABORAL NO PUEDE SER NEGATIVA.");
            else if (workingDay > 8){
                System.err.println("ERROR. LA JORNADA LABORAL MÁXIMA POR CONVENIO ES 8 HORAS DIARIAS.");
            }
        }
    }

    /**
     * Implementación del método run de la interfaz Runnable.
     * Simula la jornada laboral de un repartidor. Se almacena en una variable la jornada laboral que se debe completar
     * hoy. Comprueba si la jornada laboral se ha completado y, si no es así, intenra recoger un paquete y genera la
     * distancia de forma aleatoria en un rango de 0 a 100 kilómetros (radio aproximado de una ciudad grande como
     * Madrid) y la almacena en una variable. Tras ello, se calcula el tiempo que queda en horas y se resta del total.
     * A continuación, se bloquea el hilo en ejecución el tiempo que tarda el repartidor en llevar el paquete esa
     * distancia y volver (suponiendo que tiene suerte o es muy temerario y puede ir a la velocidad máxima). Si el
     * repartidor no ha descansado y ha superado la mitad de la jornada laboral de ese día, el hilo se bloquea 45
     * minutos, simulando un descanso real del repartidor.
     * @since v2.2
     * @see #getWorkingDay()
     * @see Store#llevarPaquete()
     * @see #setWorkingDay(int)
     * @see Store#setTotalVehicles(int)
     */
    public void run(){
        int todayWorkingDay = getWorkingDay();
        boolean descanso = false;
        while (getWorkingDay() > 0){
            long distancia = rand.nextLong(100);
            store.llevarPaquete();
            setWorkingDay(getWorkingDay() - (int) (2*(distancia/MAX_VEL)));

            try {
                Thread.sleep(2*(distancia/MAX_VEL)*HOURSTOMILI);
            }catch (InterruptedException e){
                Thread.currentThread().interrupt();
            }

            store.setTotalVehicles(store.getTotalVehicles() + 1);
            System.out.println("Vehículo entregado con éxito");

            if (getWorkingDay() >= todayWorkingDay/2 && !descanso){
                try {
                    Thread.sleep(45*60*1000); //ALTERAR
                }catch (InterruptedException e){
                    Thread.currentThread().interrupt();
                }

                descanso = true;
            }
        }

        if (workingDay == 0){
            System.out.println("Jornada laboral finalizada.");
        }
    }
}
