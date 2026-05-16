package br.edu.ifsudestemg.fl.model.repository;

import br.edu.ifsudestemg.fl.model.entity.Inscricao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InscricaoRepository extends JpaRepository<Inscricao, Long> {
}
