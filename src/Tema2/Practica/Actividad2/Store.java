package Tema2.Practica.Actividad2;

/**
 * Clase Store para la práctica del tema 2.
 * @author Guillermo Martín Chippirraz
 * @version v2.2
 */
public class Store {
    private static final int MAX_PACK = 50;
    private static final int MIN_PACK = 0;
    private int packNum;
    private int totalVehicles;

    /**
     * Constructor por defecto
     * @since v2.2
     */
    public Store(){
        packNum = 0;
        totalVehicles = 0;
    }

    /**
     * Constructor por parámetros
     * @since v2.2
     * @see #setPackNum(int)
     * @see #setTotalVehicles(int)
     * @param packNum Entero que se envía al setter del atributo packNum
     * @param totalVehicles Entero que se envía al setter del atributo totalVehicles
     */
    public Store(int packNum, int totalVehicles){
        setPackNum(packNum);
        setTotalVehicles(totalVehicles);
    }

    /**
     * Getter del atributo packNum
     * @since v2.2
     * @return Entero almacenado en el atributo packNum
     */
    public int getPackNum(){
        return packNum;
    }

    /**
     * Setter del atributo packNum. Comprueba si el entero introducido por parámetros entra en la cantidad almacenable.
     * En caso de ser así, se almacena el entero en el atributo packNum. En caso contrario, se almacena el valor por
     * defecto y se envía un mensaje de error para cada caso.
     * @since 2.2
     * @param packNum Entero a validar y almacenar en el atributo packNum
     */
    public void setPackNum(int packNum){
        if (packNum >= MIN_PACK && packNum < MAX_PACK)
            this.packNum = packNum;
        else {
            this.packNum = 0;
            if (packNum > MAX_PACK)
                System.err.println("ERROR. EL NÚMERO DE PAQUETES NO DEBE SUPERAR LA CAPACIDAD MÁXIMA DEL ALMACÉN.");
            else if (packNum < MIN_PACK)
                System.out.println("ERROR. NO SE PUEDE ALMACENAR UN NÚMERO NEGATIVO DE PAQUETES.");
        }
    }

    /**
     * Getter del atributo totalVehicles
     * @since v2.2
     * @return Entero almacenado en el atributo totalVehicles
     */
    public int getTotalVehicles(){
        return totalVehicles;
    }

    /**
     * Setter del atributo totalVehicles. Comprueba si el entero introducido por parámetros es no negativo y almacena
     * dicho entero en el atributo totalVehicles en caso de ser así. En caso contrario, almacena el valor por defecto y
     * envía un mensaje de error.
     * @since v2.2
     * @param totalVehicles Entero a validar y almacenar en el atributo totalVehicles.
     */
    public void setTotalVehicles(int totalVehicles){
        if (totalVehicles >= 0)
            this.totalVehicles = totalVehicles;
        else {
            this.totalVehicles = 0;
            System.err.println("ERROR. NO PUEDE HABER UNA CANTIDAD NEGATIVA DE VEHÍCULOS.");
        }
    }
    /**
     * Método para agregar paquetes. Agrega un paquete al total almacenado sólo si la cantidad almacenada no iguala o
     * supera la máxima capacidad del almacén. En caso de no ser así, el método bloquea el hilo hasta que se reduzca la
     * cantidad de paquetes almacenados.
     * @since v2.2
     * @see #setPackNum(int)
     * @see #getPackNum()
     */
    public synchronized void agregarPaquete(){
        System.out.println("Comenzando proceso de agregado de paquete...");
        while (getPackNum() >= MAX_PACK){
            try {
                wait();
            }catch (InterruptedException e){
                Thread.currentThread().interrupt();
            }
        }
        setPackNum(getPackNum() + 1);
        System.out.println("Paquete agregado exitosamente");
        notifyAll();
    }

    /**
     * Método para transportar paquetes. Se bloquea el hilo en ejecución en caso de no haber paquetes en el almacén o en
     * caso de no haber vehículos para transportar paquetes hasta que haya al menos un vehículo y al menos un paquete.
     * @since v2.2
     * @see #getPackNum()
     * @see #getTotalVehicles()
     * @see #setPackNum(int)
     * @see #setTotalVehicles(int)
     */
    public synchronized void llevarPaquete(){
        System.out.println("Proceso de extracción de paquete iniciado...");
        while (getPackNum() <= MIN_PACK || getTotalVehicles() <= 0){
            try {
                wait();
            }catch (InterruptedException e){
                Thread.currentThread().interrupt();
            }
        }
        setPackNum(getPackNum() - 1);
        setTotalVehicles(getTotalVehicles() - 1);
        System.out.println("Paquete extraído con éxito.");
        notifyAll();
    }

    /**
     * Método toString. Sobreescrito de la clase Object.
     * @since v2.2
     * @return String con los valores almacenados en los atributos packNum y totalVehicles
     */
    public String toString(){
        return "Actualmente hay " + packNum + " paquetes almacenados y un total de " + totalVehicles + " vehículos " +
                "disponibles para su uso.";
    }
}
