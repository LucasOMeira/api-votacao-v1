package com.projetovotos.api_votacao.dto;

import lombok.Data;

@Data
public class CandidatoDTO {
    private Long id;
    private String nome;
    private String partido;
}
