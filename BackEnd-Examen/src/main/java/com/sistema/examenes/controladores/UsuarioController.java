package com.sistema.examenes.controladores;

import com.sistema.examenes.modelo.Rol;
import com.sistema.examenes.modelo.Usuario;
import com.sistema.examenes.modelo.UsuarioRol;
import com.sistema.examenes.servicios.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/usuarios")
@CrossOrigin("*")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping("/")
    public Usuario guardarUsuario(@RequestBody Usuario usuario) throws Exception {
        try {
            if (usuario == null) {
                throw new IllegalArgumentException("El usuario no puede ser nulo");
            }
            if (usuario.getUsername() == null || usuario.getUsername().trim().isEmpty()) {
                throw new IllegalArgumentException("El nombre de usuario no puede estar vacío");
            }
            if (usuario.getPassword() == null || usuario.getPassword().trim().isEmpty()) {
                throw new IllegalArgumentException("La contraseña no puede estar vacía");
            }
            if (usuario.getEmail() == null || usuario.getEmail().trim().isEmpty()) {
                throw new IllegalArgumentException("El email no puede estar vacío");
            }

            usuario.setPerfil("default.png");
            usuario.setPassword(this.bCryptPasswordEncoder.encode(usuario.getPassword()));

            Set<UsuarioRol> usuarioRoles = new HashSet<>();

            Rol rol = new Rol();
            rol.setRolId(1L);
            rol.setRolNombre("ADMIN");

            UsuarioRol usuarioRol = new UsuarioRol();
            usuarioRol.setUsuario(usuario);
            usuarioRol.setRol(rol);

            usuarioRoles.add(usuarioRol);
            return usuarioService.guardarUsuario(usuario, usuarioRoles);
        } catch (Exception e) {
            System.err.println("Error al guardar usuario: " + e.getMessage());
            throw e;
        }
    }


        @GetMapping("/{username}")
        public Usuario obtenerUsuario(@PathVariable("username") String username){
            if (username == null || username.trim().isEmpty()) {
                throw new IllegalArgumentException("El nombre de usuario no puede estar vacío");
            }
            try {
                return usuarioService.obtenerUsuario(username);
            } catch (Exception e) {
                System.err.println("Error al obtener usuario: " + e.getMessage());
                return null;
            }
        }

        @DeleteMapping("/{usuarioId}")
        public void eliminarUsuario(@PathVariable("usuarioId") Long usuarioId){
            if (usuarioId == null || usuarioId <= 0) {
                throw new IllegalArgumentException("El ID de usuario no es válido");
            }
            try {
                usuarioService.eliminarUsuario(usuarioId);
            } catch (Exception e) {
                System.err.println("Error al eliminar usuario: " + e.getMessage());
                // Manejo de error adicional si es necesario
            }
        }

}
