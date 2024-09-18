package imail.fiap.com.br.imail_backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "t_marcador")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Marcador {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "marcador_seq")
    @SequenceGenerator(name = "marcador_seq", sequenceName = "SEQ_MARCADOR", allocationSize = 1)
    @Column(name = "id_marcador", updatable = false, nullable = false)
    private Integer idMarcador;

    @NotBlank
    @Size(max = 20)
    private String nmMarcador;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "t_usuario_id_usuario")
    @JsonIgnore
    private Usuario usuario;

    @ManyToMany(mappedBy = "marcadores")
    @JsonIgnore
    private List<EmailEntity> emails;
}
