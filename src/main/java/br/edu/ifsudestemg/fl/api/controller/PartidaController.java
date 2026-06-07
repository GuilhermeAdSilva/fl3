package br.edu.ifsudestemg.fl.api.controller;

import br.edu.ifsudestemg.fl.api.dto.PartidaDTO;
import br.edu.ifsudestemg.fl.exception.RegraNegocioException;
import br.edu.ifsudestemg.fl.model.entity.Partida;
import br.edu.ifsudestemg.fl.service.PartidaService;
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
@RequestMapping("/api/v1/partidas")
@RequiredArgsConstructor
@Api
@CrossOrigin
public class PartidaController {
    
    private final PartidaService service;

    @GetMapping
    @ApiOperation("Obter detalhes de todas as partidas")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Busca realizada")
    })
    public ResponseEntity get() {
        List<Partida> partidas = service.getPartidas();
        return ResponseEntity.ok(partidas.stream().map(PartidaDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @ApiOperation("Obter detalhes de uma partida")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Partida encontrada"),
            @ApiResponse(code = 404, message = "Partida não encontrada")
    })
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Partida> partida = service.getPartidaById(id);
        if (!partida.isPresent()) {
            return new ResponseEntity("Partida não encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(partida.map(PartidaDTO::create));
    }

    @PostMapping
    @ApiOperation("Salva uma nova partida")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Partida salva com sucesso"),
            @ApiResponse(code = 400, message = "Erro ao salvar a partida")
    })
    public ResponseEntity post(@RequestBody PartidaDTO dto) {
        try {
            Partida partida = converter(dto);
            partida = service.salvar(partida);
            return new ResponseEntity(partida, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    @ApiOperation("Altera uma partida existente")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Partida alterada com sucesso"),
            @ApiResponse(code = 400, message = "Erro ao salvar a partida"),
            @ApiResponse(code = 404, message = "Partida não encontrada")
    })
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody PartidaDTO dto) {
        if (!service.getPartidaById(id).isPresent()) {
            return new ResponseEntity("Partida não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            Partida partida = converter(dto);
            partida.setId(id);
            service.salvar(partida);
            return ResponseEntity.ok(partida);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    @ApiOperation("Deleta uma partida existente")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Partida deletada com sucesso"),
            @ApiResponse(code = 404, message = "Partida não encontrada"),
            @ApiResponse(code = 500, message = "Erro ao deletar a partida")
    })
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<Partida> partida = service.getPartidaById(id);
        if (!partida.isPresent()) {
            return new ResponseEntity("partida não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(partida.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Partida converter(PartidaDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Partida partida = modelMapper.map(dto, Partida.class);
        return partida;
    }
}
