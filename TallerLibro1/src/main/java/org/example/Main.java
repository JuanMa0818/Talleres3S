package org.example;

import org.example.model.Libro;

import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        List<Libro> libros = new ArrayList<>();
        leerArchivo(libros);

        for (Libro libro : libros) {
            System.out.println(libro.getTitulo());
        }

    }

    private static void leerArchivo(List<Libro> lista) {
        try {
            BufferedReader lector = new BufferedReader(new FileReader("D:\\TbLibros.txt"));
            String linea = "";
            while ((linea = lector.readLine()) != null) {
                String[] bloques = linea.split("\t");
                if (bloques.length == 4) {
                String titulo = bloques[0];
                String autor = bloques[1];
                String editorial = bloques[2];
                int anio = Integer.parseInt(bloques[3]);
                lista.add (new Libro (titulo,autor, editorial, anio));

            }
        }

        lector.close ();

        } catch (IOException exception) {
            System.out.println("Error al leer el archivo" + exception.getMessage());
        }
    }
}
