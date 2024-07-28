package com.projetovotos.api_votacao.service;

import com.projetovotos.api_votacao.dto.VotoDTO;
import com.projetovotos.api_votacao.model.Candidato;
import com.projetovotos.api_votacao.model.Eleitor;
import com.projetovotos.api_votacao.model.Sessao;
import com.projetovotos.api_votacao.model.Voto;
import com.projetovotos.api_votacao.repository.CandidatoRepository;
import com.projetovotos.api_votacao.repository.EleitorRepository;
import com.projetovotos.api_votacao.repository.SessaoRepository;
import com.projetovotos.api_votacao.repository.VotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EleitorService {

    @Autowired
    EleitorRepository eleitorRepository;
    CandidatoRepository candidatoRepository;
    SessaoRepository sessaoRepository;
    VotoRepository votoRepository;

    @Cacheable("eleitores")
    public List<Eleitor> findAll() {
        return eleitorRepository.findAll();
    }

    @Cacheable(value = "eleitor", key = "#id")
    public Optional<Eleitor> findById(Long id) {
        return eleitorRepository.findById(id);
    }

    @CachePut(value = "eleitor", key = "#eleitor.id")
    @CacheEvict(value = "eleitores", allEntries = true)
    public Eleitor save(Eleitor eleitor) {
        if(eleitorRepository.existsByCpf(eleitor.getCpf())) {
            throw new IllegalArgumentException("Eleitor com o mesmo cpf já existe");
        }
        return eleitorRepository.save(eleitor);
    }

    @CacheEvict(value = {"eleitores", "sessoes"}, allEntries = true)
    public void votar(Long eleitorId, VotoDTO votoDTO) {
        Sessao sessao = sessaoRepository.findById(votoDTO.getSessaoId())
                .orElseThrow(() -> new IllegalArgumentException("Sessão não encontrada."));

        if (!sessao.isAberta()) {
            throw new IllegalArgumentException("Sessão não está aberta.");
        }

        Eleitor eleitor = eleitorRepository.findById(eleitorId)
                .orElseThrow(() -> new IllegalArgumentException("Eleitor não encontrado."));

        Candidato candidato = candidatoRepository.findById(votoDTO.getCandidatoId())
                .orElseThrow(() -> new IllegalArgumentException("Candidato não encontrado."));

        // Verificar se o eleitor já votou nesta sessão
        if (votoRepository.existsByEleitorAndSessao(eleitor, sessao)) {
            throw new IllegalArgumentException("Eleitor já votou nesta sessão.");
        }

        Voto voto = new Voto();
        voto.setEleitor(eleitor);
        voto.setCandidato(candidato);
        voto.setSessao(sessao);

        votoRepository.save(voto);
    }

    public void deleteById(Long id) {
        eleitorRepository.deleteById(id);
    }
}
