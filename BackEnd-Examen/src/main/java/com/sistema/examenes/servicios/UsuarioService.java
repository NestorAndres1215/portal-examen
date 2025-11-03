package com.sistema.examenes.servicios;

import com.sistema.examenes.excepciones.UsuarioFoundException;
import com.sistema.examenes.modelo.Usuario;
import com.sistema.examenes.modelo.UsuarioRol;
import com.sistema.examenes.repositorios.RolRepository;
import com.sistema.examenes.repositorios.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;

    private static final String USUARIO_NULO = "El usuario o el nombre de usuario no puede ser nulo.";
    private static final String USUARIO_EXISTENTE = "El usuario ya está presente.";
    private static final String ROLES_INVALIDOS = "El usuario debe tener al menos un rol asignado.";
    private static final String ROL_NULO = "El rol no puede ser nulo.";
    private static final String USUARIO_NO_EXISTE = "El usuario con ID %d no existe.";

    public Usuario guardarUsuario(Usuario usuario, Set<UsuarioRol> usuarioRoles) throws Exception {

        if (usuario == null || usuario.getUsername() == null || usuario.getUsername().trim().isEmpty()) {
            throw new IllegalArgumentException(USUARIO_NULO);
        }

        Usuario usuarioExistente = usuarioRepository.findByUsername(usuario.getUsername());
        if (usuarioExistente != null) {
            throw new UsuarioFoundException(USUARIO_EXISTENTE);
        }

        if (usuarioRoles == null || usuarioRoles.isEmpty()) {
            throw new IllegalArgumentException(ROLES_INVALIDOS);
        }

        for (UsuarioRol usuarioRol : usuarioRoles) {
            if (usuarioRol == null || usuarioRol.getRol() == null) {
                throw new IllegalArgumentException(ROL_NULO);
            }
            rolRepository.save(usuarioRol.getRol());
        }

        usuario.getUsuarioRoles().addAll(usuarioRoles);

        return usuarioRepository.save(usuario);
    }

    public Usuario obtenerUsuario(String username) {
        return usuarioRepository.findByUsername(username);
    }

    public void eliminarUsuario(Long usuarioId) {
        if (!usuarioRepository.existsById(usuarioId)) {
            throw new IllegalArgumentException(String.format(USUARIO_NO_EXISTE, usuarioId));
        }
        usuarioRepository.deleteById(usuarioId);
    }
}
