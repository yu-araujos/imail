package imail.fiap.com.br.imail_backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "t_usuario")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "usuario_seq")
    @SequenceGenerator(name = "usuario_seq", sequenceName = "seq_usuario_id", allocationSize = 1)
    @Column(name = "id_usuario", updatable = false)
    private Integer idUsuario;

    @NotBlank
    @Email
    @Size(max = 100)
    @Column(name = "email_usuario")
    private String emailUsuario;

    @NotBlank
    @Column(name = "senha_usuario")
    private String senhaUsuario;

    @NotBlank
    @Size(max = 20)
    @Column(name = "tema_cor_usuario")
    private String temaCorUsuario;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore  // Evita recursão ao serializar os emails
    private List<EmailEntity> listaDeEmails;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore  // Evita recursão ao serializar os marcadores
    private List<Marcador> listaDeMarcadores;
}
