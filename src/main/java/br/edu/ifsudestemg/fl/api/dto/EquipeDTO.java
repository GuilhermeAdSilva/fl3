package br.edu.ifsudestemg.fl.api.dto;

import br.edu.ifsudestemg.fl.model.entity.Equipe;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EquipeDTO {

    private Long id;
    private String nome;

    public static EquipeDTO create(Equipe equipe) {
        ModelMapper modelMapper = new ModelMapper();
        EquipeDTO dto = modelMapper.map(equipe, EquipeDTO.class);
        return dto;
    }
}
