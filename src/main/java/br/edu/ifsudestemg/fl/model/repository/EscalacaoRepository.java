package br.edu.ifsudestemg.fl.model.repository;

import br.edu.ifsudestemg.fl.model.entity.Escalacao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EscalacaoRepository extends JpaRepository<Escalacao, Long> {
}
