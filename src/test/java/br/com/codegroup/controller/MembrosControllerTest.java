package br.com.codegroup.controller;

import br.com.codegroup.domain.dto.VincularMembroDTO;
import br.com.codegroup.service.MembrosService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class MembrosControllerTest {

    @InjectMocks private MembrosController membrosController;

    @Mock private MembrosService membrosService;

    @Test
    public void givenVincularMembroDTO_whenCallVincular_thenLinkProjectWithPeople() {
        final VincularMembroDTO vincularMembroDTO = new VincularMembroDTO();

        doNothing().when(membrosService).vincular(any(VincularMembroDTO.class));

        final ResponseEntity<Void> response = membrosController.vincular(vincularMembroDTO);

        assertNotNull(response);
        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());

        verify(membrosService).vincular(vincularMembroDTO);
    }
}
