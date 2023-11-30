package com.salitadelibros.salita.controller;

import com.salitadelibros.salita.dtos.EditorialDTO;
import com.salitadelibros.salita.dtos.LibroDTO;
import com.salitadelibros.salita.models.Editorial;
import com.salitadelibros.salita.models.Libro;
import com.salitadelibros.salita.repositories.EditorialRepositorio;
import com.salitadelibros.salita.repositories.LibroRepositorio;
import com.salitadelibros.salita.services.LibroServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/api")
public class LibroControlador {
    @Autowired
    private LibroServicio libroServicio;
    @Autowired
    private LibroRepositorio libroRepositorio;
    @Autowired
    private EditorialRepositorio editorialRepositorio;

    // metodo que va a devolver una lista de libros que se la pido al repositorio por eso esta inyectada
    // /api/libros servlet una ruta de una petición asociado a un metodo
    @GetMapping("/libros")
    public Map<String, Object> getLibros() {
        Map<String, Object> response = new HashMap<>();

        List<LibroDTO> libroDTOList = libroServicio.getLibros()
                .stream()
                .map(libro -> new LibroDTO(libro))
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
  /*
    @GetMapping("/categorias")
    public List<String> getCategorias() {
        return LibroRepositorio.getCategorias();
    }

    @PostMapping
    public void saveOrUpdate(@RequestBody Libro libro) {
        LibroRepositorio.saveOrUpdate(libro);
    }

    @DeleteMapping("/{libroId}")
    public void delete(@PathVariable("libroId") Long libroId) {
        LibroRepositorio.delete(libroId);
    }*/
}
