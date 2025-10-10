public class Actividad1 extends Thread{
    private int[] datos;
    private int inicio, fin;
    private double promedio;
    private int cantidad;

    public Actividad1(int[] datos, int inicio, int fin){
        setDatos(datos);
        setInicio(inicio);
        setFin(fin);
        setCantidad(fin - inicio);
        setPromedio(calculaPromedio());
    }

    public int[] getDatos() {
        return datos;
    }

    public void setDatos(int[] datos) {
        this.datos = datos;
    }

    public int getInicio() {
        return inicio;
    }

    public void setInicio(int inicio) {
        this.inicio = inicio;
    }

    public int getFin() {
        return fin;
    }

    public void setFin(int fin) {
        this.fin = fin;
    }

    public double getPromedio() {
        return promedio;
    }

    public void setPromedio(double promedio) {
        this.promedio = promedio;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double calculaPromedio(){
        int suma = 0;

        for (int i = 0; i < cantidad; i++){
            suma += datos[i];
        }

        return (double) suma/cantidad;
    }
}
