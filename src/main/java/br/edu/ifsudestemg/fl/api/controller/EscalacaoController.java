package br.edu.ifsudestemg.fl.api.controller;

import br.edu.ifsudestemg.fl.api.dto.EscalacaoDTO;
import br.edu.ifsudestemg.fl.exception.RegraNegocioException;
import br.edu.ifsudestemg.fl.model.entity.Escalacao;
import br.edu.ifsudestemg.fl.service.EscalacaoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/escalacoes")
@RequiredArgsConstructor
@Api
public class EscalacaoController {
    
    private final EscalacaoService service;

    @GetMapping
    @ApiOperation("Obter detalhes de todas as escalações")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Busca realizada")
    })
    public ResponseEntity get() {
        List<Escalacao> escalacoes = service.getEscalacoes();
        return ResponseEntity.ok(escalacoes.stream().map(EscalacaoDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @ApiOperation("Obter detalhes de uma escalação")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Escalação encontrada"),
            @ApiResponse(code = 404, message = "Escalação não encontrada")
    })
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Escalacao> escalacao = service.getEscalacaoById(id);
        if (!escalacao.isPresent()) {
            return new ResponseEntity("Escalação não encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(escalacao.map(EscalacaoDTO::create));
    }

    @PostMapping
    @ApiOperation("Salva uma nova escalação")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Escalação salva com sucesso"),
            @ApiResponse(code = 400, message = "Erro ao salvar a escalação")
    })
    public ResponseEntity post(@RequestBody EscalacaoDTO dto) {
        try {
            Escalacao escalacao = converter(dto);
            escalacao = service.salvar(escalacao);
            return new ResponseEntity(escalacao, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    @ApiOperation("Altera uma escalação existente")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Escalação alterada com sucesso"),
            @ApiResponse(code = 400, message = "Erro ao salvar a escalação"),
            @ApiResponse(code = 404, message = "Escalação não encontrada")
    })
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody EscalacaoDTO dto) {
        if (!service.getEscalacaoById(id).isPresent()) {
            return new ResponseEntity("Escalação não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            Escalacao escalacao = converter(dto);
            escalacao.setId(id);
            service.salvar(escalacao);
            return ResponseEntity.ok(escalacao);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    @ApiOperation("Deleta uma escalação existente")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Escalação deletada com sucesso"),
            @ApiResponse(code = 404, message = "Escalação não encontrada"),
            @ApiResponse(code = 500, message = "Erro ao deletar a escalação")
    })
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<Escalacao> escalacao = service.getEscalacaoById(id);
        if (!escalacao.isPresent()) {
            return new ResponseEntity("Escalação não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(escalacao.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Escalacao converter(EscalacaoDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Escalacao escalacao = modelMapper.map(dto, Escalacao.class);
        return escalacao;
    }
}
