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
    @Column(name = "id")
    private Long id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "formato")
    private FormatoTorneio formato;

    @Column(name = "quantidade_equipes")
    private Integer quantidadeEquipes;

    @Column(name = "descricao")
    private String descricao;
}
