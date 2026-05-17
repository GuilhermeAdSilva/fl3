package br.edu.ifsudestemg.fl.api.controller;

import br.edu.ifsudestemg.fl.api.dto.GolDTO;
import br.edu.ifsudestemg.fl.exception.RegraNegocioException;
import br.edu.ifsudestemg.fl.model.entity.Gol;
import br.edu.ifsudestemg.fl.service.GolService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/gols")
@RequiredArgsConstructor
public class GolController {

    private final GolService service;

    @GetMapping
    public ResponseEntity get() {
        List<Gol> gols = service.getGols();
        return ResponseEntity.ok(gols.stream().map(GolDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Gol> gol = service.getGolById(id);
        if (!gol.isPresent()) {
            return new ResponseEntity("Gol não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(gol.map(GolDTO::create));
    }

    @PostMapping
    public ResponseEntity post(@RequestBody GolDTO dto) {
        try {
            Gol gol = converter(dto);
            gol = service.salvar(gol);
            return new ResponseEntity(gol, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody GolDTO dto) {
        if (!service.getGolById(id).isPresent()) {
            return new ResponseEntity("Gol não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            Gol gol = converter(dto);
            gol.setId(id);
            service.salvar(gol);
            return ResponseEntity.ok(gol);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<Gol> gol = service.getGolById(id);
        if (!gol.isPresent()) {
            return new ResponseEntity("Gol não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(gol.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Gol converter(GolDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Gol gol = modelMapper.map(dto, Gol.class);
        return gol;
    }
}
