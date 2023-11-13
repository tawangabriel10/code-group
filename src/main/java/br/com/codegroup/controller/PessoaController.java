package br.com.codegroup.controller;

import br.com.codegroup.domain.dto.PessoaDTO;
import br.com.codegroup.sevice.PessoaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@Tag(name = "Pessoas Controller")
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/pessoa")
public class PessoaController {

    private final PessoaService service;

    @Operation(summary="Cadastrar Pessoa", description="Método responsável por cadastrar pessoas no sistema")
    @PostMapping
    public ResponseEntity<PessoaDTO> salvar(@RequestBody @Valid PessoaDTO pessoaDTO) throws URISyntaxException {
        PessoaDTO response = service.salvar(pessoaDTO);
        return ResponseEntity.created(new URI(String.format("/controller/v1/pessoa/%s", response.getId()))).body(response);
    }

    @Operation(summary="Alterar Pessoa", description="Método responsável por alterar dadpos de pessoas no sistema")
    @PutMapping(value = "/{id}")
    public ResponseEntity<PessoaDTO> alterar(@PathVariable("id") Long id, @RequestBody @Valid PessoaDTO pessoaDTO) throws URISyntaxException {
        PessoaDTO response = service.alterar(id, pessoaDTO);
        return ResponseEntity.created(new URI(String.format("/controller/v1/pessoa/%s", response.getId()))).body(response);
    }

    @Operation(summary="Consultar Pessoa por ID", description="Método responsável por consultar dados de pessoa pelo seu ID no sistema")
    @GetMapping(value = "/{id}")
    public ResponseEntity<PessoaDTO> consultarPorId(@PathVariable("id") Long id) {
        PessoaDTO projetoDTO = service.consultarPorId(id);
        return ResponseEntity.ok().body(projetoDTO);
    }

    @Operation(summary="Consultar Todas as Pessoas", description="Método responsável por buscar todas as pessoas cadastradas no sistema")
    @GetMapping
    public ResponseEntity<List<PessoaDTO>> consultarTodos() {
        List<PessoaDTO> pessoas = service.buscarTodos();
        return ResponseEntity.ok().body(pessoas);
    }

    @Operation(summary="Excluir Pessoa", description="Método responsável por remover uma pessoa cadastrada do sistema")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> excluir(@PathVariable("id") Long id) {
        this.service.excluir(id);
        return ResponseEntity.ok(null);
    }
}
