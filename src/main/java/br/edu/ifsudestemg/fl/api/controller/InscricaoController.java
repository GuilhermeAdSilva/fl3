package br.edu.ifsudestemg.fl.api.controller;

import br.edu.ifsudestemg.fl.api.dto.InscricaoDTO;
import br.edu.ifsudestemg.fl.exception.RegraNegocioException;
import br.edu.ifsudestemg.fl.model.entity.Inscricao;
import br.edu.ifsudestemg.fl.service.InscricaoService;
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
@RequestMapping("/api/v1/inscricoes")
@RequiredArgsConstructor
@Api
public class InscricaoController {

    private final InscricaoService service;

    @GetMapping
    @ApiOperation("Obter detalhes de todas as inscrições")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Busca realizada")
    })
    public ResponseEntity get() {
        List<Inscricao> inscricoes = service.getInscricoes();
        return ResponseEntity.ok(inscricoes.stream().map(InscricaoDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @ApiOperation("Obter detalhes de uma inscrição")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Inscrição encontrada"),
            @ApiResponse(code = 404, message = "Inscrição não encontrada")
    })
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Inscricao> inscricao = service.getInscricaoById(id);
        if (!inscricao.isPresent()) {
            return new ResponseEntity("Inscrição não encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(inscricao.map(InscricaoDTO::create));
    }

    @PostMapping
    @ApiOperation("Salva uma nova inscrição")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Inscrição salva com sucesso"),
            @ApiResponse(code = 400, message = "Erro ao salvar a inscrição")
    })
    public ResponseEntity post(@RequestBody InscricaoDTO dto) {
        try {
            Inscricao inscricao = converter(dto);
            inscricao = service.salvar(inscricao);
            return new ResponseEntity(inscricao, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    @ApiOperation("Altera uma inscrição existente")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Inscrição alterada com sucesso"),
            @ApiResponse(code = 400, message = "Erro ao salvar a inscrição"),
            @ApiResponse(code = 404, message = "Inscrição não encontrada")
    })
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody InscricaoDTO dto) {
        if (!service.getInscricaoById(id).isPresent()) {
            return new ResponseEntity("Inscrição não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            Inscricao inscricao = converter(dto);
            inscricao.setId(id);
            service.salvar(inscricao);
            return ResponseEntity.ok(inscricao);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    @ApiOperation("Deleta uma inscrição existente")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Inscrição deletada com sucesso"),
            @ApiResponse(code = 404, message = "Inscrição não encontrada"),
            @ApiResponse(code = 500, message = "Erro ao deletar a inscrição")
    })
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<Inscricao> inscricao = service.getInscricaoById(id);
        if (!inscricao.isPresent()) {
            return new ResponseEntity("Inscrição não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(inscricao.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Inscricao converter(InscricaoDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Inscricao inscricao = modelMapper.map(dto, Inscricao.class);
        return inscricao;
    }

}
