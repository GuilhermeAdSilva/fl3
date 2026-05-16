package br.edu.ifsudestemg.fl.api.dto;

import br.edu.ifsudestemg.fl.model.entity.Resultado;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultadoDTO {

    private Long id;
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

    public static ResultadoDTO create(Resultado resultado) {
        ModelMapper modelMapper = new ModelMapper();
        ResultadoDTO dto = modelMapper.map(resultado, ResultadoDTO.class);
        dto.nomeEquipeMandante = resultado.getEquipeMandante().getNome();
        dto.nomeEquipeVisitante = resultado.getEquipeVisitante().getNome();
        return dto;
    }
}
