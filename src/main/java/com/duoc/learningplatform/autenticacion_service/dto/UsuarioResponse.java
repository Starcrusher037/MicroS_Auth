package com.duoc.learningplatform.autenticacion_service.dto;

public class UsuarioResponse {

    private Long id;
    private String nombre;
    private String correo;
    private String rol;

    public UsuarioResponse(Long id, String nombre, String correo, String rol) {
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
        this.rol = rol;
    }

    public Long getId() { return id; }
    public String getNombre() { return nombre; }
    public String getCorreo() { return correo; }
    public String getRol() { return rol; }
}