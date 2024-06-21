package com.salitadelibros.salita.services;


import com.salitadelibros.salita.models.*;
import com.salitadelibros.salita.services.services.*;
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
        @Autowired
        private AutorServicio autorServicio;
        @Autowired
        private CategoriaServicio categoriaServicio;


        public void saveOrUpdateLibro(Libro libro) {
            libroServicio.saveOrUpdate(libro);

        }

        public void saveOrUpdateEditorial(Editorial editorial) {
            editorialServicio.saveOrUpdate(editorial);

        }
    public void saveOrUpdateIlustrador(Ilustrador ilustrador) {
        ilustradorServicio.saveOrUpdate(ilustrador);

    }

    public void saveOrUpdateAutor(Autor autor) {
            autorServicio.saveOrUpdate(autor);
    }
    public void saveOrUpdateCategoria(Categoria categoria){
            categoriaServicio.saveOrUpdate(categoria);
    }
    }


