/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectopii;

import java.io.*;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

/**
 *
 * @author Alberto
 */

//Clase que manejará la escritura de ficheros txt.
public class Filewriter {

    String ruta1 = ("personas.txt");
    String ruta2 = ("asignaturas.txt");
    File archivoescrit = new File(ruta1);
    File archivosubj = new File(ruta2);
    BufferedWriter bf;

   //Introduce un profesor en el fichero
    public void insertaprofesor(Profesor newprof, String nombrefichero) throws IOException {
        
        PrintWriter salida = new PrintWriter(new FileWriter(nombrefichero, true));
        
        salida.println(newprof.getID());
        salida.println(newprof.getPerfil());
        salida.println(newprof.getNombre());
        salida.println(newprof.getApellidos());
        salida.println(newprof.getFechaDeNacimiento());
        salida.println(newprof.getCatlaboral());
        salida.println(newprof.getDepartamento());
        salida.println(newprof.getDedicacionlaboral());
        if (newprof.gruposimpartidos.isEmpty()) {
            salida.println("");
        } else {
            Grupo grupowriting = newprof.gruposimpartidos.get(0);
            salida.print(grupowriting.getIdasign() + " " + grupowriting.getTeoprac() + " " + grupowriting.getIdgrupo());
            for (int x = 1; x < newprof.gruposimpartidos.size(); x++) {
                grupowriting = newprof.gruposimpartidos.get(x);
                salida.print(grupowriting.getIdasign() + " " + grupowriting.getTeoprac() + " " + grupowriting.getIdgrupo());
            }
            salida.println("");
        }        
        
        salida.println("*");
        salida.close();
    }
    //Introduce un alumno en el fichero
    public void insertalumno(Alumno newchild) throws IOException {
        PrintWriter salida = new PrintWriter(new FileWriter(archivoescrit, true));
        
        salida.println(newchild.getID());
        salida.println(newchild.getPerfil());
        salida.println(newchild.getNombre());
        salida.println(newchild.getApellidos());
        salida.println(newchild.getFechaDeNacimiento());
        salida.println(newchild.getFechaIngreaso());
        if (newchild.IDAsigSuperadas.get(0) == 0) {
            salida.println("");
        } else {
            salida.print(newchild.IDAsigSuperadas.get(0));
            for (int x = 1; x < newchild.IDAsigSuperadas.size(); x++) {
                salida.print(", " + newchild.IDAsigSuperadas.get(x));
            }
            salida.println("");
        }
        //Se comprueba si hay atributos vacíos para así dejarlos en blanco
        salida.println(newchild.getNotaMedia());
        if (newchild.grupos.isEmpty()) {
            salida.println("");
        } else {
            Grupo grupowriting = newchild.grupos.get(0);
            salida.print(grupowriting.getIdasign() + " " + grupowriting.getTeoprac() + " " + grupowriting.getIdgrupo());
            for (int x = 1; x < newchild.grupos.size(); x++) {
                grupowriting = newchild.grupos.get(x);
                salida.print(grupowriting.getIdasign() + " " + grupowriting.getTeoprac() + " " + grupowriting.getIdgrupo());
            }
            salida.println("");
        }        
        salida.println("*");
        salida.close();

        
    }
    //Método para la escritura de asignaturas
    public void subwrite(ArrayList<Asignatura> sublist, String archivo) throws IOException {
        PrintWriter salida = new PrintWriter(new FileWriter(archivo));
        
        Asignatura subject = new Asignatura();
        
        for (int i = 0; i < sublist.size(); i++) {
            subject = sublist.get(i);
            
            salida.println(subject.getId());
            salida.println(subject.getNombre());
            salida.println(subject.getSiglas());
            if (subject.getCoordinador() == 0) {
                salida.println("");
            } else {
                salida.println(subject.getCoordinador());
            }
            if ("[0]".equals(Arrays.toString(subject.getRequisitos()))) {
                salida.println("");
                //También comprobamos que esté vacío o no
            } else {
                //Problema con el array, tendría que haber usado ArrayList, pero era demasiado tarde. Para quitar corchetes recurro a este método.
                String wait = GestionFicheros.EliminaCaracteres(Arrays.toString(subject.getRequisitos()), "]");
                String wait2 = GestionFicheros.EliminaCaracteres(wait, "[");
                salida.println(wait2);
            }
            if (subject.getDuraciont() == 0) {
                salida.println("");
            } else {
                salida.println(subject.getDuraciont());
            }
            if ((subject.getDuracionp() == 0)) {
                salida.println("");
            } else {
                salida.println(subject.getDuracionp());
            }
            salida.println(subject.getDiast());
            salida.println(subject.getDiasp());
            
            salida.println("*");
            
        }
        salida.close();
    }
    //Método para la escritura del archivo personas.txt
    public void outpersonas(ArrayList<Alumno> childlist, ArrayList<Profesor> proflist) throws IOException {
        PrintWriter salida = new PrintWriter(new FileWriter(archivoescrit));
        
        for (int i = 0; i < childlist.size(); i++) {
            this.insertalumno(childlist.get(i));
        }
        for (int j = 0; j < proflist.size(); j++) {
            this.insertaprofesor(proflist.get(j), "personas.txt");
        }
        salida.close();
    }
//Método para la escritura del archivo calendario.
    public static void outcalendar(String nombrefichero, Alumno child) throws IOException {
        if (child.getGrupos().isEmpty()) {//Comprobación de alumno
            System.out.println("Alumno sin asignaciones");
            Filewriter.errorout("OCALEN -- Alumno sin asignaciones");
        } else {
            PrintWriter salida = new PrintWriter(new FileWriter(nombrefichero));
            salida.println("dia;hora;tipogrupo;id grupo;asignatura;docente");
            ArrayList<Grupo> childgroup = child.getGrupos();
            //Ordenamos cronologicamente
            Collections.sort(childgroup, new Comparator<Grupo>() {//Método para ordenar de la clase Collections
                @Override
                public int compare(Grupo g1, Grupo g2) {
                    if (new Integer(g1.getDia()).compareTo(new Integer(g2.getDia())) == 0) {
                        return new Integer(g1.getHora()).compareTo(new Integer(g2.getHora()));

                    } else {
                        return new Integer(g1.getDia()).compareTo(new Integer(g2.getDia()));
                    }
                    
                }});
           //Escribimos los grupos comprobando que están o no vacíos además de diferenciarlos por T o P
            for (int i = 0; i < childgroup.size(); i++) {
                Grupo group = childgroup.get(i);
                Asignatura sign = GestionArrayList.buscaAsigID(Proyecto.sublist, group.getIdasign());
                if ("T".equals(group.getTeoprac())) {
                    Grupo signg = GestionArrayList.BuscaGrupoID(sign.grouplist_t, group.getIdgrupo());
                    if(GestionArrayList.buscaProfID(Proyecto.proflist,signg.getIdprof())==null){
                      salida.println(group.getDia() + ";" + group.getHora() + ";" + group.getTeoprac() + ";" + group.getIdgrupo() + ";" + group.getIdasign() + ";");  
                    }else{
                    salida.println(group.getDia() + ";" + group.getHora() + ";" + group.getTeoprac() + ";" + group.getIdgrupo() + ";" + group.getIdasign() + ";" + GestionArrayList.buscaProfID(Proyecto.proflist,signg.getIdprof()).getApellidos());}
                }
                    
                if ("P".equals(group.getTeoprac())) {
                    Grupo signg = GestionArrayList.BuscaGrupoID(sign.grouplist_p, group.getIdgrupo());
                    if(GestionArrayList.buscaProfID(Proyecto.proflist,signg.getIdprof())==null){
                        salida.println(group.getDia() + ";" + group.getHora() + ";" + group.getTeoprac() + ";" + group.getIdgrupo() + ";" + group.getIdasign() + ";");
                    }else
                    salida.println(group.getDia() + ";" + group.getHora() + ";" + group.getTeoprac() + ";" + group.getIdgrupo() + ";" + group.getIdasign() + ";" + GestionArrayList.buscaProfID(Proyecto.proflist,signg.getIdprof()).getApellidos());
                }
            }
            salida.close();
        }
    }
//Método para la escritura de errores
    public static void errorout(String error) throws IOException {
        PrintWriter avisos = new PrintWriter(new BufferedWriter(new FileWriter(("avisos.txt"), true)));
        avisos.println(error);
        avisos.flush();
        avisos.close();
        
    }
    
}
//COMPROBAR FALLOS POSIBLES
