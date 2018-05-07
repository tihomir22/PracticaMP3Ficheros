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
import java.util.Scanner;
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
        String ruta, datos, titulo;
        Scanner teclado = new Scanner(System.in);
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
                                System.out.println("fichero encontrado" + "\t" + listaFich[i].getName());
                                if (obtenerFormato(listaFich[i].getName()).equalsIgnoreCase("3pm")) {
                                    try {
                                        System.out.println("Y ADEMAS FORMATO MP3, WOMBO COMBOOOOO!!!");
                                        skip = (listaFich[i].length() - 125);
                                        in = new DataInputStream(new FileInputStream(listaFich[i]));
                                        in.skip(skip);
                                        datos = in.readLine();
                                        titulo = datos.substring(0, 30);
                                        System.out.println(titulo);

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

                    break;

                case 3:

                    break;

            }
        } while (opcion != 0);

    }

    public static void mostrarMenu() {
        System.out.println("1.-Leer directorio");
        System.out.println("2.-Buscar canciones");
        System.out.println("3.-Mostrar catalogo");
        System.out.println("4.-Salir");

    }

    public static void recorrerSubdirectorio(String rutaOriginal, String nomCarpeta) {
        long skip2;
        String datos, titulo;
        DataInputStream in;
        DataOutputStream out;
        File nuevoDirectorio = new File(rutaOriginal + "/" + nomCarpeta);
        System.out.println("He entrado en " + nuevoDirectorio.getAbsolutePath());

        File[] listaArchivosSubdirectorio = nuevoDirectorio.listFiles();
        for (int i = 0; i < listaArchivosSubdirectorio.length; i++) {
            if (listaArchivosSubdirectorio[i].isDirectory() && !listaArchivosSubdirectorio[i].isHidden()) {
                System.out.println(listaArchivosSubdirectorio[i].getName());
                recorrerSubdirectorio(nuevoDirectorio.getAbsolutePath(), listaArchivosSubdirectorio[i].getName());
            }
            if (listaArchivosSubdirectorio[i].isFile()) {
                System.out.println("fichero encontrado" + "\t" + listaArchivosSubdirectorio[i].getName());
                if (obtenerFormato(listaArchivosSubdirectorio[i].getName()).equalsIgnoreCase("3pm")) {
                    System.out.println("Y ADEMAS FORMATO MP3, WOMBO COMBOOOOO!!!");
                    skip2 = (listaArchivosSubdirectorio[i].length() - 125);
                    try {
                        in = new DataInputStream(new FileInputStream(listaArchivosSubdirectorio[i]));
                        in.skip(skip2);
                        datos = in.readLine();
                        titulo = datos.substring(0, 30);
                        System.out.println(titulo);

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

}
