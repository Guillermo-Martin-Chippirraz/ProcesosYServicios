package Tema2.Actividades.Actividad8;

public class Monitor {
    private int[] numeros = new int[0];
    private int tam = 5;
    private int max = 10;

    private Monitor(){
        numeros = new int[tam];
    }

    public synchronized int consumir(){
        while (numeros.length == 0){
            try {
                wait();
            }catch (InterruptedException e){
                Thread.currentThread().interrupt();
            }
        }

        int numero = numeros[0];
        tam -= 1;
        int[] newNumeros = new int[tam];
        for (int i = 0; i < tam; i++){
            newNumeros[i] = numeros[i+1];
        }

        numeros = newNumeros;

        notifyAll();
        return numero;
    }

    public synchronized void producir(int num){
        while (numeros.length == max){
            try {
                wait();
            }catch (InterruptedException e){
                Thread.currentThread().interrupt();
            }
        }
        tam++;
        int[] newNumeros = new int[tam];
        for (int i = 0; i < tam - 1; i++){
            newNumeros[i] = numeros[i];
        }
        newNumeros[tam-1] = num;
        numeros = newNumeros;
        notifyAll();
    }
}
