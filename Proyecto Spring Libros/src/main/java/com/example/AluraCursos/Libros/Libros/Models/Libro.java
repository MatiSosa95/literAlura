package com.example.AluraCursos.Libros.Libros.Models;


import jakarta.persistence.*;

@Entity
@Table(name="libros")
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(unique = true)
    private String titulo;
    @ManyToOne
    private Autor autor;
    private String idiomas;
    private Double numeroDeDescargas;

    public Libro() {
    }

    public Libro(DatosLibros datosLibros, Autor datosAutor){
        this.titulo= datosLibros.titulo();
        this.autor= datosAutor;
        this.idiomas= datosLibros.idiomas().get(0);
        this.numeroDeDescargas= datosLibros.numeroDeDescargas();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public String getIdiomas() {
        return idiomas;
    }

    public void setIdiomas(String idiomas) {
        this.idiomas = idiomas;
    }

    public Double getNumeroDeDescargas() {
        return numeroDeDescargas;
    }

    public void setNumeroDeDescargas(Double numeroDeDescargas) {
        this.numeroDeDescargas = numeroDeDescargas;
    }

    @Override
    public String toString() {
        return """
                -----------LIBRO-------------
                Titulo: %s
                Autor: %s
                Idioma: %s
                Numero de Descargas: %s
                """.formatted(titulo, autor.getNombre(), idiomas, numeroDeDescargas);
    }
}
