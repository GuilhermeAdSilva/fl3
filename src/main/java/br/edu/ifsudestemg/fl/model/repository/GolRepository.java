package br.edu.ifsudestemg.fl.model.repository;

import br.edu.ifsudestemg.fl.model.entity.Gol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface GolRepository extends JpaRepository<Gol, Long> {
    @Query(" select g.partida.torneio.nome from Gol g where g.id = ?1 ")
    String pegarNomeTorneio (Long id);
}
