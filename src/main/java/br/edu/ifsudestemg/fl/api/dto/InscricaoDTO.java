package br.edu.ifsudestemg.fl.api.dto;

import br.edu.ifsudestemg.fl.model.entity.Inscricao;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InscricaoDTO {

    private Long id;
    private Long idEquipe;
    private String nomeEquipe;
    private Long idTorneio;
    private String nomeTorneio;

    public static InscricaoDTO create(Inscricao inscricao) {
        ModelMapper modelMapper = new ModelMapper();
        InscricaoDTO dto = modelMapper.map(inscricao, InscricaoDTO.class);
        dto.nomeEquipe = inscricao.getEquipe().getNome();
        dto.nomeTorneio = inscricao.getTorneio().getNome();
        return dto;
    }
}
