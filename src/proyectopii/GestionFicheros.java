/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectopii;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import static proyectopii.Proyecto.proflist;
import static proyectopii.Proyecto.sub_index;
import static proyectopii.Proyecto.sublist;

/**
 *
 * @author Alberto
 */
//Clase donde se realizan las funciones
public class GestionFicheros {
//Genera persona nueva dependiendo de si es profesor o alumno
    public static void nuevapersona(String linearead) throws IOException {
        String[] frag = linearead.split("\"");
        String[] fragaux = frag[0].split(" ");
        if (null != fragaux[1]) {
            switch (fragaux[1]) {
                case "profesor":
                    GestionFicheros.insertaprofesor(frag, fragaux[1]);
                    break;
                case "alumno":
                    GestionFicheros.insertalumno(frag, fragaux[1]);
                    break;
                default:
                    System.out.println("Perfil incorrecto");
                    Filewriter.errorout("IP -- Perfil incorrecto");
            }
        }

    }
//Inserta profesor, pasandole el objeto a escribir al Filewriter
    public static void insertaprofesor(String[] fragaux, String perfil) throws IOException {
        try {
            Filewriter n = new Filewriter();
            String[] fechaocat = fragaux[4].split(" ");//Divisiones
            if (GestionFicheros.Compruebafecha(fechaocat[1]) == false) {
                System.err.println("FECHA NO VALIDA");
                Filewriter.errorout("IP -- Fecha incorrecta");
                return;
            }//COMPROBAMOS SI LA FECHA ES CORRECTA

            String ded = GestionFicheros.EliminaCaracteres(fragaux[6], " ");//Elimina espacios
            if ((Integer.parseInt(ded) > 20) || (Integer.parseInt(ded) < 0)) {
                System.out.println("Numero horas incorrectas(>20 o <0)");
                Filewriter.errorout("IP -- Numero de horas incorrecto");
            } else if (("interino".equals(fechaocat[2]) && (Integer.parseInt(ded) > 15))) {
                System.out.println("A un interino se le asignan +15 horas");
                Filewriter.errorout("IP -- Numero de horas incorrecto");
            } else {
                Profesor NuevoProfesor = new Profesor(fragaux[5], fechaocat[2], Integer.parseInt(ded), fragaux[1], fragaux[3], Persona.getContador(), fechaocat[1], perfil, "");//Construcción del nuevo profesor
                Proyecto.proflist.add(NuevoProfesor);
            }
        } catch (Exception e) {
            System.err.println("ERROR INESPERADO");
            Filewriter.errorout("IP -- ERROR INESPERADO, SALTA EXCEPCION");

//añadimos al profesor al arraylist
//Consciente de que se puede añadir directamente en el add, se hizo así para depurar.
        } finally {
            return;
        }
    }
//Construye el alumno y se lo pasa al Filewriter
    public static void insertalumno(String[] fragaux, String perfil) throws IOException {
        Filewriter n = new Filewriter();

        int[] nada = {0};
        String[] rest = fragaux[4].split(" ");
        if ((GestionFicheros.Compruebafecha(rest[1]) || (GestionFicheros.Compruebafecha(rest[2]))) == false) {
            System.err.println("FECHA NO VALIDA");
            Filewriter.errorout("IP -- Fecha incorrecta");

        }//COMPROBAMOS SI LA FECHA ES CORRECTA
        else if (Alumno.calculoedad(rest[1], rest[2]) == false) {
            System.out.println("Rango de fechas errónea");
            Filewriter.errorout("IP -- Fecha de ingreso errónea");
        } else if ((Double.parseDouble(rest[3]) > 10) || (Double.parseDouble(rest[3]) < 5)) {
            System.err.println("Nota no válida");
            Filewriter.errorout("IP -- Nota media incorrecta");
        } else {
            Alumno NuevoAlumno = new Alumno(rest[2], Double.parseDouble(rest[3]), fragaux[1], fragaux[3], Persona.getContador(), rest[1], perfil, nada, "");
            Proyecto.childlist.add(NuevoAlumno);
        }
    }

    public static void asignacarga(String persona, String idasignatura, String tipogrupo, String idgrupo) throws IOException {
        Profesor auxprof = GestionArrayList.buscaProfID(Proyecto.proflist, Integer.parseInt(persona));
        if (auxprof==null){
          System.out.println("No se ha encontrado profesor");
          Filewriter.errorout("ACD -- Profesor inexistente");
        }
        Asignatura sign = GestionArrayList.buscaAsigID(Proyecto.sublist, Integer.parseInt(idasignatura));
       
        if (sign == null){
           
        System.out.println("Asignatura inexistente");
        Filewriter.errorout("ACD -- Asignatura inexistente");
        }else{
        Grupo group = null;
if(GestionArrayList.BuscaGrupoID(auxprof.gruposimpartidos, Integer.parseInt(idgrupo))!=null){
    System.out.println("Grupo ya asignado");
    Filewriter.errorout("ACD -- Grupo ya asignado");
}else{
        if ("T".equals(tipogrupo)) {
            group = GestionArrayList.BuscaGrupoID(sign.getGrouplist_t(), Integer.parseInt(idgrupo));
            if (group == null){
                System.out.println("Grupo inexistente");
                Filewriter.errorout("ACD -- Grupo inexistente");
            }
                
            else if ((auxprof.getCargadocente() + sign.getDuraciont()) > auxprof.getDedicacionlaboral()) {
                System.out.println("Máximo de horas alcanzado");

                Filewriter.errorout("ACD -- Horas asignables superior al máximo");

            } else {
                 if ((GestionFicheros.solape(group, auxprof.gruposimpartidos) == false)) {
            System.out.println("SOLAPE");
            Filewriter.errorout("ACD -- Se genera solape");
                }else{
                group.setIdprof(auxprof.getID());
                group.setTeoprac(tipogrupo);
                sign.grouplist_t.set(Asignatura.group_index, group);
                auxprof.setCargadocente(auxprof.getCargadocente() + sign.getDuraciont());
//si excede ERROR
                auxprof.gruposimpartidos.add(group);
                Proyecto.proflist.set(Proyecto.prof_index, auxprof);
            }
            }
        }
            else if ("P".equals(tipogrupo)) {
                group = GestionArrayList.BuscaGrupoID(sign.getGrouplist_p(), Integer.parseInt(idgrupo));
                if ((auxprof.getCargadocente() + sign.getDuracionp()) > auxprof.getDedicacionlaboral()) {
                    System.out.println("Máximo de horas alcanzado");

                    Filewriter.errorout("ACD -- Horas asignables superior al máximo");

                } else {
                    if ((GestionFicheros.solape(group, auxprof.gruposimpartidos) == false)) {
            System.out.println("SOLAPE");
            Filewriter.errorout("ACD -- Se genera solape");
                }else{
                    group.setIdprof(auxprof.getID());
                    group.setTeoprac(tipogrupo);
                    sign.grouplist_p.set(Asignatura.group_index, group);
                    auxprof.setCargadocente(auxprof.getCargadocente() + sign.getDuracionp());

                    auxprof.gruposimpartidos.add(group);
                    Proyecto.proflist.set(Proyecto.prof_index, auxprof);
                }
                }
            } else {
                System.out.println("ERROR TIPO GRUPO");
                Filewriter.errorout("ACD -- Tipo de grupo incorrecto");
            }

         

            

         
   

            
        }
}
    }
        
        
        
//Método de matriculación de alumnos
    public static void matricular(int idalum, int subid) throws IOException {//VOY POR AQUÍ
        Alumno child = GestionArrayList.buscaChildID(Proyecto.childlist, idalum);
        Asignatura subject = GestionArrayList.buscaAsigID(Proyecto.sublist, subid);
        if (child == null) {
            System.out.println("NO SE ENCUENTRA ALUMNO");
            Filewriter.errorout("MAT -- Alumno inexistente");
        } else if (subject == null) {
            System.out.println("NO SE ENCUENTRA ASIGNATURA");
            Filewriter.errorout("MAT -- Asignatura inexistente");

        } else if (child.matriculados.contains(subject)) {
            System.out.println("YA MATRICULADO");
            Filewriter.errorout("MAT -- Ya es alumno de la asignatura indicada");
        } else if (GestionFicheros.prerequisitos(subject, child) == false) {
            System.out.println("No cumple requisitos");
            Filewriter.errorout("MAT -- No cumple requisitos");
        } else {
            child.matriculados.add(subject);
            Proyecto.childlist.set(Proyecto.child_index, child);//Sustituye al alumno.
            subject.alumnos_matriculados++;//Suma uno al numero de alumnos matriculados

        }
    }

    public static void asignagrupo(String idalumno, String asignatura, String tipogrupo, String idgrupo) throws IOException {
        Alumno auxchild = GestionArrayList.buscaChildID(Proyecto.childlist, Integer.parseInt(idalumno));
        Asignatura sign = GestionArrayList.buscaAsigID(Proyecto.sublist, Integer.parseInt(asignatura));
        Grupo group = null;
        String top = "e";
        if ("T".equals(tipogrupo)) {
            group = GestionArrayList.BuscaGrupoID(sign.getGrouplist_t(), Integer.parseInt(idgrupo));
            top = "T";
        } else if ("P".equals(tipogrupo)) {
            group = GestionArrayList.BuscaGrupoID(sign.getGrouplist_p(), Integer.parseInt(idgrupo));
            top = "P";
        } else {
            System.out.println("TIPO INCORRECTO");
            Filewriter.errorout("AGRUPO -- Tipo de grupo incorrecto");
        }
        if (auxchild == null) {
            System.out.println("ID ALUMNO NO ENCONTRADO");
            Filewriter.errorout("AGRUPO -- Alumno inexistente");
//generar error
        } else if (sign == null) {
            System.out.println("ASIGNATURA NO ENCONTRADA");//generar error
            Filewriter.errorout("AGRUPO -- Asignatura inexistente");
        } else if (group == null) {
            System.out.println("No existe el grupo seleccionado");
            Filewriter.errorout("AGRUPO -- Grupo inexistente");
            //generar error
        } else if ("error".equals(auxchild.compDocencia(Integer.toString(sign.getId())))) {
            System.out.println("Alumno no matriculado");
            Filewriter.errorout("AGRUPO -- Alumno no matriculado");

        } else if (solape(group, auxchild.grupos) == false) {
            System.out.println("Se genera solape");
            Filewriter.errorout("AGRUPO -- Se genera solape");
        } else {
            group.setTeoprac(top);
            auxchild.getGrupos().add(group);
        }
    }

    public static void evaluar(String subid, String fichero) throws IOException {

        ArrayList childnotes = new ArrayList();
        childnotes = LecturaFicheros.evread(fichero);
        if(childnotes == null){
            return;
        }
        for (int x = 0; x < childnotes.size(); x++) {
            String strchild = (String) childnotes.get(x);
            String[] straux = strchild.split(" ");
            if (GestionArrayList.buscaChildID(Proyecto.childlist, Integer.parseInt(straux[0])) == null) {
                System.out.println("No se ha encontrado al alumno pedido");
                Filewriter.errorout("EVALUA -- Alumno inexistente" + "<" + straux[0] + "> linea " + (x + 1));
            } else if (GestionArrayList.buscaAsigID(sublist, Integer.parseInt(subid)) == null) {
                System.out.println("Asignatura inexistente");
                Filewriter.errorout("EVALUA -- Asignatura inexistente");
            } else {
                Alumno tempchild = GestionArrayList.buscaChildID(Proyecto.childlist, Integer.parseInt(straux[0]));
                String subject = tempchild.compDocencia(subid);
                if ("error".equals(subject)) {
                    System.out.println("ASIGNATURA NO MATRICULADA");
                    Filewriter.errorout("EVALUA -- Alumno no matriculado" + "<" + subid + "> linea:" + (x + 1));
                } else {
                    Double nota = Double.parseDouble(straux[1]);
                    if ((nota > 0) && (nota < 5)) {
                        tempchild.matriculados.remove(subject);
                        try{
                        tempchild.grupos.remove(GestionArrayList.BuscaGrupoIDsign(tempchild.getGrupos(), Integer.parseInt(subid)));
                        tempchild.grupos.remove(GestionArrayList.BuscaGrupoIDsign(tempchild.getGrupos(), Integer.parseInt(subid)));}
                        catch(Exception e){
                            
                        }
                    }
                    if ((nota >= 5) && (nota <= 10)) {
                       
                        //VER SI YA LA TIENE SUPERADA?, TENDRÍA QUE MATRICULARSE, Y NO PUEDE
                        tempchild.matriculados.remove(GestionArrayList.buscaAsigID(sublist, Integer.parseInt(subid)));
                        try{
                        tempchild.grupos.remove(GestionArrayList.BuscaGrupoIDsign(tempchild.getGrupos(), Integer.parseInt(subid)));
                         tempchild.grupos.remove(GestionArrayList.BuscaGrupoIDsign(tempchild.getGrupos(), Integer.parseInt(subid)));}
                        catch(Exception e){
                            
                        }
                        tempchild.setnewnotamedia(nota);
                        if (tempchild.IDAsigSuperadas.get(0) == 0) {
                            tempchild.IDAsigSuperadas.set(0, Integer.parseInt(subid));
                        } else {
                            tempchild.IDAsigSuperadas.add(Integer.parseInt(subid));
                        }
                    } else {
                        System.err.println("NOTA NO VÁLIDA");
                        Filewriter.errorout("EVALUA -- Nota incorrecta, linea: " + (x + 1));
                    }
                }
            }

        }

    }

    public static void calendario(String idchild, String fichnoum) throws IOException {
        Alumno auxchild = GestionArrayList.buscaChildID(Proyecto.childlist, Integer.parseInt(idchild));
        if (auxchild == null) {
            System.out.println("ALUMNO SOLICITADO NO ENCONTRADO");
            Filewriter.errorout("OCALEN -- Alumno inexistente");
        } else {
            Filewriter.outcalendar(fichnoum, auxchild);
        }

    }

    public static void proforden(String fichsalida) throws IOException {
        ArrayList<Profesor> titulares = new ArrayList();

        for (int x = 0; x < Proyecto.proflist.size(); x++) {
            Profesor auxprof = Proyecto.proflist.get(x);
            if ("titular".equals(auxprof.getCatlaboral())) {
                titulares.add(auxprof);
            }
        }
        if (titulares.isEmpty()) {
            System.out.println("No hay profesores titulares");
            Filewriter.errorout("OTIT -- No hay profesores titulares");
        } else {
            Collections.sort(titulares, new Comparator<Profesor>() {
                @Override
                public int compare(Profesor p1, Profesor p2) {
                    if (new Integer(p1.getCargadocente()).compareTo(new Integer(p2.getCargadocente())) == 0) {
                        return new Integer(p1.getID()).compareTo(new Integer(p2.getID()));

                    } else {
                        return new Integer(p1.getCargadocente()).compareTo(new Integer(p2.getCargadocente()));
                    }
                }

            });
            Filewriter n = new Filewriter();
            for (int y = 0; y < titulares.size(); y++) {
                n.insertaprofesor(titulares.get(y), fichsalida);
            }
        }
    }

    public static void suborden(String accion) throws IOException {
        if (GestionArrayList.matriculados() == false) {
            System.out.println("No hay alumnos matriculados");
            Filewriter.errorout("OASIG -- No hay ningún alumno matriculado");
        }

        Collections.sort(Proyecto.sublist, new Comparator<Asignatura>() {
            @Override
            public int compare(Asignatura p1, Asignatura p2) {
                if (new Integer(p1.getAlumnos_matriculados()).compareTo(new Integer(p2.getAlumnos_matriculados())) == 0) {
                    return new Integer(p1.getId()).compareTo(new Integer(p2.getId()));

                } else {
                    return new Integer(p1.getAlumnos_matriculados()).compareTo(new Integer(p2.getAlumnos_matriculados()));
                }
            }

        });
        Filewriter n = new Filewriter();
        n.subwrite(Proyecto.sublist, accion);
    }

    public static void AsignaCoord(String accion1, String accion2) throws IOException {
        Profesor auxprof = GestionArrayList.buscaProfID(proflist, Integer.parseInt(accion1));
        Asignatura sign = GestionArrayList.buscaAsigID(sublist, Integer.parseInt(accion2));
        if (auxprof == null) {
            System.out.println("ID PROFESOR NO ENCONTRADO");
            Filewriter.errorout("ACOORD -- Profesor Inexistente");//generar error

        } else if ("interino".equals(auxprof.getCatlaboral())) {
            System.out.println("Profesor no titular");
            Filewriter.errorout("ACOORD -- Profesor no titular");

        } else if (auxprof.numcord == 2) {
            System.out.println("Número máximo alcanzado");
            Filewriter.errorout("ACOORD -- Profesor ya es coordinador de 2 materias");

        } else if (sign == null) {
            System.out.println("ASIGNATURA NO ENCONTRADA");
            Filewriter.errorout("ACOORD -- Asignatura inexistente");//generar error

        } else {
            sign.setCoordinador(auxprof.getID());
            auxprof.numcord++;
            sublist.set(sub_index, sign);

        }
    }

    public static boolean solape(Grupo comp, ArrayList<Grupo> lista) {
        for (int x = 0; x < lista.size(); x++) {
            char dia = lista.get(x).getDia();
            int hora = lista.get(x).getHora();
            if ((comp.getDia() == dia) && (comp.getHora() == hora)) {
                return false;
            }
        }
        return true;
    }

    public static boolean prerequisitos(Asignatura sub, Alumno child) {
        List req = Arrays.asList(sub.getRequisitos());
        for (int x = 0; x < req.size(); x++) {
            int[] id =  (int[]) req.get(x);
           int[] cero = {0};
            if (id[0]== 0){
            return true;
            }
            
        
            if (child.IDAsigSuperadas.contains(id[x]) == false) {
                return false;
            }

        }
        return true;

    }

    public static String EliminaCaracteres(String s_cadena, String s_caracteres) {
        String nueva_cadena = "";
        Character caracter = null;
        boolean valido = true;

        /* Va recorriendo la cadena s_cadena y copia a la cadena que va a regresar,
         sólo los caracteres que no estén en la cadena s_caracteres */
        for (int i = 0; i < s_cadena.length(); i++) {
            valido = true;
            for (int j = 0; j < s_caracteres.length(); j++) {
                caracter = s_caracteres.charAt(j);

                if (s_cadena.charAt(i) == caracter) {
                    valido = false;
                    break;
                }
            }
            if (valido) {
                nueva_cadena += s_cadena.charAt(i);
            }
        }

        return nueva_cadena;
    }

    private static boolean Compruebafecha(String fechaocat) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy");
            Calendar cal = Calendar.getInstance();
            cal.setTime(sdf.parse(fechaocat));

            if ((cal.get(cal.YEAR) < 1950) || (cal.get(cal.YEAR) > 2020)) {
                return false;
            } else {
                return true;
            }
        } catch (ParseException ex) {
            System.out.println("Fecha incorrecta");
            return false;
        }
    }
}
