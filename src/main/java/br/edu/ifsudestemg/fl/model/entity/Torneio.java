package br.edu.ifsudestemg.fl.model.entity;

import br.edu.ifsudestemg.fl.model.enums.FormatoTorneio;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "torneios")
public class Torneio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @Enumerated(EnumType.STRING)
    private FormatoTorneio formato;

    private Integer quantidadeEquipes;

    private String descricao;
}
