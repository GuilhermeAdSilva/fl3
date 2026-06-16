package br.edu.ifsudestemg.fl.model.repository;

import br.edu.ifsudestemg.fl.model.entity.Equipe;
import br.edu.ifsudestemg.fl.model.entity.Inscricao;
import br.edu.ifsudestemg.fl.model.entity.Torneio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InscricaoRepository extends JpaRepository<Inscricao, Long> {
    Optional<Inscricao> findByEquipeAndTorneio(Equipe equipe, Torneio torneio);
}
