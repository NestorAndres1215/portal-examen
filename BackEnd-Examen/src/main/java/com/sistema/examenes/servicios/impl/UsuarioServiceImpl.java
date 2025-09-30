package com.sistema.examenes.servicios.impl;

import com.sistema.examenes.excepciones.UsuarioFoundException;
import com.sistema.examenes.modelo.Usuario;
import com.sistema.examenes.modelo.UsuarioRol;
import com.sistema.examenes.repositorios.RolRepository;
import com.sistema.examenes.repositorios.UsuarioRepository;
import com.sistema.examenes.servicios.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RolRepository rolRepository;

    @Override
    public Usuario guardarUsuario(Usuario usuario, Set<UsuarioRol> usuarioRoles) throws Exception {
        // Validar que el usuario no exista previamente
        if (usuario == null || usuario.getUsername() == null) {
            throw new IllegalArgumentException("El usuario o el nombre de usuario no puede ser nulo.");
        }

        Usuario usuarioLocal = usuarioRepository.findByUsername(usuario.getUsername());
        if (usuarioLocal != null) {
            throw new UsuarioFoundException("El usuario ya está presente");
        }

        // Validar que los roles no sean nulos ni vacíos
        if (usuarioRoles == null || usuarioRoles.isEmpty()) {
            throw new IllegalArgumentException("El usuario debe tener al menos un rol asignado.");
        }

        // Guardar roles y asociarlos al usuario
        for (UsuarioRol usuarioRol : usuarioRoles) {
            if (usuarioRol == null || usuarioRol.getRol() == null) {
                throw new IllegalArgumentException("El rol no puede ser nulo.");
            }
            rolRepository.save(usuarioRol.getRol());
        }
        usuario.getUsuarioRoles().addAll(usuarioRoles);

        // Guardar usuario
        usuarioLocal = usuarioRepository.save(usuario);
        return usuarioLocal;
    }

    @Override
    public Usuario obtenerUsuario(String username) {
        return usuarioRepository.findByUsername(username);
    }

    @Override
    public void eliminarUsuario(Long usuarioId) {
        if (!usuarioRepository.existsById(usuarioId)) {
            throw new IllegalArgumentException("El usuario con ID " + usuarioId + " no existe.");
        }
        usuarioRepository.deleteById(usuarioId);
    }

}