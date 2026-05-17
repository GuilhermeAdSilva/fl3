package br.edu.ifsudestemg.fl.api.dto;

import br.edu.ifsudestemg.fl.model.entity.Gol;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GolDTO {

    private Long id;
    private Long idPartida;
    private Long idJogadorGol;
    private String nomeJogadorGol;
    private Long idJogadorAssistencia;
    private String nomeJogadorAssistencia;

    public static GolDTO create(Gol gol) {
        ModelMapper modelMapper = new ModelMapper();
        GolDTO dto = modelMapper.map(gol, GolDTO.class);
        dto.nomeJogadorGol = gol.getJogadorGol().getNome();
        if (gol.getJogadorAssistencia() != null) {
            dto.nomeJogadorAssistencia = gol.getJogadorAssistencia().getNome();
        }
        return dto;
    }
}
