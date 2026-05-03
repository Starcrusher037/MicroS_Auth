package com.duoc.learningplatform.autenticacion_service.service;

import com.duoc.learningplatform.autenticacion_service.model.Usuario;
import com.duoc.learningplatform.autenticacion_service.repository.UsuarioRepository;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
public class UsuarioDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioDetailsService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String correo) throws UsernameNotFoundException {

        Usuario usuario = usuarioRepository.findByCorreo(correo)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        return org.springframework.security.core.userdetails.User
                .builder()
                .username(usuario.getCorreo())
                .password(usuario.getContrasenia())
                .roles(normalizarRol(usuario.getRol())) // 🔥 CLAVE PARA EVITAR 403
                .disabled(!usuario.isEnabled())
                .build();
    }

    /**
     * Normaliza el rol para Spring Security:
     * - acepta "alumno", "ALUMNO", "profesor", etc.
     * - elimina ROLE_ si alguien lo manda por error
     */
    private String normalizarRol(String rol) {
        return rol
                .trim()
                .toUpperCase()
                .replace("ROLE_", "");
    }
}