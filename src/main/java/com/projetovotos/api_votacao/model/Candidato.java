package com.projetovotos.api_votacao.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Table(name = "candidatos")
@Entity
public class Candidato {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "partido")
    private String partido;

    @OneToMany(mappedBy = "candidato")
    private List<Voto> votos;
}