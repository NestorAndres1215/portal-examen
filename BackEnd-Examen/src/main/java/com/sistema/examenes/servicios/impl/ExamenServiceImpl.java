package com.sistema.examenes.servicios.impl;

import com.sistema.examenes.modelo.Categoria;
import com.sistema.examenes.modelo.Examen;
import com.sistema.examenes.repositorios.ExamenRepository;
import com.sistema.examenes.servicios.ExamenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Service
public class ExamenServiceImpl implements ExamenService {

    @Autowired
    private ExamenRepository examenRepository;

    @Override
    public Examen agregarExamen(Examen examen) {
        if (examen == null) {
            throw new IllegalArgumentException("El examen no puede ser nulo.");
        }
        if (examen.getTitulo() == null || examen.getTitulo().trim().isEmpty()) {
            throw new IllegalArgumentException("El título del examen es obligatorio.");
        }
        if (examen.getCategoria() == null) {
            throw new IllegalArgumentException("La categoría del examen es obligatoria.");
        }
        if (examen.getPuntosMaximos() == null || examen.getPuntosMaximos().trim().isEmpty()) {
            throw new IllegalArgumentException("Los puntos máximos son obligatorios.");
        }
        if (examen.getNumeroDePreguntas() == null || examen.getNumeroDePreguntas().trim().isEmpty()) {
            throw new IllegalArgumentException("El número de preguntas es obligatorio.");
        }
        return examenRepository.save(examen);
    }

    @Override
    public Examen actualizarExamen(Examen examen) {
        if (examen.getExamenId() == null) {
            throw new IllegalArgumentException("El ID del examen no puede ser nulo.");
        }
        if (!examenRepository.existsById(examen.getExamenId())) {
            throw new IllegalArgumentException("El examen con ID " + examen.getExamenId() + " no existe.");
        }
        if (examen.getTitulo() == null || examen.getTitulo().trim().isEmpty()) {
            throw new IllegalArgumentException("El título del examen es obligatorio.");
        }
        if (examen.getCategoria() == null) {
            throw new IllegalArgumentException("La categoría del examen es obligatoria.");
        }
        if (examen.getPuntosMaximos() == null || examen.getPuntosMaximos().trim().isEmpty()) {
            throw new IllegalArgumentException("Los puntos máximos son obligatorios.");
        }
        if (examen.getNumeroDePreguntas() == null || examen.getNumeroDePreguntas().trim().isEmpty()) {
            throw new IllegalArgumentException("El número de preguntas es obligatorio.");
        }
        // Verificar si la categoría ya existe
        return examenRepository.save(examen);
    }

    @Override
    public Set<Examen> obtenerExamenes() {
        return new LinkedHashSet<>(examenRepository.findAll());
    }

    @Override
    public Examen obtenerExamen(Long examenId) {
        return examenRepository.findById(examenId).get();
    }

    @Override
    public void eliminarExamen(Long examenId) {
        if (!examenRepository.existsById(examenId)) {
            throw new IllegalArgumentException("El examen con ID " + examenId + " no existe.");
        }
        Examen examen = new Examen();
        examen.setExamenId(examenId);
        examenRepository.delete(examen);
    }

    @Override
    public List<Examen> listarExamenesDeUnaCategoria(Categoria categoria) {
        return this.examenRepository.findByCategoria(categoria);
    }

    @Override
    public List<Examen> obtenerExamenesActivos() {
        return examenRepository.findByActivo(true);
    }

    @Override
    public List<Examen> obtenerExamenesActivosDeUnaCategoria(Categoria categoria) {
        return examenRepository.findByCategoriaAndActivo(categoria,true);
    }
}
