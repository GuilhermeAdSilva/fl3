package br.edu.ifsudestemg.fl.service;

import br.edu.ifsudestemg.fl.model.entity.Torneio;
import br.edu.ifsudestemg.fl.model.repository.TorneioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class TorneioService {
    
    private final TorneioRepository repository;

    public TorneioService(TorneioRepository repository) {
        this.repository = repository;
    }

    public List<Torneio> getTorneios() {
        return repository.findAll();
    }

    public Optional<Torneio> getTorneioById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public Torneio salvar(Torneio torneio) {
        validar(torneio);
        return repository.save(torneio);
    }

    @Transactional
    public void excluir(Torneio torneio) {
        Objects.requireNonNull(torneio.getId());
        repository.delete(torneio);
    }

    public void validar(Torneio torneio) {
        //vou validar pela dto por bean validator
    }
}
