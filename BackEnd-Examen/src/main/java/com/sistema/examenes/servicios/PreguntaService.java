package com.sistema.examenes.servicios;

import com.sistema.examenes.modelo.Examen;
import com.sistema.examenes.modelo.Pregunta;
import com.sistema.examenes.repositorios.PreguntaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class PreguntaService {

    private final PreguntaRepository preguntaRepository;

    // Constantes de mensajes
    private static final String ID_NULO = "El ID de la pregunta no puede ser nulo.";
    private static final String PREGUNTA_NO_EXISTE = "La pregunta con ID %d no existe.";
    private static final String CONTENIDO_VACIO = "El contenido de la pregunta no puede estar vacío.";
    private static final String RESPUESTA_VACIA = "La respuesta de la pregunta no puede estar vacía.";
    private static final String OPCION1_VACIA = "La opción 1 de la pregunta no puede estar vacía.";
    private static final String OPCION2_VACIA = "La opción 2 de la pregunta no puede estar vacía.";
    private static final String OPCION3_VACIA = "La opción 3 de la pregunta no puede estar vacía.";
    private static final String OPCION4_VACIA = "La opción 4 de la pregunta no puede estar vacía.";
    private static final String EXAMEN_NULO = "El examen de la pregunta no puede ser nulo.";

    // Agregar pregunta
    public Pregunta agregarPregunta(Pregunta pregunta) {
        validarPregunta(pregunta);
        return preguntaRepository.save(pregunta);
    }

    // Actualizar pregunta
    public Pregunta actualizarPregunta(Pregunta pregunta) {
        if (pregunta.getPreguntaId() == null) {
            throw new IllegalArgumentException(ID_NULO);
        }
        if (!preguntaRepository.existsById(pregunta.getPreguntaId())) {
            throw new IllegalArgumentException(String.format(PREGUNTA_NO_EXISTE, pregunta.getPreguntaId()));
        }
        validarPregunta(pregunta);
        return preguntaRepository.save(pregunta);
    }

    // Obtener todas las preguntas
    public Set<Pregunta> obtenerPreguntas() {
        return new HashSet<>(preguntaRepository.findAll());
    }

    // Obtener pregunta por ID
    public Pregunta obtenerPregunta(Long preguntaId) {
        return preguntaRepository.findById(preguntaId)
                .orElseThrow(() -> new IllegalArgumentException(String.format(PREGUNTA_NO_EXISTE, preguntaId)));
    }

    // Obtener preguntas de un examen
    public Set<Pregunta> obtenerPreguntasDelExamen(Examen examen) {
        return preguntaRepository.findByExamen(examen);
    }

    // Eliminar pregunta
    public void eliminarPregunta(Long preguntaId) {
        if (!preguntaRepository.existsById(preguntaId)) {
            throw new IllegalArgumentException(String.format(PREGUNTA_NO_EXISTE, preguntaId));
        }
        preguntaRepository.deleteById(preguntaId);
    }

    // Método privado para validaciones
    private void validarPregunta(Pregunta pregunta) {
        if (pregunta == null) {
            throw new IllegalArgumentException("La pregunta no puede ser nula.");
        }
        if (pregunta.getContenido() == null || pregunta.getContenido().trim().isEmpty()) {
            throw new IllegalArgumentException(CONTENIDO_VACIO);
        }
        if (pregunta.getRespuesta() == null || pregunta.getRespuesta().trim().isEmpty()) {
            throw new IllegalArgumentException(RESPUESTA_VACIA);
        }
        if (pregunta.getOpcion1() == null || pregunta.getOpcion1().trim().isEmpty()) {
            throw new IllegalArgumentException(OPCION1_VACIA);
        }
        if (pregunta.getOpcion2() == null || pregunta.getOpcion2().trim().isEmpty()) {
            throw new IllegalArgumentException(OPCION2_VACIA);
        }
        if (pregunta.getOpcion3() == null || pregunta.getOpcion3().trim().isEmpty()) {
            throw new IllegalArgumentException(OPCION3_VACIA);
        }
        if (pregunta.getOpcion4() == null || pregunta.getOpcion4().trim().isEmpty()) {
            throw new IllegalArgumentException(OPCION4_VACIA);
        }
        if (pregunta.getExamen() == null) {
            throw new IllegalArgumentException(EXAMEN_NULO);
        }
    }
}
