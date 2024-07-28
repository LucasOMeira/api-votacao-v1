package com.projetovotos.api_votacao.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.Generated;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "eleitores")
@Data
@JsonIgnoreProperties("votos")
public class Eleitor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "cpf")
    private String cpf;

    @OneToMany(mappedBy = "eleitor")
    private List<Voto> votos;
}