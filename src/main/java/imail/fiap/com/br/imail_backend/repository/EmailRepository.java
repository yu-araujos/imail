package imail.fiap.com.br.imail_backend.repository;

import imail.fiap.com.br.imail_backend.model.EmailEntity;
import imail.fiap.com.br.imail_backend.model.Usuario;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface EmailRepository extends JpaRepository<EmailEntity, Integer> {

    List<EmailEntity> findByDestinatarioEmail(String destinatarioEmail);

    List<EmailEntity> findByRemetenteEmail(String remetenteEmail);

    List<EmailEntity> findByUsuario(Usuario usuario);

    Optional<EmailEntity> findByAssuntoEmailAndDestinatarioEmailAndRemetenteEmailAndDtRecebEmail(
            String assuntoEmail, String destinatarioEmail, String remetenteEmail, LocalDate dtRecebEmail
    );

    int countByRemetenteEmail(String remetenteEmail);

    @Modifying
    @Transactional
    void deleteByUsuario(Usuario usuario);
}
