package br.edu.ifsudestemg.fl.model.repository;

import br.edu.ifsudestemg.fl.model.entity.Equipe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface EquipeRepository extends JpaRepository<Equipe, Long> {

    Optional<Equipe> findByNome(String nome);
}
