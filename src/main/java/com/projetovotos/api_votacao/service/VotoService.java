package com.projetovotos.api_votacao.service;

import com.projetovotos.api_votacao.model.Voto;
import com.projetovotos.api_votacao.repository.VotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;
import java.util.Optional;

@Service
public class VotoService {

    @Autowired
    private VotoRepository votoRepository;

    @Cacheable("votos")
    public Optional<Voto> findById(Long id) {
        return votoRepository.findById(id);
    }

    @Cacheable(value = "voto", key = "#id")
    public Voto votar(Voto voto) {
        if (votoRepository.existsByEleitorId(voto.getEleitor().getId())) {
            throw new IllegalArgumentException("Eleitor já votou.");
        }
        return votoRepository.save(voto);
    }
}
