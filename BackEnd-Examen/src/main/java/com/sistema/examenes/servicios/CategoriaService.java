package com.sistema.examenes.servicios;

import com.sistema.examenes.modelo.Categoria;
import com.sistema.examenes.repositorios.CategoriaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    // Constantes de mensajes
    private static final String TITULO_VACIO = "El título de la categoría no puede estar vacío.";
    private static final String DESCRIPCION_VACIA = "La descripción de la categoría no puede estar vacía.";
    private static final String CATEGORIA_NO_EXISTE = "La categoría con ID %d no existe.";
    private static final String ID_NULO = "El ID de la categoría no puede ser nulo.";


    public Categoria agregarCategoria(Categoria categoria) {
        validarCategoria(categoria);
        return categoriaRepository.save(categoria);
    }


    public Categoria actualizarCategoria(Categoria categoria) {
        if (categoria.getCategoriaId() == null) {
            throw new IllegalArgumentException(ID_NULO);
        }
        if (!categoriaRepository.existsById(categoria.getCategoriaId())) {
            throw new IllegalArgumentException(String.format(CATEGORIA_NO_EXISTE, categoria.getCategoriaId()));
        }
        validarCategoria(categoria);

        if (categoria.getExamenes() != null) {
            categoria.getExamenes().forEach(examen -> examen.setCategoria(categoria));
        }

        return categoriaRepository.save(categoria);
    }

    public Set<Categoria> obtenerCategorias() {
        return new LinkedHashSet<>(categoriaRepository.findAll());
    }
    public Categoria obtenerCategoria(Long categoriaId) {
        return categoriaRepository.findById(categoriaId)
                .orElseThrow(() -> new IllegalArgumentException(String.format(CATEGORIA_NO_EXISTE, categoriaId)));
    }

    public void eliminarCategoria(Long categoriaId) {
        if (!categoriaRepository.existsById(categoriaId)) {
            throw new IllegalArgumentException(String.format(CATEGORIA_NO_EXISTE, categoriaId));
        }
        categoriaRepository.deleteById(categoriaId);
    }

    private void validarCategoria(Categoria categoria) {
        if (categoria.getTitulo() == null || categoria.getTitulo().trim().isEmpty()) {
            throw new IllegalArgumentException(TITULO_VACIO);
        }
        if (categoria.getDescripcion() == null || categoria.getDescripcion().trim().isEmpty()) {
            throw new IllegalArgumentException(DESCRIPCION_VACIA);
        }
    }
}
