package org.segonzalezz.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.segonzalezz.dominio.Usuario;
import org.segonzalezz.dominio.UsuarioRepository;
import org.segonzalezz.dto.usuario.DatosRegistroUsuarioDTO;
import org.segonzalezz.dto.usuario.DatosRespuestaUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @PostMapping
    @Transactional
    public ResponseEntity<DatosRespuestaUsuario> registrarUsuario(@RequestBody @Valid DatosRegistroUsuarioDTO datosRegistroUsuarioDTO, UriComponentsBuilder uriBuilder) {
        String hashedPassword = passwordEncoder.encode(datosRegistroUsuarioDTO.password());

        // Crear y guardar el usuario
        Usuario usuario = new Usuario(datosRegistroUsuarioDTO, hashedPassword);
        usuarioRepository.save(usuario);

        // Crear URI del recurso
        var uri = uriBuilder.path("/usuarios/{correoElectronico}").buildAndExpand(usuario.getCorreoElectronico()).toUri();

        // Retornar respuesta con detalles del usuario creado
        return ResponseEntity.created(uri).body(
                new DatosRespuestaUsuario(
                        usuario.getId(),
                        usuario.getNombre(),
                        usuario.getApellido(),
                        usuario.getCorreoElectronico(),
                        usuario.getRole()
                )
        );
    }
}
