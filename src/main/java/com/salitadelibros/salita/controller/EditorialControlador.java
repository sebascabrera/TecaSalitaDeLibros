package com.salitadelibros.salita.controller;

import com.salitadelibros.salita.models.Editorial;
import com.salitadelibros.salita.services.EditorialServicio;
import com.salitadelibros.salita.services.ServicioComun;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/editoriales")
public class EditorialControlador {

    @Autowired
    private ServicioComun servicioComun;
    @Autowired
    private EditorialServicio editorialServicio;


    @PostMapping("/guardar-editorial")
    public ResponseEntity<String> saveOrUpdateEditorial(@RequestBody Editorial editorial) {
        servicioComun.saveOrUpdateEditorial(editorial);

        return ResponseEntity.ok("Editorial guardada o actualizada exitosamente");
    }

    @GetMapping("/editoriales")
    public List<Editorial> getEditoriales(){
        return editorialServicio.getEditoriales();
    }


}

