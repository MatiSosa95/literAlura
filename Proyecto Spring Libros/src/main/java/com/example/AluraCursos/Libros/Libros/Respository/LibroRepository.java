package com.example.AluraCursos.Libros.Libros.Respository;

import com.example.AluraCursos.Libros.Libros.Models.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LibroRepository extends JpaRepository<Libro, Long> {

    Libro findByTituloContainsIgnoreCase(String nombreLibro);

    @Query("SELECT l FROM Libro l WHERE l.idiomas=:idioma")
    List<Libro> librosPorIdioma(String idioma);

    @Query("SELECT l from Libro l ORDER BY l.numeroDeDescargas DESC LIMIT 5")
    List<Libro> findTop5ByOrderByNumeroDeDescargasDesc();
}
