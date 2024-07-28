package com.projetovotos.api_votacao.repository;
import com.projetovotos.api_votacao.model.Cargo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CargoRepository extends JpaRepository<Cargo, Long>{
}
