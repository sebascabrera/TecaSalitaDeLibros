package com.salitadelibros.salita.controller;

import com.salitadelibros.salita.models.Editorial;
import com.salitadelibros.salita.services.ServicioComun;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class EditorialControlador {

    @Autowired
    private ServicioComun servicioComun;



    @PostMapping("/guardar")
    public ResponseEntity<String> saveOrUpdateEditorial(@RequestBody Editorial editorial) {
        servicioComun.saveOrUpdateEditorial(editorial);

        return ResponseEntity.ok("Editorial guardada o actualizada exitosamente");
    }
}

