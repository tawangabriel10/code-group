package br.com.codegroup.service;

import br.com.codegroup.domain.dto.AlterarStatusDTO;
import br.com.codegroup.domain.dto.PessoaDTO;
import br.com.codegroup.domain.dto.ProjetoDTO;
import br.com.codegroup.domain.entity.Projeto;
import br.com.codegroup.domain.enumeration.ProjetoStatusEnum;
import br.com.codegroup.domain.exceptions.ProjectLockedException;
import br.com.codegroup.domain.exceptions.ProjectNotFoundException;
import br.com.codegroup.repository.ProjetoRepository;
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
public class ProjetoServiceTest {

    @InjectMocks private ProjetoService service;
    @Mock private ProjetoRepository repository;
    @Mock private ModelMapper modelMapper;
    @Mock private PessoaService pessoaService;

    @Test
    public void givenProjetoDTO_whenCallSalvar_thenSaveAndReturnProjectDTO() {
        final ProjetoDTO projetoDTO = new ProjetoDTO();
        final Projeto projeto = new Projeto();
        final PessoaDTO pessoaDTO = new PessoaDTO();
        pessoaDTO.setId(1L);
        projetoDTO.setGerente(pessoaDTO);
        final Long pessoaId = 1L;

        when(pessoaService.consultarPorId(anyLong())).thenReturn(pessoaDTO);
        when(modelMapper.map(any(ProjetoDTO.class), eq(Projeto.class))).thenReturn(projeto);
        when(repository.save(any(Projeto.class))).thenReturn(projeto);
        when(modelMapper.map(any(Projeto.class), eq(ProjetoDTO.class))).thenReturn(projetoDTO);

        final ProjetoDTO response = service.salvar(projetoDTO);

        assertNotNull(response);

        verify(pessoaService).consultarPorId(pessoaId);
        verify(modelMapper).map(projetoDTO, Projeto.class);
        verify(repository).save(projeto);
        verify(modelMapper).map(projeto, ProjetoDTO.class);
    }

    @Test
    public void givenIdAndProjetoDTO_whenCallAlterar_thenUpdateAndReturnProjectDTO() {
        final ProjetoDTO projetoDTO = new ProjetoDTO();
        final Projeto projeto = new Projeto();
        final Long projetoId = 1L;
        final PessoaDTO pessoaDTO = new PessoaDTO();
        pessoaDTO.setId(1L);
        projetoDTO.setGerente(pessoaDTO);
        final Long pessoaId = 1L;

        when(repository.findById(anyLong())).thenReturn(Optional.of(projeto));
        when(pessoaService.consultarPorId(anyLong())).thenReturn(pessoaDTO);
        when(modelMapper.map(any(ProjetoDTO.class), eq(Projeto.class))).thenReturn(projeto);
        when(repository.save(any(Projeto.class))).thenReturn(projeto);
        when(modelMapper.map(any(Projeto.class), eq(ProjetoDTO.class))).thenReturn(projetoDTO);

        final ProjetoDTO response = service.alterar(projetoId, projetoDTO);

        assertNotNull(response);

        verify(repository).findById(projetoId);
        verify(pessoaService).consultarPorId(pessoaId);
        verify(modelMapper).map(projetoDTO, Projeto.class);
        verify(repository).save(projeto);
        verify(modelMapper).map(projeto, ProjetoDTO.class);
    }

    @Test
    public void givenIdAndProjetoDTO_whenCallAlterar_thenProjectNotFoundAndThrowsException() {
        final ProjetoDTO projetoDTO = new ProjetoDTO();
        final Long projetoId = 1L;

        when(repository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ProjectNotFoundException.class, () -> service.alterar(projetoId, projetoDTO));

        verify(repository).findById(projetoId);
    }

    @Test
    public void givenId_whenCallConsultarPorId_thenReturnProjectDTO() {
        final ProjetoDTO projetoDTO = new ProjetoDTO();
        final Projeto projeto = new Projeto();
        final Long projetoId = 1L;

        when(repository.findById(anyLong())).thenReturn(Optional.of(projeto));
        when(modelMapper.map(any(Projeto.class), eq(ProjetoDTO.class))).thenReturn(projetoDTO);

        final ProjetoDTO response = service.consultarPorId(projetoId);

        assertNotNull(response);

        verify(repository).findById(projetoId);
        verify(modelMapper).map(projeto, ProjetoDTO.class);
    }

    @Test
    public void givenId_whenCallConsultarPorId_thenReturnEmptyAndThrowException() {
        final Long projetoId = 1L;

        when(repository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ProjectNotFoundException.class, () -> service.consultarPorId(projetoId));

        verify(repository).findById(projetoId);
    }

    @Test
    public void given_whenCallBuscarTodos_thenReturnListProjectDTO() {
        final ProjetoDTO projetoDTO = new ProjetoDTO();
        final Projeto projeto = new Projeto();

        when(repository.findAll()).thenReturn(Collections.singletonList(projeto));
        when(modelMapper.map(any(Projeto.class), eq(ProjetoDTO.class))).thenReturn(projetoDTO);

        final List<ProjetoDTO> response = service.buscarTodos();

        assertNotNull(response);
        assertNotNull(response.get(0));

        verify(repository).findAll();
        verify(modelMapper).map(projeto, ProjetoDTO.class);
    }

    @Test
    public void givenId_whenCallExcluir_thenDeleteProject() {
        final Long projetoId = 1L;
        final Projeto projeto = new Projeto();
        projeto.setStatus(ProjetoStatusEnum.PLANEJADO);

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

        assertThrows(ProjectNotFoundException.class, () -> service.excluir(projetoId));

        verify(repository).findById(projetoId);
    }

    @Test
    public void givenId_whenCallExcluir_thenReturnWithStatusINICIADOAndThrowsException() {
        final Long projetoId = 1L;
        final Projeto projeto = new Projeto();
        projeto.setStatus(ProjetoStatusEnum.INICIADO);

        when(repository.findById(anyLong())).thenReturn(Optional.of(projeto));

        assertThrows(ProjectLockedException.class, () -> service.excluir(projetoId));

        verify(repository).findById(projetoId);
    }

    @Test
    public void givenId_whenCallExcluir_thenReturnWithStatusEM_ANDAMENTOAndThrowsException() {
        final Long projetoId = 1L;
        final Projeto projeto = new Projeto();
        projeto.setStatus(ProjetoStatusEnum.EM_ANDAMENTO);

        when(repository.findById(anyLong())).thenReturn(Optional.of(projeto));

        assertThrows(ProjectLockedException.class, () -> service.excluir(projetoId));

        verify(repository).findById(projetoId);
    }

    @Test
    public void givenId_whenCallExcluir_thenReturnWithStatusENCERRADOAndThrowsException() {
        final Long projetoId = 1L;
        final Projeto projeto = new Projeto();
        projeto.setStatus(ProjetoStatusEnum.ENCERRADO);

        when(repository.findById(anyLong())).thenReturn(Optional.of(projeto));

        assertThrows(ProjectLockedException.class, () -> service.excluir(projetoId));

        verify(repository).findById(projetoId);
    }

    @Test
    public void givenIdAndAlterarStatusDTO_whenCallAlterarStatus_thenUpdateStatusAndReturnProject() {
        final Long projetoId = 1L;
        final AlterarStatusDTO alterarStatusDTO = new AlterarStatusDTO();
        alterarStatusDTO.setStatus(ProjetoStatusEnum.PLANEJADO);
        final Projeto projeto = new Projeto();
        projeto.setStatus(ProjetoStatusEnum.PLANEJADO);
        final ProjetoDTO projetoDTO = new ProjetoDTO();

        when(repository.findById(anyLong())).thenReturn(Optional.of(projeto));
        when(repository.save(any(Projeto.class))).thenReturn(projeto);
        when(modelMapper.map(any(Projeto.class), eq(ProjetoDTO.class))).thenReturn(projetoDTO);

        final ProjetoDTO response = service.alterarStatus(projetoId, alterarStatusDTO);
        assertNotNull(response);

        verify(repository).findById(projetoId);
        verify(repository).save(projeto);
        verify(modelMapper).map(projeto, ProjetoDTO.class);
    }

    @Test
    public void givenIdAndAlterarStatusDTO_whenCallAlterarStatus_thenProjectNotFopund() {
        final Long projetoId = 1L;
        final AlterarStatusDTO alterarStatusDTO = new AlterarStatusDTO();
        alterarStatusDTO.setStatus(ProjetoStatusEnum.PLANEJADO);

        when(repository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ProjectNotFoundException.class, () -> service.alterarStatus(projetoId, alterarStatusDTO));

        verify(repository).findById(projetoId);
    }
}
