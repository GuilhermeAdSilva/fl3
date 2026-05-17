package br.edu.ifsudestemg.fl.api.controller;

import br.edu.ifsudestemg.fl.api.dto.TorneioDTO;
import br.edu.ifsudestemg.fl.exception.RegraNegocioException;
import br.edu.ifsudestemg.fl.model.entity.Torneio;
import br.edu.ifsudestemg.fl.service.TorneioService;
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
public class TorneioController {

    private final TorneioService service;

    @GetMapping
    public ResponseEntity get() {
        List<Torneio> torneios = service.getTorneios();
        return ResponseEntity.ok(torneios.stream().map(TorneioDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Torneio> torneio = service.getTorneioById(id);
        if (!torneio.isPresent()) {
            return new ResponseEntity("Torneio não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(torneio.map(TorneioDTO::create));
    }

    @PostMapping
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
