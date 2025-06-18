package com.suporteJoaoPessoa.chamados.controller;

import com.suporteJoaoPessoa.chamados.dto.AgenteDeTiDTO;
import com.suporteJoaoPessoa.chamados.dto.ChamadoDTO;
import com.suporteJoaoPessoa.chamados.model.entity.AgenteDeTi;
import com.suporteJoaoPessoa.chamados.model.entity.Chamado;
import com.suporteJoaoPessoa.chamados.model.enums.StatusChamado;
import com.suporteJoaoPessoa.chamados.repository.AgenteDeTiRepository;
import com.suporteJoaoPessoa.chamados.repository.ChamadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.suporteJoaoPessoa.chamados.model.enums.StatusChamado.EM_ANDAMENTO;
@RestController
@RequestMapping("/api/chamados")
public class ChamadoController {

    @Autowired
    private ChamadoRepository chamadoRepository;
    @Autowired
    private AgenteDeTiRepository agenteDeTiRepository;

    private ChamadoDTO converterParaDTO(Chamado chamado) {
        ChamadoDTO dto = new ChamadoDTO();
        dto.setId(chamado.getId());
        dto.setTitulo(chamado.getTitulo());
        dto.setDescricao(chamado.getDescricao());
        dto.setNomeSolicitante(chamado.getNomeSolicitante());
        dto.setEmailSolicitante(chamado.getEmailSolicitante());
        dto.setNumeroSolicitante(chamado.getNumeroSolicitante());
        dto.setAgenciaSolicitante(chamado.getAgenciaSolicitante());
        dto.setDataCriacao(chamado.getDataCriacao());
        dto.setDataAssumido(chamado.getDataAssumido());
        dto.setDataConclusao(chamado.getDataConclusao());
        dto.setStatus(chamado.getStatus().toString());
        dto.setAgenteDeTi(
                chamado.getAgenteDeTi() != null ? chamado.getAgenteDeTi().getNomeCompleto() : null
        );


        return dto;
    }

    @GetMapping
    public List<ChamadoDTO> listarChamados() {
        List<Chamado> chamados = chamadoRepository.findAll();
        return chamados.stream()
                .map(this::converterParaDTO)
                .toList();
    }


    @GetMapping("/{id}")
    public ResponseEntity<ChamadoDTO> buscarPorId(@PathVariable Long id) {
        Optional<Chamado> chamado = chamadoRepository.findById(id);
        return chamado
                .map(this::converterParaDTO)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ChamadoDTO> criarChamado(@RequestBody ChamadoDTO dto){
        Chamado chamado = new Chamado();
        chamado.setTitulo(dto.getTitulo());
        chamado.setDescricao(dto.getDescricao());
        chamado.setNomeSolicitante(dto.getNomeSolicitante());
        chamado.setEmailSolicitante(dto.getEmailSolicitante());
        chamado.setNumeroSolicitante(dto.getNumeroSolicitante());
        chamado.setAgenciaSolicitante(dto.getAgenciaSolicitante());
        chamado.setDataCriacao(LocalDateTime.now());
        chamado.setStatus(StatusChamado.ABERTO);
        chamado.setAgenteDeTi(null);

        Chamado salvo = chamadoRepository.save(chamado);
        ChamadoDTO dtoResposta = converterParaDTO(salvo);
        return ResponseEntity.ok(dtoResposta);
    }

    @PutMapping("/{id}/vincular-agente/{idAgente}")
    public ResponseEntity<ChamadoDTO> atribuirAgente(@PathVariable Long id, @PathVariable Long idAgente) {
        Optional<Chamado> optionalChamado = chamadoRepository.findById(id);
        if (optionalChamado.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Optional<AgenteDeTi> optionalAgente = agenteDeTiRepository.findById(idAgente);
        if (optionalAgente.isEmpty()) {
            return ResponseEntity.badRequest().build(); // ou notFound(), dependendo da sua regra
        }

        Chamado chamado = optionalChamado.get();
        AgenteDeTi agente = optionalAgente.get();

        chamado.setAgenteDeTi(agente);
        chamado.setDataAssumido(LocalDateTime.now()); // define a hora atual
        chamado.setResponsavel(agente.getNomeCompleto()); // atribui o nome do agente

        chamadoRepository.save(chamado);

        ChamadoDTO dto = converterParaDTO(chamado);
        return ResponseEntity.ok(dto);
    }

}