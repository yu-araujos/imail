package imail.fiap.com.br.imail_backend.service;

import imail.fiap.com.br.imail_backend.model.Marcador;
import imail.fiap.com.br.imail_backend.repository.MarcadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MarcadorService {

    @Autowired
    private MarcadorRepository marcadorRepository;

    public List<Marcador> findAll() {
        return marcadorRepository.findAll();
    }

    public Optional<Marcador> findById(Integer idMarcador) {
        return marcadorRepository.findById(idMarcador);
    }

    public List<Marcador> findByUsuario(Integer idUsuario) {
        return marcadorRepository.findByUsuario_IdUsuario(idUsuario);
    }

    public Marcador save(Marcador marcador) {
        return marcadorRepository.save(marcador);
    }

    public void deleteById(Integer idMarcador) {
        marcadorRepository.deleteById(idMarcador);
    }
}
