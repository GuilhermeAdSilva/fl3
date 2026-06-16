package br.edu.ifsudestemg.fl.model.repository;

import br.edu.ifsudestemg.fl.model.entity.Jogador;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface JogadorRepository extends JpaRepository<Jogador, Long> {
}
