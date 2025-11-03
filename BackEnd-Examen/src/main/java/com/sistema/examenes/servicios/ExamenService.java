package com.sistema.examenes.servicios;

import com.sistema.examenes.modelo.Categoria;
import com.sistema.examenes.modelo.Examen;
import com.sistema.examenes.repositorios.ExamenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ExamenService {

    private final ExamenRepository examenRepository;

    // Constantes de mensajes
    private static final String EXAMEN_NULO = "El examen no puede ser nulo.";
    private static final String ID_NULO = "El ID del examen no puede ser nulo.";
    private static final String EXAMEN_NO_EXISTE = "El examen con ID %d no existe.";
    private static final String TITULO_OBLIGATORIO = "El título del examen es obligatorio.";
    private static final String CATEGORIA_OBLIGATORIA = "La categoría del examen es obligatoria.";
    private static final String PUNTOS_MAXIMOS_OBLIGATORIOS = "Los puntos máximos son obligatorios.";
    private static final String NUMERO_PREGUNTAS_OBLIGATORIO = "El número de preguntas es obligatorio.";

    // Agregar examen
    public Examen agregarExamen(Examen examen) {
        validarExamen(examen);
        return examenRepository.save(examen);
    }

    // Actualizar examen
    public Examen actualizarExamen(Examen examen) {
        if (examen.getExamenId() == null) {
            throw new IllegalArgumentException(ID_NULO);
        }
        if (!examenRepository.existsById(examen.getExamenId())) {
            throw new IllegalArgumentException(String.format(EXAMEN_NO_EXISTE, examen.getExamenId()));
        }
        validarExamen(examen);
        return examenRepository.save(examen);
    }

    // Obtener todos los exámenes
    public Set<Examen> obtenerExamenes() {
        return new LinkedHashSet<>(examenRepository.findAll());
    }

    // Obtener examen por ID
    public Examen obtenerExamen(Long examenId) {
        return examenRepository.findById(examenId)
                .orElseThrow(() -> new IllegalArgumentException(String.format(EXAMEN_NO_EXISTE, examenId)));
    }

    // Eliminar examen
    public void eliminarExamen(Long examenId) {
        if (!examenRepository.existsById(examenId)) {
            throw new IllegalArgumentException(String.format(EXAMEN_NO_EXISTE, examenId));
        }
        examenRepository.deleteById(examenId);
    }

    // Listar exámenes de una categoría
    public List<Examen> listarExamenesDeUnaCategoria(Categoria categoria) {
        return examenRepository.findByCategoria(categoria);
    }

    // Listar exámenes activos
    public List<Examen> obtenerExamenesActivos() {
        return examenRepository.findByActivo(true);
    }

    // Listar exámenes activos de una categoría
    public List<Examen> obtenerExamenesActivosDeUnaCategoria(Categoria categoria) {
        return examenRepository.findByCategoriaAndActivo(categoria, true);
    }

    // Validaciones privadas
    private void validarExamen(Examen examen) {
        if (examen == null) {
            throw new IllegalArgumentException(EXAMEN_NULO);
        }
        if (examen.getTitulo() == null || examen.getTitulo().trim().isEmpty()) {
            throw new IllegalArgumentException(TITULO_OBLIGATORIO);
        }
        if (examen.getCategoria() == null) {
            throw new IllegalArgumentException(CATEGORIA_OBLIGATORIA);
        }
        if (examen.getPuntosMaximos() == null || examen.getPuntosMaximos().trim().isEmpty()) {
            throw new IllegalArgumentException(PUNTOS_MAXIMOS_OBLIGATORIOS);
        }
        if (examen.getNumeroDePreguntas() == null || examen.getNumeroDePreguntas().trim().isEmpty()) {
            throw new IllegalArgumentException(NUMERO_PREGUNTAS_OBLIGATORIO);
        }
    }
}
