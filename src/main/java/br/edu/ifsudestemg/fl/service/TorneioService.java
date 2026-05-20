package br.edu.ifsudestemg.fl.service;

import br.edu.ifsudestemg.fl.exception.RegraNegocioException;
import br.edu.ifsudestemg.fl.model.entity.Torneio;
import br.edu.ifsudestemg.fl.model.enums.FormatoTorneio;
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
        if (torneio.getNome() == null ||
                torneio.getNome().trim().length() < 2 ||
                torneio.getNome().trim().length() > 45) {
            throw new RegraNegocioException("Nome inválido");
        }

        if (torneio.getFormato() == null) {
            throw new RegraNegocioException("Formato inválido");
        }

        if (torneio.getQuantidadeEquipes() == null) {
            throw new RegraNegocioException("Quantidade de equipes inválida");
        }

        if (torneio.getFormato() == FormatoTorneio.PONTOS_CORRIDOS &&
                (torneio.getQuantidadeEquipes() < 2 || torneio.getQuantidadeEquipes() > 32)) {
            throw new RegraNegocioException("Quantidade de equipes inválida");
        }

        if (torneio.getFormato() == FormatoTorneio.MATA_MATA) {
            int check = 2;
            for (int i = 1; i <= 6; i++) {
                if (torneio.getQuantidadeEquipes() == Math.pow(check, i)) {
                    check = 0;
                    break;
                }
            }
            if (check != 0) {
                throw new RegraNegocioException("Quantidade de equipes inválida");
            }
        }

        if (torneio.getFormato() == FormatoTorneio.FASE_DE_GRUPOS &&
                (torneio.getQuantidadeEquipes() < 3 || torneio.getQuantidadeEquipes() > 64)) {
            throw new RegraNegocioException("Quantidade de equipes inválida");
        }

        if (torneio.getDescricao() != null && torneio.getDescricao().length() > 255) {
            throw new RegraNegocioException("Descrição inválida");
        }
    }
}
