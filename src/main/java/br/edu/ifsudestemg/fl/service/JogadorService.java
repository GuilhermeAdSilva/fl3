package br.edu.ifsudestemg.fl.service;

import br.edu.ifsudestemg.fl.exception.RegraNegocioException;
import br.edu.ifsudestemg.fl.model.entity.Equipe;
import br.edu.ifsudestemg.fl.model.entity.Jogador;
import br.edu.ifsudestemg.fl.model.repository.EquipeRepository;
import br.edu.ifsudestemg.fl.model.repository.JogadorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class JogadorService {

    private final JogadorRepository repository;
    private final EquipeRepository equipeRepository;

    public JogadorService(JogadorRepository repository, EquipeRepository equipeRepository) {
        this.repository = repository;
        this.equipeRepository = equipeRepository;
    }

    public List<Jogador> getJogadores() {
        return repository.findAll();
    }

    public Optional<Jogador> getJogadorById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public Jogador salvar(Jogador jogador) {
        validar(jogador);
        if(jogador.getEquipe() != null && jogador.getEquipe().getId() != null) {
            Equipe equipe = equipeRepository
                    .findById(jogador.getEquipe().getId())
                    .orElseThrow(() ->
                            new IllegalArgumentException("Equipe não encontrada.")
                    );
            jogador.setEquipe(equipe);
        }
        return repository.save(jogador);
    }

    @Transactional
    public void excluir(Jogador jogador) {
        Objects.requireNonNull(jogador.getId());
        repository.delete(jogador);
    }

    public void validar(Jogador jogador) {
        if (jogador.getNome() == null ||
                jogador.getNome().trim().length() < 2 ||
                jogador.getNome().trim().length() > 100) {
            throw new RegraNegocioException("Nome inválido");
        }

        if (jogador.getEmail() == null ||
                jogador.getEmail().trim().length() < 2 ||
                jogador.getEmail().trim().length() > 100) {
            throw new RegraNegocioException("E-mail inválido");
        }
    }
}
