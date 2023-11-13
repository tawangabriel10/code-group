package br.com.codegroup.domain.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

@Getter
@AllArgsConstructor
public enum ProjetoStatusEnum implements Serializable {

    EM_ANALISE("em análise"),
    ANALISE_REALIZADA("análise realizada"),
    ANALISE_APROVADA("análise aprovada"),
    INICIADO("iniciado"),
    PLANEJADO("planejado"),
    EM_ANDAMENTO("em andamento"),
    ENCERRADO("encerrado"),
    CANCELADO("cancelado");

    private final String descricao;

}
