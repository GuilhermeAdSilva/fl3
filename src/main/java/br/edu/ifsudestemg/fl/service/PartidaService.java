package br.edu.ifsudestemg.fl.service;

import br.edu.ifsudestemg.fl.exception.RegraNegocioException;
import br.edu.ifsudestemg.fl.model.entity.*;
import br.edu.ifsudestemg.fl.model.entity.Partida;
import br.edu.ifsudestemg.fl.model.enums.StatusPartida;
import br.edu.ifsudestemg.fl.model.repository.PartidaRepository;
import br.edu.ifsudestemg.fl.model.repository.ResultadoRepository;
import br.edu.ifsudestemg.fl.model.repository.TorneioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class PartidaService {

    private final PartidaRepository repository;
    private final TorneioRepository torneioRepository;
    private final ResultadoRepository resultadoRepository;

    public PartidaService(PartidaRepository repository, TorneioRepository torneioRepository, ResultadoRepository resultadoRepository) {
        this.repository = repository;
        this.torneioRepository = torneioRepository;
        this.resultadoRepository = resultadoRepository;
    }

    public List<Partida> getPartidas() {
        return repository.findAll();
    }

    public Optional<Partida> getPartidaById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public Partida salvar(Partida partida) {
        validar(partida);
        if(partida.getTorneio() != null && partida.getTorneio().getId() != null) {
            Torneio torneio = torneioRepository
                    .findById(partida.getTorneio().getId())
                    .orElseThrow(() ->
                            new IllegalArgumentException("Torneio não encontrado.")
                    );
            partida.setTorneio(torneio);
        }
        if(partida.getResultado() != null && partida.getResultado().getId() != null) {
            Resultado resultado = resultadoRepository
                    .findById(partida.getResultado().getId())
                    .orElseThrow(() ->
                            new IllegalArgumentException("Resultado não encontrado.")
                    );
            partida.setResultado(resultado);
        }
        return repository.save(partida);
    }

    @Transactional
    public void excluir(Partida partida) {
        Objects.requireNonNull(partida.getId());
        repository.delete(partida);
    }

    public void validar(Partida partida) {
        if (partida.getTorneio() == null) {
            throw new RegraNegocioException("Torneio inválido em partida");
        }
        if (partida.getStatus() == null) {
            throw new RegraNegocioException("Status de partida inválido");
        }
        if (partida.getStatus() == StatusPartida.FINALIZADA && partida.getStatus() == null) {
            throw new RegraNegocioException("Partida finalizada sem resultado");
        }
    }
}
