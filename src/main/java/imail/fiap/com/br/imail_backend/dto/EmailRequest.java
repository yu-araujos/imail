package imail.fiap.com.br.imail_backend.dto;

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

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class EmailRequest {

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

    @NotNull
    private UsuarioRequest usuario;

    private List<MarcadorRequest> marcadores;
}
