package com.salitadelibros.salita;

import com.salitadelibros.salita.models.*;
import com.salitadelibros.salita.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import static com.salitadelibros.salita.models.Genero.*;

@SpringBootApplication
public class SalitaApplication {
	@Autowired
	PasswordEncoder passwordEncoder;

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
			Libro libro1 = new Libro("Tengo un monstruo en el bolsillo", "1999", NARRATIVA, "9781400000463");
			Libro libro2 = new Libro("Mar Preferido de los Piratas, El", "2024", POESIA, "9789504644569");
			Libro libro3 = new Libro("Toda Mafalda", "2024", NARRATIVA, "9789505156948");
			Libro libro4 = new Libro("Aventuras De Ceci", "2024", TEATRO, "98798879756");
			Libro libro5 = new Libro("Cuidado con el Perro", "2016", POESIA, "9789504644477");
			Libro libro6 = new Libro("La razon populista", "2009", NARRATIVA, "9789505576357");
			Libro libro7 = new Libro("¿Cuanta verdad necesita el hombre?", "2013",NARRATIVA, "9789876701792");
			Libro libro8 = new Libro("Venas abiertas de America Latina", "2015", NARRATIVA, "9789876295116");
			Libro libro9 = new Libro("Nueva Gramatica basica", "2009", NARRATIVA, "9788467034714");
			Libro libro10 = new	Libro("Hobbit", "2014", POESIA, "9789505470631");
			Libro libro11 = new	Libro("Memorias Del Aguila y del Jaguar", "2015", POESIA, "9789875668157");
			Libro libro12 = new	Libro("zonceras Argentinas y otras yerbas", "2011", NARRATIVA, "9789504926238");
			Libro libro13 = new	Libro("Historia de la filosofia medieval", "2014", POESIA, "9786071613929");
			Libro libro14 = new Libro("Cristianismo primitivo y paideia Griega","2012", NARRATIVA, "9789681620301");
			libroRepositorio.save(libro14);
			libroRepositorio.save(libro1);
			libroRepositorio.save(libro2);
			libroRepositorio.save(libro3);
			libroRepositorio.save(libro4);
			libroRepositorio.save(libro5);
			libroRepositorio.save(libro6);
			libroRepositorio.save(libro7);
			libroRepositorio.save(libro8);
			libroRepositorio.save(libro9);
			libroRepositorio.save(libro10);
			libroRepositorio.save(libro11);
			libroRepositorio.save(libro12);
			libroRepositorio.save(libro13);
			libroRepositorio.save(libro14);
			Editorial editorial1= new Editorial("AZ editores");
			Editorial editorial2= new Editorial("Gato de Hojalata");
			Editorial editorial3= new Editorial("Lo que leo");
			Editorial editorial4= new Editorial("Fondo de Cultura Económica");
			Editorial editorial5= new Editorial("Tusquets");
			Editorial editorial6= new Editorial("Siglo XXI");
			Editorial editorial7= new Editorial("Espasa");
			Editorial editorial8= new Editorial("Minotauro");
			Editorial editorial9= new Editorial("De Bolsillo");
			Editorial editorial10= new Editorial("Planeta");

			editorialRepositorio.save(editorial1);
			editorialRepositorio.save(editorial2);
			editorialRepositorio.save(editorial3);
			editorialRepositorio.save(editorial4);
			editorialRepositorio.save(editorial5);
			editorialRepositorio.save(editorial6);
			editorialRepositorio.save(editorial7);
			editorialRepositorio.save(editorial8);
			editorialRepositorio.save(editorial9);
			editorialRepositorio.save(editorial10);
			libro1.addEditorial(editorial1);
			libro2.addEditorial(editorial2);
			libro3.addEditorial(editorial2);
			libro4.addEditorial(editorial3);
			libro5.addEditorial(editorial3);
			libro6.addEditorial(editorial4);
			libro7.addEditorial(editorial5);
			libro8.addEditorial(editorial6);
			libro9.addEditorial(editorial7);
			libro10.addEditorial(editorial8);
			libro11.addEditorial(editorial9);
			libro12.addEditorial(editorial10);
			libro13.addEditorial(editorial4);
			libro14.addEditorial(editorial4);
			Autor autor1 = new Autor("Liliana ", "Cinetto");
			Autor autor2 = new Autor("Ricardo Jesus", "Mariño");
			Autor autor3 = new Autor("Liliana", "Bodoc");
			Autor autor4 = new Autor("Ernesto", "Laclau");
			Autor autor5 = new Autor("Rudiger", "Zafranski");
			Autor autor6 = new Autor("Eduardo", "Galeano");
			Autor autor7 = new Autor("Real Academia Española", "");
			Autor autor8 = new Autor("J.R.R", "Tolkien");
			Autor autor9 = new Autor("Isabel", "Allende");
			Autor autor10 = new Autor("Anibal", "Fernandez");
			Autor autor11 = new Autor("Mauricio", "Beuchot");
			Autor autor12 = new Autor("Jaeger", "Werner");
			autorRepositorio.save(autor3);
			autorRepositorio.save(autor2);
			autorRepositorio.save(autor1);
			autorRepositorio.save(autor4);
			autorRepositorio.save(autor5);
			autorRepositorio.save(autor6);
			autorRepositorio.save(autor7);
			autorRepositorio.save(autor8);
			autorRepositorio.save(autor9);
			autorRepositorio.save(autor10);
			autorRepositorio.save(autor11);
			autorRepositorio.save(autor12);
			LibroAutor libroAutor = new LibroAutor(libro5, autor3);
			LibroAutor libroAutor1 = new LibroAutor(libro1, autor1);
			LibroAutor libroAutor2 = new LibroAutor(libro2, autor2);
			LibroAutor libroAutor3 = new LibroAutor(libro4, autor1);
			LibroAutor libroAutor4 = new LibroAutor(libro3, autor2);
			LibroAutor libroAutor5 = new LibroAutor(libro6, autor4);
			LibroAutor libroAutor6 = new LibroAutor(libro7, autor5);
			LibroAutor libroAutor7 = new LibroAutor(libro8, autor6);
			LibroAutor libroAutor8 = new LibroAutor(libro9, autor7);
			LibroAutor libroAutor9 = new LibroAutor(libro10, autor8);
			LibroAutor libroAutor10 = new LibroAutor(libro11, autor9);
			LibroAutor libroAutor11 = new LibroAutor(libro12, autor10);
			LibroAutor libroAutor12 = new LibroAutor(libro13, autor11);
			LibroAutor libroAutor13 = new LibroAutor(libro14, autor12);
			LibroAutor libroAutor14 = new LibroAutor(libro14, autor11);
			libroAutorRepositorio.save(libroAutor);
			libroAutorRepositorio.save(libroAutor1);
			libroAutorRepositorio.save(libroAutor2);
			libroAutorRepositorio.save(libroAutor3);
			libroAutorRepositorio.save(libroAutor4);
			libroAutorRepositorio.save(libroAutor5);
			libroAutorRepositorio.save(libroAutor6);
			libroAutorRepositorio.save(libroAutor7);
			libroAutorRepositorio.save(libroAutor8);
			libroAutorRepositorio.save(libroAutor9);
			libroAutorRepositorio.save(libroAutor10);
			libroAutorRepositorio.save(libroAutor11);
			libroAutorRepositorio.save(libroAutor12);
			libroAutorRepositorio.save(libroAutor13);
			libroAutorRepositorio.save(libroAutor14);
			Ilustrador ilustrador1 = new Ilustrador("Agustina", "Di Mauro");
			Ilustrador ilustrador2 = new Ilustrador("Pablo", "Bernasconi");
			Ilustrador ilustrador3 = new Ilustrador("Abril", "Di Mauro");
			ilustradorRepositorio.save(ilustrador1);
			ilustradorRepositorio.save(ilustrador2);
			ilustradorRepositorio.save(ilustrador3);
			LibroIlustrador libroIlustrador = new LibroIlustrador(libro1, ilustrador2);
			LibroIlustrador libroIlustrador1 = new LibroIlustrador(libro5, ilustrador1);
			LibroIlustrador libroIlustrador2 = new LibroIlustrador(libro2, ilustrador2);
			LibroIlustrador libroIlustrador3 = new LibroIlustrador(libro5, ilustrador2);
			LibroIlustrador libroIlustrador4 = new LibroIlustrador(libro4, ilustrador2);
			LibroIlustrador libroIlustrador5 = new LibroIlustrador(libro3, ilustrador3);
			libroIlustradorRepositorio.save(libroIlustrador2);
			libroIlustradorRepositorio.save(libroIlustrador3);
			libroIlustradorRepositorio.save(libroIlustrador4);
			libroIlustradorRepositorio.save(libroIlustrador1);
			libroIlustradorRepositorio.save(libroIlustrador);
			libroIlustradorRepositorio.save(libroIlustrador5);
			Categoria categoria1 = new Categoria("Pizza");
			Categoria categoria2 = new Categoria("Aviones");
			Categoria categoria3 = new Categoria("Fantasía");
			Categoria categoria4 = new Categoria("Dinosaurios");
			Categoria categoria5 = new Categoria("Viajes");
			Categoria categoria6 = new Categoria("Escaleras");
			Categoria categoria7 = new Categoria("Filosofia Politica");
			Categoria categoria8 = new Categoria("Filosofia");
			Categoria categoria9 = new Categoria("Lengua");
			Categoria categoria10 = new Categoria("Epica");
			categoriaRepositorio.save(categoria1);
			categoriaRepositorio.save(categoria2);
			categoriaRepositorio.save(categoria3);
			categoriaRepositorio.save(categoria4);
			categoriaRepositorio.save(categoria5);
			categoriaRepositorio.save(categoria6);
			categoriaRepositorio.save(categoria7);
			categoriaRepositorio.save(categoria8);
			categoriaRepositorio.save(categoria9);
			categoriaRepositorio.save(categoria10);
			LibroCategoria libroCategoria1 = new LibroCategoria(libro1, categoria1);
			LibroCategoria libroCategoria2 = new LibroCategoria(libro1, categoria2);
			LibroCategoria libroCategoria3 = new LibroCategoria(libro1, categoria3);
			LibroCategoria libroCategoria4 = new LibroCategoria(libro1, categoria4);
			LibroCategoria libroCategoria5 = new LibroCategoria(libro2, categoria1);
			LibroCategoria libroCategoria6 = new LibroCategoria(libro6, categoria7);
			LibroCategoria libroCategoria7 = new LibroCategoria(libro7, categoria7);
			LibroCategoria libroCategoria8 = new LibroCategoria(libro8, categoria8);
			LibroCategoria libroCategoria9 = new LibroCategoria(libro8, categoria7);
			LibroCategoria libroCategoria10 = new LibroCategoria(libro9, categoria9);
			LibroCategoria libroCategoria11 = new LibroCategoria(libro10, categoria10);
			LibroCategoria libroCategoria12 = new LibroCategoria(libro11, categoria5);
			LibroCategoria libroCategoria13 = new LibroCategoria(libro11, categoria10);
			LibroCategoria libroCategoria14 = new LibroCategoria(libro13, categoria8);
			libroCategoriaRepositorio.save(libroCategoria1);
			libroCategoriaRepositorio.save(libroCategoria2);
			libroCategoriaRepositorio.save(libroCategoria3);
			libroCategoriaRepositorio.save(libroCategoria4);
			libroCategoriaRepositorio.save(libroCategoria5);
			libroCategoriaRepositorio.save(libroCategoria6);
			libroCategoriaRepositorio.save(libroCategoria7);
			libroCategoriaRepositorio.save(libroCategoria8);
			libroCategoriaRepositorio.save(libroCategoria9);
			libroCategoriaRepositorio.save(libroCategoria10);
			libroCategoriaRepositorio.save(libroCategoria11);
			libroCategoriaRepositorio.save(libroCategoria12);
			libroCategoriaRepositorio.save(libroCategoria13);
			libroCategoriaRepositorio.save(libroCategoria14);
			libroRepositorio.save(libro1);
			libroRepositorio.save(libro2);
			libroRepositorio.save(libro3);
			libroRepositorio.save(libro4);
			libroRepositorio.save(libro5);
			libroRepositorio.save(libro6);
			libroRepositorio.save(libro7);
			libroRepositorio.save(libro8);
			libroRepositorio.save(libro9);
			libroRepositorio.save(libro10);
			libroRepositorio.save(libro11);
			libroRepositorio.save(libro12);
			libroRepositorio.save(libro13);
			libroRepositorio.save(libro14);

	});
}

}