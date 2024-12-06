package org.segonzalezz.dto.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.segonzalezz.dominio.Role;

public record DatosRegistroUsuarioDTO(
        @NotBlank
        String nombre,
        @NotBlank
        String apellido,
        @NotBlank
        @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
        String password,
        @NotBlank
        @Email
        String correoElectronico,
        @NotNull
        Role role
) {
}