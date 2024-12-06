package org.segonzalezz.dto.usuario;

import org.segonzalezz.dominio.Role;

public record DatosRespuestaUsuario(
        Long id,
        String nombre,
        String apellido,
        String correoElectronico,
        Role role
) {
}
