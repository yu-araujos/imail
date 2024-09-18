package imail.fiap.com.br.imail_backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "t_email")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class EmailEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "email_seq")
    @SequenceGenerator(name = "email_seq", sequenceName = "email_seq", allocationSize = 1)
    @Column(name = "id_email", updatable = false, nullable = false)
    private Long idEmail;

    @Size(max = 1000)
    private String assuntoEmail;

    @NotBlank
    @Size(max = 100)
    private String destinatarioEmail;

    @NotBlank
    @Size(max = 100)
    private String remetenteEmail;

    @NotBlank
    private String corpoDoEmail;

    @NotNull
    private LocalDate dtRecebEmail;

    @ManyToOne
    @JoinColumn(name = "t_usuario_id_usuario", nullable = false)
    private Usuario usuario;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "t_marcador_email",
            joinColumns = @JoinColumn(name = "id_email"),
            inverseJoinColumns = @JoinColumn(name = "id_marcador")
    )
    private List<Marcador> marcadores;
}
