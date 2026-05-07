package com.duoc.learningplatform.autenticacion_service.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "usuario")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String nombre;

    @NotBlank
    @Column(nullable = false, unique = true)
    private String correo;

    @NotBlank
    @Column(nullable = false)
    private String contrasenia;

    @NotBlank
    @Pattern(regexp = "ALUMNO|PROFESOR|ADMIN",message = "El rol debe ser ALUMNO,PROFESOR o ADMIN")
    @Column(nullable = false)
    private String rol;

    @Column(nullable = false)
    private boolean enabled = true;
}