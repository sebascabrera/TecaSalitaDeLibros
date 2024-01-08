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
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;
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
    public ResponseEntity<String> saveOrUpdateLibro(@RequestParam MultiValueMap<String, String> datosPrincipales) {
        try {
            Libro libro = new Libro(datosPrincipales);

            // Guardar libro y obtener el ID
            servicioComun.saveOrUpdateLibro(libro);

            // Devolver el ID del nuevo libro
            return ResponseEntity.ok().body("Libro guardado o actualizado exitosamente {\"id\":" + libro.getId() + "}");

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al procesar el libro: " + e.getMessage());
        }
    }

    @PostMapping("/asociarDatos")
    public ResponseEntity<String> asociarDatosLibro(@RequestParam Long libroId,
                                                    @RequestBody Editorial editorial,
                                                    @RequestBody Autor autor,
                                                    @RequestBody Ilustrador ilustrador) {
        try {
            // Obtener el libro por ID
            Optional<Libro> libroOptional = libroServicio.getLibro(libroId);

            if (libroOptional.isPresent()) {
                Libro libro = libroOptional.get();

                // Guardar Editorial
                libro.addEditorial(editorial);


                // Guardar Autores
                Set<Autor> autores = libro.getAutores()
                        .stream()
                        .map(LibroAutor::getAutor)
                        .collect(Collectors.toSet());
                for (Autor autorEnLoop : autores) {
                    if (autorEnLoop.getId() != 0) {
                        libro.addLibroAutor(new LibroAutor(libro, autor));
                    }
                }

                // Guardar Ilustradores
                Set<Ilustrador> ilustradores = libro.getIlustradores()
                        .stream()
                        .map(LibroIlustrador::getIlustrador)
                        .collect(Collectors.toSet());
                for (Ilustrador ilustradorEnLoop : ilustradores) {
                    if (ilustradorEnLoop.getId() == null) {
                        new LibroIlustrador(libro, ilustrador);
                        libro.addLibroIlustrador(new LibroIlustrador(libro, ilustrador));
                    }
                }
                return ResponseEntity.ok("Libro guardado o actualizado exitosamente");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró el libro con ID: " + libroId);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al procesar segunda etapa del libro: " + e.getMessage());
        }
    }
}
