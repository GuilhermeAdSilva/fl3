package br.edu.ifsudestemg.fl.api.dto;

import br.edu.ifsudestemg.fl.model.entity.Partida;
import br.edu.ifsudestemg.fl.model.enums.StatusPartida;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class PartidaDTO {

    private Long id;
    private Long idTorneio;
    private String nomeTorneio;
    private StatusPartida status;
    private Long idEquipeMandante;
    private String nomeEquipeMandante;
    private Integer golsMandante;
    private Long idEquipeVisitante;
    private String nomeEquipeVisitante;
    private Integer golsVisitante;
    private Boolean prorrogacao;
    private Boolean penaltis;
    private Integer penaltisMandante;
    private Integer penaltisVisitante;

    public static PartidaDTO create(Partida partida) {
        ModelMapper modelMapper = new ModelMapper();
        PartidaDTO dto = modelMapper.map(partida, PartidaDTO.class);
        dto.nomeTorneio = partida.getTorneio().getNome();
        dto.nomeEquipeMandante = partida.getEquipeMandante().getNome();
        dto.nomeEquipeVisitante = partida.getEquipeVisitante().getNome();
        return dto;
    }
}
