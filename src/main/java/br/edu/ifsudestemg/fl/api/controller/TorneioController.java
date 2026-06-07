package br.edu.ifsudestemg.fl.api.controller;

import br.edu.ifsudestemg.fl.api.dto.TorneioDTO;
import br.edu.ifsudestemg.fl.exception.RegraNegocioException;
import br.edu.ifsudestemg.fl.model.entity.Torneio;
import br.edu.ifsudestemg.fl.service.TorneioService;
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
@RequestMapping("/api/v1/torneios")
@RequiredArgsConstructor
@Api
@CrossOrigin
public class TorneioController {

    private final TorneioService service;

    @GetMapping
    @ApiOperation("Obter detalhes de todas os torneios")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Busca realizada")
    })
    public ResponseEntity get() {
        List<Torneio> torneios = service.getTorneios();
        return ResponseEntity.ok(torneios.stream().map(TorneioDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @ApiOperation("Obter detalhes de um torneio")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Torneio encontrado"),
            @ApiResponse(code = 404, message = "Torneio não encontrado")
    })
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Torneio> torneio = service.getTorneioById(id);
        if (!torneio.isPresent()) {
            return new ResponseEntity("Torneio não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(torneio.map(TorneioDTO::create));
    }

    @PostMapping
    @ApiOperation("Salva um novo torneio")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Torneio salvo com sucesso"),
            @ApiResponse(code = 400, message = "Erro ao salvar o torneio")
    })
    public ResponseEntity post(@RequestBody TorneioDTO dto) {
        try {
            Torneio torneio = converter(dto);
            torneio = service.salvar(torneio);
            return new ResponseEntity(torneio, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    @ApiOperation("Altera um torneio existente")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Torneio alterado com sucesso"),
            @ApiResponse(code = 400, message = "Erro ao salvar o torneio"),
            @ApiResponse(code = 404, message = "Torneio não encontrado")
    })
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody TorneioDTO dto) {
        if (!service.getTorneioById(id).isPresent()) {
            return new ResponseEntity("Torneio não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            Torneio torneio = converter(dto);
            torneio.setId(id);
            service.salvar(torneio);
            return ResponseEntity.ok(torneio);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    @ApiOperation("Deleta um torneio existente")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Torneio deletado com sucesso"),
            @ApiResponse(code = 404, message = "Torneio não encontrado"),
            @ApiResponse(code = 500, message = "Erro ao deletar o torneio")
    })
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<Torneio> torneio = service.getTorneioById(id);
        if (!torneio.isPresent()) {
            return new ResponseEntity("Torneio não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(torneio.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Torneio converter(TorneioDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Torneio torneio = modelMapper.map(dto, Torneio.class);
        return torneio;
    }
}
