package com.duoc.learningplatform.autenticacion_service.service;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.duoc.learningplatform.autenticacion_service.model.Usuario;
import com.duoc.learningplatform.autenticacion_service.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public List<Usuario> obtenerTodosUsuarios() {
        return usuarioRepository.findAll();
    }

    public Usuario obtenerUsuarioPorId(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }

    public Usuario registrarUsuario(Usuario usuario){
        usuario.setContrasenia(passwordEncoder.encode(usuario.getContrasenia()));
        return usuarioRepository.save(usuario);
    }

    public Usuario modificarUsuario(Long id, Usuario usuarioActualizado){
        Usuario usuario = obtenerUsuarioPorId(id);

        usuario.setNombre(usuarioActualizado.getNombre());
        usuario.setCorreo(usuarioActualizado.getCorreo());

        // si viene contraseña nueva, re-encriptar
        if (usuarioActualizado.getContrasenia() != null && !usuarioActualizado.getContrasenia().isBlank()) {
            usuario.setContrasenia(passwordEncoder.encode(usuarioActualizado.getContrasenia()));
        }

        usuario.setRol(usuarioActualizado.getRol());

        return usuarioRepository.save(usuario);
    }

    public void eliminarUsuario(Long id){
        if(!usuarioRepository.existsById(id)){
            throw new RuntimeException("Usuario no encontrado");
        }
        usuarioRepository.deleteById(id);
    }
}