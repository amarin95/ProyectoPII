/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectopii;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

/**
 *
 * @author Alberto
 */
public class Profesor extends Persona {

    public void setDocenciaimpartida(String docenciaimpartida) {
        this.docenciaimpartida = docenciaimpartida;
    }
    String departamento;
    String catlaboral;
    int dedicacionlaboral;//HORAS MÁXIMAS >0 <20
    String docenciaimpartida;
    int cargadocente;//horas
    int numcord = 0;
    ArrayList<Grupo> gruposimpartidos = new ArrayList();

    public int getCargadocente() {
        return cargadocente;
    }

    public void setCargadocente(int cargadocente) {
        this.cargadocente = cargadocente;
    }

    public String getDocenciaimpartida() {
        return docenciaimpartida;
    }

//Constructores
    public Profesor(String departamento, String catlaboral, int dedicacionlaboral, String nombre, String apellidos, int id, String fechadenacimiento, String perfil, String docencia) {
        super(nombre, apellidos, id, fechadenacimiento, perfil);
        this.departamento = departamento;
        this.catlaboral = catlaboral;
        this.dedicacionlaboral = dedicacionlaboral;
        this.docenciaimpartida = docencia;
    }

    public Profesor() {
    }
//Getters & Setters

    public void setCatlaboral(String catlaboral) {
        this.catlaboral = catlaboral;
    }

    public void setDedicacionlaboral(int dedicacionlaboral) {
        this.dedicacionlaboral = dedicacionlaboral;
    }

    public String getCatlaboral() {
        return catlaboral;
    }

    public int getDedicacionlaboral() {
        return dedicacionlaboral;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    //Método para convertir a String Docencia
    public String ToDocencia(int idasig, char tOp, int idgroup) {
        return (this.docenciaimpartida + Integer.toString(idasig) + " " + tOp + " " + Integer.toString(idgroup) + ";");
    }

    public void gruposmaker(String grupo, Profesor prof) {
        String[] grupos = grupo.split(";");
        for (int x = 0; x < grupos.length; x++) {
            String[] docencias = grupos[x].split(" ");
            try {
                Grupo tempgroup = new Grupo(Integer.parseInt(docencias[0]), docencias[1], Integer.parseInt(docencias[2]));
                Asignatura subtemp = GestionArrayList.buscaAsigID(Proyecto.sublist, Integer.parseInt(docencias[0]));

                if (subtemp == null) {
                    System.out.println("ASIGNATURA QUE INTENTA CREAR NO EXISTE");
                } else {
                    if ("T".equals(docencias[1])) {
                        Grupo grupobuscadoT = GestionArrayList.BuscaGrupoID(subtemp.getGrouplist_t(), Integer.parseInt(docencias[2]));
                        if (grupobuscadoT == null) {
                            System.out.println("GRUPO BUSCADO NO EXISTENTE");
                        } else {
                            this.gruposimpartidos.add(new Grupo(subtemp.getId(), grupobuscadoT.getIdgrupo(), "T", grupobuscadoT.getDia(), grupobuscadoT.getHora(), prof.getID()));
                            grupobuscadoT.setIdprof(prof.getID());
                            prof.setCargadocente(prof.getCargadocente() + subtemp.getDuraciont());//COMPROBAR SI MAYOR A 20/15
                        }
                    }
                    if ("P".equals(docencias[1])) {
                        Grupo grupobuscadoP = GestionArrayList.BuscaGrupoID(subtemp.getGrouplist_p(), Integer.parseInt(docencias[2]));
                        if (grupobuscadoP == null) {
                            System.out.println("GRUPO BUSCADO NO EXISTENTE");
                        } else {
                            grupobuscadoP.setIdprof(prof.getID());
                            prof.setCargadocente(prof.getCargadocente() + subtemp.getDuracionp());//COMPROBAR SI MAYOR A 20/15
                            this.gruposimpartidos.add(new Grupo(subtemp.getId(), grupobuscadoP.getIdgrupo(), "T", grupobuscadoP.getDia(), grupobuscadoP.getHora(), prof.getID()));
                        }
                    }

                }
            } catch (NumberFormatException e) {
                System.out.println("No hay grupos asignados");
            }
        }
    }
}
