/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectopii;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import static proyectopii.Proyecto.sublist;
import sun.misc.IOUtils;

public class LecturaFicheros {

    public static String compruebalinea(String cadena) throws FileNotFoundException, IOException {

        if (cadena == null) {
            System.out.println("FIN DE EJECUCION");
            System.exit(0);
        } else {
        }
        String[] aux = cadena.split(" ");

        if (cadena.startsWith("*")) {
            
            return "sigue";

        } else {
            

            return cadena;

            //} 
        }

    }

    public static String leerlinea(String archivo) throws FileNotFoundException, IOException {
        BufferedReader r = new BufferedReader(new FileReader(archivo));
        String cadena = new String();
        Proyecto.contador_lineas++;
        for (int i = 0; i < Proyecto.contador_lineas; i++) {

            cadena = r.readLine();

        }
        
        return cadena;
    }

    public static int countLines(String filename) throws IOException {
        InputStream is = new BufferedInputStream(new FileInputStream(filename));
        try {
            byte[] c = new byte[1024];
            int count = 0;
            int readChars = 0;
            boolean empty = true;
            while ((readChars = is.read(c)) != -1) {
                empty = false;
                for (int i = 0; i < readChars; ++i) {
                    if (c[i] == '\n') {
                        ++count;
                    }
                }
            }
            return (count == 0 && !empty) ? 1 : count;
        } finally {
            is.close();
        }
    }

    public static void outread(String filename) throws IOException {//Lee archivo salida y guarda en lista
        FileInputStream fis = null;
        BufferedReader reader = null;
        String[] straux = new String[9];
        try {

            fis = new FileInputStream(filename);
            reader = new BufferedReader(new InputStreamReader(fis));

            String line = reader.readLine();

            while (line != null) {
                if ("*".equals(line)) {
                    line = reader.readLine();
                    continue;
                } else {
                    for (int i = 0; i <= 8; i++) {

                        straux[i] = line;
                        line = reader.readLine();
                    }

                }
                try {
                    int[] idintfin = {0};
                    if (straux[1].contains("alumno")) {
                        if ("".equals(straux[6])) {

                        } else {
                            String cosa = GestionFicheros.EliminaCaracteres(straux[6], " ");
                            String[] idasig = cosa.split(",");
                            int[] idint = new int[idasig.length];
                            for (int i = 0; i < idasig.length; i++) {
                                idint[i] = Integer.parseInt(idasig[i]);
                                idintfin = idint;
                            }
                        }
                        Alumno alnew = new Alumno(straux[5], Double.parseDouble(straux[7]), straux[2], straux[3], Integer.parseInt(straux[0]), straux[4], straux[1], idintfin, straux[8]);
                        //matricular en grupos recibidos:
                        alnew.gruposmaker(straux[8]);
                        Proyecto.childlist.add(alnew);

                    } else if ("profesor".equals(straux[1])) {
                        Profesor profnew = new Profesor(straux[6], straux[5], Integer.parseInt(straux[7]), straux[2], straux[3], Integer.parseInt(straux[0]), straux[4], straux[1], straux[8]);

                        profnew.gruposmaker(straux[8], profnew);

                        Proyecto.proflist.add(profnew);

                    }
                } catch (NullPointerException e) {
                    System.out.println("Archivo vacío");
                }
            }
        } catch (FileNotFoundException ex) {
            System.out.println("Necesidad de crear archivo");
            File archivo = new File("personas.txt");
            BufferedWriter bw;
            bw = new BufferedWriter(new FileWriter(archivo));
            bw.write("Acabo de crear el fichero de texto.");
        }
    }

    public static void subread(String filename) throws FileNotFoundException, IOException {
        try {
            FileInputStream fis = null;
            BufferedReader reader = null;
            String[] straux = new String[9];
            fis = new FileInputStream(filename);
            reader = new BufferedReader(new InputStreamReader(fis));
            int x = 0, y = 0, z = 0;
            int[] prerrec;
            String line = reader.readLine();

            while (line != null) {
                if ("*".equals(line)) {
                    line = reader.readLine();
                    continue;
                } else {
                    for (int i = 0; i <= 8; i++) {

                        straux[i] = line;
                        line = reader.readLine();
                    }

                }//try{
                String[] auxsub = straux[4].split(", ");
                if ("".equals(auxsub)) {
                    int[] prereq = {0};
                } else {
                    int[] prereq = new int[auxsub.length];

                    for (int i = 0; i < auxsub.length; i++) {

                        try {
                            prereq[i] = Integer.parseInt(auxsub[i]);

                        } catch (NumberFormatException q) {

                        }
                    }

                    //******************************
                    try {
                        x = Integer.parseInt(straux[3]);
                    } catch (NumberFormatException q) {
                        x = 0;

                    }

                    try {

                        y = Integer.parseInt(straux[5]);
                    } catch (NumberFormatException w) {
                        y = 0;
                    }

                    try {
                        z = Integer.parseInt(straux[6]);

                    } catch (NumberFormatException p) {
                        z = 0;
                    }

                    //Problema en lectura de archivo. Lee `1 a veces, rehacer el archivo para solución
                    try {
                        Asignatura newsub = new Asignatura(straux[1], Integer.parseInt(straux[0]), prereq, straux[2], x, y, z, straux[7], straux[8]);
                        String[] temp_t = straux[7].split(";");
                        String[] temp_p = straux[8].split(";");
                        if ("".equals(temp_t[0])) {

                        } else {
                            for (int q = 0; q < temp_t.length; q++) {
                                Grupo group_t = newsub.groupfabric(temp_t[q]);
                                newsub.grouplist_t.add(group_t);
                            }
                        }
                        if ("".equals(temp_p[0])) {

                        } else {
                            for (int e = 0; e < temp_p.length; e++) {
                                Grupo group_p = newsub.groupfabric(temp_p[e]);
                                newsub.grouplist_p.add(group_p);
                            }
                        }
                        sublist.add(newsub);
                    } catch (NumberFormatException NF) {
                        System.err.println("Algo introducido no es válido");
                        System.out.println("NOTA: a veces falla al leer la primera linea, ya que lee ﻿1 por algun motivo");
                    }
                }

            }
        } catch (FileNotFoundException ex) {
            System.out.println("Necesidad de crear archivo");
            File archivo = new File("Asignaturas.txt");
            BufferedWriter bw;
            bw = new BufferedWriter(new FileWriter(archivo));
            bw.write("Acabo de crear el fichero de texto.");
        }
    }

    public static ArrayList<String> evread(String filename) throws IOException {
        try {
            BufferedReader r = new BufferedReader(new FileReader(filename));

            ArrayList<String> childnotes = new ArrayList<String>();
            String cadena = new String();
            int x = 0;
            while (x != 1) {
                cadena = r.readLine();

                if (cadena != null) {
                    childnotes.add(cadena);

                } else {
                    r.close();
                    break;
                }
            }
            return childnotes;
        } catch (FileNotFoundException ex) {
            System.out.println("Archivo con notas no encontrado");
            Filewriter.errorout("EVALU -- Fichero de notas inexistente");
            return null;
        }

    }
}
