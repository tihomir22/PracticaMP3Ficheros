/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practicamp3ficheros;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author sportak
 */
public class PracticaMP3Ficheros {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int opcion;
        long skip;
        File ruta2;
        File subdirectorio;
        DataInputStream in;
        DataOutputStream out;
        File[] listaFich;
        String ruta, datos, nombre;
        Scanner teclado = new Scanner(System.in);

        File eliminar = new File("catalogo.csv");
        eliminar.deleteOnExit();
        do {
            mostrarMenu();
            opcion = teclado.nextInt();
            switch (opcion) {
                case 1:
                    System.out.println("Introduzca ruta absoluta a listar");
                    teclado.nextLine();
                    ruta = teclado.nextLine();
                    ruta2 = new File(ruta);
                    if (ruta2.exists()) {
                        listaFich = ruta2.listFiles();
                        for (int i = 0; i < listaFich.length; i++) {

                            if (listaFich[i].isDirectory()) {//si encontramos un directorio dentro de la ruta absoluta...          
                                recorrerSubdirectorio(ruta, listaFich[i].getName());
                            } else {
                                //System.out.println("fichero encontrado" + "\t" + listaFich[i].getName());
                                if (obtenerFormato(listaFich[i].getName()).equalsIgnoreCase("3pm")) {
                                    try {
                                        // System.out.println("Y ADEMAS FORMATO MP3, WOMBO COMBOOOOO!!!");
                                        skip = (listaFich[i].length() - 125);
                                        in = new DataInputStream(new FileInputStream(listaFich[i]));

                                        PrintWriter escritura = new PrintWriter(new FileOutputStream("catalogo.csv", true));
                                        in.skip(skip);
                                        datos = in.readLine();
                                        String titulo = datos.substring(0, 30);
                                        if (titulo.isEmpty()) {
                                            titulo = "DESCONOCIDO";
                                        }
                                        String artista = datos.substring(30, 60);
                                        if (artista.isEmpty()) {
                                            artista = "DESCONOCIDO";
                                        }
                                        String album = datos.substring(60, 90);
                                        if (album.isEmpty()) {
                                            album = "DESCONOCIDO";
                                        }
                                        String anyo = datos.substring(90, 94);
                                        if (anyo.isEmpty()) {
                                            anyo = "DESCONOCIDO";
                                        }
                                        String coment = datos.substring(94, 124);
                                        if (coment.isEmpty()) {
                                            coment = "DESCONOCIDO";
                                        }
                                        String genero = datos.substring(125);
                                        if (genero.isEmpty()) {
                                            genero = "DESCONOCIDO";
                                        }

                                        escritura.append(listaFich[i].getAbsolutePath() + ";" + listaFich[i].getParent() + ";" + titulo + ";" + artista + ";" + album + ";" + anyo + ";" + coment + ";" + genero + ";");
                                        escritura.close();

                                        System.out.println("Cancion " + titulo.trim() + " escrita exitosamente en fichero");
                                    } catch (FileNotFoundException ex) {
                                        Logger.getLogger(PracticaMP3Ficheros.class.getName()).log(Level.SEVERE, null, ex);
                                    } catch (IOException ex) {
                                        Logger.getLogger(PracticaMP3Ficheros.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                }
                            }
                        }
                    } else {
                        System.out.println("No existe");
                    }

                    break;

                case 2:
                    System.out.println("Introduzca nombre de cancion a buscar,SOLO EL NOMBRE, NI GRUPOS NI NOMBRES COMPLETOS, SOLO EL NOMBRE");
                    teclado.nextLine();
                    ruta = teclado.nextLine();
                    try {
                        buscarCancion(ruta);
                    } catch (FileNotFoundException ex) {
                        Logger.getLogger(PracticaMP3Ficheros.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    break;
                case 3: {
                    try {
                        buscarCancion("");
                    } catch (FileNotFoundException ex) {
                        Logger.getLogger(PracticaMP3Ficheros.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                break;

            }
        } while (opcion != 4);

    }

    public static void mostrarMenu() {
        System.out.println("1.-Leer directorio");
        System.out.println("2.-Buscar canciones");
        System.out.println("3.-Mostrar catalogo");
        System.out.println("4.-Salir");

    }

    public static void recorrerSubdirectorio(String rutaOriginal, String nomCarpeta) {
        long skip2;
        String datos;
        DataInputStream in;
        DataOutputStream out;
        File nuevoDirectorio = new File(rutaOriginal + "/" + nomCarpeta);
        //System.out.println("He entrado en " + nuevoDirectorio.getAbsolutePath());

        File[] listaArchivosSubdirectorio = nuevoDirectorio.listFiles();
        for (int i = 0; i < listaArchivosSubdirectorio.length; i++) {
            if (listaArchivosSubdirectorio[i].isDirectory() && !listaArchivosSubdirectorio[i].isHidden()) {
                //System.out.println(listaArchivosSubdirectorio[i].getName());
                recorrerSubdirectorio(nuevoDirectorio.getAbsolutePath(), listaArchivosSubdirectorio[i].getName());
            }
            if (listaArchivosSubdirectorio[i].isFile()) {
                // System.out.println("fichero encontrado" + "\t" + listaArchivosSubdirectorio[i].getName());
                if (obtenerFormato(listaArchivosSubdirectorio[i].getName()).equalsIgnoreCase("3pm")) {
                    skip2 = (listaArchivosSubdirectorio[i].length() - 125);
                    try {
                        in = new DataInputStream(new FileInputStream(listaArchivosSubdirectorio[i]));
                        in.skip(skip2);
                        datos = in.readLine();
                        PrintWriter escritura = new PrintWriter(new FileOutputStream("catalogo.csv", true));
                        String titulo = datos.substring(0, 30);
                        if (titulo.isEmpty()) {
                            titulo = "DESCONOCIDO";
                        }
                        String artista = datos.substring(30, 60);
                        if (artista.isEmpty()) {
                            artista = "DESCONOCIDO";
                        }
                        String album = datos.substring(60, 90);
                        if (album.isEmpty()) {
                            album = "DESCONOCIDO";
                        }
                        String anyo = datos.substring(90, 94);
                        if (anyo.isEmpty() || anyo.equalsIgnoreCase(" ")) {
                            anyo = "DESCONOCIDO";
                        }
                        String coment = datos.substring(94, 124);
                        if (coment.isEmpty()) {
                            coment = "DESCONOCIDO";
                        }
                        String genero = datos.substring(125);
                        if (genero.isEmpty()) {
                            genero = "DESCONOCIDO";
                        }
                        escritura.append(listaArchivosSubdirectorio[i].getAbsolutePath() + ";" + listaArchivosSubdirectorio[i].getParent() + ";" + titulo + ";" + artista + ";" + album + ";" + anyo + ";" + coment + ";" + genero + ";");
                        escritura.close();
                        System.out.println("Cancion " + titulo.trim() + "escrita exitosamente en archivo");

                    } catch (FileNotFoundException ex) {
                        Logger.getLogger(PracticaMP3Ficheros.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        Logger.getLogger(PracticaMP3Ficheros.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
            }
        }

    }

    public static String obtenerFormato(String nombre) {
        //System.out.println(nombre);
        String res = "";
        for (int i = nombre.length() - 1; i > 0; i--) {
            if (nombre.charAt(i) == '.') {
                break;
            } else {
                res += nombre.charAt(i);
            }
        }

        return res;
    }

    public static void buscarCancion(String nombre) throws FileNotFoundException {

        //Scanner sc = new Scanner(new File("catalogo.csv"));
        DataInputStream lectura = new DataInputStream(new FileInputStream(new File("catalogo.csv")));
        Vector palabras = new Vector();
        String linea = "";
        int i = 0, cInt = 0;
        while (cInt != -1) {
            try {
                cInt = lectura.read();
                if ((char) cInt == ';') {
                    linea = (linea.trim());
                    palabras.add(i, linea);
                    //System.out.println("Palabra num " + i + " agregada al vector" + linea);
                    i++;
                    linea = "";

                } else {
                    linea += ((char) cInt);
                }

            } catch (IOException ex) {
                Logger.getLogger(PracticaMP3Ficheros.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        try {
            if (!nombre.isEmpty()) {
                for (int j = 0; j < palabras.size(); j++) {

                    if (palabras.get(j).equals(nombre)) {
                        // System.out.println("Comparamos" + palabras.get(j + 2) + " con " + nombre);
                        System.out.println("************************************");
                        for (int k = 7; k > 0; k--) {
                            System.out.println(palabras.get(j));
                            j++;
                        }
                        System.out.println("************************************");
                        break;
                    }
                }
            } else {
                for (int j = 0; j < palabras.size(); j++) {
                    System.out.println("************************************");
                    for (int k = 7; k > 0; k--) {
                        System.out.println(palabras.get(j));
                        j++;
                    }
                    System.out.println("************************************");
                }
            }
            lectura.close();
        } catch (Exception e) {
            System.out.println("Informacion sobre el archivo");
        }
    }
}
