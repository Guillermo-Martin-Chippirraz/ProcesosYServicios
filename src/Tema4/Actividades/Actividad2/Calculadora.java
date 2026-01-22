package Tema4.Actividades.Actividad2;

public class Calculadora {
    public double suma(double sumando1, double sumando2){
        return sumando1 + sumando2;
    }

    public double diferencia(double minuendo, double sustraendo){
        return minuendo - sustraendo;
    }

    public double producto(double factor1, double factor2) {
        return factor1*factor2;
    }

    public Double division(double dividendo, double divisor) {
        try{
            return dividendo/divisor;
        } catch (Exception e){
            System.out.println("No se puede dividir entre cero");
            return null;
        }
    }

    public double modulo(double dividendo, double divisor) {
        try {
            return dividendo%divisor;
        } catch (Exception e) {
            System.out.println("No se puede dividir entre cero");
            return dividendo;
        }
    }

    public double potencia(double base, double exponente) {
        return Math.pow(base, exponente);
    }

    public double raiz(double radicando, double indice) {
        double potencia = 1/indice;
        return Math.pow(radicando, potencia);
    }

    public Double logaritmo(double base, double operando) {
        try {
            return Math.log(base)/Math.log(operando);
        } catch (Exception e) {
            System.out.println("No se puede extraer el logaritmo de un operando o base nulo");
            return null;
        }
    }
}
