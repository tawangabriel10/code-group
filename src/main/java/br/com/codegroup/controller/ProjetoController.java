package br.com.codegroup.controller;

import br.com.codegroup.domain.dto.AlterarStatusDTO;
import br.com.codegroup.domain.dto.ProjetoDTO;
import br.com.codegroup.service.ProjetoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@Tag(name = "Projetos Controller")
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/projeto")
public class ProjetoController {

    private final ProjetoService service;

    @Operation(summary="Cadastrar Projeto", description="Método responsável por cadastrar projetos no sistema")
    @PostMapping
    public ResponseEntity<ProjetoDTO> salvar(@RequestBody @Valid ProjetoDTO projetoDTO) throws URISyntaxException {
        ProjetoDTO response = service.salvar(projetoDTO);
        return ResponseEntity.created(new URI(String.format("/controller/v1/projeto/%s", response.getId()))).body(response);
    }

    @Operation(summary="Alterar Projeto", description="Método responsável por alterar dados do projeto no sistema")
    @PutMapping(value = "/{id}")
    public ResponseEntity<ProjetoDTO> alterar(@PathVariable("id") Long id, @RequestBody @Valid ProjetoDTO projetoDTO) throws URISyntaxException {
        ProjetoDTO response = service.alterar(id, projetoDTO);
        return ResponseEntity.created(new URI(String.format("/controller/v1/projeto/%s", response.getId()))).body(response);
    }

    @Operation(summary="Consultar Projeto por ID", description="Método responsável por consultar dados do projeto por ID no sistema")
    @GetMapping(value = "/{id}")
    public ResponseEntity<ProjetoDTO> consultarPorId(@PathVariable("id") Long id) {
        ProjetoDTO projetoDTO = service.consultarPorId(id);
        return ResponseEntity.ok().body(projetoDTO);
    }

    @Operation(summary="Buscar Todos os Projetos", description="Método responsável por buscar todos os projetos cadastrados no sistema")
    @GetMapping
    public ResponseEntity<List<ProjetoDTO>> consultarTodos() {
        List<ProjetoDTO> projetos = service.buscarTodos();
        return ResponseEntity.ok().body(projetos);
    }

    @Operation(summary="Excluir Projeto", description="Método responsável por excluir um projeto do sistema")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> excluir(@PathVariable("id") Long id) {
        this.service.excluir(id);
        return ResponseEntity.ok(null);
    }

    @Operation(summary="Alterar Status do Projeto por ID", description="Método responsável por alterar status do projeto por ID no sistema")
    @PatchMapping(value = "/status/{id}")
    public ResponseEntity<ProjetoDTO> alterarStatus(@PathVariable("id") Long id, @RequestBody @Valid AlterarStatusDTO alterarStatusDTO) {
        ProjetoDTO projetoDTO = service.alterarStatus(id, alterarStatusDTO);
        return ResponseEntity.ok().body(projetoDTO);
    }
}
