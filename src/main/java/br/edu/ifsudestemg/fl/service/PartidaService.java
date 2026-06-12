package br.edu.ifsudestemg.fl.service;

import br.edu.ifsudestemg.fl.exception.RegraNegocioException;
import br.edu.ifsudestemg.fl.model.entity.*;
import br.edu.ifsudestemg.fl.model.entity.Partida;
import br.edu.ifsudestemg.fl.model.enums.StatusPartida;
import br.edu.ifsudestemg.fl.model.repository.EquipeRepository;
import br.edu.ifsudestemg.fl.model.repository.PartidaRepository;
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
    private final EquipeRepository equipeRepository;

    public PartidaService(PartidaRepository repository, TorneioRepository torneioRepository, EquipeRepository equipeRepository) {
        this.repository = repository;
        this.torneioRepository = torneioRepository;
        this.equipeRepository = equipeRepository;
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
        if(partida.getEquipeMandante() != null && partida.getEquipeMandante().getId() != null) {
            Equipe equipeMandante = equipeRepository
                    .findById(partida.getEquipeMandante().getId())
                    .orElseThrow(() ->
                            new IllegalArgumentException("Equipe mandante não encontrada.")
                    );
            partida.setEquipeMandante(equipeMandante);
        }
        if(partida.getEquipeVisitante() != null && partida.getEquipeVisitante().getId() != null) {
            Equipe equipeVisitante = equipeRepository
                    .findById(partida.getEquipeVisitante().getId())
                    .orElseThrow(() ->
                            new IllegalArgumentException("Equipe visitante não encontrada.")
                    );
            partida.setEquipeVisitante(equipeVisitante);
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

        if (partida.getEquipeMandante() == null || partida.getEquipeVisitante() == null) {
            throw new RegraNegocioException("Partida com equipe inválida");
        }

        if (partida.getStatus() == null) {
            throw new RegraNegocioException("Status de partida inválido");
        }
        if (partida.getStatus() == StatusPartida.FINALIZADA && partida.getStatus() == null) {
            throw new RegraNegocioException("Partida finalizada sem resultado");
        }
    }
}
