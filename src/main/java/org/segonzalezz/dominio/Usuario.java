package org.segonzalezz.dominio;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.*;
import lombok.*;
import org.segonzalezz.dto.usuario.DatosRegistroUsuarioDTO;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Table(name="usuarios")
@Entity(name="Usuario")
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String apellido;
    private String password;
    @Column(unique = true, nullable = false)
    private String correoElectronico;

    @Enumerated(EnumType.STRING)
    private Role role;


    @ManyToMany
    @JoinTable(
            name = "usuario_perfil",
            joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "perfil_id")
    )
    private Set<Perfil> perfiles;


    public Usuario(DatosRegistroUsuarioDTO datos, String hashedPassword) {
        this.nombre = datos.nombre();
        this.apellido = datos.apellido();
        this.password = hashedPassword;
        this.correoElectronico = datos.correoElectronico();
        this.role = datos.role();
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public Long getId() {
        return id;
    }
    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }


    public Role getRole() {
        return role;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return perfiles.stream()
                .map(perfil -> new SimpleGrantedAuthority("ROLE_" + perfil.getNombre()))
                .collect(Collectors.toSet());
    }

    @Override
    public String getUsername() {
        return this.correoElectronico;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Cambiar según lógica si es necesario.
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Cambiar según lógica si es necesario.
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Cambiar según lógica si es necesario.
    }

    @Override
    public boolean isEnabled() {
        return true; // Cambiar según lógica si es necesario.
    }


}
