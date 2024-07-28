package com.projetovotos.api_votacao.controller;

import com.projetovotos.api_votacao.dto.RelatorioDTO;
import com.projetovotos.api_votacao.service.RelatorioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/boletim-urna")
public class RelatorioController {

    @Autowired
    private RelatorioService relatorioService;

    @GetMapping("/{idSessao}")
    public ResponseEntity<RelatorioDTO> gerarRelatorio(@PathVariable Long idSessao) {
        try {
            RelatorioDTO relatorio = relatorioService.gerarRelatorio(idSessao);
            return ResponseEntity.ok(relatorio);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}
