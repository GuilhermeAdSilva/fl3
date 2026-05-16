package br.edu.ifsudestemg.fl.api.dto;

import br.edu.ifsudestemg.fl.model.entity.Jogador;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JogadorDTO {

    private Long id;
    private String nome;
    private String email;
    private String senha;
    private Long idEquipe;
    private String nomeEquipe;

    public static JogadorDTO create(Jogador jogador) {
        ModelMapper modelMapper = new ModelMapper();
        JogadorDTO dto = modelMapper.map(jogador, JogadorDTO.class);
        dto.nomeEquipe = jogador.getEquipe().getNome();
        return dto;
    }
}
