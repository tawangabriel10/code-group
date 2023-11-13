package br.com.codegroup.controller;

import br.com.codegroup.domain.dto.VincularMembroDTO;
import br.com.codegroup.sevice.MembrosService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Membros Controller")
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/membros")
public class MembrosController {

    private final MembrosService membrosService;

    @Operation(summary="Vincular Projetos e Pessoas", description="Metodo responsável por vincular pessoas à projetos")
    @PostMapping
    public ResponseEntity<Void> vincular(@RequestBody @Valid VincularMembroDTO vincularMembroDTO) {
        membrosService.vincular(vincularMembroDTO);
        return ResponseEntity.ok(null);
    }
}
