package br.com.codegroup.controller;

import br.com.codegroup.domain.dto.AlterarStatusDTO;
import br.com.codegroup.domain.dto.ProjetoDTO;
import br.com.codegroup.domain.enumeration.ProjetoStatusEnum;
import br.com.codegroup.sevice.ProjetoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.net.URISyntaxException;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProjetoControllerTest {

    @InjectMocks private ProjetoController controller;
    @Mock private ProjetoService service;

    @Test
    public void givenProjetoDTO_whenCallSalvar_thenSaveAndReturnProject() throws URISyntaxException {
        final ProjetoDTO projetoDTO = new ProjetoDTO();

        when(service.salvar(any(ProjetoDTO.class))).thenReturn(projetoDTO);

        final ResponseEntity<ProjetoDTO> response = controller.salvar(projetoDTO);

        assertNotNull(response);
        assertEquals(HttpStatusCode.valueOf(201), response.getStatusCode());
        assertNotNull(response.getBody());

        verify(service).salvar(projetoDTO);
    }

    @Test
    public void givenProjetoDTO_whenCallAlterar_thenUpdateAndReturnProject() throws URISyntaxException {
        final ProjetoDTO projetoDTO = new ProjetoDTO();
        final Long projetoId = 1L;

        when(service.alterar(anyLong(), any(ProjetoDTO.class))).thenReturn(projetoDTO);

        final ResponseEntity<ProjetoDTO> response = controller.alterar(projetoId, projetoDTO);

        assertNotNull(response);
        assertEquals(HttpStatusCode.valueOf(201), response.getStatusCode());
        assertNotNull(response.getBody());

        verify(service).alterar(projetoId, projetoDTO);
    }

    @Test
    public void givenProjetoDTO_whenCallConsultarPorId_thenReturnProject() {
        final ProjetoDTO projetoDTO = new ProjetoDTO();
        final Long projetoId = 1L;

        when(service.consultarPorId(anyLong())).thenReturn(projetoDTO);

        final ResponseEntity<ProjetoDTO> response = controller.consultarPorId(projetoId);

        assertNotNull(response);
        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
        assertNotNull(response.getBody());

        verify(service).consultarPorId(projetoId);
    }

    @Test
    public void givenProjetoDTO_whenCallConsultarTodos_thenReturnListProject() {
        final ProjetoDTO projetoDTO = new ProjetoDTO();

        when(service.buscarTodos()).thenReturn(Collections.singletonList(projetoDTO));

        final ResponseEntity<List<ProjetoDTO>> response = controller.consultarTodos();

        assertNotNull(response);
        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().get(0));

        verify(service).buscarTodos();
    }

    @Test
    public void givenProjetoDTO_whenCallExcluir_thenDeleteProject() {
        final Long projetoId = 1L;

        doNothing().when(service).excluir(anyLong());

        final ResponseEntity<Void> response = controller.excluir(projetoId);

        assertNotNull(response);
        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());

        verify(service).excluir(projetoId);
    }

    @Test
    public void givenAlterarStatusDTO_whenCallAlterarStatus_thenUpdateStatusAndReturnProject() {
        final Long projetoId = 1L;
        final AlterarStatusDTO alterarStatusDTO = new AlterarStatusDTO();
        alterarStatusDTO.setStatus(ProjetoStatusEnum.EM_ANDAMENTO);
        final ProjetoDTO projetoDTO = new ProjetoDTO();

        when(service.alterarStatus(anyLong(), any(AlterarStatusDTO.class))).thenReturn(projetoDTO);

        final ResponseEntity<ProjetoDTO> response = controller.alterarStatus(projetoId, alterarStatusDTO);

        assertNotNull(response);
        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
        assertNotNull(response.getBody());

        verify(service).alterarStatus(projetoId, alterarStatusDTO);
    }
}
