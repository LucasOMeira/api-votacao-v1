package com.projetovotos.api_votacao.repository;

import com.projetovotos.api_votacao.model.Eleitor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EleitorRepository extends JpaRepository<Eleitor, Long> {
    boolean existsByNome(String nome);
    boolean existsByCpf(String cpf);
    boolean existsByIdAndVotosNotEmpty(Long id);
}
