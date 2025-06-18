package com.suporteJoaoPessoa.chamados.controller;

import com.suporteJoaoPessoa.chamados.dto.ChamadoDTO;
import com.suporteJoaoPessoa.chamados.model.entity.AgenteDeTi;
import com.suporteJoaoPessoa.chamados.model.entity.Chamado;
import com.suporteJoaoPessoa.chamados.model.enums.StatusChamado;
import com.suporteJoaoPessoa.chamados.repository.AgenteDeTiRepository;
import com.suporteJoaoPessoa.chamados.repository.ChamadoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.suporteJoaoPessoa.chamados.model.enums.StatusChamado.EM_ANDAMENTO;

@Controller
public class PainelController {

    @Autowired
    private ChamadoRepository chamadoRepository;

    @Autowired
    private AgenteDeTiRepository agenteRepository;

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

    @GetMapping("/painel")
    public String exibirPainel(Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }

        Optional<AgenteDeTi> optionalAgente = agenteRepository.findByMatricula(principal.getName());
        if (optionalAgente.isEmpty()) {
            return "redirect:/login";
        }

        AgenteDeTi agente = optionalAgente.get();
        List<Chamado> chamados = chamadoRepository.findAll();

        List<Chamado> chamadosSemResponsavel = chamados.stream()
                .filter(c -> c.getResponsavel() == null || c.getResponsavel().isEmpty())
                .collect(Collectors.toList());

        model.addAttribute("agente", agente);
        model.addAttribute("chamados", chamados);
        model.addAttribute("chamadosSemResponsavel", chamadosSemResponsavel);

        return "painel";
    }

    @GetMapping("/painel/meus")
    public String exibirMeusChamados(Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }

        Optional<AgenteDeTi> optionalAgente = agenteRepository.findByMatricula(principal.getName());
        if (optionalAgente.isEmpty()) {
            return "redirect:/login";
        }

        AgenteDeTi agente = optionalAgente.get();

        // Buscar chamados do agente logado
        List<Chamado> meusChamados = chamadoRepository.findByAgenteDeTiId(agente.getId());

        model.addAttribute("agente", agente);
        model.addAttribute("meusChamados", meusChamados);

        return "painel-meus-chamados";
    }



    @PostMapping("/chamados/{id}/iniciar")
    public String iniciarChamado(@PathVariable Long id, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }

        Optional<AgenteDeTi> optionalAgente = agenteRepository.findByMatricula(principal.getName());
        if (optionalAgente.isEmpty()) {
            return "redirect:/login";
        }

        AgenteDeTi agente = optionalAgente.get();

        Optional<Chamado> chamadoOpt = chamadoRepository.findById(id);
        if (chamadoOpt.isPresent()) {
            Chamado chamado = chamadoOpt.get();
            chamado.setResponsavel(agente.getNomeCompleto());
            chamado.setAgenteDeTi(agente);
            chamado.setDataAssumido(LocalDateTime.now());
            chamado.setStatus(EM_ANDAMENTO);
            chamadoRepository.save(chamado);
        }

        return "redirect:/painel";
    }

    @PostMapping("/chamados/{id}/concluir")
    public String concluirChamado(@PathVariable Long id, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }

        Optional<AgenteDeTi> optionalAgente = agenteRepository.findByMatricula(principal.getName());
        if (optionalAgente.isEmpty()) {
            return "redirect:/login";
        }

        AgenteDeTi agente = optionalAgente.get();

        Optional<Chamado> optionalChamado = chamadoRepository.findById(id);
        if (optionalChamado.isEmpty()) {
            return "redirect:/painel?erro=chamado-nao-encontrado";
        }

        Chamado chamado = optionalChamado.get();

        if (chamado.getAgenteDeTi() == null || !chamado.getAgenteDeTi().getId().equals(agente.getId())) {
            return "redirect:/painel?erro=nao-autorizado";
        }

        chamado.setStatus(StatusChamado.CONCLUIDO);
        chamado.setDataConclusao(LocalDateTime.now());
        chamadoRepository.save(chamado);

        return "redirect:/painel/meus";
    }
}
