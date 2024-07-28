package com.projetovotos.api_votacao.repository;

import com.projetovotos.api_votacao.model.Eleitor;
import com.projetovotos.api_votacao.model.Sessao;
import com.projetovotos.api_votacao.model.Voto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VotoRepository extends JpaRepository<Voto, Long> {
    boolean existsByEleitorId(Long eleitorId);
    List<Voto> findBySessao(Sessao sessao);

    boolean existsByEleitorAndSessao(Eleitor eleitor, Sessao sessao);
}
