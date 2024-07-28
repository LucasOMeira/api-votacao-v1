package com.projetovotos.api_votacao.repository;

import com.projetovotos.api_votacao.model.Candidato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CandidatoRepository extends JpaRepository<Candidato, Long> {
    boolean existsByNome(String nome);
    boolean existsByPartido(String partido);
    boolean existsByIdAndVotosNotEmpty(Long id);
}
