package com.projetovotos.api_votacao.dto;

import lombok.Data;

@Data
public class VotoDTO {

    private Long id;
    private Long eleitorId;
    private Long candidatoId;
    private Long sessaoId;
}
