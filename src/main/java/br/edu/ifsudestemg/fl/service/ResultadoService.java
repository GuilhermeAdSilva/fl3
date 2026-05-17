package br.edu.ifsudestemg.fl.service;

import br.edu.ifsudestemg.fl.model.entity.*;
import br.edu.ifsudestemg.fl.model.entity.Resultado;
import br.edu.ifsudestemg.fl.model.repository.EquipeRepository;
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

    public ResultadoService(ResultadoRepository repository, EquipeRepository equipeRepository) {
        this.repository = repository;
        this.equipeRepository = equipeRepository;
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
        return repository.save(resultado);
    }

    @Transactional
    public void excluir(Resultado resultado) {
        Objects.requireNonNull(resultado.getId());
        repository.delete(resultado);
    }

    public void validar(Resultado resultado) {
        //vou validar pela dto por bean validator
    }
}
