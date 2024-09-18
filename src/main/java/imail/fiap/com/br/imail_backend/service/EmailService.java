package imail.fiap.com.br.imail_backend.service;

import imail.fiap.com.br.imail_backend.model.EmailEntity;
import imail.fiap.com.br.imail_backend.dto.EmailRequest;
import imail.fiap.com.br.imail_backend.model.Marcador;
import imail.fiap.com.br.imail_backend.model.Usuario;
import imail.fiap.com.br.imail_backend.repository.EmailRepository;
import imail.fiap.com.br.imail_backend.repository.MarcadorRepository;
import imail.fiap.com.br.imail_backend.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmailService {

    @Autowired
    private EmailRepository emailRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private SpamService spamService;

    @Autowired
    private MarcadorRepository marcadorRepository;

    @Autowired
    private EmailGeneratorService emailGeneratorService;

    public EmailEntity generateRandomEmail(Integer userId) {
        Usuario usuario = usuarioRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        String randomSender = emailGeneratorService.generateRandomEmailAddress();
        String randomSubject = emailGeneratorService.generateRandomSubject();
        String randomMessage = emailGeneratorService.generateRandomMessage();

        EmailEntity email = new EmailEntity();
        email.setAssuntoEmail(randomSubject);
        email.setDestinatarioEmail(usuario.getEmailUsuario());
        email.setRemetenteEmail(randomSender);
        email.setCorpoDoEmail(randomMessage);
        email.setDtRecebEmail(emailGeneratorService.generateRandomDate());
        email.setUsuario(usuario);

        // Inicializa a lista de marcadores
        email.setMarcadores(new ArrayList<>());

        return save(email);
    }

    public List<EmailEntity> findAll() {
        return emailRepository.findAll();
    }

    public Optional<EmailEntity> findById(Integer id) {
        return emailRepository.findById(id);
    }

    public List<EmailEntity> findByUserId(Integer userId) {
        Usuario usuario = usuarioRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        return emailRepository.findByUsuario(usuario);
    }

    public EmailEntity save(EmailEntity email) {
        Optional<EmailEntity> existingEmail = emailRepository.findByAssuntoEmailAndDestinatarioEmailAndRemetenteEmailAndDtRecebEmail(
                email.getAssuntoEmail(), email.getDestinatarioEmail(), email.getRemetenteEmail(), email.getDtRecebEmail());

        if (existingEmail.isPresent()) {
            throw new RuntimeException("Este e-mail já foi enviado.");
        }

        int emailsEnviados = emailRepository.countByRemetenteEmail(email.getRemetenteEmail());

        if (spamService.isSpam(email, emailsEnviados)) {
            Marcador spamMarcador = getOrCreateSpamMarcador();
            email.getMarcadores().add(spamMarcador);
        }

        return emailRepository.save(email);
    }

    public EmailEntity createEmail(EmailRequest request) {
        Usuario usuario = usuarioRepository.findById(request.getUsuario().getIdUsuario())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        EmailEntity email = new EmailEntity();
        email.setAssuntoEmail(request.getAssuntoEmail());
        email.setDestinatarioEmail(request.getDestinatarioEmail());
        email.setRemetenteEmail(request.getRemetenteEmail());
        email.setCorpoDoEmail(request.getCorpoDoEmail());
        email.setDtRecebEmail(request.getDtRecebEmail());
        email.setUsuario(usuario);

        List<Marcador> marcadores = request.getMarcadores().stream()
                .map(marcadorRequest -> getOrCreateMarcador(marcadorRequest.getNmMarcador()))
                .collect(Collectors.toList());

        email.setMarcadores(marcadores);

        return emailRepository.save(email);
    }

    private Marcador getOrCreateMarcador(String nomeMarcador) {
        return marcadorRepository.findByNmMarcador(nomeMarcador)
                .stream()
                .findFirst()
                .orElseGet(() -> criarMarcador(nomeMarcador));
    }

    private Marcador criarMarcador(String nomeMarcador) {
        Marcador novoMarcador = new Marcador();
        novoMarcador.setNmMarcador(nomeMarcador);
        return marcadorRepository.save(novoMarcador);
    }

    private Marcador getOrCreateSpamMarcador() {
        return marcadorRepository.findByNmMarcador("usuarioSpam")
                .stream()
                .findFirst()
                .orElseGet(() -> criarMarcador("usuarioSpam"));
    }

    public EmailEntity update(Integer id, EmailEntity emailDetails) {
        return emailRepository.findById(id).map(email -> {
            email.setAssuntoEmail(emailDetails.getAssuntoEmail());
            email.setDestinatarioEmail(emailDetails.getDestinatarioEmail());
            email.setRemetenteEmail(emailDetails.getRemetenteEmail());
            email.setCorpoDoEmail(emailDetails.getCorpoDoEmail());
            email.setDtRecebEmail(emailDetails.getDtRecebEmail());

            List<Marcador> marcadores = emailDetails.getMarcadores().stream()
                    .map(marcador -> {
                        if (marcador.getIdMarcador() != null) {
                            return marcadorRepository.findById(marcador.getIdMarcador())
                                    .orElseGet(() -> criarMarcador(marcador.getNmMarcador()));
                        } else {
                            return getOrCreateMarcador(marcador.getNmMarcador());
                        }
                    })
                    .collect(Collectors.toList());

            email.setMarcadores(marcadores);

            return emailRepository.save(email);
        }).orElseThrow(() -> new RuntimeException("Email não encontrado com o id: " + id));
    }

    public void deleteById(Integer id) {
        emailRepository.deleteById(id);
    }
}
