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
                .roles(normalizarRol(usuario.getRol())) 
                .disabled(!usuario.isEnabled())
                .build();
    }

    private String normalizarRol(String rol) {
        return rol
                .trim()
                .toUpperCase()
                .replace("ROLE_", "");
    }
}