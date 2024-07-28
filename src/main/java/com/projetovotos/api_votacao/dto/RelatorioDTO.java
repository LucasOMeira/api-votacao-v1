package com.projetovotos.api_votacao.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

@Data
public class RelatorioDTO {

    private LocalDateTime dataRelatorio;
    private String cargo;
    private Map<String, Integer> candidatosVotos;
    private Integer totalVotos;
    private String vencedor;
}
