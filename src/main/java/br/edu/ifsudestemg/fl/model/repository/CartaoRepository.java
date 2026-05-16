package br.edu.ifsudestemg.fl.model.repository;

import br.edu.ifsudestemg.fl.model.entity.Cartao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartaoRepository extends JpaRepository<Cartao, Long> {
}
