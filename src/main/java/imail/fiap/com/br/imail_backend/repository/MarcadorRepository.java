package imail.fiap.com.br.imail_backend.repository;

import imail.fiap.com.br.imail_backend.model.Marcador;
import imail.fiap.com.br.imail_backend.model.Usuario;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MarcadorRepository extends JpaRepository<Marcador, Integer> {

    List<Marcador> findByNmMarcador(String nmMarcador);

    List<Marcador> findByUsuario_IdUsuario(Integer idUsuario);

    @Modifying
    @Transactional
    void deleteByUsuario(Usuario usuario);
}
