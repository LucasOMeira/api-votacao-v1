package com.projetovotos.api_votacao.model;
import jakarta.persistence.*;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.Generated;


@Entity
@Table(name = "cargos")
@Data
public class Cargo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome")
    private String nome;
}