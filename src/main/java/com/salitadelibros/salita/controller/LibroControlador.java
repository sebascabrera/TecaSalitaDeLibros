package com.salitadelibros.salita.controller;

import com.salitadelibros.salita.models.LibroIlustrador;
import com.salitadelibros.salita.models.LibroAutor;
import com.salitadelibros.salita.dtos.EditorialDTO;
import com.salitadelibros.salita.dtos.LibroDTO;
import com.salitadelibros.salita.models.Autor;
import com.salitadelibros.salita.models.Editorial;
import com.salitadelibros.salita.models.Ilustrador;
import com.salitadelibros.salita.models.Libro;
import com.salitadelibros.salita.repositories.AutorRepositorio;
import com.salitadelibros.salita.repositories.EditorialRepositorio;
import com.salitadelibros.salita.repositories.IlustradorRepositorio;
import com.salitadelibros.salita.repositories.LibroRepositorio;
import com.salitadelibros.salita.services.LibroServicio;
import com.salitadelibros.salita.services.LibroServicioImpl;
import com.salitadelibros.salita.services.ServicioComun;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    private ServicioComun servicioComun;

    @GetMapping("/libros")
    public Map<String, Object> getLibros() {
        Map<String, Object> response = new HashMap<>();

        List<LibroDTO> libroDTOList = libroServicio.getLibros()
                .stream()
                .map(libro -> new LibroDTO((Libro) libro))
                .collect(Collectors.toList());

        List<String> categorias = libroServicio.getCategorias();

        response.put("libros", libroDTOList);
        response.put("categoriasexistentes", categorias);

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


            // Guardar Ilustradores
            Set<Ilustrador> ilustradores = libro.getIlustradores()
                    .stream()
                    .map(LibroIlustrador::getIlustrador)
                    .collect(Collectors.toSet());

            for (Ilustrador ilustrador : ilustradores) {
                if (ilustrador.getId() == null) {
                    ilustradorRepositorio.save(ilustrador);
                }
            }

            // Guardar Autores
            Set<Autor> autores = libro.getAutores()
                    .stream()
                    .map(LibroAutor::getAutor)
                    .collect(Collectors.toSet());

            for (Autor autor : autores) {
                if (autor.getId() != 0) {
                    autorRepositorio.save(autor);
                }
            }
            servicioComun.saveOrUpdateLibro(libro);
            return ResponseEntity.ok("Libro guardado o actualizado exitosamente");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al procesar el libro: " + e.getMessage());
        }
    }


}
