package com.salitadelibros.salita.services.services;

import com.salitadelibros.salita.models.Editorial;
import com.salitadelibros.salita.models.Libro;

import java.util.List;
import java.util.Optional;

public interface EditorialServicio {

    void saveOrUpdate(Editorial editorial);

    public List<Editorial> getEditoriales();

    public Optional<Editorial> getEditorial(Long id);
}
