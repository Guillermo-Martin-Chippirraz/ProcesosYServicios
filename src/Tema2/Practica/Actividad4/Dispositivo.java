package Tema2.Practica.Actividad4;

/**
 * Clase Dispositivo para la actividad 4 de la práctica del tema 2
 * @author Guillermo Martín Chippirraz
 * @version 2.5
 * @see Solicitud
 */
public class Dispositivo {
    private final String nombre;
    private final int capacidad;
    private final Solicitud[] buffer;
    private int count = 0;

    /**
     * Constructor por defecto.
     * @since v2.4
     */
    public Dispositivo(){
        nombre = "";
        capacidad = 0;
        buffer = null;
    }

    /**
     * Constructor por parámetros.
     * Se inicializa el buffer de objetos de la clase Solicitud con el valor del parámetro capacidad asignando el
     * tamaño
     * @since v2.4
     * @param nombre String que se va a asignar al atributo nombre
     * @param capacidad Entero que se va a asignar al atributo capacidad.
     */
    public Dispositivo(String nombre, int capacidad){
        this.nombre = nombre;
        this.capacidad = capacidad;
        this.buffer = new Solicitud[capacidad];
    }

    /**
     * Getter del atributo nombre
     * @since v2.4
     * @return String almacenado en el atributo nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Getter del atributo capacidad
     * @since v2.4
     * @return Entero almacenado en el atributo capacidad
     */
    public int getCapacidad() {
        return capacidad;
    }

    /**
     * Getter del atributo buffer
     * @since v2.4
     * @return Array de objetos de la clase Solicitud almacenado en el atributo buffer
     */
    public Solicitud[] getBuffer() {
        return buffer;
    }

    /**
     * Método sincronizado para agregar solicitudes.
     * Se compryeba si el entero almacenado en el atributo count es menor que el entero almacenado en el atributo
     * capacidad. En caso de ser así, se declara la variable index y se le asigna el valor almacenado en el
     * atributo count aumentado en 1, el cual también se actualiza, se copia superficialmente en la celda del atributo
     * buffer correspondiente al valor de index y notifica a todos los hilos para prepararlos.
     * @since v2.4
     * @param s Objeto de la clase Solicitud a copiar superficialmente en la celda correspondiente del atributo buffer
     * @return Booleano según si se ha podido agregar la solicitud o no.
     */
    public synchronized boolean agregar(Solicitud s){
        if(count < capacidad){
            int index = count++;
            buffer[index] = s;
            notifyAll();
            System.out.println("Capacidad ocupada del dispositivo " + nombre + ": " + count);
            return true;
        }
        return false;
    }

    /**
     * Método sincronizado que reemplaza las solicitudes de baja prioridad en el buffer por otras solicitudes.
     * Se inicia un bucle for con el valor almacenado en el atributo count como valor de parada. En el bucle,
     * si la solicitud almacenada en la celda correspondiente al valor del iterador en el atributo buffer no es de
     * alta prioridad, se reemplaza por la copia superficial del objeto de la clase Solicitud introducido por parámetros
     * y se notifica a todos los hilos para prepararlos.
     * @see Solicitud#esAltaPrioridad()
     * @since 2.4.1
     * @param nueva Objeto de la clase Solicitud que se almacenará en el primer espacio del array de objetos de la clase
     *              Solicitud almacenado en el atributo buffer, que contenga un objeto de la clase Solicitud de baja
     *              prioridad.
     * @return Se devuelve un booleano en función de si el reemplazo ha sido posible o no.
     */
    public synchronized boolean reemplazarBajaPrioridad(Solicitud nueva){
        for (int i = 0; i < count; i++){
            if (!buffer[i].esAltaPrioridad()){
                buffer[i] = nueva;
                notifyAll();
                return true;
            }
        }
        return false;
    }

    /**
     * Método sincronizado que extrae la primera solicitud de alta prioridad almacenada en el buffer.
     * Se inicializa un buffer con el entero almacenado en el atributo count como valor de parada y, si
     * el objeto de la clase Solicitud almacenado en la celda correspondiente al valor del iterador en el atributo
     * buffer es de alta prioridad, se declara un objeto de la clase Solicitud y se almacena en él una copia
     * superficial del objeto almacenado en dicha celda del atributo buffer. Posteriormente, se inicializa otro bucle
     * con el valor del iterador del bucle exterior como valor inicial y el entero almacenado en el atributo count
     * disminuido en uno como valor de parada, de tal forma que, en cada celda del atributo buffer, se copia
     * superficialmente el valor almacenado en la celda posterior. Tras esto, se declara la variable entera index y
     * se le asigna el entero almacenado en el atributo count una vez este ha sido disminuido en uno. Esta última
     * variable es la posición de la celda del atributo buffer en el que se almacenará un objeto por defecto de la
     * clase Solicitud.
     *
     * @since v2.4
     * @see Solicitud#esAltaPrioridad()
     * @see Solicitud#Solicitud()
     * @return Copia superficial del primer objeto de la clase Solicitud almacenado en el atributo buffer cuya prioridad
     * sea alta u objeto de la clase Solicitud por defecto en caso de no haber solicitudes de alta prioridad.
     */
    public synchronized Solicitud extraerAltaPrioridad(){
        for (int i = 0; i < count; i++){
            if (buffer[i].esAltaPrioridad()){
                Solicitud s = buffer[i];
                for (int j = i; j < count - 1; j++){
                    buffer[j] = buffer[j+1];
                }
                int index = count--;
                buffer[index - 1] = new Solicitud();
                return s;
            }
        }
        return new Solicitud();
    }

    /**
     * Método sincronizado para comprobar si hay espacio disponible.
     * @since v2.4
     * @return Booleano con el resultado de comprobar si el valor almacenado en el atributo count es menor que el valor
     * almacenado en el atributo capacidad.
     */
    public synchronized boolean hayEspacio(){
        return count < capacidad;
    }

    /**
     * Método sincronizado para procesar solicitudes.
     * Se comprueba si el entero almacenado en el atributo count es mayor que cero. En caso de ser así, se declara
     * un objeto de la clase Solicitud y se le asigna una copia superficial del objeto almacenado en la primera
     * posición del atributo buffer. Se declara una variable entera y se le asigna el valor 0. Se inicia un
     * bucle while que se ejecuta mientras la variable de iteración es menor que el valor almacenado en count disminuido
     * en uno. En el bucle, se asigna a cada espacio del buffer el objeto almacenado en la posición posterior y
     * se aumenta en uno el iterador. Tras el bucle, la posición correspondiente al valor de count disminuido en uno,
     * se le almacena el objeto por defecto y se disminuye el valor de count en uno. Posteriormente, se crea una
     * variable String que se inicializa como «Baja». Si el objeto de la clase Solicitud inicializado anteriormente
     * es de alta prioridad, se cambia el valor almacenado en esta variable como «Alta». Se imprime por pantalla
     * que el proceso se está llevando a cabo y se bloquea por sleep el hilo el tiempo correspondiente a la duración
     * del stream. Finalmente se notifica a todos los hilos para prepararlos.
     *
     * @since v2.4
     * @see Solicitud#Solicitud()
     * @see Solicitud#esAltaPrioridad()
     * @see Solicitud#getId()
     * @see Solicitud#getDuracion()
     * @throws InterruptedException Controla excepciones relacionadas con la interrupción de los hilos.
     */
    public synchronized void procesarSolicitudes() throws InterruptedException{
        if (count > 0) {
            Solicitud s = buffer[0];

            int i = 0;
            while (i < count - 1) {
                buffer[i] = buffer[i + 1];
                i = i + 1;
            }

            buffer[count - 1] = new Solicitud();
            count = count - 1;

            String prioridad = "Baja";
            if (s.esAltaPrioridad()) {
                prioridad = "Alta";
            }

            System.out.println(nombre + " procesando " + s.getId() + " (" + prioridad + ")");
            Thread.sleep(s.getDuracion() * 100);
            notifyAll();
        }
    }
}
