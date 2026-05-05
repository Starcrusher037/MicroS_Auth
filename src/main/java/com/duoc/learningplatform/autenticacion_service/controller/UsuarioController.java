package com.duoc.learningplatform.autenticacion_service.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.duoc.learningplatform.autenticacion_service.model.Usuario;
import com.duoc.learningplatform.autenticacion_service.service.UsuarioService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<List<Usuario>> obtenerTodosUsuario() {
        return ResponseEntity.ok(usuarioService.obtenerTodosUsuarios());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> obtenerUsuarioPorId(@PathVariable @Positive Long id) {
        return ResponseEntity.ok(usuarioService.obtenerUsuarioPorId(id));
    }

    @PostMapping
    public ResponseEntity<Usuario> registrarUsuario(@Valid @RequestBody Usuario usuario) {
        Usuario usuarioCreado = usuarioService.registrarUsuario(usuario);

        URI ruta = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(usuarioCreado.getId())
            .toUri();

        return ResponseEntity.created(ruta).body(usuarioCreado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> actualizarUsuario(@PathVariable @Positive Long id,
                                                     @Valid @RequestBody Usuario usuario) {
        return ResponseEntity.ok(usuarioService.modificarUsuario(id, usuario));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable @Positive Long id){
        usuarioService.eliminarUsuario(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/exists")
    public ResponseEntity<Boolean> existsUserById(@PathVariable Long id) {
        boolean exists = usuarioService.existeUsuarioPorId(id);
        return ResponseEntity.ok(exists);
    }

    @GetMapping("/{id}/role")
    public String getUserRole(@PathVariable Long id) {
        return usuarioService.obtenerRolPorId(id);
    }
}