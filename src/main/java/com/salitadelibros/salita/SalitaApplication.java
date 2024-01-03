package com.salitadelibros.salita;

import com.salitadelibros.salita.models.*;
import com.salitadelibros.salita.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;

@SpringBootApplication

public class SalitaApplication implements CommandLineRunner {
	@Autowired
	private LibroRepositorio libroRepository;

	@Autowired
	private AutorRepositorio autorRepository;

	@Autowired
	private IlustradorRepositorio ilustradorRepository;

	@Autowired
	private EditorialRepositorio editorialRepository;

	@Autowired
	private LibroAutorRepositorio libroAutorRepositorio;

	@Autowired
	private UsuarioRepositorio usuarioRepositorio;

	@Autowired
	PasswordEncoder passwordEncoder;
	public static void main(String[] args) {
		SpringApplication.run(SalitaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {


		// Crear una editorial
		/*Editorial editorial = new Editorial("Editorial XYZ");
		editorialRepository.save(editorial);

		// Crear un autor
		Autor autor = new Autor("Hector", "Diaz");
		autorRepository.save(autor);

		// Crear un ilustrador
		Ilustrador ilustrador = new Ilustrador("Mirtha", "Gonzalez");
		ilustradorRepository.save(ilustrador);

		// Crear un libro con autor, ilustrador y editorial
		Libro libro = new Libro("Renata", 25/05/1984, );
		libro.addLibroAutor(new LibroAutor(libro, autor));
		libro.addLibroIlustrador(new LibroIlustrador(libro, ilustrador));
		libroRepository.save(libro);

	Usuario usuario1 = new Usuario("Sebastian","sebasfedele@gmail.com", passwordEncoder.encode("17defebrero"));
	usuarioRepositorio.save(usuario1);*/
	}
}
