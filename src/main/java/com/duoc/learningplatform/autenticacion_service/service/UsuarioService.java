package com.duoc.learningplatform.autenticacion_service.service;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.duoc.learningplatform.autenticacion_service.dto.RegisterRequest;
import com.duoc.learningplatform.autenticacion_service.dto.UpdateUsuarioRequest;
import com.duoc.learningplatform.autenticacion_service.exception.BadRequestException;
import com.duoc.learningplatform.autenticacion_service.exception.NotFoundException;
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
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado"));
    }

    public Usuario registrarUsuario(RegisterRequest request){

        if (usuarioRepository.findByCorreo(request.getCorreo()).isPresent()) {
            throw new BadRequestException("El correo ya está registrado");
        }

        if (!request.getRol().equals("ALUMNO") && !request.getRol().equals("PROFESOR")) {
            throw new BadRequestException("Rol inválido");
        }

        Usuario usuario = new Usuario();
        usuario.setNombre(request.getNombre());
        usuario.setCorreo(request.getCorreo());
        usuario.setContrasenia(passwordEncoder.encode(request.getContrasenia()));
        usuario.setRol(request.getRol());

        return usuarioRepository.save(usuario);
    }

    public Usuario modificarUsuario(Long id, UpdateUsuarioRequest request){

        Usuario usuario = obtenerUsuarioPorId(id);

        if (!usuario.getCorreo().equals(request.getCorreo())
                && usuarioRepository.findByCorreo(request.getCorreo()).isPresent()) {
            throw new BadRequestException("El correo ya está registrado");
        }

        usuario.setNombre(request.getNombre());
        usuario.setCorreo(request.getCorreo());

        return usuarioRepository.save(usuario);
    }

    public void eliminarUsuario(Long id){
        if(!usuarioRepository.existsById(id)){
            throw new NotFoundException("Usuario no encontrado");
        }
        usuarioRepository.deleteById(id);
    }

    public String obtenerRolPorId(Long id) {
        return usuarioRepository.findById(id)
        .map(Usuario::getRol)
        .orElseThrow(() -> new NotFoundException("Usuario no encontrado"));
    }

    public boolean existeUsuarioPorId(Long id) {
        return usuarioRepository.existsById(id);
    }
}