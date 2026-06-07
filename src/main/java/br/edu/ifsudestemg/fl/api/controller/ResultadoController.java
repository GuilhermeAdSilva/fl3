package br.edu.ifsudestemg.fl.api.controller;

import br.edu.ifsudestemg.fl.api.dto.ResultadoDTO;
import br.edu.ifsudestemg.fl.api.dto.ResultadoDTO;
import br.edu.ifsudestemg.fl.api.dto.ResultadoDTO;
import br.edu.ifsudestemg.fl.exception.RegraNegocioException;
import br.edu.ifsudestemg.fl.model.entity.Resultado;
import br.edu.ifsudestemg.fl.model.entity.Resultado;
import br.edu.ifsudestemg.fl.model.entity.Resultado;
import br.edu.ifsudestemg.fl.service.ResultadoService;
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
@RequestMapping("/api/v1/resultados")
@RequiredArgsConstructor
@Api
@CrossOrigin
public class ResultadoController {
    
    private final ResultadoService service;

    @GetMapping
    @ApiOperation("Obter detalhes de todos os resultados")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Busca realizada")
    })
    public ResponseEntity get() {
        List<Resultado> inscricoes = service.getResultados();
        return ResponseEntity.ok(inscricoes.stream().map(ResultadoDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @ApiOperation("Obter detalhes de um resultado")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Resultado encontrado"),
            @ApiResponse(code = 404, message = "Resultado não encontrado")
    })
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Resultado> resultado = service.getResultadoById(id);
        if (!resultado.isPresent()) {
            return new ResponseEntity("Resultado não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(resultado.map(ResultadoDTO::create));
    }

    @PostMapping
    @ApiOperation("Salva um novo resultado")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Resultado salvo com sucesso"),
            @ApiResponse(code = 400, message = "Erro ao salvar o resultado")
    })
    public ResponseEntity post(@RequestBody ResultadoDTO dto) {
        try {
            Resultado resultado = converter(dto);
            resultado = service.salvar(resultado);
            return new ResponseEntity(resultado, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    @ApiOperation("Altera um resultado existente")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Resultado alterado com sucesso"),
            @ApiResponse(code = 400, message = "Erro ao salvar o resultado"),
            @ApiResponse(code = 404, message = "Resultado não encontrado")
    })
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody ResultadoDTO dto) {
        if (!service.getResultadoById(id).isPresent()) {
            return new ResponseEntity("Resultado não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            Resultado resultado = converter(dto);
            resultado.setId(id);
            service.salvar(resultado);
            return ResponseEntity.ok(resultado);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    @ApiOperation("Deleta um resultado existente")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Resultado deletado com sucesso"),
            @ApiResponse(code = 404, message = "Resultado não encontrado"),
            @ApiResponse(code = 500, message = "Erro ao deletar o resultado")
    })
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<Resultado> resultado = service.getResultadoById(id);
        if (!resultado.isPresent()) {
            return new ResponseEntity("Resultado não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(resultado.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Resultado converter(ResultadoDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Resultado resultado = modelMapper.map(dto, Resultado.class);
        return resultado;
    }
}
