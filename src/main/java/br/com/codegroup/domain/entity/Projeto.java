package br.com.codegroup.domain.entity;

import br.com.codegroup.domain.enumeration.ProjetoRiscoEnum;
import br.com.codegroup.domain.enumeration.ProjetoStatusEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "projeto")
public class Projeto implements Serializable {

    @Id
    @SequenceGenerator(name = "projeto_id_seq", sequenceName = "projeto_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "projeto_id_seq")
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "data_inicio", nullable = false)
    private Date dataInicio;

    @Column(name = "data_previsao_fim", nullable = false)
    private Date dataPrevisaoFim;

    @Column(name = "data_fim", nullable = false)
    private Date dataFim;

    @Column(name = "descricao", nullable = false)
    private String descricao;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ProjetoStatusEnum status;

    @Column(name = "orcamento", nullable = false)
    private Double orcamento;

    @Enumerated(EnumType.STRING)
    @Column(name = "risco", nullable = false)
    private ProjetoRiscoEnum risco;

    @OneToOne
    @JoinColumn(name = "idgerente", nullable = true)
    private Pessoa gerente;

    @ManyToMany
    @JoinTable(
            name = "membros",
            joinColumns = @JoinColumn(name = "idprojeto"),
            inverseJoinColumns = @JoinColumn(name = "idpessoa"))
    private List<Pessoa> membros;
}
