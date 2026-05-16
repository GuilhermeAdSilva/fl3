package br.edu.ifsudestemg.fl.api.controller;

import br.edu.ifsudestemg.fl.api.dto.EquipeDTO;
import br.edu.ifsudestemg.fl.api.dto.JogadorDTO;
import br.edu.ifsudestemg.fl.exception.RegraNegocioException;
import br.edu.ifsudestemg.fl.model.entity.Equipe;
import br.edu.ifsudestemg.fl.model.entity.Jogador;
import br.edu.ifsudestemg.fl.service.EquipeService;
import br.edu.ifsudestemg.fl.service.JogadorService;
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

public class JogadorController {

    private final JogadorService service;
    private final EquipeService equipeService;

    @GetMapping
    public ResponseEntity get() {
        List<Jogador> jogadors = service.getJogadores();
        return ResponseEntity.ok(jogadors.stream().map(JogadorDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Jogador> jogador = service.getJogadorById(id);
        if (!jogador.isPresent()) {
            return new ResponseEntity("Jogador não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(jogador.map(JogadorDTO::create));
    }

    @PostMapping
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
