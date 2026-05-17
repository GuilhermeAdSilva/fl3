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
    private Long idResultado;
    private StatusPartida status;

    public static PartidaDTO create(Partida partida) {
        ModelMapper modelMapper = new ModelMapper();
        PartidaDTO dto = modelMapper.map(partida, PartidaDTO.class);
        dto.nomeTorneio = partida.getTorneio().getNome();
        return dto;
    }
}
