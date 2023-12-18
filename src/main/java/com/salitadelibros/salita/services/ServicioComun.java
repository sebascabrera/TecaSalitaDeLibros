package com.salitadelibros.salita.services;


import com.salitadelibros.salita.models.Editorial;
import com.salitadelibros.salita.models.Ilustrador;
import com.salitadelibros.salita.models.Libro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
    public class ServicioComun {

        @Autowired
        private LibroServicio libroServicio;
        @Autowired
        private EditorialServicio editorialServicio;

        @Autowired
        private IlustradorServicio ilustradorServicio;

        public void saveOrUpdateLibro(Libro libro) {
            libroServicio.saveOrUpdate(libro);

        }

        public void saveOrUpdateEditorial(Editorial editorial) {
            editorialServicio.saveOrUpdate(editorial);

        }
    public void saveOrUpdateIlustrador(Ilustrador ilustrador) {
        ilustradorServicio.saveOrUpdate(ilustrador);

    }
    }


