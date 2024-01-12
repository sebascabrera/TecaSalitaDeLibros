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

@SpringBootApplication
public class SalitaApplication {

	public static void main(String[] args) {
		SpringApplication.run(SalitaApplication.class, args);
	}
	@Bean
	public CommandLineRunner initData(LibroRepositorio libroRepositorio,
									  AutorRepositorio autorRepositorio,
									  IlustradorRepositorio ilustradorRepositorio,
									  EditorialRepositorio editorialRepositorio,
									  LibroAutorRepositorio libroAutorRepositorio,
									  UsuarioRepositorio usuarioRepositorio){

		return (args -> {

			Editorial editorial= new Editorial("AZ editores");
			Autor autor = new Autor("Dimas", "Cabrera");
			Ilustrador ilustrador = new Ilustrador("Agustina", "Di Mauro");
			Categoria categoria = new Categoria("Pizza");
	});
}

}