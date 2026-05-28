package br.edu.ifsudestemg.fl.api.controller;

import br.edu.ifsudestemg.fl.api.dto.CartaoDTO;
import br.edu.ifsudestemg.fl.exception.RegraNegocioException;
import br.edu.ifsudestemg.fl.model.entity.Cartao;
import br.edu.ifsudestemg.fl.service.CartaoService;
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
@RequestMapping("/api/v1/cartoes")
@RequiredArgsConstructor
@Api
public class CartaoController {
    
    private final CartaoService service;

    @GetMapping
    @ApiOperation("Obter detalhes de todos os cartões")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Busca realizada")
    })
    public ResponseEntity get() {
        List<Cartao> cartoes = service.getCartoes();
        return ResponseEntity.ok(cartoes.stream().map(CartaoDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @ApiOperation("Obter detalhes de um cartão")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Cartão encontrado"),
            @ApiResponse(code = 404, message = "Cartão não encontrado")
    })
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Cartao> cartao = service.getCartaoById(id);
        if (!cartao.isPresent()) {
            return new ResponseEntity("Cartão não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(cartao.map(CartaoDTO::create));
    }

    @PostMapping
    @ApiOperation("Salva um novo cartão")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Cartão salvo com sucesso"),
            @ApiResponse(code = 400, message = "Erro ao salvar o cartão")
    })
    public ResponseEntity post(@RequestBody CartaoDTO dto) {
        try {
            Cartao cartao = converter(dto);
            cartao = service.salvar(cartao);
            return new ResponseEntity(cartao, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    @ApiOperation("Altera um cartão existente")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Cartão alterado com sucesso"),
            @ApiResponse(code = 400, message = "Erro ao salvar o cartão"),
            @ApiResponse(code = 404, message = "Cartão não encontrado")
    })
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody CartaoDTO dto) {
        if (!service.getCartaoById(id).isPresent()) {
            return new ResponseEntity("Cartão não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            Cartao cartao = converter(dto);
            cartao.setId(id);
            service.salvar(cartao);
            return ResponseEntity.ok(cartao);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    @ApiOperation("Deleta um cartão existente")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Cartão deletado com sucesso"),
            @ApiResponse(code = 404, message = "Cartão não encontrado"),
            @ApiResponse(code = 500, message = "Erro ao deletar o cartão")
    })
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<Cartao> cartao = service.getCartaoById(id);
        if (!cartao.isPresent()) {
            return new ResponseEntity("Cartão não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(cartao.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Cartao converter(CartaoDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Cartao cartao = modelMapper.map(dto, Cartao.class);
        return cartao;
    }
}
