package org.segonzalezz.dominio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UsuarioRepository extends JpaRepository<Long, Usuario> {
    UserDetails findByUsername(String username);
}
