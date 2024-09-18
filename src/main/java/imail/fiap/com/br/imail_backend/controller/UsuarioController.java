package imail.fiap.com.br.imail_backend.controller;

import imail.fiap.com.br.imail_backend.dto.LoginRequest;
import imail.fiap.com.br.imail_backend.model.Usuario;
import imail.fiap.com.br.imail_backend.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<List<Usuario>> getAllUsuarios() {
        List<Usuario> usuarios = usuarioService.findAll();
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> getUsuarioById(@PathVariable Integer id) {
        Optional<Usuario> usuario = usuarioService.findById(id);
        return usuario.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/register")
    public ResponseEntity<Usuario> registerUsuario(@RequestBody Usuario usuario) {
        Usuario savedUsuario = usuarioService.save(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUsuario);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> updateUsuario(@PathVariable Integer id, @RequestBody Usuario usuarioDetails) {
        Optional<Usuario> usuarioOptional = usuarioService.findById(id);
        if (usuarioOptional.isPresent()) {
            Usuario usuario = usuarioOptional.get();
            usuario.setEmailUsuario(usuarioDetails.getEmailUsuario());
            usuario.setSenhaUsuario(usuarioDetails.getSenhaUsuario());
            usuario.setTemaCorUsuario(usuarioDetails.getTemaCorUsuario());
            Usuario updatedUsuario = usuarioService.save(usuario);
            return ResponseEntity.ok(updatedUsuario);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUsuario(@PathVariable Integer id) {
        usuarioService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/verificar")
    public ResponseEntity<String> verificarUsuario(@RequestParam String emailUsuario) {
        boolean existe = usuarioService.verificarSeUsuarioExiste(emailUsuario);
        if (existe) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Usuário já existe");
        } else {
            return ResponseEntity.ok("Usuário criado");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        Optional<Usuario> usuarioOpt = usuarioService.findByEmail(loginRequest.getEmail());

        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();

            if (usuario.getSenhaUsuario().equals(loginRequest.getSenha())) {
                // Retornar apenas o ID do usuário e o tema
                String responseMessage = "Login realizado com sucesso. " +
                        "ID do usuário: " + usuario.getIdUsuario() +
                        "%Tema: " + usuario.getTemaCorUsuario();
                return ResponseEntity.ok(responseMessage);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Senha incorreta");
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado");
        }
    }
}
