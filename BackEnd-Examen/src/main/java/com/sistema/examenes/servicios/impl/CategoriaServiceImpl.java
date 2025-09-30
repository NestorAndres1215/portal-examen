package com.sistema.examenes.servicios.impl;


import com.sistema.examenes.modelo.Categoria;
import com.sistema.examenes.repositorios.CategoriaRepository;
import com.sistema.examenes.servicios.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;
import java.util.Set;

@Service
public class CategoriaServiceImpl  implements CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Override
    public Categoria agregarCategoria(Categoria categoria) {
        if (categoria.getTitulo() == null || categoria.getTitulo().trim().isEmpty()) {
            throw new IllegalArgumentException("El título de la categoría no puede estar vacío.");
        }
        if (categoria.getDescripcion() == null || categoria.getDescripcion().trim().isEmpty()) {
            throw new IllegalArgumentException("La descripción de la categoría no puede estar vacía.");
        }
        return categoriaRepository.save(categoria);
    }


    @Override
    public Categoria actualizarCategoria(Categoria categoria) {
        if (categoria.getCategoriaId() == null) {
            throw new IllegalArgumentException("El ID de la categoría no puede ser nulo.");
        }
        if (!categoriaRepository.existsById(categoria.getCategoriaId())) {
            throw new IllegalArgumentException("La categoría con ID " + categoria.getCategoriaId() + " no existe.");
        }
        if (categoria.getTitulo() == null || categoria.getTitulo().trim().isEmpty()) {
            throw new IllegalArgumentException("El título de la categoría no puede estar vacío.");
        }
        if (categoria.getDescripcion() == null || categoria.getDescripcion().trim().isEmpty()) {
            throw new IllegalArgumentException("La descripción de la categoría no puede estar vacía.");
        }
        if (categoria.getExamenes() != null) {
            categoria.getExamenes().forEach(examen -> examen.setCategoria(categoria));
        }
        // Verificar si la categoría ya existe
        return categoriaRepository.save(categoria);
    }

    @Override
    public Set<Categoria> obtenerCategorias() {
        return new LinkedHashSet<>(categoriaRepository.findAll());
    }

    @Override
    public Categoria obtenerCategoria(Long categoriaId) {
        return categoriaRepository.findById(categoriaId).get();
    }

    @Override
    public void eliminarCategoria(Long categoriaId) {
        if (!categoriaRepository.existsById(categoriaId)) {
            throw new IllegalArgumentException("La categoría con ID " + categoriaId + " no existe.");
        }
        categoriaRepository.deleteById(categoriaId);
    }
}
