package br.edu.ifsudestemg.fl.service;

import br.edu.ifsudestemg.fl.exception.RegraNegocioException;
import br.edu.ifsudestemg.fl.model.entity.*;
import br.edu.ifsudestemg.fl.model.repository.EquipeRepository;
import br.edu.ifsudestemg.fl.model.repository.EscalacaoRepository;
import br.edu.ifsudestemg.fl.model.repository.JogadorRepository;
import br.edu.ifsudestemg.fl.model.repository.PartidaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class EscalacaoService {

    private final EscalacaoRepository repository;
    private final PartidaRepository partidaRepository;
    private final EquipeRepository equipeRepository;
    private final JogadorRepository jogadorRepository;

    public EscalacaoService(EscalacaoRepository repository, PartidaRepository partidaRepository, EquipeRepository equipeRepository, JogadorRepository jogadorRepository) {
        this.repository = repository;
        this.partidaRepository = partidaRepository;
        this.equipeRepository = equipeRepository;
        this.jogadorRepository = jogadorRepository;
    }

    public List<Escalacao> getEscalacoes() {
        return repository.findAll();
    }

    public Optional<Escalacao> getEscalacaoById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public Escalacao salvar(Escalacao escalacao) {
        validar(escalacao);
        if(escalacao.getPartida() != null && escalacao.getPartida().getId() != null) {
            Partida partida = partidaRepository
                    .findById(escalacao.getPartida().getId())
                    .orElseThrow(() ->
                            new IllegalArgumentException("Partida não encontrada.")
                    );
            escalacao.setPartida(partida);
        }
        if(escalacao.getEquipe() != null && escalacao.getEquipe().getId() != null) {
            Equipe equipe = equipeRepository
                    .findById(escalacao.getEquipe().getId())
                    .orElseThrow(() ->
                            new IllegalArgumentException("Equipe não encontrada.")
                    );
            escalacao.setEquipe(equipe);
        }
        if(escalacao.getJogador() != null && escalacao.getJogador().getId() != null) {
            Jogador jogador = jogadorRepository
                    .findById(escalacao.getJogador().getId())
                    .orElseThrow(() ->
                            new IllegalArgumentException("Jogador não encontrado.")
                    );
            escalacao.setJogador(jogador);
        }
        return repository.save(escalacao);
    }

    @Transactional
    public void excluir(Escalacao escalacao) {
        Objects.requireNonNull(escalacao.getId());
        repository.delete(escalacao);
    }

    public void validar(Escalacao escalacao) {
        if (escalacao.getEquipe() == null) {
            throw new RegraNegocioException("Equipe da escalação inváldia");
        }
        if (escalacao.getJogador() == null) {
            throw new RegraNegocioException("Jogador da escalação inválido");
        }
        if (escalacao.getPartida() == null) {
            throw new RegraNegocioException("Partida da escalação inválida");
        }
    }
}
