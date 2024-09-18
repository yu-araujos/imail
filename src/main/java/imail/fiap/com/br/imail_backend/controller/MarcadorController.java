package imail.fiap.com.br.imail_backend.controller;

import imail.fiap.com.br.imail_backend.model.Marcador;
import imail.fiap.com.br.imail_backend.service.MarcadorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/marcadores")
public class MarcadorController {

    @Autowired
    private MarcadorService marcadorService;

    @GetMapping
    public ResponseEntity<List<Marcador>> getAllMarcadores() {
        List<Marcador> marcadores = marcadorService.findAll();
        return ResponseEntity.ok(marcadores);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Marcador> getMarcadorById(@PathVariable Integer id) {
        Optional<Marcador> marcador = marcadorService.findById(id);
        return marcador.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Marcador> createMarcador(@RequestBody Marcador marcador) {
        Marcador savedMarcador = marcadorService.save(marcador);
        return ResponseEntity.ok(savedMarcador);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMarcador(@PathVariable Integer id) {
        marcadorService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
