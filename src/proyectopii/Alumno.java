/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectopii;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class Alumno extends Persona {

    String FechaIngreaso;
    double NotaMedia;
    ArrayList<Integer> IDAsigSuperadas = new ArrayList<Integer>();
    
    ArrayList<Grupo> grupos = new ArrayList();
    ArrayList<Asignatura> matriculados = new ArrayList<Asignatura>();

   

    //CONSTRUCTORES
    public Alumno(String FechaIngreaso, double NotaMedia, String Nombre, String Apellidos, int ID, String FechaDeNacimiento, String Perfil, int[] IDAsigSuperada, String docrec) {
        super(Nombre, Apellidos, ID, FechaDeNacimiento, Perfil);
        this.FechaIngreaso = FechaIngreaso;
        this.NotaMedia = NotaMedia;
        for (int index = 0; index < IDAsigSuperada.length; index++) {
            IDAsigSuperadas.add(IDAsigSuperada[index]);
        }
        
    }
    public Alumno() {
    }
//Getters & Setters de Alumno
    public ArrayList<Integer> getIDAsigSuperadas() {
        return IDAsigSuperadas;
    }

    public ArrayList getGrupos() {
        return grupos;
    }

    public ArrayList<Asignatura> getMatriculados() {
        return matriculados;
    }
 public void setIDAsigSuperadas(ArrayList IDAsigSuperadas) {
        this.IDAsigSuperadas = IDAsigSuperadas;
    }

    

    public void setMatriculados(ArrayList matriculados) {
        this.matriculados = matriculados;
    }
    

    

    
    public void setNotaMedia(double NotaMedia) {
        this.NotaMedia = NotaMedia;
    }

    public void setAsigSuperadas(ArrayList AsigSuperadas) {
        this.IDAsigSuperadas = AsigSuperadas;
    }

    public void setFechaIngreaso(String FechaIngreaso) {
        this.FechaIngreaso = FechaIngreaso;
    }

    public double getNotaMedia() {
        return NotaMedia;
    }

    public String getFechaIngreaso() {
        return FechaIngreaso;
    }

   
//Métodos para comprobaciones propias de alumnos
    //Metodo para comprobar si el grupo existe ya
    public String compDocencia(String subcom) {
        for (int i = 0; i < matriculados.size(); i++) {
            if (matriculados.get(i).getId() == Integer.parseInt(subcom)) {
                return subcom;
            }

        }
        return "error";

    }
    //Mñetodo para calcular nota media
    public void setnewnotamedia(Double addnot) {
        this.NotaMedia = (((this.IDAsigSuperadas.size()) * this.NotaMedia + addnot) / (this.IDAsigSuperadas.size() + 1));
    }
//Crea grupos leídos a partir de una String que lo represente
    public void gruposmaker(String grupo) {
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
                            this.grupos.add(new Grupo(subtemp.getId(), grupobuscadoT.getIdgrupo(), "T", grupobuscadoT.getDia(), grupobuscadoT.getHora(), grupobuscadoT.getIdprof()));
                        }
                    }
                    if ("P".equals(docencias[1])) {
                        Grupo grupobuscadoP = GestionArrayList.BuscaGrupoID(subtemp.getGrouplist_p(), Integer.parseInt(docencias[2]));
                        if (grupobuscadoP == null) {
                            System.out.println("GRUPO BUSCADO NO EXISTENTE");
                        } else {
                            this.grupos.add(new Grupo(subtemp.getId(), grupobuscadoP.getIdgrupo(), "T", grupobuscadoP.getDia(), grupobuscadoP.getHora(), grupobuscadoP.getIdprof()));
                        }
                    }

                }
            } catch (NumberFormatException e) {
                //Si salta esta excepcion, el grupo está mal introducido, lo que invalida todo
            }
        }
    }
//Método para calcular la edad entre la fecha de nacimiento e ingreso 
    public static boolean calculoedad(String fechanac, String fechaing) {
        try {
            //Pasamos de String a fecha. Métodos para formatear String to Calendar
            SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy");
            Calendar calnac = Calendar.getInstance();
            Calendar caling = Calendar.getInstance();
            calnac.setTime(sdf.parse(fechanac));
            caling.setTime(sdf.parse(fechaing));
            //Obtenemos tiempo
            java.sql.Date timenac = new java.sql.Date(calnac.getTimeInMillis());
            java.sql.Date timeing = new java.sql.Date(caling.getTimeInMillis());
            //Calculamos diferencia
            double diff = timeing.getTime() - timenac.getTime();
//Constantes correspondientes a un periodo de 65 y 15 años
            final double sixtyfive = 2.0513088E12;//Por algún motivo no me dejaba usar otro formato
            final double fifteen = 4.733856E11;//por algún motivo no me dejaba usar otro tipo
            if ((diff < fifteen) || (diff > sixtyfive)) {
                return false;
            } else {
                return true;
            }
//En caso de que la fecha introducida tenga un formato no válido, salta un ParseExcepcion el cual se captura y envía una señal de error
        } catch (ParseException ex) {
            System.out.println("Fecha incorrecta");
            return false;
        }
    }
}

