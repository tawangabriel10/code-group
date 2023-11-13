package br.com.codegroup.domain.dto;

import br.com.codegroup.domain.entity.Pessoa;
import br.com.codegroup.domain.enumeration.ProjetoRiscoEnum;
import br.com.codegroup.domain.enumeration.ProjetoStatusEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProjetoDTO implements Serializable {
    private Long id;

    @NotBlank(message = "Nome é obrigatório")
    @Size(min = 3, max = 200, message = "Nome deve ter entre 3 e 200 caracteres")
    private String nome;

    @NotNull(message = "Data início é obrigatória")
    private Date dataInicio;

    private Date dataPrevisaoFim;

    private Date dataFim;

    @NotBlank(message = "Descrição é obrigatória")
    @Size(min = 3, max = 300, message = "Descrição deve ter entre 3 e 300 caracteres")
    private String descricao;

    @NotNull(message = "Status é obrigatório")
    private ProjetoStatusEnum status;

    @NotNull(message = "Orçamento é obrigatório")
    private Double orcamento;

    @NotNull(message = "Risco é obrigatório")
    private ProjetoRiscoEnum risco;

    @NotNull(message = "Gerente é obrigatório")
    private PessoaDTO gerente;

    private List<PessoaDTO> membros;
}
