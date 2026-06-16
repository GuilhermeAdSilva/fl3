package br.edu.ifsudestemg.fl.model.repository;

import br.edu.ifsudestemg.fl.model.entity.Equipe;
import br.edu.ifsudestemg.fl.model.entity.Inscricao;
import br.edu.ifsudestemg.fl.model.entity.Torneio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface InscricaoRepository extends JpaRepository<Inscricao, Long> {
    Optional<Inscricao> findByEquipeAndTorneio(Equipe equipe, Torneio torneio);

    @Query(" select count(i) from Inscricao i where i.torneio.id = ?1 ")
    Integer quantidadeEquipesInscritasTorneio(Long idTorneio);

    @Query(" select t.quantidadeEquipes from Torneio t where t.id = ?1" )
    Integer buscarLimiteEquipes(Long idTorneio);
}
