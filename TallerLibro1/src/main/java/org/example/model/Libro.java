package org.example.model;

public class Libro {
    private String titulo;
    private String autor;
    private String editorial;
    private int anio;

    public Libro (String titulo, String autor, String editorial, int anio) {
        this.titulo = titulo;
        this.autor = autor;
        this.editorial = editorial;
        this.anio = anio;

    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getEditorial() {
        return editorial;
    }

    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public void mostrar_libro () {
        System.out.println("Titulo:" + this.titulo);
        System.out.println("Autor:" + this.autor);
        System.out.println("Editorial:" + this.editorial);
        System.out.println("Año de publicación:" + this.anio);
    }

}

