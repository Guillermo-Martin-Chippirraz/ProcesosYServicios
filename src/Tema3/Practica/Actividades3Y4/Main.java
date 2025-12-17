package Tema3.Practica.Actividades3Y4;

public class Main {
    public static void main(String[] args) {
        Gestor gestor = new Gestor();
        gestor.getCuentas().add(new CuentaBancaria("Cuenta1" , 10F));
        gestor.getCuentas().add(new CuentaBancaria("Cuenta2" , 1000.27F));
        gestor.getCuentas().add(new CuentaBancaria("Cuenta3" , 0F));

        Servidor servidor = new Servidor(gestor);
        new Thread(servidor).start();

        for (int i = 1; i <= 3; i++){
            boolean admin;
            if (Math.random() > 0.5){
                admin = true;
            } else admin = false;
            new Thread(new Cliente("Cliente" + i, admin)).start();
        }
    }
}

