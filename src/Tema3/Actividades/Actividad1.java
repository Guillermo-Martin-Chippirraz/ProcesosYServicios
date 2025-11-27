package Tema3.Actividades;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Actividad1 implements Serializable {
    private int id;
    private String nombre;
    private String apellido;
    private LocalDate fechaNacimiento;
    private float sueldo;
    private String numeroDeTelefono;

    public Actividad1(){
        id = 0;
        nombre = apellido = numeroDeTelefono = "";
        fechaNacimiento = null;
        sueldo = 0f;
    }

    public Actividad1(int id, String nombre, String apellido, String year, String month, String day, float sueldo, String numeroDeTelefono){
        setId(id);
        setNombre(nombre);
        setApellido(apellido);
        setFechaNacimiento(year, month, day);
        setSueldo(sueldo);
        setNumeroDeTelefono(numeroDeTelefono);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        nombre = nombre.toLowerCase();
        nombre.replace(nombre.charAt(0), (char) (nombre.charAt(0) - 40));
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        apellido = apellido.toLowerCase();
        apellido.replace(apellido.charAt(0), (char) (apellido.charAt(0) - 40));
        this.apellido = apellido;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String year, String month, String day) {
        int yearNum = Integer.parseInt(year);
        int dayNum = Integer.parseInt(day);
        int monthNum = -1;
        String [] meses = new String[]{"enero", "febrero", "marzo", "abril", "mayo", "junio", "julio", "agosto", "septiembre", "octubre", "noviembre", "diciembre"};
        switch (month.toLowerCase()){
            case "enero", "febrero", "marzo", "abril", "mayo", "junio", "julio", "agosto", "septiembre", "octubre", "noviembre", "diciembre":
                int i = -1;
                do {
                    i++;
                    if (month.toLowerCase().equals(meses[i]))
                        monthNum = i+1;
                }while (!month.toLowerCase().equals(meses[i]));
                break;
            case "1", "01", "2", "02", "3", "03", "4", "04", "5", "05", "6", "06", "7", "07", "8", "08", "9", "09", "10", "11", "12":
                monthNum = Integer.parseInt(month);
                break;
        }
        if (monthNum != -1){
            this.fechaNacimiento = LocalDate.of(yearNum, monthNum, dayNum);
        }
    }

    public float getSueldo() {
        return sueldo;
    }

    public void setSueldo(float sueldo) {
        sueldo = Float.parseFloat(String.valueOf((double) (Math.round(sueldo*100) / 100)));
        this.sueldo = sueldo;
    }

    public String getNumeroDeTelefono() {
        return numeroDeTelefono;
    }

    public void setNumeroDeTelefono(String numeroDeTelefono) {
        this.numeroDeTelefono = numeroDeTelefono;
    }
}
