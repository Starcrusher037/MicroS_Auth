package com.duoc.learningplatform.autenticacion_service.dto;
import jakarta.validation.constraints.NotBlank;

public class UpdateUsuarioRequest {

    @NotBlank
    private String nombre;

    @NotBlank
    private String correo;

    public String getNombre() { return nombre; }
    public String getCorreo() { return correo; }
}