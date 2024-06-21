package com.salitadelibros.salita.controller;

import com.salitadelibros.salita.dtos.EditorialDTO;
import com.salitadelibros.salita.models.Editorial;
import com.salitadelibros.salita.repositories.EditorialRepositorio;
import com.salitadelibros.salita.services.services.EditorialServicio;
import com.salitadelibros.salita.services.ServicioComun;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/editoriales")
public class EditorialControlador {

    @Autowired
    private ServicioComun servicioComun;
    @Autowired
    private EditorialServicio editorialServicio;
    @Autowired
    EditorialRepositorio editorialRepositorio;
    @RequestMapping("/editoriales")
    public Set<EditorialDTO> getEditoriales() {
        List<Editorial> listaEditorial = editorialRepositorio.findAll();
        Set<EditorialDTO> editorialDTOList = listaEditorial
                .stream()
                .map(editorial -> new EditorialDTO(editorial))
                .collect(Collectors.toSet());
        return editorialDTOList;
    }

    @PostMapping
    public ResponseEntity<String> saveOrUpdateEditorial(@RequestBody Editorial editorial) {
        servicioComun.saveOrUpdateEditorial(editorial);

        return ResponseEntity.ok("Editorial guardada o actualizada exitosamente");
    }




}

