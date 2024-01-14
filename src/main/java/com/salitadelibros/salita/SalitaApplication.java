package com.salitadelibros.salita;

import com.salitadelibros.salita.models.*;
import com.salitadelibros.salita.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;

import static com.salitadelibros.salita.models.Genero.NARRATIVA;

@SpringBootApplication
public class SalitaApplication {

	public static void main(String[] args) {
		SpringApplication.run(SalitaApplication.class, args);
	}
	@Bean
	public CommandLineRunner initData(LibroRepositorio libroRepositorio,
									  AutorRepositorio autorRepositorio,
									  CategoriaRepositorio categoriaRepositorio,
									  IlustradorRepositorio ilustradorRepositorio,
									  EditorialRepositorio editorialRepositorio,
									  LibroAutorRepositorio libroAutorRepositorio,
									  LibroIlustradorRepositorio libroIlustradorRepositorio,
									  LibroCategoriaRepositorio libroCategoriaRepositorio,
									  UsuarioRepositorio usuarioRepositorio){

		return (args -> {
			Libro libro1 = new Libro("aventuras de Abril", "2024", NARRATIVA, "98798879787");
			libroRepositorio.save(libro1);
			Editorial editorial1= new Editorial("AZ editores");
			editorialRepositorio.save(editorial1);
			Autor autor1 = new Autor("Dimas", "Cabrera");
			autorRepositorio.save(autor1);
			Ilustrador ilustrador1 = new Ilustrador("Agustina", "Di Mauro");
			ilustradorRepositorio.save(ilustrador1);
			Categoria categoria1 = new Categoria("Pizza");
			categoriaRepositorio.save(categoria1);
			LibroIlustrador libroIlustrador = new LibroIlustrador(libro1, ilustrador1);
			libroIlustradorRepositorio.save(libroIlustrador);
			LibroAutor libroAutor = new LibroAutor(libro1, autor1);
			libroAutorRepositorio.save(libroAutor);
			libro1.addEditorial(editorial1);
			libroRepositorio.save(libro1);
			LibroCategoria libroCategoria1 = new LibroCategoria(libro1, categoria1);
			libroCategoriaRepositorio.save(libroCategoria1);
	});
}

}