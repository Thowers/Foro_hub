package foro_hub.foro.domain.user;

import foro_hub.foro.domain.user.data.CreateUser;
import foro_hub.foro.domain.user.data.ModifyUser;
import foro_hub.foro.domain.user.perfil.Profile;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "users")
@EqualsAndHashCode(of = "id")
public class User implements UserDetails{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    @Column(name = "correoElectronico")
    private String correo;

    private String contrasena;

    @ManyToMany(fetch = FetchType.EAGER,targetEntity = Profile.class, cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "usuario_perfiles",
            joinColumns = @JoinColumn(name = "id_usuario"),
            inverseJoinColumns = @JoinColumn(name = "id_perfil")
    )
    private Set<Profile> roles;

    public User(CreateUser createUser) {
        this.nombre = createUser.nombre();
        this.correo = createUser.email();
        this.contrasena = createUser.contrasena();
        this.roles = createUser.roles().stream().map(rol -> new Profile(rol))
                .collect(Collectors.toSet());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return contrasena;
    }

    @Override
    public String getUsername() {
        return nombre;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void actualizardatos(ModifyUser datos, PasswordEncoder encoder) {
        if (datos.nombre() != null) this.nombre = datos.nombre();
        if (datos.correo() != null) this.correo = datos.correo();
        if (datos.contrasena() != null) this.contrasena = encoder.encode(datos.contrasena());
        if (datos.roles() != null)this.roles = datos.roles().stream().map(rol -> new Profile(rol)).collect(Collectors.toSet());
    }
}
