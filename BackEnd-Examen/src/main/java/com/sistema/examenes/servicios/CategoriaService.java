package com.sistema.examenes.servicios;

import com.sistema.examenes.modelo.Categoria;
import com.sistema.examenes.repositorios.CategoriaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;
import java.util.Set;

import static com.sistema.examenes.servicios.PreguntaService.*;
import static com.sistema.examenes.util.MensajesConstantes.*;

@Service
@RequiredArgsConstructor
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;



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
