package com.salitadelibros.salita.controller;

import com.salitadelibros.salita.dtos.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.salitadelibros.salita.models.*;
import com.salitadelibros.salita.repositories.*;
import com.salitadelibros.salita.services.LibroServicio;
import com.salitadelibros.salita.services.LibroServicioImpl;
import com.salitadelibros.salita.services.ServicioComun;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/libros")
public class LibroControlador {

    private static final Logger logger = LoggerFactory.getLogger(LibroControlador.class);

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
    @Autowired
    private LibroIlustradorRepositorio libroIlustradorRepositorio;
    @Autowired
    private LibroAutorRepositorio libroAutorRepositorio;

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
            String idlibro = String.valueOf(libro.getId());
            System.out.println("ID del nuevo libro: " + libro.getId());
            logger.info("Iniciando asociarDatosLibro...{}", libro.getId());
            // Devolver el ID del nuevo libro
            return ResponseEntity.status(HttpStatus.CREATED).body("libro creado" + libro.getId());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al procesar el libro: " + e.getMessage());
        }
    }
    @PostMapping("/asociarDatos")
    public ResponseEntity<Object> asociarDatos(@RequestBody LibroDTO libroDTO){
        try {
            // Obtener el libro por ID
            Optional<Libro> libroOptional = libroServicio.getLibro(libroDTO.getId());
            logger.info("Iniciando asociarDatosLibro...{}", libroOptional);
            // Guardar datos
            if (libroOptional.isPresent()) {
                Libro libro = libroOptional.get();
                logger.info("Iniciando asociarDatosLibro...{}", libro);
                // Guardar Editorial
                EditorialDTO editorialDTO = libroDTO.getEditorial();
                if (editorialDTO != null) {
                    Editorial editorial = new Editorial(editorialDTO.getNombreEditorial());
                    libro.addEditorial(editorial);
                }
                // Guardar Autores
                Set<LibroAutorDTO> autoresDTO = libroDTO.getAutores();
                for (LibroAutorDTO libroAutorDTO : autoresDTO.stream().collect(Collectors.toSet())) {
                    Autor autor = new Autor(libroAutorDTO.getAutorDTO().getNombreAutor(), libroAutorDTO.getAutorDTO().getApellidoAutor());
                    if (autor.getId() != 0) {
                        LibroAutor libroAutor = new LibroAutor();
                        libro.addLibroAutor(libroAutor);
                        libroAutor.setAutor(autor);
                        libroAutor.setLibro(libro);
                        logger.info("libroAutor...{}", libroAutor);
                    }
                }
                // Guardar Ilustradores
                Set<LibroIlustradorDTO> ilustradoresDTO = libroDTO.getIlustradores();
                for (LibroIlustradorDTO libroIlustradorDTO : ilustradoresDTO.stream().collect(Collectors.toSet())) {
                        Ilustrador ilustrador = new Ilustrador(libroIlustradorDTO.getIlustradorDTO().getNombreIlustrador()
                                , libroIlustradorDTO.getIlustradorDTO().getApellidoIlustrador());
                    if (ilustrador.getId() != 0) {
                        LibroIlustrador libroIlustrador = new LibroIlustrador();
                        libroIlustrador.setIlustrador(ilustrador);
                        libroIlustrador.setLibro(libro);
                        libro.addLibroIlustrador(libroIlustrador);
                        logger.info("libroIlustrador...{}", libroIlustrador);
                    }
                }
                // Guardar Categorias
                Set<LibroCategoriaDTO> categoriaDTO = libroDTO.getCategorias();
                for (LibroCategoriaDTO libroCategoriaDTO : categoriaDTO.stream().collect(Collectors.toSet())) {
                    Categoria categoria = new Categoria(libroCategoriaDTO.getCategoriaDTO().getPalabraCategoria());
                    if (categoria.getId() != null){
                        LibroCategoria libroCategoria = new LibroCategoria();
                        libroCategoria.setCategoria(categoria);
                        libroCategoria.setLibro(libro);
                        libro.addLibroCategoria(libroCategoria);
                        logger.info("libroIlustrador...{}", libroCategoria);
                    }
                }

                return ResponseEntity.ok("Libro guardado o actualizado exitosamente");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró el libro con ID: " + libroDTO.getId());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al procesar segunda etapa del libro: " + e.getMessage());
        }
    }

}

