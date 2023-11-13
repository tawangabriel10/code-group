package br.com.codegroup.service;

import br.com.codegroup.domain.dto.PessoaDTO;
import br.com.codegroup.domain.entity.Pessoa;
import br.com.codegroup.domain.exceptions.PersonNotFoundException;
import br.com.codegroup.repository.PessoaRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class PessoaService {

    private final PessoaRepository repository;

    private final ModelMapper modelMapper;

    public PessoaDTO salvar(PessoaDTO pessoaDTO) {
        final Pessoa pessoa = modelMapper.map(pessoaDTO, Pessoa.class);

        final Pessoa pessoaSalva = repository.save(pessoa);
        return modelMapper.map(pessoaSalva, PessoaDTO.class);
    }

    public PessoaDTO alterar(Long id, PessoaDTO pessoaDTO) {
        Optional<Pessoa> pessoaOp = repository.findById(id);

        if (pessoaOp.isEmpty()) {
            throw new PersonNotFoundException("Pessoa não encontrada");
        }

        pessoaDTO.setId(id);
        final Pessoa projeto = modelMapper.map(pessoaDTO, Pessoa.class);

        final Pessoa pessoaSalva = repository.save(projeto);
        return modelMapper.map(pessoaSalva, PessoaDTO.class);
    }

    public PessoaDTO consultarPorId(Long id) {
        final Optional<Pessoa> pessoaOp = repository.findById(id);

        if (pessoaOp.isEmpty()) {
            throw new PersonNotFoundException("Pessoa não encontrada");
        }

        return modelMapper.map(pessoaOp.get(), PessoaDTO.class);
    }

    public List<PessoaDTO> buscarTodos() {
        final List<Pessoa> pessoas = repository.findAll();
        return pessoas.stream()
                .map(pessoa -> this.modelMapper.map(pessoa, PessoaDTO.class))
                .collect(Collectors.toList());
    }

    public void excluir(Long id) {
        Optional<Pessoa> pessoaOp = repository.findById(id);

        if (pessoaOp.isEmpty()) {
            throw new PersonNotFoundException("Pessoa não encontrada");
        }

        this.repository.deleteById(id);
    }
}
