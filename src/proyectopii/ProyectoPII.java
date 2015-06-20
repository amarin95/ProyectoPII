/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectopii;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
/**
 *
 * @author Alberto
 */

public class ProyectoPII {
static int contador_lineas=0;  public static ArrayList <Alumno> childlist = new <Alumno> ArrayList(); 
public static ArrayList <Profesor> proflist = new <Profesor> ArrayList();
public static ArrayList <Asignatura> sublist = new <Asignatura> ArrayList(); 
public static ArrayList <Grupo> grouplist = new <Grupo> ArrayList();
public static int sub_index;
public static int prof_index;

   
    public static void main(String[] args) throws IOException {
  //ARCHIVOS
       Filewriter asdf = new Filewriter();
       File prueba = new File("C:\\Users\\Alberto\\Documents\\NetBeansProjects\\prueba.txt");
       String archivoout = new String("C:\\Users\\Alberto\\Documents\\NetBeansProjects\\salida.txt");
       String subout = new String("C:\\Users\\Alberto\\Documents\\NetBeansProjects\\Asignaturas.txt");
       LecturaFicheros.subread(subout);
  //ESTABLECIMIENTO DE LA ID
       
       LecturaFicheros.outread(archivoout);
       int x=((LecturaFicheros.countLines(archivoout)-1)/8);
       Persona.setContador(x);
      
        System.err.println(x);
        
   //COMPROBACION DE BUCLE
        
        Scanner Line = new Scanner(prueba);
        
        
        while(Line.hasNext()){
            
     
        String linealect; 
        String linealeida;
        linealeida = LecturaFicheros.leerlinea("C:\\Users\\Alberto\\Documents\\NetBeansProjects\\prueba.txt");
        linealect = LecturaFicheros.compruebalinea(linealeida);
        String[] accion = linealect.split(" ");
        String sel = accion[0];
        switch(sel){
            case "InsertaPersona":
            case"insertapersona":
                    
                System.out.println("Case!");
                GestionFicheros.nuevapersona(linealect);
                System.out.println("uwot");
                continue;
            case "AsignaCoordinador": 
            case "asignacoordinador"://FALTA EL SI YA TIENE COORDINADOR.
                
                Profesor auxprof = GestionArrayList.buscaProfID(proflist, Integer.parseInt(accion[1]));
                Asignatura sign = GestionArrayList.buscaAsigID(sublist, Integer.parseInt(accion[2]));
                if(auxprof==null)
                    System.out.println("ID PROFESOR NO ENCONTRADO");//generar error
                else if(sign==null){
                    System.out.println("ASIGNATURA NO ENCONTRADA");//generar error
                   continue;
                }else{
                    sign.setCoordinador(auxprof.getID());
                    sublist.set(sub_index, sign);
                    asdf.subwrite(sublist);
                    continue;
                }
            case "AsignaCargaDocente":
            case "asignacargadocente":
                GestionFicheros.asignacarga(accion[1],accion[2],accion[3],accion[4]);
            case "Matricula":;
            case "AsignaGrupo":;
            case "EvaluarAsignatura":;
            case "EvaluarCalendarioClases":;
            case "OrdenarProfesoresPorCargaDocente":;
            case "OrdenarAsignaturas":;
                
            case"sigue":
                    ;
                
                
                
                
        }
        }
        }

    public ProyectoPII() {
        
    }
       }

    

    
//}


//Matricular= LISTO;
//NO EXISTE CONTROL DE ERRORES (AUN)
//Falta FILEWRITE DE ASIGNATURAS AL IGUAL QUE LA LECTURA.
