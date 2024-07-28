package com.projetovotos.api_votacao.service;

import com.projetovotos.api_votacao.model.Sessao;
import com.projetovotos.api_votacao.repository.SessaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class SessaoService {

    @Autowired
    private SessaoRepository sessaoRepository;

    @Cacheable("sessoes")
    public List<Sessao> findAll() {
        return sessaoRepository.findAll();
    }

    @Cacheable(value = "sessao", key = "#id")
    public Optional<Sessao> findById(Long id) {
        return sessaoRepository.findById(id);
    }

    @CacheEvict(value = "sessoes", allEntries = true)
    @CachePut(value = "sessao", key = "#result.id")
    public Sessao abrirSessao() {
        Sessao sessao = new Sessao();
        sessao.setInicio(LocalDateTime.now());
        sessao.setAberta(true);
        return sessaoRepository.save(sessao);
    }


    @CacheEvict(value = "sessoes", allEntries = true)
    @CachePut(value = "sessao", key = "#id")
    public Sessao fecharSessao(Long id) {
        Sessao sessao = sessaoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Sessão não encontrada."));

        if (!sessao.isAberta()) {
            throw new IllegalStateException("Sessão já está fechada.");
        }

        sessao.setAberta(false);
        sessao.setFim(LocalDateTime.now());
        sessaoRepository.save(sessao);
        return sessao;
    }
}
