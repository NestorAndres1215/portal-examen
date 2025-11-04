package com.sistema.examenes.servicios;

import com.sistema.examenes.modelo.Usuario;
import com.sistema.examenes.repositorios.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static com.sistema.examenes.util.MensajesConstantes.USUARIO_NO_ENCONTRADO;

@Service
@RequiredArgsConstructor
public class LoginDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByUsername(username);

        if (usuario == null) {
            throw new UsernameNotFoundException(String.format(USUARIO_NO_ENCONTRADO, username));
        }

        return usuario;
    }
}
