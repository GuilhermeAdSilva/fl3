package br.edu.ifsudestemg.fl.api.dto;

import br.edu.ifsudestemg.fl.model.entity.Cartao;
import br.edu.ifsudestemg.fl.model.enums.CorCartao;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartaoDTO {

    private Long id;
    private CorCartao cor;
    private Long idPartida;
    private Long idJogador;
    private String nomeJogador;

    public static CartaoDTO create(Cartao cartao) {
        ModelMapper modelMapper = new ModelMapper();
        CartaoDTO dto = modelMapper.map(cartao, CartaoDTO.class);
        dto.nomeJogador = cartao.getJogador().getNome();
        return dto;
    }
}
