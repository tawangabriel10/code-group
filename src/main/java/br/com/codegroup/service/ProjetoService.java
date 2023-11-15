package br.com.codegroup.service;

import br.com.codegroup.domain.dto.AlterarStatusDTO;
import br.com.codegroup.domain.dto.PessoaDTO;
import br.com.codegroup.domain.dto.ProjetoDTO;
import br.com.codegroup.domain.entity.Projeto;
import br.com.codegroup.domain.enumeration.ProjetoStatusEnum;
import br.com.codegroup.domain.exceptions.ProjectLockedException;
import br.com.codegroup.domain.exceptions.ProjectNotFoundException;
import br.com.codegroup.repository.ProjetoRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Validated
@RequiredArgsConstructor
@Transactional
public class ProjetoService {

    private final ProjetoRepository repository;
    private final ModelMapper modelMapper;
    private final PessoaService pessoaService;

    public ProjetoDTO salvar(ProjetoDTO projetoDTO) {
        final PessoaDTO gerente = this.pessoaService.consultarPorId(projetoDTO.getGerente().getId());
        projetoDTO.setGerente(gerente);

        final Projeto projeto = modelMapper.map(projetoDTO, Projeto.class);
        projeto.setStatus(ProjetoStatusEnum.EM_ANALISE);

        final Projeto projetoSalvo = repository.save(projeto);
        return modelMapper.map(projetoSalvo, ProjetoDTO.class);
    }

    public ProjetoDTO alterar(Long id, ProjetoDTO projetoDTO) {
        Optional<Projeto> projetoOp = repository.findById(id);

        if (projetoOp.isEmpty()) {
            throw new ProjectNotFoundException("Projeto não encontrado");
        }

        final PessoaDTO gerente = this.pessoaService.consultarPorId(projetoDTO.getGerente().getId());
        projetoDTO.setGerente(gerente);
        projetoDTO.setId(id);
        final Projeto projeto = modelMapper.map(projetoDTO, Projeto.class);

        final Projeto projetoSalvo = repository.save(projeto);
        return modelMapper.map(projetoSalvo, ProjetoDTO.class);
    }

    public ProjetoDTO consultarPorId(Long id) {
        Optional<Projeto> projetoOp = repository.findById(id);

        if (projetoOp.isEmpty()) {
            throw new ProjectNotFoundException("Projeto não encontrado");
        }

        return modelMapper.map(projetoOp.get(), ProjetoDTO.class);
    }

    public List<ProjetoDTO> buscarTodos() {
        List<Projeto> projetos = repository.findAll();
        return projetos.stream()
                .map(projeto -> this.modelMapper.map(projeto, ProjetoDTO.class))
                .collect(Collectors.toList());
    }

    public void excluir(Long id) {
        Optional<Projeto> projetoOp = repository.findById(id);

        if (projetoOp.isEmpty()) {
            throw new ProjectNotFoundException("Projeto não encontrado");
        }

        validarStatusExcluir(projetoOp.get());

        this.repository.deleteById(id);
    }

    private void validarStatusExcluir(Projeto projeto) {
        if (ProjetoStatusEnum.INICIADO.equals(projeto.getStatus())
            || ProjetoStatusEnum.EM_ANDAMENTO.equals(projeto.getStatus())
            || ProjetoStatusEnum.ENCERRADO.equals(projeto.getStatus())) {
            throw new ProjectLockedException(String.format("Projeto com status %s não pode ser excluido", projeto.getStatus().getDescricao()));
        }
    }

    public ProjetoDTO alterarStatus(Long id, AlterarStatusDTO alterarStatusDTO) {
        Optional<Projeto> projetoOp = repository.findById(id);

        if (projetoOp.isEmpty()) {
            throw new ProjectNotFoundException("Projeto não encontrado");
        }

        final Projeto projeto = projetoOp.get();
        projeto.setStatus(alterarStatusDTO.getStatus());

        final Projeto projetoSalvo = repository.save(projeto);
        return modelMapper.map(projetoSalvo, ProjetoDTO.class);
    }
}
