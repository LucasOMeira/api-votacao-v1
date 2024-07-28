package com.projetovotos.api_votacao.dto;

import lombok.Data;

@Data
public class EleitorDTO {
    private Long id;
    private String nome;
    private String cpf;
}
