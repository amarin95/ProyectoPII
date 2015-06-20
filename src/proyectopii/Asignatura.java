/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectopii;

import java.util.ArrayList;

/**
 *
 * @author Alberto
 */
public class Asignatura {

    private String nombre;
    private int id;
    private int[] requisitos;
    private String siglas;
    private int coordinador;
    private int duraciont;
    private int duracionp;
    private String diast;
    private String diasp;
    public ArrayList<Grupo> grouplist_t = new <Grupo> ArrayList();
    public ArrayList<Grupo> grouplist_p = new <Grupo> ArrayList();
    public static int group_index;
    int alumnos_matriculados;

    

//Constructores
    public Asignatura() {
    }

    public Asignatura(String nombre, int id, int[] requisitos, String siglas, int coordinador) {
        this.nombre = nombre;
        this.id = id;
        this.requisitos = requisitos;
        this.siglas = siglas;
        this.coordinador = coordinador;
    }

    public Asignatura(String nombre, int id, int[] requisitos, String siglas, int coordinador, int duraciont, int duracionp, String diast, String diasp) {
        this.nombre = nombre;
        this.id = id;
        this.requisitos = requisitos;
        this.siglas = siglas;
        this.coordinador = coordinador;
        this.duraciont = duraciont;
        this.duracionp = duracionp;
        this.diast = diast;
        this.diasp = diasp;
    }

//Getters & Setters
    public int getAlumnos_matriculados() {
        return alumnos_matriculados;
    }
    public String getNombre() {
        return nombre;
    }

    public int getCoordinador() {
        return coordinador;
    }

    public int getDuraciont() {
        return duraciont;
    }

    public int getDuracionp() {
        return duracionp;
    }

    public String getDiast() {
        return diast;
    }

    public String getDiasp() {
        return diasp;
    }

    public int[] getRequisitos() {
        return requisitos;
    }

    public int getId() {
        return id;
    }

    public String getSiglas() {
        return siglas;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setSiglas(String siglas) {
        this.siglas = siglas;
    }

    public void setRequisitos(int[] requisitos) {
        this.requisitos = requisitos;
    }

    public void setCoordinador(int profesor) {
        this.coordinador = profesor;
    }

    public ArrayList<Grupo> getGrouplist_t() {
        return grouplist_t;
    }

    public static int getGroup_index() {
        return group_index;
    }

    

    //CREAR GRUPOS A PARTIR DE LAS STRINGS
  public  ArrayList<Grupo> getGrouplist_p() {
        return grouplist_p;
    }
  public Grupo groupfabric(String group) {
        String[] straux = group.split(" ");
        if ("".equals(straux[0])) {
            int id = Integer.parseInt(straux[1]);
            char dia = straux[2].charAt(0);
            String[] horaux = straux[3].split(";");
            int hora = Integer.parseInt(horaux[0]);
            Grupo x = new Grupo(this.getId(),id, dia, hora);
            return x;
        } else {

            int id = Integer.parseInt(straux[0]);
            char dia = straux[1].charAt(0);
            String[] horaux = straux[2].split(";");
            int hora = Integer.parseInt(horaux[0]);
            Grupo x = new Grupo(this.getId(),id, dia, hora);
            return x;
        }
    }
  
}
