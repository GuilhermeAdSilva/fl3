package br.edu.ifsudestemg.fl.service;

import br.edu.ifsudestemg.fl.exception.RegraNegocioException;
import br.edu.ifsudestemg.fl.model.entity.Gol;
import br.edu.ifsudestemg.fl.model.entity.Jogador;
import br.edu.ifsudestemg.fl.model.entity.Partida;
import br.edu.ifsudestemg.fl.model.repository.GolRepository;
import br.edu.ifsudestemg.fl.model.repository.JogadorRepository;
import br.edu.ifsudestemg.fl.model.repository.PartidaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class GolService {
    
    private GolRepository repository;
    private PartidaRepository partidaRepository;
    private JogadorRepository jogadorRepository;

    public GolService(GolRepository repository, PartidaRepository partidaRepository, JogadorRepository jogadorRepository) {
        this.repository = repository;
        this.partidaRepository = partidaRepository;
        this.jogadorRepository = jogadorRepository;
    }

    public List<Gol> getGols() {
        List<Gol> gols = repository.findAll();

        for (Gol gol : gols) {
            String nomeTorneio = repository.pegarNomeTorneio(gol.getId());
            gol.setNomeTorneio(nomeTorneio);
        }

        return gols;
    }

    public Optional<Gol> getGolById(Long id) {
        Optional<Gol> golOpt = repository.findById(id);
        golOpt.ifPresent(gol -> gol.setNomeTorneio(repository.pegarNomeTorneio(id)));
        return golOpt;
    }

    @Transactional
    public Gol salvar(Gol gol) {
        validar(gol);
        if(gol.getPartida() != null && gol.getPartida().getId() != null) {
            Partida partida = partidaRepository
                    .findById(gol.getPartida().getId())
                    .orElseThrow(() ->
                            new IllegalArgumentException("Partida não encontrada.")
                    );
            gol.setPartida(partida);
        }
        if(gol.getJogadorGol() != null && gol.getJogadorGol().getId() != null) {
            Jogador jogadorGol = jogadorRepository
                    .findById(gol.getJogadorGol().getId())
                    .orElseThrow(() ->
                            new IllegalArgumentException("Jogador que marcou não encontrado.")
                    );
            gol.setJogadorGol(jogadorGol);
        }
        if(gol.getJogadorAssistencia() != null && gol.getJogadorAssistencia().getId() != null) {
            Jogador jogadorAssistencia = jogadorRepository
                    .findById(gol.getJogadorAssistencia().getId())
                    .orElseThrow(() ->
                            new IllegalArgumentException("Jogador que assistiu não encontrado.")
                    );
            gol.setJogadorAssistencia(jogadorAssistencia);
        }

        Gol golSalvo = repository.save(gol);
        golSalvo.setNomeTorneio(repository.pegarNomeTorneio(golSalvo.getId()));

        return repository.save(gol);
    }

    @Transactional
    public void excluir(Gol gol) {
        Objects.requireNonNull(gol.getId());
        repository.delete(gol);
    }

    public void validar(Gol gol) {
        if (gol.getJogadorGol() == null) {
            throw new RegraNegocioException("Jogador que fez o gol inválido");
        }
        if (gol.getPartida() == null) {
            throw new RegraNegocioException("Gol com partida inválida");
        }
    }

    public String pegarNomeTorneio(Gol gol) {
        return repository.pegarNomeTorneio(gol.getId());
    }
}
