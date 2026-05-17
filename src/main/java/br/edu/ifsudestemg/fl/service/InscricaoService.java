package br.edu.ifsudestemg.fl.service;

import br.edu.ifsudestemg.fl.model.entity.Equipe;
import br.edu.ifsudestemg.fl.model.entity.Inscricao;
import br.edu.ifsudestemg.fl.model.entity.Torneio;
import br.edu.ifsudestemg.fl.model.repository.EquipeRepository;
import br.edu.ifsudestemg.fl.model.repository.InscricaoRepository;
import br.edu.ifsudestemg.fl.model.repository.TorneioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class InscricaoService {

    private final InscricaoRepository repository;
    private final EquipeRepository equipeRepository;
    private final TorneioRepository torneioRepository;

    public InscricaoService(InscricaoRepository repository, EquipeRepository equipeRepository, TorneioRepository torneioRepository) {
        this.repository = repository;
        this.equipeRepository = equipeRepository;
        this.torneioRepository = torneioRepository;
    }

    public List<Inscricao> getInscricoes() {
        return repository.findAll();
    }

    public Optional<Inscricao> getInscricaoById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public Inscricao salvar(Inscricao inscricao) {
        validar(inscricao);
        if(inscricao.getEquipe() != null && inscricao.getEquipe().getId() != null) {
            Equipe equipe = equipeRepository
                    .findById(inscricao.getEquipe().getId())
                    .orElseThrow(() ->
                            new IllegalArgumentException("Equipe não encontrada.")
                    );
            inscricao.setEquipe(equipe);
        }
        if(inscricao.getTorneio() != null && inscricao.getTorneio().getId() != null) {
            Torneio torneio = torneioRepository
                    .findById(inscricao.getTorneio().getId())
                    .orElseThrow(() ->
                            new IllegalArgumentException("Torneio não encontrado.")
                    );
            inscricao.setTorneio(torneio);
        }
        return repository.save(inscricao);
    }

    @Transactional
    public void excluir(Inscricao inscricao) {
        Objects.requireNonNull(inscricao.getId());
        repository.delete(inscricao);
    }

    public void validar(Inscricao inscricao) {
        //vou validar pela dto por bean validator
    }
}
