package com.sistema.examenes.modelo;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "roles")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Rol {

    @Id
    private Long rolId;

    @Column(nullable = false, unique = true)
    private String rolNombre;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "rol")
    @Builder.Default
    private Set<UsuarioRol> usuarioRoles = new HashSet<>();
}