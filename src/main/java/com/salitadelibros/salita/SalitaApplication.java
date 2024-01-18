package com.salitadelibros.salita;

import com.salitadelibros.salita.models.*;
import com.salitadelibros.salita.repositories.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

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
									  LibroCategoriaRepositorio libroCategoriaRepositorio){

		return (args -> {
			Libro libro1 = new Libro("Aventuras De Abril", "2024", NARRATIVA, "98798879787");
			libroRepositorio.save(libro1);
			Editorial editorial1= new Editorial("AZ editores");
			Editorial editorial2= new Editorial("Gato de Hojalata");
			editorialRepositorio.save(editorial1);
			editorialRepositorio.save(editorial2);
			Autor autor1 = new Autor("Dimas", "Cabrera");
			Autor autor2 = new Autor("Cecilia", "Di Mauro");
			Autor autor3 = new Autor("Lourdes", "Pereyra");
			autorRepositorio.save(autor3);
			autorRepositorio.save(autor2);
			autorRepositorio.save(autor1);
			Ilustrador ilustrador1 = new Ilustrador("Agustina", "Di Mauro");
			ilustradorRepositorio.save(ilustrador1);
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