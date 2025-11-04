package com.sistema.examenes.servicios;

import com.sistema.examenes.excepciones.UsuarioFoundException;
import com.sistema.examenes.modelo.Usuario;
import com.sistema.examenes.modelo.UsuarioRol;
import com.sistema.examenes.repositorios.RolRepository;
import com.sistema.examenes.repositorios.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

import static com.sistema.examenes.util.MensajesConstantes.*;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;



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
