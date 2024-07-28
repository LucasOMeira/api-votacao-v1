package com.projetovotos.api_votacao.service;

import com.projetovotos.api_votacao.model.Candidato;
import com.projetovotos.api_votacao.repository.CandidatoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;
import java.util.Optional;

@Service
public class CandidatoService {

    @Autowired
    private CandidatoRepository candidatoRepository;

    @Cacheable("candidatos")
    public List<Candidato> findAll() {
        return candidatoRepository.findAll();
    }

    @Cacheable(value = "candidato", key = "#id")
    public Optional<Candidato> findById(Long id) {
        return candidatoRepository.findById(id);
    }

    @CachePut(value = "candidato", key = "#candidato.id")
    @CacheEvict(value = "candidatos", allEntries = true)
    public Candidato save(Candidato candidato) {
        if (candidatoRepository.existsByNome(candidato.getNome()) && candidatoRepository.existsByPartido(candidato.getPartido())) {
            throw new IllegalArgumentException("Candidato duplicado: não é possível cadastrar o mesmo candidato duas vezes.");
        }
        return candidatoRepository.save(candidato);
    }

    public void deleteById(Long id) {
        if (candidatoRepository.existsByIdAndVotosNotEmpty(id)) {
            throw new IllegalStateException("Candidato não pode ser deletado, pois já possui votos.");
        }
        candidatoRepository.deleteById(id);
    }
}
