package br.com.codegroup.service;

import br.com.codegroup.domain.dto.VincularMembroDTO;
import br.com.codegroup.domain.entity.Pessoa;
import br.com.codegroup.domain.entity.Projeto;
import br.com.codegroup.domain.exceptions.PersonNotEmployeeException;
import br.com.codegroup.domain.exceptions.PersonNotFoundException;
import br.com.codegroup.domain.exceptions.ProjectNotFoundException;
import br.com.codegroup.repository.PessoaRepository;
import br.com.codegroup.repository.ProjetoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MembrosServiceTest {

    @InjectMocks private MembrosService service;
    @Mock private ProjetoRepository projetoRepository;
    @Mock private PessoaRepository pessoaRepository;

    @Test
    public void givenVincularMembroDTO_whenCallVincular_thenLinkProjectWithPerson() {
        final VincularMembroDTO vincularMembroDTO = new VincularMembroDTO();
        vincularMembroDTO.setIdPessoa(1L);
        vincularMembroDTO.setIdProjeto(1L);
        final Pessoa pessoa = new Pessoa();
        pessoa.setFuncionario(true);
        final Projeto projeto = new Projeto();

        when(pessoaRepository.findById(anyLong())).thenReturn(Optional.of(pessoa));
        when(projetoRepository.findById(anyLong())).thenReturn(Optional.of(projeto));
        when(projetoRepository.save(any(Projeto.class))).thenReturn(projeto);

        service.vincular(vincularMembroDTO);

        verify(pessoaRepository).findById(1L);
        verify(projetoRepository).findById(1L);
        verify(projetoRepository).save(projeto);
    }

    @Test
    public void givenVincularMembroDTO_whenCallVincular_thenPersonNotFoundAndThrowsException() {
        final VincularMembroDTO vincularMembroDTO = new VincularMembroDTO();
        vincularMembroDTO.setIdPessoa(1L);
        vincularMembroDTO.setIdProjeto(1L);

        when(pessoaRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(PersonNotFoundException.class, () -> service.vincular(vincularMembroDTO));

        verify(pessoaRepository).findById(1L);
    }

    @Test
    public void givenVincularMembroDTO_whenCallVincular_thenPersonNotEmployeeAndThrowException() {
        final VincularMembroDTO vincularMembroDTO = new VincularMembroDTO();
        vincularMembroDTO.setIdPessoa(1L);
        vincularMembroDTO.setIdProjeto(1L);
        final Pessoa pessoa = new Pessoa();
        pessoa.setFuncionario(false);

        when(pessoaRepository.findById(anyLong())).thenReturn(Optional.of(pessoa));

        assertThrows(PersonNotEmployeeException.class, () -> service.vincular(vincularMembroDTO));

        verify(pessoaRepository).findById(1L);
    }

    @Test
    public void givenVincularMembroDTO_whenCallVincular_thenProjectNotFoundAndThrowException() {
        final VincularMembroDTO vincularMembroDTO = new VincularMembroDTO();
        vincularMembroDTO.setIdPessoa(1L);
        vincularMembroDTO.setIdProjeto(1L);
        final Pessoa pessoa = new Pessoa();
        pessoa.setFuncionario(true);

        when(pessoaRepository.findById(anyLong())).thenReturn(Optional.of(pessoa));
        when(projetoRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ProjectNotFoundException.class, () -> service.vincular(vincularMembroDTO));

        verify(pessoaRepository).findById(1L);
        verify(projetoRepository).findById(1L);
    }

}
