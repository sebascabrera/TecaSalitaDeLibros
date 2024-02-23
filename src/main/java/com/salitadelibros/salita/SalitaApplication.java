package com.salitadelibros.salita;

import com.salitadelibros.salita.models.*;
import com.salitadelibros.salita.repositories.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import static com.salitadelibros.salita.models.Genero.*;

@SpringBootApplication
public class SalitaApplication {

	public static void main(String[] args) {
		SpringApplication.run(SalitaApplication.class, args);
	}

	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
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
			Libro libro1 = new Libro("Tengo un monstruo en el bolsillo", "1999", NARRATIVA, "9781400000463");
			Libro libro2 = new Libro("Mar Preferido de los Piratas, El", "2024", POESIA, "9789504644569");
			Libro libro3 = new Libro("Toda Mafalda", "2024", NARRATIVA, "9789505156948");
			Libro libro4 = new Libro("Aventuras De Ceci", "2024", TEATRO, "98798879756");
			Libro libro5 = new Libro("Cuidado con el Perro", "2016", POESIA, "9789504644477");
			libroRepositorio.save(libro1);
			libroRepositorio.save(libro2);
			libroRepositorio.save(libro3);
			libroRepositorio.save(libro4);
			libroRepositorio.save(libro5);

			Editorial editorial1= new Editorial("AZ editores");
			Editorial editorial2= new Editorial("Gato de Hojalata");
			Editorial editorial3= new Editorial("lo que leo");
			editorialRepositorio.save(editorial1);
			editorialRepositorio.save(editorial2);
			editorialRepositorio.save(editorial3);

			Autor autor1 = new Autor("Liliana ", "Cinetto");
			Autor autor2 = new Autor("Ricardo Jesus", "Mariño");
			Autor autor3 = new Autor("Liliana", "Bodoc");
			autorRepositorio.save(autor3);
			autorRepositorio.save(autor2);
			autorRepositorio.save(autor1);

			Ilustrador ilustrador1 = new Ilustrador("Agustina", "Di Mauro");
			Ilustrador ilustrador2 = new Ilustrador("Pablo", "Bernasconi");
			ilustradorRepositorio.save(ilustrador1);
			ilustradorRepositorio.save(ilustrador2);

			Categoria categoria1 = new Categoria("Pizza");
			Categoria categoria2 = new Categoria("Aviones");
			Categoria categoria3 = new Categoria("Fantasía");
			Categoria categoria4 = new Categoria("Dinosaurios");
			Categoria categoria5 = new Categoria("Viajes");
			Categoria categoria6 = new Categoria("Escaleras");
			categoriaRepositorio.save(categoria1);
			categoriaRepositorio.save(categoria2);
			categoriaRepositorio.save(categoria3);
			categoriaRepositorio.save(categoria4);
			categoriaRepositorio.save(categoria5);
			categoriaRepositorio.save(categoria6);

			LibroCategoria libroCategoria1 = new LibroCategoria(libro1, categoria1);
			LibroCategoria libroCategoria2 = new LibroCategoria(libro1, categoria2);
			LibroCategoria libroCategoria3 = new LibroCategoria(libro1, categoria3);
			LibroCategoria libroCategoria4 = new LibroCategoria(libro1, categoria4);
			libroCategoriaRepositorio.save(libroCategoria1);
			libroCategoriaRepositorio.save(libroCategoria2);
			libroCategoriaRepositorio.save(libroCategoria3);
			libroCategoriaRepositorio.save(libroCategoria4);
			LibroCategoria libroCategoria5 = new LibroCategoria(libro2, categoria1);
			libroCategoriaRepositorio.save(libroCategoria5);

			LibroIlustrador libroIlustrador = new LibroIlustrador(libro1, ilustrador2);
			LibroIlustrador libroIlustrador1 = new LibroIlustrador(libro5, ilustrador1);
			LibroIlustrador libroIlustrador2 = new LibroIlustrador(libro2, ilustrador2);
			LibroIlustrador libroIlustrador3 = new LibroIlustrador(libro3, ilustrador2);
			LibroIlustrador libroIlustrador4 = new LibroIlustrador(libro4, ilustrador2);
			libroIlustradorRepositorio.save(libroIlustrador2);
			libroIlustradorRepositorio.save(libroIlustrador3);
			libroIlustradorRepositorio.save(libroIlustrador4);
			libroIlustradorRepositorio.save(libroIlustrador1);
			libroIlustradorRepositorio.save(libroIlustrador);

			LibroAutor libroAutor = new LibroAutor(libro5, autor3);
			LibroAutor libroAutor1 = new LibroAutor(libro1, autor1);
			LibroAutor libroAutor2 = new LibroAutor(libro2, autor2);
			LibroAutor libroAutor3 = new LibroAutor(libro4, autor1);
			LibroAutor libroAutor4 = new LibroAutor(libro3, autor2);
			libroAutorRepositorio.save(libroAutor);
			libroAutorRepositorio.save(libroAutor1);
			libroAutorRepositorio.save(libroAutor2);
			libroAutorRepositorio.save(libroAutor3);
			libroAutorRepositorio.save(libroAutor4);

			libro1.addEditorial(editorial1);
			libro2.addEditorial(editorial2);
			libro3.addEditorial(editorial2);
			libro4.addEditorial(editorial3);
			libro5.addEditorial(editorial3);

			libroRepositorio.save(libro1);
			libroRepositorio.save(libro2);
			libroRepositorio.save(libro3);
			libroRepositorio.save(libro4);
			libroRepositorio.save(libro5);
			Usuario usuario1 = new Usuario("Sebastian Cabrera", "sebasfedele@gmai.com", passwordEncoder().encode("!17Defebrero"));
			usuarioRepositorio.save(usuario1);
			usuario1.addRol(Roles.ADMIN);
			usuarioRepositorio.save(usuario1);
	});
}

}