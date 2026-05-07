package com.duoc.learningplatform.autenticacion_service.config;

import com.duoc.learningplatform.autenticacion_service.model.Usuario;
import com.duoc.learningplatform.autenticacion_service.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AdminInitializer {

    @Bean
    CommandLineRunner initAdmin(
            UsuarioRepository usuarioRepository,
            PasswordEncoder passwordEncoder
    ) {

        return args -> {

            boolean existeAdmin =
                    usuarioRepository.findByCorreo("admin@duoc.cl").isPresent();

            if (!existeAdmin) {

                Usuario admin = new Usuario();

                admin.setNombre("Administrador");
                admin.setCorreo("admin@duoc.cl");

                admin.setContrasenia(
                        passwordEncoder.encode("Admin123")
                );

                admin.setRol("ADMIN");

                usuarioRepository.save(admin);

                System.out.println("ADMIN CREADO");
            }
        };
    }
}