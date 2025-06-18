package com.suporteJoaoPessoa.chamados.repository;

import com.suporteJoaoPessoa.chamados.model.entity.AgenteDeTi;
import com.suporteJoaoPessoa.chamados.model.entity.Chamado;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChamadoRepository extends JpaRepository<Chamado, Long> {
    List<Chamado> findByStatus(String status);
    List<Chamado> findByAgenteDeTi(AgenteDeTi agenteDeTi);


    List<Chamado> findByResponsavelIsNull();

    List<Chamado> findByResponsavel(String nomeCompleto);

    List<Chamado> findByAgenteDeTiId(Long id);
}

