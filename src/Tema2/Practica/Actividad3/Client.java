package Tema2.Practica.Actividad3;

/**
 * Clase para los clientes de la actividad 3 de la práctica del tema 2
 * @author Guillermo Martín Chippirraz
 * @version v2.3
 */
public class Client implements Runnable{
    private Shop shop;

    /**
     * Constructor por defecto
     * @since v2.3
     */
    public Client(){
        shop = new Shop();
    }

    /**
     * Constructor por parámetros
     * @since v2.3
     * @param shop Objeto de la clase shop que se copiará superficialmente en el atributo shop.
     */
    public Client(Shop shop){
        this.shop = shop;
    }

    /**
     * Implementación del método run de la interfaz Runnable. Simula el proceso de espera y compra de tarjetas gráficas.
     * El cliente intenta entrar a la tienda a comprar una tarjeta. Una vez consigue entrar, tarda 1 segundo en pagar
     * (tenía el dinero preparado) y una vez termina, deja la tienda libre.
     * @since v2.3
     * @see Shop#sellCard()
     * @see Shop#setISFULL(boolean)
     */
    public void run(){
        shop.sellCard();
        try {
            Thread.sleep(1000);
        }catch (InterruptedException e){
            Thread.currentThread().interrupt();
        }

        synchronized (shop){
            shop.setISFULL(false);
            shop.notifyAll();
        }
    }
}
