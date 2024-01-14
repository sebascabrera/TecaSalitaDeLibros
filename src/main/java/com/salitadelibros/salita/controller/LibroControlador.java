package com.salitadelibros.salita.controller;

import com.salitadelibros.salita.dtos.*;

import com.salitadelibros.salita.services.*;
import com.salitadelibros.salita.services.services.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.salitadelibros.salita.models.*;
import com.salitadelibros.salita.repositories.*;
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
    private EditorialRepositorio editorialRepositorio;
    @Autowired
    private EditorialServicio editorialServicio;
    @Autowired
    private ServicioComun servicioComun;
    @Autowired
    private AutorServicio autorServicio;
    @Autowired
    private LibroAutorServicio libroAutorServicio;
    @Autowired
    private IlustradorServicio ilustradorServicio;
    @Autowired
    LibroIlustradorServicio libroIlustradorServicio;
    @Autowired
    CategoriaServicio categoriaServicio;
    @Autowired
    LibroCategoriaServicio libroCategoriaServicio;


    @GetMapping("/libros")
    public List<LibroDTO> getLibros() {

        List<Libro> libroLista = libroServicio.getLibros();
        List<LibroDTO> libroDTOLista = libroLista
                .stream()
                .map(libro -> new LibroDTO(libro))
                .collect(Collectors.toList());

        return libroDTOLista;
    }

    @RequestMapping("/editoriales")
    public Set<EditorialDTO> getEditoriales() {
        List<Editorial> listaEditorial = editorialRepositorio.findAll();
        Set<EditorialDTO> editorialDTOList = listaEditorial
                .stream()
                .map(editorial -> new EditorialDTO(editorial))
                .collect(Collectors.toSet());
        return editorialDTOList;
    }

    @PostMapping("/guardarLibro")
    public ResponseEntity<String> saveOrUpdateLibro(@RequestParam MultiValueMap<String, String> datosPrincipales) {
        try {
            com.salitadelibros.salita.models.Libro libro = new com.salitadelibros.salita.models.Libro(datosPrincipales);

            // Guardar libro y obtener el ID
            servicioComun.saveOrUpdateLibro(libro);
            String id = String.valueOf(libro.getId());
            System.out.println("ID del nuevo libro: " + libro.getId());
            logger.info("Iniciando asociarDatosLibro...{}", libro.getId());
            // Devolver el ID del nuevo libro
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("libro creado" + libro.getId());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al procesar el libro: " + e.getMessage());
        }
    }

    @PostMapping("/asociarDatos")
    public ResponseEntity<Object> asociarDatos(@RequestParam Long[] autores, @RequestParam Long id) {
        try {
            logger.info("Recibidos autores: {}, id: {}", Arrays.toString(autores), id);
            com.salitadelibros.salita.models.Libro libro = libroServicio.getLibroById(id);

            for (Long i : autores) {
                Autor autor = autorServicio.getAutorById(i);

                logger.info("Obtenido autor con id {}: {}", i, autor);

                if (autor != null) {

                    LibroAutor libroAutor = new LibroAutor(libro, autor);
                    libroAutorServicio.saveOrUpdate(libroAutor);
                    logger.info("Obtenido libroAutor {}", libroAutor);

                } else {
                    logger.error("No se encontró el autor con id {}", i);
                }
            }
            return new ResponseEntity<>("Autores asociados correctamente", HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Error al procesar la asociación de datos", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al procesar la asociación de datos: " + e.getMessage());
        }
    }

    @PostMapping("/asociarIlustradores")
    public ResponseEntity<Object> asociarIlustradores(@RequestParam Long[] ilustradores, @RequestParam Long id) {
        try {
            logger.info("Recibidos ilustradores: {}, id: {}", Arrays.toString(ilustradores), id);
            com.salitadelibros.salita.models.Libro libro = libroServicio.getLibroById(id);

            for (Long i : ilustradores) {
                Ilustrador ilustrador = ilustradorServicio.getIlustradorById(i);
                logger.info("Obtenido ilustradores con id {}: {}", i, ilustrador);
                if (ilustrador != null){
                    LibroIlustrador libroIlustrador = new LibroIlustrador(libro, ilustrador);
                    libroIlustradorServicio.saveOrUpdate(libroIlustrador);
                }else {
                    logger.error("El ilustrador es nulo para ese id:{}", i);
                }
            } return new ResponseEntity<>("Ilustradores asociados correctamente", HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Error al procesar la asociación de datos de ilustradores", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al procesar la asociación de ilustradores: " + e.getMessage());
        }
    }

    @PostMapping("/asociarCategorias")
    public ResponseEntity<Object> asociarCategorias(@RequestParam Long[] categorias, @RequestParam Long id){
        try{
            logger.info("Recibidos ilustradores: {}, id: {}", Arrays.toString(categorias), id);
            com.salitadelibros.salita.models.Libro libro = libroServicio.getLibroById(id);
            for (Long i : categorias){
                Categoria categoria= categoriaServicio.findCategoriaById(i);
                if (categoria != null){
                    LibroCategoria libroCategoria = new LibroCategoria(libro, categoria);
                    libroCategoriaServicio.saveOrUpdate(libroCategoria);
                    logger.info("Obtenido libroCategoria con {}",  libroCategoria);
                }else {
                    logger.error("La categoria es nulo para ese id:{}", i);
                }
            } return new ResponseEntity<>("categorias asociadas correctamente", HttpStatus.CREATED);
        }catch (Exception e){
            logger.error("Error al procesar la asociación de datos de categorias", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al procesar la asociación de categorias: " + e.getMessage());
        }
    }
    @PostMapping("/asociarEditorial")
    public ResponseEntity<Object> asociarEditorial(@RequestParam Long[] editorial, @RequestParam Long id){
        try{
            logger.info("Recibido editorial: {}, id: {}", Arrays.toString(editorial), id);
            com.salitadelibros.salita.models.Libro libro = libroServicio.getLibroById(id);
            for (Long i : editorial){
                logger.info("i es igual a : {}", i);
                Editorial editorial1 = editorialServicio.getEditorialById(i);
                if (editorial1 != null){
                    libro.addEditorial(editorial1);
                    servicioComun.saveOrUpdateLibro(libro);
                }else {
                    logger.error("La editorial es nula para el id: {}", i);
                }
            }return new ResponseEntity<>("Editorial asociada correctamente", HttpStatus.CREATED);
        }catch (Exception e){
            logger.error("Error al asociar Editorial ", e);
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al procesar la asociacion de Editorial" +e.getMessage());
        }
    }
}