package br.edu.ifsudestemg.fl.api.controller;

import br.edu.ifsudestemg.fl.api.dto.EquipeDTO;
import br.edu.ifsudestemg.fl.exception.RegraNegocioException;
import br.edu.ifsudestemg.fl.model.entity.Equipe;
import br.edu.ifsudestemg.fl.service.EquipeService;
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
@RequestMapping("/api/v1/equipes")
@RequiredArgsConstructor
@Api
@CrossOrigin
public class EquipeController {

    private final EquipeService service;

    @GetMapping
    @ApiOperation("Obter detalhes de todas as equipes")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Busca realizada")
    })
    public ResponseEntity get() {
        List<Equipe> equipes = service.getEquipes();
        return ResponseEntity.ok(equipes.stream().map(EquipeDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @ApiOperation("Obter detalhes de uma equipe")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Equipe encontrada"),
            @ApiResponse(code = 404, message = "Equipe não encontrada")
    })
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Equipe> equipe = service.getEquipeById(id);
        if (!equipe.isPresent()) {
            return new ResponseEntity("Equipe não encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(equipe.map(EquipeDTO::create));
    }

    @PostMapping
    @ApiOperation("Salva uma nova equipe")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Equipe salva com sucesso"),
            @ApiResponse(code = 400, message = "Erro ao salvar a equipe")
    })
    public ResponseEntity post(@RequestBody EquipeDTO dto) {
        try {
            Equipe equipe = converter(dto);
            equipe = service.salvar(equipe);
            return new ResponseEntity(equipe, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    @ApiOperation("Altera uma equipe existente")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Equipe alterada com sucesso"),
            @ApiResponse(code = 400, message = "Erro ao salvar a equipe"),
            @ApiResponse(code = 404, message = "Equipe não encontrada")
    })
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody EquipeDTO dto) {
        if (!service.getEquipeById(id).isPresent()) {
            return new ResponseEntity("Equipe não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            Equipe equipe = converter(dto);
            equipe.setId(id);
            service.salvar(equipe);
            return ResponseEntity.ok(equipe);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    @ApiOperation("Deleta uma equipe existente")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Equipe deletada com sucesso"),
            @ApiResponse(code = 404, message = "Equipe não encontrada"),
            @ApiResponse(code = 500, message = "Erro ao deletar a equipe")
    })
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<Equipe> equipe = service.getEquipeById(id);
        if (!equipe.isPresent()) {
            return new ResponseEntity("Equipe não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(equipe.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Equipe converter(EquipeDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Equipe equipe = modelMapper.map(dto, Equipe.class);
        return equipe;
    }
}
