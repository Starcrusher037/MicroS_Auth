package com.duoc.learningplatform.autenticacion_service.dto;

import jakarta.validation.constraints.NotBlank;

public class RegisterRequest {

    @NotBlank
    private String nombre;

    @NotBlank
    private String correo;

    @NotBlank
    private String contrasenia;

    @NotBlank
    private String rol;

    public String getNombre() { return nombre; }
    public String getCorreo() { return correo; }
    public String getContrasenia() { return contrasenia; }
    public String getRol() { return rol; }
}