package imail.fiap.com.br.imail_backend.service;

import imail.fiap.com.br.imail_backend.model.Usuario;
import imail.fiap.com.br.imail_backend.repository.EmailRepository;
import imail.fiap.com.br.imail_backend.repository.MarcadorRepository;
import imail.fiap.com.br.imail_backend.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private EmailRepository emailRepository;

    @Autowired
    private MarcadorRepository marcadorRepository;

    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> findById(Integer idUsuario) {
        return usuarioRepository.findById(idUsuario);
    }

    public Optional<Usuario> findByEmail(String email) {
        return usuarioRepository.findByEmailUsuario(email);
    }

    public Usuario save(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public void deleteById(Integer idUsuario) {
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado."));

        emailRepository.deleteByUsuario(usuario);
        marcadorRepository.deleteByUsuario(usuario);

        usuarioRepository.deleteById(idUsuario);
    }

    public boolean verificarSeUsuarioExiste(String emailUsuario) {
        return usuarioRepository.findByEmailUsuario(emailUsuario).isPresent();
    }
}
