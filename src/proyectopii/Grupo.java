/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectopii;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 *
 * @author Alberto
 */
public class Grupo {
    public static int groupnum=0;
    int idasign;
    int idgrupo;
    char dia;
    
    String teoprac;
    int hora;
    int idprof;
    
    
    
    
    
    
//Constructores
    public Grupo() {
    }
    
     public Grupo(int idasign,int idgrupo, char dia, int horas) {
        this.idgrupo = idgrupo;
       this.idasign= idasign;
        this.dia=dia;
        this.hora = horas;
    }
     public Grupo(int idasign ,String tipogrupo, int idgrupo){
         this.teoprac = tipogrupo;
         this.idgrupo = idgrupo;
         this.idasign = idasign;
     }
public Grupo(int idasign,int idgrupo,String teoprac,char dia,int hora){
    this.idasign = idasign;
    this.idgrupo = idgrupo;
    this.teoprac = teoprac;
    this.dia = dia;
    this.hora = hora;
}
public Grupo(int idasign,int idgrupo,String teoprac,char dia,int hora,int idprof){
    this.idasign = idasign;
    this.idgrupo = idgrupo;
    this.teoprac = teoprac;
    this.dia = dia;
    this.hora = hora;
    this.idprof=idprof;
}

    public void setIdprof(int idprof) {
        this.idprof = idprof;
    }
    public static int getGroupnum() {
        return groupnum;
    }

    public int getIdasign() {
        return idasign;
    }

    public char getDia() {
        return dia;
    }

    public int getHora() {
        return hora;
    }

    public int getIdprof() {
        return idprof;
    }
//Getters & Setters
   
    public int getIdgrupo() {
        return idgrupo;
    }
    public String getTeoprac() {
        return teoprac;
    }
    
    public void setIdgrupo(int idgrupo) {
        this.idgrupo = groupnum;
        groupnum++;
    }
    public void setTeoprac(String teoprac) {
        this.teoprac = teoprac;
    }
 
     
        
 
             }
