package com.salitadelibros.salita.controller;

import com.salitadelibros.salita.models.*;
import com.salitadelibros.salita.dtos.EditorialDTO;
import com.salitadelibros.salita.dtos.LibroDTO;
import com.salitadelibros.salita.repositories.*;
import com.salitadelibros.salita.services.LibroServicio;
import com.salitadelibros.salita.services.LibroServicioImpl;
import com.salitadelibros.salita.services.ServicioComun;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/libros")
public class LibroControlador {
    @Autowired
    private LibroServicio libroServicio;
    @Autowired
    private LibroRepositorio libroRepositorio;
    @Autowired
    private EditorialRepositorio editorialRepositorio;
    @Autowired
    private LibroServicioImpl libroServicioimpl;
    @Autowired
    private IlustradorRepositorio ilustradorRepositorio;
    @Autowired
    private AutorRepositorio autorRepositorio;
    @Autowired
    CategoriaRepositorio categoriaRepositorio;
    @Autowired
    private ServicioComun servicioComun;

    @GetMapping("/libros")
    public Map<String, Object> getLibros() {
        Map<String, Object> response = new HashMap<>();

        List<LibroDTO> libroDTOList = libroServicio.getLibros()
                .stream()
                .map(libro -> new LibroDTO((Libro) libro))
                .collect(Collectors.toList());

      //  List<String> categorias = libroServicio.getCategorias();

      //  response.put("libros", libroDTOList);
      //  response.put("categoriasexistentes", categorias);

        return response;
    }

    @RequestMapping("/editoriales")
    public List<EditorialDTO> getEditoriales() {
        List<Editorial> listaEditorial = editorialRepositorio.findAll();
        List<EditorialDTO> editorialDTOList = listaEditorial
                .stream()
                .map(editorial -> new EditorialDTO(editorial))
                .collect(Collectors.toList());
        return editorialDTOList;
    }

    @PostMapping("/guardarLibro")
    public ResponseEntity<String> saveOrUpdateLibro(@RequestBody Libro libro) {
        try {
            System.out.println("Solicitud recibida: " + libro);
            if (libro == null) {
                return ResponseEntity.badRequest().body("El libro no puede ser nulo");
            }
            if (libro.getTitulo() == null || libro.getTitulo().isEmpty()) {
                return ResponseEntity.badRequest().body("El título del libro es obligatorio");
            }
            if (libro.getGenero() == null) {
                return ResponseEntity.badRequest().body("El género del libro es obligatorio");
            }
            if (libro.getFechaDeEdicion() == null) {
                return ResponseEntity.badRequest().body("La fecha de edición del libro es obligatoria");
            }
            if (libro.getAutores() == null || libro.getAutores().isEmpty()) {
                return ResponseEntity.badRequest().body("Debes asociar al menos un autor al libro");
            }
            if (libro.getIlustradores() == null || libro.getIlustradores().isEmpty()) {
                return ResponseEntity.badRequest().body("Debes asociar al menos un ilustrador al libro");
            }

            // Guardar Titulo
            String titulo = libro.getTitulo();
            if(titulo == null){
                return ResponseEntity.badRequest().body("El TITULO del libro es obligatoria");
            }
            // Asignar titulo al libro
            libro.setTitulo(titulo);

            // Fecha de Edición
            LocalDate fechaDeEdicion = libro.getFechaDeEdicion();
            if (fechaDeEdicion == null) {
                return ResponseEntity.badRequest().body("La fecha de edición del libro es obligatoria");
            }
            // Asignar la fecha de edición al libro
            libro.setFechaDeEdicion(fechaDeEdicion);

            // Guardar Editorial
            Editorial editorial = libro.getEditorial();
            if (editorial != null) {
                if (editorial.getId() == null) {
                    // La editorial es nueva
                    servicioComun.saveOrUpdateEditorial(editorial);
                    editorial.addLibro(libro);
                }
            }
            // Asignar la editorial al libro
            libro.addEditorial(editorial);

            // Guardar el Género (Enum)
            Genero genero = libro.getGenero();
            libro.setGenero(genero);

            // Guardar las Categorias
          Set<Categoria> categorias = libro.getCategorias()
                  .stream()
                  .map(LibroCategoria::getCategoria)
                  .collect(Collectors.toSet());
            for (Categoria categoria : categorias){
                if(categoria.getId() == null){
                    servicioComun.saveOrUpdateCategoria(categoria);
                    libro.addLibroCategoria(new LibroCategoria(libro, categoria));
                }
            }

            // Gurdar ISBN
            String isbn = libro.getIsbn();
            if (isbn == null || isbn.isEmpty()){
                return ResponseEntity.badRequest().body("El ISBN es obligatorio");
            }
            // Asignar ISBN al libro
            libro.setIsbn(isbn);


            // Guardar Ilustradores
            Set<Ilustrador> ilustradores = libro.getIlustradores()
                    .stream()
                    .map(LibroIlustrador::getIlustrador)
                    .collect(Collectors.toSet());

            for (Ilustrador ilustrador : ilustradores) {
                if (ilustrador.getId() == null) {
                    servicioComun.saveOrUpdateIlustrador(ilustrador);
                    libro.addLibroIlustrador(new LibroIlustrador(libro, ilustrador));
                }
            }

            // Guardar Autores
            Set<Autor> autores = libro.getAutores()
                    .stream()
                    .map(LibroAutor::getAutor)
                    .collect(Collectors.toSet());

            for (Autor autor : autores) {
                if (autor.getId() != 0) {
                    servicioComun.saveOrUpdateAutor(autor);
                    libro.addLibroAutor(new LibroAutor(libro, autor));
                }
            }

            // Guardar libro
            servicioComun.saveOrUpdateLibro(libro);

            return ResponseEntity.ok("Libro guardado o actualizado exitosamente");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al procesar el libro: " + e.getMessage());
        }
    }

}
