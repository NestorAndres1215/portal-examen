package com.sistema.examenes.servicios.impl;

import com.sistema.examenes.modelo.Examen;
import com.sistema.examenes.modelo.Pregunta;
import com.sistema.examenes.repositorios.PreguntaRepository;
import com.sistema.examenes.servicios.PreguntaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;



@Service
public class PreguntaServiceImpl implements PreguntaService {

    @Autowired
    private PreguntaRepository preguntaRepository;

    @Override
    public Pregunta agregarPregunta(Pregunta pregunta) {
        if (pregunta.getContenido() == null || pregunta.getContenido().trim().isEmpty()) {
            throw new IllegalArgumentException("El contenido de la pregunta no puede estar vacío.");
        }
        if (pregunta.getRespuesta() == null || pregunta.getRespuesta().trim().isEmpty()) {
            throw new IllegalArgumentException("La respuesta de la pregunta no puede estar vacía.");
        }
        if (pregunta.getOpcion1() == null || pregunta.getOpcion1().trim().isEmpty()) {
            throw new IllegalArgumentException("La opción 1 de la pregunta no puede estar vacía.");
        }
        if (pregunta.getOpcion2() == null || pregunta.getOpcion2().trim().isEmpty()) {
            throw new IllegalArgumentException("La opción 2 de la pregunta no puede estar vacía.");
        }
        if (pregunta.getOpcion3() == null || pregunta.getOpcion3().trim().isEmpty()) {
            throw new IllegalArgumentException("La opción 3 de la pregunta no puede estar vacía.");
        }
        if (pregunta.getOpcion4() == null || pregunta.getOpcion4().trim().isEmpty()) {
            throw new IllegalArgumentException("La opción 4 de la pregunta no puede estar vacía.");
        }
        if (pregunta.getExamen() == null) {
            throw new IllegalArgumentException("El examen de la pregunta no puede ser nulo.");
        }


        return preguntaRepository.save(pregunta);
    }

    @Override
    public Pregunta actualizarPregunta(Pregunta pregunta) {
        if (pregunta.getPreguntaId() == null) {
            throw new IllegalArgumentException("El ID de la pregunta no puede ser nulo.");
        }
        if (!preguntaRepository.existsById(pregunta.getPreguntaId())) {
            throw new IllegalArgumentException("La pregunta con ID " + pregunta.getPreguntaId() + " no existe.");
        }
        if (pregunta.getContenido() == null || pregunta.getContenido().trim().isEmpty()) {
            throw new IllegalArgumentException("El contenido de la pregunta no puede estar vacío.");
        }
        if (pregunta.getRespuesta() == null || pregunta.getRespuesta().trim().isEmpty()) {
            throw new IllegalArgumentException("La respuesta de la pregunta no puede estar vacía.");
        }
        if (pregunta.getOpcion1() == null || pregunta.getOpcion1().trim().isEmpty()) {
            throw new IllegalArgumentException("La opción 1 de la pregunta no puede estar vacía.");
        }
        if (pregunta.getOpcion2() == null || pregunta.getOpcion2().trim().isEmpty()) {
            throw new IllegalArgumentException("La opción 2 de la pregunta no puede estar vacía.");
        }
        if (pregunta.getOpcion3() == null || pregunta.getOpcion3().trim().isEmpty()) {
            throw new IllegalArgumentException("La opción 3 de la pregunta no puede estar vacía.");
        }
        if (pregunta.getOpcion4() == null || pregunta.getOpcion4().trim().isEmpty()) {
            throw new IllegalArgumentException("La opción 4 de la pregunta no puede estar vacía.");
        }
        if (pregunta.getExamen() == null) {
            throw new IllegalArgumentException("El examen de la pregunta no puede ser nulo.");
        }
        return preguntaRepository.save(pregunta);
    }

    @Override
    public Set<Pregunta> obtenerPreguntas() {
        return new java.util.HashSet<>(preguntaRepository.findAll());
    }

    @Override
    public Pregunta obtenerPregunta(Long preguntaId) {
        return preguntaRepository.findById(preguntaId).get();
    }

    @Override
    public Set<Pregunta> obtenerPreguntasDelExamen(Examen examen) {
        return preguntaRepository.findByExamen(examen);
    }

    @Override
    public void eliminarPregunta(Long preguntaId) {
        if (!preguntaRepository.existsById(preguntaId)) {
            throw new IllegalArgumentException("La pregunta con ID " + preguntaId + " no existe.");
        }
        preguntaRepository.deleteById(preguntaId);
    }

    @Override
    public Pregunta listarPregunta(Long preguntaId) {
        return this.preguntaRepository.getById(preguntaId);
    }
}
