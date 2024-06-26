package com.salitadelibros.salita.services.implementations;

import com.salitadelibros.salita.models.Categoria;
import com.salitadelibros.salita.repositories.CategoriaRepositorio;
import com.salitadelibros.salita.services.services.CategoriaServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaServicioImpl implements CategoriaServicio {
    @Autowired
  private  CategoriaRepositorio categoriaRepositorio;

    @Override
    public List<Categoria> getCategorias() {
        return categoriaRepositorio.findAll();
    }
    @Override
    public Optional<Categoria> getCategoria(Long id){
        return categoriaRepositorio.findById(id);
    }
    @Override
    public void saveOrUpdate(Categoria categoria){
        categoriaRepositorio.save(categoria);
    }
    @Override
    public void delete(Long id){
        categoriaRepositorio.deleteById(id);
    }
    @Override
    public Categoria findByPalabraCategoria(String palabraCategoria) {

        return categoriaRepositorio.findByPalabraCategoria(palabraCategoria);
    }
    @Override
    public Categoria findCategoriaById(Long id){
        Optional<Categoria> optionalCategoria = categoriaRepositorio.findById(id);
        if (optionalCategoria.isPresent()){
            Categoria categoria = optionalCategoria.get();
            return categoria;
        }return null;
    }


}
