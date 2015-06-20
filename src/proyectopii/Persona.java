/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectopii;

import java.util.Date;

/**
 *
 * @author Alberto
 */
public abstract class Persona {
    
    String nombre;
    String apellidos;
    int id;
    String fechadenacimiento;
    String perfil;
    static int contador = 0;
    static int contador_inicial;

    
    
    //Constructores
    public Persona(String nombre, String apellidos, int id, String fechadenacimiento, String perfil) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.id = id;
        this.fechadenacimiento = fechadenacimiento;
        this.perfil = perfil;
    }
    public Persona() {
    }
    //Getters & Setters
    public String getApellidos() {
        return apellidos;
    }
    public String getNombre() {
        return nombre;
    }
    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }
    public void setFechaDeNacimiento(String fechadenacimiento) {
        this.fechadenacimiento = fechadenacimiento;
    }
    public int getID() {
        return id;
    }
    public String getFechaDeNacimiento() {
        return fechadenacimiento;
    }
    public String getPerfil() {
        return perfil;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public void setPerfil(String perfil) {
        this.perfil = perfil;
    }
    public void setID(int id) {
        this.id = id;
    }

    public static void setContador(int contador) {
        Persona.contador = contador;
    }

    public static int getContador() {
        Persona.contador ++;
        return contador;
    }
  
    
    
    
    
}
