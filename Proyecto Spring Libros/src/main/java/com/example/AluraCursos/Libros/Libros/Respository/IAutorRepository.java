package com.example.AluraCursos.Libros.Libros.Respository;

import com.example.AluraCursos.Libros.Libros.Models.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IAutorRepository extends JpaRepository<Autor, Long> {

    Autor findByNombreContainingIgnoreCase(String nombreAutor);

    @Query("SELECT a from Autor a WHERE :anio BETWEEN a.fechaDeNacimiento AND a.fechaDeMuerte")
    List<Autor> autoresVivosPorAño(int anio);

}
