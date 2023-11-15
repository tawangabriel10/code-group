package br.com.codegroup.service;

import br.com.codegroup.domain.dto.VincularMembroDTO;
import br.com.codegroup.domain.entity.Pessoa;
import br.com.codegroup.domain.entity.Projeto;
import br.com.codegroup.domain.exceptions.PersonNotEmployeeException;
import br.com.codegroup.domain.exceptions.PersonNotFoundException;
import br.com.codegroup.domain.exceptions.ProjectNotFoundException;
import br.com.codegroup.repository.PessoaRepository;
import br.com.codegroup.repository.ProjetoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;

@Service
@Validated
@RequiredArgsConstructor
@Transactional
public class MembrosService {

    private final ProjetoRepository projetoRepository;

    private final PessoaRepository pessoaRepository;

    public void vincular(VincularMembroDTO vincularMembroDTO) {
        final Optional<Pessoa> pessoaOp = pessoaRepository.findById(vincularMembroDTO.getIdPessoa());

        if (pessoaOp.isEmpty()) {
            throw new PersonNotFoundException("Pessoa não encontrada");
        }

        final Pessoa pessoa = pessoaOp.get();
        if (!pessoa.isFuncionario()) {
            throw new PersonNotEmployeeException("Pessoa não é um funcionário");
        }

        final Optional<Projeto> projetoOp = projetoRepository.findById(vincularMembroDTO.getIdProjeto());

        if (projetoOp.isEmpty()) {
            throw new ProjectNotFoundException("Projeto não encontrado");
        }

        final Projeto projeto = projetoOp.get();

        if (Objects.isNull(projeto.getMembros())) {
            projeto.setMembros(new ArrayList<>());
        }

        projeto.getMembros().add(pessoa);
        projetoRepository.save(projeto);
    }
}
