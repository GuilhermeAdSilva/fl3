package br.edu.ifsudestemg.fl.api.controller;

import br.edu.ifsudestemg.fl.api.dto.ResultadoDTO;
import br.edu.ifsudestemg.fl.api.dto.ResultadoDTO;
import br.edu.ifsudestemg.fl.api.dto.ResultadoDTO;
import br.edu.ifsudestemg.fl.exception.RegraNegocioException;
import br.edu.ifsudestemg.fl.model.entity.Resultado;
import br.edu.ifsudestemg.fl.model.entity.Resultado;
import br.edu.ifsudestemg.fl.model.entity.Resultado;
import br.edu.ifsudestemg.fl.service.ResultadoService;
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
public class ResultadoController {
    
    private final ResultadoService service;

    @GetMapping
    public ResponseEntity get() {
        List<Resultado> inscricoes = service.getResultados();
        return ResponseEntity.ok(inscricoes.stream().map(ResultadoDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Resultado> resultado = service.getResultadoById(id);
        if (!resultado.isPresent()) {
            return new ResponseEntity("Resultado não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(resultado.map(ResultadoDTO::create));
    }

    @PostMapping
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
