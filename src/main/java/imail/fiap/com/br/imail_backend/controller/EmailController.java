package imail.fiap.com.br.imail_backend.controller;

import imail.fiap.com.br.imail_backend.model.EmailEntity;
import imail.fiap.com.br.imail_backend.dto.EmailRequest;
import imail.fiap.com.br.imail_backend.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/emails")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @GetMapping
    public ResponseEntity<List<EmailEntity>> getAllEmails() {
        List<EmailEntity> emails = emailService.findAll();
        return ResponseEntity.ok(emails);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmailEntity> getEmailById(@PathVariable Integer id) {
        Optional<EmailEntity> email = emailService.findById(id);
        return email.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<EmailEntity>> getEmailsByUserId(@PathVariable Integer userId) {
        List<EmailEntity> emails = emailService.findByUserId(userId);
        if (emails.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(emails);
    }

    @PostMapping
    public ResponseEntity<EmailEntity> createEmail(@RequestBody EmailRequest emailRequest) {
        EmailEntity savedEmail = emailService.createEmail(emailRequest);
        return ResponseEntity.ok(savedEmail);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmailEntity> updateEmail(@PathVariable Integer id, @RequestBody EmailEntity updatedEmail) {
        EmailEntity savedEmail = emailService.update(id, updatedEmail);
        return ResponseEntity.ok(savedEmail);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmail(@PathVariable Integer id) {
        emailService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/generate")
    public ResponseEntity<EmailEntity> generateRandomEmail(@RequestParam Integer userId) {
        EmailEntity randomEmail = emailService.generateRandomEmail(userId);
        return ResponseEntity.ok(randomEmail);
    }
}
