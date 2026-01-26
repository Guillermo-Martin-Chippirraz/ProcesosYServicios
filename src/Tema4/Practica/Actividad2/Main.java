package Tema4.Practica.Actividad2;

import Tema4.Practica.Actividad2.modelos.SensorInfo;
import Tema4.Practica.Actividad2.observadores.ClienteObservador;
import Tema4.Practica.Actividad2.sensores.SensorSimulado;
import Tema4.Practica.Actividad2.servidor.ServidorCentral;

public class Main {

    public static void main(String[] args) {

        String host = "localhost";
        int puerto = 5555;

        ServidorCentral servidor = new ServidorCentral(host, puerto);
        Thread ts = new Thread(servidor);
        ts.start();

        SensorInfo s1 = new SensorInfo("TEMP01", "temperatura", 2000);
        SensorInfo s2 = new SensorInfo("HUM01", "humedad", 3000);
        SensorInfo s3 = new SensorInfo("PRES01", "presion", 2500);

        Thread t1 = new Thread(new SensorSimulado(s1, host, puerto));
        Thread t2 = new Thread(new SensorSimulado(s2, host, puerto));
        Thread t3 = new Thread(new SensorSimulado(s3, host, puerto));

        t1.start();
        t2.start();
        t3.start();

        Thread o1 = new Thread(new ClienteObservador(host, puerto, "juanito", "1234"));
        Thread o2 = new Thread(new ClienteObservador(host, puerto, "mary", "abcd"));

        o1.start();
        o2.start();
    }
}

