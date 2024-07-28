package com.projetovotos.api_votacao.controller;

import com.projetovotos.api_votacao.model.Candidato;
import com.projetovotos.api_votacao.service.CandidatoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/candidatos")
public class CandidatoController {

    @Autowired
    private CandidatoService candidatoService;

    @GetMapping
    public ResponseEntity<List<Candidato>> listarCandidatos() {
        List<Candidato> candidatos = candidatoService.findAll();
        return ResponseEntity.ok(candidatos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Candidato> obterCandidato(@PathVariable Long id) {
        Optional<Candidato> candidato = candidatoService.findById(id);
        return candidato.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Candidato> salvarCandidato(@RequestBody Candidato candidato) {
        Candidato savedCandidato = candidatoService.save(candidato);
        return ResponseEntity.ok(savedCandidato);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarCandidato(@PathVariable Long id) {
        try {
            candidatoService.deleteById(id);
            return ResponseEntity.noContent().build(); // Retorna 204 No Content
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build(); // Retorna 404 Not Found se o candidato não for encontrado
        }
    }
}
