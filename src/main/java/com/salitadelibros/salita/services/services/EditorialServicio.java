package com.salitadelibros.salita.services.services;

import com.salitadelibros.salita.models.Editorial;

import java.util.List;

public interface EditorialServicio {

    void saveOrUpdate(Editorial editorial);

    public List<Editorial> getEditoriales();

    public Editorial getEditorialById(Long id);
}
