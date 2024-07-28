package com.projetovotos.api_votacao.controller;

import com.projetovotos.api_votacao.model.Sessao;
import com.projetovotos.api_votacao.service.SessaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;

@RestController
@RequestMapping("/sessoes")
public class SessaoController {

    @Autowired
    private SessaoService sessaoService;

    @GetMapping
    public ResponseEntity<List<Sessao>> listarSessoes() {
        List<Sessao> sessoes = sessaoService.findAll();
        return ResponseEntity.ok(sessoes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Sessao> obterSessao(@PathVariable Long id) {
        try {
            Sessao sessao = sessaoService.findById(id).orElseThrow(() -> new EntityNotFoundException("Sessão não encontrada"));
            return ResponseEntity.ok(sessao);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(404).body(null); // Not Found
        }
    }

    @PostMapping("/abrir-sessao")
    public ResponseEntity<Sessao> abrirSessao() {
        Sessao sessao = sessaoService.abrirSessao();
        return ResponseEntity.status(201).body(sessao);
    }

    @PatchMapping("/fechar-sessao/{id}")
    public ResponseEntity<String> fecharSessao(@PathVariable Long id) {
        try {
            sessaoService.fecharSessao(id);
            return ResponseEntity.ok("SESSÃO ENCERRADA COM SUCESSO");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(404).body("Sessão não encontrada");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro interno do servidor");
        }
    }
}
