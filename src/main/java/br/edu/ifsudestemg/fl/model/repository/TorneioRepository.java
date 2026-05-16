package br.edu.ifsudestemg.fl.model.repository;

import br.edu.ifsudestemg.fl.model.entity.Torneio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TorneioRepository extends JpaRepository<Torneio, Long> {
}
