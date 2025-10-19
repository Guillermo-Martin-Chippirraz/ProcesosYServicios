package Tema2.Practica.Actividad3;

import java.util.HashMap;

/**
 * Clase para la tienda de informática de la actividad 3 de la práctica del tema 2
 * @author Guillermo Martín Chippirraz
 * @version 2.3
 */
public class Shop {
    private final HashMap<Thread, Integer> blockCount = new HashMap<>();
    private int availableCards;
    private boolean isFull = false;

    /**
     * Constructor por defecto
     * @since v2.3
     */
    public Shop(){
        availableCards = 0;
    }

    /**
     * Constructor por parámetros
     * @since v2.3
     * @see #setAvailableCards(int)
     * @param availableCards Entero a enviar al setter del atributo availableCards
     */
    public Shop(int availableCards){
        setAvailableCards(availableCards);
    }

    /**
     * Getter del atributo avaliableCards
     * @since v2.3
     * @return Entero almacenado en el atributo availableCards
     */
    public int getAvailableCards(){
        return availableCards;
    }

    /**
     * Setter del atributo availableCards. Comprueba si la cantidad introducida por parámetros es no negativa y la
     * asigna al atributo avaliableCards en caso de ser así. En caso contrario, envía un mensaje de error y asigna el
     * valor por defecto al atributo.
     * @since v2.3
     * @param availableCards Entero a validar y almacenar en el atributo availableCards
     */
    public void setAvailableCards(int availableCards){
        if (availableCards >= 0)
            this.availableCards = availableCards;
        else{
            this.availableCards = 0;
            System.out.println("ERROR. EL NÚMERO DE TARJETAS GRÁFICAS NO PUEDE SER NEGATIVO.");
        }
    }

    /**
     * Setter del atributo ISFULL
     * @since v2.3
     * @param isFull Booleano que se asigna al atributo estático ISFULL
     */
    public void setISFULL(boolean isFull){
        this.isFull = isFull;
    }

    /**
     * Método sincronizado para la venta concurrente de tarjetas gráficas.
     * En primer lugar se comprueba si el cliente se ha ido o no, de tal forma que la tienda pueda vender a ese cliente.
     * Comprueba si la tienda está ocupada, de ser así, llama al método waitCounter().
     * Si en el proceso de espera el cliente se marcha, el proceso de venta se detiene.
     * Una vez el atributo ISFULL es falso, se comprueba si quedan tarjetas. En caso de no quedar, el hilo en ejecución se interrumpe y se
     * prepara el resto de hilos.
     * En cualquier otro caso, la venta tiene éxito y se prepara el resto de hilos.
     * @since v2.3
     * @see #getAvailableCards()
     * @see #waitCounter()
     * @see #setAvailableCards(int)
     */
    public synchronized void sellCard(){
        boolean canSell = true;
        if (Thread.currentThread().isInterrupted()){
            System.out.println("El cliente " + Thread.currentThread().getName() + " se ha ido.");
            canSell = false;
        }

        if (canSell){
            while (isFull && !Thread.currentThread().isInterrupted()){
                System.out.println("La tienda está llena");
                waitCounter();

                if (Thread.currentThread().isInterrupted()){
                    System.out.println("El cliente " + Thread.currentThread().getName() + " se fue");
                    canSell = false;
                }
            }
        }


        if (canSell){
            setISFULL(true);

            if (getAvailableCards() <= 0){
                System.out.println("Se acabaron las tarjetas gráficas.");
                notifyAll();
                Thread.currentThread().interrupt();
            }else {
                System.out.println("Tarjeta gráfica vendida con éxito.");
                setAvailableCards(getAvailableCards() - 1);
            }

            notifyAll();
        }

    }

    /**
     * Método privado sincronizado que cuenta los intentos del cliente para entrar a la tienda.
     * Una vez que el número de bloqueos es mayor o igual a 10, el hilo es interrumpido. En otro caso, se aumenta en 1
     * el contador y se bloquea el hilo.
     * @since v2.3
     */
    private synchronized void waitCounter(){
        Thread thread = Thread.currentThread();
        int blocks = blockCount.getOrDefault(thread, 0);

        if (blocks >= 10){
            System.out.println("El cliente " + thread.getName() + " se cansó de esperar y se va al hospital.");
            thread.interrupt();
        }else {
            blockCount.put(thread, blocks + 1);
            try{
                wait();
            }catch (InterruptedException e){
                thread.interrupt();
            }
        }
    }
}
