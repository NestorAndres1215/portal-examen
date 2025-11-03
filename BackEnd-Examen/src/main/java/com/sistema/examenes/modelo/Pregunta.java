package com.sistema.examenes.modelo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Entity
@Table(name = "preguntas")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pregunta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long preguntaId;

    @Column(length = 5000, nullable = false)
    private String contenido;

    private String imagen;

    @Column(nullable = false)
    private String opcion1;

    @Column(nullable = false)
    private String opcion2;

    @Column(nullable = false)
    private String opcion3;

    @Column(nullable = false)
    private String opcion4;

    @Transient
    private String respuestaDada;

    @Column(nullable = false)
    private String respuesta;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "examen_id")
    private Examen examen;
}
