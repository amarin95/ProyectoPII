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
//Clase para el manejo de las ArrayList que actuan como contenedores de los objetos
public class GestionArrayList {
//Método que busca a un alumno por su ID, devuelve false (error) si no se encuentra
    public static Alumno buscaChildID(ArrayList<Alumno> Lista, int IDsearch) {
        for (int i = 0; i < Lista.size(); i++) {
            Alumno child = Lista.get(i);
            if (child.getID() == IDsearch) {
                Proyecto.child_index = Lista.indexOf(child);
                return child;
            };

        }
        return null;
    }
//Método para buscar profesor
    public static Profesor buscaProfID(ArrayList<Profesor> Lista, int IDsearch) {

        for (int i = 0; i < Lista.size(); i++) {
            Profesor prof = Lista.get(i);
            if (prof.getID() == IDsearch) {
                Proyecto.prof_index = Lista.indexOf(prof);
                return prof;
            }
        }
        return null;

    }
//Método de búsqueda de Asignatura por ID
    public static Asignatura buscaAsigID(ArrayList<Asignatura> Lista, int IDsearch) {

        for (int i = 0; i < Lista.size(); i++) {
            Asignatura sign = Lista.get(i);
            if (sign.getId() == IDsearch) {
                Proyecto.sub_index = Lista.indexOf(sign);
                return sign;
            }
        }
        return null;
    }
//Método de búsqueda de grupo por ID
    public static Grupo BuscaGrupoID(ArrayList<Grupo> Lista, int IDsearch) {

        for (int i = 0; i < Lista.size(); i++) {
            Grupo group = Lista.get(i);
            if (group.getIdgrupo() == IDsearch) {
                Asignatura.group_index = Lista.indexOf(group);
                
                return group;
            }
        }
        return null;
    }
    //Búsqueda de grupo por la ID de la asignatura
    public static Grupo BuscaGrupoIDsign(ArrayList<Grupo> Lista, int IDsearch) {

        for (int i = 0; i < Lista.size(); i++) {
            Grupo group = Lista.get(i);
            if (group.getIdasign() == IDsearch) {
                Asignatura.group_index = Lista.indexOf(group);
                
                return group;
            }
        }
        return null;
    }
//Método para comprobar si hay alumnos matriculados o no en esa asignatura
    public static boolean matriculados() {
        for (int x = 0; x < Proyecto.sublist.size(); x++) {
            if (Proyecto.sublist.get(x).getAlumnos_matriculados() != 0);
            return true;
        }
        return false;
    }
}
