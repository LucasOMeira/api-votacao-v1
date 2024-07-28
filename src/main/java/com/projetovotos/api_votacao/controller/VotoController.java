package com.projetovotos.api_votacao.controller;

import com.projetovotos.api_votacao.dto.VotoDTO;
import com.projetovotos.api_votacao.model.Candidato;
import com.projetovotos.api_votacao.model.Eleitor;
import com.projetovotos.api_votacao.model.Sessao;
import com.projetovotos.api_votacao.model.Voto;
import com.projetovotos.api_votacao.service.CandidatoService;
import com.projetovotos.api_votacao.service.EleitorService;
import com.projetovotos.api_votacao.service.SessaoService;
import com.projetovotos.api_votacao.service.VotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/votos")
public class VotoController {

    @Autowired
    private VotoService votoService;

    @Autowired
    private EleitorService eleitorService;

    @Autowired
    private CandidatoService candidatoService;

    @Autowired
    private SessaoService sessaoService;

    @PostMapping
    public ResponseEntity<VotoDTO> votar(@RequestBody VotoDTO votoDTO) {
        try {
            // Buscar o eleitor, candidato e sessão pelos IDs
            Eleitor eleitor = eleitorService.findById(votoDTO.getEleitorId())
                    .orElseThrow(() -> new IllegalArgumentException("Eleitor não encontrado."));
            Candidato candidato = candidatoService.findById(votoDTO.getCandidatoId())
                    .orElseThrow(() -> new IllegalArgumentException("Candidato não encontrado."));
            Sessao sessao = sessaoService.findById(votoDTO.getSessaoId())
                    .orElseThrow(() -> new IllegalArgumentException("Sessão não encontrada."));

            // Verificar se a sessão está aberta
            if (!sessao.isAberta()) {
                throw new IllegalStateException("Sessão está fechada.");
            }

            // Criar e salvar o voto
            Voto voto = new Voto();
            voto.setEleitor(eleitor);
            voto.setCandidato(candidato);
            voto.setSessao(sessao);

            Voto savedVoto = votoService.votar(voto);

            // Transformar o modelo em DTO para a resposta
            VotoDTO savedVotoDTO = new VotoDTO();
            savedVotoDTO.setId(savedVoto.getId());
            savedVotoDTO.setEleitorId(savedVoto.getEleitor().getId());
            savedVotoDTO.setCandidatoId(savedVoto.getCandidato().getId());
            savedVotoDTO.setSessaoId(savedVoto.getSessao().getId());

            return ResponseEntity.ok(savedVotoDTO);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(400).body(null); // Bad Request
        } catch (IllegalStateException e) {
            return ResponseEntity.status(400).body(null); // Bad Request
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null); // Internal Server Error
        }
    }
}
