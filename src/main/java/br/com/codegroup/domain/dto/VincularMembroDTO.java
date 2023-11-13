package br.com.codegroup.domain.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class VincularMembroDTO implements Serializable {

    @NotNull(message = "Projeto é obrigatório")
    private Long idProjeto;

    @NotNull(message = "Pessoa é obrigatório")
    private Long idPessoa;
}
