package br.edu.ifsudestemg.fl.service;

import br.edu.ifsudestemg.fl.model.entity.Jogador;
import br.edu.ifsudestemg.fl.model.entity.Partida;
import br.edu.ifsudestemg.fl.model.entity.Cartao;
import br.edu.ifsudestemg.fl.model.entity.Torneio;
import br.edu.ifsudestemg.fl.model.repository.CartaoRepository;
import br.edu.ifsudestemg.fl.model.repository.JogadorRepository;
import br.edu.ifsudestemg.fl.model.repository.PartidaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CartaoService {
    
    private final CartaoRepository repository;
    private final PartidaRepository partidaRepository;
    private final JogadorRepository jogadorRepository;

    public CartaoService(CartaoRepository repository, PartidaRepository partidaRepository, JogadorRepository jogadorRepository) {
        this.repository = repository;
        this.partidaRepository = partidaRepository;
        this.jogadorRepository = jogadorRepository;
    }

    public List<Cartao> getCartoes() {
        return repository.findAll();
    }

    public Optional<Cartao> getCartaoById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public Cartao salvar(Cartao cartao) {
        validar(cartao);
        if(cartao.getPartida() != null && cartao.getPartida().getId() != null) {
            Partida partida = partidaRepository
                    .findById(cartao.getPartida().getId())
                    .orElseThrow(() ->
                            new IllegalArgumentException("Partida não encontrada.")
                    );
            cartao.setPartida(partida);
        }
        if(cartao.getJogador() != null && cartao.getJogador().getId() != null) {
            Jogador jogador = jogadorRepository
                    .findById(cartao.getJogador().getId())
                    .orElseThrow(() ->
                            new IllegalArgumentException("Jogador não encontrado.")
                    );
            cartao.setJogador(jogador);
        }
        return repository.save(cartao);
    }

    @Transactional
    public void excluir(Cartao cartao) {
        Objects.requireNonNull(cartao.getId());
        repository.delete(cartao);
    }

    public void validar(Cartao cartao) {
        //vou validar pela dto por bean validator
    }
}
