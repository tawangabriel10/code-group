package br.com.codegroup.domain.dto;

import br.com.codegroup.domain.enumeration.ProjetoStatusEnum;
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
public class AlterarStatusDTO implements Serializable {

    @NotNull(message = "Status é obrigatório")
    private ProjetoStatusEnum status;
}
