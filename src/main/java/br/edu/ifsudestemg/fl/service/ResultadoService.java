package br.edu.ifsudestemg.fl.service;

import br.edu.ifsudestemg.fl.exception.RegraNegocioException;
import br.edu.ifsudestemg.fl.model.entity.*;
import br.edu.ifsudestemg.fl.model.entity.Resultado;
import br.edu.ifsudestemg.fl.model.repository.EquipeRepository;
import br.edu.ifsudestemg.fl.model.repository.PartidaRepository;
import br.edu.ifsudestemg.fl.model.repository.JogadorRepository;
import br.edu.ifsudestemg.fl.model.repository.ResultadoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ResultadoService {
    
    private final ResultadoRepository repository;
    private final EquipeRepository equipeRepository;
    private final PartidaRepository partidaRepository;

    public ResultadoService(ResultadoRepository repository, EquipeRepository equipeRepository, PartidaRepository partidaRepository) {
        this.repository = repository;
        this.equipeRepository = equipeRepository;
        this.partidaRepository = partidaRepository;
    }

    public List<Resultado> getResultados() {
        return repository.findAll();
    }

    public Optional<Resultado> getResultadoById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public Resultado salvar(Resultado resultado) {
        validar(resultado);
        if(resultado.getEquipeMandante() != null && resultado.getEquipeMandante().getId() != null) {
            Equipe equipeMandante = equipeRepository
                    .findById(resultado.getEquipeMandante().getId())
                    .orElseThrow(() ->
                            new IllegalArgumentException("Equipe mandante não encontrada.")
                    );
            resultado.setEquipeMandante(equipeMandante);
        }
        if(resultado.getEquipeVisitante() != null && resultado.getEquipeVisitante().getId() != null) {
            Equipe equipeVisitante = equipeRepository
                    .findById(resultado.getEquipeVisitante().getId())
                    .orElseThrow(() ->
                            new IllegalArgumentException("Equipe visitante não encontrada.")
                    );
            resultado.setEquipeVisitante(equipeVisitante);
        }
        if(resultado.getPartida() != null && resultado.getPartida().getId() != null) {
            Partida partida = partidaRepository
                    .findById(resultado.getPartida().getId())
                    .orElseThrow(() ->
                            new IllegalArgumentException("Partida não encontrada.")
                    );
            resultado.setPartida(partida);
        }
        return repository.save(resultado);
    }

    @Transactional
    public void excluir(Resultado resultado) {
        Objects.requireNonNull(resultado.getId());
        repository.delete(resultado);
    }

    public void validar(Resultado resultado) {

        if (resultado.getEquipeMandante() == null || resultado.getEquipeVisitante() == null) {
            throw new RegraNegocioException("Resultado com equipe inválida");
        }

        if (resultado.getPartida() == null) {
            throw new RegraNegocioException("Partida inválida");
        }

        if (resultado.getGolsMandante() == null || resultado.getGolsVisitante() == null) {
            throw new RegraNegocioException("Gols inválidos");
        }

        if (resultado.getProrrogacao() == null) {
            throw new RegraNegocioException("Prorrogação inválida");
        }

        if (resultado.getPenaltis() == null ||
                (resultado.getPenaltis() == true &&
                        (resultado.getPenaltisMandante() == null || resultado.getPenaltisVisitante() == null))) {
            throw new RegraNegocioException("Penaltis inválidos");
        }
    }
}
