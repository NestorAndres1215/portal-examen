package com.sistema.examenes.controladores;

import com.sistema.examenes.modelo.Examen;
import com.sistema.examenes.modelo.Pregunta;
import com.sistema.examenes.servicios.ExamenService;
import com.sistema.examenes.servicios.PreguntaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/pregunta")
@CrossOrigin("*")
public class PreguntaController {

    @Autowired
    private PreguntaService preguntaService;

    @Autowired
    private ExamenService examenService;

    @PostMapping("/")
    public ResponseEntity<?> guardarPregunta(@RequestBody Pregunta pregunta) {
        try {
            if (pregunta == null) {
                Map<String, String> error = new HashMap<>();
                error.put("error", "La pregunta no puede ser nula.");
                return ResponseEntity.badRequest().body(error);
            }
            Pregunta preguntaGuardada = preguntaService.agregarPregunta(pregunta);
            if (preguntaGuardada == null) {
                Map<String, String> error = new HashMap<>();
                error.put("error", "No se pudo guardar la pregunta.");
                return ResponseEntity.status(500).body(error);
            }
            return ResponseEntity.ok(preguntaGuardada);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Ocurrió un error al guardar la pregunta: " + e.getMessage());
            return ResponseEntity.status(500).body(error);
        }
    }

    @PutMapping("/")
    public ResponseEntity<?> actualizarPregunta(@RequestBody Pregunta pregunta) {
        try {
            if (pregunta == null || pregunta.getPreguntaId() == null) {
                Map<String, String> error = new HashMap<>();
                error.put("error", "La pregunta o el ID de la pregunta no pueden ser nulos.");
                return ResponseEntity.badRequest().body(error);
            }
            Pregunta preguntaActualizada = preguntaService.actualizarPregunta(pregunta);
            if (preguntaActualizada == null) {
                Map<String, String> error = new HashMap<>();
                error.put("error", "No se pudo actualizar la pregunta.");
                return ResponseEntity.status(404).body(error);
            }
            return ResponseEntity.ok(preguntaActualizada);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Ocurrió un error al actualizar la pregunta: " + e.getMessage());
            return ResponseEntity.status(500).body(error);
        }
    }

    @GetMapping("/examen/{examenId}")
    public ResponseEntity<?> listarPreguntasDelExamen(@PathVariable("examenId") Long examenId) {
        try {
            Examen examen = examenService.obtenerExamen(examenId);
            if (examen == null) {
                Map<String, String> error = new HashMap<>();
                error.put("error", "Examen no encontrado.");
                return ResponseEntity.status(404).body(error);
            }
            Set<Pregunta> preguntas = examen.getPreguntas();
            List<Pregunta> examenes = new ArrayList<>(preguntas);

            int numeroDePreguntas = Integer.parseInt(examen.getNumeroDePreguntas());
            if (examenes.size() > numeroDePreguntas) {
                examenes = examenes.subList(0, numeroDePreguntas);
            }

            Collections.shuffle(examenes);
            return ResponseEntity.ok(examenes);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Ocurrió un error al obtener las preguntas del examen: " + e.getMessage());
            return ResponseEntity.status(500).body(error);
        }
    }

    @GetMapping("/{preguntaId}")
    public ResponseEntity<?> listarPreguntaPorId(@PathVariable("preguntaId") Long preguntaId) {
        try {
            Pregunta pregunta = preguntaService.obtenerPregunta(preguntaId);
            if (pregunta == null) {
                Map<String, String> error = new HashMap<>();
                error.put("error", "Pregunta no encontrada.");
                return ResponseEntity.status(404).body(error);
            }
            return ResponseEntity.ok(pregunta);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Ocurrió un error al obtener la pregunta: " + e.getMessage());
            return ResponseEntity.status(500).body(error);
        }
    }

    @DeleteMapping("/{preguntaId}")
    public ResponseEntity<?> eliminarPregunta(@PathVariable("preguntaId") Long preguntaId) {
        try {
            preguntaService.eliminarPregunta(preguntaId);
            Map<String, String> response = new HashMap<>();
            response.put("mensaje", "Pregunta eliminada correctamente.");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Ocurrió un error al eliminar la pregunta: " + e.getMessage());
            return ResponseEntity.status(500).body(error);
        }
    }

    @GetMapping("/examen/todos/{examenId}")
    public ResponseEntity<?> listarPreguntaDelExamenComoAdministrador(@PathVariable("examenId") Long examenId) {
        try {
            Examen examen = new Examen();
            examen.setExamenId(examenId);
            Set<Pregunta> preguntas = preguntaService.obtenerPreguntasDelExamen(examen);
            return ResponseEntity.ok(preguntas);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Ocurrió un error al obtener las preguntas: " + e.getMessage());
            return ResponseEntity.status(500).body(error);
        }
    }

    @PostMapping("/evaluar-examen")
    public ResponseEntity<?> evaluarExamen(@RequestBody List<Pregunta> preguntas) {
        Map<String, Object> respuestas = new HashMap<>();
        try {
            double puntosMaximos = 0;
            Integer respuestasCorrectas = 0;
            Integer intentos = 0;

            if (preguntas == null || preguntas.isEmpty()) {
                respuestas.put("error", "La lista de preguntas está vacía o es nula.");
                return ResponseEntity.badRequest().body(respuestas);
            }

            for (Pregunta p : preguntas) {
                Pregunta pregunta = this.preguntaService.listarPregunta(p.getPreguntaId());
                if (pregunta != null && pregunta.getRespuesta() != null
                        && pregunta.getRespuesta().equals(p.getRespuestaDada())) {
                    respuestasCorrectas++;
                    double puntos = Double.parseDouble(preguntas.get(0).getExamen().getPuntosMaximos())
                            / preguntas.size();
                    puntosMaximos += puntos;
                }
                if (p.getRespuestaDada() != null) {
                    intentos++;
                }
            }

            respuestas.put("puntosMaximos", puntosMaximos);
            respuestas.put("respuestasCorrectas", respuestasCorrectas);
            respuestas.put("intentos", intentos);
            return ResponseEntity.ok(respuestas);
        } catch (Exception e) {
            respuestas.put("error", "Ocurrió un error al evaluar el examen: " + e.getMessage());
            return ResponseEntity.status(500).body(respuestas);
        }
    }
}
