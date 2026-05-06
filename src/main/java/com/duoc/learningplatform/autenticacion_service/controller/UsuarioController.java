package com.duoc.learningplatform.autenticacion_service.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.duoc.learningplatform.autenticacion_service.dto.RegisterRequest;
import com.duoc.learningplatform.autenticacion_service.dto.UsuarioResponse;
import com.duoc.learningplatform.autenticacion_service.dto.UpdateUsuarioRequest;
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
    public ResponseEntity<List<UsuarioResponse>> obtenerTodosUsuario() {

        List<UsuarioResponse> lista = usuarioService.obtenerTodosUsuarios()
                .stream()
                .map(u -> new UsuarioResponse(
                        u.getId(),
                        u.getNombre(),
                        u.getCorreo(),
                        u.getRol()
                ))
                .toList();

        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponse> obtenerUsuarioPorId(@PathVariable @Positive Long id) {

        Usuario u = usuarioService.obtenerUsuarioPorId(id);

        return ResponseEntity.ok(new UsuarioResponse(
                u.getId(),
                u.getNombre(),
                u.getCorreo(),
                u.getRol()
        ));
    }

    @PostMapping
    public ResponseEntity<UsuarioResponse> registrarUsuario(@Valid @RequestBody RegisterRequest request) {

        Usuario usuarioCreado = usuarioService.registrarUsuario(request);

        URI ruta = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(usuarioCreado.getId())
                .toUri();

        return ResponseEntity.created(ruta).body(new UsuarioResponse(
                usuarioCreado.getId(),
                usuarioCreado.getNombre(),
                usuarioCreado.getCorreo(),
                usuarioCreado.getRol()
        ));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponse> actualizarUsuario(
            @PathVariable @Positive Long id,
            @Valid @RequestBody UpdateUsuarioRequest request) {

        Usuario actualizado = usuarioService.modificarUsuario(id, request);

        return ResponseEntity.ok(new UsuarioResponse(
                actualizado.getId(),
                actualizado.getNombre(),
                actualizado.getCorreo(),
                actualizado.getRol()
        ));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable @Positive Long id){
        usuarioService.eliminarUsuario(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/exists")
    public ResponseEntity<Boolean> existsUserById(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.existeUsuarioPorId(id));
    }

    @GetMapping("/{id}/role")
    public ResponseEntity<String> getUserRole(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.obtenerRolPorId(id));
    }
}