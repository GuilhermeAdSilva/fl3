package br.edu.ifsudestemg.fl.api.controller;

import br.edu.ifsudestemg.fl.api.dto.EquipeDTO;
import br.edu.ifsudestemg.fl.api.dto.JogadorDTO;
import br.edu.ifsudestemg.fl.exception.RegraNegocioException;
import br.edu.ifsudestemg.fl.model.entity.Equipe;
import br.edu.ifsudestemg.fl.model.entity.Jogador;
import br.edu.ifsudestemg.fl.service.EquipeService;
import br.edu.ifsudestemg.fl.service.JogadorService;
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
@RequestMapping("/api/v1/jogadores")
@RequiredArgsConstructor
@Api
@CrossOrigin
public class JogadorController {

    private final JogadorService service;
    private final EquipeService equipeService;

    @GetMapping
    @ApiOperation("Obter detalhes de todos os jogadores")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Busca realizada")
    })
    public ResponseEntity get() {
        List<Jogador> jogadores = service.getJogadores();
        return ResponseEntity.ok(jogadores.stream().map(JogadorDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @ApiOperation("Obter detalhes de um jogador")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Jogador encontrado"),
            @ApiResponse(code = 404, message = "Jogador não encontrado")
    })
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Jogador> jogador = service.getJogadorById(id);
        if (!jogador.isPresent()) {
            return new ResponseEntity("Jogador não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(jogador.map(JogadorDTO::create));
    }

    @PostMapping
    @ApiOperation("Salva um novo jogador")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Jogador salvo com sucesso"),
            @ApiResponse(code = 400, message = "Erro ao salvar o jogador")
    })
    public ResponseEntity post(@RequestBody JogadorDTO dto) {
        try {
            Jogador jogador = converter(dto);
            jogador = service.salvar(jogador);
            return new ResponseEntity(jogador, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    @ApiOperation("Altera um jogador existente")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Jogador alterado com sucesso"),
            @ApiResponse(code = 400, message = "Erro ao salvar o jogador"),
            @ApiResponse(code = 404, message = "Jogador não encontrado")
    })
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody JogadorDTO dto) {
        if (!service.getJogadorById(id).isPresent()) {
            return new ResponseEntity("Jogador não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            Jogador jogador = converter(dto);
            jogador.setId(id);
            service.salvar(jogador);
            return ResponseEntity.ok(jogador);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    @ApiOperation("Deleta um jogador existente")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Jogador deletado com sucesso"),
            @ApiResponse(code = 404, message = "Jogador não encontrado"),
            @ApiResponse(code = 500, message = "Erro ao deletar o jogador")
    })
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<Jogador> jogador = service.getJogadorById(id);
        if (!jogador.isPresent()) {
            return new ResponseEntity("Jogador não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(jogador.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Jogador converter(JogadorDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Jogador jogador = modelMapper.map(dto, Jogador.class);
        return jogador;
    }
}
