package imail.fiap.com.br.imail_backend.repository;

import imail.fiap.com.br.imail_backend.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    // Buscar usuário por ID
    Optional<Usuario> findById(Integer idUsuario);

    // Buscar usuário por email
    Optional<Usuario> findByEmailUsuario(String emailUsuario);
}