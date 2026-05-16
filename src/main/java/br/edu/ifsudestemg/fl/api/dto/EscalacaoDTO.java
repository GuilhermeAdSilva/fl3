package br.edu.ifsudestemg.fl.api.dto;

import br.edu.ifsudestemg.fl.model.entity.Escalacao;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class EscalacaoDTO {

    private Long id;
    private Long idPartida;
    private Long idEquipe;
    private String nomeEquipe;
    private Long idJogador;
    private String nomeJogador;

    public static EscalacaoDTO create(Escalacao escalacao) {
        ModelMapper modelMapper = new ModelMapper();
        EscalacaoDTO dto = modelMapper.map(escalacao, EscalacaoDTO.class);
        dto.nomeEquipe = escalacao.getEquipe().getNome();
        dto.nomeJogador = escalacao.getJogador().getNome();
        return dto;
    }
}
