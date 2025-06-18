package com.suporteJoaoPessoa.chamados.repository;

import com.suporteJoaoPessoa.chamados.model.entity.AgenteDeTi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AgenteDeTiRepository extends JpaRepository<AgenteDeTi, Long> {
    Optional<AgenteDeTi> findByMatricula(String matricula);

    boolean existsByMatricula(String matricula);
}
