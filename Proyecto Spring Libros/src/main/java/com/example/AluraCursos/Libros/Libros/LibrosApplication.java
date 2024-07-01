package com.example.AluraCursos.Libros.Libros;

import com.example.AluraCursos.Libros.Libros.Principal.Ppal;
import com.example.AluraCursos.Libros.Libros.Respository.IAutorRepository;
import com.example.AluraCursos.Libros.Libros.Respository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LibrosApplication implements CommandLineRunner {

	@Autowired
	private LibroRepository libroRepository;
	@Autowired
	private IAutorRepository autorRepository;

	public LibrosApplication(LibroRepository libroRepository, IAutorRepository autorRepository) {
		this.libroRepository = libroRepository;
		this.autorRepository = autorRepository;
	}

	public static void main(String[] args) {

		SpringApplication.run(LibrosApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Ppal principal= new Ppal(libroRepository,autorRepository);
		principal.mostrarMenu();
	}
}
