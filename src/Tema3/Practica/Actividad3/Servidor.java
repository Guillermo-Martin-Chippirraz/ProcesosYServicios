package Tema3.Practica.Actividad3;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor implements Runnable{
    private Gestor gestor;
    private static final int PUERTO = 5555;
    private static final String HOSTNAME = "localhost/";

    public Servidor(){
        gestor = new Gestor();
    }
    public Servidor(Gestor gestor){
        this.gestor = gestor;
    }
    public Gestor getGestor(){
        return gestor;
    }

    public void run(){
        try(ServerSocket serverSocket = new ServerSocket()){
            InetSocketAddress add = new InetSocketAddress(HOSTNAME, PUERTO);
            serverSocket.bind(add);
            while (true){
                Socket cliente = serverSocket.accept();
                DataInputStream dis = new DataInputStream(cliente.getInputStream());
                DataOutputStream dos = new DataOutputStream(cliente.getOutputStream());
                System.out.println("El cliente " + dis.readUTF() + " se ha conectado correctamente.");
                dos.writeUTF("Por favor, introduzca su número de cuenta: ");
                CuentaBancaria cuenta = new CuentaBancaria();
                for (int i = 0; i < 3; i++){
                    String numCuenta = dis.readUTF();
                    cuenta = gestor.getByNum(numCuenta);
                    if (!cuenta.getNumeroDeCuenta().isEmpty()) dos.writeUTF("Bienvenido/a/e a su cuenta bancaria.");
                    else {
                        if (i < 2) dos.writeUTF("El número de cuenta no existe o no es válido, por favor, vuelva a intentarlo: ");
                        else {
                            dos.writeUTF("Demasiados intentos fallidos. Se sospecha un intento de usurpación de " +
                                    "identidad. Un furgón de la policía está en camino a su ubicación. ¡Puntúe su " +
                                    "experiencia con Revolut en la tienda de aplicaciones!");
                            cliente.close();
                        }
                    }
                }
                dos.writeUTF("Por favor, seleccione alguna de las siguientes operaciones\n" +
                        "1) CONSULTAR\n" +
                        "2) INGRESAR <cantidad>\n" +
                        "3) RETIRAR <cantidad>\n" +
                        "4) TRANSFERIR <cuentaDestino> <cantidad>");
                String opcion = dis.readUTF();
                System.out.println("Comando recibido: " + opcion);
                boolean estado = false;
                String response;
                String [] partes = opcion.split(" ");
                switch (partes[0].toUpperCase()){
                    case "CONSULTAR":
                        if (partes.length > 1){
                            dos.writeUTF("Uso: CONSULTAR");
                            cliente.close();
                        }
                        float saldo = gestor.consultar(cuenta);
                        if (saldo != -1F) estado = true;
                        dos.writeUTF("Hay " + saldo + " € disponibles.");
                        break;
                    case "INGRESAR":
                        if (partes.length > 2){
                            dos.writeUTF("Uso: INGRESAR <cantidad>");
                            cliente.close();
                        }
                        response = gestor.ingresar(cuenta, Float.parseFloat(partes[1]));
                        if (response.contains("ingresado")) estado = true;
                        dos.writeUTF(response);
                        break;
                    case "RETIRAR":
                        if (partes.length > 2){
                            dos.writeUTF("Uso: RETIRAR <cantidad>");
                            cliente.close();
                        }
                        response = gestor.retirar(cuenta, Float.parseFloat(partes[1]));
                        if (response.contains("retirado")) estado = true;
                        dos.writeUTF(response);
                        break;
                    case "TRANSFERIR":
                        if (partes.length > 3){
                            dos.writeUTF("Uso: TRANSFERIR <cuentaDestino> <cantidad>");
                            cliente.close();
                        }
                        CuentaBancaria cuentaDestino = gestor.getByNum(partes[1]);
                        response = gestor.transferir(cuenta, cuentaDestino, Float.parseFloat(partes[2]));
                        if (response.contains("transferido")) estado = true;
                        dos.writeUTF(response);
                        break;
                    default:
                        dos.writeUTF("El comando introducido no existe.");
                }

                if (estado) System.out.println("OK");
                else System.out.println("ERROR");
                cliente.close();
            }
        }catch(IOException e){
            e.printStackTrace();
        }finally{
            System.out.println("Conexión finalizada.");
        }
    }
}
