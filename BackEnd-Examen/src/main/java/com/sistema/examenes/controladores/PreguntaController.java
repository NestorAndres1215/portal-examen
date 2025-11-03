package com.sistema.examenes.controladores;

import com.sistema.examenes.modelo.Examen;
import com.sistema.examenes.modelo.Pregunta;
import com.sistema.examenes.servicios.ExamenService;
import com.sistema.examenes.servicios.PreguntaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/pregunta")
@CrossOrigin("*")
@RequiredArgsConstructor
public class PreguntaController {


    private final PreguntaService preguntaService;
    private final ExamenService examenService;

    @PostMapping("/")
    public ResponseEntity<?> guardarPregunta(@RequestBody Pregunta pregunta) {
        Pregunta preguntaGuardada = preguntaService.agregarPregunta(pregunta);
        return ResponseEntity.ok(preguntaGuardada);
    }

    @PutMapping("/")
    public ResponseEntity<?> actualizarPregunta(@RequestBody Pregunta pregunta) {
        Pregunta preguntaActualizada = preguntaService.actualizarPregunta(pregunta);
        return ResponseEntity.ok(preguntaActualizada);
    }

    @GetMapping("/examen/{examenId}")
    public ResponseEntity<?> listarPreguntasDelExamen(@PathVariable("examenId") Long examenId) {

        Examen examen = examenService.obtenerExamen(examenId);

        Set<Pregunta> preguntas = examen.getPreguntas();
        List<Pregunta> examenes = new ArrayList<>(preguntas);

        int numeroDePreguntas = Integer.parseInt(examen.getNumeroDePreguntas());
        if (examenes.size() > numeroDePreguntas) {
            examenes = examenes.subList(0, numeroDePreguntas);
        }

        Collections.shuffle(examenes);
        return ResponseEntity.ok(examenes);

    }

    @GetMapping("/{preguntaId}")
    public ResponseEntity<?> listarPreguntaPorId(@PathVariable("preguntaId") Long preguntaId) {
        Pregunta pregunta = preguntaService.obtenerPregunta(preguntaId);
        return ResponseEntity.ok(pregunta);
    }

    @DeleteMapping("/{preguntaId}")
    public ResponseEntity<?> eliminarPregunta(@PathVariable("preguntaId") Long preguntaId) {
        preguntaService.eliminarPregunta(preguntaId);
        Map<String, String> response = new HashMap<>();
        response.put("mensaje", "Pregunta eliminada correctamente.");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/examen/todos/{examenId}")
    public ResponseEntity<?> listarPreguntaDelExamenComoAdministrador(@PathVariable("examenId") Long examenId) {
        Examen examen = new Examen();
        examen.setExamenId(examenId);
        Set<Pregunta> preguntas = preguntaService.obtenerPreguntasDelExamen(examen);
        return ResponseEntity.ok(preguntas);
    }

    @PostMapping("/evaluar-examen")
    public ResponseEntity<?> evaluarExamen(@RequestBody List<Pregunta> preguntas) {
        Map<String, Object> respuestas = new HashMap<>();

        double puntosMaximos = 0;
        Integer respuestasCorrectas = 0;
        Integer intentos = 0;


        for (Pregunta p : preguntas) {
            Pregunta pregunta = this.preguntaService.obtenerPregunta(p.getPreguntaId());
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

    }
}
