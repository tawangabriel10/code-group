package br.com.codegroup.controller;

import br.com.codegroup.domain.dto.PessoaDTO;
import br.com.codegroup.service.PessoaService;
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
public class PessoaControllerTest {

    @InjectMocks private PessoaController controller;

    @Mock private PessoaService service;

    @Test
    public void givenPessoaDTO_whenCallSalvar_thenSaveAndReturnPessoaDTO() throws URISyntaxException {
        final PessoaDTO pessoaDTO = new PessoaDTO();

        when(service.salvar(any(PessoaDTO.class))).thenReturn(pessoaDTO);

        final ResponseEntity<PessoaDTO> response = controller.salvar(pessoaDTO);

        assertNotNull(response);
        assertEquals(HttpStatusCode.valueOf(201), response.getStatusCode());
        assertNotNull(response.getBody());

        verify(service).salvar(pessoaDTO);
    }

    @Test
    public void givenPessoaDTO_whenCallAlterar_thenUpdateAndReturnPessoaDTO() throws URISyntaxException {
        final PessoaDTO pessoaDTO = new PessoaDTO();
        final Long pessoaId = 1L;

        when(service.alterar(anyLong(), any(PessoaDTO.class))).thenReturn(pessoaDTO);

        final ResponseEntity<PessoaDTO> response = controller.alterar(pessoaId, pessoaDTO);

        assertNotNull(response);
        assertEquals(HttpStatusCode.valueOf(201), response.getStatusCode());
        assertNotNull(response.getBody());

        verify(service).alterar(pessoaId, pessoaDTO);
    }

    @Test
    public void givenId_whenCallConsultarPorId_thenReturnPessoaDTO() {
        final PessoaDTO pessoaDTO = new PessoaDTO();
        final Long pessoaId = 1L;

        when(service.consultarPorId(anyLong())).thenReturn(pessoaDTO);

        final ResponseEntity<PessoaDTO> response = controller.consultarPorId(pessoaId);

        assertNotNull(response);
        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
        assertNotNull(response.getBody());

        verify(service).consultarPorId(pessoaId);
    }

    @Test
    public void given_whenCallConsultarTodos_thenReturnListPessoaDTO() {
        final PessoaDTO pessoaDTO = new PessoaDTO();

        when(service.buscarTodos()).thenReturn(Collections.singletonList(pessoaDTO));

        final ResponseEntity<List<PessoaDTO>> response = controller.consultarTodos();

        assertNotNull(response);
        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().get(0));

        verify(service).buscarTodos();
    }

    @Test
    public void givenId_whenCallExcluir_theDeletePessoaDTO() {
        final Long pessoaId = 1L;

        doNothing().when(service).excluir(anyLong());

        final ResponseEntity<Void> response = controller.excluir(pessoaId);

        assertNotNull(response);
        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());

        verify(service).excluir(pessoaId);
    }
}
