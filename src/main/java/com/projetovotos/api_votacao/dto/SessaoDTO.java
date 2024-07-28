package com.projetovotos.api_votacao.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SessaoDTO {
    private Long id;
    private LocalDateTime inicio;
    private LocalDateTime fim;
    private boolean aberta;
}
