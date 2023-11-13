package br.com.codegroup.service;

import br.com.codegroup.domain.dto.PessoaDTO;
import br.com.codegroup.domain.entity.Pessoa;
import br.com.codegroup.domain.exceptions.PersonNotFoundException;
import br.com.codegroup.repository.PessoaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PessoaServiceTest {

    @InjectMocks private PessoaService service;
    @Mock private PessoaRepository repository;
    @Mock private ModelMapper modelMapper;

    @Test
    public void givenPessoaDTO_whenCallSalvar_thenSaveAndReturnPessoaDTO() {
        final PessoaDTO pessoaDTO = new PessoaDTO();
        final Pessoa pessoa = new Pessoa();

        when(modelMapper.map(any(PessoaDTO.class), eq(Pessoa.class))).thenReturn(pessoa);
        when(repository.save(any(Pessoa.class))).thenReturn(pessoa);
        when(modelMapper.map(any(Pessoa.class), eq(PessoaDTO.class))).thenReturn(pessoaDTO);

        final PessoaDTO response = service.salvar(pessoaDTO);

        assertNotNull(response);

        verify(modelMapper).map(pessoaDTO, Pessoa.class);
        verify(repository).save(pessoa);
        verify(modelMapper).map(pessoa, PessoaDTO.class);
    }

    @Test
    public void givenIdAndPessoaDTO_whenCallAlterar_thenUpdateAndReturnPessoaDTO() {
        final PessoaDTO pessoaDTO = new PessoaDTO();
        final Pessoa pessoa = new Pessoa();
        final Long pessoaId = 1L;

        when(repository.findById(anyLong())).thenReturn(Optional.of(pessoa));
        when(modelMapper.map(any(PessoaDTO.class), eq(Pessoa.class))).thenReturn(pessoa);
        when(repository.save(any(Pessoa.class))).thenReturn(pessoa);
        when(modelMapper.map(any(Pessoa.class), eq(PessoaDTO.class))).thenReturn(pessoaDTO);

        final PessoaDTO response = service.alterar(pessoaId, pessoaDTO);

        assertNotNull(response);

        verify(repository).findById(pessoaId);
        verify(modelMapper).map(pessoaDTO, Pessoa.class);
        verify(repository).save(pessoa);
        verify(modelMapper).map(pessoa, PessoaDTO.class);
    }

    @Test
    public void givenIdAndPessoaDTO_whenCallAlterar_thenPersonNotFoundAndThrowsException() {
        final PessoaDTO pessoaDTO = new PessoaDTO();
        final Long pessoaId = 1L;

        when(repository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(PersonNotFoundException.class, () -> service.alterar(pessoaId, pessoaDTO));

        verify(repository).findById(pessoaId);
    }

    @Test
    public void givenId_whenCallConsultarPorId_thenReturnPessoaDTO() {
        final PessoaDTO pessoaDTO = new PessoaDTO();
        final Pessoa pessoa = new Pessoa();
        final Long pessoaId = 1L;

        when(repository.findById(anyLong())).thenReturn(Optional.of(pessoa));
        when(modelMapper.map(any(Pessoa.class), eq(PessoaDTO.class))).thenReturn(pessoaDTO);

        final PessoaDTO response = service.consultarPorId(pessoaId);

        assertNotNull(response);

        verify(repository).findById(pessoaId);
        verify(modelMapper).map(pessoa, PessoaDTO.class);
    }

    @Test
    public void givenId_whenCallConsultarPorId_thenReturnEmptyAndThrowException() {
        final Long pessoaId = 1L;

        when(repository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(PersonNotFoundException.class, () -> service.consultarPorId(pessoaId));

        verify(repository).findById(pessoaId);
    }

    @Test
    public void given_whenCallBuscarTodos_thenReturnListPessoaDTO() {
        final PessoaDTO pessoaDTO = new PessoaDTO();
        final Pessoa pessoa = new Pessoa();

        when(repository.findAll()).thenReturn(Collections.singletonList(pessoa));
        when(modelMapper.map(any(Pessoa.class), eq(PessoaDTO.class))).thenReturn(pessoaDTO);

        final List<PessoaDTO> response = service.buscarTodos();

        assertNotNull(response);
        assertNotNull(response.get(0));

        verify(repository).findAll();
        verify(modelMapper).map(pessoa, PessoaDTO.class);
    }

    @Test
    public void givenId_whenCallExcluir_thenDeleteProject() {
        final Long projetoId = 1L;
        final Pessoa projeto = new Pessoa();

        when(repository.findById(anyLong())).thenReturn(Optional.of(projeto));
        doNothing().when(repository).deleteById(anyLong());

        service.excluir(projetoId);

        verify(repository).findById(projetoId);
        verify(repository).deleteById(projetoId);
    }

    @Test
    public void givenId_whenCallExcluir_thenReturnEmptyAndThrowsException() {
        final Long projetoId = 1L;

        when(repository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(PersonNotFoundException.class, () -> service.excluir(projetoId));

        verify(repository).findById(projetoId);
    }

}
