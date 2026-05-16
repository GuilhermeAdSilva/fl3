package br.edu.ifsudestemg.fl.api.dto;

import br.edu.ifsudestemg.fl.model.entity.Torneio;
import br.edu.ifsudestemg.fl.model.enums.FormatoTorneio;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TorneioDTO {
    
    private Long id;
    private String nome;
    private FormatoTorneio formato;
    private Integer quantidadeEquipes;
    private String descricao;

    public static TorneioDTO create(Torneio torneio) {
        ModelMapper modelMapper = new ModelMapper();
        TorneioDTO dto = modelMapper.map(torneio, TorneioDTO.class);
        return dto;
    }
}
